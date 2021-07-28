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

package com.liferay.search.tuning.synonyms.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the st synonyms entry service. This utility wraps <code>com.liferay.search.tuning.synonyms.service.persistence.impl.STSynonymsEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see STSynonymsEntryPersistence
 * @generated
 */
public class STSynonymsEntryUtil {

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
	public static void clearCache(STSynonymsEntry stSynonymsEntry) {
		getPersistence().clearCache(stSynonymsEntry);
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
	public static Map<Serializable, STSynonymsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<STSynonymsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<STSynonymsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<STSynonymsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static STSynonymsEntry update(STSynonymsEntry stSynonymsEntry) {
		return getPersistence().update(stSynonymsEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static STSynonymsEntry update(
		STSynonymsEntry stSynonymsEntry, ServiceContext serviceContext) {

		return getPersistence().update(stSynonymsEntry, serviceContext);
	}

	/**
	 * Returns all the st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st synonyms entries
	 */
	public static List<STSynonymsEntry> findBycompanyId(long companyId) {
		return getPersistence().findBycompanyId(companyId);
	}

	/**
	 * Returns a range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of matching st synonyms entries
	 */
	public static List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end) {

		return getPersistence().findBycompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st synonyms entries
	 */
	public static List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st synonyms entries
	 */
	public static List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBycompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	public static STSynonymsEntry findBycompanyId_First(
			long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws com.liferay.search.tuning.synonyms.exception.
			NoSuchSTSynonymsEntryException {

		return getPersistence().findBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	public static STSynonymsEntry fetchBycompanyId_First(
		long companyId, OrderByComparator<STSynonymsEntry> orderByComparator) {

		return getPersistence().fetchBycompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	public static STSynonymsEntry findBycompanyId_Last(
			long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws com.liferay.search.tuning.synonyms.exception.
			NoSuchSTSynonymsEntryException {

		return getPersistence().findBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	public static STSynonymsEntry fetchBycompanyId_Last(
		long companyId, OrderByComparator<STSynonymsEntry> orderByComparator) {

		return getPersistence().fetchBycompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the st synonyms entries before and after the current st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param STSynonymsEntryId the primary key of the current st synonyms entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry[] findBycompanyId_PrevAndNext(
			long STSynonymsEntryId, long companyId,
			OrderByComparator<STSynonymsEntry> orderByComparator)
		throws com.liferay.search.tuning.synonyms.exception.
			NoSuchSTSynonymsEntryException {

		return getPersistence().findBycompanyId_PrevAndNext(
			STSynonymsEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the st synonyms entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeBycompanyId(long companyId) {
		getPersistence().removeBycompanyId(companyId);
	}

	/**
	 * Returns the number of st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st synonyms entries
	 */
	public static int countBycompanyId(long companyId) {
		return getPersistence().countBycompanyId(companyId);
	}

	/**
	 * Caches the st synonyms entry in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 */
	public static void cacheResult(STSynonymsEntry stSynonymsEntry) {
		getPersistence().cacheResult(stSynonymsEntry);
	}

	/**
	 * Caches the st synonyms entries in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntries the st synonyms entries
	 */
	public static void cacheResult(List<STSynonymsEntry> stSynonymsEntries) {
		getPersistence().cacheResult(stSynonymsEntries);
	}

	/**
	 * Creates a new st synonyms entry with the primary key. Does not add the st synonyms entry to the database.
	 *
	 * @param STSynonymsEntryId the primary key for the new st synonyms entry
	 * @return the new st synonyms entry
	 */
	public static STSynonymsEntry create(long STSynonymsEntryId) {
		return getPersistence().create(STSynonymsEntryId);
	}

	/**
	 * Removes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry remove(long STSynonymsEntryId)
		throws com.liferay.search.tuning.synonyms.exception.
			NoSuchSTSynonymsEntryException {

		return getPersistence().remove(STSynonymsEntryId);
	}

	public static STSynonymsEntry updateImpl(STSynonymsEntry stSynonymsEntry) {
		return getPersistence().updateImpl(stSynonymsEntry);
	}

	/**
	 * Returns the st synonyms entry with the primary key or throws a <code>NoSuchSTSynonymsEntryException</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry findByPrimaryKey(long STSynonymsEntryId)
		throws com.liferay.search.tuning.synonyms.exception.
			NoSuchSTSynonymsEntryException {

		return getPersistence().findByPrimaryKey(STSynonymsEntryId);
	}

	/**
	 * Returns the st synonyms entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry, or <code>null</code> if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry fetchByPrimaryKey(long STSynonymsEntryId) {
		return getPersistence().fetchByPrimaryKey(STSynonymsEntryId);
	}

	/**
	 * Returns all the st synonyms entries.
	 *
	 * @return the st synonyms entries
	 */
	public static List<STSynonymsEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of st synonyms entries
	 */
	public static List<STSynonymsEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st synonyms entries
	 */
	public static List<STSynonymsEntry> findAll(
		int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st synonyms entries
	 */
	public static List<STSynonymsEntry> findAll(
		int start, int end,
		OrderByComparator<STSynonymsEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the st synonyms entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of st synonyms entries.
	 *
	 * @return the number of st synonyms entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static STSynonymsEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<STSynonymsEntryPersistence, STSynonymsEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			STSynonymsEntryPersistence.class);

		ServiceTracker<STSynonymsEntryPersistence, STSynonymsEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<STSynonymsEntryPersistence, STSynonymsEntryPersistence>(
						bundle.getBundleContext(),
						STSynonymsEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}