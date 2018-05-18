/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	public CommerceCountry fetchCommerceCountry() {
		return CommerceCountryLocalServiceUtil.fetchCommerceCountry(
			getCommerceCountryId());
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