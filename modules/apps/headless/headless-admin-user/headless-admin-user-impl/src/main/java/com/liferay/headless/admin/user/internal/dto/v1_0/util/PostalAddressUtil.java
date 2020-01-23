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

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier Gamarra
 */
public class PostalAddressUtil {

	public static PostalAddress toPostalAddress(
		boolean acceptAllLanguages, Address address, long companyId,
		Locale locale) {

		ListType listType = address.getType();

		return new PostalAddress() {
			{
				addressLocality = address.getCity();
				addressType = listType.getName();
				id = address.getAddressId();
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

						Set<Locale> locales =
							LanguageUtil.getCompanyAvailableLocales(companyId);

						Stream<Locale> localeStream = locales.stream();

						Country country = address.getCountry();

						return localeStream.collect(
							Collectors.toMap(
								Locale::toLanguageTag, country::getName));
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