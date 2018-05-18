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