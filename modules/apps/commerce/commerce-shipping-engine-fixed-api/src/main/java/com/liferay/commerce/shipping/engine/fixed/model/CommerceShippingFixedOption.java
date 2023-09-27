/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceShippingFixedOption service. Represents a row in the &quot;CommerceShippingFixedOption&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl"
)
@ProviderType
public interface CommerceShippingFixedOption
	extends CommerceShippingFixedOptionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShippingFixedOption, Long>
		COMMERCE_SHIPPING_FIXED_OPTION_ID_ACCESSOR =
			new Accessor<CommerceShippingFixedOption, Long>() {

				@Override
				public Long get(
					CommerceShippingFixedOption commerceShippingFixedOption) {

					return commerceShippingFixedOption.
						getCommerceShippingFixedOptionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceShippingFixedOption> getTypeClass() {
					return CommerceShippingFixedOption.class;
				}

			};

}