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
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemFinderImpl
	extends CommerceOrderItemFinderBaseImpl implements CommerceOrderItemFinder {

	public static final String FIND_BY_C_C =
		CommerceOrderItemFinder.class.getName() + ".findByC_C";

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

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_C_C);

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
	public int getCPInstanceQuantity(long cpInstanceId, int status) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), GET_CP_INSTANCE_QUANTITY);

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

}