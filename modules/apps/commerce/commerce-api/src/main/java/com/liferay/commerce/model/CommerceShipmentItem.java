/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceShipmentItem service. Represents a row in the &quot;CommerceShipmentItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceShipmentItemImpl"
)
@ProviderType
public interface CommerceShipmentItem
	extends CommerceShipmentItemModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceShipmentItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShipmentItem, Long>
		COMMERCE_SHIPMENT_ITEM_ID_ACCESSOR =
			new Accessor<CommerceShipmentItem, Long>() {

				@Override
				public Long get(CommerceShipmentItem commerceShipmentItem) {
					return commerceShipmentItem.getCommerceShipmentItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceShipmentItem> getTypeClass() {
					return CommerceShipmentItem.class;
				}

			};

	public CommerceOrderItem fetchCommerceOrderItem();

	public CommerceShipment getCommerceShipment()
		throws com.liferay.portal.kernel.exception.PortalException;

}