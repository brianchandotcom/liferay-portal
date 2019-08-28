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

package com.liferay.wiki.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.service.persistence.WikiPageFinder;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = WikiPageFinder.class)
public class WikiPageFinderImpl
	extends WikiPageFinderBaseImpl implements WikiPageFinder {

	public static final String COUNT_BY_CREATE_DATE =
		WikiPageFinder.class.getName() + ".countByCreateDate";

	public static final String COUNT_BY_MODIFIED_DATE =
		WikiPageFinder.class.getName() + ".countByModifiedDate";

	public static final String COUNT_BY_G_N_H_S =
		WikiPageFinder.class.getName() + ".countByG_N_H_S";

	public static final String FIND_BY_RESOURCE_PRIM_KEY =
		WikiPageFinder.class.getName() + ".findByResourcePrimKey";

	public static final String FIND_BY_CREATE_DATE =
		WikiPageFinder.class.getName() + ".findByCreateDate";

	public static final String FIND_BY_MODIFIED_DATE =
		WikiPageFinder.class.getName() + ".findByModifiedDate";

	public static final String FIND_BY_NO_ASSETS =
		WikiPageFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_G_N_H_S =
		WikiPageFinder.class.getName() + ".findByG_N_H_S";

	@Override
	public int countByCreateDate(
		long groupId, long nodeId, Date createDate, boolean before) {

		return countByCreateDate(
			groupId, nodeId, new Timestamp(createDate.getTime()), before);
	}

	@Override
	public int countByCreateDate(
		long groupId, long nodeId, Timestamp createDate, boolean before) {

		return doCountByCreateDate(groupId, nodeId, createDate, before, false);
	}

	@Override
	public int countByModifiedDate(
		long groupId, long nodeId, Date modifiedDate, boolean before) {

		return doCountByModifiedDate(
			groupId, nodeId, new Timestamp(modifiedDate.getTime()), before,
			false);
	}

	@Override
	public int countByModifiedDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before) {

		return doCountByModifiedDate(
			groupId, nodeId, modifiedDate, before, false);
	}

	@Override
	public int countByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition) {

		return doCountByG_N_H_S(groupId, nodeId, head, queryDefinition, false);
	}

	@Override
	public int filterCountByCreateDate(
		long groupId, long nodeId, Date createDate, boolean before) {

		return doCountByCreateDate(
			groupId, nodeId, new Timestamp(createDate.getTime()), before, true);
	}

	@Override
	public int filterCountByCreateDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before) {

		return doCountByCreateDate(groupId, nodeId, modifiedDate, before, true);
	}

	@Override
	public int filterCountByModifiedDate(
		long groupId, long nodeId, Date modifiedDate, boolean before) {

		return doCountByModifiedDate(
			groupId, nodeId, new Timestamp(modifiedDate.getTime()), before,
			true);
	}

	@Override
	public int filterCountByModifiedDate(
		long groupId, long nodeId, Timestamp createDate, boolean before) {

		return doCountByModifiedDate(groupId, nodeId, createDate, before, true);
	}

	@Override
	public int filterCountByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition) {

		return doCountByG_N_H_S(groupId, nodeId, head, queryDefinition, true);
	}

	@Override
	public List<WikiPage> filterFindByCreateDate(
		long groupId, long nodeId, Date createDate, boolean before, int start,
		int end) {

		return doFindByCreateDate(
			groupId, nodeId, new Timestamp(createDate.getTime()), before, start,
			end, true);
	}

	@Override
	public List<WikiPage> filterFindByCreateDate(
		long groupId, long nodeId, Timestamp createDate, boolean before,
		int start, int end) {

		return doFindByCreateDate(
			groupId, nodeId, createDate, before, start, end, true);
	}

	@Override
	public List<WikiPage> filterFindByModifiedDate(
		long groupId, long nodeId, Date modifiedDate, boolean before, int start,
		int end) {

		return doFindByCreateDate(
			groupId, nodeId, new Timestamp(modifiedDate.getTime()), before,
			start, end, true);
	}

	@Override
	public List<WikiPage> filterFindByModifiedDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before,
		int start, int end) {

		return doFindByCreateDate(
			groupId, nodeId, modifiedDate, before, start, end, true);
	}

	@Override
	public List<WikiPage> filterFindByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition) {

		return doFindByG_N_H_S(groupId, nodeId, head, queryDefinition, true);
	}

	@Override
	public WikiPage findByResourcePrimKey(long resourcePrimKey)
		throws NoSuchPageException {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_RESOURCE_PRIM_KEY);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("WikiPage", WikiPageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourcePrimKey);

			List<WikiPage> pages = q.list();

			if (!pages.isEmpty()) {
				return pages.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(3);

		sb.append("No WikiPage exists with the key {resourcePrimKey");
		sb.append(resourcePrimKey);
		sb.append("}");

		throw new NoSuchPageException(sb.toString());
	}

	@Override
	public List<WikiPage> findByCreateDate(
		long groupId, long nodeId, Date createDate, boolean before, int start,
		int end) {

		return doFindByCreateDate(
			groupId, nodeId, new Timestamp(createDate.getTime()), before, start,
			end, false);
	}

	@Override
	public List<WikiPage> findByCreateDate(
		long groupId, long nodeId, Timestamp createDate, boolean before,
		int start, int end) {

		return doFindByCreateDate(
			groupId, nodeId, createDate, before, start, end, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<WikiPage> findByModifiedDate(
		long groupId, long nodeId, Date modifiedDate, boolean before, int start,
		int end) {

		return doFindByModifiedDate(
			groupId, nodeId, new Timestamp(modifiedDate.getTime()), before,
			start, end, false);
	}

	@Override
	public List<WikiPage> findByModifiedDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before,
		int start, int end) {

		return doFindByModifiedDate(
			groupId, nodeId, modifiedDate, before, start, end, false);
	}

	@Override
	public List<WikiPage> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("WikiPage", WikiPageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(WikiPage.class));

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<WikiPage> findByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition) {

		return doFindByG_N_H_S(groupId, nodeId, head, queryDefinition, false);
	}

	protected int doCountByCreateDate(
		long groupId, long nodeId, Timestamp createDate, boolean before,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_CREATE_DATE);

			String createDateComparator = StringPool.GREATER_THAN;

			if (before) {
				createDateComparator = StringPool.LESS_THAN;
			}

			sql = StringUtil.replace(
				sql, "[$CREATE_DATE_COMPARATOR$]", createDateComparator);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(createDate);
			qPos.add(true);
			qPos.add(WorkflowConstants.STATUS_APPROVED);

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

	protected int doCountByModifiedDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_MODIFIED_DATE);

			String modifiedDateComparator = StringPool.GREATER_THAN;

			if (before) {
				modifiedDateComparator = StringPool.LESS_THAN;
			}

			sql = StringUtil.replace(
				sql, "[$MODIFIED_DATE_COMPARATOR$]", modifiedDateComparator);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(modifiedDate);
			qPos.add(true);
			qPos.add(WorkflowConstants.STATUS_APPROVED);

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

	protected int doCountByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_N_H_S, queryDefinition, "WikiPage");

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(head);

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					qPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			qPos.add(queryDefinition.getStatus());

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

	protected List<WikiPage> doFindByCreateDate(
		long groupId, long nodeId, Timestamp createDate, boolean before,
		int start, int end, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_CREATE_DATE);

			String createDateComparator = StringPool.GREATER_THAN;

			if (before) {
				createDateComparator = StringPool.LESS_THAN;
			}

			sql = StringUtil.replace(
				sql, "[$CREATE_DATE_COMPARATOR$]", createDateComparator);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("WikiPage", WikiPageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(createDate);
			qPos.add(true);
			qPos.add(WorkflowConstants.STATUS_APPROVED);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<WikiPage> doFindByModifiedDate(
		long groupId, long nodeId, Timestamp modifiedDate, boolean before,
		int start, int end, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_MODIFIED_DATE);

			String modifiedDateComparator = StringPool.GREATER_THAN;

			if (before) {
				modifiedDateComparator = StringPool.LESS_THAN;
			}

			sql = StringUtil.replace(
				sql, "[$MODIFIED_DATE_COMPARATOR$]", modifiedDateComparator);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("WikiPage", WikiPageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(modifiedDate);
			qPos.add(true);
			qPos.add(WorkflowConstants.STATUS_APPROVED);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<WikiPage> doFindByG_N_H_S(
		long groupId, long nodeId, boolean head,
		QueryDefinition<WikiPage> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_N_H_S, queryDefinition, "WikiPage");

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, WikiPage.class.getName(), "WikiPage.resourcePrimKey",
					groupId);
			}

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator("WikiPage"));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("WikiPage", WikiPageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(nodeId);
			qPos.add(head);

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					qPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			qPos.add(queryDefinition.getStatus());

			return (List<WikiPage>)QueryUtil.list(
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

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private Portal _portal;

}