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

package com.liferay.message.boards.service.persistence.impl;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.impl.MBThreadImpl;
import com.liferay.message.boards.service.persistence.MBThreadFinder;
import com.liferay.message.boards.service.persistence.MBThreadUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(service = MBThreadFinder.class)
public class MBThreadFinderImpl
	extends MBThreadFinderBaseImpl implements MBThreadFinder {

	public static final String COUNT_BY_G_U =
		MBThreadFinder.class.getName() + ".countByG_U";

	public static final String COUNT_BY_G_C =
		MBThreadFinder.class.getName() + ".countByG_C";

	public static final String COUNT_BY_G_U_C =
		MBThreadFinder.class.getName() + ".countByG_U_C";

	public static final String COUNT_BY_G_U_LPD =
		MBThreadFinder.class.getName() + ".countByG_U_LPD";

	public static final String COUNT_BY_G_U_A =
		MBThreadFinder.class.getName() + ".countByG_U_A";

	public static final String COUNT_BY_S_G_U =
		MBThreadFinder.class.getName() + ".countByS_G_U";

	public static final String COUNT_BY_G_U_C_A =
		MBThreadFinder.class.getName() + ".countByG_U_C_A";

	public static final String COUNT_BY_S_G_U_C =
		MBThreadFinder.class.getName() + ".countByS_G_U_C";

	public static final String FIND_BY_G_U =
		MBThreadFinder.class.getName() + ".findByG_U";

	public static final String FIND_BY_G_C =
		MBThreadFinder.class.getName() + ".findByG_C";

	public static final String FIND_BY_G_U_C =
		MBThreadFinder.class.getName() + ".findByG_U_C";

	public static final String FIND_BY_G_U_LPD =
		MBThreadFinder.class.getName() + ".findByG_U_LPD";

	public static final String FIND_BY_G_U_A =
		MBThreadFinder.class.getName() + ".findByG_U_A";

	public static final String FIND_BY_S_G_U =
		MBThreadFinder.class.getName() + ".findByS_G_U";

	public static final String FIND_BY_G_U_C_A =
		MBThreadFinder.class.getName() + ".findByG_U_C_A";

	public static final String FIND_BY_S_G_U_C =
		MBThreadFinder.class.getName() + ".findByS_G_U_C";

	@Override
	public int countByG_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

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
	public int countByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByG_C(groupId, categoryId, queryDefinition, false);
	}

	@Override
	public int countByG_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

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
	public int countByG_U_LPD(
		long groupId, long userId, Date lastPostDate,
		QueryDefinition<MBThread> queryDefinition) {

		return countByG_U_LPD_A(
			groupId, userId, lastPostDate, true, queryDefinition);
	}

	@Override
	public int countByG_U_A(
		long groupId, long userId, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U_A);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

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
	public int countByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U(groupId, userId, queryDefinition);
	}

	@Override
	public int countByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

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
	public int countByG_U_LPD_A(
		long groupId, long userId, Date lastPostDate, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_U_LPD);

			if (userId <= 0) {
				sql = StringUtil.replace(
					sql, "DISTINCT MBThread.threadId", StringPool.STAR);
				sql = StringUtil.replace(
					sql, _INNER_JOIN_SQL, StringPool.BLANK);
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			sql = updateSQL(sql, queryDefinition);

			if (!includeAnonymous && (userId > 0)) {
				sql = _customSQL.appendCriteria(
					sql, "AND (MBMessage.anonymous = [$FALSE$])");
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(lastPostDate);

			if (userId > 0) {
				qPos.add(userId);
			}

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
	public int countByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, false);
	}

	@Override
	public int filterCountByG_C(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return MBThreadUtil.countByG_C(groupId, categoryId);
		}

		Session session = null;

		try {
			session = openSession();

			QueryDefinition queryDefinition = new QueryDefinition(
				WorkflowConstants.STATUS_ANY);

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_C, queryDefinition,
				MBThreadImpl.TABLE_NAME);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(WorkflowConstants.STATUS_ANY);

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int filterCountByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByG_C(groupId, categoryId, queryDefinition, true);
	}

	@Override
	public int filterCountByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, true);
	}

	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return MBThreadUtil.findByG_C(groupId, categoryId, start, end);
		}

		Session session = null;

		try {
			session = openSession();

			QueryDefinition queryDefinition = new QueryDefinition(
				WorkflowConstants.STATUS_ANY);

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_C, queryDefinition,
				MBThreadImpl.TABLE_NAME);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(WorkflowConstants.STATUS_ANY);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByG_C(groupId, categoryId, queryDefinition, true);
	}

	@Override
	public List<MBThread> filterFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, true);
	}

	@Override
	public List<MBThread> findByG_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByG_C(groupId, categoryId, queryDefinition, false);
	}

	@Override
	public List<MBThread> findByG_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByG_U_LPD(
		long groupId, long userId, Date lastPostDate,
		QueryDefinition<MBThread> queryDefinition) {

		return findByG_U_LPD_A(
			groupId, userId, lastPostDate, true, queryDefinition);
	}

	@Override
	public List<MBThread> findByG_U_A(
		long groupId, long userId, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_A);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_S_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_C_A);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByG_U_LPD_A(
		long groupId, long userId, Date lastPostDate, boolean includeAnonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_U_LPD);

			if (userId <= 0) {
				sql = StringUtil.replace(sql, "DISTINCT ", StringPool.BLANK);
				sql = StringUtil.replace(
					sql, _INNER_JOIN_SQL, StringPool.BLANK);
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			sql = updateSQL(sql, queryDefinition);

			if (!includeAnonymous && (userId > 0)) {
				sql = _customSQL.appendCriteria(
					sql, "AND (MBMessage.anonymous = [$FALSE$])");
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(lastPostDate);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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
	public List<MBThread> findByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, false);
	}

	protected int doCountByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		if (!inlineSQLHelper || !InlineSQLHelperUtil.isEnabled(groupId)) {
			if (queryDefinition.isExcludeStatus()) {
				return MBThreadUtil.countByG_C_NotS(
					groupId, categoryId, queryDefinition.getStatus());
			}

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				return MBThreadUtil.countByG_C_S(
					groupId, categoryId, queryDefinition.getStatus());
			}

			return MBThreadUtil.countByG_C(groupId, categoryId);
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_C, queryDefinition,
				MBThreadImpl.TABLE_NAME);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(queryDefinition.getStatus());

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					qPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_S_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

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

	protected int doCountByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_S_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(), "MBThread.rootMessageId",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

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

	protected List<MBThread> doFindByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		if (!inlineSQLHelper || !InlineSQLHelperUtil.isEnabled(groupId)) {
			if (queryDefinition.isExcludeStatus()) {
				return MBThreadUtil.findByG_C_NotS(
					groupId, categoryId, queryDefinition.getStatus(),
					queryDefinition.getStart(), queryDefinition.getEnd(),
					queryDefinition.getOrderByComparator());
			}

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				return MBThreadUtil.findByG_C_S(
					groupId, categoryId, queryDefinition.getStatus(),
					queryDefinition.getStart(), queryDefinition.getEnd(),
					queryDefinition.getOrderByComparator());
			}

			return MBThreadUtil.findByG_C(
				groupId, categoryId, queryDefinition.getStart(),
				queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_C, queryDefinition,
				MBThreadImpl.TABLE_NAME);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);
			qPos.add(queryDefinition.getStatus());

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					qPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			return (List<MBThread>)QueryUtil.list(
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

	protected List<MBThread> doFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_S_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				String mergedCategoryIds = StringUtil.merge(
					categoryIds, " OR MBThread.categoryId = ");

				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " + mergedCategoryIds);
			}

			sql = updateSQL(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(), "MBThread.rootMessageId",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
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

	protected String updateSQL(
		String sql, QueryDefinition<MBThread> queryDefinition) {

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			return sql;
		}

		if (queryDefinition.isExcludeStatus()) {
			return _customSQL.appendCriteria(sql, "AND (MBThread.status != ?)");
		}

		return _customSQL.appendCriteria(sql, "AND (MBThread.status = ?)");
	}

	private static final String _INNER_JOIN_SQL =
		"INNER JOIN MBMessage ON MBThread.threadId = MBMessage.threadId";

	private static final String _USER_ID_SQL = "AND (MBMessage.userId = ?)";

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private Portal _portal;

}