/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceTaxMethod service. Represents a row in the &quot;CommerceTaxMethod&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceTaxMethodModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.tax.model.impl.CommerceTaxMethodImpl"
)
@ProviderType
public interface CommerceTaxMethod
	extends CommerceTaxMethodModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.tax.model.impl.CommerceTaxMethodImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxMethod, Long>
		COMMERCE_TAX_METHOD_ID_ACCESSOR =
			new Accessor<CommerceTaxMethod, Long>() {

				@Override
				public Long get(CommerceTaxMethod commerceTaxMethod) {
					return commerceTaxMethod.getCommerceTaxMethodId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceTaxMethod> getTypeClass() {
					return CommerceTaxMethod.class;
				}

			};

}