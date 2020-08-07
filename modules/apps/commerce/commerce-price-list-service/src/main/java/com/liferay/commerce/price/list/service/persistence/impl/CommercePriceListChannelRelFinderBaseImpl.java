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

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListChannelRelPersistence;
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
public class CommercePriceListChannelRelFinderBaseImpl
	extends BasePersistenceImpl<CommercePriceListChannelRel> {

	public CommercePriceListChannelRelFinderBaseImpl() {
		setModelClass(CommercePriceListChannelRel.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommercePriceListChannelRelPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce price list channel rel persistence.
	 *
	 * @return the commerce price list channel rel persistence
	 */
	public CommercePriceListChannelRelPersistence
		getCommercePriceListChannelRelPersistence() {

		return commercePriceListChannelRelPersistence;
	}

	/**
	 * Sets the commerce price list channel rel persistence.
	 *
	 * @param commercePriceListChannelRelPersistence the commerce price list channel rel persistence
	 */
	public void setCommercePriceListChannelRelPersistence(
		CommercePriceListChannelRelPersistence
			commercePriceListChannelRelPersistence) {

		this.commercePriceListChannelRelPersistence =
			commercePriceListChannelRelPersistence;
	}

	@BeanReference(type = CommercePriceListChannelRelPersistence.class)
	protected CommercePriceListChannelRelPersistence
		commercePriceListChannelRelPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListChannelRelFinderBaseImpl.class);

}