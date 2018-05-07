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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionOptionValueRelDemoDataCreatorHelper.class)
public class CPDefinitionOptionValueRelDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPDefinitionOptionValueRels(
			Locale locale, long userId, long groupId,
			CPDefinitionOptionRel cpDefinitionOptionRel, JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("name");
			int priority = jsonObject.getInt("priority");
			String key = jsonObject.getString("key");

			Map<Locale, String> nameMap = Collections.singletonMap(
				locale, name);

			ServiceContext serviceContext = getServiceContext(userId, groupId);

			_cpDefinitionOptionValueRelLocalService.
				addCPDefinitionOptionValueRel(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(), nameMap,
					priority, key, serviceContext);
		}

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		_cpDefinitionOptionValueRels.put(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
			cpDefinitionOptionValueRels);
	}

	public Map<Long, List<CPDefinitionOptionValueRel>>
		getCPDefinitionOptionValueRels() {

		return _cpDefinitionOptionValueRels;
	}

	public void init() {
		_cpDefinitionOptionValueRels = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_cpDefinitionOptionValueRels = null;
	}

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	private Map<Long, List<CPDefinitionOptionValueRel>>
		_cpDefinitionOptionValueRels;

}