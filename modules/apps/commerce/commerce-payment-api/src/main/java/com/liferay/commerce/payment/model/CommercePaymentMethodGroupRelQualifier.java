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

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePaymentMethodGroupRelQualifier service. Represents a row in the &quot;CPMethodGroupRelQualifier&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelQualifierImpl"
)
@ProviderType
public interface CommercePaymentMethodGroupRelQualifier
	extends CommercePaymentMethodGroupRelQualifierModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelQualifierImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePaymentMethodGroupRelQualifier, Long>
		COMMERCE_PAYMENT_METHOD_GROUP_REL_QUALIFIER_ID_ACCESSOR =
			new Accessor<CommercePaymentMethodGroupRelQualifier, Long>() {

				@Override
				public Long get(
					CommercePaymentMethodGroupRelQualifier
						commercePaymentMethodGroupRelQualifier) {

					return commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelQualifierId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePaymentMethodGroupRelQualifier>
					getTypeClass() {

					return CommercePaymentMethodGroupRelQualifier.class;
				}

			};

}