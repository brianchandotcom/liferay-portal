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

package com.liferay.shopping.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.ShoppingOrderConstants;
import com.liferay.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.shopping.service.persistence.ShoppingOrderFinder;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderFinderImpl
	extends ShoppingOrderFinderBaseImpl implements ShoppingOrderFinder {

	public static final String COUNT_BY_G_C_U_N_PPPS =
		ShoppingOrderFinder.class.getName() + ".countByG_C_U_N_PPPS";

	public static final String FIND_BY_G_C_U_N_PPPS =
		ShoppingOrderFinder.class.getName() + ".findByG_C_U_N_PPPS";

	@Override
	public int countByG_C_U_N_PPPS(
		long groupId, long companyId, long userId, String number,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator) {

		number = StringUtil.upperCase(number);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), COUNT_BY_G_C_U_N_PPPS);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderConstants.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(number);
			qPos.add(number);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<ShoppingOrder> findByG_C_U_N_PPPS(
		long groupId, long companyId, long userId, String number,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator, int start, int end,
		OrderByComparator<ShoppingOrder> obc) {

		number = StringUtil.upperCase(number);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_G_C_U_N_PPPS);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (Validator.isNull(ppPaymentStatus)) {
				sql = StringUtil.replace(
					sql, "ppPaymentStatus = ?", "ppPaymentStatus != ?");

				ppPaymentStatus = ShoppingOrderConstants.STATUS_LATEST;
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("ShoppingOrder", ShoppingOrderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(number);
			qPos.add(number);
			qPos.add(billingFirstName);
			qPos.add(billingFirstName);
			qPos.add(billingLastName);
			qPos.add(billingLastName);
			qPos.add(billingEmailAddress);
			qPos.add(billingEmailAddress);
			qPos.add(shippingFirstName);
			qPos.add(shippingFirstName);
			qPos.add(shippingLastName);
			qPos.add(shippingLastName);
			qPos.add(shippingEmailAddress);
			qPos.add(shippingEmailAddress);
			qPos.add(ppPaymentStatus);

			return (List<ShoppingOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _USER_ID_SQL = "(userId = ?) AND";

}