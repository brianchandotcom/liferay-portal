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
 * The extended model interface for the ShoppingItem service. Represents a row in the &quot;ShoppingItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemModel
 * @see com.liferay.shopping.model.impl.ShoppingItemImpl
 * @see com.liferay.shopping.model.impl.ShoppingItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.shopping.model.impl.ShoppingItemImpl")
@ProviderType
public interface ShoppingItem extends ShoppingItemModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.shopping.model.impl.ShoppingItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ShoppingItem, Long> ITEM_ID_ACCESSOR = new Accessor<ShoppingItem, Long>() {
			@Override
			public Long get(ShoppingItem shoppingItem) {
				return shoppingItem.getItemId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ShoppingItem> getTypeClass() {
				return ShoppingItem.class;
			}
		};

	public ShoppingCategory getCategory();

	public java.lang.String[] getFieldsQuantitiesArray();

	public java.util.List<ShoppingItemPrice> getItemPrices()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getShoppingItemImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public boolean isInfiniteStock();

	public void setFieldsQuantitiesArray(
		java.lang.String[] fieldsQuantitiesArray);
}