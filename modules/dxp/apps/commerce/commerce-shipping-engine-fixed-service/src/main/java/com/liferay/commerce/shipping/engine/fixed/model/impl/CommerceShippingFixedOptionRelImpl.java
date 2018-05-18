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