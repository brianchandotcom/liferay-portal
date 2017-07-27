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
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
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
@Component(service = CPSpecificationOptionDemoDataCreatorHelper.class)
public class CPSpecificationOptionDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPSpecificationOptions(
			Locale locale, long userId, long groupId, long cpDefinitionId,
			JSONArray jsonArray)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			CPSpecificationOption cpSpecificationOption =
				getCPSpecificationOption(
					locale, userId, groupId, jsonObject, serviceContext);

			String value = jsonObject.getString("value");
			int priority = jsonObject.getInt("priority");

			Map<Locale, String> valueMap = Collections.singletonMap(
				locale, value);

			_cpDefinitionSpecificationOptionValueLocalService.
				addCPDefinitionSpecificationOptionValue(
					cpDefinitionId,
					cpSpecificationOption.getCPSpecificationOptionId(),
					cpSpecificationOption.getCPOptionCategoryId(), valueMap,
					priority, serviceContext);
		}
	}

	public void deleteCPSpecificationOptions() throws PortalException {
		Set<Map.Entry<String, CPSpecificationOption>> entrySet =
			_cpSpecificationOptions.entrySet();

		Iterator<Map.Entry<String, CPSpecificationOption>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, CPSpecificationOption> entry = iterator.next();

			_cpSpecificationOptionLocalService.deleteCPSpecificationOption(
				entry.getValue());

			iterator.remove();
		}
	}

	public void init() {
		_cpSpecificationOptions = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_cpSpecificationOptions = null;
	}

	protected CPSpecificationOption getCPSpecificationOption(
			Locale locale, long userId, long groupId, JSONObject jsonObject,
			ServiceContext serviceContext)
		throws PortalException {

		String key = jsonObject.getString("key");

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptions.get(key);

		if (cpSpecificationOption != null) {
			return cpSpecificationOption;
		}

		String categoryKey = jsonObject.getString("categoryKey");

		Map<String, CPOptionCategory> cpOptionCategories =
			_cpOptionCategoryDemoDataCreatorHelper.getCPOptionCategories();

		CPOptionCategory cpOptionCategory = cpOptionCategories.get(categoryKey);

		String title = jsonObject.getString("title");
		String description = jsonObject.getString("description");
		boolean facetable = jsonObject.getBoolean("facetable");
		Map<Locale, String> titleMap = Collections.singletonMap(locale, title);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, description);

		cpSpecificationOption =
			_cpSpecificationOptionLocalService.addCPSpecificationOption(
				cpOptionCategory.getCPOptionCategoryId(), titleMap,
				descriptionMap, facetable, key, serviceContext);

		_cpSpecificationOptions.put(key, cpSpecificationOption);

		return cpSpecificationOption;
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private CPOptionCategoryDemoDataCreatorHelper
		_cpOptionCategoryDemoDataCreatorHelper;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	private Map<String, CPSpecificationOption> _cpSpecificationOptions;

}