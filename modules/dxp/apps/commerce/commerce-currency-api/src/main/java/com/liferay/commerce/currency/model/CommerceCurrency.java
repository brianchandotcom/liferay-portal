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

package com.liferay.commerce.currency.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceCurrency service. Represents a row in the &quot;CommerceCurrency&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyModel
 * @see com.liferay.commerce.currency.model.impl.CommerceCurrencyImpl
 * @see com.liferay.commerce.currency.model.impl.CommerceCurrencyModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.currency.model.impl.CommerceCurrencyImpl")
@ProviderType
public interface CommerceCurrency extends CommerceCurrencyModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.currency.model.impl.CommerceCurrencyImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCurrency, Long> COMMERCE_CURRENCY_ID_ACCESSOR =
		new Accessor<CommerceCurrency, Long>() {
			@Override
			public Long get(CommerceCurrency commerceCurrency) {
				return commerceCurrency.getCommerceCurrencyId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCurrency> getTypeClass() {
				return CommerceCurrency.class;
			}
		};

	public CommerceMoney getZero();
}