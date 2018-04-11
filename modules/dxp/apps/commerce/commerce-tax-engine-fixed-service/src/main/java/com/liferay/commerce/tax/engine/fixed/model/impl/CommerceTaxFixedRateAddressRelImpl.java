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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalServiceUtil;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.commerce.service.CommerceTaxMethodLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelImpl
	extends CommerceTaxFixedRateAddressRelBaseImpl {

	public CommerceTaxFixedRateAddressRelImpl() {
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
	public CommerceTaxMethod getCommerceTaxMethod() throws PortalException {
		if (getCommerceTaxMethodId() > 0) {
			return CommerceTaxMethodLocalServiceUtil.getCommerceTaxMethod(
				getCommerceTaxMethodId());
		}

		return null;
	}

	@Override
	public CPTaxCategory getCPTaxCategory() throws PortalException {
		return CPTaxCategoryLocalServiceUtil.getCPTaxCategory(
			getCPTaxCategoryId());
	}

}