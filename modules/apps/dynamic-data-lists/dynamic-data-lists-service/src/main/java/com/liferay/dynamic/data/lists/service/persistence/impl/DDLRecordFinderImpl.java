/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service.persistence.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDLRecordFinder.class)
public class DDLRecordFinderImpl
	extends DDLRecordFinderBaseImpl implements DDLRecordFinder {

	public static final String COUNT_BY_R_S =
		DDLRecordFinder.class.getName() + ".countByR_S";

	public static final String COUNT_BY_C_S_S =
		DDLRecordFinder.class.getName() + ".countByC_S_S";

	public static final String FIND_BY_R_S =
		DDLRecordFinder.class.getName() + ".findByR_S";

	public static final String FIND_BY_C_S_S =
		DDLRecordFinder.class.getName() + ".findByC_S_S";

	@Override
	public int countByR_S(long recordSetId, int status) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_R_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(recordSetId);

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
	public int countByC_S_S(long companyId, int status, int scope) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_S_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(scope);
			queryPos.add(companyId);

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
	public List<DDLRecord> findByR_S(
		long recordSetId, int status, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_R_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("DDLRecord", DDLRecordImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(recordSetId);

			return (List<DDLRecord>)QueryUtil.list(
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
	public List<DDLRecord> findByC_S_S(
		long companyId, int status, int scope, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_S_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("DDLRecord", DDLRecordImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(scope);
			queryPos.add(companyId);

			return (List<DDLRecord>)QueryUtil.list(
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
	public Long[] findByC_S_S_MinAndMax(long companyId, int status, int scope) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_S_S);

			sql = StringUtil.replace(
				sql, "COUNT(DISTINCT DDLRecord.recordId) AS COUNT_VALUE",
				"MIN(DDLRecord.recordId) AS minRecordId, " +
					"MAX(DDLRecord.recordId) AS maxRecordId");

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("minRecordId", Type.LONG);
			sqlQuery.addScalar("maxRecordId", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(scope);
			queryPos.add(companyId);

			Object[] array = (Object[])sqlQuery.iterateNext();

			if (array == null) {
				return null;
			}

			return ArrayUtil.toLongArray(array);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DDLRecord> findByC_S_S_MinAndMax(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_S_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDLRecordVersion.status = ?) AND");
			}

			sql = _customSQL.removeOrderBy(sql);

			sql +=
				" AND (DDLRecord.recordId >= ?) AND (DDLRecord.recordId < ?)";

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("DDLRecord", DDLRecordImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(scope);
			queryPos.add(companyId);
			queryPos.add(minRecordId);
			queryPos.add(maxRecordId);

			return sqlQuery.list();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}