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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.persistence.CommerceOrderItemPersistence;
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
public class CommerceOrderItemFinderBaseImpl
	extends BasePersistenceImpl<CommerceOrderItem> {

	public CommerceOrderItemFinderBaseImpl() {
		setModelClass(CommerceOrderItem.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");
		dbColumnNames.put(
			"discountPercentageLevel1WithTaxAmount",
			"discountPctLevel1WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel2WithTaxAmount",
			"discountPctLevel2WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel3WithTaxAmount",
			"discountPctLevel3WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel4WithTaxAmount",
			"discountPctLevel4WithTaxAmount");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceOrderItemPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce order item persistence.
	 *
	 * @return the commerce order item persistence
	 */
	public CommerceOrderItemPersistence getCommerceOrderItemPersistence() {
		return commerceOrderItemPersistence;
	}

	/**
	 * Sets the commerce order item persistence.
	 *
	 * @param commerceOrderItemPersistence the commerce order item persistence
	 */
	public void setCommerceOrderItemPersistence(
		CommerceOrderItemPersistence commerceOrderItemPersistence) {

		this.commerceOrderItemPersistence = commerceOrderItemPersistence;
	}

	@BeanReference(type = CommerceOrderItemPersistence.class)
	protected CommerceOrderItemPersistence commerceOrderItemPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemFinderBaseImpl.class);

}