/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchUserGroupException;
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
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupImpl;
import com.liferay.portal.model.impl.UserGroupModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the user group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupPersistence
 * @see UserGroupUtil
 * @generated
 */
public class UserGroupPersistenceImpl extends BasePersistenceImpl<UserGroup>
	implements UserGroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserGroupUtil} to access the user group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			UserGroupModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserGroupModelImpl.COMPANYID_COLUMN_BITMASK |
			UserGroupModelImpl.PARENTUSERGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			UserGroupModelImpl.COMPANYID_COLUMN_BITMASK |
			UserGroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, UserGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the user group in the entity cache if it is enabled.
	 *
	 * @param userGroup the user group
	 */
	public void cacheResult(UserGroup userGroup) {
		EntityCacheUtil.putResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupImpl.class, userGroup.getPrimaryKey(), userGroup);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(userGroup.getCompanyId()),
				
			userGroup.getName()
			}, userGroup);

		userGroup.resetOriginalValues();
	}

	/**
	 * Caches the user groups in the entity cache if it is enabled.
	 *
	 * @param userGroups the user groups
	 */
	public void cacheResult(List<UserGroup> userGroups) {
		for (UserGroup userGroup : userGroups) {
			if (EntityCacheUtil.getResult(
						UserGroupModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupImpl.class, userGroup.getPrimaryKey()) == null) {
				cacheResult(userGroup);
			}
			else {
				userGroup.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserGroupImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserGroupImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserGroup userGroup) {
		EntityCacheUtil.removeResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupImpl.class, userGroup.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(userGroup);
	}

	@Override
	public void clearCache(List<UserGroup> userGroups) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserGroup userGroup : userGroups) {
			EntityCacheUtil.removeResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupImpl.class, userGroup.getPrimaryKey());

			clearUniqueFindersCache(userGroup);
		}
	}

	protected void clearUniqueFindersCache(UserGroup userGroup) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(userGroup.getCompanyId()),
				
			userGroup.getName()
			});
	}

	/**
	 * Creates a new user group with the primary key. Does not add the user group to the database.
	 *
	 * @param userGroupId the primary key for the new user group
	 * @return the new user group
	 */
	public UserGroup create(long userGroupId) {
		UserGroup userGroup = new UserGroupImpl();

		userGroup.setNew(true);
		userGroup.setPrimaryKey(userGroupId);

		return userGroup;
	}

	/**
	 * Removes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupId the primary key of the user group
	 * @return the user group that was removed
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup remove(long userGroupId)
		throws NoSuchUserGroupException, SystemException {
		return remove(Long.valueOf(userGroupId));
	}

	/**
	 * Removes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user group
	 * @return the user group that was removed
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup remove(Serializable primaryKey)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroup userGroup = (UserGroup)session.get(UserGroupImpl.class,
					primaryKey);

			if (userGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userGroup);
		}
		catch (NoSuchUserGroupException nsee) {
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
	protected UserGroup removeImpl(UserGroup userGroup)
		throws SystemException {
		userGroup = toUnwrappedModel(userGroup);

		try {
			clearGroups.clear(userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}

		try {
			clearTeams.clear(userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}

		try {
			clearUsers.clear(userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (userGroup.isCachedModel()) {
				userGroup = (UserGroup)session.get(UserGroupImpl.class,
						userGroup.getPrimaryKeyObj());
			}

			if (userGroup != null) {
				session.delete(userGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userGroup != null) {
			clearCache(userGroup);
		}

		return userGroup;
	}

	@Override
	public UserGroup updateImpl(com.liferay.portal.model.UserGroup userGroup)
		throws SystemException {
		userGroup = toUnwrappedModel(userGroup);

		boolean isNew = userGroup.isNew();

		UserGroupModelImpl userGroupModelImpl = (UserGroupModelImpl)userGroup;

		Session session = null;

		try {
			session = openSession();

			if (userGroup.isNew()) {
				session.save(userGroup);

				userGroup.setNew(false);
			}
			else {
				session.merge(userGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserGroupModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userGroupModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(userGroupModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((userGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userGroupModelImpl.getOriginalCompanyId()),
						Long.valueOf(userGroupModelImpl.getOriginalParentUserGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						Long.valueOf(userGroupModelImpl.getCompanyId()),
						Long.valueOf(userGroupModelImpl.getParentUserGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}
		}

		EntityCacheUtil.putResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupImpl.class, userGroup.getPrimaryKey(), userGroup);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					Long.valueOf(userGroup.getCompanyId()),
					
				userGroup.getName()
				}, userGroup);
		}
		else {
			if ((userGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userGroupModelImpl.getOriginalCompanyId()),
						
						userGroupModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
					new Object[] {
						Long.valueOf(userGroup.getCompanyId()),
						
					userGroup.getName()
					}, userGroup);
			}
		}

		return userGroup;
	}

	protected UserGroup toUnwrappedModel(UserGroup userGroup) {
		if (userGroup instanceof UserGroupImpl) {
			return userGroup;
		}

		UserGroupImpl userGroupImpl = new UserGroupImpl();

		userGroupImpl.setNew(userGroup.isNew());
		userGroupImpl.setPrimaryKey(userGroup.getPrimaryKey());

		userGroupImpl.setUserGroupId(userGroup.getUserGroupId());
		userGroupImpl.setCompanyId(userGroup.getCompanyId());
		userGroupImpl.setParentUserGroupId(userGroup.getParentUserGroupId());
		userGroupImpl.setName(userGroup.getName());
		userGroupImpl.setDescription(userGroup.getDescription());
		userGroupImpl.setAddedByLDAPImport(userGroup.isAddedByLDAPImport());

		return userGroupImpl;
	}

	/**
	 * Returns the user group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group
	 * @return the user group
	 * @throws com.liferay.portal.NoSuchModelException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user group with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	 *
	 * @param userGroupId the primary key of the user group
	 * @return the user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByPrimaryKey(long userGroupId)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByPrimaryKey(userGroupId);

		if (userGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userGroupId);
			}

			throw new NoSuchUserGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userGroupId);
		}

		return userGroup;
	}

	/**
	 * Returns the user group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group
	 * @return the user group, or <code>null</code> if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroup fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupId the primary key of the user group
	 * @return the user group, or <code>null</code> if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByPrimaryKey(long userGroupId)
		throws SystemException {
		UserGroup userGroup = (UserGroup)EntityCacheUtil.getResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupImpl.class, userGroupId);

		if (userGroup == _nullUserGroup) {
			return null;
		}

		if (userGroup == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userGroup = (UserGroup)session.get(UserGroupImpl.class,
						Long.valueOf(userGroupId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userGroup != null) {
					cacheResult(userGroup);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserGroupModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupImpl.class, userGroupId, _nullUserGroup);
				}

				closeSession(session);
			}
		}

		return userGroup;
	}

	/**
	 * Returns all the user groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<UserGroup> list = (List<UserGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserGroup userGroup : list) {
				if ((companyId != userGroup.getCompanyId())) {
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

			query.append(_SQL_SELECT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<UserGroup>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first user group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (userGroup != null) {
			return userGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupException(msg.toString());
	}

	/**
	 * Returns the first user group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserGroup> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByCompanyId_Last(companyId, orderByComparator);

		if (userGroup != null) {
			return userGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupException(msg.toString());
	}

	/**
	 * Returns the last user group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<UserGroup> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user groups before and after the current user group in the ordered set where companyId = &#63;.
	 *
	 * @param userGroupId the primary key of the current user group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup[] findByCompanyId_PrevAndNext(long userGroupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = findByPrimaryKey(userGroupId);

		Session session = null;

		try {
			session = openSession();

			UserGroup[] array = new UserGroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, userGroup,
					companyId, orderByComparator, true);

			array[1] = userGroup;

			array[2] = getByCompanyId_PrevAndNext(session, userGroup,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroup getByCompanyId_PrevAndNext(Session session,
		UserGroup userGroup, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUP_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(UserGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByCompanyId(long companyId)
		throws SystemException {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user groups that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(UserGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, UserGroupImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, UserGroupImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<UserGroup>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param userGroupId the primary key of the current user group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup[] filterFindByCompanyId_PrevAndNext(long userGroupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(userGroupId, companyId,
				orderByComparator);
		}

		UserGroup userGroup = findByPrimaryKey(userGroupId);

		Session session = null;

		try {
			session = openSession();

			UserGroup[] array = new UserGroupImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session, userGroup,
					companyId, orderByComparator, true);

			array[1] = userGroup;

			array[2] = filterGetByCompanyId_PrevAndNext(session, userGroup,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroup filterGetByCompanyId_PrevAndNext(Session session,
		UserGroup userGroup, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(UserGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, UserGroupImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, UserGroupImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @return the matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByC_P(long companyId, long parentUserGroupId)
		throws SystemException {
		return findByC_P(companyId, parentUserGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByC_P(long companyId, long parentUserGroupId,
		int start, int end) throws SystemException {
		return findByC_P(companyId, parentUserGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findByC_P(long companyId, long parentUserGroupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { companyId, parentUserGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					companyId, parentUserGroupId,
					
					start, end, orderByComparator
				};
		}

		List<UserGroup> list = (List<UserGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserGroup userGroup : list) {
				if ((companyId != userGroup.getCompanyId()) ||
						(parentUserGroupId != userGroup.getParentUserGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentUserGroupId);

				list = (List<UserGroup>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByC_P_First(long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByC_P_First(companyId, parentUserGroupId,
				orderByComparator);

		if (userGroup != null) {
			return userGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentUserGroupId=");
		msg.append(parentUserGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupException(msg.toString());
	}

	/**
	 * Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByC_P_First(long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserGroup> list = findByC_P(companyId, parentUserGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByC_P_Last(long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByC_P_Last(companyId, parentUserGroupId,
				orderByComparator);

		if (userGroup != null) {
			return userGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentUserGroupId=");
		msg.append(parentUserGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserGroupException(msg.toString());
	}

	/**
	 * Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByC_P_Last(long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_P(companyId, parentUserGroupId);

		List<UserGroup> list = findByC_P(companyId, parentUserGroupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user groups before and after the current user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param userGroupId the primary key of the current user group
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup[] findByC_P_PrevAndNext(long userGroupId, long companyId,
		long parentUserGroupId, OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = findByPrimaryKey(userGroupId);

		Session session = null;

		try {
			session = openSession();

			UserGroup[] array = new UserGroupImpl[3];

			array[0] = getByC_P_PrevAndNext(session, userGroup, companyId,
					parentUserGroupId, orderByComparator, true);

			array[1] = userGroup;

			array[2] = getByC_P_PrevAndNext(session, userGroup, companyId,
					parentUserGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroup getByC_P_PrevAndNext(Session session,
		UserGroup userGroup, long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUP_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

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
			query.append(UserGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentUserGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @return the matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByC_P(long companyId,
		long parentUserGroupId) throws SystemException {
		return filterFindByC_P(companyId, parentUserGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByC_P(long companyId,
		long parentUserGroupId, int start, int end) throws SystemException {
		return filterFindByC_P(companyId, parentUserGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups that the user has permissions to view where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> filterFindByC_P(long companyId,
		long parentUserGroupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_P(companyId, parentUserGroupId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(UserGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, UserGroupImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, UserGroupImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(parentUserGroupId);

			return (List<UserGroup>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param userGroupId the primary key of the current user group
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup[] filterFindByC_P_PrevAndNext(long userGroupId,
		long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_P_PrevAndNext(userGroupId, companyId,
				parentUserGroupId, orderByComparator);
		}

		UserGroup userGroup = findByPrimaryKey(userGroupId);

		Session session = null;

		try {
			session = openSession();

			UserGroup[] array = new UserGroupImpl[3];

			array[0] = filterGetByC_P_PrevAndNext(session, userGroup,
					companyId, parentUserGroupId, orderByComparator, true);

			array[1] = userGroup;

			array[2] = filterGetByC_P_PrevAndNext(session, userGroup,
					companyId, parentUserGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroup filterGetByC_P_PrevAndNext(Session session,
		UserGroup userGroup, long companyId, long parentUserGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(UserGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(UserGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, UserGroupImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, UserGroupImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentUserGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the user group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching user group
	 * @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup findByC_N(long companyId, String name)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByC_N(companyId, name);

		if (userGroup == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserGroupException(msg.toString());
		}

		return userGroup;
	}

	/**
	 * Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user group, or <code>null</code> if a matching user group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof UserGroup) {
			UserGroup userGroup = (UserGroup)result;

			if ((companyId != userGroup.getCompanyId()) ||
					!Validator.equals(name, userGroup.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			query.append(UserGroupModelImpl.ORDER_BY_JPQL);

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

				List<UserGroup> list = q.list();

				result = list;

				UserGroup userGroup = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					userGroup = list.get(0);

					cacheResult(userGroup);

					if ((userGroup.getCompanyId() != companyId) ||
							(userGroup.getName() == null) ||
							!userGroup.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, userGroup);
					}
				}

				return userGroup;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
				return (UserGroup)result;
			}
		}
	}

	/**
	 * Returns all the user groups.
	 *
	 * @return the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroup> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<UserGroup> list = (List<UserGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERGROUP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERGROUP.concat(UserGroupModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserGroup>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserGroup>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the user groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (UserGroup userGroup : findByCompanyId(companyId)) {
			remove(userGroup);
		}
	}

	/**
	 * Removes all the user groups where companyId = &#63; and parentUserGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P(long companyId, long parentUserGroupId)
		throws SystemException {
		for (UserGroup userGroup : findByC_P(companyId, parentUserGroupId)) {
			remove(userGroup);
		}
	}

	/**
	 * Removes the user group where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the user group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroup removeByC_N(long companyId, String name)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = findByC_N(companyId, name);

		return remove(userGroup);
	}

	/**
	 * Removes all the user groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserGroup userGroup : findAll()) {
			remove(userGroup);
		}
	}

	/**
	 * Returns the number of user groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByCompanyId(long companyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_USERGROUP_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of user groups where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @return the number of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P(long companyId, long parentUserGroupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, parentUserGroupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentUserGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentUserGroupId the parent user group ID
	 * @return the number of matching user groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByC_P(long companyId, long parentUserGroupId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_P(companyId, parentUserGroupId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_USERGROUP_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTUSERGROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				UserGroup.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(parentUserGroupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of user groups where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching user groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUP_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
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

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user groups.
	 *
	 * @return the number of user groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERGROUP);

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
	 * Returns all the groups associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the groups associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of groups associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			com.liferay.portal.model.impl.GroupImpl.class,
			UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_GROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the user group
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
			UserGroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			Long.class,
			UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"getGroupsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_GROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of groups associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the number of groups associated with the user group
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
			UserGroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			Boolean.class,
			UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME,
			"containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the user group; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the user group has any groups associated with it.
	 *
	 * @param pk the primary key of the user group to check for associations with groups
	 * @return <code>true</code> if the user group has any groups associated with it; <code>false</code> otherwise
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
	 * Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user group and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group to clear the associated groups from
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param groupPKs the primary keys of the groups to be associated with the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param groups the groups to be associated with the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Returns all the teams associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the teams associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk)
		throws SystemException {
		return getTeams(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the teams associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of teams associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end) throws SystemException {
		return getTeams(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS,
			com.liferay.portal.model.impl.TeamImpl.class,
			UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME, "getTeams",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_TEAMS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the teams associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Team> list = (List<com.liferay.portal.model.Team>)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETTEAMS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETTEAMS.concat(com.liferay.portal.model.impl.TeamModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Team", com.liferay.portal.model.impl.TeamImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Team>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_TEAMS,
						finderArgs);
				}
				else {
					teamPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS_SIZE = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS,
			Long.class, UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME,
			"getTeamsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_TEAMS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of teams associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the number of teams associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public int getTeamsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTEAMSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TEAM = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERGROUPS_TEAMS,
			Boolean.class,
			UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME,
			"containsTeam",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the team is associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if the team is associated with the user group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeam(long pk, long teamPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, teamPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TEAM,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTeam.contains(pk, teamPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TEAM,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user group has any teams associated with it.
	 *
	 * @param pk the primary key of the user group to check for associations with teams
	 * @return <code>true</code> if the user group has any teams associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeams(long pk) throws SystemException {
		if (getTeamsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, long teamPK) throws SystemException {
		try {
			addTeam.add(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			addTeam.add(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				addTeam.add(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Clears all associations between the user group and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group to clear the associated teams from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearTeams(long pk) throws SystemException {
		try {
			clearTeams.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, long teamPK) throws SystemException {
		try {
			removeTeam.remove(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			removeTeam.remove(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				removeTeam.remove(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				removeTeam.remove(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teamPKs the primary keys of the teams to be associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			Set<Long> teamPKSet = SetUtil.fromArray(teamPKs);

			List<com.liferay.portal.model.Team> teams = getTeams(pk);

			for (com.liferay.portal.model.Team team : teams) {
				if (!teamPKSet.remove(team.getPrimaryKey())) {
					removeTeam.remove(pk, team.getPrimaryKey());
				}
			}

			for (Long teamPK : teamPKSet) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param teams the teams to be associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			long[] teamPKs = new long[teams.size()];

			for (int i = 0; i < teams.size(); i++) {
				com.liferay.portal.model.Team team = teams.get(i);

				teamPKs[i] = team.getPrimaryKey();
			}

			setTeams(pk, teamPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERGROUPS_TEAMS_NAME);
		}
	}

	/**
	 * Returns all the users associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the users associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @return the range of users associated with the user group
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			com.liferay.portal.model.impl.UserImpl.class,
			UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the users associated with the user group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user group
	 * @param start the lower bound of the range of user groups
	 * @param end the upper bound of the range of user groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the user group
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
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			Long.class, UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"getUsersSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of users associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @return the number of users associated with the user group
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
			UserGroupModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			Boolean.class,
			UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the user group.
	 *
	 * @param pk the primary key of the user group
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the user group; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the user group has any users associated with it.
	 *
	 * @param pk the primary key of the user group to check for associations with users
	 * @return <code>true</code> if the user group has any users associated with it; <code>false</code> otherwise
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
	 * Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group to clear the associated users from
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param userPKs the primary keys of the users to be associated with the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user group
	 * @param users the users to be associated with the user group
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
			FinderCacheUtil.clearCache(UserGroupModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Initializes the user group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserGroup")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserGroup>> listenersList = new ArrayList<ModelListener<UserGroup>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserGroup>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup();

		addGroup = new AddGroup();
		clearGroups = new ClearGroups();
		removeGroup = new RemoveGroup();

		containsTeam = new ContainsTeam();

		addTeam = new AddTeam();
		clearTeams = new ClearTeams();
		removeTeam = new RemoveTeam();

		containsUser = new ContainsUser();

		addUser = new AddUser();
		clearUsers = new ClearUsers();
		removeUser = new RemoveUser();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserGroupImpl.class.getName());
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
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
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
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsTeam containsTeam;
	protected AddTeam addTeam;
	protected ClearTeams clearTeams;
	protected RemoveTeam removeTeam;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userGroupId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userGroupId), new Long(groupId)
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
		protected AddGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_UserGroups (userGroupId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userGroupId, long groupId)
			throws SystemException {
			if (!containsGroup.contains(userGroupId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeAddAssociation(userGroupId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(groupId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterAddAssociation(userGroupId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearGroups {
		protected ClearGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE userGroupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userGroupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(userGroupId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onBeforeRemoveAssociation(userGroupId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userGroupId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onAfterRemoveAssociation(userGroupId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE userGroupId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userGroupId, long groupId)
			throws SystemException {
			if (containsGroup.contains(userGroupId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(groupId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsTeam {
		protected ContainsTeam() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTEAM,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userGroupId, long teamId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userGroupId), new Long(teamId)
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

	protected class AddTeam {
		protected AddTeam() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO UserGroups_Teams (userGroupId, teamId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userGroupId, long teamId)
			throws SystemException {
			if (!containsTeam.contains(userGroupId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeAddAssociation(userGroupId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeAddAssociation(teamId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(teamId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterAddAssociation(userGroupId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterAddAssociation(teamId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearTeams {
		protected ClearTeams() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM UserGroups_Teams WHERE userGroupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userGroupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

			List<com.liferay.portal.model.Team> teams = null;

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				teams = getTeams(userGroupId);

				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onBeforeRemoveAssociation(userGroupId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onBeforeRemoveAssociation(team.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userGroupId) });

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onAfterRemoveAssociation(userGroupId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onAfterRemoveAssociation(team.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTeam {
		protected RemoveTeam() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM UserGroups_Teams WHERE userGroupId = ? AND teamId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userGroupId, long teamId)
			throws SystemException {
			if (containsTeam.contains(userGroupId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeRemoveAssociation(teamId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(teamId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterRemoveAssociation(teamId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUser {
		protected ContainsUser() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userGroupId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userGroupId), new Long(userId)
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
		protected AddUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_UserGroups (userGroupId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userGroupId, long userId)
			throws SystemException {
			if (!containsUser.contains(userGroupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeAddAssociation(userGroupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(userId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterAddAssociation(userGroupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUsers {
		protected ClearUsers() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userGroupId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userGroupId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(userGroupId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onBeforeRemoveAssociation(userGroupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userGroupId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<UserGroup> listener : listeners) {
						listener.onAfterRemoveAssociation(userGroupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							UserGroup.class.getName(), userGroupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userGroupId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userGroupId, long userId)
			throws SystemException {
			if (containsUser.contains(userGroupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						UserGroup.class.getName(), userGroupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userGroupId), new Long(userId)
					});

				for (ModelListener<UserGroup> listener : listeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						UserGroup.class.getName(), userGroupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_USERGROUP = "SELECT userGroup FROM UserGroup userGroup";
	private static final String _SQL_SELECT_USERGROUP_WHERE = "SELECT userGroup FROM UserGroup userGroup WHERE ";
	private static final String _SQL_COUNT_USERGROUP = "SELECT COUNT(userGroup) FROM UserGroup userGroup";
	private static final String _SQL_COUNT_USERGROUP_WHERE = "SELECT COUNT(userGroup) FROM UserGroup userGroup WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_UserGroups ON (Groups_UserGroups.groupId = Group_.groupId) WHERE (Groups_UserGroups.userGroupId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE userGroupId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE userGroupId = ? AND groupId = ?";
	private static final String _SQL_GETTEAMS = "SELECT {Team.*} FROM Team INNER JOIN UserGroups_Teams ON (UserGroups_Teams.teamId = Team.teamId) WHERE (UserGroups_Teams.userGroupId = ?)";
	private static final String _SQL_GETTEAMSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM UserGroups_Teams WHERE userGroupId = ?";
	private static final String _SQL_CONTAINSTEAM = "SELECT COUNT(*) AS COUNT_VALUE FROM UserGroups_Teams WHERE userGroupId = ? AND teamId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_UserGroups ON (Users_UserGroups.userId = User_.userId) WHERE (Users_UserGroups.userGroupId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "userGroup.companyId = ?";
	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "userGroup.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PARENTUSERGROUPID_2 = "userGroup.parentUserGroupId = ?";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "userGroup.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "userGroup.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "userGroup.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(userGroup.name IS NULL OR userGroup.name = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "userGroup.userGroupId";
	private static final String _FILTER_SQL_SELECT_USERGROUP_WHERE = "SELECT DISTINCT {userGroup.*} FROM UserGroup userGroup WHERE ";
	private static final String _FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {UserGroup.*} FROM (SELECT DISTINCT userGroup.userGroupId FROM UserGroup userGroup WHERE ";
	private static final String _FILTER_SQL_SELECT_USERGROUP_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN UserGroup ON TEMP_TABLE.userGroupId = UserGroup.userGroupId";
	private static final String _FILTER_SQL_COUNT_USERGROUP_WHERE = "SELECT COUNT(DISTINCT userGroup.userGroupId) AS COUNT_VALUE FROM UserGroup userGroup WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "userGroup";
	private static final String _FILTER_ENTITY_TABLE = "UserGroup";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroup.";
	private static final String _ORDER_BY_ENTITY_TABLE = "UserGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserGroup exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserGroup exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserGroupPersistenceImpl.class);
	private static UserGroup _nullUserGroup = new UserGroupImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserGroup> toCacheModel() {
				return _nullUserGroupCacheModel;
			}
		};

	private static CacheModel<UserGroup> _nullUserGroupCacheModel = new CacheModel<UserGroup>() {
			public UserGroup toEntityModel() {
				return _nullUserGroup;
			}
		};
}