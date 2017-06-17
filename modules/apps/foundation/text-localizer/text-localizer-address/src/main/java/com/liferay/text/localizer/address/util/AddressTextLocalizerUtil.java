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

package com.liferay.text.localizer.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;

/**
 * @author Pei-Jung Lan
 */
public class AddressTextLocalizerUtil {

	public static String getCountry(Address address) {
		long countryId = address.getCountryId();

		Country country = CountryServiceUtil.fetchCountry(countryId);

		if (country != null) {
			return country.getName(getUserLocale());
		}

		return null;
	}

	public static String getRegion(Address address) {
		long regionId = address.getRegionId();

		Region region = RegionServiceUtil.fetchRegion(regionId);

		if (region != null) {
			return region.getName();
		}

		return null;
	}

	public static ThemeDisplay getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return serviceContext.getThemeDisplay();
	}

	public static Locale getUserLocale() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		User user = themeDisplay.getUser();

		if (user != null) {
			return user.getLocale();
		}

		return themeDisplay.getLocale();
	}

}