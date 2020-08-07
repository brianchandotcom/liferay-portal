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

import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountCommerceAccountGroupRelPersistence;
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
public class CommerceDiscountCommerceAccountGroupRelFinderBaseImpl
	extends BasePersistenceImpl<CommerceDiscountCommerceAccountGroupRel> {

	public CommerceDiscountCommerceAccountGroupRelFinderBaseImpl() {
		setModelClass(CommerceDiscountCommerceAccountGroupRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceDiscountCommerceAccountGroupRelId",
			"CDiscountCAccountGroupRelId");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceDiscountCommerceAccountGroupRelPersistence().
			getBadColumnNames();
	}

	/**
	 * Returns the commerce discount commerce account group rel persistence.
	 *
	 * @return the commerce discount commerce account group rel persistence
	 */
	public CommerceDiscountCommerceAccountGroupRelPersistence
		getCommerceDiscountCommerceAccountGroupRelPersistence() {

		return commerceDiscountCommerceAccountGroupRelPersistence;
	}

	/**
	 * Sets the commerce discount commerce account group rel persistence.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelPersistence the commerce discount commerce account group rel persistence
	 */
	public void setCommerceDiscountCommerceAccountGroupRelPersistence(
		CommerceDiscountCommerceAccountGroupRelPersistence
			commerceDiscountCommerceAccountGroupRelPersistence) {

		this.commerceDiscountCommerceAccountGroupRelPersistence =
			commerceDiscountCommerceAccountGroupRelPersistence;
	}

	@BeanReference(
		type = CommerceDiscountCommerceAccountGroupRelPersistence.class
	)
	protected CommerceDiscountCommerceAccountGroupRelPersistence
		commerceDiscountCommerceAccountGroupRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountCommerceAccountGroupRelFinderBaseImpl.class);

}