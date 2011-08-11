/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourcePermissionFinderImpl
	extends BasePersistenceImpl<ResourcePermission>
	implements ResourcePermissionFinder {

	public static String COUNT_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".countByR_S";

	public static String FIND_BY_RESOURCE =
		ResourcePermissionFinder.class.getName() + ".findByResource";

	public static String FIND_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".findByR_S";

	public static String FIND_BY_C_N_S =
		ResourcePermissionFinder.class.getName() + ".findByC_N_S";

	public int countByR_S(long roleId, int[] scopes) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_R_S);

			sql = StringUtil.replace(sql, "[$SCOPE$]", getScopes(scopes));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);
			qPos.add(scopes);

			Iterator<Long> itr = q.list().iterator();

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

	public List<ResourcePermission> findByResource(
			long companyId, long groupId, String name, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_RESOURCE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ResourcePermission", ResourcePermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(primKey);
			qPos.add(groupId);

			return (List<ResourcePermission>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ResourcePermission> findByR_S(
			long roleId, int[] scopes, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_R_S);

			sql = StringUtil.replace(sql, "[$SCOPE$]", getScopes(scopes));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ResourcePermission", ResourcePermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);
			qPos.add(scopes);

			return (List<ResourcePermission>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<String> findByC_N_S(long companyId, String name, int scope)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_S);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("primKey", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(scope);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getScopes(int[] scopes) {
		StringBuilder sb = new StringBuilder();

		if (scopes.length > 0) {
			sb.append("(");
		}

		for (int i = 0; i < scopes.length; i++) {
			sb.append("scope = ? ");

			if ((i + 1) != scopes.length) {
				sb.append("OR ");
			}
		}

		if (scopes.length > 0) {
			sb.append(")");
		}

		return sb.toString();
	}

}