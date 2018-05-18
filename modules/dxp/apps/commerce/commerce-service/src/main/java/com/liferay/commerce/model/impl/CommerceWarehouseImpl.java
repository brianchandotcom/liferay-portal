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

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.commerce.service.CommerceWarehouseItemLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceWarehouseImpl extends CommerceWarehouseBaseImpl {

	public CommerceWarehouseImpl() {
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

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems() {
		return CommerceWarehouseItemLocalServiceUtil.
			getCommerceWarehouseItemsByCommerceWarehouseId(
				getCommerceWarehouseId());
	}

	@Override
	public boolean isGeolocated() {
		if ((getLatitude() == 0) && (getLongitude() == 0)) {
			return false;
		}

		return true;
	}

}