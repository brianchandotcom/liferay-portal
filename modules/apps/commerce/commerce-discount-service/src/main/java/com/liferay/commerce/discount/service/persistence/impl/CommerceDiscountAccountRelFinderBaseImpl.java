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

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelPersistence;
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
public class CommerceDiscountAccountRelFinderBaseImpl
	extends BasePersistenceImpl<CommerceDiscountAccountRel> {

	public CommerceDiscountAccountRelFinderBaseImpl() {
		setModelClass(CommerceDiscountAccountRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceDiscountAccountRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce discount account rel persistence.
	 *
	 * @return the commerce discount account rel persistence
	 */
	public CommerceDiscountAccountRelPersistence
		getCommerceDiscountAccountRelPersistence() {

		return commerceDiscountAccountRelPersistence;
	}

	/**
	 * Sets the commerce discount account rel persistence.
	 *
	 * @param commerceDiscountAccountRelPersistence the commerce discount account rel persistence
	 */
	public void setCommerceDiscountAccountRelPersistence(
		CommerceDiscountAccountRelPersistence
			commerceDiscountAccountRelPersistence) {

		this.commerceDiscountAccountRelPersistence =
			commerceDiscountAccountRelPersistence;
	}

	@BeanReference(type = CommerceDiscountAccountRelPersistence.class)
	protected CommerceDiscountAccountRelPersistence
		commerceDiscountAccountRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountAccountRelFinderBaseImpl.class);

}