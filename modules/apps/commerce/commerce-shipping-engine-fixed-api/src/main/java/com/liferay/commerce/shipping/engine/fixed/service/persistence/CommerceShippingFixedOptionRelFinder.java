/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionRelFinder {

	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel fetchByC_C_C_Z_W_First(
			long commerceShippingFixedOptionId, long countryId, long regionId,
			String zip, double weight);

	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel> findByC_C_C_Z_W(
				long commerceShippingFixedOptionId, long countryId,
				long regionId, String zip, double weight);

	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel> findByC_C_C_Z_W(
				long commerceShippingFixedOptionId, long countryId,
				long regionId, String zip, double weight, int start, int end);

}