/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the grand parent service. This utility wraps <code>com.liferay.change.tracking.sample.service.persistence.impl.GrandParentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GrandParentPersistence
 * @generated
 */
public class GrandParentUtil {

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
	public static void clearCache(GrandParent grandParent) {
		getPersistence().clearCache(grandParent);
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
	public static Map<Serializable, GrandParent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<GrandParent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<GrandParent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<GrandParent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<GrandParent> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static GrandParent update(GrandParent grandParent) {
		return getPersistence().update(grandParent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static GrandParent update(
		GrandParent grandParent, ServiceContext serviceContext) {

		return getPersistence().update(grandParent, serviceContext);
	}

	/**
	 * Returns all the grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching grand parents
	 */
	public static List<GrandParent> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of matching grand parents
	 */
	public static List<GrandParent> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching grand parents
	 */
	public static List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<GrandParent> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching grand parents
	 */
	public static List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<GrandParent> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	public static GrandParent findByCompanyId_First(
			long companyId, OrderByComparator<GrandParent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchGrandParentException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	public static GrandParent fetchByCompanyId_First(
		long companyId, OrderByComparator<GrandParent> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	public static GrandParent findByCompanyId_Last(
			long companyId, OrderByComparator<GrandParent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchGrandParentException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	public static GrandParent fetchByCompanyId_Last(
		long companyId, OrderByComparator<GrandParent> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the grand parents before and after the current grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param grandParentId the primary key of the current grand parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public static GrandParent[] findByCompanyId_PrevAndNext(
			long grandParentId, long companyId,
			OrderByComparator<GrandParent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchGrandParentException {

		return getPersistence().findByCompanyId_PrevAndNext(
			grandParentId, companyId, orderByComparator);
	}

	/**
	 * Removes all the grand parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching grand parents
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the grand parent in the entity cache if it is enabled.
	 *
	 * @param grandParent the grand parent
	 */
	public static void cacheResult(GrandParent grandParent) {
		getPersistence().cacheResult(grandParent);
	}

	/**
	 * Caches the grand parents in the entity cache if it is enabled.
	 *
	 * @param grandParents the grand parents
	 */
	public static void cacheResult(List<GrandParent> grandParents) {
		getPersistence().cacheResult(grandParents);
	}

	/**
	 * Creates a new grand parent with the primary key. Does not add the grand parent to the database.
	 *
	 * @param grandParentId the primary key for the new grand parent
	 * @return the new grand parent
	 */
	public static GrandParent create(long grandParentId) {
		return getPersistence().create(grandParentId);
	}

	/**
	 * Removes the grand parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent that was removed
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public static GrandParent remove(long grandParentId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchGrandParentException {

		return getPersistence().remove(grandParentId);
	}

	public static GrandParent updateImpl(GrandParent grandParent) {
		return getPersistence().updateImpl(grandParent);
	}

	/**
	 * Returns the grand parent with the primary key or throws a <code>NoSuchGrandParentException</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	public static GrandParent findByPrimaryKey(long grandParentId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchGrandParentException {

		return getPersistence().findByPrimaryKey(grandParentId);
	}

	/**
	 * Returns the grand parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent, or <code>null</code> if a grand parent with the primary key could not be found
	 */
	public static GrandParent fetchByPrimaryKey(long grandParentId) {
		return getPersistence().fetchByPrimaryKey(grandParentId);
	}

	/**
	 * Returns all the grand parents.
	 *
	 * @return the grand parents
	 */
	public static List<GrandParent> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of grand parents
	 */
	public static List<GrandParent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of grand parents
	 */
	public static List<GrandParent> findAll(
		int start, int end, OrderByComparator<GrandParent> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of grand parents
	 */
	public static List<GrandParent> findAll(
		int start, int end, OrderByComparator<GrandParent> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the grand parents from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of grand parents.
	 *
	 * @return the number of grand parents
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static GrandParentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(GrandParentPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile GrandParentPersistence _persistence;

}