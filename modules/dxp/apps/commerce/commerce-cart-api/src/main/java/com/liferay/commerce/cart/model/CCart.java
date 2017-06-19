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

package com.liferay.commerce.cart.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CCart service. Represents a row in the &quot;CCart&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CCartModel
 * @see com.liferay.commerce.cart.model.impl.CCartImpl
 * @see com.liferay.commerce.cart.model.impl.CCartModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.cart.model.impl.CCartImpl")
@ProviderType
public interface CCart extends CCartModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.cart.model.impl.CCartImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CCart, Long> C_CART_ID_ACCESSOR = new Accessor<CCart, Long>() {
			@Override
			public Long get(CCart cCart) {
				return cCart.getCCartId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CCart> getTypeClass() {
				return CCart.class;
			}
		};
}