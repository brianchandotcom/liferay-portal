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

package com.liferay.commerce.inventory.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceInventoryWarehouse service. Represents a row in the &quot;CIWarehouse&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseImpl"
)
@ProviderType
public interface CommerceInventoryWarehouse
	extends CommerceInventoryWarehouseModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceInventoryWarehouse, Long>
		COMMERCE_INVENTORY_WAREHOUSE_ID_ACCESSOR =
			new Accessor<CommerceInventoryWarehouse, Long>() {

				@Override
				public Long get(
					CommerceInventoryWarehouse commerceInventoryWarehouse) {

					return commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceInventoryWarehouse> getTypeClass() {
					return CommerceInventoryWarehouse.class;
				}

			};

	public java.util.List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItems();

	public boolean isGeolocated();

}