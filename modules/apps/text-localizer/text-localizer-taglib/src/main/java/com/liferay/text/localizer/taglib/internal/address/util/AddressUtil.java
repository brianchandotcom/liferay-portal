/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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