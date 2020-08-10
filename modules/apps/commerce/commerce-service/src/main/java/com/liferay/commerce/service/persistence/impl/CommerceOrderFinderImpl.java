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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.impl.CommerceOrderImpl;
import com.liferay.commerce.service.persistence.CommerceOrderFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderFinderImpl
	extends CommerceOrderFinderBaseImpl implements CommerceOrderFinder {

	public static final String COUNT_BY_G_U_C_O =
		CommerceOrderFinder.class.getName() + ".countByG_U_C_O";

	public static final String FIND_BY_G_O =
		CommerceOrderFinder.class.getName() + ".findByG_O";

	public static final String FIND_BY_G_U_C_O =
		CommerceOrderFinder.class.getName() + ".findByG_U_C_O";

	public static final String FIND_BY_G_U_C_O_S =
		CommerceOrderFinder.class.getName() + ".findByG_U_C_O_S";

	public static final String
		GET_SHIPPED_COMMERCE_ORDERS_BY_COMMERCE_SHIPMENT_ID =
			CommerceOrderFinder.class.getName() +
				".getShippedCommerceOrdersByCommerceShipmentId";

	@Override
	public int countByG_U_C_O(
		long userId, QueryDefinition<CommerceOrder> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U_C_O);

			sql = StringUtil.replace(
				sql, "[$USER_ID$]", String.valueOf(userId));

			long commerceAccountId = (Long)queryDefinition.getAttribute(
				"commerceAccountId");

			if (commerceAccountId > 0) {
				sql = StringUtil.replace(
					sql, "[$ACCOUNT_ID$]",
					_getAccountIdClause(commerceAccountId));
			}
			else {
				sql = StringUtil.replace(
					sql, "[$ACCOUNT_ID$]", StringPool.BLANK);
			}

			Integer orderStatus = (Integer)queryDefinition.getAttribute(
				"orderStatus");

			if (orderStatus != null) {
				boolean excludeOrderStatus =
					(boolean)queryDefinition.getAttribute("excludeOrderStatus");

				sql = StringUtil.replace(
					sql, "[$ORDER_STATUS$]",
					_getOrderStatusClause(orderStatus, excludeOrderStatus));
			}
			else {
				sql = StringUtil.replace(
					sql, "[$ORDER_STATUS$]", StringPool.BLANK);
			}

			String[] names = new String[0];

			String keywords = (String)queryDefinition.getAttribute("keywords");

			if (Validator.isNotNull(keywords)) {
				keywords = StringUtil.quote(
					StringUtil.lowerCase(keywords), StringPool.PERCENT);

				names = ArrayUtil.append(names, keywords);

				sql = _customSQL.replaceKeywords(
					sql, "lower(CommerceAccount.name)", StringPool.LIKE, false,
					names);

				sql = _customSQL.replaceAndOperator(sql, true);
			}
			else {
				sql = StringUtil.replace(
					sql,
					"AND (LOWER(CommerceAccount.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			long groupId = (Long)queryDefinition.getAttribute("groupId");

			qPos.add(groupId);

			if (Validator.isNotNull(keywords)) {
				qPos.add(names, 2);
			}

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public CommerceOrder fetchByG_U_C_O_S_First(
		long groupId, long userId, long commerceAccountId, int orderStatus) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_C_O_S);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(CommerceOrderImpl.TABLE_NAME, CommerceOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(commerceAccountId);
			qPos.add(orderStatus);
			qPos.add(userId);

			List<CommerceOrder> commerceOrders =
				(List<CommerceOrder>)QueryUtil.list(q, getDialect(), 0, 1);

			if (!commerceOrders.isEmpty()) {
				return commerceOrders.get(0);
			}

			return null;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceOrder> findByG_O(long groupId, int[] orderStatuses) {
		return doFindByG_O(
			groupId, orderStatuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceOrder> findByG_O(
		long groupId, int[] orderStatuses, int start, int end) {

		return doFindByG_O(groupId, orderStatuses, start, end);
	}

	@Override
	public List<CommerceOrder> findByG_U_C_O(
		long userId, QueryDefinition<CommerceOrder> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_C_O);

			sql = StringUtil.replace(
				sql, "[$USER_ID$]", String.valueOf(userId));

			long commerceAccountId = (Long)queryDefinition.getAttribute(
				"commerceAccountId");

			if (commerceAccountId > 0) {
				sql = StringUtil.replace(
					sql, "[$ACCOUNT_ID$]",
					_getAccountIdClause(commerceAccountId));
			}
			else {
				sql = StringUtil.replace(
					sql, "[$ACCOUNT_ID$]", StringPool.BLANK);
			}

			Integer orderStatus = (Integer)queryDefinition.getAttribute(
				"orderStatus");

			if (orderStatus != null) {
				boolean excludeOrderStatus =
					(boolean)queryDefinition.getAttribute("excludeOrderStatus");

				sql = StringUtil.replace(
					sql, "[$ORDER_STATUS$]",
					_getOrderStatusClause(orderStatus, excludeOrderStatus));
			}
			else {
				sql = StringUtil.replace(
					sql, "[$ORDER_STATUS$]", StringPool.BLANK);
			}

			String[] names = new String[0];

			String keywords = (String)queryDefinition.getAttribute("keywords");

			if (Validator.isNotNull(keywords)) {
				keywords = StringUtil.quote(
					StringUtil.lowerCase(keywords), StringPool.PERCENT);

				names = ArrayUtil.append(names, keywords);

				sql = _customSQL.replaceKeywords(
					sql, "lower(CommerceAccount.name)", StringPool.LIKE, true,
					names);

				sql = _customSQL.replaceAndOperator(sql, true);
			}
			else {
				sql = StringUtil.replace(
					sql,
					"AND (LOWER(CommerceAccount.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(CommerceOrderImpl.TABLE_NAME, CommerceOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			long groupId = (Long)queryDefinition.getAttribute("groupId");

			qPos.add(groupId);

			if (Validator.isNotNull(keywords)) {
				qPos.add(names, 2);
			}

			return (List<CommerceOrder>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceOrder> getShippedCommerceOrdersByCommerceShipmentId(
		long shipmentId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(),
				GET_SHIPPED_COMMERCE_ORDERS_BY_COMMERCE_SHIPMENT_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceOrder", CommerceOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(shipmentId);

			return (List<CommerceOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<CommerceOrder> doFindByG_O(
		long groupId, int[] orderStatuses, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_O);

			sql = replaceOrderStatus(sql, orderStatuses);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceOrder", CommerceOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
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

	private String _getAccountIdClause(long commerceAccountId) {
		return "(CommerceAccount.commerceAccountId = " + commerceAccountId +
			" ) AND";
	}

	private String _getOrderStatusClause(int orderStatus, boolean exclude) {
		if (orderStatus < 0) {
			return StringPool.BLANK;
		}

		if (exclude) {
			return "(CommerceOrder.orderStatus != " + orderStatus + ") AND";
		}

		return "(CommerceOrder.orderStatus = " + orderStatus + ") AND";
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}