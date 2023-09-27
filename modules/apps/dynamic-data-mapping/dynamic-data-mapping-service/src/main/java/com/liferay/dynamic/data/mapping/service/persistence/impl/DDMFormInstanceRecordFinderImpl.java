/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = DDMFormInstanceRecordFinder.class)
public class DDMFormInstanceRecordFinderImpl
	extends DDMFormInstanceRecordFinderBaseImpl
	implements DDMFormInstanceRecordFinder {

	public static final String COUNT_BY_F_S =
		DDMFormInstanceRecordFinder.class.getName() + ".countByF_S";

	public static final String FIND_BY_F_S =
		DDMFormInstanceRecordFinder.class.getName() + ".findByF_S";

	@Override
	public int countByF_S(long ddmFormInstanceId, int status) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_F_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDMFormInstanceRecordVersion.status = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(ddmFormInstanceId);

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
	public List<DDMFormInstanceRecord> findByF_S(
		long ddmFormInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_F_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.removeSubstring(
					sql, "(DDMFormInstanceRecordVersion.status = ?) AND");
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"DDMFormInstanceRecord", DDMFormInstanceRecordImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (status != WorkflowConstants.STATUS_ANY) {
				queryPos.add(status);
			}

			queryPos.add(ddmFormInstanceId);

			return (List<DDMFormInstanceRecord>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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