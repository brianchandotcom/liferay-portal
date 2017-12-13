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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommercePaymentMethod service. Represents a row in the &quot;CommercePaymentMethod&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodModel
 * @see com.liferay.commerce.model.impl.CommercePaymentMethodImpl
 * @see com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommercePaymentMethodImpl")
@ProviderType
public interface CommercePaymentMethod extends CommercePaymentMethodModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommercePaymentMethodImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePaymentMethod, Long> COMMERCE_PAYMENT_METHOD_ID_ACCESSOR =
		new Accessor<CommercePaymentMethod, Long>() {
			@Override
			public Long get(CommercePaymentMethod commercePaymentMethod) {
				return commercePaymentMethod.getCommercePaymentMethodId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommercePaymentMethod> getTypeClass() {
				return CommercePaymentMethod.class;
			}
		};

	public java.lang.String getImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);
}