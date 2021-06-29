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
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

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
@Component(enabled = false, service = CPSpecificationOptionsImporter.class)
public class CPSpecificationOptionsImporter {

	public List<CPSpecificationOption> importCPSpecificationOptions(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		List<CPSpecificationOption> cpSpecificationOptions = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CPSpecificationOption cpSpecificationOption =
				_importCPSpecificationOption(
					jsonArray.getJSONObject(i), serviceContext);

			cpSpecificationOptions.add(cpSpecificationOption);
		}

		return cpSpecificationOptions;
	}

	private CPSpecificationOption _importCPSpecificationOption(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		String key = jsonObject.getString("key");

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				serviceContext.getCompanyId(), key);

		if (cpSpecificationOption != null) {
			return cpSpecificationOption;
		}

		long cpOptionCategoryId = 0;

		String categoryKey = jsonObject.getString("categoryKey");

		if (Validator.isNotNull(categoryKey)) {
			CPOptionCategory cpOptionCategory =
				_cpOptionCategoryLocalService.getCPOptionCategory(
					serviceContext.getCompanyId(), categoryKey);

			cpOptionCategoryId = cpOptionCategory.getCPOptionCategoryId();
		}

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> titleMap = Collections.singletonMap(
			locale, CommerceInitializerUtil.getValue(jsonObject, "title", key));

		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, jsonObject.getString("description"));

		boolean facetable = jsonObject.getBoolean("facetable");

		return _cpSpecificationOptionLocalService.addCPSpecificationOption(
			serviceContext.getUserId(), cpOptionCategoryId, titleMap,
			descriptionMap, facetable, key, serviceContext);
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}