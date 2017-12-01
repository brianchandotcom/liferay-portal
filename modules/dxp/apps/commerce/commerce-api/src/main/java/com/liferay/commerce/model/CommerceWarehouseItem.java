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
 * The extended model interface for the CommerceWarehouseItem service. Represents a row in the &quot;CommerceWarehouseItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemModel
 * @see com.liferay.commerce.model.impl.CommerceWarehouseItemImpl
 * @see com.liferay.commerce.model.impl.CommerceWarehouseItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceWarehouseItemImpl")
@ProviderType
public interface CommerceWarehouseItem extends CommerceWarehouseItemModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceWarehouseItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWarehouseItem, Long> COMMERCE_WAREHOUSE_ITEM_ID_ACCESSOR =
		new Accessor<CommerceWarehouseItem, Long>() {
			@Override
			public Long get(CommerceWarehouseItem commerceWarehouseItem) {
				return commerceWarehouseItem.getCommerceWarehouseItemId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceWarehouseItem> getTypeClass() {
				return CommerceWarehouseItem.class;
			}
		};

	public CommerceWarehouse getCommerceWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException;
}