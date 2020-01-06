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
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the resource permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ResourcePermissionPersistenceImpl
	extends BasePersistenceImpl<ResourcePermission>
	implements ResourcePermissionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ResourcePermissionUtil</code> to access the resource permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ResourcePermissionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByName;
	private FinderPath _finderPathWithoutPaginationFindByName;
	private FinderPath _finderPathCountByName;

	/**
	 * Returns all the resource permissions where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByName(String name) {
		return findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByName(
		String name, int start, int end) {

		return findByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByName(
		String name, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByName(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByName(
		String name, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByName;
				finderArgs = new Object[] {name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByName;
			finderArgs = new Object[] {name, start, end, orderByComparator};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if (!name.equals(resourcePermission.getName())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByName_First(
			String name,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByName_First(
			name, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByName_First(
		String name, OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByName(
			name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByName_Last(
			String name,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByName_Last(
			name, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByName_Last(
		String name, OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByName(name);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByName(
			name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where name = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByName_PrevAndNext(
			long resourcePermissionId, String name,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		name = Objects.toString(name, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByName_PrevAndNext(
				session, resourcePermission, name, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByName_PrevAndNext(
				session, resourcePermission, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByName_PrevAndNext(
		Session session, ResourcePermission resourcePermission, String name,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_NAME_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_NAME_NAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName(String name) {
		for (ResourcePermission resourcePermission :
				findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByName(String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByName;

			finderArgs = new Object[] {name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"resourcePermission.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(resourcePermission.name IS NULL OR resourcePermission.name = '')";

	private FinderPath _finderPathWithPaginationFindByScope;
	private FinderPath _finderPathWithoutPaginationFindByScope;
	private FinderPath _finderPathCountByScope;
	private FinderPath _finderPathWithPaginationCountByScope;

	/**
	 * Returns all the resource permissions where scope = &#63;.
	 *
	 * @param scope the scope
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(int scope) {
		return findByScope(scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scope the scope
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(int scope, int start, int end) {
		return findByScope(scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scope the scope
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(
		int scope, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByScope(scope, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scope the scope
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(
		int scope, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByScope;
				finderArgs = new Object[] {scope};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByScope;
			finderArgs = new Object[] {scope, start, end, orderByComparator};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if (scope != resourcePermission.getScope()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_SCOPE_SCOPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(scope);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where scope = &#63;.
	 *
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByScope_First(
			int scope, OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByScope_First(
			scope, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("scope=");
		msg.append(scope);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where scope = &#63;.
	 *
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByScope_First(
		int scope, OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByScope(
			scope, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where scope = &#63;.
	 *
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByScope_Last(
			int scope, OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByScope_Last(
			scope, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("scope=");
		msg.append(scope);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where scope = &#63;.
	 *
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByScope_Last(
		int scope, OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByScope(scope);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByScope(
			scope, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where scope = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByScope_PrevAndNext(
			long resourcePermissionId, int scope,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByScope_PrevAndNext(
				session, resourcePermission, scope, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByScope_PrevAndNext(
				session, resourcePermission, scope, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByScope_PrevAndNext(
		Session session, ResourcePermission resourcePermission, int scope,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_SCOPE_SCOPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(scope);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the resource permissions where scope = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scopes the scopes
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(int[] scopes) {
		return findByScope(scopes, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where scope = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scopes the scopes
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(
		int[] scopes, int start, int end) {

		return findByScope(scopes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where scope = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scopes the scopes
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(
		int[] scopes, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByScope(scopes, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where scope = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param scope the scope
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByScope(
		int[] scopes, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		if (scopes == null) {
			scopes = new int[0];
		}
		else if (scopes.length > 1) {
			scopes = ArrayUtil.sortedUnique(scopes);
		}

		if (scopes.length == 1) {
			return findByScope(scopes[0], start, end, orderByComparator);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(scopes)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(scopes), start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByScope, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if (!ArrayUtil.contains(
							scopes, resourcePermission.getScope())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			if (scopes.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_SCOPE_SCOPE_7);

				query.append(StringUtil.merge(scopes));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByScope, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationFindByScope, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the resource permissions where scope = &#63; from the database.
	 *
	 * @param scope the scope
	 */
	@Override
	public void removeByScope(int scope) {
		for (ResourcePermission resourcePermission :
				findByScope(
					scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where scope = &#63;.
	 *
	 * @param scope the scope
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByScope(int scope) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByScope;

			finderArgs = new Object[] {scope};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_SCOPE_SCOPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(scope);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource permissions where scope = any &#63;.
	 *
	 * @param scopes the scopes
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByScope(int[] scopes) {
		if (scopes == null) {
			scopes = new int[0];
		}
		else if (scopes.length > 1) {
			scopes = ArrayUtil.sortedUnique(scopes);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(scopes)};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByScope, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			if (scopes.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_SCOPE_SCOPE_7);

				query.append(StringUtil.merge(scopes));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByScope, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationCountByScope, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SCOPE_SCOPE_2 =
		"resourcePermission.scope = ?";

	private static final String _FINDER_COLUMN_SCOPE_SCOPE_7 =
		"resourcePermission.scope IN (";

	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the resource permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByRoleId(
		long roleId, int start, int end) {

		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRoleId;
				finderArgs = new Object[] {roleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRoleId;
			finderArgs = new Object[] {roleId, start, end, orderByComparator};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if (roleId != resourcePermission.getRoleId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByRoleId_First(
			long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByRoleId_First(
			roleId, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByRoleId_First(
		long roleId, OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByRoleId(
			roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByRoleId_Last(
			long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByRoleId_Last(
			roleId, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByRoleId_Last(
		long roleId, OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByRoleId(
			roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByRoleId_PrevAndNext(
			long resourcePermissionId, long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByRoleId_PrevAndNext(
				session, resourcePermission, roleId, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByRoleId_PrevAndNext(
				session, resourcePermission, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByRoleId_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long roleId,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (ResourcePermission resourcePermission :
				findByRoleId(
					roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByRoleId(long roleId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRoleId;

			finderArgs = new Object[] {roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 =
		"resourcePermission.roleId = ?";

	private FinderPath _finderPathWithPaginationFindByC_LikeP;
	private FinderPath _finderPathWithPaginationCountByC_LikeP;

	/**
	 * Returns all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_LikeP(
		long companyId, String primKey) {

		return findByC_LikeP(
			companyId, primKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_LikeP(
		long companyId, String primKey, int start, int end) {

		return findByC_LikeP(companyId, primKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_LikeP(
		long companyId, String primKey, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_LikeP(
			companyId, primKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_LikeP(
		long companyId, String primKey, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LikeP;
		finderArgs = new Object[] {
			companyId, primKey, start, end, orderByComparator
		};

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							resourcePermission.getPrimKey(), primKey, '_', '%',
							'\\', true)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_LIKEP_COMPANYID_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_LikeP_First(
			long companyId, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_LikeP_First(
			companyId, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", primKeyLIKE");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_LikeP_First(
		long companyId, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByC_LikeP(
			companyId, primKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_LikeP_Last(
			long companyId, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_LikeP_Last(
			companyId, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", primKeyLIKE");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_LikeP_Last(
		long companyId, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByC_LikeP(companyId, primKey);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByC_LikeP(
			companyId, primKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByC_LikeP_PrevAndNext(
			long resourcePermissionId, long companyId, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		primKey = Objects.toString(primKey, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_LikeP_PrevAndNext(
				session, resourcePermission, companyId, primKey,
				orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_LikeP_PrevAndNext(
				session, resourcePermission, companyId, primKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_LikeP_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long companyId,
		String primKey, OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_LIKEP_COMPANYID_2);

		boolean bindPrimKey = false;

		if (primKey.isEmpty()) {
			query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_3);
		}
		else {
			bindPrimKey = true;

			query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindPrimKey) {
			qPos.add(primKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and primKey LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 */
	@Override
	public void removeByC_LikeP(long companyId, String primKey) {
		for (ResourcePermission resourcePermission :
				findByC_LikeP(
					companyId, primKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and primKey LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param primKey the prim key
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_LikeP(long companyId, String primKey) {
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_LikeP;

			finderArgs = new Object[] {companyId, primKey};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_LIKEP_COMPANYID_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_LIKEP_PRIMKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_LIKEP_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_LIKEP_PRIMKEY_2 =
		"resourcePermission.primKey LIKE ?";

	private static final String _FINDER_COLUMN_C_LIKEP_PRIMKEY_3 =
		"(resourcePermission.primKey IS NULL OR resourcePermission.primKey LIKE '')";

	private FinderPath _finderPathWithPaginationFindByC_S_P;
	private FinderPath _finderPathWithoutPaginationFindByC_S_P;
	private FinderPath _finderPathCountByC_S_P;

	/**
	 * Returns all the resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_S_P(
		long companyId, int scope, String primKey) {

		return findByC_S_P(
			companyId, scope, primKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_S_P(
		long companyId, int scope, String primKey, int start, int end) {

		return findByC_S_P(companyId, scope, primKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_S_P(
		long companyId, int scope, String primKey, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_S_P(
			companyId, scope, primKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_S_P(
		long companyId, int scope, String primKey, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_S_P;
				finderArgs = new Object[] {companyId, scope, primKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_S_P;
			finderArgs = new Object[] {
				companyId, scope, primKey, start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						(scope != resourcePermission.getScope()) ||
						!primKey.equals(resourcePermission.getPrimKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_S_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_P_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_S_P_First(
			long companyId, int scope, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_S_P_First(
			companyId, scope, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKey=");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_S_P_First(
		long companyId, int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByC_S_P(
			companyId, scope, primKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_S_P_Last(
			long companyId, int scope, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_S_P_Last(
			companyId, scope, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKey=");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_S_P_Last(
		long companyId, int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByC_S_P(companyId, scope, primKey);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByC_S_P(
			companyId, scope, primKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByC_S_P_PrevAndNext(
			long resourcePermissionId, long companyId, int scope,
			String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		primKey = Objects.toString(primKey, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_S_P_PrevAndNext(
				session, resourcePermission, companyId, scope, primKey,
				orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_S_P_PrevAndNext(
				session, resourcePermission, companyId, scope, primKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_S_P_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long companyId,
		int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_S_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_P_SCOPE_2);

		boolean bindPrimKey = false;

		if (primKey.isEmpty()) {
			query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_3);
		}
		else {
			bindPrimKey = true;

			query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(scope);

		if (bindPrimKey) {
			qPos.add(primKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 */
	@Override
	public void removeByC_S_P(long companyId, int scope, String primKey) {
		for (ResourcePermission resourcePermission :
				findByC_S_P(
					companyId, scope, primKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param primKey the prim key
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_S_P(long companyId, int scope, String primKey) {
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_S_P;

			finderArgs = new Object[] {companyId, scope, primKey};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_S_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_P_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_S_P_PRIMKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_P_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_P_SCOPE_2 =
		"resourcePermission.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_S_P_PRIMKEY_2 =
		"resourcePermission.primKey = ?";

	private static final String _FINDER_COLUMN_C_S_P_PRIMKEY_3 =
		"(resourcePermission.primKey IS NULL OR resourcePermission.primKey = '')";

	private FinderPath _finderPathWithPaginationFindByC_N_S_P;
	private FinderPath _finderPathWithoutPaginationFindByC_N_S_P;
	private FinderPath _finderPathCountByC_N_S_P;

	/**
	 * Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P(
		long companyId, String name, int scope, String primKey) {

		return findByC_N_S_P(
			companyId, name, scope, primKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P(
		long companyId, String name, int scope, String primKey, int start,
		int end) {

		return findByC_N_S_P(companyId, name, scope, primKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P(
		long companyId, String name, int scope, String primKey, int start,
		int end, OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_N_S_P(
			companyId, name, scope, primKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P(
		long companyId, String name, int scope, String primKey, int start,
		int end, OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_N_S_P;
				finderArgs = new Object[] {companyId, name, scope, primKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_N_S_P;
			finderArgs = new Object[] {
				companyId, name, scope, primKey, start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!name.equals(resourcePermission.getName()) ||
						(scope != resourcePermission.getScope()) ||
						!primKey.equals(resourcePermission.getPrimKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_P_First(
			long companyId, String name, int scope, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_P_First(
			companyId, name, scope, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKey=");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_First(
		long companyId, String name, int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByC_N_S_P(
			companyId, name, scope, primKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_P_Last(
			long companyId, String name, int scope, String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_P_Last(
			companyId, name, scope, primKey, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKey=");
		msg.append(primKey);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_Last(
		long companyId, String name, int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByC_N_S_P(companyId, name, scope, primKey);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByC_N_S_P(
			companyId, name, scope, primKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByC_N_S_P_PrevAndNext(
			long resourcePermissionId, long companyId, String name, int scope,
			String primKey,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_N_S_P_PrevAndNext(
				session, resourcePermission, companyId, name, scope, primKey,
				orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_N_S_P_PrevAndNext(
				session, resourcePermission, companyId, name, scope, primKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_N_S_P_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long companyId,
		String name, int scope, String primKey,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

		boolean bindPrimKey = false;

		if (primKey.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
		}
		else {
			bindPrimKey = true;

			query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(scope);

		if (bindPrimKey) {
			qPos.add(primKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 */
	@Override
	public void removeByC_N_S_P(
		long companyId, String name, int scope, String primKey) {

		for (ResourcePermission resourcePermission :
				findByC_N_S_P(
					companyId, name, scope, primKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_P(
		long companyId, String name, int scope, String primKey) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N_S_P;

			finderArgs = new Object[] {companyId, name, scope, primKey};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_S_P_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_NAME_2 =
		"resourcePermission.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_NAME_3 =
		"(resourcePermission.name IS NULL OR resourcePermission.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_SCOPE_2 =
		"resourcePermission.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_PRIMKEY_2 =
		"resourcePermission.primKey = ?";

	private static final String _FINDER_COLUMN_C_N_S_P_PRIMKEY_3 =
		"(resourcePermission.primKey IS NULL OR resourcePermission.primKey = '')";

	private FinderPath _finderPathWithPaginationFindByC_N_S_R;
	private FinderPath _finderPathWithoutPaginationFindByC_N_S_R;
	private FinderPath _finderPathCountByC_N_S_R;

	/**
	 * Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_R(
		long companyId, String name, int scope, long roleId) {

		return findByC_N_S_R(
			companyId, name, scope, roleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_R(
		long companyId, String name, int scope, long roleId, int start,
		int end) {

		return findByC_N_S_R(companyId, name, scope, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_R(
		long companyId, String name, int scope, long roleId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_N_S_R(
			companyId, name, scope, roleId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_R(
		long companyId, String name, int scope, long roleId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_N_S_R;
				finderArgs = new Object[] {companyId, name, scope, roleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_N_S_R;
			finderArgs = new Object[] {
				companyId, name, scope, roleId, start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!name.equals(resourcePermission.getName()) ||
						(scope != resourcePermission.getScope()) ||
						(roleId != resourcePermission.getRoleId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_R_SCOPE_2);

			query.append(_FINDER_COLUMN_C_N_S_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				qPos.add(roleId);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_R_First(
			long companyId, String name, int scope, long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_R_First(
			companyId, name, scope, roleId, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_R_First(
		long companyId, String name, int scope, long roleId,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByC_N_S_R(
			companyId, name, scope, roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_R_Last(
			long companyId, String name, int scope, long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_R_Last(
			companyId, name, scope, roleId, orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_R_Last(
		long companyId, String name, int scope, long roleId,
		OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByC_N_S_R(companyId, name, scope, roleId);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByC_N_S_R(
			companyId, name, scope, roleId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByC_N_S_R_PrevAndNext(
			long resourcePermissionId, long companyId, String name, int scope,
			long roleId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		name = Objects.toString(name, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_N_S_R_PrevAndNext(
				session, resourcePermission, companyId, name, scope, roleId,
				orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_N_S_R_PrevAndNext(
				session, resourcePermission, companyId, name, scope, roleId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_N_S_R_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long companyId,
		String name, int scope, long roleId,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_R_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_R_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_R_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_R_SCOPE_2);

		query.append(_FINDER_COLUMN_C_N_S_R_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(scope);

		qPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 */
	@Override
	public void removeByC_N_S_R(
		long companyId, String name, int scope, long roleId) {

		for (ResourcePermission resourcePermission :
				findByC_N_S_R(
					companyId, name, scope, roleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param roleId the role ID
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_R(
		long companyId, String name, int scope, long roleId) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N_S_R;

			finderArgs = new Object[] {companyId, name, scope, roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_R_SCOPE_2);

			query.append(_FINDER_COLUMN_C_N_S_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_S_R_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_R_NAME_2 =
		"resourcePermission.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_R_NAME_3 =
		"(resourcePermission.name IS NULL OR resourcePermission.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_S_R_SCOPE_2 =
		"resourcePermission.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_R_ROLEID_2 =
		"resourcePermission.roleId = ?";

	private FinderPath _finderPathWithPaginationFindByC_N_S_P_R;
	private FinderPath _finderPathWithoutPaginationFindByC_N_S_P_R;
	private FinderPath _finderPathFetchByC_N_S_P_R;
	private FinderPath _finderPathCountByC_N_S_P_R;
	private FinderPath _finderPathWithPaginationCountByC_N_S_P_R;

	/**
	 * Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleIds the role IDs
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R(
		long companyId, String name, int scope, String primKey,
		long[] roleIds) {

		return findByC_N_S_P_R(
			companyId, name, scope, primKey, roleIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleIds the role IDs
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long[] roleIds,
		int start, int end) {

		return findByC_N_S_P_R(
			companyId, name, scope, primKey, roleIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleIds the role IDs
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long[] roleIds,
		int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_N_S_P_R(
			companyId, name, scope, primKey, roleIds, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long[] roleIds,
		int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		if (roleIds == null) {
			roleIds = new long[0];
		}
		else if (roleIds.length > 1) {
			roleIds = ArrayUtil.sortedUnique(roleIds);
		}

		if (roleIds.length == 1) {
			ResourcePermission resourcePermission = fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleIds[0]);

			if (resourcePermission == null) {
				return Collections.emptyList();
			}
			else {
				return Collections.singletonList(resourcePermission);
			}
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					companyId, name, scope, primKey, StringUtil.merge(roleIds)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				companyId, name, scope, primKey, StringUtil.merge(roleIds),
				start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByC_N_S_P_R, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!name.equals(resourcePermission.getName()) ||
						(scope != resourcePermission.getScope()) ||
						!primKey.equals(resourcePermission.getPrimKey()) ||
						!ArrayUtil.contains(
							roleIds, resourcePermission.getRoleId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) &&
					(end == QueryUtil.ALL_POS) &&
					(databaseInMaxParameters > 0) &&
					(roleIds.length > databaseInMaxParameters)) {

					list = new ArrayList<ResourcePermission>();

					long[][] roleIdsPages = (long[][])ArrayUtil.split(
						roleIds, databaseInMaxParameters);

					for (long[] roleIdsPage : roleIdsPages) {
						list.addAll(
							_findByC_N_S_P_R(
								companyId, name, scope, primKey, roleIdsPage,
								start, end, orderByComparator));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByC_N_S_P_R(
						companyId, name, scope, primKey, roleIds, start, end,
						orderByComparator);
				}

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByC_N_S_P_R, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationFindByC_N_S_P_R, finderArgs);
				}

				throw processException(exception);
			}
		}

		return list;
	}

	private List<ResourcePermission> _findByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long[] roleIds,
		int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

		boolean bindPrimKey = false;

		if (primKey.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
		}
		else {
			bindPrimKey = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
		}

		if (roleIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_7);

			query.append(StringUtil.merge(roleIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (orderByComparator != null) {
			appendOrderByComparator(
				query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(scope);

			if (bindPrimKey) {
				qPos.add(primKey);
			}

			list = (List<ResourcePermission>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	/**
	 * Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a <code>NoSuchResourcePermissionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @return the matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_P_R(
			long companyId, String name, int scope, String primKey, long roleId)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_P_R(
			companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchResourcePermissionException(msg.toString());
		}

		return resourcePermission;
	}

	/**
	 * Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long roleId) {

		return fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId, true);
	}

	/**
	 * Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long roleId,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, name, scope, primKey, roleId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_N_S_P_R, finderArgs, this);
		}

		if (result instanceof ResourcePermission) {
			ResourcePermission resourcePermission = (ResourcePermission)result;

			if ((companyId != resourcePermission.getCompanyId()) ||
				!Objects.equals(name, resourcePermission.getName()) ||
				(scope != resourcePermission.getScope()) ||
				!Objects.equals(primKey, resourcePermission.getPrimKey()) ||
				(roleId != resourcePermission.getRoleId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				qPos.add(roleId);

				List<ResourcePermission> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_N_S_P_R, finderArgs, list);
					}
				}
				else {
					ResourcePermission resourcePermission = list.get(0);

					result = resourcePermission;

					cacheResult(resourcePermission);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByC_N_S_P_R, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ResourcePermission)result;
		}
	}

	/**
	 * Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @return the resource permission that was removed
	 */
	@Override
	public ResourcePermission removeByC_N_S_P_R(
			long companyId, String name, int scope, String primKey, long roleId)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = findByC_N_S_P_R(
			companyId, name, scope, primKey, roleId);

		return remove(resourcePermission);
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleId the role ID
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_P_R(
		long companyId, String name, int scope, String primKey, long roleId) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N_S_P_R;

			finderArgs = new Object[] {companyId, name, scope, primKey, roleId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

			boolean bindPrimKey = false;

			if (primKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
			}
			else {
				bindPrimKey = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (bindPrimKey) {
					qPos.add(primKey);
				}

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKey the prim key
	 * @param roleIds the role IDs
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_P_R(
		long companyId, String name, int scope, String primKey,
		long[] roleIds) {

		name = Objects.toString(name, "");
		primKey = Objects.toString(primKey, "");

		if (roleIds == null) {
			roleIds = new long[0];
		}
		else if (roleIds.length > 1) {
			roleIds = ArrayUtil.sortedUnique(roleIds);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				companyId, name, scope, primKey, StringUtil.merge(roleIds)
			};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByC_N_S_P_R, finderArgs, this);
		}

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
					(roleIds.length > databaseInMaxParameters)) {

					count = Long.valueOf(0);

					long[][] roleIdsPages = (long[][])ArrayUtil.split(
						roleIds, databaseInMaxParameters);

					for (long[] roleIdsPage : roleIdsPages) {
						count += Long.valueOf(
							_countByC_N_S_P_R(
								companyId, name, scope, primKey, roleIdsPage));
					}
				}
				else {
					count = Long.valueOf(
						_countByC_N_S_P_R(
							companyId, name, scope, primKey, roleIds));
				}

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByC_N_S_P_R, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationCountByC_N_S_P_R, finderArgs);
				}

				throw processException(exception);
			}
		}

		return count.intValue();
	}

	private int _countByC_N_S_P_R(
		long companyId, String name, int scope, String primKey,
		long[] roleIds) {

		Long count = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

		boolean bindPrimKey = false;

		if (primKey.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
		}
		else {
			bindPrimKey = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
		}

		if (roleIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_7);

			query.append(StringUtil.merge(roleIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(scope);

			if (bindPrimKey) {
				qPos.add(primKey);
			}

			count = (Long)q.uniqueResult();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_S_P_R_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_NAME_2 =
		"resourcePermission.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_NAME_3 =
		"(resourcePermission.name IS NULL OR resourcePermission.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_SCOPE_2 =
		"resourcePermission.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2 =
		"resourcePermission.primKey = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3 =
		"(resourcePermission.primKey IS NULL OR resourcePermission.primKey = '') AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_ROLEID_2 =
		"resourcePermission.roleId = ?";

	private static final String _FINDER_COLUMN_C_N_S_P_R_ROLEID_7 =
		"resourcePermission.roleId IN (";

	private FinderPath _finderPathWithPaginationFindByC_N_S_P_R_V;
	private FinderPath _finderPathWithoutPaginationFindByC_N_S_P_R_V;
	private FinderPath _finderPathCountByC_N_S_P_R_V;
	private FinderPath _finderPathWithPaginationCountByC_N_S_P_R_V;

	/**
	 * Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId, int start, int end) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_N_S_P_R_V;
				finderArgs = new Object[] {
					companyId, name, scope, primKeyId, roleId, viewActionId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_N_S_P_R_V;
			finderArgs = new Object[] {
				companyId, name, scope, primKeyId, roleId, viewActionId, start,
				end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!name.equals(resourcePermission.getName()) ||
						(scope != resourcePermission.getScope()) ||
						(primKeyId != resourcePermission.getPrimKeyId()) ||
						(roleId != resourcePermission.getRoleId()) ||
						(viewActionId != resourcePermission.isViewActionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					8 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(8);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_ROLEID_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				qPos.add(primKeyId);

				qPos.add(roleId);

				qPos.add(viewActionId);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_P_R_V_First(
			long companyId, String name, int scope, long primKeyId, long roleId,
			boolean viewActionId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_P_R_V_First(
			companyId, name, scope, primKeyId, roleId, viewActionId,
			orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(14);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKeyId=");
		msg.append(primKeyId);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append(", viewActionId=");
		msg.append(viewActionId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_R_V_First(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission
	 * @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission findByC_N_S_P_R_V_Last(
			long companyId, String name, int scope, long primKeyId, long roleId,
			boolean viewActionId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByC_N_S_P_R_V_Last(
			companyId, name, scope, primKeyId, roleId, viewActionId,
			orderByComparator);

		if (resourcePermission != null) {
			return resourcePermission;
		}

		StringBundler msg = new StringBundler(14);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", scope=");
		msg.append(scope);

		msg.append(", primKeyId=");
		msg.append(primKeyId);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append(", viewActionId=");
		msg.append(viewActionId);

		msg.append("}");

		throw new NoSuchResourcePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 */
	@Override
	public ResourcePermission fetchByC_N_S_P_R_V_Last(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		OrderByComparator<ResourcePermission> orderByComparator) {

		int count = countByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId);

		if (count == 0) {
			return null;
		}

		List<ResourcePermission> list = findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleId, viewActionId, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission[] findByC_N_S_P_R_V_PrevAndNext(
			long resourcePermissionId, long companyId, String name, int scope,
			long primKeyId, long roleId, boolean viewActionId,
			OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException {

		name = Objects.toString(name, "");

		ResourcePermission resourcePermission = findByPrimaryKey(
			resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_N_S_P_R_V_PrevAndNext(
				session, resourcePermission, companyId, name, scope, primKeyId,
				roleId, viewActionId, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_N_S_P_R_V_PrevAndNext(
				session, resourcePermission, companyId, name, scope, primKeyId,
				roleId, viewActionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_N_S_P_R_V_PrevAndNext(
		Session session, ResourcePermission resourcePermission, long companyId,
		String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(8);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_ROLEID_2);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(scope);

		qPos.add(primKeyId);

		qPos.add(roleId);

		qPos.add(viewActionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourcePermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleIds the role IDs
	 * @param viewActionId the view action ID
	 * @return the matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleIds, viewActionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleIds the role IDs
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId, int start, int end) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleIds, viewActionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleIds the role IDs
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findByC_N_S_P_R_V(
			companyId, name, scope, primKeyId, roleIds, viewActionId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource permissions
	 */
	@Override
	public List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		if (roleIds == null) {
			roleIds = new long[0];
		}
		else if (roleIds.length > 1) {
			roleIds = ArrayUtil.sortedUnique(roleIds);
		}

		if (roleIds.length == 1) {
			return findByC_N_S_P_R_V(
				companyId, name, scope, primKeyId, roleIds[0], viewActionId,
				start, end, orderByComparator);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					companyId, name, scope, primKeyId,
					StringUtil.merge(roleIds), viewActionId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				companyId, name, scope, primKeyId, StringUtil.merge(roleIds),
				viewActionId, start, end, orderByComparator
			};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByC_N_S_P_R_V, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourcePermission resourcePermission : list) {
					if ((companyId != resourcePermission.getCompanyId()) ||
						!name.equals(resourcePermission.getName()) ||
						(scope != resourcePermission.getScope()) ||
						(primKeyId != resourcePermission.getPrimKeyId()) ||
						!ArrayUtil.contains(
							roleIds, resourcePermission.getRoleId()) ||
						(viewActionId != resourcePermission.isViewActionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) &&
					(end == QueryUtil.ALL_POS) &&
					(databaseInMaxParameters > 0) &&
					(roleIds.length > databaseInMaxParameters)) {

					list = new ArrayList<ResourcePermission>();

					long[][] roleIdsPages = (long[][])ArrayUtil.split(
						roleIds, databaseInMaxParameters);

					for (long[] roleIdsPage : roleIdsPages) {
						list.addAll(
							_findByC_N_S_P_R_V(
								companyId, name, scope, primKeyId, roleIdsPage,
								viewActionId, start, end, orderByComparator));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByC_N_S_P_R_V(
						companyId, name, scope, primKeyId, roleIds,
						viewActionId, start, end, orderByComparator);
				}

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByC_N_S_P_R_V, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationFindByC_N_S_P_R_V, finderArgs);
				}

				throw processException(exception);
			}
		}

		return list;
	}

	private List<ResourcePermission> _findByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId, int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		List<ResourcePermission> list = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2);

		if (roleIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_ROLEID_7);

			query.append(StringUtil.merge(roleIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (orderByComparator != null) {
			appendOrderByComparator(
				query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
		}
		else {
			query.append(ResourcePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(scope);

			qPos.add(primKeyId);

			qPos.add(viewActionId);

			list = (List<ResourcePermission>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 */
	@Override
	public void removeByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId) {

		for (ResourcePermission resourcePermission :
				findByC_N_S_P_R_V(
					companyId, name, scope, primKeyId, roleId, viewActionId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleId the role ID
	 * @param viewActionId the view action ID
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long roleId,
		boolean viewActionId) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N_S_P_R_V;

			finderArgs = new Object[] {
				companyId, name, scope, primKeyId, roleId, viewActionId
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_ROLEID_2);

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(scope);

				qPos.add(primKeyId);

				qPos.add(roleId);

				qPos.add(viewActionId);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param scope the scope
	 * @param primKeyId the prim key ID
	 * @param roleIds the role IDs
	 * @param viewActionId the view action ID
	 * @return the number of matching resource permissions
	 */
	@Override
	public int countByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId) {

		name = Objects.toString(name, "");

		if (roleIds == null) {
			roleIds = new long[0];
		}
		else if (roleIds.length > 1) {
			roleIds = ArrayUtil.sortedUnique(roleIds);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				companyId, name, scope, primKeyId, StringUtil.merge(roleIds),
				viewActionId
			};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByC_N_S_P_R_V, finderArgs, this);
		}

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
					(roleIds.length > databaseInMaxParameters)) {

					count = Long.valueOf(0);

					long[][] roleIdsPages = (long[][])ArrayUtil.split(
						roleIds, databaseInMaxParameters);

					for (long[] roleIdsPage : roleIdsPages) {
						count += Long.valueOf(
							_countByC_N_S_P_R_V(
								companyId, name, scope, primKeyId, roleIdsPage,
								viewActionId));
					}
				}
				else {
					count = Long.valueOf(
						_countByC_N_S_P_R_V(
							companyId, name, scope, primKeyId, roleIds,
							viewActionId));
				}

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByC_N_S_P_R_V, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationCountByC_N_S_P_R_V,
						finderArgs);
				}

				throw processException(exception);
			}
		}

		return count.intValue();
	}

	private int _countByC_N_S_P_R_V(
		long companyId, String name, int scope, long primKeyId, long[] roleIds,
		boolean viewActionId) {

		Long count = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2);

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2);

		if (roleIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_N_S_P_R_V_ROLEID_7);

			query.append(StringUtil.merge(roleIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(scope);

			qPos.add(primKeyId);

			qPos.add(viewActionId);

			count = (Long)q.uniqueResult();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_COMPANYID_2 =
		"resourcePermission.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_NAME_2 =
		"resourcePermission.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_NAME_3 =
		"(resourcePermission.name IS NULL OR resourcePermission.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_SCOPE_2 =
		"resourcePermission.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_PRIMKEYID_2 =
		"resourcePermission.primKeyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_ROLEID_2 =
		"resourcePermission.roleId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_ROLEID_7 =
		"resourcePermission.roleId IN (";

	private static final String _FINDER_COLUMN_C_N_S_P_R_V_VIEWACTIONID_2 =
		"resourcePermission.viewActionId = ?";

	public ResourcePermissionPersistenceImpl() {
		setModelClass(ResourcePermission.class);

		setModelImplClass(ResourcePermissionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the resource permission in the entity cache if it is enabled.
	 *
	 * @param resourcePermission the resource permission
	 */
	@Override
	public void cacheResult(ResourcePermission resourcePermission) {
		if (resourcePermission.getCtCollectionId() != 0) {
			resourcePermission.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_N_S_P_R,
			new Object[] {
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId()
			},
			resourcePermission);

		resourcePermission.resetOriginalValues();
	}

	/**
	 * Caches the resource permissions in the entity cache if it is enabled.
	 *
	 * @param resourcePermissions the resource permissions
	 */
	@Override
	public void cacheResult(List<ResourcePermission> resourcePermissions) {
		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (resourcePermission.getCtCollectionId() != 0) {
				resourcePermission.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
					ResourcePermissionImpl.class,
					resourcePermission.getPrimaryKey()) == null) {

				cacheResult(resourcePermission);
			}
			else {
				resourcePermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all resource permissions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource permission.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourcePermission resourcePermission) {
		EntityCacheUtil.removeResult(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ResourcePermissionModelImpl)resourcePermission, true);
	}

	@Override
	public void clearCache(List<ResourcePermission> resourcePermissions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			EntityCacheUtil.removeResult(
				ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourcePermissionImpl.class,
				resourcePermission.getPrimaryKey());

			clearUniqueFindersCache(
				(ResourcePermissionModelImpl)resourcePermission, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourcePermissionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourcePermissionModelImpl resourcePermissionModelImpl) {

		Object[] args = new Object[] {
			resourcePermissionModelImpl.getCompanyId(),
			resourcePermissionModelImpl.getName(),
			resourcePermissionModelImpl.getScope(),
			resourcePermissionModelImpl.getPrimKey(),
			resourcePermissionModelImpl.getRoleId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_N_S_P_R, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_N_S_P_R, args, resourcePermissionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		ResourcePermissionModelImpl resourcePermissionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getName(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getPrimKey(),
				resourcePermissionModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N_S_P_R, args);
		}

		if ((resourcePermissionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N_S_P_R.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				resourcePermissionModelImpl.getOriginalCompanyId(),
				resourcePermissionModelImpl.getOriginalName(),
				resourcePermissionModelImpl.getOriginalScope(),
				resourcePermissionModelImpl.getOriginalPrimKey(),
				resourcePermissionModelImpl.getOriginalRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N_S_P_R, args);
		}
	}

	/**
	 * Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	 *
	 * @param resourcePermissionId the primary key for the new resource permission
	 * @return the new resource permission
	 */
	@Override
	public ResourcePermission create(long resourcePermissionId) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setNew(true);
		resourcePermission.setPrimaryKey(resourcePermissionId);

		resourcePermission.setCompanyId(CompanyThreadLocal.getCompanyId());

		return resourcePermission;
	}

	/**
	 * Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePermissionId the primary key of the resource permission
	 * @return the resource permission that was removed
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission remove(long resourcePermissionId)
		throws NoSuchResourcePermissionException {

		return remove((Serializable)resourcePermissionId);
	}

	/**
	 * Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource permission
	 * @return the resource permission that was removed
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission remove(Serializable primaryKey)
		throws NoSuchResourcePermissionException {

		Session session = null;

		try {
			session = openSession();

			ResourcePermission resourcePermission =
				(ResourcePermission)session.get(
					ResourcePermissionImpl.class, primaryKey);

			if (resourcePermission == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourcePermissionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(resourcePermission);
		}
		catch (NoSuchResourcePermissionException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ResourcePermission removeImpl(
		ResourcePermission resourcePermission) {

		if (!CTPersistenceHelperUtil.isRemove(resourcePermission)) {
			return resourcePermission;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourcePermission)) {
				resourcePermission = (ResourcePermission)session.get(
					ResourcePermissionImpl.class,
					resourcePermission.getPrimaryKeyObj());
			}

			if (resourcePermission != null) {
				session.delete(resourcePermission);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (resourcePermission != null) {
			clearCache(resourcePermission);
		}

		return resourcePermission;
	}

	@Override
	public ResourcePermission updateImpl(
		ResourcePermission resourcePermission) {

		boolean isNew = resourcePermission.isNew();

		if (!(resourcePermission instanceof ResourcePermissionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(resourcePermission.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					resourcePermission);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in resourcePermission proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ResourcePermission implementation " +
					resourcePermission.getClass());
		}

		ResourcePermissionModelImpl resourcePermissionModelImpl =
			(ResourcePermissionModelImpl)resourcePermission;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(resourcePermission)) {
				if (!isNew) {
					ResourcePermission oldResourcePermission =
						(ResourcePermission)session.get(
							ResourcePermissionImpl.class,
							resourcePermission.getPrimaryKeyObj());

					if (oldResourcePermission != null) {
						session.evict(oldResourcePermission);
					}
				}

				session.save(resourcePermission);

				resourcePermission.setNew(false);
			}
			else {
				resourcePermission = (ResourcePermission)session.merge(
					resourcePermission);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (resourcePermission.getCtCollectionId() != 0) {
			resourcePermission.resetOriginalValues();

			return resourcePermission;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ResourcePermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				resourcePermissionModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByName, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByName, args);

			args = new Object[] {resourcePermissionModelImpl.getScope()};

			FinderCacheUtil.removeResult(_finderPathCountByScope, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByScope, args);

			args = new Object[] {resourcePermissionModelImpl.getRoleId()};

			FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByRoleId, args);

			args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getPrimKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_S_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_S_P, args);

			args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getName(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getPrimKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_N_S_P, args);

			args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getName(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_R, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_N_S_R, args);

			args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getName(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getPrimKey(),
				resourcePermissionModelImpl.getRoleId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_N_S_P_R, args);

			args = new Object[] {
				resourcePermissionModelImpl.getCompanyId(),
				resourcePermissionModelImpl.getName(),
				resourcePermissionModelImpl.getScope(),
				resourcePermissionModelImpl.getPrimKeyId(),
				resourcePermissionModelImpl.getRoleId(),
				resourcePermissionModelImpl.isViewActionId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R_V, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_N_S_P_R_V, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByName.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByName, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByName, args);

				args = new Object[] {resourcePermissionModelImpl.getName()};

				FinderCacheUtil.removeResult(_finderPathCountByName, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByName, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByScope.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalScope()
				};

				FinderCacheUtil.removeResult(_finderPathCountByScope, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByScope, args);

				args = new Object[] {resourcePermissionModelImpl.getScope()};

				FinderCacheUtil.removeResult(_finderPathCountByScope, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByScope, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);

				args = new Object[] {resourcePermissionModelImpl.getRoleId()};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRoleId, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalCompanyId(),
					resourcePermissionModelImpl.getOriginalScope(),
					resourcePermissionModelImpl.getOriginalPrimKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_S_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_S_P, args);

				args = new Object[] {
					resourcePermissionModelImpl.getCompanyId(),
					resourcePermissionModelImpl.getScope(),
					resourcePermissionModelImpl.getPrimKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_S_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_S_P, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_N_S_P.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalCompanyId(),
					resourcePermissionModelImpl.getOriginalName(),
					resourcePermissionModelImpl.getOriginalScope(),
					resourcePermissionModelImpl.getOriginalPrimKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P, args);

				args = new Object[] {
					resourcePermissionModelImpl.getCompanyId(),
					resourcePermissionModelImpl.getName(),
					resourcePermissionModelImpl.getScope(),
					resourcePermissionModelImpl.getPrimKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_N_S_R.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalCompanyId(),
					resourcePermissionModelImpl.getOriginalName(),
					resourcePermissionModelImpl.getOriginalScope(),
					resourcePermissionModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_R, args);

				args = new Object[] {
					resourcePermissionModelImpl.getCompanyId(),
					resourcePermissionModelImpl.getName(),
					resourcePermissionModelImpl.getScope(),
					resourcePermissionModelImpl.getRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_R, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_N_S_P_R.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalCompanyId(),
					resourcePermissionModelImpl.getOriginalName(),
					resourcePermissionModelImpl.getOriginalScope(),
					resourcePermissionModelImpl.getOriginalPrimKey(),
					resourcePermissionModelImpl.getOriginalRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P_R, args);

				args = new Object[] {
					resourcePermissionModelImpl.getCompanyId(),
					resourcePermissionModelImpl.getName(),
					resourcePermissionModelImpl.getScope(),
					resourcePermissionModelImpl.getPrimKey(),
					resourcePermissionModelImpl.getRoleId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_S_P_R, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P_R, args);
			}

			if ((resourcePermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_N_S_P_R_V.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					resourcePermissionModelImpl.getOriginalCompanyId(),
					resourcePermissionModelImpl.getOriginalName(),
					resourcePermissionModelImpl.getOriginalScope(),
					resourcePermissionModelImpl.getOriginalPrimKeyId(),
					resourcePermissionModelImpl.getOriginalRoleId(),
					resourcePermissionModelImpl.getOriginalViewActionId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_N_S_P_R_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P_R_V, args);

				args = new Object[] {
					resourcePermissionModelImpl.getCompanyId(),
					resourcePermissionModelImpl.getName(),
					resourcePermissionModelImpl.getScope(),
					resourcePermissionModelImpl.getPrimKeyId(),
					resourcePermissionModelImpl.getRoleId(),
					resourcePermissionModelImpl.isViewActionId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_N_S_P_R_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_N_S_P_R_V, args);
			}
		}

		EntityCacheUtil.putResult(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission, false);

		clearUniqueFindersCache(resourcePermissionModelImpl, false);
		cacheUniqueFindersCache(resourcePermissionModelImpl);

		resourcePermission.resetOriginalValues();

		return resourcePermission;
	}

	/**
	 * Returns the resource permission with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource permission
	 * @return the resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourcePermissionException {

		ResourcePermission resourcePermission = fetchByPrimaryKey(primaryKey);

		if (resourcePermission == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourcePermissionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return resourcePermission;
	}

	/**
	 * Returns the resource permission with the primary key or throws a <code>NoSuchResourcePermissionException</code> if it could not be found.
	 *
	 * @param resourcePermissionId the primary key of the resource permission
	 * @return the resource permission
	 * @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission findByPrimaryKey(long resourcePermissionId)
		throws NoSuchResourcePermissionException {

		return findByPrimaryKey((Serializable)resourcePermissionId);
	}

	/**
	 * Returns the resource permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource permission
	 * @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				ResourcePermission.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		ResourcePermission resourcePermission = null;

		Session session = null;

		try {
			session = openSession();

			resourcePermission = (ResourcePermission)session.get(
				ResourcePermissionImpl.class, primaryKey);

			if (resourcePermission != null) {
				cacheResult(resourcePermission);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return resourcePermission;
	}

	/**
	 * Returns the resource permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePermissionId the primary key of the resource permission
	 * @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	 */
	@Override
	public ResourcePermission fetchByPrimaryKey(long resourcePermissionId) {
		return fetchByPrimaryKey((Serializable)resourcePermissionId);
	}

	@Override
	public Map<Serializable, ResourcePermission> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				ResourcePermission.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ResourcePermission> map =
			new HashMap<Serializable, ResourcePermission>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ResourcePermission resourcePermission = fetchByPrimaryKey(
				primaryKey);

			if (resourcePermission != null) {
				map.put(primaryKey, resourcePermission);
			}

			return map;
		}

		StringBundler query = new StringBundler(primaryKeys.size() * 2 + 1);

		query.append(getSelectSQL());
		query.append(" WHERE ");
		query.append(getPKDBName());
		query.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ResourcePermission resourcePermission :
					(List<ResourcePermission>)q.list()) {

				map.put(
					resourcePermission.getPrimaryKeyObj(), resourcePermission);

				cacheResult(resourcePermission);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the resource permissions.
	 *
	 * @return the resource permissions
	 */
	@Override
	public List<ResourcePermission> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @return the range of resource permissions
	 */
	@Override
	public List<ResourcePermission> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource permissions
	 */
	@Override
	public List<ResourcePermission> findAll(
		int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourcePermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource permissions
	 * @param end the upper bound of the range of resource permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of resource permissions
	 */
	@Override
	public List<ResourcePermission> findAll(
		int start, int end,
		OrderByComparator<ResourcePermission> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ResourcePermission> list = null;

		if (useFinderCache && productionMode) {
			list = (List<ResourcePermission>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_RESOURCEPERMISSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEPERMISSION;

				sql = sql.concat(ResourcePermissionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<ResourcePermission>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache && productionMode) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the resource permissions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ResourcePermission resourcePermission : findAll()) {
			remove(resourcePermission);
		}
	}

	/**
	 * Returns the number of resource permissions.
	 *
	 * @return the number of resource permissions
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			ResourcePermission.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEPERMISSION);

				count = (Long)q.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				if (productionMode) {
					FinderCacheUtil.removeResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "resourcePermissionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RESOURCEPERMISSION;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return ResourcePermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "ResourcePermission";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("scope");
		ctStrictColumnNames.add("primKey");
		ctStrictColumnNames.add("primKeyId");
		ctStrictColumnNames.add("roleId");
		ctStrictColumnNames.add("ownerId");
		ctStrictColumnNames.add("actionIds");
		ctStrictColumnNames.add("viewActionId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("resourcePermissionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"companyId", "name", "scope", "primKey", "roleId"});
	}

	/**
	 * Initializes the resource permission persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByName = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByName = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] {String.class.getName()},
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByName = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByScope = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByScope",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByScope = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByScope",
			new String[] {Integer.class.getName()},
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK);

		_finderPathCountByScope = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByScope",
			new String[] {Integer.class.getName()});

		_finderPathWithPaginationCountByScope = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByScope",
			new String[] {Integer.class.getName()});

		_finderPathWithPaginationFindByRoleId = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] {Long.class.getName()},
			ResourcePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRoleId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_LikeP = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeP",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_LikeP = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeP",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.PRIMKEY_COLUMN_BITMASK);

		_finderPathCountByC_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByC_N_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_N_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.PRIMKEY_COLUMN_BITMASK);

		_finderPathCountByC_N_S_P = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName()
			});

		_finderPathWithPaginationFindByC_N_S_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_S_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_N_S_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_S_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByC_N_S_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_S_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_N_S_P_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_N_S_P_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.PRIMKEY_COLUMN_BITMASK |
			ResourcePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathFetchByC_N_S_P_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.PRIMKEY_COLUMN_BITMASK |
			ResourcePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByC_N_S_P_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationCountByC_N_S_P_R = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByC_N_S_P_R_V = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_S_P_R_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_N_S_P_R_V = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourcePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_S_P_R_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			ResourcePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
			ResourcePermissionModelImpl.SCOPE_COLUMN_BITMASK |
			ResourcePermissionModelImpl.PRIMKEYID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.ROLEID_COLUMN_BITMASK |
			ResourcePermissionModelImpl.VIEWACTIONID_COLUMN_BITMASK);

		_finderPathCountByC_N_S_P_R_V = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_S_P_R_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationCountByC_N_S_P_R_V = new FinderPath(
			ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_N_S_P_R_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourcePermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RESOURCEPERMISSION =
		"SELECT resourcePermission FROM ResourcePermission resourcePermission";

	private static final String _SQL_SELECT_RESOURCEPERMISSION_WHERE =
		"SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ";

	private static final String _SQL_COUNT_RESOURCEPERMISSION =
		"SELECT COUNT(resourcePermission) FROM ResourcePermission resourcePermission";

	private static final String _SQL_COUNT_RESOURCEPERMISSION_WHERE =
		"SELECT COUNT(resourcePermission) FROM ResourcePermission resourcePermission WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "resourcePermission.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ResourcePermission exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ResourcePermission exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionPersistenceImpl.class);

}