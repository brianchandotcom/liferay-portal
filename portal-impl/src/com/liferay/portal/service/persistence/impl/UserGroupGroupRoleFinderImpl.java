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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRoleFinder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Norbert Kocsis
 * @author Drew Brokke
 */
public class UserGroupGroupRoleFinderImpl
	extends UserGroupGroupRoleFinderBaseImpl
	implements UserGroupGroupRoleFinder {

	public static final String FIND_BY_GROUP_ROLE_TYPE =
		UserGroupGroupRoleFinder.class.getName() + ".findByGroupRoleType";

	public static final String FIND_BY_USER_GROUPS_USERS =
		UserGroupGroupRoleFinder.class.getName() + ".findByUserGroupsUsers";

	@Override
	public List<UserGroupGroupRole> findByGroupRoleType(
		long groupId, int roleType) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_ROLE_TYPE);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(roleType);

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
	public List<UserGroupGroupRole> findByUserGroupsUsers(long userId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_USER_GROUPS_USERS);

			sql = StringUtil.removeSubstring(sql, "[$WHERE$]");

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

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
	public List<UserGroupGroupRole> findByUserGroupsUsers(
		long userId, long groupId) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_USER_GROUPS_USERS);

			sql = StringUtil.replace(
				sql, "[$WHERE$]", "(UserGroupGroupRole.groupId = ?) AND ");

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}