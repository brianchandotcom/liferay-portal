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

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceAddressImpl extends CommerceAddressBaseImpl {

	public CommerceAddressImpl() {
	}

	@Override
	public CommerceCountry getCommerceCountry() throws PortalException {
		long commerceCountryId = getCommerceCountryId();

		if (commerceCountryId > 0) {
			return CommerceCountryLocalServiceUtil.getCommerceCountry(
				commerceCountryId);
		}

		return null;
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

}