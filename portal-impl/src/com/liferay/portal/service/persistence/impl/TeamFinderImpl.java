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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.persistence.TeamFinder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.TeamImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamFinderImpl extends TeamFinderBaseImpl implements TeamFinder {

	public static final String COUNT_BY_G_N_D =
		TeamFinder.class.getName() + ".countByG_N_D";

	public static final String FIND_BY_G_U =
		TeamFinder.class.getName() + ".findByG_U";

	public static final String FIND_BY_G_N_D =
		TeamFinder.class.getName() + ".findByG_N_D";

	public static final String JOIN_BY_USERS_USER_GROUPS =
		TeamFinder.class.getName() + ".joinByUsersUserGroups";

	public static final String JOIN_BY_USERS_TEAMS =
		TeamFinder.class.getName() + ".joinByUsersTeams";

	@Override
	public int countByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params) {

		return doCountByG_N_D(groupId, name, description, params, false);
	}

	@Override
	public int filterCountByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params) {

		return doCountByG_N_D(groupId, name, description, params, true);
	}

	@Override
	public List<Team> filterFindByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> obc) {

		return doFindByG_N_D(
			groupId, name, description, params, start, end, obc, true);
	}

	@Override
	public List<Team> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<Team> obc) {

		return doFindByG_U(groupId, userId, start, end, obc);
	}

	@Override
	public List<Team> findByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> obc) {

		return doFindByG_N_D(
			groupId, name, description, params, start, end, obc, false);
	}

	protected int doCountByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean inlineSQLHelper) {

		name = CustomSQLUtil.keywords(name)[0];
		description = CustomSQLUtil.keywords(description)[0];

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Team.class.getName(), "Team.teamId", groupId);
			}

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

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

	protected List<Team> doFindByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<Team> obc) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Team", TeamImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(userId);

			return (List<Team>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Team> doFindByG_N_D(
		long groupId, String name, String description,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Team> obc, boolean inlineSQLHelper) {

		name = CustomSQLUtil.keywords(name)[0];
		description = CustomSQLUtil.keywords(description)[0];

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Team.class.getName(), "Team.teamId", groupId);
			}

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params));
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Team", TeamImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			return (List<Team>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (Validator.isNotNull(entry.getValue())) {
				sb.append(getJoin(entry.getKey()));
			}
		}

		return sb.toString();
	}

	protected String getJoin(String key) {
		String join = StringPool.BLANK;

		if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
		}
		else if (key.equals("usersTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_TEAMS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	protected String getWhere(LinkedHashMap<String, Object> params) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (Validator.isNotNull(entry.getValue())) {
				sb.append(getWhere(entry.getKey()));
			}
		}

		return sb.toString();
	}

	protected String getWhere(String key) {
		String join = StringPool.BLANK;

		if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
		}
		else if (key.equals("usersTeams")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_TEAMS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5);

				join = join.concat(" AND ");
			}
			else {
				join = StringPool.BLANK;
			}
		}

		return join;
	}

	protected void setJoin(
		QueryPos qPos, LinkedHashMap<String, Object> params) {

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof Long) {
				Long valueLong = (Long)value;

				if (Validator.isNotNull(valueLong)) {
					qPos.add(valueLong);
				}
			}
		}
	}

}