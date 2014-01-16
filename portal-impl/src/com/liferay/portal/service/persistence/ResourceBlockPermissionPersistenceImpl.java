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

import com.liferay.portal.NoSuchResourceBlockPermissionException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.impl.ResourceBlockPermissionImpl;
import com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the resource block permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionPersistence
 * @see ResourceBlockPermissionUtil
 * @generated
 */
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEBLOCKID =
		new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourceBlockId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEBLOCKID =
		new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourceBlockId",
			new String[] { Long.class.getName() },
			ResourceBlockPermissionModelImpl.RESOURCEBLOCKID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEBLOCKID = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourceBlockId", new String[] { Long.class.getName() });

	/**
	 * Returns all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @return the matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId) throws SystemException {
		return findByResourceBlockId(resourceBlockId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end) throws SystemException {
		return findByResourceBlockId(resourceBlockId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEBLOCKID;
			finderArgs = new Object[] { resourceBlockId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEBLOCKID;
			finderArgs = new Object[] {
					resourceBlockId,
					
					start, end, orderByComparator
				};
		}

		List<ResourceBlockPermission> list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceBlockPermission resourceBlockPermission : list) {
				if ((resourceBlockId != resourceBlockPermission.getResourceBlockId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
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

					list = new UnmodifiableList<ResourceBlockPermission>(list);
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByResourceBlockId_First(resourceBlockId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourceBlockId=");
		msg.append(resourceBlockId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByResourceBlockId_First(
		long resourceBlockId, OrderByComparator orderByComparator)
		throws SystemException {
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByResourceBlockId_Last(resourceBlockId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourceBlockId=");
		msg.append(resourceBlockId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByResourceBlockId_Last(
		long resourceBlockId, OrderByComparator orderByComparator)
		throws SystemException {
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		long resourceBlockPermissionId, long resourceBlockId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
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
		long resourceBlockId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
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
			Object[] values = orderByComparator.getOrderByConditionValues(resourceBlockPermission);

			for (Object value : values) {
				qPos.add(value);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByResourceBlockId(long resourceBlockId)
		throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByResourceBlockId(long resourceBlockId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEBLOCKID;

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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID =
		new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] { Long.class.getName() },
			ResourceBlockPermissionModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRoleId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the resource block permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId)
		throws SystemException {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId, int start,
		int end) throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID;
			finderArgs = new Object[] { roleId, start, end, orderByComparator };
		}

		List<ResourceBlockPermission> list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceBlockPermission resourceBlockPermission : list) {
				if ((roleId != resourceBlockPermission.getRoleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
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

					list = new UnmodifiableList<ResourceBlockPermission>(list);
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByRoleId_First(roleId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the first resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByRoleId_First(long roleId,
		OrderByComparator orderByComparator) throws SystemException {
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByRoleId_Last(roleId,
				orderByComparator);

		if (resourceBlockPermission != null) {
			return resourceBlockPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("roleId=");
		msg.append(roleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceBlockPermissionException(msg.toString());
	}

	/**
	 * Returns the last resource block permission in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByRoleId_Last(long roleId,
		OrderByComparator orderByComparator) throws SystemException {
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
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission[] findByRoleId_PrevAndNext(
		long resourceBlockPermissionId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockPermissionException, SystemException {
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
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
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
			Object[] values = orderByComparator.getOrderByConditionValues(resourceBlockPermission);

			for (Object value : values) {
				qPos.add(value);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByRoleId(long roleId) throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByRoleId(long roleId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROLEID;

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
	public static final FinderPath FINDER_PATH_FETCH_BY_R_R = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByR_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			ResourceBlockPermissionModelImpl.RESOURCEBLOCKID_COLUMN_BITMASK |
			ResourceBlockPermissionModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_R = new FinderPath(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the matching resource block permission
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByR_R(resourceBlockId,
				roleId);

		if (resourceBlockPermission == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceBlockId=");
			msg.append(resourceBlockId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByR_R(long resourceBlockId, long roleId)
		throws SystemException {
		return fetchByR_R(resourceBlockId, roleId, true);
	}

	/**
	 * Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByR_R(long resourceBlockId,
		long roleId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { resourceBlockId, roleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_R_R,
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
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
						finderArgs, list);
				}
				else {
					ResourceBlockPermission resourceBlockPermission = list.get(0);

					result = resourceBlockPermission;

					cacheResult(resourceBlockPermission);

					if ((resourceBlockPermission.getResourceBlockId() != resourceBlockId) ||
							(resourceBlockPermission.getRoleId() != roleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
							finderArgs, resourceBlockPermission);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_R,
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
			return (ResourceBlockPermission)result;
		}
	}

	/**
	 * Removes the resource block permission where resourceBlockId = &#63; and roleId = &#63; from the database.
	 *
	 * @param resourceBlockId the resource block ID
	 * @param roleId the role ID
	 * @return the resource block permission that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission removeByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByR_R(long resourceBlockId, long roleId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_R;

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

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R,
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceBlockPermissionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceBlockPermissionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource block permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceBlockPermission resourceBlockPermission) {
		EntityCacheUtil.removeResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			resourceBlockPermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(resourceBlockPermission);
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

			clearUniqueFindersCache(resourceBlockPermission);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceBlockPermission resourceBlockPermission) {
		if (resourceBlockPermission.isNew()) {
			Object[] args = new Object[] {
					resourceBlockPermission.getResourceBlockId(),
					resourceBlockPermission.getRoleId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_R, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R, args,
				resourceBlockPermission);
		}
		else {
			ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl = (ResourceBlockPermissionModelImpl)resourceBlockPermission;

			if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_R_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockPermission.getResourceBlockId(),
						resourceBlockPermission.getRoleId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_R, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_R, args,
					resourceBlockPermission);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ResourceBlockPermission resourceBlockPermission) {
		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl = (ResourceBlockPermissionModelImpl)resourceBlockPermission;

		Object[] args = new Object[] {
				resourceBlockPermission.getResourceBlockId(),
				resourceBlockPermission.getRoleId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_R, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_R, args);

		if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_R_R.getColumnBitmask()) != 0) {
			args = new Object[] {
					resourceBlockPermissionModelImpl.getOriginalResourceBlockId(),
					resourceBlockPermissionModelImpl.getOriginalRoleId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_R, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_R, args);
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

		return resourceBlockPermission;
	}

	/**
	 * Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission remove(long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException, SystemException {
		return remove((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission remove(Serializable primaryKey)
		throws NoSuchResourceBlockPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)session.get(ResourceBlockPermissionImpl.class,
					primaryKey);

			if (resourceBlockPermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
		ResourceBlockPermission resourceBlockPermission)
		throws SystemException {
		resourceBlockPermission = toUnwrappedModel(resourceBlockPermission);

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
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission)
		throws SystemException {
		resourceBlockPermission = toUnwrappedModel(resourceBlockPermission);

		boolean isNew = resourceBlockPermission.isNew();

		ResourceBlockPermissionModelImpl resourceBlockPermissionModelImpl = (ResourceBlockPermissionModelImpl)resourceBlockPermission;

		Session session = null;

		try {
			session = openSession();

			if (resourceBlockPermission.isNew()) {
				session.save(resourceBlockPermission);

				resourceBlockPermission.setNew(false);
			}
			else {
				session.merge(resourceBlockPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ResourceBlockPermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEBLOCKID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockPermissionModelImpl.getOriginalResourceBlockId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RESOURCEBLOCKID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEBLOCKID,
					args);

				args = new Object[] {
						resourceBlockPermissionModelImpl.getResourceBlockId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RESOURCEBLOCKID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEBLOCKID,
					args);
			}

			if ((resourceBlockPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceBlockPermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);

				args = new Object[] { resourceBlockPermissionModelImpl.getRoleId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockPermissionImpl.class,
			resourceBlockPermission.getPrimaryKey(), resourceBlockPermission);

		clearUniqueFindersCache(resourceBlockPermission);
		cacheUniqueFindersCache(resourceBlockPermission);

		resourceBlockPermission.resetOriginalValues();

		return resourceBlockPermission;
	}

	protected ResourceBlockPermission toUnwrappedModel(
		ResourceBlockPermission resourceBlockPermission) {
		if (resourceBlockPermission instanceof ResourceBlockPermissionImpl) {
			return resourceBlockPermission;
		}

		ResourceBlockPermissionImpl resourceBlockPermissionImpl = new ResourceBlockPermissionImpl();

		resourceBlockPermissionImpl.setNew(resourceBlockPermission.isNew());
		resourceBlockPermissionImpl.setPrimaryKey(resourceBlockPermission.getPrimaryKey());

		resourceBlockPermissionImpl.setResourceBlockPermissionId(resourceBlockPermission.getResourceBlockPermissionId());
		resourceBlockPermissionImpl.setResourceBlockId(resourceBlockPermission.getResourceBlockId());
		resourceBlockPermissionImpl.setRoleId(resourceBlockPermission.getRoleId());
		resourceBlockPermissionImpl.setActionIds(resourceBlockPermission.getActionIds());
		resourceBlockPermissionImpl.setMvccVersion(resourceBlockPermission.getMvccVersion());

		return resourceBlockPermissionImpl;
	}

	/**
	 * Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceBlockPermissionException, SystemException {
		ResourceBlockPermission resourceBlockPermission = fetchByPrimaryKey(primaryKey);

		if (resourceBlockPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceBlockPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceBlockPermission;
	}

	/**
	 * Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission findByPrimaryKey(
		long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException, SystemException {
		return findByPrimaryKey((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block permission
	 * @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		ResourceBlockPermission resourceBlockPermission = (ResourceBlockPermission)EntityCacheUtil.getResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockPermissionImpl.class, primaryKey);

		if (resourceBlockPermission == _nullResourceBlockPermission) {
			return null;
		}

		if (resourceBlockPermission == null) {
			Session session = null;

			try {
				session = openSession();

				resourceBlockPermission = (ResourceBlockPermission)session.get(ResourceBlockPermissionImpl.class,
						primaryKey);

				if (resourceBlockPermission != null) {
					cacheResult(resourceBlockPermission);
				}
				else {
					EntityCacheUtil.putResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockPermissionImpl.class, primaryKey,
						_nullResourceBlockPermission);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ResourceBlockPermissionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceBlockPermissionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return resourceBlockPermission;
	}

	/**
	 * Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockPermission fetchByPrimaryKey(
		long resourceBlockPermissionId) throws SystemException {
		return fetchByPrimaryKey((Serializable)resourceBlockPermissionId);
	}

	/**
	 * Returns all the resource block permissions.
	 *
	 * @return the resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ResourceBlockPermission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ResourceBlockPermission> list = (List<ResourceBlockPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

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

					list = new UnmodifiableList<ResourceBlockPermission>(list);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (ResourceBlockPermission resourceBlockPermission : findAll()) {
			remove(resourceBlockPermission);
		}
	}

	/**
	 * Returns the number of resource block permissions.
	 *
	 * @return the number of resource block permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEBLOCKPERMISSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the resource block permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceBlockPermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceBlockPermission>> listenersList = new ArrayList<ModelListener<ResourceBlockPermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceBlockPermission>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceBlockPermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RESOURCEBLOCKPERMISSION = "SELECT resourceBlockPermission FROM ResourceBlockPermission resourceBlockPermission";
	private static final String _SQL_SELECT_RESOURCEBLOCKPERMISSION_WHERE = "SELECT resourceBlockPermission FROM ResourceBlockPermission resourceBlockPermission WHERE ";
	private static final String _SQL_COUNT_RESOURCEBLOCKPERMISSION = "SELECT COUNT(resourceBlockPermission) FROM ResourceBlockPermission resourceBlockPermission";
	private static final String _SQL_COUNT_RESOURCEBLOCKPERMISSION_WHERE = "SELECT COUNT(resourceBlockPermission) FROM ResourceBlockPermission resourceBlockPermission WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceBlockPermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceBlockPermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceBlockPermission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourceBlockPermissionPersistenceImpl.class);
	private static ResourceBlockPermission _nullResourceBlockPermission = new ResourceBlockPermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ResourceBlockPermission> toCacheModel() {
				return _nullResourceBlockPermissionCacheModel;
			}
		};

	private static CacheModel<ResourceBlockPermission> _nullResourceBlockPermissionCacheModel =
		new CacheModel<ResourceBlockPermission>() {
			@Override
			public ResourceBlockPermission toEntityModel() {
				return _nullResourceBlockPermission;
			}
		};
}