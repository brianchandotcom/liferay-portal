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
import com.liferay.portal.NoSuchOrgGroupPermissionException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.model.impl.OrgGroupPermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the org group permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupPermissionPersistence
 * @see OrgGroupPermissionUtil
 * @generated
 */
public class OrgGroupPermissionPersistenceImpl extends BasePersistenceImpl<OrgGroupPermission>
	implements OrgGroupPermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OrgGroupPermissionUtil} to access the org group permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OrgGroupPermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			OrgGroupPermissionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PERMISSIONID =
		new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPermissionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PERMISSIONID =
		new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPermissionId",
			new String[] { Long.class.getName() },
			OrgGroupPermissionModelImpl.PERMISSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PERMISSIONID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPermissionId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			OrgGroupPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the org group permission in the entity cache if it is enabled.
	 *
	 * @param orgGroupPermission the org group permission
	 */
	public void cacheResult(OrgGroupPermission orgGroupPermission) {
		EntityCacheUtil.putResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey(),
			orgGroupPermission);

		orgGroupPermission.resetOriginalValues();
	}

	/**
	 * Caches the org group permissions in the entity cache if it is enabled.
	 *
	 * @param orgGroupPermissions the org group permissions
	 */
	public void cacheResult(List<OrgGroupPermission> orgGroupPermissions) {
		for (OrgGroupPermission orgGroupPermission : orgGroupPermissions) {
			if (EntityCacheUtil.getResult(
						OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
						OrgGroupPermissionImpl.class,
						orgGroupPermission.getPrimaryKey()) == null) {
				cacheResult(orgGroupPermission);
			}
		}
	}

	/**
	 * Clears the cache for all org group permissions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(OrgGroupPermissionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(OrgGroupPermissionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the org group permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OrgGroupPermission orgGroupPermission) {
		EntityCacheUtil.removeResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Creates a new org group permission with the primary key. Does not add the org group permission to the database.
	 *
	 * @param orgGroupPermissionPK the primary key for the new org group permission
	 * @return the new org group permission
	 */
	public OrgGroupPermission create(OrgGroupPermissionPK orgGroupPermissionPK) {
		OrgGroupPermission orgGroupPermission = new OrgGroupPermissionImpl();

		orgGroupPermission.setNew(true);
		orgGroupPermission.setPrimaryKey(orgGroupPermissionPK);

		return orgGroupPermission;
	}

	/**
	 * Removes the org group permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the org group permission
	 * @return the org group permission that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupPermission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((OrgGroupPermissionPK)primaryKey);
	}

	/**
	 * Removes the org group permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orgGroupPermissionPK the primary key of the org group permission
	 * @return the org group permission that was removed
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission remove(OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
					orgGroupPermissionPK);

			if (orgGroupPermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						orgGroupPermissionPK);
				}

				throw new NoSuchOrgGroupPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					orgGroupPermissionPK);
			}

			return orgGroupPermissionPersistence.remove(orgGroupPermission);
		}
		catch (NoSuchOrgGroupPermissionException nsee) {
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
	 * Removes the org group permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orgGroupPermission the org group permission
	 * @return the org group permission that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupPermission remove(OrgGroupPermission orgGroupPermission)
		throws SystemException {
		return super.remove(orgGroupPermission);
	}

	@Override
	protected OrgGroupPermission removeImpl(
		OrgGroupPermission orgGroupPermission) throws SystemException {
		orgGroupPermission = toUnwrappedModel(orgGroupPermission);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, orgGroupPermission);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		EntityCacheUtil.removeResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey());

		return orgGroupPermission;
	}

	@Override
	public OrgGroupPermission updateImpl(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission,
		boolean merge) throws SystemException {
		orgGroupPermission = toUnwrappedModel(orgGroupPermission);

		boolean isNew = orgGroupPermission.isNew();

		OrgGroupPermissionModelImpl orgGroupPermissionModelImpl = (OrgGroupPermissionModelImpl)orgGroupPermission;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, orgGroupPermission, merge);

			orgGroupPermission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !OrgGroupPermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((orgGroupPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(orgGroupPermissionModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((orgGroupPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PERMISSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(orgGroupPermissionModelImpl.getOriginalPermissionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PERMISSIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PERMISSIONID,
					args);
			}
		}

		EntityCacheUtil.putResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey(),
			orgGroupPermission);

		return orgGroupPermission;
	}

	protected OrgGroupPermission toUnwrappedModel(
		OrgGroupPermission orgGroupPermission) {
		if (orgGroupPermission instanceof OrgGroupPermissionImpl) {
			return orgGroupPermission;
		}

		OrgGroupPermissionImpl orgGroupPermissionImpl = new OrgGroupPermissionImpl();

		orgGroupPermissionImpl.setNew(orgGroupPermission.isNew());
		orgGroupPermissionImpl.setPrimaryKey(orgGroupPermission.getPrimaryKey());

		orgGroupPermissionImpl.setOrganizationId(orgGroupPermission.getOrganizationId());
		orgGroupPermissionImpl.setGroupId(orgGroupPermission.getGroupId());
		orgGroupPermissionImpl.setPermissionId(orgGroupPermission.getPermissionId());

		return orgGroupPermissionImpl;
	}

	/**
	 * Returns the org group permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the org group permission
	 * @return the org group permission
	 * @throws com.liferay.portal.NoSuchModelException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((OrgGroupPermissionPK)primaryKey);
	}

	/**
	 * Returns the org group permission with the primary key or throws a {@link com.liferay.portal.NoSuchOrgGroupPermissionException} if it could not be found.
	 *
	 * @param orgGroupPermissionPK the primary key of the org group permission
	 * @return the org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission findByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = fetchByPrimaryKey(orgGroupPermissionPK);

		if (orgGroupPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					orgGroupPermissionPK);
			}

			throw new NoSuchOrgGroupPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				orgGroupPermissionPK);
		}

		return orgGroupPermission;
	}

	/**
	 * Returns the org group permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the org group permission
	 * @return the org group permission, or <code>null</code> if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgGroupPermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((OrgGroupPermissionPK)primaryKey);
	}

	/**
	 * Returns the org group permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orgGroupPermissionPK the primary key of the org group permission
	 * @return the org group permission, or <code>null</code> if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission fetchByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK) throws SystemException {
		OrgGroupPermission orgGroupPermission = (OrgGroupPermission)EntityCacheUtil.getResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
				OrgGroupPermissionImpl.class, orgGroupPermissionPK);

		if (orgGroupPermission == _nullOrgGroupPermission) {
			return null;
		}

		if (orgGroupPermission == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
						orgGroupPermissionPK);
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (orgGroupPermission != null) {
					cacheResult(orgGroupPermission);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
						OrgGroupPermissionImpl.class, orgGroupPermissionPK,
						_nullOrgGroupPermission);
				}

				closeSession(session);
			}
		}

		return orgGroupPermission;
	}

	/**
	 * Returns all the org group permissions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group permissions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @return the range of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group permissions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(finderPath,
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

			query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first org group permission in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last org group permission in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByGroupId(groupId);

		List<OrgGroupPermission> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the org group permissions before and after the current org group permission in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param orgGroupPermissionPK the primary key of the current org group permission
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission[] findByGroupId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, orgGroupPermission,
					groupId, orderByComparator, true);

			array[1] = orgGroupPermission;

			array[2] = getByGroupId_PrevAndNext(session, orgGroupPermission,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgGroupPermission getByGroupId_PrevAndNext(Session session,
		OrgGroupPermission orgGroupPermission, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(orgGroupPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the org group permissions where permissionId = &#63;.
	 *
	 * @param permissionId the permission ID
	 * @return the matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByPermissionId(long permissionId)
		throws SystemException {
		return findByPermissionId(permissionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group permissions where permissionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param permissionId the permission ID
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @return the range of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end) throws SystemException {
		return findByPermissionId(permissionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group permissions where permissionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param permissionId the permission ID
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PERMISSIONID;
			finderArgs = new Object[] { permissionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PERMISSIONID;
			finderArgs = new Object[] {
					permissionId,
					
					start, end, orderByComparator
				};
		}

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(finderPath,
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

			query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

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

				qPos.add(permissionId);

				list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first org group permission in the ordered set where permissionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param permissionId the permission ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission findByPermissionId_First(long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByPermissionId(permissionId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("permissionId=");
			msg.append(permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last org group permission in the ordered set where permissionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param permissionId the permission ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission findByPermissionId_Last(long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByPermissionId(permissionId);

		List<OrgGroupPermission> list = findByPermissionId(permissionId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("permissionId=");
			msg.append(permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the org group permissions before and after the current org group permission in the ordered set where permissionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param orgGroupPermissionPK the primary key of the current org group permission
	 * @param permissionId the permission ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next org group permission
	 * @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgGroupPermission[] findByPermissionId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = getByPermissionId_PrevAndNext(session,
					orgGroupPermission, permissionId, orderByComparator, true);

			array[1] = orgGroupPermission;

			array[2] = getByPermissionId_PrevAndNext(session,
					orgGroupPermission, permissionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgGroupPermission getByPermissionId_PrevAndNext(
		Session session, OrgGroupPermission orgGroupPermission,
		long permissionId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(permissionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(orgGroupPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the org group permissions.
	 *
	 * @return the org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org group permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @return the range of org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the org group permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of org group permissions
	 * @param end the upper bound of the range of org group permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgGroupPermission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ORGGROUPPERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ORGGROUPPERMISSION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<OrgGroupPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<OrgGroupPermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the org group permissions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByGroupId(groupId)) {
			orgGroupPermissionPersistence.remove(orgGroupPermission);
		}
	}

	/**
	 * Removes all the org group permissions where permissionId = &#63; from the database.
	 *
	 * @param permissionId the permission ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPermissionId(long permissionId)
		throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByPermissionId(
				permissionId)) {
			orgGroupPermissionPersistence.remove(orgGroupPermission);
		}
	}

	/**
	 * Removes all the org group permissions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findAll()) {
			orgGroupPermissionPersistence.remove(orgGroupPermission);
		}
	}

	/**
	 * Returns the number of org group permissions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGGROUPPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of org group permissions where permissionId = &#63;.
	 *
	 * @param permissionId the permission ID
	 * @return the number of matching org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPermissionId(long permissionId) throws SystemException {
		Object[] finderArgs = new Object[] { permissionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PERMISSIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGGROUPPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PERMISSIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of org group permissions.
	 *
	 * @return the number of org group permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ORGGROUPPERMISSION);

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
	 * Initializes the org group permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.OrgGroupPermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OrgGroupPermission>> listenersList = new ArrayList<ModelListener<OrgGroupPermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<OrgGroupPermission>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(OrgGroupPermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
	private static final String _SQL_SELECT_ORGGROUPPERMISSION = "SELECT orgGroupPermission FROM OrgGroupPermission orgGroupPermission";
	private static final String _SQL_SELECT_ORGGROUPPERMISSION_WHERE = "SELECT orgGroupPermission FROM OrgGroupPermission orgGroupPermission WHERE ";
	private static final String _SQL_COUNT_ORGGROUPPERMISSION = "SELECT COUNT(orgGroupPermission) FROM OrgGroupPermission orgGroupPermission";
	private static final String _SQL_COUNT_ORGGROUPPERMISSION_WHERE = "SELECT COUNT(orgGroupPermission) FROM OrgGroupPermission orgGroupPermission WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "orgGroupPermission.id.groupId = ?";
	private static final String _FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2 = "orgGroupPermission.id.permissionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "orgGroupPermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OrgGroupPermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OrgGroupPermission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(OrgGroupPermissionPersistenceImpl.class);
	private static OrgGroupPermission _nullOrgGroupPermission = new OrgGroupPermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<OrgGroupPermission> toCacheModel() {
				return _nullOrgGroupPermissionCacheModel;
			}
		};

	private static CacheModel<OrgGroupPermission> _nullOrgGroupPermissionCacheModel =
		new CacheModel<OrgGroupPermission>() {
			public OrgGroupPermission toEntityModel() {
				return _nullOrgGroupPermission;
			}
		};
}