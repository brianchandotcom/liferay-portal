/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.impl.CommerceOrderItemImpl;
import com.liferay.commerce.service.persistence.CommerceOrderItemFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemFinderImpl
	extends CommerceOrderItemFinderBaseImpl implements CommerceOrderItemFinder {

	public static final String FIND_BY_C_C =
		CommerceOrderItemFinder.class.getName() + ".findByC_C";

	public static final String GET_COMMERCE_ORDER_ITEMS_QUANTITY =
		CommerceOrderItemFinder.class.getName() +
			".getCommerceOrderItemsQuantity";

	public static final String GET_CP_INSTANCE_QUANTITY =
		CommerceOrderItemFinder.class.getName() + ".getCPInstanceQuantity";

	public static final String SUM_VALUE = "SUM_VALUE";

	@Override
	public List<CommerceOrderItem> findByC_C(
		long commerceWarehouseId, long commerceAddressId) {

		return findByC_C(
			commerceWarehouseId, commerceAddressId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceOrderItem> findByC_C(
		long commerceWarehouseId, long commerceAddressId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceOrderItem", CommerceOrderItemImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceWarehouseId);
			qPos.add(commerceAddressId);

			return (List<CommerceOrderItem>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(SUM_VALUE, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceOrderId);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long sum = itr.next();

				if (sum != null) {
					return sum.intValue();
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
	public int getCPInstanceQuantity(long cpInstanceId, int status) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), GET_CP_INSTANCE_QUANTITY);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(SUM_VALUE, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(cpInstanceId);
			qPos.add(status);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long sum = itr.next();

				if (sum != null) {
					return sum.intValue();
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

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}