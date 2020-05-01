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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier Gamarra
 */
public class PostalAddressUtil {

	public static long getServiceBuilderRegionId(
		String addressRegion, long countryId) {

		if (Validator.isNull(addressRegion) || (countryId <= 0)) {
			return 0;
		}

		Region region = RegionServiceUtil.fetchRegion(countryId, addressRegion);

		if (region != null) {
			return region.getRegionId();
		}

		List<Region> regions = RegionServiceUtil.getRegions(countryId);

		for (Region curRegion : regions) {
			if (StringUtil.equalsIgnoreCase(
					addressRegion, curRegion.getName())) {

				return curRegion.getRegionId();
			}
		}

		return 0;
	}

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

						Stream<Locale> localesStream = locales.stream();

						Country country = address.getCountry();

						return localesStream.collect(
							Collectors.toMap(
								LocaleUtil::toBCP47LanguageId,
								country::getName));
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

	public static Address toServiceBuilderAddress(
		PostalAddress postalAddress, String type) {

		String street1 = postalAddress.getStreetAddressLine1();
		String street2 = postalAddress.getStreetAddressLine2();
		String street3 = postalAddress.getStreetAddressLine3();
		String city = postalAddress.getAddressLocality();
		String zip = postalAddress.getPostalCode();
		long countryId = toServiceBuilderCountryId(
			postalAddress.getAddressCountry());

		if (Validator.isNull(street1) && Validator.isNull(street2) &&
			Validator.isNull(street3) && Validator.isNull(city) &&
			Validator.isNull(zip) && (countryId == 0)) {

			return null;
		}

		Address address = AddressLocalServiceUtil.createAddress(
			GetterUtil.getLong(postalAddress.getId()));

		address.setStreet1(street1);
		address.setStreet2(street2);
		address.setStreet3(street3);
		address.setCity(city);
		address.setZip(zip);
		address.setRegionId(
			getServiceBuilderRegionId(
				postalAddress.getAddressRegion(), countryId));
		address.setCountryId(countryId);
		address.setTypeId(
			ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
				"other", postalAddress.getAddressType(), type));
		address.setMailing(true);
		address.setPrimary(GetterUtil.getBoolean(postalAddress.getPrimary()));

		return address;
	}

	public static Country toServiceBuilderCountry(String addressCountry) {
		try {
			Country country = CountryServiceUtil.fetchCountryByA2(
				addressCountry);

			if (country != null) {
				return country;
			}

			country = CountryServiceUtil.fetchCountryByA3(addressCountry);

			if (country != null) {
				return country;
			}

			return CountryServiceUtil.getCountryByName(addressCountry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	public static long toServiceBuilderCountryId(String addressCountry) {
		return Optional.ofNullable(
			addressCountry
		).map(
			PostalAddressUtil::toServiceBuilderCountry
		).map(
			Country::getCountryId
		).orElse(
			(long)0
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PostalAddressUtil.class);

}