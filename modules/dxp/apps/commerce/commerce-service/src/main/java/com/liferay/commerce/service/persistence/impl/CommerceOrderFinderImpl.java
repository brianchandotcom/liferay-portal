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

import com.liferay.commerce.service.persistence.CommerceOrderFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderFinderImpl
	extends CommerceOrderFinderBaseImpl implements CommerceOrderFinder {

	public static final String COUNT_BY_G_s =
		CommerceOrderFinder.class.getName() + ".countByG_S";

	public Map<Integer, Long> countByG_S(long groupId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), COUNT_BY_G_s);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("status", Type.INTEGER);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Map<Integer, Long> statusCounts = new HashMap<>();

			Iterator<Object[]> iterator = q.iterate();

			while (iterator.hasNext()) {
				Object[] array = iterator.next();

				statusCounts.put((Integer)array[0], (Long)array[1]);
			}

			return statusCounts;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}