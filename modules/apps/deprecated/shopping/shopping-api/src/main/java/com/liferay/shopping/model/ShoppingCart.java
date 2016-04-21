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

package com.liferay.shopping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ShoppingCart service. Represents a row in the &quot;ShoppingCart&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartModel
 * @see com.liferay.shopping.model.impl.ShoppingCartImpl
 * @see com.liferay.shopping.model.impl.ShoppingCartModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.shopping.model.impl.ShoppingCartImpl")
@ProviderType
public interface ShoppingCart extends ShoppingCartModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.shopping.model.impl.ShoppingCartImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ShoppingCart, Long> CART_ID_ACCESSOR = new Accessor<ShoppingCart, Long>() {
			@Override
			public Long get(ShoppingCart shoppingCart) {
				return shoppingCart.getCartId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ShoppingCart> getTypeClass() {
				return ShoppingCart.class;
			}
		};

	public void addItemId(long itemId, java.lang.String fields);

	public ShoppingCoupon getCoupon()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<ShoppingCartItem, java.lang.Integer> getItems();

	public int getItemsSize();
}