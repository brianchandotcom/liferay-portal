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

	public java.math.BigDecimal round(java.math.BigDecimal value);
}