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

import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;
import com.liferay.portal.service.persistence.UserTrackerPathPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@ProviderType
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERTRACKERID =
		new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			UserTrackerPathImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserTrackerId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
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

	/**
	 * Returns all the user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId) {
		return findByUserTrackerId(userTrackerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end) {
		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end, OrderByComparator<UserTrackerPath> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

			query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserTrackerPathModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				if (!pagination) {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
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
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 */
	@Override
	public UserTrackerPath findByUserTrackerId_First(long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator)
		throws NoSuchUserTrackerPathException {
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
	 */
	@Override
	public UserTrackerPath fetchByUserTrackerId_First(long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator) {
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
	 */
	@Override
	public UserTrackerPath findByUserTrackerId_Last(long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator)
		throws NoSuchUserTrackerPathException {
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
	 */
	@Override
	public UserTrackerPath fetchByUserTrackerId_Last(long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator) {
		int count = countByUserTrackerId(userTrackerId);

		if (count == 0) {
			return null;
		}

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
	 */
	@Override
	public UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator)
		throws NoSuchUserTrackerPathException {
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
		OrderByComparator<UserTrackerPath> orderByComparator, boolean previous) {
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
		else {
			query.append(UserTrackerPathModelImpl.ORDER_BY_JPQL);
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
	 * Removes all the user tracker paths where userTrackerId = &#63; from the database.
	 *
	 * @param userTrackerId the user tracker ID
	 */
	@Override
	public void removeByUserTrackerId(long userTrackerId) {
		for (UserTrackerPath userTrackerPath : findByUserTrackerId(
				userTrackerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(userTrackerPath);
		}
	}

	/**
	 * Returns the number of user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the number of matching user tracker paths
	 */
	@Override
	public int countByUserTrackerId(long userTrackerId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERTRACKERID;

		Object[] finderArgs = new Object[] { userTrackerId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2 = "userTrackerPath.userTrackerId = ?";

	public UserTrackerPathPersistenceImpl() {
		setModelClass(UserTrackerPath.class);
	}

	/**
	 * Caches the user tracker path in the entity cache if it is enabled.
	 *
	 * @param userTrackerPath the user tracker path
	 */
	@Override
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
	@Override
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

		EntityCacheUtil.clearCache(UserTrackerPathImpl.class);

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
	@Override
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
	 */
	@Override
	public UserTrackerPath remove(long userTrackerPathId)
		throws NoSuchUserTrackerPathException {
		return remove((Serializable)userTrackerPathId);
	}

	/**
	 * Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path that was removed
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath remove(Serializable primaryKey)
		throws NoSuchUserTrackerPathException {
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
	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath) {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userTrackerPath)) {
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
		com.liferay.portal.model.UserTrackerPath userTrackerPath) {
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
						userTrackerPathModelImpl.getOriginalUserTrackerId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID,
					args);

				args = new Object[] { userTrackerPathModelImpl.getUserTrackerId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERTRACKERID,
					args);
			}
		}

		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath, false);

		userTrackerPath.resetOriginalValues();

		return userTrackerPath;
	}

	protected UserTrackerPath toUnwrappedModel(UserTrackerPath userTrackerPath) {
		if (userTrackerPath instanceof UserTrackerPathImpl) {
			return userTrackerPath;
		}

		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setNew(userTrackerPath.isNew());
		userTrackerPathImpl.setPrimaryKey(userTrackerPath.getPrimaryKey());

		userTrackerPathImpl.setMvccVersion(userTrackerPath.getMvccVersion());
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
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserTrackerPathException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(primaryKey);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return userTrackerPath;
	}

	/**
	 * Returns the user tracker path with the primary key or throws a {@link com.liferay.portal.NoSuchUserTrackerPathException} if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException {
		return findByPrimaryKey((Serializable)userTrackerPathId);
	}

	/**
	 * Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath fetchByPrimaryKey(Serializable primaryKey) {
		UserTrackerPath userTrackerPath = (UserTrackerPath)EntityCacheUtil.getResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerPathImpl.class, primaryKey);

		if (userTrackerPath == _nullUserTrackerPath) {
			return null;
		}

		if (userTrackerPath == null) {
			Session session = null;

			try {
				session = openSession();

				userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
						primaryKey);

				if (userTrackerPath != null) {
					cacheResult(userTrackerPath);
				}
				else {
					EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerPathImpl.class, primaryKey,
						_nullUserTrackerPath);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerPathImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return userTrackerPath;
	}

	/**
	 * Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId) {
		return fetchByPrimaryKey((Serializable)userTrackerPathId);
	}

	@Override
	public Map<Serializable, UserTrackerPath> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserTrackerPath> map = new HashMap<Serializable, UserTrackerPath>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserTrackerPath userTrackerPath = fetchByPrimaryKey(primaryKey);

			if (userTrackerPath != null) {
				map.put(primaryKey, userTrackerPath);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			UserTrackerPath userTrackerPath = (UserTrackerPath)EntityCacheUtil.getResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerPathImpl.class, primaryKey);

			if (userTrackerPath == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, userTrackerPath);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_USERTRACKERPATH_WHERE_PKS_IN);

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

			for (UserTrackerPath userTrackerPath : (List<UserTrackerPath>)q.list()) {
				map.put(userTrackerPath.getPrimaryKeyObj(), userTrackerPath);

				cacheResult(userTrackerPath);

				uncachedPrimaryKeys.remove(userTrackerPath.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerPathImpl.class, primaryKey, _nullUserTrackerPath);
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
	 * Returns all the user tracker paths.
	 *
	 * @return the user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user tracker paths.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user tracker paths.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findAll(int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {
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

				if (pagination) {
					sql = sql.concat(UserTrackerPathModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
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
	 * Removes all the user tracker paths from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserTrackerPath userTrackerPath : findAll()) {
			remove(userTrackerPath);
		}
	}

	/**
	 * Returns the number of user tracker paths.
	 *
	 * @return the number of user tracker paths
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERTRACKERPATH);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the user tracker path persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserTrackerPathImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERTRACKERPATH = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE_PKS_IN = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE userTrackerPathId IN (";
	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _SQL_COUNT_USERTRACKERPATH = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_COUNT_USERTRACKERPATH_WHERE = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTrackerPath.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTrackerPath exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTrackerPath exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"path"
			});
	private static final UserTrackerPath _nullUserTrackerPath = new UserTrackerPathImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserTrackerPath> toCacheModel() {
				return _nullUserTrackerPathCacheModel;
			}
		};

	private static final CacheModel<UserTrackerPath> _nullUserTrackerPathCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<UserTrackerPath>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public UserTrackerPath toEntityModel() {
			return _nullUserTrackerPath;
		}
	}
}