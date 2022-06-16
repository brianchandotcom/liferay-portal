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

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShipmentFinderBaseImpl
	extends BasePersistenceImpl<CommerceShipment> {

	public CommerceShipmentFinderBaseImpl() {
		setModelClass(CommerceShipment.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceShipmentPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce shipment persistence.
	 *
	 * @return the commerce shipment persistence
	 */
	public CommerceShipmentPersistence getCommerceShipmentPersistence() {
		return commerceShipmentPersistence;
	}

	/**
	 * Sets the commerce shipment persistence.
	 *
	 * @param commerceShipmentPersistence the commerce shipment persistence
	 */
	public void setCommerceShipmentPersistence(
		CommerceShipmentPersistence commerceShipmentPersistence) {

		this.commerceShipmentPersistence = commerceShipmentPersistence;
	}

	@BeanReference(type = CommerceShipmentPersistence.class)
	protected CommerceShipmentPersistence commerceShipmentPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentFinderBaseImpl.class);

}