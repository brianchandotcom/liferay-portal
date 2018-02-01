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
 * The extended model interface for the CommerceCart service. Represents a row in the &quot;CommerceCart&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartModel
 * @see com.liferay.commerce.model.impl.CommerceCartImpl
 * @see com.liferay.commerce.model.impl.CommerceCartModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceCartImpl")
@ProviderType
public interface CommerceCart extends CommerceCartModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceCartImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCart, Long> COMMERCE_CART_ID_ACCESSOR = new Accessor<CommerceCart, Long>() {
			@Override
			public Long get(CommerceCart commerceCart) {
				return commerceCart.getCommerceCartId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCart> getTypeClass() {
				return CommerceCart.class;
			}
		};

	public CommerceAddress getBillingAddress()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getClassName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getClassPK()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CommerceCartItem> getCommerceCartItems();

	public CommerceAddress getShippingAddress()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isB2B()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isGuestCart()
		throws com.liferay.portal.kernel.exception.PortalException;
}