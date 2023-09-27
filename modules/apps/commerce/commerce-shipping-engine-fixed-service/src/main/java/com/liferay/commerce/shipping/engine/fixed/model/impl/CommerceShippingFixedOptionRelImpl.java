/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalServiceUtil;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelImpl
	extends CommerceShippingFixedOptionRelBaseImpl {

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws PortalException {

		if (getCommerceInventoryWarehouseId() > 0) {
			return CommerceInventoryWarehouseLocalServiceUtil.
				getCommerceInventoryWarehouse(
					getCommerceInventoryWarehouseId());
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
	public Country getCountry() throws PortalException {
		if (getCountryId() > 0) {
			return CountryLocalServiceUtil.getCountry(getCountryId());
		}

		return null;
	}

	@Override
	public Region getRegion() throws PortalException {
		if (getRegionId() > 0) {
			return RegionLocalServiceUtil.getRegion(getRegionId());
		}

		return null;
	}

}