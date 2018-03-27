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

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "commerce.region.starter.key=" + ItalyCommerceRegionsStarter.ITALY_NUMERIC_ISO_CODE,
	service = CommerceRegionsStarter.class
)
public class ItalyCommerceRegionsStarter implements CommerceRegionsStarter {

	public static final int ITALY_NUMERIC_ISO_CODE = 380;

	public CommerceCountry getCommerceCountry(long groupId)
		throws PortalException {

		return _commerceCountryLocalService.fetchCommerceCountry(
			groupId, ITALY_NUMERIC_ISO_CODE);
	}

	public JSONArray getCommerceRegionsJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String layoutsPath = "com/liferay/commerce/internal/italy.json";

		String regionsJSON = StringUtil.read(
			clazz.getClassLoader(), layoutsPath, false);

		return _jsonFactory.createJSONArray(regionsJSON);
	}

	@Override
	public void start(ServiceContext serviceContext) throws Exception {
		CommerceCountry commerceCountry = getCommerceCountry(
			serviceContext.getScopeGroupId());

		if (commerceCountry == null) {
			return;
		}

		JSONArray jsonArray = getCommerceRegionsJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String code = jsonObject.getString("code");
			String name = jsonObject.getString("name");
			double priority = jsonObject.getDouble("priority");

			_commerceRegionLocalService.addCommerceRegion(
				commerceCountry.getCommerceCountryId(), name, code, priority,
				true, serviceContext);
		}
	}

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}