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

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountFinderBaseImpl
	extends BasePersistenceImpl<CommerceDiscount> {

	public CommerceDiscountFinderBaseImpl() {
		setModelClass(CommerceDiscount.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("level", "levelType");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceDiscountPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce discount persistence.
	 *
	 * @return the commerce discount persistence
	 */
	public CommerceDiscountPersistence getCommerceDiscountPersistence() {
		return commerceDiscountPersistence;
	}

	/**
	 * Sets the commerce discount persistence.
	 *
	 * @param commerceDiscountPersistence the commerce discount persistence
	 */
	public void setCommerceDiscountPersistence(
		CommerceDiscountPersistence commerceDiscountPersistence) {

		this.commerceDiscountPersistence = commerceDiscountPersistence;
	}

	@BeanReference(type = CommerceDiscountPersistence.class)
	protected CommerceDiscountPersistence commerceDiscountPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountFinderBaseImpl.class);

}