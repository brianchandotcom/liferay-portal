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

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.commerce.service.CommerceWarehouseLocalServiceUtil;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceShippingFixedOptionRelImpl
	extends CommerceShippingFixedOptionRelBaseImpl {

	public CommerceShippingFixedOptionRelImpl() {
	}

	@Override
	public CommerceCountry getCommerceCountry() throws PortalException {
		if (getCommerceCountryId() > 0) {
			return CommerceCountryLocalServiceUtil.getCommerceCountry(
				getCommerceCountryId());
		}

		return null;
	}

	@Override
	public CommerceRegion getCommerceRegion() throws PortalException {
		if (getCommerceRegionId() > 0) {
			return CommerceRegionLocalServiceUtil.getCommerceRegion(
				getCommerceRegionId());
		}

		return null;
	}

	@Override
	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws PortalException {

		if (getCommerceShippingFixedOptionId() > 0) {
			return CommerceShippingFixedOptionLocalServiceUtil.
				getCommerceShippingFixedOption(
					getCommerceShippingFixedOptionId());
		}

		return null;
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod()
		throws PortalException {

		if (getCommerceShippingMethodId() > 0) {
			return CommerceShippingMethodLocalServiceUtil.
				getCommerceShippingMethod(getCommerceShippingMethodId());
		}

		return null;
	}

	@Override
	public CommerceWarehouse getCommerceWarehouse() throws PortalException {
		if (getCommerceWarehouseId() > 0) {
			return CommerceWarehouseLocalServiceUtil.getCommerceWarehouse(
				getCommerceWarehouseId());
		}

		return null;
	}

}