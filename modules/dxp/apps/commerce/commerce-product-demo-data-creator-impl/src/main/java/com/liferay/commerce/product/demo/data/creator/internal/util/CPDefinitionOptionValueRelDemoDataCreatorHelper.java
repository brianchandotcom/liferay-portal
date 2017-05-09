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

import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
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
@Component(service = CPDefinitionOptionValueRelDemoDataCreatorHelper.class)
public class CPDefinitionOptionValueRelDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPDefinitionOptionValueRels(
			Locale locale, long userId, long groupId,
			long cpDefinitionOptionRelId, JSONArray values)
		throws PortalException {

		for (int i = 0; i < values.length(); i++) {
			JSONObject value = values.getJSONObject(i);

			String name = value.getString("name");
			String title = value.getString("title");
			int priority = value.getInt("priority");

			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(locale, title);

			createCPDefinitionOptionValueRel(
				userId, groupId, cpDefinitionOptionRelId, name, titleMap,
				priority);
		}
	}

	public void createCPDefinitionOptionValueRel(
			long userId, long groupId, long cpDefinitionOptionRelId,
			String name, Map<Locale, String> titleMap, int priority)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		_cpDefinitionOptionValueRelLocalService.addCPDefinitionOptionValueRel(
			cpDefinitionOptionRelId, name, titleMap, priority, serviceContext);
	}

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

}