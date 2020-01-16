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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordFinder;
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
				sql = StringUtil.replace(
					sql, "(DDMFormInstanceRecordVersion.status = ?) AND",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(ddmFormInstanceId);

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
	public List<DDMFormInstanceRecord> findByF_S(
		long ddmFormInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_F_S);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(
					sql, "(DDMFormInstanceRecordVersion.status = ?) AND",
					StringPool.BLANK);
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"DDMFormInstanceRecord", DDMFormInstanceRecordImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(ddmFormInstanceId);

			return (List<DDMFormInstanceRecord>)QueryUtil.list(
				q, getDialect(), start, end);
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