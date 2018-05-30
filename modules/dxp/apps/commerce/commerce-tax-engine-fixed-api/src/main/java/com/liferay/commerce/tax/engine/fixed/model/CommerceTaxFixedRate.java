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

package com.liferay.commerce.tax.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceTaxFixedRate service. Represents a row in the &quot;CommerceTaxFixedRate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateModel
 * @see com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl
 * @see com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl")
@ProviderType
public interface CommerceTaxFixedRate extends CommerceTaxFixedRateModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxFixedRate, Long> COMMERCE_TAX_FIXED_RATE_ID_ACCESSOR =
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