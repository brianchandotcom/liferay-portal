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

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.impl.CommerceShipmentItemImpl;
import com.liferay.commerce.service.persistence.CommerceShipmentItemFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemFinderImpl
	extends CommerceShipmentItemFinderBaseImpl
	implements CommerceShipmentItemFinder {

	public static final String FIND_BY_COMMERCE_ORDER_ITEM_ID =
		CommerceShipmentItemFinder.class.getName() +
			".findByCommerceOrderItemId";

	@Override
	public List<CommerceShipmentItem> findByCommerceOrderItemId(
		long commerceOrderItemId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_COMMERCE_ORDER_ITEM_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceShipmentItem", CommerceShipmentItemImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceOrderItemId);

			return (List<CommerceShipmentItem>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}