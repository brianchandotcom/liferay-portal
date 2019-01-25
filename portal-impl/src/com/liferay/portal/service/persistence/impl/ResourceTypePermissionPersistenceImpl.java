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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchResourceTypePermissionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.ResourceTypePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.ResourceTypePermissionImpl;
import com.liferay.portal.model.impl.ResourceTypePermissionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The persistence implementation for the resource type permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionPersistence
 * @see com.liferay.portal.kernel.service.persistence.ResourceTypePermissionUtil
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceTypePermissionPersistenceImpl extends BasePersistenceImpl<ResourceTypePermission>
	implements ResourceTypePermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceTypePermissionUtil} to access the resource type permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceTypePermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the resource type permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByRoleId(long roleId, int start,
		int end) {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator<ResourceTypePermission> orderByComparator) {
		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByRoleId;
			finderArgs = new Object[] { roleId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByRoleId;
			finderArgs = new Object[] { roleId, start, end, orderByComparator };
		}

		List<ResourceTypePermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceTypePermission resourceTypePermission : list) {
					if ((roleId != resourceTypePermission.getRoleId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceTypePermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				if (!pagination) {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource type permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission
	 * @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission findByRoleId_First(long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByRoleId_First(roleId,
				orderByComparator);

		if (resourceTypePermission != null) {
			return resourceTypePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceTypePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource type permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByRoleId_First(long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		List<ResourceTypePermission> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource type permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission
	 * @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission findByRoleId_Last(long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByRoleId_Last(roleId,
				orderByComparator);

		if (resourceTypePermission != null) {
			return resourceTypePermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceTypePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource type permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByRoleId_Last(long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<ResourceTypePermission> list = findByRoleId(roleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource type permissions before and after the current resource type permission in the ordered set where roleId = &#63;.
	 *
	 * @param resourceTypePermissionId the primary key of the current resource type permission
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource type permission
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission[] findByRoleId_PrevAndNext(
		long resourceTypePermissionId, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = findByPrimaryKey(resourceTypePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourceTypePermission[] array = new ResourceTypePermissionImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, resourceTypePermission,
					roleId, orderByComparator, true);

			array[1] = resourceTypePermission;

			array[2] = getByRoleId_PrevAndNext(session, resourceTypePermission,
					roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceTypePermission getByRoleId_PrevAndNext(Session session,
		ResourceTypePermission resourceTypePermission, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(ResourceTypePermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceTypePermission)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<ResourceTypePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource type permissions where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (ResourceTypePermission resourceTypePermission : findByRoleId(
				roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceTypePermission);
		}
	}

	/**
	 * Returns the number of resource type permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 */
	@Override
	public int countByRoleId(long roleId) {
		FinderPath finderPath = _finderPathCountByRoleId;

		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "resourceTypePermission.roleId = ?";
	private FinderPath _finderPathWithPaginationFindByC_N_R;
	private FinderPath _finderPathWithoutPaginationFindByC_N_R;
	private FinderPath _finderPathCountByC_N_R;

	/**
	 * Returns all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId) {
		return findByC_N_R(companyId, name, roleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end) {
		return findByC_N_R(companyId, name, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		return findByC_N_R(companyId, name, roleId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end,
		OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_N_R;
			finderArgs = new Object[] { companyId, name, roleId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_N_R;
			finderArgs = new Object[] {
					companyId, name, roleId,
					
					start, end, orderByComparator
				};
		}

		List<ResourceTypePermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceTypePermission resourceTypePermission : list) {
					if ((companyId != resourceTypePermission.getCompanyId()) ||
							!name.equals(resourceTypePermission.getName()) ||
							(roleId != resourceTypePermission.getRoleId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceTypePermissionModelImpl.ORDER_BY_JPQL);
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

				qPos.add(roleId);

				if (!pagination) {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission
	 * @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission findByC_N_R_First(long companyId,
		String name, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByC_N_R_First(companyId,
				name, roleId, orderByComparator);

		if (resourceTypePermission != null) {
			return resourceTypePermission;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceTypePermissionException(msg.toString());
	}

	/**
	 * Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByC_N_R_First(long companyId,
		String name, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		List<ResourceTypePermission> list = findByC_N_R(companyId, name,
				roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission
	 * @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission findByC_N_R_Last(long companyId, String name,
		long roleId, OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByC_N_R_Last(companyId,
				name, roleId, orderByComparator);

		if (resourceTypePermission != null) {
			return resourceTypePermission;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceTypePermissionException(msg.toString());
	}

	/**
	 * Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByC_N_R_Last(long companyId,
		String name, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		int count = countByC_N_R(companyId, name, roleId);

		if (count == 0) {
			return null;
		}

		List<ResourceTypePermission> list = findByC_N_R(companyId, name,
				roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource type permissions before and after the current resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param resourceTypePermissionId the primary key of the current resource type permission
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource type permission
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission[] findByC_N_R_PrevAndNext(
		long resourceTypePermissionId, long companyId, String name,
		long roleId, OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
		name = Objects.toString(name, "");

		ResourceTypePermission resourceTypePermission = findByPrimaryKey(resourceTypePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourceTypePermission[] array = new ResourceTypePermissionImpl[3];

			array[0] = getByC_N_R_PrevAndNext(session, resourceTypePermission,
					companyId, name, roleId, orderByComparator, true);

			array[1] = resourceTypePermission;

			array[2] = getByC_N_R_PrevAndNext(session, resourceTypePermission,
					companyId, name, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceTypePermission getByC_N_R_PrevAndNext(Session session,
		ResourceTypePermission resourceTypePermission, long companyId,
		String name, long roleId,
		OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_R_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_R_NAME_2);
		}

		query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(ResourceTypePermissionModelImpl.ORDER_BY_JPQL);
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

		qPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceTypePermission)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<ResourceTypePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 */
	@Override
	public void removeByC_N_R(long companyId, String name, long roleId) {
		for (ResourceTypePermission resourceTypePermission : findByC_N_R(
				companyId, name, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(resourceTypePermission);
		}
	}

	/**
	 * Returns the number of resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 */
	@Override
	public int countByC_N_R(long companyId, String name, long roleId) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N_R;

		Object[] finderArgs = new Object[] { companyId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

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

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_R_COMPANYID_2 = "resourceTypePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = '') AND ";
	private static final String _FINDER_COLUMN_C_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";
	private FinderPath _finderPathFetchByC_G_N_R;
	private FinderPath _finderPathCountByC_G_N_R;

	/**
	 * Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or throws a {@link NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permission
	 * @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission findByC_G_N_R(long companyId, long groupId,
		String name, long roleId) throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByC_G_N_R(companyId,
				groupId, name, roleId);

		if (resourceTypePermission == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchResourceTypePermissionException(msg.toString());
		}

		return resourceTypePermission;
	}

	/**
	 * Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId) {
		return fetchByC_G_N_R(companyId, groupId, name, roleId, true);
	}

	/**
	 * Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId, boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByC_G_N_R,
					finderArgs, this);
		}

		if (result instanceof ResourceTypePermission) {
			ResourceTypePermission resourceTypePermission = (ResourceTypePermission)result;

			if ((companyId != resourceTypePermission.getCompanyId()) ||
					(groupId != resourceTypePermission.getGroupId()) ||
					!Objects.equals(name, resourceTypePermission.getName()) ||
					(roleId != resourceTypePermission.getRoleId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_R_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_G_N_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(roleId);

				List<ResourceTypePermission> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByC_G_N_R,
						finderArgs, list);
				}
				else {
					ResourceTypePermission resourceTypePermission = list.get(0);

					result = resourceTypePermission;

					cacheResult(resourceTypePermission);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_R,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ResourceTypePermission)result;
		}
	}

	/**
	 * Removes the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the resource type permission that was removed
	 */
	@Override
	public ResourceTypePermission removeByC_G_N_R(long companyId, long groupId,
		String name, long roleId) throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = findByC_G_N_R(companyId,
				groupId, name, roleId);

		return remove(resourceTypePermission);
	}

	/**
	 * Returns the number of resource type permissions where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 */
	@Override
	public int countByC_G_N_R(long companyId, long groupId, String name,
		long roleId) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_G_N_R;

		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_R_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_G_N_R_NAME_2);
			}

			query.append(_FINDER_COLUMN_C_G_N_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(roleId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_G_N_R_COMPANYID_2 = "resourceTypePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_GROUPID_2 = "resourceTypePermission.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = '') AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";

	public ResourceTypePermissionPersistenceImpl() {
		setModelClass(ResourceTypePermission.class);

		setModelImplClass(ResourceTypePermissionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the resource type permission in the entity cache if it is enabled.
	 *
	 * @param resourceTypePermission the resource type permission
	 */
	@Override
	public void cacheResult(ResourceTypePermission resourceTypePermission) {
		EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey(), resourceTypePermission);

		FinderCacheUtil.putResult(_finderPathFetchByC_G_N_R,
			new Object[] {
				resourceTypePermission.getCompanyId(),
				resourceTypePermission.getGroupId(),
				resourceTypePermission.getName(),
				resourceTypePermission.getRoleId()
			}, resourceTypePermission);

		resourceTypePermission.resetOriginalValues();
	}

	/**
	 * Caches the resource type permissions in the entity cache if it is enabled.
	 *
	 * @param resourceTypePermissions the resource type permissions
	 */
	@Override
	public void cacheResult(
		List<ResourceTypePermission> resourceTypePermissions) {
		for (ResourceTypePermission resourceTypePermission : resourceTypePermissions) {
			if (EntityCacheUtil.getResult(
						ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceTypePermissionImpl.class,
						resourceTypePermission.getPrimaryKey()) == null) {
				cacheResult(resourceTypePermission);
			}
			else {
				resourceTypePermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all resource type permissions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ResourceTypePermissionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource type permission.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceTypePermission resourceTypePermission) {
		EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ResourceTypePermissionModelImpl)resourceTypePermission,
			true);
	}

	@Override
	public void clearCache(List<ResourceTypePermission> resourceTypePermissions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceTypePermission resourceTypePermission : resourceTypePermissions) {
			EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				resourceTypePermission.getPrimaryKey());

			clearUniqueFindersCache((ResourceTypePermissionModelImpl)resourceTypePermission,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl) {
		Object[] args = new Object[] {
				resourceTypePermissionModelImpl.getCompanyId(),
				resourceTypePermissionModelImpl.getGroupId(),
				resourceTypePermissionModelImpl.getName(),
				resourceTypePermissionModelImpl.getRoleId()
			};

		FinderCacheUtil.putResult(_finderPathCountByC_G_N_R, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByC_G_N_R, args,
			resourceTypePermissionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					resourceTypePermissionModelImpl.getCompanyId(),
					resourceTypePermissionModelImpl.getGroupId(),
					resourceTypePermissionModelImpl.getName(),
					resourceTypePermissionModelImpl.getRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_G_N_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_R, args);
		}

		if ((resourceTypePermissionModelImpl.getColumnBitmask() &
				_finderPathFetchByC_G_N_R.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					resourceTypePermissionModelImpl.getOriginalCompanyId(),
					resourceTypePermissionModelImpl.getOriginalGroupId(),
					resourceTypePermissionModelImpl.getOriginalName(),
					resourceTypePermissionModelImpl.getOriginalRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_G_N_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_G_N_R, args);
		}
	}

	/**
	 * Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	 *
	 * @param resourceTypePermissionId the primary key for the new resource type permission
	 * @return the new resource type permission
	 */
	@Override
	public ResourceTypePermission create(long resourceTypePermissionId) {
		ResourceTypePermission resourceTypePermission = new ResourceTypePermissionImpl();

		resourceTypePermission.setNew(true);
		resourceTypePermission.setPrimaryKey(resourceTypePermissionId);

		resourceTypePermission.setCompanyId(companyProvider.getCompanyId());

		return resourceTypePermission;
	}

	/**
	 * Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission that was removed
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission remove(long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException {
		return remove((Serializable)resourceTypePermissionId);
	}

	/**
	 * Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission that was removed
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission remove(Serializable primaryKey)
		throws NoSuchResourceTypePermissionException {
		Session session = null;

		try {
			session = openSession();

			ResourceTypePermission resourceTypePermission = (ResourceTypePermission)session.get(ResourceTypePermissionImpl.class,
					primaryKey);

			if (resourceTypePermission == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceTypePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(resourceTypePermission);
		}
		catch (NoSuchResourceTypePermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ResourceTypePermission removeImpl(
		ResourceTypePermission resourceTypePermission) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourceTypePermission)) {
				resourceTypePermission = (ResourceTypePermission)session.get(ResourceTypePermissionImpl.class,
						resourceTypePermission.getPrimaryKeyObj());
			}

			if (resourceTypePermission != null) {
				session.delete(resourceTypePermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (resourceTypePermission != null) {
			clearCache(resourceTypePermission);
		}

		return resourceTypePermission;
	}

	@Override
	public ResourceTypePermission updateImpl(
		ResourceTypePermission resourceTypePermission) {
		boolean isNew = resourceTypePermission.isNew();

		if (!(resourceTypePermission instanceof ResourceTypePermissionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(resourceTypePermission.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(resourceTypePermission);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in resourceTypePermission proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ResourceTypePermission implementation " +
				resourceTypePermission.getClass());
		}

		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

		Session session = null;

		try {
			session = openSession();

			if (resourceTypePermission.isNew()) {
				session.save(resourceTypePermission);

				resourceTypePermission.setNew(false);
			}
			else {
				resourceTypePermission = (ResourceTypePermission)session.merge(resourceTypePermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ResourceTypePermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					resourceTypePermissionModelImpl.getRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
				args);

			args = new Object[] {
					resourceTypePermissionModelImpl.getCompanyId(),
					resourceTypePermissionModelImpl.getName(),
					resourceTypePermissionModelImpl.getRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_N_R, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N_R,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((resourceTypePermissionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceTypePermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
					args);

				args = new Object[] { resourceTypePermissionModelImpl.getRoleId() };

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
					args);
			}

			if ((resourceTypePermissionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByC_N_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceTypePermissionModelImpl.getOriginalCompanyId(),
						resourceTypePermissionModelImpl.getOriginalName(),
						resourceTypePermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_R, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N_R,
					args);

				args = new Object[] {
						resourceTypePermissionModelImpl.getCompanyId(),
						resourceTypePermissionModelImpl.getName(),
						resourceTypePermissionModelImpl.getRoleId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_N_R, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_N_R,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey(), resourceTypePermission,
			false);

		clearUniqueFindersCache(resourceTypePermissionModelImpl, false);
		cacheUniqueFindersCache(resourceTypePermissionModelImpl);

		resourceTypePermission.resetOriginalValues();

		return resourceTypePermission;
	}

	/**
	 * Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByPrimaryKey(primaryKey);

		if (resourceTypePermission == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceTypePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceTypePermission;
	}

	/**
	 * Returns the resource type permission with the primary key or throws a {@link NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission
	 * @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission findByPrimaryKey(
		long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException {
		return findByPrimaryKey((Serializable)resourceTypePermissionId);
	}

	/**
	 * Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission fetchByPrimaryKey(
		long resourceTypePermissionId) {
		return fetchByPrimaryKey((Serializable)resourceTypePermissionId);
	}

	/**
	 * Returns all the resource type permissions.
	 *
	 * @return the resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findAll(int start, int end,
		OrderByComparator<ResourceTypePermission> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource type permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of resource type permissions
	 */
	@Override
	public List<ResourceTypePermission> findAll(int start, int end,
		OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ResourceTypePermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_RESOURCETYPEPERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCETYPEPERMISSION;

				if (pagination) {
					sql = sql.concat(ResourceTypePermissionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the resource type permissions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ResourceTypePermission resourceTypePermission : findAll()) {
			remove(resourceTypePermission);
		}
	}

	/**
	 * Returns the number of resource type permissions.
	 *
	 * @return the number of resource type permissions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCETYPEPERMISSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "resourceTypePermissionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RESOURCETYPEPERMISSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ResourceTypePermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the resource type permission persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countAll", new String[0]);

		_finderPathWithPaginationFindByRoleId = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
				new String[] { Long.class.getName() },
				ResourceTypePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByRoleId", new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByC_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_R",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByC_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_R",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Long.class.getName()
				},
				ResourceTypePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
				ResourceTypePermissionModelImpl.NAME_COLUMN_BITMASK |
				ResourceTypePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByC_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByC_N_R",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Long.class.getName()
				});

		_finderPathFetchByC_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceTypePermissionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByC_G_N_R",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), Long.class.getName()
				},
				ResourceTypePermissionModelImpl.COMPANYID_COLUMN_BITMASK |
				ResourceTypePermissionModelImpl.GROUPID_COLUMN_BITMASK |
				ResourceTypePermissionModelImpl.NAME_COLUMN_BITMASK |
				ResourceTypePermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByC_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByC_G_N_R",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), Long.class.getName()
				});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceTypePermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION_WHERE = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION_WHERE = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceTypePermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceTypePermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceTypePermission exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ResourceTypePermissionPersistenceImpl.class);
}