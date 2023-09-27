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
 * The extended model interface for the CommerceInventoryWarehouseItem service. Represents a row in the &quot;CIWarehouseItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemImpl"
)
@ProviderType
public interface CommerceInventoryWarehouseItem
	extends CommerceInventoryWarehouseItemModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceInventoryWarehouseItem, Long>
		COMMERCE_INVENTORY_WAREHOUSE_ITEM_ID_ACCESSOR =
			new Accessor<CommerceInventoryWarehouseItem, Long>() {

				@Override
				public Long get(
					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem) {

					return commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceInventoryWarehouseItem> getTypeClass() {
					return CommerceInventoryWarehouseItem.class;
				}

			};

	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException;

}