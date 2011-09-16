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
import com.liferay.portal.NoSuchResourceTypePermissionException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.model.impl.ResourceTypePermissionImpl;
import com.liferay.portal.model.impl.ResourceTypePermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class ResourceTypePermissionPersistenceImpl extends BasePersistenceImpl<ResourceTypePermission>
	implements ResourceTypePermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceTypePermissionUtil} to access the resource type permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceTypePermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class, FINDER_CLASS_NAME_LIST,
			"findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class, FINDER_CLASS_NAME_LIST,
			"findByC_N_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_N_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_G_N_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_N_R = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_G_N_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED,
			ResourceTypePermissionImpl.class, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the resource type permission in the entity cache if it is enabled.
	 *
	 * @param resourceTypePermission the resource type permission
	 */
	public void cacheResult(ResourceTypePermission resourceTypePermission) {
		EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey(), resourceTypePermission);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
			new Object[] {
				Long.valueOf(resourceTypePermission.getCompanyId()),
				Long.valueOf(resourceTypePermission.getGroupId()),
				
			resourceTypePermission.getName(),
				Long.valueOf(resourceTypePermission.getRoleId())
			}, resourceTypePermission);

		resourceTypePermission.resetOriginalValues();
	}

	/**
	 * Caches the resource type permissions in the entity cache if it is enabled.
	 *
	 * @param resourceTypePermissions the resource type permissions
	 */
	public void cacheResult(
		List<ResourceTypePermission> resourceTypePermissions) {
		for (ResourceTypePermission resourceTypePermission : resourceTypePermissions) {
			if (EntityCacheUtil.getResult(
						ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceTypePermissionImpl.class,
						resourceTypePermission.getPrimaryKey()) == null) {
				cacheResult(resourceTypePermission);
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

		EntityCacheUtil.clearCache(ResourceTypePermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
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

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R,
			new Object[] {
				Long.valueOf(resourceTypePermission.getCompanyId()),
				Long.valueOf(resourceTypePermission.getGroupId()),
				
			resourceTypePermission.getName(),
				Long.valueOf(resourceTypePermission.getRoleId())
			});
	}

	/**
	 * Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	 *
	 * @param resourceTypePermissionId the primary key for the new resource type permission
	 * @return the new resource type permission
	 */
	public ResourceTypePermission create(long resourceTypePermissionId) {
		ResourceTypePermission resourceTypePermission = new ResourceTypePermissionImpl();

		resourceTypePermission.setNew(true);
		resourceTypePermission.setPrimaryKey(resourceTypePermissionId);

		return resourceTypePermission;
	}

	/**
	 * Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceTypePermission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission that was removed
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission remove(long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceTypePermission resourceTypePermission = (ResourceTypePermission)session.get(ResourceTypePermissionImpl.class,
					Long.valueOf(resourceTypePermissionId));

			if (resourceTypePermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						resourceTypePermissionId);
				}

				throw new NoSuchResourceTypePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceTypePermissionId);
			}

			return resourceTypePermissionPersistence.remove(resourceTypePermission);
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

	/**
	 * Removes the resource type permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceTypePermission the resource type permission
	 * @return the resource type permission that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceTypePermission remove(
		ResourceTypePermission resourceTypePermission)
		throws SystemException {
		return super.remove(resourceTypePermission);
	}

	@Override
	protected ResourceTypePermission removeImpl(
		ResourceTypePermission resourceTypePermission)
		throws SystemException {
		resourceTypePermission = toUnwrappedModel(resourceTypePermission);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, resourceTypePermission);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R,
			new Object[] {
				Long.valueOf(resourceTypePermissionModelImpl.getCompanyId()),
				Long.valueOf(resourceTypePermissionModelImpl.getGroupId()),
				
			resourceTypePermissionModelImpl.getName(),
				Long.valueOf(resourceTypePermissionModelImpl.getRoleId())
			});

		EntityCacheUtil.removeResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey());

		return resourceTypePermission;
	}

	@Override
	public ResourceTypePermission updateImpl(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission,
		boolean merge) throws SystemException {
		resourceTypePermission = toUnwrappedModel(resourceTypePermission);

		boolean isNew = resourceTypePermission.isNew();

		ResourceTypePermissionModelImpl resourceTypePermissionModelImpl = (ResourceTypePermissionModelImpl)resourceTypePermission;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourceTypePermission, merge);

			resourceTypePermission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceTypePermissionImpl.class,
			resourceTypePermission.getPrimaryKey(), resourceTypePermission);

		if (!isNew &&
				((resourceTypePermission.getCompanyId() != resourceTypePermissionModelImpl.getOriginalCompanyId()) ||
				(resourceTypePermission.getGroupId() != resourceTypePermissionModelImpl.getOriginalGroupId()) ||
				!Validator.equals(resourceTypePermission.getName(),
					resourceTypePermissionModelImpl.getOriginalName()) ||
				(resourceTypePermission.getRoleId() != resourceTypePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R,
				new Object[] {
					Long.valueOf(
						resourceTypePermissionModelImpl.getOriginalCompanyId()),
					Long.valueOf(
						resourceTypePermissionModelImpl.getOriginalGroupId()),
					
				resourceTypePermissionModelImpl.getOriginalName(),
					Long.valueOf(
						resourceTypePermissionModelImpl.getOriginalRoleId())
				});
		}

		if (isNew ||
				((resourceTypePermission.getCompanyId() != resourceTypePermissionModelImpl.getOriginalCompanyId()) ||
				(resourceTypePermission.getGroupId() != resourceTypePermissionModelImpl.getOriginalGroupId()) ||
				!Validator.equals(resourceTypePermission.getName(),
					resourceTypePermissionModelImpl.getOriginalName()) ||
				(resourceTypePermission.getRoleId() != resourceTypePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
				new Object[] {
					Long.valueOf(resourceTypePermission.getCompanyId()),
					Long.valueOf(resourceTypePermission.getGroupId()),
					
				resourceTypePermission.getName(),
					Long.valueOf(resourceTypePermission.getRoleId())
				}, resourceTypePermission);
		}

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
	 * @throws com.liferay.portal.NoSuchModelException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceTypePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByPrimaryKey(
		long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException, SystemException {
		ResourceTypePermission resourceTypePermission = fetchByPrimaryKey(resourceTypePermissionId);

		if (resourceTypePermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceTypePermissionId);
			}

			throw new NoSuchResourceTypePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceTypePermissionId);
		}

		return resourceTypePermission;
	}

	/**
	 * Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource type permission
	 * @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceTypePermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceTypePermissionId the primary key of the resource type permission
	 * @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission fetchByPrimaryKey(
		long resourceTypePermissionId) throws SystemException {
		ResourceTypePermission resourceTypePermission = (ResourceTypePermission)EntityCacheUtil.getResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceTypePermissionImpl.class, resourceTypePermissionId);

		if (resourceTypePermission == _nullResourceTypePermission) {
			return null;
		}

		if (resourceTypePermission == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				resourceTypePermission = (ResourceTypePermission)session.get(ResourceTypePermissionImpl.class,
						Long.valueOf(resourceTypePermissionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (resourceTypePermission != null) {
					cacheResult(resourceTypePermission);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ResourceTypePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceTypePermissionImpl.class,
						resourceTypePermissionId, _nullResourceTypePermission);
				}

				closeSession(session);
			}
		}

		return resourceTypePermission;
	}

	/**
	 * Returns all the resource type permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByRoleId(long roleId)
		throws SystemException {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByRoleId(long roleId, int start,
		int end) throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { roleId, start, end, orderByComparator };

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
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

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

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

				qPos.add(roleId);

				list = (List<ResourceTypePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ROLEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource type permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
		List<ResourceTypePermission> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceTypePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last resource type permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
		int count = countByRoleId(roleId);

		List<ResourceTypePermission> list = findByRoleId(roleId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceTypePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the resource type permissions before and after the current resource type permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceTypePermissionId the primary key of the current resource type permission
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission[] findByRoleId_PrevAndNext(
		long resourceTypePermissionId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
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
		OrderByComparator orderByComparator, boolean previous) {
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

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourceTypePermission);

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
	 * Returns all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId) throws SystemException {
		return findByC_N_R(companyId, name, roleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end) throws SystemException {
		return findByC_N_R(companyId, name, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, name, roleId,
				
				start, end, orderByComparator
			};

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_N_R,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_R_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

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

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(roleId);

				list = (List<ResourceTypePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_N_R,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_N_R,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByC_N_R_First(long companyId,
		String name, long roleId, OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
		List<ResourceTypePermission> list = findByC_N_R(companyId, name,
				roleId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
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
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByC_N_R_Last(long companyId, String name,
		long roleId, OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
		int count = countByC_N_R(companyId, name, roleId);

		List<ResourceTypePermission> list = findByC_N_R(companyId, name,
				roleId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
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
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the resource type permissions before and after the current resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceTypePermissionId the primary key of the current resource type permission
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission[] findByC_N_R_PrevAndNext(
		long resourceTypePermissionId, long companyId, String name,
		long roleId, OrderByComparator orderByComparator)
		throws NoSuchResourceTypePermissionException, SystemException {
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
		String name, long roleId, OrderByComparator orderByComparator,
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

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_R_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_N_R_NAME_2);
			}
		}

		query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

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

		qPos.add(companyId);

		if (name != null) {
			qPos.add(name);
		}

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourceTypePermission);

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
	 * Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the matching resource type permission
	 * @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission findByC_G_N_R(long companyId, long groupId,
		String name, long roleId)
		throws NoSuchResourceTypePermissionException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId) throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_G_N_R,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_R_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_G_N_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_G_N_R_NAME_2);
				}
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

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(roleId);

				List<ResourceTypePermission> list = q.list();

				result = list;

				ResourceTypePermission resourceTypePermission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_G_N_R,
						finderArgs, list);
				}
				else {
					resourceTypePermission = list.get(0);

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

				return resourceTypePermission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_G_N_R,
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
				return (ResourceTypePermission)result;
			}
		}
	}

	/**
	 * Returns all the resource type permissions.
	 *
	 * @return the resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource type permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @return the range of resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource type permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource type permissions
	 * @param end the upper bound of the range of resource type permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceTypePermission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<ResourceTypePermission> list = (List<ResourceTypePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourceTypePermission>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the resource type permissions where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRoleId(long roleId) throws SystemException {
		for (ResourceTypePermission resourceTypePermission : findByRoleId(
				roleId)) {
			resourceTypePermissionPersistence.remove(resourceTypePermission);
		}
	}

	/**
	 * Removes all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_N_R(long companyId, String name, long roleId)
		throws SystemException {
		for (ResourceTypePermission resourceTypePermission : findByC_N_R(
				companyId, name, roleId)) {
			resourceTypePermissionPersistence.remove(resourceTypePermission);
		}
	}

	/**
	 * Removes the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_G_N_R(long companyId, long groupId, String name,
		long roleId)
		throws NoSuchResourceTypePermissionException, SystemException {
		ResourceTypePermission resourceTypePermission = findByC_G_N_R(companyId,
				groupId, name, roleId);

		resourceTypePermissionPersistence.remove(resourceTypePermission);
	}

	/**
	 * Removes all the resource type permissions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ResourceTypePermission resourceTypePermission : findAll()) {
			resourceTypePermissionPersistence.remove(resourceTypePermission);
		}
	}

	/**
	 * Returns the number of resource type permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N_R(long companyId, String name, long roleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_R_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_R_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource type permissions where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param name the name
	 * @param roleId the role ID
	 * @return the number of matching resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_G_N_R(long companyId, long groupId, String name,
		long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, groupId, name, roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_G_N_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCETYPEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_G_N_R_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_N_R_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_G_N_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_G_N_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_G_N_R_NAME_2);
				}
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

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_G_N_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource type permissions.
	 *
	 * @return the number of resource type permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCETYPEPERMISSION);

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
	 * Initializes the resource type permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceTypePermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceTypePermission>> listenersList = new ArrayList<ModelListener<ResourceTypePermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceTypePermission>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceTypePermissionImpl.class.getName());
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
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_SELECT_RESOURCETYPEPERMISSION_WHERE = "SELECT resourceTypePermission FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission";
	private static final String _SQL_COUNT_RESOURCETYPEPERMISSION_WHERE = "SELECT COUNT(resourceTypePermission) FROM ResourceTypePermission resourceTypePermission WHERE ";
	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "resourceTypePermission.roleId = ?";
	private static final String _FINDER_COLUMN_C_N_R_COMPANYID_2 = "resourceTypePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_1 = "resourceTypePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";
	private static final String _FINDER_COLUMN_C_G_N_R_COMPANYID_2 = "resourceTypePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_GROUPID_2 = "resourceTypePermission.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_1 = "resourceTypePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_2 = "resourceTypePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_NAME_3 = "(resourceTypePermission.name IS NULL OR resourceTypePermission.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_G_N_R_ROLEID_2 = "resourceTypePermission.roleId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceTypePermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceTypePermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceTypePermission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourceTypePermissionPersistenceImpl.class);
	private static ResourceTypePermission _nullResourceTypePermission = new ResourceTypePermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ResourceTypePermission> toCacheModel() {
				return _nullResourceTypePermissionCacheModel;
			}
		};

	private static CacheModel<ResourceTypePermission> _nullResourceTypePermissionCacheModel =
		new CacheModel<ResourceTypePermission>() {
			public ResourceTypePermission toEntityModel() {
				return _nullResourceTypePermission;
			}
		};
}