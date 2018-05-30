/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.virtual.order.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceVirtualOrderItem service. Represents a row in the &quot;CommerceVirtualOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemModel
 * @see com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl
 * @see com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl")
@ProviderType
public interface CommerceVirtualOrderItem extends CommerceVirtualOrderItemModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceVirtualOrderItem, Long> COMMERCE_VIRTUAL_ORDER_ITEM_ID_ACCESSOR =
		new Accessor<CommerceVirtualOrderItem, Long>() {
			@Override
			public Long get(CommerceVirtualOrderItem commerceVirtualOrderItem) {
				return commerceVirtualOrderItem.getCommerceVirtualOrderItemId();
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