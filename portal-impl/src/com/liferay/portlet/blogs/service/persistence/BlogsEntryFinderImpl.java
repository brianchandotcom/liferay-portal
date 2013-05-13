/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryFinderImpl
	extends BasePersistenceImpl<BlogsEntry> implements BlogsEntryFinder {

	public static final String COUNT_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".countByOrganizationIds";

	public static final String FIND_BY_GROUP_IDS =
		BlogsEntryFinder.class.getName() + ".findByGroupIds";

	public static final String FIND_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".findByOrganizationIds";

	public static final String FIND_BY_NO_ASSETS =
		BlogsEntryFinder.class.getName() + ".findByNoAssets";

	@Override
	public int countByOrganizationId(
			long organizationId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return countByOrganizationIds(
			organizationIds, displayDate, queryDefinition);
	}

	@Override
	public int countByOrganizationIds(
			List<Long> organizationIds, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_ORGANIZATION_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
			}

			qPos.add(displayDate_TS);

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
	public List<BlogsEntry> findByGroupIds(
			long companyId, long groupId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(displayDate);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<BlogsEntry>)QueryUtil.list(
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

	@Override
	public List<BlogsEntry> findByOrganizationId(
			long organizationId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return findByOrganizationIds(
			organizationIds, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> findByOrganizationIds(
			List<Long> organizationIds, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ORGANIZATION_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = CustomSQLUtil.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));
			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
			}

			qPos.add(displayDate_TS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<BlogsEntry>)QueryUtil.list(
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

	@Override
	public List<BlogsEntry> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getOrganizationIds(List<Long> organizationIds) {
		if (organizationIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(organizationIds.size() * 2 - 1);

		for (int i = 0; i < organizationIds.size(); i++) {
			sb.append("Users_Orgs.organizationId = ? ");

			if ((i + 1) != organizationIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}