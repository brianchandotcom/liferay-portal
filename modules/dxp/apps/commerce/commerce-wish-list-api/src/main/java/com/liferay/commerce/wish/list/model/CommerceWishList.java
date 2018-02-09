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

package com.liferay.commerce.wish.list.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceWishList service. Represents a row in the &quot;CommerceWishList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListModel
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl")
@ProviderType
public interface CommerceWishList extends CommerceWishListModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWishList, Long> COMMERCE_WISH_LIST_ID_ACCESSOR =
		new Accessor<CommerceWishList, Long>() {
			@Override
			public Long get(CommerceWishList commerceWishList) {
				return commerceWishList.getCommerceWishListId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceWishList> getTypeClass() {
				return CommerceWishList.class;
			}
		};
}