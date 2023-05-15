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

package com.liferay.akismet.service.persistence;

import com.liferay.akismet.model.AkismetEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the akismet entry service. This utility wraps <code>com.liferay.akismet.service.persistence.impl.AkismetEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Jamie Sammons
 * @see AkismetEntryPersistence
 * @generated
 */
public class AkismetEntryUtil {

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
	public static void clearCache(AkismetEntry akismetEntry) {
		getPersistence().clearCache(akismetEntry);
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
	public static Map<Serializable, AkismetEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AkismetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AkismetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AkismetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AkismetEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AkismetEntry update(AkismetEntry akismetEntry) {
		return getPersistence().update(akismetEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AkismetEntry update(
		AkismetEntry akismetEntry, ServiceContext serviceContext) {

		return getPersistence().update(akismetEntry, serviceContext);
	}

	/**
	 * Returns all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the matching akismet entries
	 */
	public static List<AkismetEntry> findByLtModifiedDate(Date modifiedDate) {
		return getPersistence().findByLtModifiedDate(modifiedDate);
	}

	/**
	 * Returns a range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of matching akismet entries
	 */
	public static List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end) {

		return getPersistence().findByLtModifiedDate(modifiedDate, start, end);
	}

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching akismet entries
	 */
	public static List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		OrderByComparator<AkismetEntry> orderByComparator) {

		return getPersistence().findByLtModifiedDate(
			modifiedDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching akismet entries
	 */
	public static List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		OrderByComparator<AkismetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtModifiedDate(
			modifiedDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public static AkismetEntry findByLtModifiedDate_First(
			Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().findByLtModifiedDate_First(
			modifiedDate, orderByComparator);
	}

	/**
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public static AkismetEntry fetchByLtModifiedDate_First(
		Date modifiedDate, OrderByComparator<AkismetEntry> orderByComparator) {

		return getPersistence().fetchByLtModifiedDate_First(
			modifiedDate, orderByComparator);
	}

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public static AkismetEntry findByLtModifiedDate_Last(
			Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().findByLtModifiedDate_Last(
			modifiedDate, orderByComparator);
	}

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public static AkismetEntry fetchByLtModifiedDate_Last(
		Date modifiedDate, OrderByComparator<AkismetEntry> orderByComparator) {

		return getPersistence().fetchByLtModifiedDate_Last(
			modifiedDate, orderByComparator);
	}

	/**
	 * Returns the akismet entries before and after the current akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param akismetEntryId the primary key of the current akismet entry
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry[] findByLtModifiedDate_PrevAndNext(
			long akismetEntryId, Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().findByLtModifiedDate_PrevAndNext(
			akismetEntryId, modifiedDate, orderByComparator);
	}

	/**
	 * Removes all the akismet entries where modifiedDate &lt; &#63; from the database.
	 *
	 * @param modifiedDate the modified date
	 */
	public static void removeByLtModifiedDate(Date modifiedDate) {
		getPersistence().removeByLtModifiedDate(modifiedDate);
	}

	/**
	 * Returns the number of akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the number of matching akismet entries
	 */
	public static int countByLtModifiedDate(Date modifiedDate) {
		return getPersistence().countByLtModifiedDate(modifiedDate);
	}

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public static AkismetEntry findByC_C(long classNameId, long classPK)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public static AkismetEntry fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public static AkismetEntry fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the akismet entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the akismet entry that was removed
	 */
	public static AkismetEntry removeByC_C(long classNameId, long classPK)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of akismet entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching akismet entries
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Caches the akismet entry in the entity cache if it is enabled.
	 *
	 * @param akismetEntry the akismet entry
	 */
	public static void cacheResult(AkismetEntry akismetEntry) {
		getPersistence().cacheResult(akismetEntry);
	}

	/**
	 * Caches the akismet entries in the entity cache if it is enabled.
	 *
	 * @param akismetEntries the akismet entries
	 */
	public static void cacheResult(List<AkismetEntry> akismetEntries) {
		getPersistence().cacheResult(akismetEntries);
	}

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	public static AkismetEntry create(long akismetEntryId) {
		return getPersistence().create(akismetEntryId);
	}

	/**
	 * Removes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry remove(long akismetEntryId)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().remove(akismetEntryId);
	}

	public static AkismetEntry updateImpl(AkismetEntry akismetEntry) {
		return getPersistence().updateImpl(akismetEntry);
	}

	/**
	 * Returns the akismet entry with the primary key or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry findByPrimaryKey(long akismetEntryId)
		throws com.liferay.akismet.exception.NoSuchAkismetEntryException {

		return getPersistence().findByPrimaryKey(akismetEntryId);
	}

	/**
	 * Returns the akismet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry, or <code>null</code> if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry fetchByPrimaryKey(long akismetEntryId) {
		return getPersistence().fetchByPrimaryKey(akismetEntryId);
	}

	/**
	 * Returns all the akismet entries.
	 *
	 * @return the akismet entries
	 */
	public static List<AkismetEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of akismet entries
	 */
	public static List<AkismetEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of akismet entries
	 */
	public static List<AkismetEntry> findAll(
		int start, int end, OrderByComparator<AkismetEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of akismet entries
	 */
	public static List<AkismetEntry> findAll(
		int start, int end, OrderByComparator<AkismetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the akismet entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AkismetEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AkismetEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AkismetEntryPersistence _persistence;

}