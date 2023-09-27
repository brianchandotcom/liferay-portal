/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CPOptionCategoriesImporter.class)
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
			return _cpOptionCategoryLocalService.updateCPOptionCategory(
				cpOptionCategory.getCPOptionCategoryId(),
				_toMap(key, jsonObject, "title"),
				_toMap(null, jsonObject, "description"),
				jsonObject.getDouble("priority", defaultPriority), key);
		}

		return _cpOptionCategoryLocalService.addCPOptionCategory(
			serviceContext.getUserId(), _toMap(key, jsonObject, "title"),
			_toMap(null, jsonObject, "description"),
			jsonObject.getDouble("priority", defaultPriority), key,
			serviceContext);
	}

	private Map<Locale, String> _toMap(
		String defaultValue, JSONObject jsonObject, String nodeName) {

		String value = jsonObject.getString(nodeName);

		if (Validator.isBlank(value)) {
			if (Validator.isBlank(defaultValue)) {
				return Collections.emptyMap();
			}

			return Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				CommerceInitializerUtil.getValue(
					jsonObject, nodeName, defaultValue));
		}

		Map<Locale, String> map = new HashMap<>();

		Map<String, String> valuesMap = ObjectMapperUtil.readValue(
			HashMap.class, value);

		for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
			map.put(
				LocaleUtil.fromLanguageId(entry.getKey()), entry.getValue());
		}

		return map;
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}