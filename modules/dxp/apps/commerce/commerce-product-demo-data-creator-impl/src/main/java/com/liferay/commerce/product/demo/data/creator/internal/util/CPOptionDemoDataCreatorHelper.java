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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPOptionDemoDataCreatorHelper.class)
public class CPOptionDemoDataCreatorHelper extends BaseCPDemoDataCreatorHelper {

	public void addCPOptions(
			Locale locale, long userId, long groupId, long cpDefinitionId,
			JSONArray jsonArray)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("name");
			String title = jsonObject.getString("title");
			String description = jsonObject.getString("description");
			String ddmFormFieldTypeName = jsonObject.getString(
				"ddmFormFieldTypeName");
			int priority = jsonObject.getInt("priority");
			boolean facetable = jsonObject.getBoolean("facetable");
			boolean skuContributor = jsonObject.getBoolean("skuContributor");

			Map<Locale, String> titleMap = Collections.singletonMap(
				locale, title);
			Map<Locale, String> descriptionMap = Collections.singletonMap(
				locale, description);

			CPOption cpOption = getCPOption(
				locale, userId, groupId, jsonObject);

			long cpOptionId = cpOption.getCPOptionId();

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
					cpDefinitionId, cpOptionId, name, titleMap, descriptionMap,
					ddmFormFieldTypeName, priority, facetable, skuContributor,
					false, false, serviceContext);

			long cpDefinitionOptionRelId =
				cpDefinitionOptionRel.getCPDefinitionOptionRelId();

			JSONArray cpDefinitionOptionValueRelsJSONArray =
				jsonObject.getJSONArray("values");

			_cpDefinitionOptionValueRelDemoDataCreatorHelper.
				addCPDefinitionOptionValueRels(
					locale, userId, groupId, cpDefinitionOptionRelId,
					cpDefinitionOptionValueRelsJSONArray);
		}
	}

	public void deleteCPOptions() throws PortalException {
		Set<Map.Entry<String, CPOption>> entrySet = _cpOptions.entrySet();

		Iterator<Map.Entry<String, CPOption>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, CPOption> entry = iterator.next();

			try {
				_cpOptionLocalService.deleteCPOption(entry.getValue());
			}
			catch (NoSuchCPOptionException nscpoe) {
				if (_log.isWarnEnabled()) {
					_log.warn(nscpoe);
				}
			}

			_cpOptions.remove(entry.getKey());
		}
	}

	public void init() {
		_cpOptions = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_cpOptions = null;
	}

	protected CPOption getCPOption(
			Locale locale, long userId, long groupId, JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");

		CPOption cpOption = _cpOptions.get(name);

		if (cpOption != null) {
			return cpOption;
		}

		String title = jsonObject.getString("title");
		String description = jsonObject.getString("description");
		String ddmFormFieldTypeName = jsonObject.getString(
			"ddmFormFieldTypeName");
		boolean facetable = jsonObject.getBoolean("facetable");
		boolean skuContributor = jsonObject.getBoolean("skuContributor");

		Map<Locale, String> titleMap = Collections.singletonMap(locale, title);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, description);

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		cpOption = _cpOptionLocalService.addCPOption(
			name, titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
			false, skuContributor, serviceContext);

		_cpOptions.put(name, cpOption);

		return cpOption;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionDemoDataCreatorHelper.class);

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelDemoDataCreatorHelper
		_cpDefinitionOptionValueRelDemoDataCreatorHelper;

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	private Map<String, CPOption> _cpOptions;

}