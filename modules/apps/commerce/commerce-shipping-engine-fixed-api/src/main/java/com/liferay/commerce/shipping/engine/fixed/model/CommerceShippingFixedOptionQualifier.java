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

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceShippingFixedOptionQualifier service. Represents a row in the &quot;CSFixedOptionQualifier&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionQualifierModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionQualifierImpl"
)
@ProviderType
public interface CommerceShippingFixedOptionQualifier
	extends CommerceShippingFixedOptionQualifierModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionQualifierImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShippingFixedOptionQualifier, Long>
		COMMERCE_SHIPPING_FIXED_OPTION_QUALIFIER_ID_ACCESSOR =
			new Accessor<CommerceShippingFixedOptionQualifier, Long>() {

				@Override
				public Long get(
					CommerceShippingFixedOptionQualifier
						commerceShippingFixedOptionQualifier) {

					return commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionQualifierId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceShippingFixedOptionQualifier>
					getTypeClass() {

					return CommerceShippingFixedOptionQualifier.class;
				}

			};

}