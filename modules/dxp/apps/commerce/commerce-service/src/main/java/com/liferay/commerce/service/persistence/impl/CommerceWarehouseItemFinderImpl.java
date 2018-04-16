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

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.model.impl.CommerceWarehouseItemImpl;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemFinderImpl
	extends CommerceWarehouseItemFinderBaseImpl
	implements CommerceWarehouseItemFinder {

	public static final String FIND_BY_CP_INSTANCE_ID =
		CommerceWarehouseItemFinder.class.getName() + ".findByCPInstanceId";

	public static final String GET_CP_INSTANCE_QUANTITY =
		CommerceWarehouseItemFinder.class.getName() + ".getCPInstanceQuantity";

	public static final String SUM_VALUE = "SUM_VALUE";

	@Override
	public List<CommerceWarehouseItem> findByCPInstanceId(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_CP_INSTANCE_ID);

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"CommerceWarehouseItem", CommerceWarehouseItemImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(cpInstanceId);

			return (List<CommerceWarehouseItem>)QueryUtil.list(
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
	public int getCPInstanceQuantity(long cpInstanceId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), GET_CP_INSTANCE_QUANTITY);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(SUM_VALUE, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(cpInstanceId);

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