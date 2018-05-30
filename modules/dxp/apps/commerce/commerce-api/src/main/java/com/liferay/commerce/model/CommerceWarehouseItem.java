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