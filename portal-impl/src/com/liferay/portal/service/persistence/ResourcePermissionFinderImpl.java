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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
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

	public static final FinderPath FINDER_PATH_HAS_PERMISSION =
		new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			ResourcePermissionPersistenceImpl.FINDER_CLASS_NAME_ENTITY,
			"hasPermission",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName(), Long.class.getName()
			}
		);

	public static String COUNT_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".countByR_S";

	public static String FIND_BY_RESOURCE =
		ResourcePermissionFinder.class.getName() + ".findByResource";

	public static String FIND_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".findByR_S";

	public static String FIND_BY_C_N_S =
		ResourcePermissionFinder.class.getName() + ".findByC_N_S";

	public static String HAS_PERMISSION =
		ResourcePermissionFinder.class.getName() + ".hasPermission";

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

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean hasPermission(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, long actionIdMask)
		throws SystemException {

		return hasPermission(
			companyId, name, scope, primKey, roleIds, actionIdMask, true);
	}

	public boolean hasPermission(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, long actionIdMask, boolean retrieveFromCache)
		throws SystemException {

		Object[] finderArgs = new Object[] {
			companyId, name, scope, primKey, roleIds, actionIdMask
		};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(
				FINDER_PATH_HAS_PERMISSION, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(HAS_PERMISSION);

				if (roleIds.length > 1) {
					StringBundler roleIdsOR = new StringBundler(
						(roleIds.length * 2) -1);

					for (int i = 0; i < roleIds.length; i++) {
						if (i > 0) {
							roleIdsOR.append(" OR ");
						}

						roleIdsOR.append("ResourcePermission.roleId = ?");
					}

					sql = StringUtil.replace(
						sql, "ResourcePermission.roleId = ?",
						roleIdsOR.toString());
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("ResourcePermission", ResourcePermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);
				qPos.add(name);
				qPos.add(scope);
				qPos.add(primKey);
				qPos.add(roleIds);
				qPos.add(actionIdMask);
				qPos.add(actionIdMask);

				List<ResourcePermission> list = q.list(false);

				Boolean hasPermission = Boolean.valueOf(!list.isEmpty());

				result = hasPermission;

				FinderCacheUtil.putResult(FINDER_PATH_HAS_PERMISSION,
					finderArgs, hasPermission);

				return hasPermission.booleanValue();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(
						FINDER_PATH_HAS_PERMISSION, finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
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