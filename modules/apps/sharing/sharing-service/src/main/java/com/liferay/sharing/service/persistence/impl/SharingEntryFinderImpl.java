/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			if (classNameId > 0) {
				queryPos.add(classNameId);
			}

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("SharingEntry", SharingEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			if (classNameId > 0) {
				queryPos.add(classNameId);
			}

			return (List<SharingEntry>)QueryUtil.list(
				sqlQuery, getDialect(), begin, end);
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
			sql = StringUtil.removeSubstring(sql, "[$CLASS_NAME_ID_WHERE$]");
		}

		return sql;
	}

	@Reference
	private CustomSQL _customSQL;

}