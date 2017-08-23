/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceWarehouseItemFinderBaseImpl extends BasePersistenceImpl<CommerceWarehouseItem> {
	public CommerceWarehouseItemFinderBaseImpl() {
		setModelClass(CommerceWarehouseItem.class);
	}

	/**
	 * Returns the commerce warehouse item persistence.
	 *
	 * @return the commerce warehouse item persistence
	 */
	public CommerceWarehouseItemPersistence getCommerceWarehouseItemPersistence() {
		return commerceWarehouseItemPersistence;
	}

	/**
	 * Sets the commerce warehouse item persistence.
	 *
	 * @param commerceWarehouseItemPersistence the commerce warehouse item persistence
	 */
	public void setCommerceWarehouseItemPersistence(
		CommerceWarehouseItemPersistence commerceWarehouseItemPersistence) {
		this.commerceWarehouseItemPersistence = commerceWarehouseItemPersistence;
	}

	@BeanReference(type = CommerceWarehouseItemPersistence.class)
	protected CommerceWarehouseItemPersistence commerceWarehouseItemPersistence;
}