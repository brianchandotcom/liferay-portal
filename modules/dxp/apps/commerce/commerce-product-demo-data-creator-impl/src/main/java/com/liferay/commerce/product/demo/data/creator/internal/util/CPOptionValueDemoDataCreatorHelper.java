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

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPOptionValueDemoDataCreatorHelper.class)
public class CPOptionValueDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPOptionValues(
			Locale locale, long userId, long groupId, long cpOptionId,
			long cpDefinitionOptionRelId, JSONArray values)
		throws PortalException {

		for (int i = 0; i < values.length(); i++) {
			JSONObject value = values.getJSONObject(i);

			String name = value.getString("name");
			String title = value.getString("title");
			int priority = value.getInt("priority");

			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(locale, title);

			CPOptionValue cpOptionValue = getCPOptionValue(
				locale, userId, groupId, cpOptionId, value);

			_cpDefinitionOptionValueRelDemoDataCreatorHelper.
				createCPDefinitionOptionValueRel(
					userId, groupId, cpDefinitionOptionRelId, name, titleMap,
					priority);
		}
	}

	public CPOptionValue createCPOptionValue(
			long userId, long groupId, long cpOptionId, String name,
			Map<Locale, String> titleMap, int priority)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		CPOptionValue cpOptionValue =
			_cpOptionValueLocalService.addCPOptionValue(
				cpOptionId, name, titleMap, priority, serviceContext);

		return cpOptionValue;
	}

	public CPOptionValue getCPOptionValue(
			Locale locale, long userId, long groupId, long cpOptionId,
			JSONObject value)
		throws PortalException {

		CPOptionValue cpOptionValue = null;

		String name = value.getString("name");

		cpOptionValue = _cpOptionValues.get(name);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		String title = value.getString("title");
		int priority = value.getInt("priority");

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, title);

		cpOptionValue = createCPOptionValue(
			userId, groupId, cpOptionId, name, titleMap, priority);

		_cpOptionValues.put(name, cpOptionValue);

		return cpOptionValue;
	}

	@Reference
	private CPDefinitionOptionValueRelDemoDataCreatorHelper
		_cpDefinitionOptionValueRelDemoDataCreatorHelper;

	@Reference
	private CPOptionValueLocalService _cpOptionValueLocalService;

	private final Map<String, CPOptionValue> _cpOptionValues = new HashMap<>();

}