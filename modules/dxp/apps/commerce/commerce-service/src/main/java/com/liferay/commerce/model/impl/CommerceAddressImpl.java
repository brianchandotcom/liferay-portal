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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceAddressImpl extends CommerceAddressBaseImpl {

	public CommerceAddressImpl() {
	}

	@Override
	public CommerceCountry getCommerceCountry() throws PortalException {
		return CommerceCountryLocalServiceUtil.getCommerceCountry(
			getCommerceCountryId());
	}

	@Override
	public CommerceRegion getCommerceRegion() throws PortalException {
		long commerceRegionId = getCommerceRegionId();

		if (commerceRegionId > 0) {
			return CommerceRegionLocalServiceUtil.getCommerceRegion(
				commerceRegionId);
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
			(getCommerceRegionId() == commerceAddress.getCommerceRegionId()) &&
			(getCommerceCountryId() ==
				commerceAddress.getCommerceCountryId()) &&
			Objects.equals(
				getPhoneNumber(), commerceAddress.getPhoneNumber())) {

			return true;
		}

		return false;
	}

}