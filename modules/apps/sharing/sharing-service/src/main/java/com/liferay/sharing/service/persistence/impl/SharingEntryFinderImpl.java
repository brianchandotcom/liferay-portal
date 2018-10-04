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

package com.liferay.sharing.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.impl.SharingEntryImpl;
import com.liferay.sharing.service.persistence.SharingEntryFinder;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class SharingEntryFinderImpl
	extends SharingEntryFinderBaseImpl implements SharingEntryFinder {

	public static final String COUNT_BY_TO_USER_ID =
		SharingEntryFinder.class.getName() + ".countByToUserId";

	public static final String FIND_BY_TO_USER_ID =
		SharingEntryFinder.class.getName() + ".findByToUserId";

	public int countByToUserId(long toUserId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_TO_USER_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(toUserId);

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

	public List<SharingEntry> findByToUserId(
		long toUserId, int begin, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_TO_USER_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("SharingEntry", SharingEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(toUserId);

			return (List<SharingEntry>)QueryUtil.list(
				q, getDialect(), begin, end);
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