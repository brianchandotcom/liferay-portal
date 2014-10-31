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

import com.liferay.portal.NoSuchResourceTypePermissionException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.model.impl.ResourceTypePermissionImpl;
import com.liferay.portal.model.impl.ResourceTypePermissionModelImpl;
import com.liferay.portal.service.persistence.ResourceTypePermissionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the resource type permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionPersistence
 * @see ResourceTypePermissionUtil
 * @generated
 */
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ROLEID = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID =
		new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRoleId",
			new String[] { Long.class.getName() },
			ResourceTypePermissionModelImpl.ROLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRoleId",
			new String[] { Long.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceTypePermission resourceTypePermission : list) {
				if ((roleId != resourceTypePermission.getRoleId())) {
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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
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
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
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
			Object[] values = orderByComparator.getOrderByConditionValues(resourceTypePermission);

			for (Object value : values) {
				qPos.add(value);
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROLEID;

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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N_R;
			finderArgs = new Object[] { companyId, name, roleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N_R;
			finderArgs = new Object[] {
					companyId, name, roleId,
					
					start, end, orderByComparator
				};
		}

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceTypePermission resourceTypePermission : list) {
				if ((companyId != resourceTypePermission.getCompanyId()) ||
						!Validator.equals(name, resourceTypePermission.getName()) ||
						(roleId != resourceTypePermission.getRoleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission[] findByC_N_R_PrevAndNext(
		long resourceTypePermissionId, long companyId, String name,
		long roleId, OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException {
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
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_R_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
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
			Object[] values = orderByComparator.getOrderByConditionValues(resourceTypePermission);

			for (Object value : values) {
				qPos.add(value);
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N_R;

		Object[] finderArgs = new Object[] { companyId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
	private static final String _FINDER_COLUMN_C_N_R_NAME_1 = "resourceTypePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = '') AND ";
	private static final String _FINDER_COLUMN_C_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_N_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
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

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
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
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	 */
	@Override
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_G_N_R,
					finderArgs, this);
		}

		if (result instanceof ResourceTypePermission) {
			ResourceTypePermission resourceTypePermission = (ResourceTypePermission)result;

			if ((companyId != resourceTypePermission.getCompanyId()) ||
					(groupId != resourceTypePermission.getGroupId()) ||
					!Validator.equals(name, resourceTypePermission.getName()) ||
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

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
						finderArgs, list);
				}
				else {
					ResourceTypePermission resourceTypePermission = list.get(0);

					result = resourceTypePermission;

					cacheResult(resourceTypePermission);

					if ((resourceTypePermission.getCompanyId() != companyId) ||
							(resourceTypePermission.getGroupId() != groupId) ||
							(resourceTypePermission.getName() == null) ||
							!resourceTypePermission.getName().equals(name) ||
							(resourceTypePermission.getRoleId() != roleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
							finderArgs, resourceTypePermission);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R,
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G_N_R;

		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_R_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
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
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_1 = "resourceTypePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = '') AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";

	public ResourceTypePermissionPersistenceImpl() {
		setModelClass(ResourceTypePermission.class);
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

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceTypePermissionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceTypePermissionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource type permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceTypePermission resourceTypePermission) {
		EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(resourceTypePermission);
	}

	@Override
	public void clearCache(List<ResourceTypePermission> resourceTypePermissions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceTypePermission resourceTypePermission : resourceTypePermissions) {
			EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionImpl.class,
				resourceTypePermission.getPrimaryKey());

			clearUniqueFindersCache(resourceTypePermission);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceTypePermission resourceTypePermission) {
		if (resourceTypePermission.isNew()) {
			Object[] args = new Object[] {
					resourceTypePermission.getCompanyId(),
					resourceTypePermission.getGroupId(),
					resourceTypePermission.getName(),
					resourceTypePermission.getRoleId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_N_R, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R, args,
				resourceTypePermission);
		}
		else {
			ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

			if ((resourceTypePermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_G_N_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceTypePermission.getCompanyId(),
						resourceTypePermission.getGroupId(),
						resourceTypePermission.getName(),
						resourceTypePermission.getRoleId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_N_R, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R, args,
					resourceTypePermission);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ResourceTypePermission resourceTypePermission) {
		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

		Object[] args = new Object[] {
				resourceTypePermission.getCompanyId(),
				resourceTypePermission.getGroupId(),
				resourceTypePermission.getName(),
				resourceTypePermission.getRoleId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N_R, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R, args);

		if ((resourceTypePermissionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_G_N_R.getColumnBitmask()) != 0) {
			args = new Object[] {
					resourceTypePermissionModelImpl.getOriginalCompanyId(),
					resourceTypePermissionModelImpl.getOriginalGroupId(),
					resourceTypePermissionModelImpl.getOriginalName(),
					resourceTypePermissionModelImpl.getOriginalRoleId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_G_N_R, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R, args);
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

		return resourceTypePermission;
	}

	/**
	 * Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission that was removed
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
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
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
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
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
		resourceTypePermission = toUnwrappedModel(resourceTypePermission);

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
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		resourceTypePermission = toUnwrappedModel(resourceTypePermission);

		boolean isNew = resourceTypePermission.isNew();

		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

		Session session = null;

		try {
			session = openSession();

			if (resourceTypePermission.isNew()) {
				session.save(resourceTypePermission);

				resourceTypePermission.setNew(false);
			}
			else {
				session.merge(resourceTypePermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ResourceTypePermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((resourceTypePermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceTypePermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);

				args = new Object[] { resourceTypePermissionModelImpl.getRoleId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROLEID,
					args);
			}

			if ((resourceTypePermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceTypePermissionModelImpl.getOriginalCompanyId(),
						resourceTypePermissionModelImpl.getOriginalName(),
						resourceTypePermissionModelImpl.getOriginalRoleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N_R,
					args);

				args = new Object[] {
						resourceTypePermissionModelImpl.getCompanyId(),
						resourceTypePermissionModelImpl.getName(),
						resourceTypePermissionModelImpl.getRoleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N_R,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey(), resourceTypePermission,
			false);

		clearUniqueFindersCache(resourceTypePermission);
		cacheUniqueFindersCache(resourceTypePermission);

		resourceTypePermission.resetOriginalValues();

		return resourceTypePermission;
	}

	protected ResourceTypePermission toUnwrappedModel(
		ResourceTypePermission resourceTypePermission) {
		if (resourceTypePermission instanceof ResourceTypePermissionImpl) {
			return resourceTypePermission;
		}

		ResourceTypePermissionImpl resourceTypePermissionImpl = new ResourceTypePermissionImpl();

		resourceTypePermissionImpl.setNew(resourceTypePermission.isNew());
		resourceTypePermissionImpl.setPrimaryKey(resourceTypePermission.getPrimaryKey());

		resourceTypePermissionImpl.setMvccVersion(resourceTypePermission.getMvccVersion());
		resourceTypePermissionImpl.setResourceTypePermissionId(resourceTypePermission.getResourceTypePermissionId());
		resourceTypePermissionImpl.setCompanyId(resourceTypePermission.getCompanyId());
		resourceTypePermissionImpl.setGroupId(resourceTypePermission.getGroupId());
		resourceTypePermissionImpl.setName(resourceTypePermission.getName());
		resourceTypePermissionImpl.setRoleId(resourceTypePermission.getRoleId());
		resourceTypePermissionImpl.setActionIds(resourceTypePermission.getActionIds());

		return resourceTypePermissionImpl;
	}

	/**
	 * Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceTypePermissionException {
		ResourceTypePermission resourceTypePermission = fetchByPrimaryKey(primaryKey);

		if (resourceTypePermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceTypePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceTypePermission;
	}

	/**
	 * Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
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
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	 */
	@Override
	public ResourceTypePermission fetchByPrimaryKey(Serializable primaryKey) {
		ResourceTypePermission resourceTypePermission = (ResourceTypePermission)EntityCacheUtil.getResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionImpl.class, primaryKey);

		if (resourceTypePermission == _nullResourceTypePermission) {
			return null;
		}

		if (resourceTypePermission == null) {
			Session session = null;

			try {
				session = openSession();

				resourceTypePermission = (ResourceTypePermission)session.get(ResourceTypePermissionImpl.class,
						primaryKey);

				if (resourceTypePermission != null) {
					cacheResult(resourceTypePermission);
				}
				else {
					EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceTypePermissionImpl.class, primaryKey,
						_nullResourceTypePermission);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceTypePermissionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return resourceTypePermission;
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

	@Override
	public Map<Serializable, ResourceTypePermission> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ResourceTypePermission> map = new HashMap<Serializable, ResourceTypePermission>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ResourceTypePermission resourceTypePermission = fetchByPrimaryKey(primaryKey);

			if (resourceTypePermission != null) {
				map.put(primaryKey, resourceTypePermission);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ResourceTypePermission resourceTypePermission = (ResourceTypePermission)EntityCacheUtil.getResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceTypePermissionImpl.class, primaryKey);

			if (resourceTypePermission == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, resourceTypePermission);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ResourceTypePermission resourceTypePermission : (List<ResourceTypePermission>)q.list()) {
				map.put(resourceTypePermission.getPrimaryKeyObj(),
					resourceTypePermission);

				cacheResult(resourceTypePermission);

				uncachedPrimaryKeys.remove(resourceTypePermission.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceTypePermissionImpl.class, primaryKey,
					_nullResourceTypePermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

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
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCETYPEPERMISSION);

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
	 * Initializes the resource type permission persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceTypePermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION_WHERE_PKS_IN = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission WHERE resourceTypePermissionId IN (";
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION_WHERE = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION_WHERE = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceTypePermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceTypePermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceTypePermission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(ResourceTypePermissionPersistenceImpl.class);
	private static final ResourceTypePermission _nullResourceTypePermission = new ResourceTypePermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ResourceTypePermission> toCacheModel() {
				return _nullResourceTypePermissionCacheModel;
			}
		};

	private static final CacheModel<ResourceTypePermission> _nullResourceTypePermissionCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<ResourceTypePermission>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public ResourceTypePermission toEntityModel() {
			return _nullResourceTypePermission;
		}
	}
}