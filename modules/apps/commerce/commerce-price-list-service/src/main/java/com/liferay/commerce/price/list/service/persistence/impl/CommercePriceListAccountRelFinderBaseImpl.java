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

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListAccountRelPersistence;
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
public class CommercePriceListAccountRelFinderBaseImpl
	extends BasePersistenceImpl<CommercePriceListAccountRel> {

	public CommercePriceListAccountRelFinderBaseImpl() {
		setModelClass(CommercePriceListAccountRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommercePriceListAccountRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce price list account rel persistence.
	 *
	 * @return the commerce price list account rel persistence
	 */
	public CommercePriceListAccountRelPersistence
		getCommercePriceListAccountRelPersistence() {

		return commercePriceListAccountRelPersistence;
	}

	/**
	 * Sets the commerce price list account rel persistence.
	 *
	 * @param commercePriceListAccountRelPersistence the commerce price list account rel persistence
	 */
	public void setCommercePriceListAccountRelPersistence(
		CommercePriceListAccountRelPersistence
			commercePriceListAccountRelPersistence) {

		this.commercePriceListAccountRelPersistence =
			commercePriceListAccountRelPersistence;
	}

	@BeanReference(type = CommercePriceListAccountRelPersistence.class)
	protected CommercePriceListAccountRelPersistence
		commercePriceListAccountRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListAccountRelFinderBaseImpl.class);

}