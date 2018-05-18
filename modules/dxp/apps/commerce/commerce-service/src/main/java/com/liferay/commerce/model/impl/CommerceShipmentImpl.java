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