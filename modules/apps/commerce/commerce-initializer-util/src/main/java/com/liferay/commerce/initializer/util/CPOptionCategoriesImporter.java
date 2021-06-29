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
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

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
@Component(enabled = false, service = CPOptionCategoriesImporter.class)
public class CPOptionCategoriesImporter {

	public List<CPOptionCategory> importCPOptionCategories(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		List<CPOptionCategory> cpOptionCategories = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CPOptionCategory cpOptionCategory = _importCPOptionCategory(
				jsonArray.getJSONObject(i), i, serviceContext);

			cpOptionCategories.add(cpOptionCategory);
		}

		return cpOptionCategories;
	}

	private CPOptionCategory _importCPOptionCategory(
			JSONObject jsonObject, double defaultPriority,
			ServiceContext serviceContext)
		throws PortalException {

		String key = jsonObject.getString("key");

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.fetchCPOptionCategory(
				serviceContext.getCompanyId(), key);

		if (cpOptionCategory != null) {
			return cpOptionCategory;
		}

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> titleMap = Collections.singletonMap(
			locale, CommerceInitializerUtil.getValue(jsonObject, "title", key));

		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, jsonObject.getString("description"));

		double priority = jsonObject.getDouble("priority", defaultPriority);

		return _cpOptionCategoryLocalService.addCPOptionCategory(
			serviceContext.getUserId(), titleMap, descriptionMap, priority, key,
			serviceContext);
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}