/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AddressImpl extends AddressBaseImpl {

	@Override
	public Country getCountry() {
		Country country = null;

		try {
			country = CountryServiceUtil.getCountry(getCountryId());
		}
		catch (Exception exception) {
			country = new CountryImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return country;
	}

	public ListType getListType() {
		ListType listType = null;

		try {
			listType = ListTypeServiceUtil.getListType(getListTypeId());
		}
		catch (Exception exception) {
			listType = new ListTypeImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return listType;
	}

	@Override
	public String getPhoneNumber() {
		List<Phone> phones = PhoneLocalServiceUtil.getPhones(
			getCompanyId(), getModelClassName(), getAddressId());

		if (phones.isEmpty()) {
			return null;
		}

		Phone phone = phones.get(0);

		return phone.getNumber();
	}

	@Override
	public Region getRegion() {
		Region region = null;

		try {
			region = RegionServiceUtil.getRegion(getRegionId());
		}
		catch (Exception exception) {
			region = new RegionImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return region;
	}

	private static final Log _log = LogFactoryUtil.getLog(AddressImpl.class);

}