/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceCurrency service. Represents a row in the &quot;CommerceCurrency&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.currency.model.impl.CommerceCurrencyImpl"
)
@ProviderType
public interface CommerceCurrency
	extends CommerceCurrencyModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.currency.model.impl.CommerceCurrencyImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCurrency, Long>
		COMMERCE_CURRENCY_ID_ACCESSOR = new Accessor<CommerceCurrency, Long>() {

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