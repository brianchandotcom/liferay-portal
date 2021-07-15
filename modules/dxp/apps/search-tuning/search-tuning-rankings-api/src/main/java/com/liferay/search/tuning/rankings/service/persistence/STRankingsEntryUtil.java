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

package com.liferay.search.tuning.rankings.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the st rankings entry service. This utility wraps <code>com.liferay.search.tuning.rankings.service.persistence.impl.STRankingsEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see STRankingsEntryPersistence
 * @generated
 */
public class STRankingsEntryUtil {

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
	public static void clearCache(STRankingsEntry stRankingsEntry) {
		getPersistence().clearCache(stRankingsEntry);
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
	public static Map<Serializable, STRankingsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<STRankingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<STRankingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<STRankingsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static STRankingsEntry update(STRankingsEntry stRankingsEntry) {
		return getPersistence().update(stRankingsEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static STRankingsEntry update(
		STRankingsEntry stRankingsEntry, ServiceContext serviceContext) {

		return getPersistence().update(stRankingsEntry, serviceContext);
	}

	/**
	 * Returns all the st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st rankings entries
	 */
	public static List<STRankingsEntry> findBycompanyId(long companyId) {
		return getPersistence().findBycompanyId(companyId);
	}

	/**
	 * Returns a range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of matching st rankings entries
	 */
	public static List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end) {

		return getPersistence().findBycompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st rankings entries
	 */
	public static List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st rankings entries
	 */
	public static List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	public static STRankingsEntry findBycompanyId_First(
			long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws com.liferay.search.tuning.rankings.exception.
			NoSuchSTRankingsEntryException {

		return getPersistence().findBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	public static STRankingsEntry fetchBycompanyId_First(
		long companyId, OrderByComparator<STRankingsEntry> orderByComparator) {

		return getPersistence().fetchBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	public static STRankingsEntry findBycompanyId_Last(
			long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws com.liferay.search.tuning.rankings.exception.
			NoSuchSTRankingsEntryException {

		return getPersistence().findBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	public static STRankingsEntry fetchBycompanyId_Last(
		long companyId, OrderByComparator<STRankingsEntry> orderByComparator) {

		return getPersistence().fetchBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the st rankings entries before and after the current st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param STRankingsEntryId the primary key of the current st rankings entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public static STRankingsEntry[] findBycompanyId_PrevAndNext(
			long STRankingsEntryId, long companyId,
			OrderByComparator<STRankingsEntry> orderByComparator)
		throws com.liferay.search.tuning.rankings.exception.
			NoSuchSTRankingsEntryException {

		return getPersistence().findBycompanyId_PrevAndNext(
			STRankingsEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the st rankings entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeBycompanyId(long companyId) {
		getPersistence().removeBycompanyId(companyId);
	}

	/**
	 * Returns the number of st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st rankings entries
	 */
	public static int countBycompanyId(long companyId) {
		return getPersistence().countBycompanyId(companyId);
	}

	/**
	 * Caches the st rankings entry in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntry the st rankings entry
	 */
	public static void cacheResult(STRankingsEntry stRankingsEntry) {
		getPersistence().cacheResult(stRankingsEntry);
	}

	/**
	 * Caches the st rankings entries in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntries the st rankings entries
	 */
	public static void cacheResult(List<STRankingsEntry> stRankingsEntries) {
		getPersistence().cacheResult(stRankingsEntries);
	}

	/**
	 * Creates a new st rankings entry with the primary key. Does not add the st rankings entry to the database.
	 *
	 * @param STRankingsEntryId the primary key for the new st rankings entry
	 * @return the new st rankings entry
	 */
	public static STRankingsEntry create(long STRankingsEntryId) {
		return getPersistence().create(STRankingsEntryId);
	}

	/**
	 * Removes the st rankings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry that was removed
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public static STRankingsEntry remove(long STRankingsEntryId)
		throws com.liferay.search.tuning.rankings.exception.
			NoSuchSTRankingsEntryException {

		return getPersistence().remove(STRankingsEntryId);
	}

	public static STRankingsEntry updateImpl(STRankingsEntry stRankingsEntry) {
		return getPersistence().updateImpl(stRankingsEntry);
	}

	/**
	 * Returns the st rankings entry with the primary key or throws a <code>NoSuchSTRankingsEntryException</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public static STRankingsEntry findByPrimaryKey(long STRankingsEntryId)
		throws com.liferay.search.tuning.rankings.exception.
			NoSuchSTRankingsEntryException {

		return getPersistence().findByPrimaryKey(STRankingsEntryId);
	}

	/**
	 * Returns the st rankings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry, or <code>null</code> if a st rankings entry with the primary key could not be found
	 */
	public static STRankingsEntry fetchByPrimaryKey(long STRankingsEntryId) {
		return getPersistence().fetchByPrimaryKey(STRankingsEntryId);
	}

	/**
	 * Returns all the st rankings entries.
	 *
	 * @return the st rankings entries
	 */
	public static List<STRankingsEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of st rankings entries
	 */
	public static List<STRankingsEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st rankings entries
	 */
	public static List<STRankingsEntry> findAll(
		int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st rankings entries
	 */
	public static List<STRankingsEntry> findAll(
		int start, int end,
		OrderByComparator<STRankingsEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the st rankings entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of st rankings entries.
	 *
	 * @return the number of st rankings entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static STRankingsEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<STRankingsEntryPersistence, STRankingsEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			STRankingsEntryPersistence.class);

		ServiceTracker<STRankingsEntryPersistence, STRankingsEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<STRankingsEntryPersistence, STRankingsEntryPersistence>(
						bundle.getBundleContext(),
						STRankingsEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}