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
import com.liferay.portal.NoSuchUserGroupGroupRoleException;
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
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the user group group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRolePersistence
 * @see UserGroupGroupRoleUtil
 * @generated
 */
public class UserGroupGroupRolePersistenceImpl extends BasePersistenceImpl<UserGroupGroupRole>
	implements UserGroupGroupRolePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserGroupGroupRoleUtil} to access the user group group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserGroupGroupRoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST,
			"findByUserGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERGROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUserGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST,
			"findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the user group group role in the entity cache if it is enabled.
	 *
	 * @param userGroupGroupRole the user group group role
	 */
	public void cacheResult(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);

		userGroupGroupRole.resetOriginalValues();
	}

	/**
	 * Caches the user group group roles in the entity cache if it is enabled.
	 *
	 * @param userGroupGroupRoles the user group group roles
	 */
	public void cacheResult(List<UserGroupGroupRole> userGroupGroupRoles) {
		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			if (EntityCacheUtil.getResult(
						UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupGroupRoleImpl.class,
						userGroupGroupRole.getPrimaryKey()) == null) {
				cacheResult(userGroupGroupRole);
			}
		}
	}

	/**
	 * Clears the cache for all user group group roles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserGroupGroupRoleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserGroupGroupRoleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the user group group role.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserGroupGroupRole userGroupGroupRole) {
		EntityCacheUtil.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);
	}

	/**
	 * Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	 *
	 * @param userGroupGroupRolePK the primary key for the new user group group role
	 * @return the new user group group role
	 */
	public UserGroupGroupRole create(UserGroupGroupRolePK userGroupGroupRolePK) {
		UserGroupGroupRole userGroupGroupRole = new UserGroupGroupRoleImpl();

		userGroupGroupRole.setNew(true);
		userGroupGroupRole.setPrimaryKey(userGroupGroupRolePK);

		return userGroupGroupRole;
	}

	/**
	 * Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroupGroupRole remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((UserGroupGroupRolePK)primaryKey);
	}

	/**
	 * Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role that was removed
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole remove(UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
					userGroupGroupRolePK);

			if (userGroupGroupRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userGroupGroupRolePK);
				}

				throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userGroupGroupRolePK);
			}

			return userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
		catch (NoSuchUserGroupGroupRoleException nsee) {
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
	 * Removes the user group group role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupGroupRole the user group group role
	 * @return the user group group role that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroupGroupRole remove(UserGroupGroupRole userGroupGroupRole)
		throws SystemException {
		return super.remove(userGroupGroupRole);
	}

	@Override
	protected UserGroupGroupRole removeImpl(
		UserGroupGroupRole userGroupGroupRole) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, userGroupGroupRole);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey());

		return userGroupGroupRole;
	}

	@Override
	public UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws SystemException {
		userGroupGroupRole = toUnwrappedModel(userGroupGroupRole);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userGroupGroupRole, merge);

			userGroupGroupRole.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
			UserGroupGroupRoleImpl.class, userGroupGroupRole.getPrimaryKey(),
			userGroupGroupRole);

		return userGroupGroupRole;
	}

	protected UserGroupGroupRole toUnwrappedModel(
		UserGroupGroupRole userGroupGroupRole) {
		if (userGroupGroupRole instanceof UserGroupGroupRoleImpl) {
			return userGroupGroupRole;
		}

		UserGroupGroupRoleImpl userGroupGroupRoleImpl = new UserGroupGroupRoleImpl();

		userGroupGroupRoleImpl.setNew(userGroupGroupRole.isNew());
		userGroupGroupRoleImpl.setPrimaryKey(userGroupGroupRole.getPrimaryKey());

		userGroupGroupRoleImpl.setUserGroupId(userGroupGroupRole.getUserGroupId());
		userGroupGroupRoleImpl.setGroupId(userGroupGroupRole.getGroupId());
		userGroupGroupRoleImpl.setRoleId(userGroupGroupRole.getRoleId());

		return userGroupGroupRoleImpl;
	}

	/**
	 * Returns the user group group role with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role
	 * @throws com.liferay.portal.NoSuchModelException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroupGroupRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	/**
	 * Returns the user group group role with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupGroupRoleException} if it could not be found.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = fetchByPrimaryKey(userGroupGroupRolePK);

		if (userGroupGroupRole == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userGroupGroupRolePK);
			}

			throw new NoSuchUserGroupGroupRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userGroupGroupRolePK);
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserGroupGroupRole fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((UserGroupGroupRolePK)primaryKey);
	}

	/**
	 * Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupGroupRolePK the primary key of the user group group role
	 * @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole fetchByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK) throws SystemException {
		UserGroupGroupRole userGroupGroupRole = (UserGroupGroupRole)EntityCacheUtil.getResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
				UserGroupGroupRoleImpl.class, userGroupGroupRolePK);

		if (userGroupGroupRole == _nullUserGroupGroupRole) {
			return null;
		}

		if (userGroupGroupRole == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userGroupGroupRole = (UserGroupGroupRole)session.get(UserGroupGroupRoleImpl.class,
						userGroupGroupRolePK);
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userGroupGroupRole != null) {
					cacheResult(userGroupGroupRole);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserGroupGroupRoleModelImpl.ENTITY_CACHE_ENABLED,
						UserGroupGroupRoleImpl.class, userGroupGroupRolePK,
						_nullUserGroupGroupRole);
				}

				closeSession(session);
			}
		}

		return userGroupGroupRole;
	}

	/**
	 * Returns all the user group group roles where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @return the matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId)
		throws SystemException {
		return findByUserGroupId(userGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end) throws SystemException {
		return findByUserGroupId(userGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByUserGroupId(long userGroupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userGroupId,
				
				start, end, orderByComparator
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERGROUPID,
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

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

				qPos.add(userGroupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERGROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERGROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByUserGroupId_First(long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByUserGroupId_Last(long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByUserGroupId(userGroupId);

		List<UserGroupGroupRole> list = findByUserGroupId(userGroupId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByUserGroupId_PrevAndNext(session,
					userGroupGroupRole, userGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByUserGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

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

		qPos.add(userGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user group group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group group role in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByGroupId(groupId);

		List<UserGroupGroupRole> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
					groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByGroupId_PrevAndNext(session, userGroupGroupRole,
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

	protected UserGroupGroupRole getByGroupId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user group group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByRoleId(long roleId)
		throws SystemException {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { roleId, start, end, orderByComparator };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
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

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
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
	 * Returns the first user group group role in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user group group role in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByRoleId(roleId);

		List<UserGroupGroupRole> list = findByRoleId(roleId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
					roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByRoleId_PrevAndNext(session, userGroupGroupRole,
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

	protected UserGroupGroupRole getByRoleId_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @return the matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId)
		throws SystemException {
		return findByU_G(userGroupId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end) throws SystemException {
		return findByU_G(userGroupId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByU_G(long userGroupId, long groupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userGroupId, groupId,
				
				start, end, orderByComparator
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_G,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

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

				qPos.add(userGroupId);

				qPos.add(groupId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_G,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_G,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByU_G_First(long userGroupId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByU_G_Last(long userGroupId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByU_G(userGroupId, groupId);

		List<UserGroupGroupRole> list = findByU_G(userGroupId, groupId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userGroupId=");
			msg.append(userGroupId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole[] findByU_G_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByU_G_PrevAndNext(session, userGroupGroupRole,
					userGroupId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByU_G_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long userGroupId, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

		query.append(_FINDER_COLUMN_U_G_GROUPID_2);

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

		qPos.add(userGroupId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId)
		throws SystemException {
		return findByG_R(groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end) throws SystemException {
		return findByG_R(groupId, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findByG_R(long groupId, long roleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, roleId,
				
				start, end, orderByComparator
			};

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_R,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_ROLEID_2);

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

				qPos.add(roleId);

				list = (List<UserGroupGroupRole>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_R,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_R,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByG_R_First(long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole findByG_R_Last(long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		int count = countByG_R(groupId, roleId);

		List<UserGroupGroupRole> list = findByG_R(groupId, roleId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserGroupGroupRoleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userGroupGroupRolePK the primary key of the current user group group role
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group group role
	 * @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserGroupGroupRole[] findByG_R_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchUserGroupGroupRoleException, SystemException {
		UserGroupGroupRole userGroupGroupRole = findByPrimaryKey(userGroupGroupRolePK);

		Session session = null;

		try {
			session = openSession();

			UserGroupGroupRole[] array = new UserGroupGroupRoleImpl[3];

			array[0] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, true);

			array[1] = userGroupGroupRole;

			array[2] = getByG_R_PrevAndNext(session, userGroupGroupRole,
					groupId, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserGroupGroupRole getByG_R_PrevAndNext(Session session,
		UserGroupGroupRole userGroupGroupRole, long groupId, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERGROUPGROUPROLE_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_ROLEID_2);

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

		qPos.add(groupId);

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userGroupGroupRole);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserGroupGroupRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user group group roles.
	 *
	 * @return the user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @return the range of user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user group group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group group roles
	 * @param end the upper bound of the range of user group group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserGroupGroupRole> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<UserGroupGroupRole> list = (List<UserGroupGroupRole>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERGROUPGROUPROLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERGROUPGROUPROLE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserGroupGroupRole>)QueryUtil.list(q,
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
	 * Removes all the user group group roles where userGroupId = &#63; from the database.
	 *
	 * @param userGroupId the user group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserGroupId(long userGroupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByUserGroupId(
				userGroupId)) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Removes all the user group group roles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByGroupId(groupId)) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Removes all the user group group roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRoleId(long roleId) throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByRoleId(roleId)) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Removes all the user group group roles where userGroupId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_G(long userGroupId, long groupId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByU_G(userGroupId,
				groupId)) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Removes all the user group group roles where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_R(long groupId, long roleId)
		throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findByG_R(groupId, roleId)) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Removes all the user group group roles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserGroupGroupRole userGroupGroupRole : findAll()) {
			userGroupGroupRolePersistence.remove(userGroupGroupRole);
		}
	}

	/**
	 * Returns the number of user group group roles where userGroupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @return the number of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserGroupId(long userGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { userGroupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERGROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_USERGROUPID_USERGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user group group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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
	 * Returns the number of user group group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

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
	 * Returns the number of user group group roles where userGroupId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupId the user group ID
	 * @param groupId the group ID
	 * @return the number of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_G(long userGroupId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userGroupId, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERGROUPID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user group group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_R(long groupId, long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERGROUPGROUPROLE_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user group group roles.
	 *
	 * @return the number of user group group roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERGROUPGROUPROLE);

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
	 * Initializes the user group group role persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserGroupGroupRole")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserGroupGroupRole>> listenersList = new ArrayList<ModelListener<UserGroupGroupRole>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserGroupGroupRole>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserGroupGroupRoleImpl.class.getName());
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
	private static final String _SQL_SELECT_USERGROUPGROUPROLE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_SELECT_USERGROUPGROUPROLE_WHERE = "SELECT userGroupGroupRole FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole";
	private static final String _SQL_COUNT_USERGROUPGROUPROLE_WHERE = "SELECT COUNT(userGroupGroupRole) FROM UserGroupGroupRole userGroupGroupRole WHERE ";
	private static final String _FINDER_COLUMN_USERGROUPID_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";
	private static final String _FINDER_COLUMN_U_G_USERGROUPID_2 = "userGroupGroupRole.id.userGroupId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_GROUPID_2 = "userGroupGroupRole.id.groupId = ?";
	private static final String _FINDER_COLUMN_G_R_GROUPID_2 = "userGroupGroupRole.id.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_R_ROLEID_2 = "userGroupGroupRole.id.roleId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userGroupGroupRole.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserGroupGroupRole exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserGroupGroupRole exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserGroupGroupRolePersistenceImpl.class);
	private static UserGroupGroupRole _nullUserGroupGroupRole = new UserGroupGroupRoleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserGroupGroupRole> toCacheModel() {
				return _nullUserGroupGroupRoleCacheModel;
			}
		};

	private static CacheModel<UserGroupGroupRole> _nullUserGroupGroupRoleCacheModel =
		new CacheModel<UserGroupGroupRole>() {
			public UserGroupGroupRole toEntityModel() {
				return _nullUserGroupGroupRole;
			}
		};
}