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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.impl.PermissionImpl;
import com.liferay.portal.model.impl.PermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPersistence
 * @see PermissionUtil
 * @generated
 */
public class PermissionPersistenceImpl extends BasePersistenceImpl<Permission>
	implements PermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PermissionUtil} to access the permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_RESOURCEID = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, PermissionImpl.class,
			FINDER_CLASS_NAME_LIST, "findByResourceId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEID = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByResourceId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A_R = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, PermissionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByA_R",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A_R = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByA_R",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, PermissionImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the permission in the entity cache if it is enabled.
	 *
	 * @param permission the permission
	 */
	public void cacheResult(Permission permission) {
		EntityCacheUtil.putResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey(), permission);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
			new Object[] {
				permission.getActionId(),
				Long.valueOf(permission.getResourceId())
			}, permission);

		permission.resetOriginalValues();
	}

	/**
	 * Caches the permissions in the entity cache if it is enabled.
	 *
	 * @param permissions the permissions
	 */
	public void cacheResult(List<Permission> permissions) {
		for (Permission permission : permissions) {
			if (EntityCacheUtil.getResult(
						PermissionModelImpl.ENTITY_CACHE_ENABLED,
						PermissionImpl.class, permission.getPrimaryKey()) == null) {
				cacheResult(permission);
			}
		}
	}

	/**
	 * Clears the cache for all permissions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PermissionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Permission permission) {
		EntityCacheUtil.removeResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
			new Object[] {
				permission.getActionId(),
				Long.valueOf(permission.getResourceId())
			});
	}

	/**
	 * Creates a new permission with the primary key. Does not add the permission to the database.
	 *
	 * @param permissionId the primary key for the new permission
	 * @return the new permission
	 */
	public Permission create(long permissionId) {
		Permission permission = new PermissionImpl();

		permission.setNew(true);
		permission.setPrimaryKey(permissionId);

		return permission;
	}

	/**
	 * Removes the permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the permission
	 * @return the permission that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Permission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionId the primary key of the permission
	 * @return the permission that was removed
	 * @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission remove(long permissionId)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Permission permission = (Permission)session.get(PermissionImpl.class,
					Long.valueOf(permissionId));

			if (permission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + permissionId);
				}

				throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					permissionId);
			}

			return permissionPersistence.remove(permission);
		}
		catch (NoSuchPermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permission the permission
	 * @return the permission that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Permission remove(Permission permission) throws SystemException {
		return super.remove(permission);
	}

	@Override
	protected Permission removeImpl(Permission permission)
		throws SystemException {
		permission = toUnwrappedModel(permission);

		try {
			clearGroups.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}

		try {
			clearRoles.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}

		try {
			clearUsers.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, permission);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PermissionModelImpl permissionModelImpl = (PermissionModelImpl)permission;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
			new Object[] {
				permissionModelImpl.getActionId(),
				Long.valueOf(permissionModelImpl.getResourceId())
			});

		EntityCacheUtil.removeResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey());

		return permission;
	}

	@Override
	public Permission updateImpl(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws SystemException {
		permission = toUnwrappedModel(permission);

		boolean isNew = permission.isNew();

		PermissionModelImpl permissionModelImpl = (PermissionModelImpl)permission;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, permission, merge);

			permission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey(), permission);

		if (!isNew &&
				(!Validator.equals(permission.getActionId(),
					permissionModelImpl.getOriginalActionId()) ||
				(permission.getResourceId() != permissionModelImpl.getOriginalResourceId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
				new Object[] {
					permissionModelImpl.getOriginalActionId(),
					Long.valueOf(permissionModelImpl.getOriginalResourceId())
				});
		}

		if (isNew ||
				(!Validator.equals(permission.getActionId(),
					permissionModelImpl.getOriginalActionId()) ||
				(permission.getResourceId() != permissionModelImpl.getOriginalResourceId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
				new Object[] {
					permission.getActionId(),
					Long.valueOf(permission.getResourceId())
				}, permission);
		}

		return permission;
	}

	protected Permission toUnwrappedModel(Permission permission) {
		if (permission instanceof PermissionImpl) {
			return permission;
		}

		PermissionImpl permissionImpl = new PermissionImpl();

		permissionImpl.setNew(permission.isNew());
		permissionImpl.setPrimaryKey(permission.getPrimaryKey());

		permissionImpl.setPermissionId(permission.getPermissionId());
		permissionImpl.setCompanyId(permission.getCompanyId());
		permissionImpl.setActionId(permission.getActionId());
		permissionImpl.setResourceId(permission.getResourceId());

		return permissionImpl;
	}

	/**
	 * Returns the permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission
	 * @return the permission
	 * @throws com.liferay.portal.NoSuchModelException if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Permission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the permission with the primary key or throws a {@link com.liferay.portal.NoSuchPermissionException} if it could not be found.
	 *
	 * @param permissionId the primary key of the permission
	 * @return the permission
	 * @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission findByPrimaryKey(long permissionId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByPrimaryKey(permissionId);

		if (permission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + permissionId);
			}

			throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				permissionId);
		}

		return permission;
	}

	/**
	 * Returns the permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission
	 * @return the permission, or <code>null</code> if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Permission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the permission
	 * @return the permission, or <code>null</code> if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission fetchByPrimaryKey(long permissionId)
		throws SystemException {
		Permission permission = (Permission)EntityCacheUtil.getResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
				PermissionImpl.class, permissionId);

		if (permission == _nullPermission) {
			return null;
		}

		if (permission == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				permission = (Permission)session.get(PermissionImpl.class,
						Long.valueOf(permissionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (permission != null) {
					cacheResult(permission);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
						PermissionImpl.class, permissionId, _nullPermission);
				}

				closeSession(session);
			}
		}

		return permission;
	}

	/**
	 * Returns all the permissions where resourceId = &#63;.
	 *
	 * @param resourceId the resource ID
	 * @return the matching permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findByResourceId(long resourceId)
		throws SystemException {
		return findByResourceId(resourceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the permissions where resourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceId the resource ID
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @return the range of matching permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findByResourceId(long resourceId, int start, int end)
		throws SystemException {
		return findByResourceId(resourceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the permissions where resourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceId the resource ID
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findByResourceId(long resourceId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				resourceId,
				
				start, end, orderByComparator
			};

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RESOURCEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_PERMISSION_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				list = (List<Permission>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_RESOURCEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RESOURCEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first permission in the ordered set where resourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceId the resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching permission
	 * @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission findByResourceId_First(long resourceId,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		List<Permission> list = findByResourceId(resourceId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last permission in the ordered set where resourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceId the resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching permission
	 * @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission findByResourceId_Last(long resourceId,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		int count = countByResourceId(resourceId);

		List<Permission> list = findByResourceId(resourceId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the permissions before and after the current permission in the ordered set where resourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param permissionId the primary key of the current permission
	 * @param resourceId the resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next permission
	 * @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission[] findByResourceId_PrevAndNext(long permissionId,
		long resourceId, OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByPrimaryKey(permissionId);

		Session session = null;

		try {
			session = openSession();

			Permission[] array = new PermissionImpl[3];

			array[0] = getByResourceId_PrevAndNext(session, permission,
					resourceId, orderByComparator, true);

			array[1] = permission;

			array[2] = getByResourceId_PrevAndNext(session, permission,
					resourceId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Permission getByResourceId_PrevAndNext(Session session,
		Permission permission, long resourceId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PERMISSION_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(permission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Permission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the permission where actionId = &#63; and resourceId = &#63; or throws a {@link com.liferay.portal.NoSuchPermissionException} if it could not be found.
	 *
	 * @param actionId the action ID
	 * @param resourceId the resource ID
	 * @return the matching permission
	 * @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission findByA_R(String actionId, long resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByA_R(actionId, resourceId);

		if (permission == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("actionId=");
			msg.append(actionId);

			msg.append(", resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPermissionException(msg.toString());
		}

		return permission;
	}

	/**
	 * Returns the permission where actionId = &#63; and resourceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param actionId the action ID
	 * @param resourceId the resource ID
	 * @return the matching permission, or <code>null</code> if a matching permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission fetchByA_R(String actionId, long resourceId)
		throws SystemException {
		return fetchByA_R(actionId, resourceId, true);
	}

	/**
	 * Returns the permission where actionId = &#63; and resourceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param actionId the action ID
	 * @param resourceId the resource ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching permission, or <code>null</code> if a matching permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Permission fetchByA_R(String actionId, long resourceId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { actionId, resourceId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A_R,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_PERMISSION_WHERE);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_A_R_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_A_R_RESOURCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(resourceId);

				List<Permission> list = q.list();

				result = list;

				Permission permission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
						finderArgs, list);
				}
				else {
					permission = list.get(0);

					cacheResult(permission);

					if ((permission.getActionId() == null) ||
							!permission.getActionId().equals(actionId) ||
							(permission.getResourceId() != resourceId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
							finderArgs, permission);
					}
				}

				return permission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Permission)result;
			}
		}
	}

	/**
	 * Returns all the permissions.
	 *
	 * @return the permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @return the range of permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Permission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PERMISSION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Permission>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Permission>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the permissions where resourceId = &#63; from the database.
	 *
	 * @param resourceId the resource ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByResourceId(long resourceId) throws SystemException {
		for (Permission permission : findByResourceId(resourceId)) {
			permissionPersistence.remove(permission);
		}
	}

	/**
	 * Removes the permission where actionId = &#63; and resourceId = &#63; from the database.
	 *
	 * @param actionId the action ID
	 * @param resourceId the resource ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByA_R(String actionId, long resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByA_R(actionId, resourceId);

		permissionPersistence.remove(permission);
	}

	/**
	 * Removes all the permissions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Permission permission : findAll()) {
			permissionPersistence.remove(permission);
		}
	}

	/**
	 * Returns the number of permissions where resourceId = &#63;.
	 *
	 * @param resourceId the resource ID
	 * @return the number of matching permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByResourceId(long resourceId) throws SystemException {
		Object[] finderArgs = new Object[] { resourceId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RESOURCEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PERMISSION_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RESOURCEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of permissions where actionId = &#63; and resourceId = &#63;.
	 *
	 * @param actionId the action ID
	 * @param resourceId the resource ID
	 * @return the number of matching permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByA_R(String actionId, long resourceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { actionId, resourceId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PERMISSION_WHERE);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_A_R_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_A_R_RESOURCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(resourceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of permissions.
	 *
	 * @return the number of permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PERMISSION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the groups associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the groups associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @return the range of groups associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			com.liferay.portal.model.impl.GroupImpl.class,
			PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME,
			"getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the groups associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETGROUPS.concat(ORDER_BY_CLAUSE)
										.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETGROUPS.concat(com.liferay.portal.model.impl.GroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_GROUPS,
						finderArgs);
				}
				else {
					groupPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			Long.class,
			PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME,
			"getGroupsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of groups associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the number of groups associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			Boolean.class,
			PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME,
			"containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the permission; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, groupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the permission has any groups associated with it.
	 *
	 * @param pk the primary key of the permission to check for associations with groups
	 * @return <code>true</code> if the permission has any groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Clears all associations between the permission and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission to clear the associated groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groupPKs the primary keys of the groups to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			Set<Long> groupPKSet = SetUtil.fromArray(groupPKs);

			List<com.liferay.portal.model.Group> groups = getGroups(pk);

			for (com.liferay.portal.model.Group group : groups) {
				if (!groupPKSet.remove(group.getPrimaryKey())) {
					removeGroup.remove(pk, group.getPrimaryKey());
				}
			}

			for (Long groupPK : groupPKSet) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param groups the groups to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_GROUPS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Returns all the roles associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the roles associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @return the range of roles associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			com.liferay.portal.model.impl.RoleImpl.class,
			PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the roles associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETROLES.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETROLES.concat(com.liferay.portal.model.impl.RoleModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ROLES,
						finderArgs);
				}
				else {
					rolePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			Long.class,
			PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"getRolesSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of roles associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the number of roles associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			Boolean.class,
			PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME,
			"containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the role is associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the permission; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, rolePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the permission has any roles associated with it.
	 *
	 * @param pk the primary key of the permission to check for associations with roles
	 * @return <code>true</code> if the permission has any roles associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Clears all associations between the permission and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission to clear the associated roles from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the roles associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param rolePKs the primary keys of the roles to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.remove(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the roles associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param roles the roles to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_ROLES_PERMISSIONS_NAME);
		}
	}

	/**
	 * Returns all the users associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the users associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @return the range of users associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			com.liferay.portal.model.impl.UserImpl.class,
			PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the users associated with the permission.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the permission
	 * @param start the lower bound of the range of permissions
	 * @param end the upper bound of the range of permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERS,
						finderArgs);
				}
				else {
					userPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			Long.class,
			PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"getUsersSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of users associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @return the number of users associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			Boolean.class,
			PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the permission.
	 *
	 * @param pk the primary key of the permission
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the permission; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, userPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the permission has any users associated with it.
	 *
	 * @param pk the primary key of the permission to check for associations with users
	 * @return <code>true</code> if the permission has any users associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Clears all associations between the permission and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission to clear the associated users from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the users associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param userPKs the primary keys of the users to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.remove(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the users associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the permission
	 * @param users the users to be associated with the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Initializes the permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Permission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Permission>> listenersList = new ArrayList<ModelListener<Permission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Permission>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Permissions (permissionId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long groupId)
			throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(permissionId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE permissionId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(permissionId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(permissionId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Roles_Permissions (permissionId, roleId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long roleId)
			throws SystemException {
			if (!_persistenceImpl.containsRole.contains(permissionId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE permissionId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(permissionId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(permissionId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(userId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUser {
		protected AddUser(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Permissions (permissionId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long userId)
			throws SystemException {
			if (!_persistenceImpl.containsUser.contains(permissionId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(userId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE permissionId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(permissionId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE permissionId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(permissionId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(userId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_PERMISSION = "SELECT permission FROM Permission permission";
	private static final String _SQL_SELECT_PERMISSION_WHERE = "SELECT permission FROM Permission permission WHERE ";
	private static final String _SQL_COUNT_PERMISSION = "SELECT COUNT(permission) FROM Permission permission";
	private static final String _SQL_COUNT_PERMISSION_WHERE = "SELECT COUNT(permission) FROM Permission permission WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Permissions ON (Groups_Permissions.groupId = Group_.groupId) WHERE (Groups_Permissions.permissionId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Roles_Permissions ON (Roles_Permissions.roleId = Role_.roleId) WHERE (Roles_Permissions.permissionId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Permissions ON (Users_Permissions.userId = User_.userId) WHERE (Users_Permissions.permissionId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_RESOURCEID_RESOURCEID_2 = "permission.resourceId = ?";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_1 = "permission.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_2 = "permission.actionId = ? AND ";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_3 = "(permission.actionId IS NULL OR permission.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_A_R_RESOURCEID_2 = "permission.resourceId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "permission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Permission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Permission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PermissionPersistenceImpl.class);
	private static Permission _nullPermission = new PermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Permission> toCacheModel() {
				return _nullPermissionCacheModel;
			}
		};

	private static CacheModel<Permission> _nullPermissionCacheModel = new CacheModel<Permission>() {
			public Permission toEntityModel() {
				return _nullPermission;
			}
		};
}