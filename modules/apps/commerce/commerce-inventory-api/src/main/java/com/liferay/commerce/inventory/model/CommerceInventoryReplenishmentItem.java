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
 * The extended model interface for the CommerceInventoryReplenishmentItem service. Represents a row in the &quot;CIReplenishmentItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemImpl"
)
@ProviderType
public interface CommerceInventoryReplenishmentItem
	extends CommerceInventoryReplenishmentItemModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryReplenishmentItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceInventoryReplenishmentItem, Long>
		COMMERCE_INVENTORY_REPLENISHMENT_ITEM_ID_ACCESSOR =
			new Accessor<CommerceInventoryReplenishmentItem, Long>() {

				@Override
				public Long get(
					CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem) {

					return commerceInventoryReplenishmentItem.
						getCommerceInventoryReplenishmentItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceInventoryReplenishmentItem>
					getTypeClass() {

					return CommerceInventoryReplenishmentItem.class;
				}

			};

	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException;

}