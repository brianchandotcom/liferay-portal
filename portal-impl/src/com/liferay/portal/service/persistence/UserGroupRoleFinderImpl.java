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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.UserGroupRoleImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleFinderImpl
	extends BasePersistenceImpl<UserGroupRole> implements UserGroupRoleFinder {

	public static final String FIND_BY_USER_USER_GROUP_GROUP_ROLE =
		UserGroupRoleFinder.class.getName() + ".findByUserUserGroupGroupRole";

	@Override
	public List<UserGroupRole> findByUserUserGroupGroupRole(
			long userId, long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_USER_USER_GROUP_GROUP_ROLE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("UserGroupRole", UserGroupRoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(groupId);

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