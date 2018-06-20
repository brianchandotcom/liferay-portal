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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.persistence.CommerceShipmentItemPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShipmentItemFinderBaseImpl extends BasePersistenceImpl<CommerceShipmentItem> {
	public CommerceShipmentItemFinderBaseImpl() {
		setModelClass(CommerceShipmentItem.class);
	}

	/**
	 * Returns the commerce shipment item persistence.
	 *
	 * @return the commerce shipment item persistence
	 */
	public CommerceShipmentItemPersistence getCommerceShipmentItemPersistence() {
		return commerceShipmentItemPersistence;
	}

	/**
	 * Sets the commerce shipment item persistence.
	 *
	 * @param commerceShipmentItemPersistence the commerce shipment item persistence
	 */
	public void setCommerceShipmentItemPersistence(
		CommerceShipmentItemPersistence commerceShipmentItemPersistence) {
		this.commerceShipmentItemPersistence = commerceShipmentItemPersistence;
	}

	@BeanReference(type = CommerceShipmentItemPersistence.class)
	protected CommerceShipmentItemPersistence commerceShipmentItemPersistence;
}