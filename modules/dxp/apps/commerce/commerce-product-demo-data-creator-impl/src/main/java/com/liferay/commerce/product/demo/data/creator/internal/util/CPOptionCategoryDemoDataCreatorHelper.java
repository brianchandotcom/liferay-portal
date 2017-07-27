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

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
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
@Component(service = CPOptionCategoryDemoDataCreatorHelper.class)
public class CPOptionCategoryDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPOptionCategories(
			long userId, long groupId, JSONArray jsonArray)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject cpOptionCategoryJSONObject = jsonArray.getJSONObject(i);

			createCPOptionCategory(cpOptionCategoryJSONObject, serviceContext);
		}
	}

	public CPOptionCategory createCPOptionCategory(
			JSONObject cpOptionCategoryJSONObject,
			ServiceContext serviceContext)
		throws PortalException {

		String key = cpOptionCategoryJSONObject.getString("key");

		CPOptionCategory cpOptionCategory = _cpOptionCategories.get(key);

		if (cpOptionCategory != null) {
			return cpOptionCategory;
		}

		cpOptionCategory = _cpOptionCategoryLocalService.fetchCPOptionCategory(
			serviceContext.getScopeGroupId(), key);

		if (cpOptionCategory != null) {
			_cpOptionCategories.put(key, cpOptionCategory);

			return cpOptionCategory;
		}

		String title = cpOptionCategoryJSONObject.getString("title");
		String description = cpOptionCategoryJSONObject.getString(
			"description");
		int priority = cpOptionCategoryJSONObject.getInt("priority");
		Map<Locale, String> titleMap = Collections.singletonMap(
			Locale.US, title);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			Locale.US, description);

		cpOptionCategory = _cpOptionCategoryLocalService.addCPOptionCategory(
			titleMap, descriptionMap, priority, key, serviceContext);

		_cpOptionCategories.put(key, cpOptionCategory);

		return cpOptionCategory;
	}

	public void deleteCPOptionCategories() throws PortalException {
		Set<Map.Entry<String, CPOptionCategory>> entrySet =
			_cpOptionCategories.entrySet();

		Iterator<Map.Entry<String, CPOptionCategory>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, CPOptionCategory> entry = iterator.next();

			_cpOptionCategoryLocalService.deleteCPOptionCategory(
				entry.getValue());

			iterator.remove();
		}
	}

	public Map<String, CPOptionCategory> getCPOptionCategories() {
		return _cpOptionCategories;
	}

	public void init() {
		_cpOptionCategories = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_cpOptionCategories = null;
	}

	private Map<String, CPOptionCategory> _cpOptionCategories;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

}