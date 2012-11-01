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
import com.liferay.portal.NoSuchPasswordTrackerException;
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
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.model.impl.PasswordTrackerImpl;
import com.liferay.portal.model.impl.PasswordTrackerModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the password tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerPersistence
 * @see PasswordTrackerUtil
 * @generated
 */
public class PasswordTrackerPersistenceImpl extends BasePersistenceImpl<PasswordTracker>
	implements PasswordTrackerPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PasswordTrackerUtil} to access the password tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordTrackerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			PasswordTrackerModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the password tracker in the entity cache if it is enabled.
	 *
	 * @param passwordTracker the password tracker
	 */
	public void cacheResult(PasswordTracker passwordTracker) {
		EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerImpl.class, passwordTracker.getPrimaryKey(),
			passwordTracker);

		passwordTracker.resetOriginalValues();
	}

	/**
	 * Caches the password trackers in the entity cache if it is enabled.
	 *
	 * @param passwordTrackers the password trackers
	 */
	public void cacheResult(List<PasswordTracker> passwordTrackers) {
		for (PasswordTracker passwordTracker : passwordTrackers) {
			if (EntityCacheUtil.getResult(
						PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
						PasswordTrackerImpl.class,
						passwordTracker.getPrimaryKey()) == null) {
				cacheResult(passwordTracker);
			}
			else {
				passwordTracker.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all password trackers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PasswordTrackerImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PasswordTrackerImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the password tracker.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PasswordTracker passwordTracker) {
		EntityCacheUtil.removeResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerImpl.class, passwordTracker.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PasswordTracker> passwordTrackers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PasswordTracker passwordTracker : passwordTrackers) {
			EntityCacheUtil.removeResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
				PasswordTrackerImpl.class, passwordTracker.getPrimaryKey());
		}
	}

	/**
	 * Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	 *
	 * @param passwordTrackerId the primary key for the new password tracker
	 * @return the new password tracker
	 */
	public PasswordTracker create(long passwordTrackerId) {
		PasswordTracker passwordTracker = new PasswordTrackerImpl();

		passwordTracker.setNew(true);
		passwordTracker.setPrimaryKey(passwordTrackerId);

		return passwordTracker;
	}

	/**
	 * Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker that was removed
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker remove(long passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		return remove(Long.valueOf(passwordTrackerId));
	}

	/**
	 * Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the password tracker
	 * @return the password tracker that was removed
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordTracker remove(Serializable primaryKey)
		throws NoSuchPasswordTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordTracker passwordTracker = (PasswordTracker)session.get(PasswordTrackerImpl.class,
					primaryKey);

			if (passwordTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPasswordTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(passwordTracker);
		}
		catch (NoSuchPasswordTrackerException nsee) {
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
	protected PasswordTracker removeImpl(PasswordTracker passwordTracker)
		throws SystemException {
		passwordTracker = toUnwrappedModel(passwordTracker);

		Session session = null;

		try {
			session = openSession();

			if (passwordTracker.isCachedModel()) {
				passwordTracker = (PasswordTracker)session.get(PasswordTrackerImpl.class,
						passwordTracker.getPrimaryKeyObj());
			}

			if (passwordTracker != null) {
				session.delete(passwordTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (passwordTracker != null) {
			clearCache(passwordTracker);
		}

		return passwordTracker;
	}

	@Override
	public PasswordTracker updateImpl(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws SystemException {
		passwordTracker = toUnwrappedModel(passwordTracker);

		boolean isNew = passwordTracker.isNew();

		PasswordTrackerModelImpl passwordTrackerModelImpl = (PasswordTrackerModelImpl)passwordTracker;

		Session session = null;

		try {
			session = openSession();

			if (passwordTracker.isNew()) {
				session.save(passwordTracker);

				passwordTracker.setNew(false);
			}
			else {
				session.merge(passwordTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PasswordTrackerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((passwordTrackerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(passwordTrackerModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(passwordTrackerModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerImpl.class, passwordTracker.getPrimaryKey(),
			passwordTracker);

		return passwordTracker;
	}

	protected PasswordTracker toUnwrappedModel(PasswordTracker passwordTracker) {
		if (passwordTracker instanceof PasswordTrackerImpl) {
			return passwordTracker;
		}

		PasswordTrackerImpl passwordTrackerImpl = new PasswordTrackerImpl();

		passwordTrackerImpl.setNew(passwordTracker.isNew());
		passwordTrackerImpl.setPrimaryKey(passwordTracker.getPrimaryKey());

		passwordTrackerImpl.setPasswordTrackerId(passwordTracker.getPasswordTrackerId());
		passwordTrackerImpl.setUserId(passwordTracker.getUserId());
		passwordTrackerImpl.setCreateDate(passwordTracker.getCreateDate());
		passwordTrackerImpl.setPassword(passwordTracker.getPassword());

		return passwordTrackerImpl;
	}

	/**
	 * Returns the password tracker with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the password tracker
	 * @return the password tracker
	 * @throws com.liferay.portal.NoSuchModelException if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the password tracker with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordTrackerException} if it could not be found.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker findByPrimaryKey(long passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = fetchByPrimaryKey(passwordTrackerId);

		if (passwordTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + passwordTrackerId);
			}

			throw new NoSuchPasswordTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				passwordTrackerId);
		}

		return passwordTracker;
	}

	/**
	 * Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password tracker
	 * @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PasswordTracker fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker fetchByPrimaryKey(long passwordTrackerId)
		throws SystemException {
		PasswordTracker passwordTracker = (PasswordTracker)EntityCacheUtil.getResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
				PasswordTrackerImpl.class, passwordTrackerId);

		if (passwordTracker == _nullPasswordTracker) {
			return null;
		}

		if (passwordTracker == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				passwordTracker = (PasswordTracker)session.get(PasswordTrackerImpl.class,
						Long.valueOf(passwordTrackerId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (passwordTracker != null) {
					cacheResult(passwordTracker);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
						PasswordTrackerImpl.class, passwordTrackerId,
						_nullPasswordTracker);
				}

				closeSession(session);
			}
		}

		return passwordTracker;
	}

	/**
	 * Returns all the password trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @return the range of matching password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<PasswordTracker> list = (List<PasswordTracker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PasswordTracker passwordTracker : list) {
				if ((userId != passwordTracker.getUserId())) {
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

			query.append(_SQL_SELECT_PASSWORDTRACKER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(PasswordTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<PasswordTracker>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first password tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a matching password tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = fetchByUserId_First(userId,
				orderByComparator);

		if (passwordTracker != null) {
			return passwordTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordTrackerException(msg.toString());
	}

	/**
	 * Returns the first password tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password tracker, or <code>null</code> if a matching password tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PasswordTracker> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last password tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a matching password tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = fetchByUserId_Last(userId,
				orderByComparator);

		if (passwordTracker != null) {
			return passwordTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordTrackerException(msg.toString());
	}

	/**
	 * Returns the last password tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password tracker, or <code>null</code> if a matching password tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<PasswordTracker> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the password trackers before and after the current password tracker in the ordered set where userId = &#63;.
	 *
	 * @param passwordTrackerId the primary key of the current password tracker
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PasswordTracker[] findByUserId_PrevAndNext(long passwordTrackerId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = findByPrimaryKey(passwordTrackerId);

		Session session = null;

		try {
			session = openSession();

			PasswordTracker[] array = new PasswordTrackerImpl[3];

			array[0] = getByUserId_PrevAndNext(session, passwordTracker,
					userId, orderByComparator, true);

			array[1] = passwordTracker;

			array[2] = getByUserId_PrevAndNext(session, passwordTracker,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PasswordTracker getByUserId_PrevAndNext(Session session,
		PasswordTracker passwordTracker, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PASSWORDTRACKER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(PasswordTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordTracker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordTracker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the password trackers.
	 *
	 * @return the password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @return the range of password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the password trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<PasswordTracker> findAll(int start, int end,
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

		List<PasswordTracker> list = (List<PasswordTracker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PASSWORDTRACKER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PASSWORDTRACKER.concat(PasswordTrackerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PasswordTracker>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PasswordTracker>)QueryUtil.list(q,
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
	 * Removes all the password trackers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (PasswordTracker passwordTracker : findByUserId(userId)) {
			remove(passwordTracker);
		}
	}

	/**
	 * Removes all the password trackers from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (PasswordTracker passwordTracker : findAll()) {
			remove(passwordTracker);
		}
	}

	/**
	 * Returns the number of password trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PASSWORDTRACKER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of password trackers.
	 *
	 * @return the number of password trackers
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDTRACKER);

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
	 * Initializes the password tracker persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PasswordTracker")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordTracker>> listenersList = new ArrayList<ModelListener<PasswordTracker>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordTracker>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PasswordTrackerImpl.class.getName());
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
	private static final String _SQL_SELECT_PASSWORDTRACKER = "SELECT passwordTracker FROM PasswordTracker passwordTracker";
	private static final String _SQL_SELECT_PASSWORDTRACKER_WHERE = "SELECT passwordTracker FROM PasswordTracker passwordTracker WHERE ";
	private static final String _SQL_COUNT_PASSWORDTRACKER = "SELECT COUNT(passwordTracker) FROM PasswordTracker passwordTracker";
	private static final String _SQL_COUNT_PASSWORDTRACKER_WHERE = "SELECT COUNT(passwordTracker) FROM PasswordTracker passwordTracker WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "passwordTracker.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordTracker exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PasswordTrackerPersistenceImpl.class);
	private static PasswordTracker _nullPasswordTracker = new PasswordTrackerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PasswordTracker> toCacheModel() {
				return _nullPasswordTrackerCacheModel;
			}
		};

	private static CacheModel<PasswordTracker> _nullPasswordTrackerCacheModel = new CacheModel<PasswordTracker>() {
			public PasswordTracker toEntityModel() {
				return _nullPasswordTracker;
			}
		};
}