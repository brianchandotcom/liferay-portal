/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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