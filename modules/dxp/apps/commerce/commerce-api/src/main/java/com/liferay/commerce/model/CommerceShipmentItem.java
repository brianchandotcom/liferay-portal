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
 * The extended model interface for the CommerceShipmentItem service. Represents a row in the &quot;CommerceShipmentItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemModel
 * @see com.liferay.commerce.model.impl.CommerceShipmentItemImpl
 * @see com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceShipmentItemImpl")
@ProviderType
public interface CommerceShipmentItem extends CommerceShipmentItemModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceShipmentItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShipmentItem, Long> COMMERCE_SHIPMENT_ITEM_ID_ACCESSOR =
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