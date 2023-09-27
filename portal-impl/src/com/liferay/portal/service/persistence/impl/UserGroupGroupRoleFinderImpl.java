/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(roleType);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"UserGroupGroupRole", UserGroupGroupRoleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(userId);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}