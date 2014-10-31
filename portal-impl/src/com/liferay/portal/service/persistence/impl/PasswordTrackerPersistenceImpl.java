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

import com.liferay.portal.NoSuchPasswordTrackerException;
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
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.model.impl.PasswordTrackerImpl;
import com.liferay.portal.model.impl.PasswordTrackerModelImpl;
import com.liferay.portal.service.persistence.PasswordTrackerPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@ProviderType
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED,
			PasswordTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			PasswordTrackerModelImpl.USERID_COLUMN_BITMASK |
			PasswordTrackerModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the password trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching password trackers
	 */
	@Override
	public List<PasswordTracker> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @return the range of matching password trackers
	 */
	@Override
	public List<PasswordTracker> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password trackers
	 */
	@Override
	public List<PasswordTracker> findByUserId(long userId, int start, int end,
		OrderByComparator<PasswordTracker> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
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
			else
			 if (pagination) {
				query.append(PasswordTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<PasswordTracker>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordTracker>)QueryUtil.list(q,
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
	 * Returns the first password tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a matching password tracker could not be found
	 */
	@Override
	public PasswordTracker findByUserId_First(long userId,
		OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException {
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
	 */
	@Override
	public PasswordTracker fetchByUserId_First(long userId,
		OrderByComparator<PasswordTracker> orderByComparator) {
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
	 */
	@Override
	public PasswordTracker findByUserId_Last(long userId,
		OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException {
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
	 */
	@Override
	public PasswordTracker fetchByUserId_Last(long userId,
		OrderByComparator<PasswordTracker> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

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
	 */
	@Override
	public PasswordTracker[] findByUserId_PrevAndNext(long passwordTrackerId,
		long userId, OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException {
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
		OrderByComparator<PasswordTracker> orderByComparator, boolean previous) {
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
	 * Removes all the password trackers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (PasswordTracker passwordTracker : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(passwordTracker);
		}
	}

	/**
	 * Returns the number of password trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching password trackers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "passwordTracker.userId = ?";

	public PasswordTrackerPersistenceImpl() {
		setModelClass(PasswordTracker.class);
	}

	/**
	 * Caches the password tracker in the entity cache if it is enabled.
	 *
	 * @param passwordTracker the password tracker
	 */
	@Override
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
	@Override
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

		EntityCacheUtil.clearCache(PasswordTrackerImpl.class);

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
	@Override
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
	 */
	@Override
	public PasswordTracker remove(long passwordTrackerId)
		throws NoSuchPasswordTrackerException {
		return remove((Serializable)passwordTrackerId);
	}

	/**
	 * Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the password tracker
	 * @return the password tracker that was removed
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 */
	@Override
	public PasswordTracker remove(Serializable primaryKey)
		throws NoSuchPasswordTrackerException {
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
	protected PasswordTracker removeImpl(PasswordTracker passwordTracker) {
		passwordTracker = toUnwrappedModel(passwordTracker);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(passwordTracker)) {
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
		com.liferay.portal.model.PasswordTracker passwordTracker) {
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
						passwordTrackerModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { passwordTrackerModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
			PasswordTrackerImpl.class, passwordTracker.getPrimaryKey(),
			passwordTracker, false);

		passwordTracker.resetOriginalValues();

		return passwordTracker;
	}

	protected PasswordTracker toUnwrappedModel(PasswordTracker passwordTracker) {
		if (passwordTracker instanceof PasswordTrackerImpl) {
			return passwordTracker;
		}

		PasswordTrackerImpl passwordTrackerImpl = new PasswordTrackerImpl();

		passwordTrackerImpl.setNew(passwordTracker.isNew());
		passwordTrackerImpl.setPrimaryKey(passwordTracker.getPrimaryKey());

		passwordTrackerImpl.setMvccVersion(passwordTracker.getMvccVersion());
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
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 */
	@Override
	public PasswordTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPasswordTrackerException {
		PasswordTracker passwordTracker = fetchByPrimaryKey(primaryKey);

		if (passwordTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPasswordTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return passwordTracker;
	}

	/**
	 * Returns the password tracker with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordTrackerException} if it could not be found.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker
	 * @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	 */
	@Override
	public PasswordTracker findByPrimaryKey(long passwordTrackerId)
		throws NoSuchPasswordTrackerException {
		return findByPrimaryKey((Serializable)passwordTrackerId);
	}

	/**
	 * Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password tracker
	 * @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	 */
	@Override
	public PasswordTracker fetchByPrimaryKey(Serializable primaryKey) {
		PasswordTracker passwordTracker = (PasswordTracker)EntityCacheUtil.getResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
				PasswordTrackerImpl.class, primaryKey);

		if (passwordTracker == _nullPasswordTracker) {
			return null;
		}

		if (passwordTracker == null) {
			Session session = null;

			try {
				session = openSession();

				passwordTracker = (PasswordTracker)session.get(PasswordTrackerImpl.class,
						primaryKey);

				if (passwordTracker != null) {
					cacheResult(passwordTracker);
				}
				else {
					EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
						PasswordTrackerImpl.class, primaryKey,
						_nullPasswordTracker);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
					PasswordTrackerImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return passwordTracker;
	}

	/**
	 * Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	 */
	@Override
	public PasswordTracker fetchByPrimaryKey(long passwordTrackerId) {
		return fetchByPrimaryKey((Serializable)passwordTrackerId);
	}

	@Override
	public Map<Serializable, PasswordTracker> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PasswordTracker> map = new HashMap<Serializable, PasswordTracker>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PasswordTracker passwordTracker = fetchByPrimaryKey(primaryKey);

			if (passwordTracker != null) {
				map.put(primaryKey, passwordTracker);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PasswordTracker passwordTracker = (PasswordTracker)EntityCacheUtil.getResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
					PasswordTrackerImpl.class, primaryKey);

			if (passwordTracker == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, passwordTracker);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PASSWORDTRACKER_WHERE_PKS_IN);

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

			for (PasswordTracker passwordTracker : (List<PasswordTracker>)q.list()) {
				map.put(passwordTracker.getPrimaryKeyObj(), passwordTracker);

				cacheResult(passwordTracker);

				uncachedPrimaryKeys.remove(passwordTracker.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(PasswordTrackerModelImpl.ENTITY_CACHE_ENABLED,
					PasswordTrackerImpl.class, primaryKey, _nullPasswordTracker);
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
	 * Returns all the password trackers.
	 *
	 * @return the password trackers
	 */
	@Override
	public List<PasswordTracker> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @return the range of password trackers
	 */
	@Override
	public List<PasswordTracker> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the password trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password trackers
	 */
	@Override
	public List<PasswordTracker> findAll(int start, int end,
		OrderByComparator<PasswordTracker> orderByComparator) {
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
				sql = _SQL_SELECT_PASSWORDTRACKER;

				if (pagination) {
					sql = sql.concat(PasswordTrackerModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PasswordTracker>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordTracker>)QueryUtil.list(q,
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
	 * Removes all the password trackers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PasswordTracker passwordTracker : findAll()) {
			remove(passwordTracker);
		}
	}

	/**
	 * Returns the number of password trackers.
	 *
	 * @return the number of password trackers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDTRACKER);

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
	 * Initializes the password tracker persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PasswordTrackerImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PASSWORDTRACKER = "SELECT passwordTracker FROM PasswordTracker passwordTracker";
	private static final String _SQL_SELECT_PASSWORDTRACKER_WHERE_PKS_IN = "SELECT passwordTracker FROM PasswordTracker passwordTracker WHERE passwordTrackerId IN (";
	private static final String _SQL_SELECT_PASSWORDTRACKER_WHERE = "SELECT passwordTracker FROM PasswordTracker passwordTracker WHERE ";
	private static final String _SQL_COUNT_PASSWORDTRACKER = "SELECT COUNT(passwordTracker) FROM PasswordTracker passwordTracker";
	private static final String _SQL_COUNT_PASSWORDTRACKER_WHERE = "SELECT COUNT(passwordTracker) FROM PasswordTracker passwordTracker WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordTracker exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(PasswordTrackerPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"password"
			});
	private static final PasswordTracker _nullPasswordTracker = new PasswordTrackerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PasswordTracker> toCacheModel() {
				return _nullPasswordTrackerCacheModel;
			}
		};

	private static final CacheModel<PasswordTracker> _nullPasswordTrackerCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PasswordTracker>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PasswordTracker toEntityModel() {
			return _nullPasswordTracker;
		}
	}
}