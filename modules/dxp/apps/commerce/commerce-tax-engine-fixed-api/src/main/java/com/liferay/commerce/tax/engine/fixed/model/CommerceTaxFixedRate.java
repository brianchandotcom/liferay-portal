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

	public com.liferay.commerce.model.CommerceTaxCategory getCommerceTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException;
}