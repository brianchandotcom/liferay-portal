/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceTaxFixedRate service. Represents a row in the &quot;CommerceTaxFixedRate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl"
)
@ProviderType
public interface CommerceTaxFixedRate
	extends CommerceTaxFixedRateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxFixedRate, Long>
		COMMERCE_TAX_FIXED_RATE_ID_ACCESSOR =
			new Accessor<CommerceTaxFixedRate, Long>() {

				@Override
				public Long get(CommerceTaxFixedRate commerceTaxFixedRate) {
					return commerceTaxFixedRate.getCommerceTaxFixedRateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceTaxFixedRate> getTypeClass() {
					return CommerceTaxFixedRate.class;
				}

			};

	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

}