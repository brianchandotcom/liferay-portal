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
import com.liferay.commerce.model.impl.CommerceOrderItemImpl;
import com.liferay.commerce.service.persistence.CommerceOrderItemFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemFinderImpl
	extends CommerceOrderItemFinderBaseImpl implements CommerceOrderItemFinder {

	public static final String COUNT_BY_G_A_O =
		CommerceOrderItemFinder.class.getName() + ".countByG_A_O";

	public static final String FIND_BY_AVAILABLE_QUANTITY =
		CommerceOrderItemFinder.class.getName() + ".findByAvailableQuantity";

	public static final String FIND_BY_G_A_O =
		CommerceOrderItemFinder.class.getName() + ".findByG_A_O";

	public static final String GET_COMMERCE_ORDER_ITEMS_QUANTITY =
		CommerceOrderItemFinder.class.getName() +
			".getCommerceOrderItemsQuantity";

	public static final String SUM_VALUE = "SUM_VALUE";

	@Override
	public int countByG_A_O(
		long groupId, long commerceAccountId, int[] orderStatuses) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_A_O);

			sql = replaceOrderStatus(sql, orderStatuses);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceAccountId);
			queryPos.add(groupId);

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
	public List<CommerceOrderItem> findByAvailableQuantity(
		long commerceOrderId) {

		return findByAvailableQuantity(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceOrderItem> findByAvailableQuantity(
		long commerceOrderId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_AVAILABLE_QUANTITY);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"CommerceOrderItem", CommerceOrderItemImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceOrderId);

			return (List<CommerceOrderItem>)QueryUtil.list(
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
	public List<CommerceOrderItem> findByG_A_O(
		long groupId, long commerceAccountId, int[] orderStatuses, int start,
		int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_A_O);

			sql = replaceOrderStatus(sql, orderStatuses);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"CommerceOrderItem", CommerceOrderItemImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceAccountId);
			queryPos.add(groupId);

			return (List<CommerceOrderItem>)QueryUtil.list(
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
	public int getCommerceOrderItemsQuantity(long commerceOrderId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), GET_COMMERCE_ORDER_ITEMS_QUANTITY);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(SUM_VALUE, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceOrderId);

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

	protected String replaceOrderStatus(String sql, int[] orderStatuses) {
		StringBundler sb = new StringBundler(orderStatuses.length);

		for (int i = 0; i < orderStatuses.length; i++) {
			sb.append(orderStatuses[i]);

			if (i != (orderStatuses.length - 1)) {
				sb.append(", ");
			}
		}

		return StringUtil.replace(sql, "[$ORDER_STATUS$]", sb.toString());
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}