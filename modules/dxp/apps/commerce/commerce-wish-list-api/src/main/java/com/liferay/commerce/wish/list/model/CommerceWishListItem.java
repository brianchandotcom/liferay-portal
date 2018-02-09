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
 * The extended model interface for the CommerceWishListItem service. Represents a row in the &quot;CommerceWishListItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemModel
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListItemImpl
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.wish.list.model.impl.CommerceWishListItemImpl")
@ProviderType
public interface CommerceWishListItem extends CommerceWishListItemModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWishListItem, Long> COMMERCE_WISH_LIST_ITEM_ID_ACCESSOR =
		new Accessor<CommerceWishListItem, Long>() {
			@Override
			public Long get(CommerceWishListItem commerceWishListItem) {
				return commerceWishListItem.getCommerceWishListItemId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceWishListItem> getTypeClass() {
				return CommerceWishListItem.class;
			}
		};

	public com.liferay.commerce.product.model.CPInstance fetchCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceWishList getCommerceWishList()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isIgnoreSKUCombinations()
		throws com.liferay.portal.kernel.exception.PortalException;
}