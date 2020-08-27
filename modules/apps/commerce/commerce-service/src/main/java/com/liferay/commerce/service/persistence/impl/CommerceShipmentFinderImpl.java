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
import com.liferay.commerce.model.impl.CommerceShipmentImpl;
import com.liferay.commerce.service.persistence.CommerceShipmentFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alec Sloan
 */
public class CommerceShipmentFinderImpl
	extends CommerceShipmentFinderBaseImpl implements CommerceShipmentFinder {

	public static final String COUNT_BY_COMMERCE_ORDER_ID =
		CommerceShipmentFinder.class.getName() + ".countByCommerceOrderId";

	public static final String FIND_BY_COMMERCE_ORDER_ID =
		CommerceShipmentFinder.class.getName() + ".findByCommerceOrderId";

	public static final String
		FIND_COMMERCE_SHIPMENT_STATUSES_BY_COMMERCE_ORDER_ID =
			CommerceShipmentFinder.class.getName() +
				".findCommerceShipmentStatusesByCommerceOrderId";

	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_COMMERCE_ORDER_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceOrderId);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
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

	@Override
	public List<CommerceShipment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_COMMERCE_ORDER_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("CommerceShipment", CommerceShipmentImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceOrderId);

			return (List<CommerceShipment>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int[] findCommerceShipmentStatusesByCommerceOrderId(
		long commerceOrderId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(),
				FIND_COMMERCE_SHIPMENT_STATUSES_BY_COMMERCE_ORDER_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceOrderId);

			List<Integer> commerceShipmentStatuses =
				(List<Integer>)QueryUtil.list(
					sqlQuery, getDialect(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			return ArrayUtil.toIntArray(commerceShipmentStatuses);
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