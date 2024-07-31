/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class PostalAddressUtil {

	public static long[] getAccountEntryContactAddressListTypeIds(
		long companyId, ListTypeLocalService listTypeLocalService) {

		return TransformUtil.transformToLongArray(
			_names,
			name -> {
				ListType listType = listTypeLocalService.getListType(
					companyId, name,
					AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS);

				return listType.getListTypeId();
			});
	}

	public static PostalAddress toPostalAddress(
		boolean acceptAllLanguages, Address address, long companyId,
		Locale locale) {

		ListType listType = address.getListType();

		return new PostalAddress() {
			{
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
				setAddressLocality(address::getCity);
				setAddressRegion(
					() -> {
						if (address.getRegionId() <= 0) {
							return null;
						}

						Region region = address.getRegion();

						return region.getName();
					});
				setAddressType(listType::getName);
				setExternalReferenceCode(address::getExternalReferenceCode);
				setId(address::getAddressId);
				setName(address::getName);
				setPhoneNumber(address::getPhoneNumber);
				setPostalCode(address::getZip);
				setPrimary(address::isPrimary);
				setStreetAddressLine1(address::getStreet1);
				setStreetAddressLine2(address::getStreet2);
				setStreetAddressLine3(address::getStreet3);
			}
		};
	}

	private static final List<String> _names = ListUtil.fromArray(
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_BILLING,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_OTHER,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_P_O_BOX,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_SHIPPING);

}