/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
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

		Country country = countryLocalService.fetchCountryByNumber(
			user.getCompanyId(), String.valueOf(getCountryIsoCode()));

		if (country == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		_importCommerceRegions(country, serviceContext);
	}

	protected abstract int getCountryIsoCode();

	protected abstract String getFilePath();

	@Reference
	protected CountryLocalService countryLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected RegionLocalService regionLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private JSONArray _getCommerceRegionsJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String regionsJSON = StringUtil.read(
			clazz.getClassLoader(), getFilePath(), false);

		return jsonFactory.createJSONArray(regionsJSON);
	}

	private void _importCommerceRegions(
			Country country, ServiceContext serviceContext)
		throws Exception {

		JSONArray jsonArray = _getCommerceRegionsJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String code = jsonObject.getString("code");
			String name = jsonObject.getString("name");
			double priority = jsonObject.getDouble("priority");

			regionLocalService.addRegion(
				country.getCountryId(), true, name, priority, code,
				serviceContext);
		}
	}

}