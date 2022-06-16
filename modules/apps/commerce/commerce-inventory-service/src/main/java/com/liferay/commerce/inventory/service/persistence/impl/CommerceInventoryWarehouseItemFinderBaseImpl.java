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

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseItemPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehouseItemFinderBaseImpl
	extends BasePersistenceImpl<CommerceInventoryWarehouseItem> {

	public CommerceInventoryWarehouseItemFinderBaseImpl() {
		setModelClass(CommerceInventoryWarehouseItem.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commerceInventoryWarehouseItemId", "CIWarehouseItemId");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceInventoryWarehouseItemPersistence().
			getBadColumnNames();
	}

	/**
	 * Returns the commerce inventory warehouse item persistence.
	 *
	 * @return the commerce inventory warehouse item persistence
	 */
	public CommerceInventoryWarehouseItemPersistence
		getCommerceInventoryWarehouseItemPersistence() {

		return commerceInventoryWarehouseItemPersistence;
	}

	/**
	 * Sets the commerce inventory warehouse item persistence.
	 *
	 * @param commerceInventoryWarehouseItemPersistence the commerce inventory warehouse item persistence
	 */
	public void setCommerceInventoryWarehouseItemPersistence(
		CommerceInventoryWarehouseItemPersistence
			commerceInventoryWarehouseItemPersistence) {

		this.commerceInventoryWarehouseItemPersistence =
			commerceInventoryWarehouseItemPersistence;
	}

	@BeanReference(type = CommerceInventoryWarehouseItemPersistence.class)
	protected CommerceInventoryWarehouseItemPersistence
		commerceInventoryWarehouseItemPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseItemFinderBaseImpl.class);

}