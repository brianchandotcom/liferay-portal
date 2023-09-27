/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class PostalAddressUtil {

	public static PostalAddress toPostalAddress(
		boolean acceptAllLanguages, Address address, long companyId,
		Locale locale) {

		ListType listType = address.getListType();

		return new PostalAddress() {
			{
				addressLocality = address.getCity();
				addressType = listType.getName();
				id = address.getAddressId();
				name = address.getName();
				postalCode = address.getZip();
				primary = address.isPrimary();
				streetAddressLine1 = address.getStreet1();
				streetAddressLine2 = address.getStreet2();
				streetAddressLine3 = address.getStreet3();

				setAddressCountry(
					() -> {
						if (address.getCountryId() <= 0) {
							return null;
						}

						Country country = address.getCountry();

						return country.getName(locale);
					});
				setAddressCountry_i18n(
					() -> {
						if (!acceptAllLanguages) {
							return null;
						}

						Map<String, String> countryNames = new HashMap<>();

						Country country = address.getCountry();

						for (Locale locale :
								LanguageUtil.getCompanyAvailableLocales(
									companyId)) {

							countryNames.put(
								LocaleUtil.toBCP47LanguageId(locale),
								country.getName());
						}

						return countryNames;
					});
				setAddressRegion(
					() -> {
						if (address.getRegionId() <= 0) {
							return null;
						}

						Region region = address.getRegion();

						return region.getName();
					});
			}
		};
	}

}