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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the ratings stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsPersistence
 * @see RatingsStatsUtil
 * @generated
 */
public class RatingsStatsPersistenceImpl extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RatingsStatsUtil} to access the ratings stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RatingsStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, RatingsStatsImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, RatingsStatsImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the ratings stats in the entity cache if it is enabled.
	 *
	 * @param ratingsStats the ratings stats
	 */
	public void cacheResult(RatingsStats ratingsStats) {
		EntityCacheUtil.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				Long.valueOf(ratingsStats.getClassNameId()),
				Long.valueOf(ratingsStats.getClassPK())
			}, ratingsStats);

		ratingsStats.resetOriginalValues();
	}

	/**
	 * Caches the ratings statses in the entity cache if it is enabled.
	 *
	 * @param ratingsStatses the ratings statses
	 */
	public void cacheResult(List<RatingsStats> ratingsStatses) {
		for (RatingsStats ratingsStats : ratingsStatses) {
			if (EntityCacheUtil.getResult(
						RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
						RatingsStatsImpl.class, ratingsStats.getPrimaryKey()) == null) {
				cacheResult(ratingsStats);
			}
		}
	}

	/**
	 * Clears the cache for all ratings statses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(RatingsStatsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(RatingsStatsImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the ratings stats.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RatingsStats ratingsStats) {
		EntityCacheUtil.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				Long.valueOf(ratingsStats.getClassNameId()),
				Long.valueOf(ratingsStats.getClassPK())
			});
	}

	/**
	 * Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	 *
	 * @param statsId the primary key for the new ratings stats
	 * @return the new ratings stats
	 */
	public RatingsStats create(long statsId) {
		RatingsStats ratingsStats = new RatingsStatsImpl();

		ratingsStats.setNew(true);
		ratingsStats.setPrimaryKey(statsId);

		return ratingsStats;
	}

	/**
	 * Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ratings stats
	 * @return the ratings stats that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsStats remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats that was removed
	 * @throws com.liferay.portlet.ratings.NoSuchStatsException if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats remove(long statsId)
		throws NoSuchStatsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RatingsStats ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
					Long.valueOf(statsId));

			if (ratingsStats == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsId);
				}

				throw new NoSuchStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					statsId);
			}

			return ratingsStatsPersistence.remove(ratingsStats);
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

	/**
	 * Removes the ratings stats from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ratingsStats the ratings stats
	 * @return the ratings stats that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsStats remove(RatingsStats ratingsStats)
		throws SystemException {
		return super.remove(ratingsStats);
	}

	@Override
	protected RatingsStats removeImpl(RatingsStats ratingsStats)
		throws SystemException {
		ratingsStats = toUnwrappedModel(ratingsStats);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ratingsStats);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RatingsStatsModelImpl ratingsStatsModelImpl = (RatingsStatsModelImpl)ratingsStats;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				Long.valueOf(ratingsStatsModelImpl.getClassNameId()),
				Long.valueOf(ratingsStatsModelImpl.getClassPK())
			});

		EntityCacheUtil.removeResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

		return ratingsStats;
	}

	@Override
	public RatingsStats updateImpl(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge) throws SystemException {
		ratingsStats = toUnwrappedModel(ratingsStats);

		boolean isNew = ratingsStats.isNew();

		RatingsStatsModelImpl ratingsStatsModelImpl = (RatingsStatsModelImpl)ratingsStats;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ratingsStats, merge);

			ratingsStats.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		if (!isNew &&
				((ratingsStats.getClassNameId() != ratingsStatsModelImpl.getOriginalClassNameId()) ||
				(ratingsStats.getClassPK() != ratingsStatsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					Long.valueOf(ratingsStatsModelImpl.getOriginalClassNameId()),
					Long.valueOf(ratingsStatsModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((ratingsStats.getClassNameId() != ratingsStatsModelImpl.getOriginalClassNameId()) ||
				(ratingsStats.getClassPK() != ratingsStatsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					Long.valueOf(ratingsStats.getClassNameId()),
					Long.valueOf(ratingsStats.getClassPK())
				}, ratingsStats);
		}

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
	 * @throws com.liferay.portal.NoSuchModelException if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsStats findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ratings stats with the primary key or throws a {@link com.liferay.portlet.ratings.NoSuchStatsException} if it could not be found.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats
	 * @throws com.liferay.portlet.ratings.NoSuchStatsException if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats findByPrimaryKey(long statsId)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = fetchByPrimaryKey(statsId);

		if (ratingsStats == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsId);
			}

			throw new NoSuchStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statsId);
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings stats
	 * @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsStats fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats fetchByPrimaryKey(long statsId)
		throws SystemException {
		RatingsStats ratingsStats = (RatingsStats)EntityCacheUtil.getResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
				RatingsStatsImpl.class, statsId);

		if (ratingsStats == _nullRatingsStats) {
			return null;
		}

		if (ratingsStats == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ratingsStats = (RatingsStats)session.get(RatingsStatsImpl.class,
						Long.valueOf(statsId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ratingsStats != null) {
					cacheResult(ratingsStats);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(RatingsStatsModelImpl.ENTITY_CACHE_ENABLED,
						RatingsStatsImpl.class, statsId, _nullRatingsStats);
				}

				closeSession(session);
			}
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.ratings.NoSuchStatsException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings stats
	 * @throws com.liferay.portlet.ratings.NoSuchStatsException if a matching ratings stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsStats fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

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

				result = list;

				RatingsStats ratingsStats = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					ratingsStats = list.get(0);

					cacheResult(ratingsStats);

					if ((ratingsStats.getClassNameId() != classNameId) ||
							(ratingsStats.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, ratingsStats);
					}
				}

				return ratingsStats;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
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
				return (RatingsStats)result;
			}
		}
	}

	/**
	 * Returns all the ratings statses.
	 *
	 * @return the ratings statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsStats> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @return the range of ratings statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsStats> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ratings statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsStats> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<RatingsStats> list = (List<RatingsStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RatingsStats>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes the ratings stats where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchStatsException, SystemException {
		RatingsStats ratingsStats = findByC_C(classNameId, classPK);

		ratingsStatsPersistence.remove(ratingsStats);
	}

	/**
	 * Removes all the ratings statses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (RatingsStats ratingsStats : findAll()) {
			ratingsStatsPersistence.remove(ratingsStats);
		}
	}

	/**
	 * Returns the number of ratings statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching ratings statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of ratings statses.
	 *
	 * @return the number of ratings statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RATINGSSTATS);

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
	 * Initializes the ratings stats persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsStats")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RatingsStats>> listenersList = new ArrayList<ModelListener<RatingsStats>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RatingsStats>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(RatingsStatsImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = RatingsEntryPersistence.class)
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_RATINGSSTATS = "SELECT ratingsStats FROM RatingsStats ratingsStats";
	private static final String _SQL_SELECT_RATINGSSTATS_WHERE = "SELECT ratingsStats FROM RatingsStats ratingsStats WHERE ";
	private static final String _SQL_COUNT_RATINGSSTATS = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats";
	private static final String _SQL_COUNT_RATINGSSTATS_WHERE = "SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats WHERE ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ratingsStats.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "ratingsStats.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ratingsStats.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RatingsStats exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RatingsStats exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(RatingsStatsPersistenceImpl.class);
	private static RatingsStats _nullRatingsStats = new RatingsStatsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RatingsStats> toCacheModel() {
				return _nullRatingsStatsCacheModel;
			}
		};

	private static CacheModel<RatingsStats> _nullRatingsStatsCacheModel = new CacheModel<RatingsStats>() {
			public RatingsStats toEntityModel() {
				return _nullRatingsStats;
			}
		};
}