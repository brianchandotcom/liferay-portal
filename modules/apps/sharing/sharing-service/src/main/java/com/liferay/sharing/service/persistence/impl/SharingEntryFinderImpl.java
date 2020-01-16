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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.impl.SharingEntryImpl;
import com.liferay.sharing.service.persistence.SharingEntryFinder;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = SharingEntryFinder.class)
public class SharingEntryFinderImpl
	extends SharingEntryFinderBaseImpl implements SharingEntryFinder {

	public static final String COUNT_BY_USER_ID =
		SharingEntryFinder.class.getName() + ".countByUserId";

	public static final String FIND_BY_USER_ID =
		SharingEntryFinder.class.getName() + ".findByUserId";

	@Override
	public int countByUserId(long userId, long classNameId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_USER_ID);

			sql = _replaceClassNameIdWhere(sql, classNameId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			if (classNameId > 0) {
				qPos.add(classNameId);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

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
	public List<SharingEntry> findByUserId(
		long userId, long classNameId, int begin, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_USER_ID);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			sql = _replaceClassNameIdWhere(sql, classNameId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("SharingEntry", SharingEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			if (classNameId > 0) {
				qPos.add(classNameId);
			}

			return (List<SharingEntry>)QueryUtil.list(
				q, getDialect(), begin, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private String _replaceClassNameIdWhere(String sql, long classNameId) {
		if (classNameId > 0) {
			sql = StringUtil.replace(
				sql, "[$CLASS_NAME_ID_WHERE$]",
				"AND SharingEntry.classNameId = ?");
		}
		else {
			sql = StringUtil.replace(
				sql, "[$CLASS_NAME_ID_WHERE$]", StringPool.BLANK);
		}

		return sql;
	}

	@Reference
	private CustomSQL _customSQL;

}