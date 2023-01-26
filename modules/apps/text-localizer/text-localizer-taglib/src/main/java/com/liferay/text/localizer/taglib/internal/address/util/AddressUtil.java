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

package com.liferay.text.localizer.taglib.internal.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
public class AddressUtil {

	public static String getCountryName(Address address) {
		if (address == null) {
			return null;
		}

		Country country = address.getCountry();

		if ((country == null) || country.isNew()) {
			return null;
		}

		return country.getTitle(LocaleThreadLocal.getThemeDisplayLocale());
	}

	public static String getRegionName(Address address) {
		if (address == null) {
			return null;
		}

		Region region = address.getRegion();

		if ((region == null) || region.isNew()) {
			return null;
		}

		return region.getName();
	}

}