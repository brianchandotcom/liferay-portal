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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserTrackerPathException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserTrackerPath;
import com.liferay.portal.kernel.model.UserTrackerPathTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserTrackerPathPersistence;
import com.liferay.portal.kernel.service.persistence.UserTrackerPathUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
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
 * @generated
 */
public class UserTrackerPathPersistenceImpl
	extends BasePersistenceImpl<UserTrackerPath>
	implements UserTrackerPathPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserTrackerPathUtil</code> to access the user tracker path persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserTrackerPathImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserTrackerId;
	private FinderPath _finderPathWithoutPaginationFindByUserTrackerId;
	private FinderPath _finderPathCountByUserTrackerId;

	/**
	 * Returns all the user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId) {
		return findByUserTrackerId(
			userTrackerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end) {

		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {

		return findByUserTrackerId(
			userTrackerId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserTrackerId;
				finderArgs = new Object[] {userTrackerId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserTrackerId;
			finderArgs = new Object[] {
				userTrackerId, start, end, orderByComparator
			};
		}

		List<UserTrackerPath> list = null;

		if (useFinderCache) {
			list = (List<UserTrackerPath>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTrackerPath userTrackerPath : list) {
					if (userTrackerId != userTrackerPath.getUserTrackerId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

			sb.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserTrackerPathModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userTrackerId);

				list = (List<UserTrackerPath>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * @throws NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 */
	@Override
	public UserTrackerPath findByUserTrackerId_First(
			long userTrackerId,
			OrderByComparator<UserTrackerPath> orderByComparator)
		throws NoSuchUserTrackerPathException {

		UserTrackerPath userTrackerPath = fetchByUserTrackerId_First(
			userTrackerId, orderByComparator);

		if (userTrackerPath != null) {
			return userTrackerPath;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userTrackerId=");
		sb.append(userTrackerId);

		sb.append("}");

		throw new NoSuchUserTrackerPathException(sb.toString());
	}

	/**
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	 */
	@Override
	public UserTrackerPath fetchByUserTrackerId_First(
		long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator) {

		List<UserTrackerPath> list = findByUserTrackerId(
			userTrackerId, 0, 1, orderByComparator);

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
	 * @throws NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 */
	@Override
	public UserTrackerPath findByUserTrackerId_Last(
			long userTrackerId,
			OrderByComparator<UserTrackerPath> orderByComparator)
		throws NoSuchUserTrackerPathException {

		UserTrackerPath userTrackerPath = fetchByUserTrackerId_Last(
			userTrackerId, orderByComparator);

		if (userTrackerPath != null) {
			return userTrackerPath;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userTrackerId=");
		sb.append(userTrackerId);

		sb.append("}");

		throw new NoSuchUserTrackerPathException(sb.toString());
	}

	/**
	 * Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	 */
	@Override
	public UserTrackerPath fetchByUserTrackerId_Last(
		long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator) {

		int count = countByUserTrackerId(userTrackerId);

		if (count == 0) {
			return null;
		}

		List<UserTrackerPath> list = findByUserTrackerId(
			userTrackerId, count - 1, count, orderByComparator);

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
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
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

			array[0] = getByUserTrackerId_PrevAndNext(
				session, userTrackerPath, userTrackerId, orderByComparator,
				true);

			array[1] = userTrackerPath;

			array[2] = getByUserTrackerId_PrevAndNext(
				session, userTrackerPath, userTrackerId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTrackerPath getByUserTrackerId_PrevAndNext(
		Session session, UserTrackerPath userTrackerPath, long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

		sb.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserTrackerPathModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userTrackerId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userTrackerPath)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserTrackerPath> list = query.list();

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
		for (UserTrackerPath userTrackerPath :
				findByUserTrackerId(
					userTrackerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByUserTrackerId;

		Object[] finderArgs = new Object[] {userTrackerId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTRACKERPATH_WHERE);

			sb.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userTrackerId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2 =
		"userTrackerPath.userTrackerId = ?";

	public UserTrackerPathPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("path", "path_");

		setDBColumnNames(dbColumnNames);

		setModelClass(UserTrackerPath.class);

		setModelImplClass(UserTrackerPathImpl.class);
		setModelPKClass(long.class);

		setTable(UserTrackerPathTable.INSTANCE);
	}

	/**
	 * Caches the user tracker path in the entity cache if it is enabled.
	 *
	 * @param userTrackerPath the user tracker path
	 */
	@Override
	public void cacheResult(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.putResult(
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the user tracker paths in the entity cache if it is enabled.
	 *
	 * @param userTrackerPaths the user tracker paths
	 */
	@Override
	public void cacheResult(List<UserTrackerPath> userTrackerPaths) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (userTrackerPaths.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			if (EntityCacheUtil.getResult(
					UserTrackerPathImpl.class,
					userTrackerPath.getPrimaryKey()) == null) {

				cacheResult(userTrackerPath);
			}
		}
	}

	/**
	 * Clears the cache for all user tracker paths.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserTrackerPathImpl.class);

		FinderCacheUtil.clearCache(UserTrackerPathImpl.class);
	}

	/**
	 * Clears the cache for the user tracker path.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.removeResult(
			UserTrackerPathImpl.class, userTrackerPath);
	}

	@Override
	public void clearCache(List<UserTrackerPath> userTrackerPaths) {
		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			EntityCacheUtil.removeResult(
				UserTrackerPathImpl.class, userTrackerPath);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(UserTrackerPathImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(UserTrackerPathImpl.class, primaryKey);
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

		userTrackerPath.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userTrackerPath;
	}

	/**
	 * Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path that was removed
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
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
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath remove(Serializable primaryKey)
		throws NoSuchUserTrackerPathException {

		Session session = null;

		try {
			session = openSession();

			UserTrackerPath userTrackerPath = (UserTrackerPath)session.get(
				UserTrackerPathImpl.class, primaryKey);

			if (userTrackerPath == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserTrackerPathException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userTrackerPath);
		}
		catch (NoSuchUserTrackerPathException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userTrackerPath)) {
				userTrackerPath = (UserTrackerPath)session.get(
					UserTrackerPathImpl.class,
					userTrackerPath.getPrimaryKeyObj());
			}

			if (userTrackerPath != null) {
				session.delete(userTrackerPath);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public UserTrackerPath updateImpl(UserTrackerPath userTrackerPath) {
		boolean isNew = userTrackerPath.isNew();

		if (!(userTrackerPath instanceof UserTrackerPathModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userTrackerPath.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userTrackerPath);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userTrackerPath proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserTrackerPath implementation " +
					userTrackerPath.getClass());
		}

		UserTrackerPathModelImpl userTrackerPathModelImpl =
			(UserTrackerPathModelImpl)userTrackerPath;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(userTrackerPath);
			}
			else {
				userTrackerPath = (UserTrackerPath)session.merge(
					userTrackerPath);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			UserTrackerPathImpl.class, userTrackerPathModelImpl, false, true);

		if (isNew) {
			userTrackerPath.setNew(false);
		}

		userTrackerPath.resetOriginalValues();

		return userTrackerPath;
	}

	/**
	 * Returns the user tracker path with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserTrackerPathException {

		UserTrackerPath userTrackerPath = fetchByPrimaryKey(primaryKey);

		if (userTrackerPath == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserTrackerPathException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userTrackerPath;
	}

	/**
	 * Returns the user tracker path with the primary key or throws a <code>NoSuchUserTrackerPathException</code> if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	@Override
	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException {

		return findByPrimaryKey((Serializable)userTrackerPathId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findAll(
		int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user tracker paths.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user tracker paths
	 */
	@Override
	public List<UserTrackerPath> findAll(
		int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserTrackerPath> list = null;

		if (useFinderCache) {
			list = (List<UserTrackerPath>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERTRACKERPATH);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERTRACKERPATH;

				sql = sql.concat(UserTrackerPathModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserTrackerPath>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USERTRACKERPATH);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userTrackerPathId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERTRACKERPATH;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserTrackerPathModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user tracker path persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUserTrackerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserTrackerId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userTrackerId"}, true);

		_finderPathWithoutPaginationFindByUserTrackerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserTrackerId",
			new String[] {Long.class.getName()}, new String[] {"userTrackerId"},
			true);

		_finderPathCountByUserTrackerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserTrackerId",
			new String[] {Long.class.getName()}, new String[] {"userTrackerId"},
			false);

		UserTrackerPathUtil.setPersistence(this);
	}

	public void destroy() {
		UserTrackerPathUtil.setPersistence(null);

		EntityCacheUtil.removeCache(UserTrackerPathImpl.class.getName());
	}

	private static final String _SQL_SELECT_USERTRACKERPATH =
		"SELECT userTrackerPath FROM UserTrackerPath userTrackerPath";

	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE =
		"SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE ";

	private static final String _SQL_COUNT_USERTRACKERPATH =
		"SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath";

	private static final String _SQL_COUNT_USERTRACKERPATH_WHERE =
		"SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userTrackerPath.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserTrackerPath exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserTrackerPath exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserTrackerPathPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"path"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}