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

import com.liferay.commerce.service.persistence.CommerceShipmentItemFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemFinderImpl
	extends CommerceShipmentItemFinderBaseImpl
	implements CommerceShipmentItemFinder {

	public static final String GET_COMMERCE_SHIPMENT_ORDER_ITEMS_QUANTITY =
		CommerceShipmentItemFinder.class.getName() +
			".getCommerceShipmentOrderItemsQuantity";

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
		long commerceShipmentId, long commerceOrderItemId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), GET_COMMERCE_SHIPMENT_ORDER_ITEMS_QUANTITY);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("SUM_VALUE", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceShipmentId);
			queryPos.add(commerceOrderItemId);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long sum = iterator.next();

				if (sum != null) {
					return sum.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}