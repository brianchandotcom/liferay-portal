/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceVirtualOrderItem service. Represents a row in the &quot;CommerceVirtualOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl"
)
@ProviderType
public interface CommerceVirtualOrderItem
	extends CommerceVirtualOrderItemModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceVirtualOrderItem, Long>
		COMMERCE_VIRTUAL_ORDER_ITEM_ID_ACCESSOR =
			new Accessor<CommerceVirtualOrderItem, Long>() {

				@Override
				public Long get(
					CommerceVirtualOrderItem commerceVirtualOrderItem) {

					return commerceVirtualOrderItem.
						getCommerceVirtualOrderItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceVirtualOrderItem> getTypeClass() {
					return CommerceVirtualOrderItem.class;
				}

			};

	public com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

}