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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxCalculationLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCalculationLocalService
 * @generated
 */
@ProviderType
public class CommerceTaxCalculationLocalServiceWrapper
	implements CommerceTaxCalculationLocalService,
		ServiceWrapper<CommerceTaxCalculationLocalService> {
	public CommerceTaxCalculationLocalServiceWrapper(
		CommerceTaxCalculationLocalService commerceTaxCalculationLocalService) {
		_commerceTaxCalculationLocalService = commerceTaxCalculationLocalService;
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxRate> getCommerceTaxRates(
		long siteGroupId, long userId, long cpTaxCategoryId) {
		return _commerceTaxCalculationLocalService.getCommerceTaxRates(siteGroupId,
			userId, cpTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTaxCalculationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceTaxCalculationLocalService getWrappedService() {
		return _commerceTaxCalculationLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCalculationLocalService commerceTaxCalculationLocalService) {
		_commerceTaxCalculationLocalService = commerceTaxCalculationLocalService;
	}

	private CommerceTaxCalculationLocalService _commerceTaxCalculationLocalService;
}