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
import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the user tracker path service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPathPersistence
 * @see UserTrackerPathUtil
 * @generated
 */
public class UserTrackerPathPersistenceImpl extends BasePersistenceImpl<UserTrackerPath>
	implements UserTrackerPathPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserTrackerPathUtil} to access the user tracker path persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserTrackerPathImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERTRACKERID =
		new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			UserTrackerPathImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserTrackerId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID =
		new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			UserTrackerPathImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserTrackerId",
			new String[] { Long.class.getName() },
			UserTrackerPathModelImpl.USERTRACKERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserTrackerId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			UserTrackerPathImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			UserTrackerPathImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the user tracker path in the entity cache if it is enabled.
	 *
	 * @param userTrackerPath the user tracker path
	 */
	public void cacheResult(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);

		userTrackerPath.resetOriginalValues();
	}

	/**
	 * Caches the user tracker paths in the entity cache if it is enabled.
	 *
	 * @param userTrackerPaths the user tracker paths
	 */
	public void cacheResult(List<UserTrackerPath> userTrackerPaths) {
		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			if (EntityCacheUtil.getResult(
						UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKey()) == null) {
				cacheResult(userTrackerPath);
			}
			else {
				userTrackerPath.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user tracker paths.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserTrackerPathImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserTrackerPathImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user tracker path.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserTrackerPath> userTrackerPaths) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey());
		}
	}

	/**
	 * Creates a new user tracker path with the primary key. Does not add the user tracker path to the database.
	 *
	 * @param userTrackerPathId the primary key for the new user tracker path
	 * @return the new user tracker path
	 */
	public UserTrackerPath create(long userTrackerPathId) {
		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();

		userTrackerPath.setNew(true);
		userTrackerPath.setPrimaryKey(userTrackerPathId);

		return userTrackerPath;
	}

	/**
	 * Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path that was removed
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath remove(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		return remove(Long.valueOf(userTrackerPathId));
	}

	/**
	 * Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path that was removed
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserTrackerPath remove(Serializable primaryKey)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPath userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
					primaryKey);

			if (userTrackerPath == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userTrackerPath);
		}
		catch (NoSuchUserTrackerPathException nsee) {
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
	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			if (userTrackerPath.isCachedModel()) {
				userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKeyObj());
			}

			if (userTrackerPath != null) {
				session.delete(userTrackerPath);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userTrackerPath != null) {
			clearCache(userTrackerPath);
		}

		return userTrackerPath;
	}

	@Override
	public UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		boolean isNew = userTrackerPath.isNew();

		UserTrackerPathModelImpl userTrackerPathModelImpl = (UserTrackerPathModelImpl)userTrackerPath;

		Session session = null;

		try {
			session = openSession();

			if (userTrackerPath.isNew()) {
				session.save(userTrackerPath);

				userTrackerPath.setNew(false);
			}
			else {
				session.merge(userTrackerPath);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserTrackerPathModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userTrackerPathModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userTrackerPathModelImpl.getOriginalUserTrackerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID,
					args);

				args = new Object[] {
						Long.valueOf(userTrackerPathModelImpl.getUserTrackerId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID,
					args);
			}
		}

		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);

		return userTrackerPath;
	}

	protected UserTrackerPath toUnwrappedModel(UserTrackerPath userTrackerPath) {
		if (userTrackerPath instanceof UserTrackerPathImpl) {
			return userTrackerPath;
		}

		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setNew(userTrackerPath.isNew());
		userTrackerPathImpl.setPrimaryKey(userTrackerPath.getPrimaryKey());

		userTrackerPathImpl.setUserTrackerPathId(userTrackerPath.getUserTrackerPathId());
		userTrackerPathImpl.setUserTrackerId(userTrackerPath.getUserTrackerId());
		userTrackerPathImpl.setPath(userTrackerPath.getPath());
		userTrackerPathImpl.setPathDate(userTrackerPath.getPathDate());

		return userTrackerPathImpl;
	}

	/**
	 * Returns the user tracker path with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws com.liferay.portal.NoSuchModelException if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserTrackerPath findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user tracker path with the primary key or throws a {@link com.liferay.portal.NoSuchUserTrackerPathException} if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(userTrackerPathId);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userTrackerPathId);
			}

			throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userTrackerPathId);
		}

		return userTrackerPath;
	}

	/**
	 * Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserTrackerPath fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId)
		throws SystemException {
		UserTrackerPath userTrackerPath = (UserTrackerPath)EntityCacheUtil.getResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerPathImpl.class, userTrackerPathId);

		if (userTrackerPath == _nullUserTrackerPath) {
			return null;
		}

		if (userTrackerPath == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
						Long.valueOf(userTrackerPathId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userTrackerPath != null) {
					cacheResult(userTrackerPath);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerPathImpl.class, userTrackerPathId,
						_nullUserTrackerPath);
				}

				closeSession(session);
			}
		}

		return userTrackerPath;
	}

	/**
	 * Returns all the user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the matching user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId)
		throws SystemException {
		return findByUserTrackerId(userTrackerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of matching user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end) throws SystemException {
		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID;
			finderArgs = new Object[] { userTrackerId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERTRACKERID;
			finderArgs = new Object[] {
					userTrackerId,
					
					start, end, orderByComparator
				};
		}

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserTrackerPath userTrackerPath : list) {
				if ((userTrackerId != userTrackerPath.getUserTrackerId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

			query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

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

				qPos.add(userTrackerId);

				list = (List<UserTrackerPath>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath findByUserTrackerId_First(long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByUserTrackerId_First(userTrackerId,
				orderByComparator);

		if (userTrackerPath != null) {
			return userTrackerPath;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userTrackerId=");
		msg.append(userTrackerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerPathException(msg.toString());
	}

	/**
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath fetchByUserTrackerId_First(long userTrackerId,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath findByUserTrackerId_Last(long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByUserTrackerId_Last(userTrackerId,
				orderByComparator);

		if (userTrackerPath != null) {
			return userTrackerPath;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userTrackerId=");
		msg.append(userTrackerId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerPathException(msg.toString());
	}

	/**
	 * Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath fetchByUserTrackerId_Last(long userTrackerId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserTrackerId(userTrackerId);

		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user tracker paths before and after the current user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerPathId the primary key of the current user tracker path
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = findByPrimaryKey(userTrackerPathId);

		Session session = null;

		try {
			session = openSession();

			UserTrackerPath[] array = new UserTrackerPathImpl[3];

			array[0] = getByUserTrackerId_PrevAndNext(session, userTrackerPath,
					userTrackerId, orderByComparator, true);

			array[1] = userTrackerPath;

			array[2] = getByUserTrackerId_PrevAndNext(session, userTrackerPath,
					userTrackerId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTrackerPath getByUserTrackerId_PrevAndNext(Session session,
		UserTrackerPath userTrackerPath, long userTrackerId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

		query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

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

		qPos.add(userTrackerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userTrackerPath);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserTrackerPath> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user tracker paths.
	 *
	 * @return the user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user tracker paths.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user tracker paths.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserTrackerPath> findAll(int start, int end,
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

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERTRACKERPATH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERTRACKERPATH;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
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
	 * Removes all the user tracker paths where userTrackerId = &#63; from the database.
	 *
	 * @param userTrackerId the user tracker ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserTrackerId(long userTrackerId)
		throws SystemException {
		for (UserTrackerPath userTrackerPath : findByUserTrackerId(
				userTrackerId)) {
			remove(userTrackerPath);
		}
	}

	/**
	 * Removes all the user tracker paths from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserTrackerPath userTrackerPath : findAll()) {
			remove(userTrackerPath);
		}
	}

	/**
	 * Returns the number of user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the number of matching user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserTrackerId(long userTrackerId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userTrackerId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERTRACKERPATH_WHERE);

			query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user tracker paths.
	 *
	 * @return the number of user tracker paths
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERTRACKERPATH);

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
	 * Initializes the user tracker path persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserTrackerPath")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserTrackerPath>> listenersList = new ArrayList<ModelListener<UserTrackerPath>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserTrackerPath>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserTrackerPathImpl.class.getName());
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
	private static final String _SQL_SELECT_USERTRACKERPATH = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _SQL_COUNT_USERTRACKERPATH = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_COUNT_USERTRACKERPATH_WHERE = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2 = "userTrackerPath.userTrackerId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTrackerPath.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTrackerPath exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTrackerPath exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceImpl.class);
	private static UserTrackerPath _nullUserTrackerPath = new UserTrackerPathImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserTrackerPath> toCacheModel() {
				return _nullUserTrackerPathCacheModel;
			}
		};

	private static CacheModel<UserTrackerPath> _nullUserTrackerPathCacheModel = new CacheModel<UserTrackerPath>() {
			public UserTrackerPath toEntityModel() {
				return _nullUserTrackerPath;
			}
		};
}