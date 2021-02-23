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

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceAddressImpl extends CommerceAddressBaseImpl {

	public CommerceAddressImpl() {
	}

	@Override
	public Country fetchCountry() {
		return CountryLocalServiceUtil.fetchCountry(getCountryId());
	}

	@Override
	public Country getCountry() throws PortalException {
		return CountryLocalServiceUtil.getCountry(getCountryId());
	}

	@Override
	public Region getRegion() throws PortalException {
		long regionId = getRegionId();

		if (regionId > 0) {
			return RegionLocalServiceUtil.getRegion(regionId);
		}

		return null;
	}

	@Override
	public boolean isGeolocated() {
		if ((getLatitude() == 0) && (getLongitude() == 0)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSameAddress(CommerceAddress commerceAddress) {
		if (Objects.equals(getName(), commerceAddress.getName()) &&
			Objects.equals(getStreet1(), commerceAddress.getStreet1()) &&
			Objects.equals(getStreet2(), commerceAddress.getStreet2()) &&
			Objects.equals(getStreet3(), commerceAddress.getStreet3()) &&
			Objects.equals(getCity(), commerceAddress.getCity()) &&
			Objects.equals(getZip(), commerceAddress.getZip()) &&
			(getRegionId() == commerceAddress.getRegionId()) &&
			(getCountryId() == commerceAddress.getCountryId()) &&
			Objects.equals(
				getPhoneNumber(), commerceAddress.getPhoneNumber())) {

			return true;
		}

		return false;
	}

}