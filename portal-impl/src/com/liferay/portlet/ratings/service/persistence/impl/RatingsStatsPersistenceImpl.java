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

package com.liferay.portlet.ratings.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the ratings stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsPersistence
 * @see com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil
 * @generated
 */
@ProviderType
public class RatingsStatsPersistenceImpl extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RatingsStatsUtil} to access the ratings stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RatingsStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, RatingsStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, RatingsStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, RatingsStatsImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			RatingsStatsModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsStatsModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchStatsException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings stats
	 * @throws NoSuchStatsException if a matching ratings stats could not be found
	 */
	@Override
	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException {
		RatingsStats ratingsStats = fetchByC_C(classNameId, classPK);

		if (ratingsStats == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsException(msg.toString());
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	 */
	@Override
	public RatingsStats fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	 */
	@Override
	public RatingsStats fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof RatingsStats) {
			RatingsStats ratingsStats = (RatingsStats)result;

			if ((classNameId != ratingsStats.getClassNameId()) ||
					(classPK != ratingsStats.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_RATINGSSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsStats> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					RatingsStats ratingsStats = list.get(0);

					result = ratingsStats;

					cacheResult(ratingsStats);

					if ((ratingsStats.getClassNameId() != classNameId) ||
							(ratingsStats.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, ratingsStats);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (RatingsStats)result;
		}
	}

	/**
	 * Removes the ratings stats where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the ratings stats that was removed
	 */
	@Override
	public RatingsStats removeByC_C(long classNameId, long classPK)
		throws NoSuchStatsException {
		RatingsStats ratingsStats = findByC_C(classNameId, classPK);

		return remove(ratingsStats);
	}

	/**
	 * Returns the number of ratings statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching ratings statses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RATINGSSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ratingsStats.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "ratingsStats.classPK = ?";

	public RatingsStatsPersistenceImpl() {
		setModelClass(RatingsStats.class);
	}

	/**
	 * Caches the ratings stats in the entity cache if it is enabled.
	 *
	 * @param ratingsStats the ratings stats
	 */
	@Override
	public void cacheResult(RatingsStats ratingsStats) {
		entityCache.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				ratingsStats.getClassNameId(), ratingsStats.getClassPK()
			}, ratingsStats);

		ratingsStats.resetOriginalValues();
	}

	/**
	 * Caches the ratings statses in the entity cache if it is enabled.
	 *
	 * @param ratingsStatses the ratings statses
	 */
	@Override
	public void cacheResult(List<RatingsStats> ratingsStatses) {
		for (RatingsStats ratingsStats : ratingsStatses) {
			if (entityCache.getResult(
						RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
						RatingsStatsImpl.class, ratingsStats.getPrimaryKey()) == null) {
				cacheResult(ratingsStats);
			}
			else {
				ratingsStats.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ratings statses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RatingsStatsImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ratings stats.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RatingsStats ratingsStats) {
		entityCache.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RatingsStatsModelImpl)ratingsStats);
	}

	@Override
	public void clearCache(List<RatingsStats> ratingsStatses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RatingsStats ratingsStats : ratingsStatses) {
			entityCache.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
				RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

			clearUniqueFindersCache((RatingsStatsModelImpl)ratingsStats);
		}
	}

	protected void cacheUniqueFindersCache(
		RatingsStatsModelImpl ratingsStatsModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ratingsStatsModelImpl.getClassNameId(),
					ratingsStatsModelImpl.getClassPK()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
				ratingsStatsModelImpl);
		}
		else {
			if ((ratingsStatsModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ratingsStatsModelImpl.getClassNameId(),
						ratingsStatsModelImpl.getClassPK()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
					ratingsStatsModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		RatingsStatsModelImpl ratingsStatsModelImpl) {
		Object[] args = new Object[] {
				ratingsStatsModelImpl.getClassNameId(),
				ratingsStatsModelImpl.getClassPK()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

		if ((ratingsStatsModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					ratingsStatsModelImpl.getOriginalClassNameId(),
					ratingsStatsModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	 *
	 * @param statsId the primary key for the new ratings stats
	 * @return the new ratings stats
	 */
	@Override
	public RatingsStats create(long statsId) {
		RatingsStats ratingsStats = new RatingsStatsImpl();

		ratingsStats.setNew(true);
		ratingsStats.setPrimaryKey(statsId);

		ratingsStats.setCompanyId(companyProvider.getCompanyId());

		return ratingsStats;
	}

	/**
	 * Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats that was removed
	 * @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats remove(long statsId) throws NoSuchStatsException {
		return remove((Serializable)statsId);
	}

	/**
	 * Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ratings stats
	 * @return the ratings stats that was removed
	 * @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats remove(Serializable primaryKey)
		throws NoSuchStatsException {
		Session session = null;

		try {
			session = openSession();

			RatingsStats ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
					primaryKey);

			if (ratingsStats == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ratingsStats);
		}
		catch (NoSuchStatsException nsee) {
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
	protected RatingsStats removeImpl(RatingsStats ratingsStats) {
		ratingsStats = toUnwrappedModel(ratingsStats);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ratingsStats)) {
				ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
						ratingsStats.getPrimaryKeyObj());
			}

			if (ratingsStats != null) {
				session.delete(ratingsStats);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ratingsStats != null) {
			clearCache(ratingsStats);
		}

		return ratingsStats;
	}

	@Override
	public RatingsStats updateImpl(RatingsStats ratingsStats) {
		ratingsStats = toUnwrappedModel(ratingsStats);

		boolean isNew = ratingsStats.isNew();

		RatingsStatsModelImpl ratingsStatsModelImpl = (RatingsStatsModelImpl)ratingsStats;

		Session session = null;

		try {
			session = openSession();

			if (ratingsStats.isNew()) {
				session.save(ratingsStats);

				ratingsStats.setNew(false);
			}
			else {
				ratingsStats = (RatingsStats)session.merge(ratingsStats);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RatingsStatsModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats,
			false);

		clearUniqueFindersCache(ratingsStatsModelImpl);
		cacheUniqueFindersCache(ratingsStatsModelImpl, isNew);

		ratingsStats.resetOriginalValues();

		return ratingsStats;
	}

	protected RatingsStats toUnwrappedModel(RatingsStats ratingsStats) {
		if (ratingsStats instanceof RatingsStatsImpl) {
			return ratingsStats;
		}

		RatingsStatsImpl ratingsStatsImpl = new RatingsStatsImpl();

		ratingsStatsImpl.setNew(ratingsStats.isNew());
		ratingsStatsImpl.setPrimaryKey(ratingsStats.getPrimaryKey());

		ratingsStatsImpl.setStatsId(ratingsStats.getStatsId());
		ratingsStatsImpl.setCompanyId(ratingsStats.getCompanyId());
		ratingsStatsImpl.setClassNameId(ratingsStats.getClassNameId());
		ratingsStatsImpl.setClassPK(ratingsStats.getClassPK());
		ratingsStatsImpl.setTotalEntries(ratingsStats.getTotalEntries());
		ratingsStatsImpl.setTotalScore(ratingsStats.getTotalScore());
		ratingsStatsImpl.setAverageScore(ratingsStats.getAverageScore());

		return ratingsStatsImpl;
	}

	/**
	 * Returns the ratings stats with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings stats
	 * @return the ratings stats
	 * @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStatsException {
		RatingsStats ratingsStats = fetchByPrimaryKey(primaryKey);

		if (ratingsStats == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats with the primary key or throws a {@link NoSuchStatsException} if it could not be found.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats
	 * @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats findByPrimaryKey(long statsId)
		throws NoSuchStatsException {
		return findByPrimaryKey((Serializable)statsId);
	}

	/**
	 * Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings stats
	 * @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats fetchByPrimaryKey(Serializable primaryKey) {
		RatingsStats ratingsStats = (RatingsStats)entityCache.getResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
				RatingsStatsImpl.class, primaryKey);

		if (ratingsStats == _nullRatingsStats) {
			return null;
		}

		if (ratingsStats == null) {
			Session session = null;

			try {
				session = openSession();

				ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
						primaryKey);

				if (ratingsStats != null) {
					cacheResult(ratingsStats);
				}
				else {
					entityCache.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
						RatingsStatsImpl.class, primaryKey, _nullRatingsStats);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
					RatingsStatsImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	 */
	@Override
	public RatingsStats fetchByPrimaryKey(long statsId) {
		return fetchByPrimaryKey((Serializable)statsId);
	}

	@Override
	public Map<Serializable, RatingsStats> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RatingsStats> map = new HashMap<Serializable, RatingsStats>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RatingsStats ratingsStats = fetchByPrimaryKey(primaryKey);

			if (ratingsStats != null) {
				map.put(primaryKey, ratingsStats);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			RatingsStats ratingsStats = (RatingsStats)entityCache.getResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
					RatingsStatsImpl.class, primaryKey);

			if (ratingsStats == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ratingsStats);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RATINGSSTATS_WHERE_PKS_IN);

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

			for (RatingsStats ratingsStats : (List<RatingsStats>)q.list()) {
				map.put(ratingsStats.getPrimaryKeyObj(), ratingsStats);

				cacheResult(ratingsStats);

				uncachedPrimaryKeys.remove(ratingsStats.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
					RatingsStatsImpl.class, primaryKey, _nullRatingsStats);
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
	 * Returns all the ratings statses.
	 *
	 * @return the ratings statses
	 */
	@Override
	public List<RatingsStats> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @return the range of ratings statses
	 */
	@Override
	public List<RatingsStats> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ratings statses
	 */
	@Override
	public List<RatingsStats> findAll(int start, int end,
		OrderByComparator<RatingsStats> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ratings statses
	 */
	@Override
	public List<RatingsStats> findAll(int start, int end,
		OrderByComparator<RatingsStats> orderByComparator,
		boolean retrieveFromCache) {
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

		List<RatingsStats> list = null;

		if (retrieveFromCache) {
			list = (List<RatingsStats>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RATINGSSTATS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RATINGSSTATS;

				if (pagination) {
					sql = sql.concat(RatingsStatsModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ratings statses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RatingsStats ratingsStats : findAll()) {
			remove(ratingsStats);
		}
	}

	/**
	 * Returns the number of ratings statses.
	 *
	 * @return the number of ratings statses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RATINGSSTATS);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	protected Map<String, Integer> getTableColumnsMap() {
		return RatingsStatsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ratings stats persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RatingsStatsImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_RATINGSSTATS = "SELECT ratingsStats FROM RatingsStats ratingsStats";
	private static final String _SQL_SELECT_RATINGSSTATS_WHERE_PKS_IN = "SELECT ratingsStats FROM RatingsStats ratingsStats WHERE statsId IN (";
	private static final String _SQL_SELECT_RATINGSSTATS_WHERE = "SELECT ratingsStats FROM RatingsStats ratingsStats WHERE ";
	private static final String _SQL_COUNT_RATINGSSTATS = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats";
	private static final String _SQL_COUNT_RATINGSSTATS_WHERE = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ratingsStats.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RatingsStats exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RatingsStats exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RatingsStatsPersistenceImpl.class);
	private static final RatingsStats _nullRatingsStats = new RatingsStatsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RatingsStats> toCacheModel() {
				return _nullRatingsStatsCacheModel;
			}
		};

	private static final CacheModel<RatingsStats> _nullRatingsStatsCacheModel = new CacheModel<RatingsStats>() {
			@Override
			public RatingsStats toEntityModel() {
				return _nullRatingsStats;
			}
		};
}