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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageFinderImpl
	extends BasePersistenceImpl<MBMessage> implements MBMessageFinder {

	public static final String COUNT_BY_C_T =
		MBMessageFinder.class.getName() + ".countByC_T";

	public static final String COUNT_BY_G_U_C_S =
		MBMessageFinder.class.getName() + ".countByG_U_C_S";

	public static final String COUNT_BY_G_U_MD_C_S =
		MBMessageFinder.class.getName() + ".countByG_U_MD_C_S";

	public static final String COUNT_BY_G_U_C_A_S =
		MBMessageFinder.class.getName() + ".countByG_U_C_A_S";

	public static final String FIND_BY_NO_ASSETS =
		MBMessageFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_G_U_C_S =
		MBMessageFinder.class.getName() + ".findByG_U_C_S";

	public static final String FIND_BY_G_U_MD_C_S =
		MBMessageFinder.class.getName() + ".findByG_U_MD_C_S";

	public static final String FIND_BY_G_U_C_A_S =
		MBMessageFinder.class.getName() + ".findByG_U_C_A_S";

	@Override
	public int countByC_T(Date createDate, long threadId)
		throws SystemException {

		Timestamp createDate_TS = CalendarUtil.getTimestamp(createDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_T);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(createDate_TS);
			qPos.add(threadId);

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
	public int countByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status)
		throws SystemException {

		return doCountByG_U_C_S(groupId, userId, categoryIds, status, false);
	}

	@Override
	public int countByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status)
		throws SystemException {

		return doCountByG_U_C_A_S(
			groupId, userId, categoryIds, anonymous, status, false);
	}

	@Override
	public int filterCountByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status)
		throws SystemException {

		return doCountByG_U_C_S(groupId, userId, categoryIds, status, true);
	}

	@Override
	public int filterCountByG_U_MD_C_S(
			long groupId, long userId, Date modifiedDate, long[] categoryIds,
			int status)
		throws SystemException {

		return doCountByG_U_MD_C_S(
			groupId, userId, modifiedDate, categoryIds, status, true);
	}

	@Override
	public int filterCountByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status)
		throws SystemException {

		return doCountByG_U_C_A_S(
			groupId, userId, categoryIds, anonymous, status, true);
	}

	@Override
	public List<Long> filterFindByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status,
			int start, int end)
		throws SystemException {

		return doFindByG_U_C_S(
			groupId, userId, categoryIds, status, start, end, true);
	}

	@Override
	public List<Long> filterFindByG_U_MD_C_S(
			long groupId, long userId, Date modifiedDate, long[] categoryIds,
			int status, int start, int end)
		throws SystemException {

		return doFindByG_U_MD_C_S(
			groupId, userId, modifiedDate, categoryIds, status, start, end,
			true);
	}

	@Override
	public List<Long> filterFindByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status, int start, int end)
		throws SystemException {

		return doFindByG_U_C_A_S(
			groupId, userId, categoryIds, anonymous, status, start, end, true);
	}

	@Override
	public List<MBMessage> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

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
	public List<Long> findByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status,
			int start, int end)
		throws SystemException {

		return doFindByG_U_C_S(
			groupId, userId, categoryIds, status, start, end, false);
	}

	@Override
	public List<Long> findByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status, int start, int end)
		throws SystemException {

		return doFindByG_U_C_A_S(
			groupId, userId, categoryIds, anonymous, status, start, end, false);
	}

	protected int doCountByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_C_S);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
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

	protected int doCountByG_U_MD_C_S(
			long groupId, long userId, Date modifiedDate, long[] categoryIds,
			int status, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_MD_C_S);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(modifiedDate);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
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

	protected int doCountByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_C_A_S);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
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

	protected List<Long> doFindByG_U_C_S(
			long groupId, long userId, long[] categoryIds, int status,
			int start, int end, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_C_S);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("threadId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Long> doFindByG_U_MD_C_S(
			long groupId, long userId, Date modifiedDate, long[] categoryIds,
			int status, int start, int end, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_MD_C_S);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("threadId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			qPos.add(modifiedDate);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Long> doFindByG_U_C_A_S(
			long groupId, long userId, long[] categoryIds, boolean anonymous,
			int status, int start, int end, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_C_A_S);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(currentMessage.categoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "currentMessage.categoryId = ?",
					"currentMessage.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR currentMessage.categoryId = "));
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.appendCriteria(
					sql, "AND (currentMessage.status = ?)");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(),
					"currentMessage.rootMessageId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("threadId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _USER_ID_SQL =
		"AND (currentMessage.userId = ?)";

}