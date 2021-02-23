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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceTaxFixedRateAddressRelFinder {

	public
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel fetchByC_C_C_R_Z_First(
				long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
				long regionId, String zip);

	public
		com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel fetchByC_C_R_Z_First(
				long commerceTaxMethodId, long countryId, long regionId,
				String zip);

	public java.util.List
		<com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel> findByC_C_R_Z(
				long commerceTaxMethodId, long countryId, long regionId,
				String zip);

	public java.util.List
		<com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel> findByC_C_R_Z(
				long commerceTaxMethodId, long countryId, long regionId,
				String zip, int start, int end);

	public java.util.List
		<com.liferay.commerce.tax.engine.fixed.model.
			CommerceTaxFixedRateAddressRel> findByC_C_C_R_Z(
				long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
				long regionId, String zip, int start, int end);

}