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

import com.liferay.portal.NoSuchBrowserTrackerException;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.impl.BrowserTrackerImpl;
import com.liferay.portal.model.impl.BrowserTrackerModelImpl;
import com.liferay.portal.service.persistence.BrowserTrackerPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the browser tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTrackerPersistence
 * @see BrowserTrackerUtil
 * @generated
 */
@ProviderType
public class BrowserTrackerPersistenceImpl extends BasePersistenceImpl<BrowserTracker>
	implements BrowserTrackerPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BrowserTrackerUtil} to access the browser tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BrowserTrackerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			BrowserTrackerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			BrowserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			BrowserTrackerImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUserId", new String[] { Long.class.getName() },
			BrowserTrackerModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the browser tracker where userId = &#63; or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching browser tracker
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a matching browser tracker could not be found
	 */
	@Override
	public BrowserTracker findByUserId(long userId)
		throws NoSuchBrowserTrackerException {
		BrowserTracker browserTracker = fetchByUserId(userId);

		if (browserTracker == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBrowserTrackerException(msg.toString());
		}

		return browserTracker;
	}

	/**
	 * Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 */
	@Override
	public BrowserTracker fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 */
	@Override
	public BrowserTracker fetchByUserId(long userId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result instanceof BrowserTracker) {
			BrowserTracker browserTracker = (BrowserTracker)result;

			if ((userId != browserTracker.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_BROWSERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<BrowserTracker> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					BrowserTracker browserTracker = list.get(0);

					result = browserTracker;

					cacheResult(browserTracker);

					if ((browserTracker.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, browserTracker);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (BrowserTracker)result;
		}
	}

	/**
	 * Removes the browser tracker where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the browser tracker that was removed
	 */
	@Override
	public BrowserTracker removeByUserId(long userId)
		throws NoSuchBrowserTrackerException {
		BrowserTracker browserTracker = findByUserId(userId);

		return remove(browserTracker);
	}

	/**
	 * Returns the number of browser trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching browser trackers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BROWSERTRACKER_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "browserTracker.userId = ?";

	public BrowserTrackerPersistenceImpl() {
		setModelClass(BrowserTracker.class);
	}

	/**
	 * Caches the browser tracker in the entity cache if it is enabled.
	 *
	 * @param browserTracker the browser tracker
	 */
	@Override
	public void cacheResult(BrowserTracker browserTracker) {
		EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey(),
			browserTracker);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { browserTracker.getUserId() }, browserTracker);

		browserTracker.resetOriginalValues();
	}

	/**
	 * Caches the browser trackers in the entity cache if it is enabled.
	 *
	 * @param browserTrackers the browser trackers
	 */
	@Override
	public void cacheResult(List<BrowserTracker> browserTrackers) {
		for (BrowserTracker browserTracker : browserTrackers) {
			if (EntityCacheUtil.getResult(
						BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						BrowserTrackerImpl.class, browserTracker.getPrimaryKey()) == null) {
				cacheResult(browserTracker);
			}
			else {
				browserTracker.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all browser trackers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BrowserTrackerImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BrowserTrackerImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the browser tracker.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BrowserTracker browserTracker) {
		EntityCacheUtil.removeResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(browserTracker);
	}

	@Override
	public void clearCache(List<BrowserTracker> browserTrackers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BrowserTracker browserTracker : browserTrackers) {
			EntityCacheUtil.removeResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				BrowserTrackerImpl.class, browserTracker.getPrimaryKey());

			clearUniqueFindersCache(browserTracker);
		}
	}

	protected void cacheUniqueFindersCache(BrowserTracker browserTracker) {
		if (browserTracker.isNew()) {
			Object[] args = new Object[] { browserTracker.getUserId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args,
				browserTracker);
		}
		else {
			BrowserTrackerModelImpl browserTrackerModelImpl = (BrowserTrackerModelImpl)browserTracker;

			if ((browserTrackerModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { browserTracker.getUserId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args,
					browserTracker);
			}
		}
	}

	protected void clearUniqueFindersCache(BrowserTracker browserTracker) {
		BrowserTrackerModelImpl browserTrackerModelImpl = (BrowserTrackerModelImpl)browserTracker;

		Object[] args = new Object[] { browserTracker.getUserId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);

		if ((browserTrackerModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
			args = new Object[] { browserTrackerModelImpl.getOriginalUserId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);
		}
	}

	/**
	 * Creates a new browser tracker with the primary key. Does not add the browser tracker to the database.
	 *
	 * @param browserTrackerId the primary key for the new browser tracker
	 * @return the new browser tracker
	 */
	@Override
	public BrowserTracker create(long browserTrackerId) {
		BrowserTracker browserTracker = new BrowserTrackerImpl();

		browserTracker.setNew(true);
		browserTracker.setPrimaryKey(browserTrackerId);

		return browserTracker;
	}

	/**
	 * Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param browserTrackerId the primary key of the browser tracker
	 * @return the browser tracker that was removed
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker remove(long browserTrackerId)
		throws NoSuchBrowserTrackerException {
		return remove((Serializable)browserTrackerId);
	}

	/**
	 * Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the browser tracker
	 * @return the browser tracker that was removed
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker remove(Serializable primaryKey)
		throws NoSuchBrowserTrackerException {
		Session session = null;

		try {
			session = openSession();

			BrowserTracker browserTracker = (BrowserTracker)session.get(BrowserTrackerImpl.class,
					primaryKey);

			if (browserTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBrowserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(browserTracker);
		}
		catch (NoSuchBrowserTrackerException nsee) {
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
	protected BrowserTracker removeImpl(BrowserTracker browserTracker) {
		browserTracker = toUnwrappedModel(browserTracker);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(browserTracker)) {
				browserTracker = (BrowserTracker)session.get(BrowserTrackerImpl.class,
						browserTracker.getPrimaryKeyObj());
			}

			if (browserTracker != null) {
				session.delete(browserTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (browserTracker != null) {
			clearCache(browserTracker);
		}

		return browserTracker;
	}

	@Override
	public BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker) {
		browserTracker = toUnwrappedModel(browserTracker);

		boolean isNew = browserTracker.isNew();

		Session session = null;

		try {
			session = openSession();

			if (browserTracker.isNew()) {
				session.save(browserTracker);

				browserTracker.setNew(false);
			}
			else {
				session.merge(browserTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BrowserTrackerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey(),
			browserTracker, false);

		clearUniqueFindersCache(browserTracker);
		cacheUniqueFindersCache(browserTracker);

		browserTracker.resetOriginalValues();

		return browserTracker;
	}

	protected BrowserTracker toUnwrappedModel(BrowserTracker browserTracker) {
		if (browserTracker instanceof BrowserTrackerImpl) {
			return browserTracker;
		}

		BrowserTrackerImpl browserTrackerImpl = new BrowserTrackerImpl();

		browserTrackerImpl.setNew(browserTracker.isNew());
		browserTrackerImpl.setPrimaryKey(browserTracker.getPrimaryKey());

		browserTrackerImpl.setMvccVersion(browserTracker.getMvccVersion());
		browserTrackerImpl.setBrowserTrackerId(browserTracker.getBrowserTrackerId());
		browserTrackerImpl.setUserId(browserTracker.getUserId());
		browserTrackerImpl.setBrowserKey(browserTracker.getBrowserKey());

		return browserTrackerImpl;
	}

	/**
	 * Returns the browser tracker with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the browser tracker
	 * @return the browser tracker
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBrowserTrackerException {
		BrowserTracker browserTracker = fetchByPrimaryKey(primaryKey);

		if (browserTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBrowserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return browserTracker;
	}

	/**
	 * Returns the browser tracker with the primary key or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	 *
	 * @param browserTrackerId the primary key of the browser tracker
	 * @return the browser tracker
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker findByPrimaryKey(long browserTrackerId)
		throws NoSuchBrowserTrackerException {
		return findByPrimaryKey((Serializable)browserTrackerId);
	}

	/**
	 * Returns the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the browser tracker
	 * @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker fetchByPrimaryKey(Serializable primaryKey) {
		BrowserTracker browserTracker = (BrowserTracker)EntityCacheUtil.getResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				BrowserTrackerImpl.class, primaryKey);

		if (browserTracker == _nullBrowserTracker) {
			return null;
		}

		if (browserTracker == null) {
			Session session = null;

			try {
				session = openSession();

				browserTracker = (BrowserTracker)session.get(BrowserTrackerImpl.class,
						primaryKey);

				if (browserTracker != null) {
					cacheResult(browserTracker);
				}
				else {
					EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						BrowserTrackerImpl.class, primaryKey,
						_nullBrowserTracker);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					BrowserTrackerImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return browserTracker;
	}

	/**
	 * Returns the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param browserTrackerId the primary key of the browser tracker
	 * @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	 */
	@Override
	public BrowserTracker fetchByPrimaryKey(long browserTrackerId) {
		return fetchByPrimaryKey((Serializable)browserTrackerId);
	}

	@Override
	public Map<Serializable, BrowserTracker> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BrowserTracker> map = new HashMap<Serializable, BrowserTracker>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BrowserTracker browserTracker = fetchByPrimaryKey(primaryKey);

			if (browserTracker != null) {
				map.put(primaryKey, browserTracker);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			BrowserTracker browserTracker = (BrowserTracker)EntityCacheUtil.getResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					BrowserTrackerImpl.class, primaryKey);

			if (browserTracker == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, browserTracker);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BROWSERTRACKER_WHERE_PKS_IN);

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

			for (BrowserTracker browserTracker : (List<BrowserTracker>)q.list()) {
				map.put(browserTracker.getPrimaryKeyObj(), browserTracker);

				cacheResult(browserTracker);

				uncachedPrimaryKeys.remove(browserTracker.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					BrowserTrackerImpl.class, primaryKey, _nullBrowserTracker);
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
	 * Returns all the browser trackers.
	 *
	 * @return the browser trackers
	 */
	@Override
	public List<BrowserTracker> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the browser trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BrowserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of browser trackers
	 * @param end the upper bound of the range of browser trackers (not inclusive)
	 * @return the range of browser trackers
	 */
	@Override
	public List<BrowserTracker> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the browser trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BrowserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of browser trackers
	 * @param end the upper bound of the range of browser trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of browser trackers
	 */
	@Override
	public List<BrowserTracker> findAll(int start, int end,
		OrderByComparator<BrowserTracker> orderByComparator) {
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

		List<BrowserTracker> list = (List<BrowserTracker>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BROWSERTRACKER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BROWSERTRACKER;

				if (pagination) {
					sql = sql.concat(BrowserTrackerModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BrowserTracker>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BrowserTracker>)QueryUtil.list(q,
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
	 * Removes all the browser trackers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BrowserTracker browserTracker : findAll()) {
			remove(browserTracker);
		}
	}

	/**
	 * Returns the number of browser trackers.
	 *
	 * @return the number of browser trackers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BROWSERTRACKER);

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

	/**
	 * Initializes the browser tracker persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(BrowserTrackerImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_BROWSERTRACKER = "SELECT browserTracker FROM BrowserTracker browserTracker";
	private static final String _SQL_SELECT_BROWSERTRACKER_WHERE_PKS_IN = "SELECT browserTracker FROM BrowserTracker browserTracker WHERE browserTrackerId IN (";
	private static final String _SQL_SELECT_BROWSERTRACKER_WHERE = "SELECT browserTracker FROM BrowserTracker browserTracker WHERE ";
	private static final String _SQL_COUNT_BROWSERTRACKER = "SELECT COUNT(browserTracker) FROM BrowserTracker browserTracker";
	private static final String _SQL_COUNT_BROWSERTRACKER_WHERE = "SELECT COUNT(browserTracker) FROM BrowserTracker browserTracker WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "browserTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BrowserTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BrowserTracker exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(BrowserTrackerPersistenceImpl.class);
	private static final BrowserTracker _nullBrowserTracker = new BrowserTrackerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BrowserTracker> toCacheModel() {
				return _nullBrowserTrackerCacheModel;
			}
		};

	private static final CacheModel<BrowserTracker> _nullBrowserTrackerCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<BrowserTracker>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public BrowserTracker toEntityModel() {
			return _nullBrowserTracker;
		}
	}
}