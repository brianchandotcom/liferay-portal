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

package com.liferay.commerce.tax.engine.fixed.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceTaxFixedRateAddressRelFinder {
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchByC_C_C_Z_First(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip);

	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> findByC_C_C_Z(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip);

	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> findByC_C_C_Z(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, int start, int end);
}