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

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelPersistence;
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
public class CommercePriceListCommerceAccountGroupRelFinderBaseImpl
	extends BasePersistenceImpl<CommercePriceListCommerceAccountGroupRel> {

	public CommercePriceListCommerceAccountGroupRelFinderBaseImpl() {
		setModelClass(CommercePriceListCommerceAccountGroupRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commercePriceListCommerceAccountGroupRelId",
			"CPLCommerceAccountGroupRelId");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommercePriceListCommerceAccountGroupRelPersistence().
			getBadColumnNames();
	}

	/**
	 * Returns the commerce price list commerce account group rel persistence.
	 *
	 * @return the commerce price list commerce account group rel persistence
	 */
	public CommercePriceListCommerceAccountGroupRelPersistence
		getCommercePriceListCommerceAccountGroupRelPersistence() {

		return commercePriceListCommerceAccountGroupRelPersistence;
	}

	/**
	 * Sets the commerce price list commerce account group rel persistence.
	 *
	 * @param commercePriceListCommerceAccountGroupRelPersistence the commerce price list commerce account group rel persistence
	 */
	public void setCommercePriceListCommerceAccountGroupRelPersistence(
		CommercePriceListCommerceAccountGroupRelPersistence
			commercePriceListCommerceAccountGroupRelPersistence) {

		this.commercePriceListCommerceAccountGroupRelPersistence =
			commercePriceListCommerceAccountGroupRelPersistence;
	}

	@BeanReference(
		type = CommercePriceListCommerceAccountGroupRelPersistence.class
	)
	protected CommercePriceListCommerceAccountGroupRelPersistence
		commercePriceListCommerceAccountGroupRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListCommerceAccountGroupRelFinderBaseImpl.class);

}