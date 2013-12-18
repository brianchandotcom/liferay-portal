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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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

	public static final String COUNT_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".countByR_S";

	public static final String COUNT_BY_C_N_S_P_R_A =
		ResourcePermissionFinder.class.getName() + ".countByC_N_S_P_R_A";

	public static final String FIND_BY_RESOURCE =
		ResourcePermissionFinder.class.getName() + ".findByResource";

	public static final String FIND_BY_R_S =
		ResourcePermissionFinder.class.getName() + ".findByR_S";

	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S_P_R_A =
		new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			ResourcePermissionPersistenceImpl.
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_N_S_P_R_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName(), Long.class.getName()
			});

	@Override
	public int countByR_S(long roleId, int[] scopes) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_R_S);

			sql = StringUtil.replace(sql, "[$SCOPE$]", getScopes(scopes));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);
			qPos.add(scopes);

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
	public int countByC_N_S_P_R_A(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, long actionId)
		throws SystemException {

		Object[] finderArgs = new Object[] {
			companyId, name, scope, primKey, roleIds, actionId
		};

		Long count = (Long)FinderCacheUtil.getResult(
			FINDER_PATH_COUNT_BY_C_N_S_P_R_A, finderArgs, this);

		if (count != null) {
			return count.intValue();
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_S_P_R_A);

			if (roleIds.length > 1) {
				StringBundler sb = new StringBundler(roleIds.length * 2 - 1);

				for (int i = 0; i < roleIds.length; i++) {
					if (i > 0) {
						sb.append(" OR ");
					}

					sb.append("ResourcePermission.roleId = ?");
				}

				sql = StringUtil.replace(
					sql, "ResourcePermission.roleId = ?", sb.toString());
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(scope);
			qPos.add(primKey);
			qPos.add(roleIds);
			qPos.add(actionId);
			qPos.add(actionId);

			count = (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			if (count == null) {
				count = Long.valueOf(0);
			}

			FinderCacheUtil.putResult(
				FINDER_PATH_COUNT_BY_C_N_S_P_R_A, finderArgs, count);

			closeSession(session);
		}

		return count.intValue();
	}

	@Override
	public List<ResourcePermission> findByResource(
			long companyId, long groupId, String name, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_RESOURCE);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("ResourcePermission", ResourcePermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(primKey);
			qPos.add(String.valueOf(groupId));

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

	@Override
	public List<ResourcePermission> findByR_S(
			long roleId, int[] scopes, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_R_S);

			sql = StringUtil.replace(sql, "[$SCOPE$]", getScopes(scopes));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

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

	protected String getScopes(int[] scopes) {
		if (scopes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(scopes.length * 2 + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < scopes.length; i++) {
			sb.append("ResourcePermission.scope = ? ");

			if ((i + 1) != scopes.length) {
				sb.append("OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

}