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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.model.impl.ResourceTypePermissionImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Connor McKay
 */
public class ResourceTypePermissionFinderImpl
	extends BasePersistenceImpl<ResourceTypePermission>
	implements ResourceTypePermissionFinder {

	public static final String FIND_BY_EITHER_SCOPE_C_G_N =
		ResourceTypePermissionFinder.class.getName() +
			".findByEitherScopeC_G_N";

	public static final String FIND_BY_GROUP_SCOPE_C_N_R =
		ResourceTypePermissionFinder.class.getName() + ".findByGroupScopeC_N_R";

	/**
	 * Returns all the resource type permissions with either scope that apply to
	 * resources of the type within the group. This method is used to find all
	 * the resource type permissions that apply to a newly created resource.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the group
	 * @param  name the fully qualified class name of the resource type
	 * @return all the resource type permissions that apply to resources of the
	 *         type within the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceTypePermission> findByEitherScopeC_G_N(
			long companyId, long groupId, String name)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_EITHER_SCOPE_C_G_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(
				"ResourceTypePermission", ResourceTypePermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(groupId);

			return (List<ResourceTypePermission>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all of the role's group scope resource type permissions that
	 * apply to resources of the type. This method is used during the process of
	 * adding a company scope resource type permissions to remove all the group
	 * scope permissions that would be overridden by it.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the fully qualified class name of the resource type
	 * @param  roleId the primary key of the role
	 * @return all of the role's group scope resource type permissions that
	 *         apply to resources of the type
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceTypePermission> findByGroupScopeC_N_R(
			long companyId, String name, long roleId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_SCOPE_C_N_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(
				"ResourceTypePermission", ResourceTypePermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(roleId);

			return (List<ResourceTypePermission>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}