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
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.commerce.service.CommerceWarehouseLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceShipmentImpl extends CommerceShipmentBaseImpl {

	public CommerceShipmentImpl() {
	}

	@Override
	public CommerceAddress fetchCommerceAddress() {
		return CommerceAddressLocalServiceUtil.fetchCommerceAddress(
			getCommerceAddressId());
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod() {
		return
			CommerceShippingMethodLocalServiceUtil.fetchCommerceShippingMethod(
				getCommerceShippingMethodId());
	}

	@Override
	public CommerceWarehouse fetchCommerceWarehouse() {
		return CommerceWarehouseLocalServiceUtil.fetchCommerceWarehouse(
			getCommerceWarehouseId());
	}

}