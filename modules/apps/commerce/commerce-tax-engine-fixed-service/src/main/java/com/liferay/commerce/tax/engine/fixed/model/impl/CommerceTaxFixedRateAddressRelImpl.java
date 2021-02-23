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

package com.liferay.commerce.tax.engine.fixed.model.impl;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalServiceUtil;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelImpl
	extends CommerceTaxFixedRateAddressRelBaseImpl {

	public CommerceTaxFixedRateAddressRelImpl() {
	}

	@Override
	public CommerceTaxMethod getCommerceTaxMethod() throws PortalException {
		if (getCommerceTaxMethodId() > 0) {
			return CommerceTaxMethodLocalServiceUtil.getCommerceTaxMethod(
				getCommerceTaxMethodId());
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
	public CPTaxCategory getCPTaxCategory() throws PortalException {
		return CPTaxCategoryLocalServiceUtil.getCPTaxCategory(
			getCPTaxCategoryId());
	}

	@Override
	public Region getRegion() throws PortalException {
		if (getRegionId() > 0) {
			return RegionLocalServiceUtil.getRegion(getRegionId());
		}

		return null;
	}

}