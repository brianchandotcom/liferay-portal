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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;
import com.liferay.ratings.kernel.exception.NoSuchStatsException;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.model.RatingsStatsTable;
import com.liferay.ratings.kernel.service.persistence.RatingsStatsPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
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
 * @generated
 */
public class RatingsStatsPersistenceImpl
	extends BasePersistenceImpl<RatingsStats>
	implements RatingsStatsPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RatingsStatsUtil</code> to access the ratings stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RatingsStatsImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;
	private FinderPath _finderPathWithPaginationCountByC_C;

	/**
	 * Returns all the ratings statses where classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the matching ratings statses
	 */
	@Override
	public List<RatingsStats> findByC_C(long classNameId, long[] classPKs) {
		return findByC_C(
			classNameId, classPKs, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings statses where classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @return the range of matching ratings statses
	 */
	@Override
	public List<RatingsStats> findByC_C(
		long classNameId, long[] classPKs, int start, int end) {

		return findByC_C(classNameId, classPKs, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings statses where classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings statses
	 */
	@Override
	public List<RatingsStats> findByC_C(
		long classNameId, long[] classPKs, int start, int end,
		OrderByComparator<RatingsStats> orderByComparator) {

		return findByC_C(
			classNameId, classPKs, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings statses where classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings statses
	 */
	@Override
	public List<RatingsStats> findByC_C(
		long classNameId, long[] classPKs, int start, int end,
		OrderByComparator<RatingsStats> orderByComparator,
		boolean useFinderCache) {

		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		if (classPKs.length == 1) {
			RatingsStats ratingsStats = fetchByC_C(classNameId, classPKs[0]);

			if (ratingsStats == null) {
				return Collections.emptyList();
			}
			else {
				return Collections.singletonList(ratingsStats);
			}
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					classNameId, StringUtil.merge(classPKs)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				classNameId, StringUtil.merge(classPKs), start, end,
				orderByComparator
			};
		}

		List<RatingsStats> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsStats>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByC_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsStats ratingsStats : list) {
					if ((classNameId != ratingsStats.getClassNameId()) ||
						!ArrayUtil.contains(
							classPKs, ratingsStats.getClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) &&
					(end == QueryUtil.ALL_POS) &&
					(databaseInMaxParameters > 0) &&
					(classPKs.length > databaseInMaxParameters)) {

					list = new ArrayList<RatingsStats>();

					long[][] classPKsPages = (long[][])ArrayUtil.split(
						classPKs, databaseInMaxParameters);

					for (long[] classPKsPage : classPKsPages) {
						list.addAll(
							_findByC_C(
								classNameId, classPKsPage, start, end,
								orderByComparator));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByC_C(
						classNameId, classPKs, start, end, orderByComparator);
				}

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByC_C, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
		}

		return list;
	}

	private List<RatingsStats> _findByC_C(
		long classNameId, long[] classPKs, int start, int end,
		OrderByComparator<RatingsStats> orderByComparator) {

		List<RatingsStats> list = null;

		StringBundler sb = new StringBundler();

		sb.append(_SQL_SELECT_RATINGSSTATS_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		if (classPKs.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_7);

			sb.append(StringUtil.merge(classPKs));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (orderByComparator != null) {
			appendOrderByComparator(
				sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
		}
		else {
			sb.append(RatingsStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(query);

			queryPos.add(classNameId);

			list = (List<RatingsStats>)QueryUtil.list(
				query, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchStatsException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ratings stats
	 * @throws NoSuchStatsException if a matching ratings stats could not be found
	 */
	@Override
	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException {

		RatingsStats ratingsStats = fetchByC_C(classNameId, classPK);

		if (ratingsStats == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStatsException(sb.toString());
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
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
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	 */
	@Override
	public RatingsStats fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof RatingsStats) {
			RatingsStats ratingsStats = (RatingsStats)result;

			if ((classNameId != ratingsStats.getClassNameId()) ||
				(classPK != ratingsStats.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_RATINGSSTATS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<RatingsStats> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					RatingsStats ratingsStats = list.get(0);

					result = ratingsStats;

					cacheResult(ratingsStats);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * @param classPK the class pk
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
	 * @param classPK the class pk
	 * @return the number of matching ratings statses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_RATINGSSTATS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	/**
	 * Returns the number of ratings statses where classNameId = &#63; and classPK = any &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the number of matching ratings statses
	 */
	@Override
	public int countByC_C(long classNameId, long[] classPKs) {
		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {classNameId, StringUtil.merge(classPKs)};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByC_C, finderArgs, this);
		}

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
					(classPKs.length > databaseInMaxParameters)) {

					count = Long.valueOf(0);

					long[][] classPKsPages = (long[][])ArrayUtil.split(
						classPKs, databaseInMaxParameters);

					for (long[] classPKsPage : classPKsPages) {
						count += Long.valueOf(
							_countByC_C(classNameId, classPKsPage));
					}
				}
				else {
					count = Long.valueOf(_countByC_C(classNameId, classPKs));
				}

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByC_C, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
		}

		return count.intValue();
	}

	private int _countByC_C(long classNameId, long[] classPKs) {
		Long count = null;

		StringBundler sb = new StringBundler();

		sb.append(_SQL_COUNT_RATINGSSTATS_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		if (classPKs.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_7);

			sb.append(StringUtil.merge(classPKs));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(query);

			queryPos.add(classNameId);

			count = (Long)query.uniqueResult();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"ratingsStats.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"ratingsStats.classPK = ?";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_7 =
		"ratingsStats.classPK IN (";

	public RatingsStatsPersistenceImpl() {
		setModelClass(RatingsStats.class);

		setModelImplClass(RatingsStatsImpl.class);
		setModelPKClass(long.class);

		setTable(RatingsStatsTable.INSTANCE);
	}

	/**
	 * Caches the ratings stats in the entity cache if it is enabled.
	 *
	 * @param ratingsStats the ratings stats
	 */
	@Override
	public void cacheResult(RatingsStats ratingsStats) {
		if (ratingsStats.getCtCollectionId() != 0) {
			ratingsStats.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				ratingsStats.getClassNameId(), ratingsStats.getClassPK()
			},
			ratingsStats);

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
			if (ratingsStats.getCtCollectionId() != 0) {
				ratingsStats.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					RatingsStatsImpl.class, ratingsStats.getPrimaryKey()) ==
						null) {

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
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RatingsStatsImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ratings stats.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RatingsStats ratingsStats) {
		EntityCacheUtil.removeResult(
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RatingsStatsModelImpl)ratingsStats, true);
	}

	@Override
	public void clearCache(List<RatingsStats> ratingsStatses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RatingsStats ratingsStats : ratingsStatses) {
			EntityCacheUtil.removeResult(
				RatingsStatsImpl.class, ratingsStats.getPrimaryKey());

			clearUniqueFindersCache((RatingsStatsModelImpl)ratingsStats, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(RatingsStatsImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RatingsStatsModelImpl ratingsStatsModelImpl) {

		Object[] args = new Object[] {
			ratingsStatsModelImpl.getClassNameId(),
			ratingsStatsModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C, args, ratingsStatsModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RatingsStatsModelImpl ratingsStatsModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ratingsStatsModelImpl.getClassNameId(),
				ratingsStatsModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
		}

		if ((ratingsStatsModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ratingsStatsModelImpl.getOriginalClassNameId(),
				ratingsStatsModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
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

		ratingsStats.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			RatingsStats ratingsStats = (RatingsStats)session.get(
				RatingsStatsImpl.class, primaryKey);

			if (ratingsStats == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ratingsStats);
		}
		catch (NoSuchStatsException noSuchEntityException) {
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
	protected RatingsStats removeImpl(RatingsStats ratingsStats) {
		if (!CTPersistenceHelperUtil.isRemove(ratingsStats)) {
			return ratingsStats;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ratingsStats)) {
				ratingsStats = (RatingsStats)session.get(
					RatingsStatsImpl.class, ratingsStats.getPrimaryKeyObj());
			}

			if (ratingsStats != null) {
				session.delete(ratingsStats);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = ratingsStats.isNew();

		if (!(ratingsStats instanceof RatingsStatsModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ratingsStats.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ratingsStats);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ratingsStats proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RatingsStats implementation " +
					ratingsStats.getClass());
		}

		RatingsStatsModelImpl ratingsStatsModelImpl =
			(RatingsStatsModelImpl)ratingsStats;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ratingsStats.getCreateDate() == null)) {
			if (serviceContext == null) {
				ratingsStats.setCreateDate(now);
			}
			else {
				ratingsStats.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ratingsStatsModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ratingsStats.setModifiedDate(now);
			}
			else {
				ratingsStats.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(ratingsStats)) {
				if (!isNew) {
					session.evict(
						RatingsStatsImpl.class,
						ratingsStats.getPrimaryKeyObj());
				}

				session.save(ratingsStats);

				ratingsStats.setNew(false);
			}
			else {
				ratingsStats = (RatingsStats)session.merge(ratingsStats);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ratingsStats.getCtCollectionId() != 0) {
			ratingsStats.resetOriginalValues();

			return ratingsStats;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ratingsStatsModelImpl.getClassNameId(),
				ratingsStatsModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ratingsStatsModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsStatsModelImpl.getOriginalClassNameId(),
					ratingsStatsModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					ratingsStatsModelImpl.getClassNameId(),
					ratingsStatsModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}
		}

		EntityCacheUtil.putResult(
			RatingsStatsImpl.class, ratingsStats.getPrimaryKey(), ratingsStats,
			false);

		clearUniqueFindersCache(ratingsStatsModelImpl, false);
		cacheUniqueFindersCache(ratingsStatsModelImpl);

		ratingsStats.resetOriginalValues();

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatsException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ratingsStats;
	}

	/**
	 * Returns the ratings stats with the primary key or throws a <code>NoSuchStatsException</code> if it could not be found.
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
		if (CTPersistenceHelperUtil.isProductionMode(RatingsStats.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		RatingsStats ratingsStats = null;

		Session session = null;

		try {
			session = openSession();

			ratingsStats = (RatingsStats)session.get(
				RatingsStatsImpl.class, primaryKey);

			if (ratingsStats != null) {
				cacheResult(ratingsStats);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
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

		if (CTPersistenceHelperUtil.isProductionMode(RatingsStats.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RatingsStats> map =
			new HashMap<Serializable, RatingsStats>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RatingsStats ratingsStats = fetchByPrimaryKey(primaryKey);

			if (ratingsStats != null) {
				map.put(primaryKey, ratingsStats);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (RatingsStats ratingsStats : (List<RatingsStats>)query.list()) {
				map.put(ratingsStats.getPrimaryKeyObj(), ratingsStats);

				cacheResult(ratingsStats);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ratings statses
	 */
	@Override
	public List<RatingsStats> findAll(
		int start, int end, OrderByComparator<RatingsStats> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ratings statses
	 */
	@Override
	public List<RatingsStats> findAll(
		int start, int end, OrderByComparator<RatingsStats> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<RatingsStats> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsStats>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RATINGSSTATS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RATINGSSTATS;

				sql = sql.concat(RatingsStatsModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RatingsStats>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsStats.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_RATINGSSTATS);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "statsId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RATINGSSTATS;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return RatingsStatsModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "RatingsStats";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("totalEntries");
		ctStrictColumnNames.add("totalScore");
		ctStrictColumnNames.add("averageScore");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("statsId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"classNameId", "classPK"});
	}

	/**
	 * Initializes the ratings stats persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RatingsStatsImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RatingsStatsImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			RatingsStatsImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			RatingsStatsImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			RatingsStatsModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsStatsModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathFetchByC_C = new FinderPath(
			RatingsStatsImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			RatingsStatsModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsStatsModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RatingsStatsImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RATINGSSTATS =
		"SELECT ratingsStats FROM RatingsStats ratingsStats";

	private static final String _SQL_SELECT_RATINGSSTATS_WHERE =
		"SELECT ratingsStats FROM RatingsStats ratingsStats WHERE ";

	private static final String _SQL_COUNT_RATINGSSTATS =
		"SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats";

	private static final String _SQL_COUNT_RATINGSSTATS_WHERE =
		"SELECT COUNT(ratingsStats) FROM RatingsStats ratingsStats WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ratingsStats.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RatingsStats exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RatingsStats exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RatingsStatsPersistenceImpl.class);

}