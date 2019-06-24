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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.impl.CTEntryImpl;
import com.liferay.change.tracking.service.persistence.CTEntryFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = CTEntryFinder.class)
public class CTEntryFinderImpl
	extends CTEntryFinderBaseImpl implements CTEntryFinder {

	public static final String COUNT_BY_RELATED_CT_ENTRIES =
		CTEntryFinder.class.getName() + ".countByRelatedCTEntries";

	public static final String FIND_BY_RELATED_CT_ENTRIES =
		CTEntryFinder.class.getName() + ".findByRelatedCTEntries";

	@Override
	@SuppressWarnings("unchecked")
	public int countByRelatedCTEntries(
		long ctEntryId, QueryDefinition<CTEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_RELATED_CT_ENTRIES);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = _customSQL.appendCriteria(
						sql, "AND (CTEntryAggregate.status != ?)");
				}
				else {
					sql = _customSQL.appendCriteria(
						sql, "AND (CTEntryAggregate.status = ?)");
				}
			}

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(ctEntryId);
			qPos.add(ctEntryId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
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
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CTEntry> findByRelatedCTEntries(
		long ctEntryId, QueryDefinition<CTEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_RELATED_CT_ENTRIES);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = _customSQL.appendCriteria(
						sql, "AND (CTEntryAggregate.status != ?)");
				}
				else {
					sql = _customSQL.appendCriteria(
						sql, "AND (CTEntryAggregate.status = ?)");
				}
			}

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CTEntry", CTEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(ctEntryId);
			qPos.add(ctEntryId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<CTEntry>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}