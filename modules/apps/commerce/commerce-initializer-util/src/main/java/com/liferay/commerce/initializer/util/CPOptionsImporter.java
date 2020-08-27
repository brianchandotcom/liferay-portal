/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.initializer.util.internal.CommerceInitializerUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CPOptionsImporter.class)
public class CPOptionsImporter {

	public List<CPOption> importCPOptions(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(user.getCompanyId());

		List<CPOption> cpOptions = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CPOption cpOption = _importCPOption(
				jsonArray.getJSONObject(i), serviceContext);

			cpOptions.add(cpOption);
		}

		return cpOptions;
	}

	private CPOptionValue _addCPOptionValue(
			CPOption cpOption, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(),
			TextFormatter.format(key, TextFormatter.J));

		return _cpOptionValueLocalService.addCPOptionValue(
			cpOption.getCPOptionId(), nameMap, priority, key, serviceContext);
	}

	private CPOption _importCPOption(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option

		String key = jsonObject.getString("Key");

		CPOption cpOption = _cpOptionLocalService.fetchCPOption(
			serviceContext.getCompanyId(), key);

		if (cpOption != null) {
			return cpOption;
		}

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = Collections.singletonMap(
			locale, CommerceInitializerUtil.getValue(jsonObject, "Name", key));

		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, jsonObject.getString("Description"));

		String ddmFormFieldTypeName = jsonObject.getString(
			"DDMFormFieldTypeName", "select");
		boolean facetable = jsonObject.getBoolean("Facetable");
		boolean required = jsonObject.getBoolean("Required");
		boolean skuContributor = jsonObject.getBoolean("SkuContributor");

		cpOption = _cpOptionLocalService.addCPOption(
			serviceContext.getUserId(), nameMap, descriptionMap,
			ddmFormFieldTypeName, facetable, required, skuContributor, key,
			null, serviceContext);

		// Commerce product option values

		JSONArray valuesJSONArray = jsonObject.getJSONArray("Values");

		if (valuesJSONArray == null) {
			return cpOption;
		}

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

			if (valueJSONObject != null) {
				_importCPOptionValue(
					valueJSONObject, cpOption, i, serviceContext);
			}
			else {
				_addCPOptionValue(
					cpOption, i, valuesJSONArray.getString(i), serviceContext);
			}
		}

		return cpOption;
	}

	private CPOptionValue _importCPOptionValue(
			JSONObject jsonObject, CPOption cpOption, double defaultPriority,
			ServiceContext serviceContext)
		throws PortalException {

		String key = jsonObject.getString("Key");

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(),
			CommerceInitializerUtil.getValue(jsonObject, "Name", key));

		double priority = jsonObject.getDouble("Priority", defaultPriority);

		return _cpOptionValueLocalService.addCPOptionValue(
			cpOption.getCPOptionId(), nameMap, priority, key, serviceContext);
	}

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private CPOptionValueLocalService _cpOptionValueLocalService;

	@Reference
	private UserLocalService _userLocalService;

}