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
 * The extended model interface for the CommerceOrderItem service. Represents a row in the &quot;CommerceOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemModel
 * @see com.liferay.commerce.model.impl.CommerceOrderItemImpl
 * @see com.liferay.commerce.model.impl.CommerceOrderItemModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceOrderItemImpl")
@ProviderType
public interface CommerceOrderItem extends CommerceOrderItemModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceOrderItemImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrderItem, Long> COMMERCE_ORDER_ITEM_ID_ACCESSOR =
		new Accessor<CommerceOrderItem, Long>() {
			@Override
			public Long get(CommerceOrderItem commerceOrderItem) {
				return commerceOrderItem.getCommerceOrderItemId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceOrderItem> getTypeClass() {
				return CommerceOrderItem.class;
			}
		};

	public CommerceOrder getCommerceOrder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException;
}