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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
public abstract class BaseCommerceRegionsStarter
	implements CommerceRegionsStarter {

	@Override
	public String getKey() {
		return String.valueOf(getCountryIsoCode());
	}

	@Override
	public void start(long userId) throws Exception {
		User user = userLocalService.getUser(userId);

		CommerceCountry commerceCountry =
			commerceCountryLocalService.fetchCommerceCountry(
				user.getCompanyId(), getCountryIsoCode());

		if (commerceCountry == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		_importCommerceRegions(commerceCountry, serviceContext);
	}

	protected abstract int getCountryIsoCode();

	protected abstract String getFilePath();

	@Reference
	protected CommerceCountryLocalService commerceCountryLocalService;

	@Reference
	protected CommerceRegionLocalService commerceRegionLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected UserLocalService userLocalService;

	private JSONArray _getCommerceRegionsJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String regionsJSON = StringUtil.read(
			clazz.getClassLoader(), getFilePath(), false);

		return jsonFactory.createJSONArray(regionsJSON);
	}

	private void _importCommerceRegions(
			CommerceCountry commerceCountry, ServiceContext serviceContext)
		throws Exception {

		JSONArray jsonArray = _getCommerceRegionsJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String code = jsonObject.getString("code");
			String name = jsonObject.getString("name");
			double priority = jsonObject.getDouble("priority");

			commerceRegionLocalService.addCommerceRegion(
				commerceCountry.getCommerceCountryId(), name, code, priority,
				true, serviceContext);
		}
	}

}