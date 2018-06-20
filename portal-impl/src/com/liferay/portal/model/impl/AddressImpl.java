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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;

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
		catch (Exception e) {
			country = new CountryImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return country;
	}

	@Override
	public Region getRegion() {
		Region region = null;

		try {
			region = RegionServiceUtil.getRegion(getRegionId());
		}
		catch (Exception e) {
			region = new RegionImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return region;
	}

	@Override
	public ListType getType() {
		ListType type = null;

		try {
			type = ListTypeServiceUtil.getListType(getTypeId());
		}
		catch (Exception e) {
			type = new ListTypeImpl();

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return type;
	}

	private static final Log _log = LogFactoryUtil.getLog(AddressImpl.class);

}