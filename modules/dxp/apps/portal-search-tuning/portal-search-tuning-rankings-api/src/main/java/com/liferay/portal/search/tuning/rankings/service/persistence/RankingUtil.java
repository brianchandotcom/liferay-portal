/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.rankings.model.Ranking;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ranking service. This utility wraps <code>com.liferay.portal.search.tuning.rankings.service.persistence.impl.RankingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see RankingPersistence
 * @generated
 */
public class RankingUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Ranking ranking) {
		getPersistence().clearCache(ranking);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Ranking> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Ranking> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Ranking> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Ranking> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Ranking> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Ranking update(Ranking ranking) {
		return getPersistence().update(ranking);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Ranking update(
		Ranking ranking, ServiceContext serviceContext) {

		return getPersistence().update(ranking, serviceContext);
	}

	/**
	 * Returns all the rankings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching rankings
	 */
	public static List<Ranking> findBycompanyId(long companyId) {
		return getPersistence().findBycompanyId(companyId);
	}

	/**
	 * Returns a range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @return the range of matching rankings
	 */
	public static List<Ranking> findBycompanyId(
		long companyId, int start, int end) {

		return getPersistence().findBycompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rankings
	 */
	public static List<Ranking> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<Ranking> orderByComparator) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching rankings
	 */
	public static List<Ranking> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<Ranking> orderByComparator, boolean useFinderCache) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ranking
	 * @throws NoSuchRankingException if a matching ranking could not be found
	 */
	public static Ranking findBycompanyId_First(
			long companyId, OrderByComparator<Ranking> orderByComparator)
		throws com.liferay.portal.search.tuning.rankings.exception.
			NoSuchRankingException {

		return getPersistence().findBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ranking, or <code>null</code> if a matching ranking could not be found
	 */
	public static Ranking fetchBycompanyId_First(
		long companyId, OrderByComparator<Ranking> orderByComparator) {

		return getPersistence().fetchBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ranking
	 * @throws NoSuchRankingException if a matching ranking could not be found
	 */
	public static Ranking findBycompanyId_Last(
			long companyId, OrderByComparator<Ranking> orderByComparator)
		throws com.liferay.portal.search.tuning.rankings.exception.
			NoSuchRankingException {

		return getPersistence().findBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ranking, or <code>null</code> if a matching ranking could not be found
	 */
	public static Ranking fetchBycompanyId_Last(
		long companyId, OrderByComparator<Ranking> orderByComparator) {

		return getPersistence().fetchBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the rankings before and after the current ranking in the ordered set where companyId = &#63;.
	 *
	 * @param rankingId the primary key of the current ranking
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ranking
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public static Ranking[] findBycompanyId_PrevAndNext(
			long rankingId, long companyId,
			OrderByComparator<Ranking> orderByComparator)
		throws com.liferay.portal.search.tuning.rankings.exception.
			NoSuchRankingException {

		return getPersistence().findBycompanyId_PrevAndNext(
			rankingId, companyId, orderByComparator);
	}

	/**
	 * Removes all the rankings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeBycompanyId(long companyId) {
		getPersistence().removeBycompanyId(companyId);
	}

	/**
	 * Returns the number of rankings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching rankings
	 */
	public static int countBycompanyId(long companyId) {
		return getPersistence().countBycompanyId(companyId);
	}

	/**
	 * Caches the ranking in the entity cache if it is enabled.
	 *
	 * @param ranking the ranking
	 */
	public static void cacheResult(Ranking ranking) {
		getPersistence().cacheResult(ranking);
	}

	/**
	 * Caches the rankings in the entity cache if it is enabled.
	 *
	 * @param rankings the rankings
	 */
	public static void cacheResult(List<Ranking> rankings) {
		getPersistence().cacheResult(rankings);
	}

	/**
	 * Creates a new ranking with the primary key. Does not add the ranking to the database.
	 *
	 * @param rankingId the primary key for the new ranking
	 * @return the new ranking
	 */
	public static Ranking create(long rankingId) {
		return getPersistence().create(rankingId);
	}

	/**
	 * Removes the ranking with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking that was removed
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public static Ranking remove(long rankingId)
		throws com.liferay.portal.search.tuning.rankings.exception.
			NoSuchRankingException {

		return getPersistence().remove(rankingId);
	}

	public static Ranking updateImpl(Ranking ranking) {
		return getPersistence().updateImpl(ranking);
	}

	/**
	 * Returns the ranking with the primary key or throws a <code>NoSuchRankingException</code> if it could not be found.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public static Ranking findByPrimaryKey(long rankingId)
		throws com.liferay.portal.search.tuning.rankings.exception.
			NoSuchRankingException {

		return getPersistence().findByPrimaryKey(rankingId);
	}

	/**
	 * Returns the ranking with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking, or <code>null</code> if a ranking with the primary key could not be found
	 */
	public static Ranking fetchByPrimaryKey(long rankingId) {
		return getPersistence().fetchByPrimaryKey(rankingId);
	}

	/**
	 * Returns all the rankings.
	 *
	 * @return the rankings
	 */
	public static List<Ranking> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @return the range of rankings
	 */
	public static List<Ranking> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rankings
	 */
	public static List<Ranking> findAll(
		int start, int end, OrderByComparator<Ranking> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rankings
	 */
	public static List<Ranking> findAll(
		int start, int end, OrderByComparator<Ranking> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the rankings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of rankings.
	 *
	 * @return the number of rankings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RankingPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<RankingPersistence, RankingPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(RankingPersistence.class);

		ServiceTracker<RankingPersistence, RankingPersistence> serviceTracker =
			new ServiceTracker<RankingPersistence, RankingPersistence>(
				bundle.getBundleContext(), RankingPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}