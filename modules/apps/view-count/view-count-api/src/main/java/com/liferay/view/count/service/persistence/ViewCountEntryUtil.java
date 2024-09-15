/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.view.count.model.ViewCountEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the view count entry service. This utility wraps <code>com.liferay.view.count.service.persistence.impl.ViewCountEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @see ViewCountEntryPersistence
 * @generated
 */
public class ViewCountEntryUtil {

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
	public static void clearCache(ViewCountEntry viewCountEntry) {
		getPersistence().clearCache(viewCountEntry);
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
	public static Map<Serializable, ViewCountEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ViewCountEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ViewCountEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ViewCountEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ViewCountEntry update(ViewCountEntry viewCountEntry) {
		return getPersistence().update(viewCountEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ViewCountEntry update(
		ViewCountEntry viewCountEntry, ServiceContext serviceContext) {

		return getPersistence().update(viewCountEntry, serviceContext);
	}

	/**
	 * Returns all the view count entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching view count entries
	 */
	public static List<ViewCountEntry> findByC_CN(
		long companyId, long classNameId) {

		return getPersistence().findByC_CN(companyId, classNameId);
	}

	/**
	 * Returns a range of all the view count entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @return the range of matching view count entries
	 */
	public static List<ViewCountEntry> findByC_CN(
		long companyId, long classNameId, int start, int end) {

		return getPersistence().findByC_CN(companyId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the view count entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching view count entries
	 */
	public static List<ViewCountEntry> findByC_CN(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return getPersistence().findByC_CN(
			companyId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the view count entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching view count entries
	 */
	public static List<ViewCountEntry> findByC_CN(
		long companyId, long classNameId, int start, int end,
		OrderByComparator<ViewCountEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_CN(
			companyId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first view count entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching view count entry
	 * @throws NoSuchEntryException if a matching view count entry could not be found
	 */
	public static ViewCountEntry findByC_CN_First(
			long companyId, long classNameId,
			OrderByComparator<ViewCountEntry> orderByComparator)
		throws com.liferay.view.count.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_First(
			companyId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first view count entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching view count entry, or <code>null</code> if a matching view count entry could not be found
	 */
	public static ViewCountEntry fetchByC_CN_First(
		long companyId, long classNameId,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_First(
			companyId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last view count entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching view count entry
	 * @throws NoSuchEntryException if a matching view count entry could not be found
	 */
	public static ViewCountEntry findByC_CN_Last(
			long companyId, long classNameId,
			OrderByComparator<ViewCountEntry> orderByComparator)
		throws com.liferay.view.count.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_Last(
			companyId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last view count entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching view count entry, or <code>null</code> if a matching view count entry could not be found
	 */
	public static ViewCountEntry fetchByC_CN_Last(
		long companyId, long classNameId,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_Last(
			companyId, classNameId, orderByComparator);
	}

	/**
	 * Returns the view count entries before and after the current view count entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param viewCountEntryPK the primary key of the current view count entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next view count entry
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	public static ViewCountEntry[] findByC_CN_PrevAndNext(
			ViewCountEntryPK viewCountEntryPK, long companyId, long classNameId,
			OrderByComparator<ViewCountEntry> orderByComparator)
		throws com.liferay.view.count.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_PrevAndNext(
			viewCountEntryPK, companyId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the view count entries where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	public static void removeByC_CN(long companyId, long classNameId) {
		getPersistence().removeByC_CN(companyId, classNameId);
	}

	/**
	 * Returns the number of view count entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching view count entries
	 */
	public static int countByC_CN(long companyId, long classNameId) {
		return getPersistence().countByC_CN(companyId, classNameId);
	}

	/**
	 * Caches the view count entry in the entity cache if it is enabled.
	 *
	 * @param viewCountEntry the view count entry
	 */
	public static void cacheResult(ViewCountEntry viewCountEntry) {
		getPersistence().cacheResult(viewCountEntry);
	}

	/**
	 * Caches the view count entries in the entity cache if it is enabled.
	 *
	 * @param viewCountEntries the view count entries
	 */
	public static void cacheResult(List<ViewCountEntry> viewCountEntries) {
		getPersistence().cacheResult(viewCountEntries);
	}

	/**
	 * Creates a new view count entry with the primary key. Does not add the view count entry to the database.
	 *
	 * @param viewCountEntryPK the primary key for the new view count entry
	 * @return the new view count entry
	 */
	public static ViewCountEntry create(ViewCountEntryPK viewCountEntryPK) {
		return getPersistence().create(viewCountEntryPK);
	}

	/**
	 * Removes the view count entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry that was removed
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	public static ViewCountEntry remove(ViewCountEntryPK viewCountEntryPK)
		throws com.liferay.view.count.exception.NoSuchEntryException {

		return getPersistence().remove(viewCountEntryPK);
	}

	public static ViewCountEntry updateImpl(ViewCountEntry viewCountEntry) {
		return getPersistence().updateImpl(viewCountEntry);
	}

	/**
	 * Returns the view count entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	public static ViewCountEntry findByPrimaryKey(
			ViewCountEntryPK viewCountEntryPK)
		throws com.liferay.view.count.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(viewCountEntryPK);
	}

	/**
	 * Returns the view count entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry, or <code>null</code> if a view count entry with the primary key could not be found
	 */
	public static ViewCountEntry fetchByPrimaryKey(
		ViewCountEntryPK viewCountEntryPK) {

		return getPersistence().fetchByPrimaryKey(viewCountEntryPK);
	}

	/**
	 * Returns all the view count entries.
	 *
	 * @return the view count entries
	 */
	public static List<ViewCountEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @return the range of view count entries
	 */
	public static List<ViewCountEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of view count entries
	 */
	public static List<ViewCountEntry> findAll(
		int start, int end,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of view count entries
	 */
	public static List<ViewCountEntry> findAll(
		int start, int end, OrderByComparator<ViewCountEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the view count entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of view count entries.
	 *
	 * @return the number of view count entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static ViewCountEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ViewCountEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ViewCountEntryPersistence _persistence;

}