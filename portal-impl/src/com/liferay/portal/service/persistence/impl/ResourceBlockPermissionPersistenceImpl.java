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
import com.liferay.portal.kernel.exception.NoSuchResourceBlockPermissionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.ResourceBlockPermissionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.ResourceBlockPermissionImpl;
import com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The persistence implementation for the resource block permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionPersistence
 * @see com.liferay.portal.kernel.service.persistence.ResourceBlockPermissionUtil
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceBlockPermissionPersistenceImpl extends BasePersistenceImpl<ResourceBlockPermission>
	implements ResourceBlockPermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceBlockPermissionUtil} to access the resource block permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceBlockPermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByResourceBlockId;
	private FinderPath _finderPathWithoutPaginationFindByResourceBlockId;
	private FinderPath _finderPathCountByResourceBlockId;

	/**
	 * Returns all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @return the matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId) {
		return findByResourceBlockId(resourceBlockId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end) {
		return findByResourceBlockId(resourceBlockId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		return findByResourceBlockId(resourceBlockId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		OrderByComparator<ResourceBlockPermission> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByResourceBlockId;
			finderArgs = new Object[] { resourceBlockId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByResourceBlockId;
			finderArgs = new Object[] {
					resourceBlockId,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlockPermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceBlockPermission resourceBlockPermission : list) {
					if ((resourceBlockId != resourceBlockPermission.getResourceBlockId())) {
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

			query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceBlockPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

				if (!pagination) {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
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
	 * Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByResourceBlockId_First(resourceBlockId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourceBlockId=");
		msg.append(resourceBlockId);

		msg.append("}");

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByResourceBlockId_First(
		long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		List<ResourceBlockPermission> list = findByResourceBlockId(resourceBlockId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByResourceBlockId_Last(resourceBlockId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourceBlockId=");
		msg.append(resourceBlockId);

		msg.append("}");

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByResourceBlockId_Last(
		long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		int count = countByResourceBlockId(resourceBlockId);

		if (count == 0) {
			return null;
		}

		List<ResourceBlockPermission> list = findByResourceBlockId(resourceBlockId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource block permissions before and after the current resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockPermissionId the primary key of the current resource block permission
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		long resourceBlockPermissionId, long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = findByPrimaryKey(resourceBlockPermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourceBlockPermission[] array = new ResourceBlockPermissionImpl[3];

			array[0] = getByResourceBlockId_PrevAndNext(session,
					resourceBlockPermission, resourceBlockId,
					orderByComparator, true);

			array[1] = resourceBlockPermission;

			array[2] = getByResourceBlockId_PrevAndNext(session,
					resourceBlockPermission, resourceBlockId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceBlockPermission getByResourceBlockId_PrevAndNext(
		Session session, ResourceBlockPermission resourceBlockPermission,
		long resourceBlockId,
		OrderByComparator<ResourceBlockPermission> orderByComparator,
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

		query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

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
			query.append(ResourceBlockPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourceBlockId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceBlockPermission)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<ResourceBlockPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource block permissions where resourceBlockId = &#63; from the database.
	 *
	 * @param resourceBlockId the resource block ID
	 */
	@Override
	public void removeByResourceBlockId(long resourceBlockId) {
		for (ResourceBlockPermission resourceBlockPermission : findByResourceBlockId(
				resourceBlockId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceBlockPermission);
		}
	}

	/**
	 * Returns the number of resource block permissions where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @return the number of matching resource block permissions
	 */
	@Override
	public int countByResourceBlockId(long resourceBlockId) {
		FinderPath finderPath = _finderPathCountByResourceBlockId;

		Object[] finderArgs = new Object[] { resourceBlockId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEBLOCKPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

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

	private static final String _FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2 =
		"resourceBlockPermission.resourceBlockId = ?";
	private FinderPath _finderPathWithPaginationFindByRoleId;
	private FinderPath _finderPathWithoutPaginationFindByRoleId;
	private FinderPath _finderPathCountByRoleId;

	/**
	 * Returns all the resource block permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId) {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId, int start,
		int end) {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator<ResourceBlockPermission> orderByComparator) {
		return findByRoleId(roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator<ResourceBlockPermission> orderByComparator,
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

		List<ResourceBlockPermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceBlockPermission resourceBlockPermission : list) {
					if ((roleId != resourceBlockPermission.getRoleId())) {
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

			query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceBlockPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				if (!pagination) {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
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
	 * Returns the first resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission findByRoleId_First(long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByRoleId_First(roleId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the first resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByRoleId_First(long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		List<ResourceBlockPermission> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission findByRoleId_Last(long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByRoleId_Last(roleId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append("}");

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the last resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByRoleId_Last(long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		int count = countByRoleId(roleId);

		if (count == 0) {
			return null;
		}

		List<ResourceBlockPermission> list = findByRoleId(roleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource block permissions before and after the current resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param resourceBlockPermissionId the primary key of the current resource block permission
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission[] findByRoleId_PrevAndNext(
		long resourceBlockPermissionId, long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = findByPrimaryKey(resourceBlockPermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourceBlockPermission[] array = new ResourceBlockPermissionImpl[3];

			array[0] = getByRoleId_PrevAndNext(session,
					resourceBlockPermission, roleId, orderByComparator, true);

			array[1] = resourceBlockPermission;

			array[2] = getByRoleId_PrevAndNext(session,
					resourceBlockPermission, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceBlockPermission getByRoleId_PrevAndNext(Session session,
		ResourceBlockPermission resourceBlockPermission, long roleId,
		OrderByComparator<ResourceBlockPermission> orderByComparator,
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

		query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE);

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
			query.append(ResourceBlockPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					resourceBlockPermission)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<ResourceBlockPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource block permissions where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	@Override
	public void removeByRoleId(long roleId) {
		for (ResourceBlockPermission resourceBlockPermission : findByRoleId(
				roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceBlockPermission);
		}
	}

	/**
	 * Returns the number of resource block permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching resource block permissions
	 */
	@Override
	public int countByRoleId(long roleId) {
		FinderPath finderPath = _finderPathCountByRoleId;

		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEBLOCKPERMISSION_WHERE);

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

	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "resourceBlockPermission.roleId = ?";
	private FinderPath _finderPathFetchByR_R;
	private FinderPath _finderPathCountByR_R;

	/**
	 * Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or throws a {@link NoSuchResourceBlockPermissionException} if it could not be found.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the matching resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission findByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByR_R(resourceBlockId,
				roleId);

		if (resourceBlockPermission == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceBlockId=");
			msg.append(resourceBlockId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchResourceBlockPermissionException(msg.toString());
		}

		return resourceBlockPermission;
	}

	/**
	 * Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByR_R(long resourceBlockId, long roleId) {
		return fetchByR_R(resourceBlockId, roleId, true);
	}

	/**
	 * Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByR_R(long resourceBlockId,
		long roleId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { resourceBlockId, roleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByR_R,
					finderArgs, this);
		}

		if (result instanceof ResourceBlockPermission) {
			ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)result;

			if ((resourceBlockId != resourceBlockPermission.getResourceBlockId()) ||
					(roleId != resourceBlockPermission.getRoleId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_R_R_RESOURCEBLOCKID_2);

			query.append(_FINDER_COLUMN_R_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

				qPos.add(roleId);

				List<ResourceBlockPermission> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByR_R,
						finderArgs, list);
				}
				else {
					ResourceBlockPermission resourceBlockPermission = list.get(0);

					result = resourceBlockPermission;

					cacheResult(resourceBlockPermission);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByR_R, finderArgs);

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
			return (ResourceBlockPermission)result;
		}
	}

	/**
	 * Removes the resource block permission where resourceBlockId = &#63; and roleId = &#63; from the database.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the resource block permission that was removed
	 */
	@Override
	public ResourceBlockPermission removeByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = findByR_R(resourceBlockId,
				roleId);

		return remove(resourceBlockPermission);
	}

	/**
	 * Returns the number of resource block permissions where resourceBlockId = &#63; and roleId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the number of matching resource block permissions
	 */
	@Override
	public int countByR_R(long resourceBlockId, long roleId) {
		FinderPath finderPath = _finderPathCountByR_R;

		Object[] finderArgs = new Object[] { resourceBlockId, roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEBLOCKPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_R_R_RESOURCEBLOCKID_2);

			query.append(_FINDER_COLUMN_R_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

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

	private static final String _FINDER_COLUMN_R_R_RESOURCEBLOCKID_2 = "resourceBlockPermission.resourceBlockId = ? AND ";
	private static final String _FINDER_COLUMN_R_R_ROLEID_2 = "resourceBlockPermission.roleId = ?";

	public ResourceBlockPermissionPersistenceImpl() {
		setModelClass(ResourceBlockPermission.class);

		setModelImplClass(ResourceBlockPermissionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the resource block permission in the entity cache if it is enabled.
	 *
	 * @param resourceBlockPermission the resource block permission
	 */
	@Override
	public void cacheResult(ResourceBlockPermission resourceBlockPermission) {
		EntityCacheUtil.putResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			resourceBlockPermission.getPrimaryKey(), resourceBlockPermission);

		FinderCacheUtil.putResult(_finderPathFetchByR_R,
			new Object[] {
				resourceBlockPermission.getResourceBlockId(),
				resourceBlockPermission.getRoleId()
			}, resourceBlockPermission);

		resourceBlockPermission.resetOriginalValues();
	}

	/**
	 * Caches the resource block permissions in the entity cache if it is enabled.
	 *
	 * @param resourceBlockPermissions the resource block permissions
	 */
	@Override
	public void cacheResult(
		List<ResourceBlockPermission> resourceBlockPermissions) {
		for (ResourceBlockPermission resourceBlockPermission : resourceBlockPermissions) {
			if (EntityCacheUtil.getResult(
						ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockPermissionImpl.class,
						resourceBlockPermission.getPrimaryKey()) == null) {
				cacheResult(resourceBlockPermission);
			}
			else {
				resourceBlockPermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all resource block permissions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ResourceBlockPermissionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource block permission.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceBlockPermission resourceBlockPermission) {
		EntityCacheUtil.removeResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			resourceBlockPermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ResourceBlockPermissionModelImpl)resourceBlockPermission,
			true);
	}

	@Override
	public void clearCache(
		List<ResourceBlockPermission> resourceBlockPermissions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceBlockPermission resourceBlockPermission : resourceBlockPermissions) {
			EntityCacheUtil.removeResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				resourceBlockPermission.getPrimaryKey());

			clearUniqueFindersCache((ResourceBlockPermissionModelImpl)resourceBlockPermission,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl) {
		Object[] args = new Object[] {
				resourceBlockPermissionModelImpl.getResourceBlockId(),
				resourceBlockPermissionModelImpl.getRoleId()
			};

		FinderCacheUtil.putResult(_finderPathCountByR_R, args, Long.valueOf(1),
			false);
		FinderCacheUtil.putResult(_finderPathFetchByR_R, args,
			resourceBlockPermissionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					resourceBlockPermissionModelImpl.getResourceBlockId(),
					resourceBlockPermissionModelImpl.getRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByR_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByR_R, args);
		}

		if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
				_finderPathFetchByR_R.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					resourceBlockPermissionModelImpl.getOriginalResourceBlockId(),
					resourceBlockPermissionModelImpl.getOriginalRoleId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByR_R, args);
			FinderCacheUtil.removeResult(_finderPathFetchByR_R, args);
		}
	}

	/**
	 * Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	 *
	 * @param resourceBlockPermissionId the primary key for the new resource block permission
	 * @return the new resource block permission
	 */
	@Override
	public ResourceBlockPermission create(long resourceBlockPermissionId) {
		ResourceBlockPermission resourceBlockPermission = new ResourceBlockPermissionImpl();

		resourceBlockPermission.setNew(true);
		resourceBlockPermission.setPrimaryKey(resourceBlockPermissionId);

		resourceBlockPermission.setCompanyId(companyProvider.getCompanyId());

		return resourceBlockPermission;
	}

	/**
	 * Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission remove(long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException {
		return remove((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission remove(Serializable primaryKey)
		throws NoSuchResourceBlockPermissionException {
		Session session = null;

		try {
			session = openSession();

			ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)session.get(ResourceBlockPermissionImpl.class,
					primaryKey);

			if (resourceBlockPermission == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceBlockPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(resourceBlockPermission);
		}
		catch (NoSuchResourceBlockPermissionException nsee) {
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
	protected ResourceBlockPermission removeImpl(
		ResourceBlockPermission resourceBlockPermission) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourceBlockPermission)) {
				resourceBlockPermission = (ResourceBlockPermission)session.get(ResourceBlockPermissionImpl.class,
						resourceBlockPermission.getPrimaryKeyObj());
			}

			if (resourceBlockPermission != null) {
				session.delete(resourceBlockPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (resourceBlockPermission != null) {
			clearCache(resourceBlockPermission);
		}

		return resourceBlockPermission;
	}

	@Override
	public ResourceBlockPermission updateImpl(
		ResourceBlockPermission resourceBlockPermission) {
		boolean isNew = resourceBlockPermission.isNew();

		if (!(resourceBlockPermission instanceof ResourceBlockPermissionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(resourceBlockPermission.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(resourceBlockPermission);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in resourceBlockPermission proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ResourceBlockPermission implementation " +
				resourceBlockPermission.getClass());
		}

		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl = (ResourceBlockPermissionModelImpl)resourceBlockPermission;

		Session session = null;

		try {
			session = openSession();

			if (resourceBlockPermission.isNew()) {
				session.save(resourceBlockPermission);

				resourceBlockPermission.setNew(false);
			}
			else {
				resourceBlockPermission = (ResourceBlockPermission)session.merge(resourceBlockPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ResourceBlockPermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					resourceBlockPermissionModelImpl.getResourceBlockId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByResourceBlockId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByResourceBlockId,
				args);

			args = new Object[] { resourceBlockPermissionModelImpl.getRoleId() };

			FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByResourceBlockId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockPermissionModelImpl.getOriginalResourceBlockId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByResourceBlockId,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByResourceBlockId,
					args);

				args = new Object[] {
						resourceBlockPermissionModelImpl.getResourceBlockId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByResourceBlockId,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByResourceBlockId,
					args);
			}

			if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByRoleId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockPermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
					args);

				args = new Object[] { resourceBlockPermissionModelImpl.getRoleId() };

				FinderCacheUtil.removeResult(_finderPathCountByRoleId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByRoleId,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			resourceBlockPermission.getPrimaryKey(), resourceBlockPermission,
			false);

		clearUniqueFindersCache(resourceBlockPermissionModelImpl, false);
		cacheUniqueFindersCache(resourceBlockPermissionModelImpl);

		resourceBlockPermission.resetOriginalValues();

		return resourceBlockPermission;
	}

	/**
	 * Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceBlockPermissionException {
		ResourceBlockPermission resourceBlockPermission = fetchByPrimaryKey(primaryKey);

		if (resourceBlockPermission == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceBlockPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceBlockPermission;
	}

	/**
	 * Returns the resource block permission with the primary key or throws a {@link NoSuchResourceBlockPermissionException} if it could not be found.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission findByPrimaryKey(
		long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException {
		return findByPrimaryKey((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission fetchByPrimaryKey(
		long resourceBlockPermissionId) {
		return fetchByPrimaryKey((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Returns all the resource block permissions.
	 *
	 * @return the resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findAll(int start, int end,
		OrderByComparator<ResourceBlockPermission> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> findAll(int start, int end,
		OrderByComparator<ResourceBlockPermission> orderByComparator,
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

		List<ResourceBlockPermission> list = null;

		if (retrieveFromCache) {
			list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_RESOURCEBLOCKPERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEBLOCKPERMISSION;

				if (pagination) {
					sql = sql.concat(ResourceBlockPermissionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceBlockPermission>)QueryUtil.list(q,
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
	 * Removes all the resource block permissions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ResourceBlockPermission resourceBlockPermission : findAll()) {
			remove(resourceBlockPermission);
		}
	}

	/**
	 * Returns the number of resource block permissions.
	 *
	 * @return the number of resource block permissions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEBLOCKPERMISSION);

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
		return "resourceBlockPermissionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RESOURCEBLOCKPERMISSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ResourceBlockPermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the resource block permission persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countAll", new String[0]);

		_finderPathWithPaginationFindByResourceBlockId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByResourceBlockId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByResourceBlockId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByResourceBlockId", new String[] { Long.class.getName() },
				ResourceBlockPermissionModelImpl.RESOURCEBLOCKID_COLUMN_BITMASK);

		_finderPathCountByResourceBlockId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByResourceBlockId", new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByRoleId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByRoleId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
				new String[] { Long.class.getName() },
				ResourceBlockPermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByRoleId = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByRoleId", new String[] { Long.class.getName() });

		_finderPathFetchByR_R = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByR_R",
				new String[] { Long.class.getName(), Long.class.getName() },
				ResourceBlockPermissionModelImpl.RESOURCEBLOCKID_COLUMN_BITMASK |
				ResourceBlockPermissionModelImpl.ROLEID_COLUMN_BITMASK);

		_finderPathCountByR_R = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
				Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByR_R",
				new String[] { Long.class.getName(), Long.class.getName() });
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceBlockPermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_RESOURCEBLOCKPERMISSION = "SELECT resourceBlockPermission FROM ResourceBlockPermission resourceBlockPermission";
	private static final String _SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE = "SELECT resourceBlockPermission FROM ResourceBlockPermission resourceBlockPermission WHERE ";
	private static final String _SQL_COUNT_RESOURCEBLOCKPERMISSION = "SELECT COUNT(resourceBlockPermission) FROM ResourceBlockPermission resourceBlockPermission";
	private static final String _SQL_COUNT_RESOURCEBLOCKPERMISSION_WHERE = "SELECT COUNT(resourceBlockPermission) FROM ResourceBlockPermission resourceBlockPermission WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceBlockPermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceBlockPermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceBlockPermission exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ResourceBlockPermissionPersistenceImpl.class);
}