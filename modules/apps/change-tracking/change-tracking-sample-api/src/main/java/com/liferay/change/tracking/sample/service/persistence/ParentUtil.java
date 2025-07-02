/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the parent service. This utility wraps <code>com.liferay.change.tracking.sample.service.persistence.impl.ParentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParentPersistence
 * @generated
 */
public class ParentUtil {

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
	public static void clearCache(Parent parent) {
		getPersistence().clearCache(parent);
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
	public static Map<Serializable, Parent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Parent> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Parent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Parent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Parent> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Parent update(Parent parent) {
		return getPersistence().update(parent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Parent update(Parent parent, ServiceContext serviceContext) {
		return getPersistence().update(parent, serviceContext);
	}

	/**
	 * Returns all the parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching parents
	 */
	public static List<Parent> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	public static List<Parent> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	public static List<Parent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Parent> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	public static List<Parent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Parent> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public static Parent findByCompanyId_First(
			long companyId, OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public static Parent fetchByCompanyId_First(
		long companyId, OrderByComparator<Parent> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public static Parent findByCompanyId_Last(
			long companyId, OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public static Parent fetchByCompanyId_Last(
		long companyId, OrderByComparator<Parent> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public static Parent[] findByCompanyId_PrevAndNext(
			long parentId, long companyId,
			OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByCompanyId_PrevAndNext(
			parentId, companyId, orderByComparator);
	}

	/**
	 * Removes all the parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching parents
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching parents
	 */
	public static List<Parent> findByC_G(long companyId, long grandParentId) {
		return getPersistence().findByC_G(companyId, grandParentId);
	}

	/**
	 * Returns a range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of matching parents
	 */
	public static List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end) {

		return getPersistence().findByC_G(companyId, grandParentId, start, end);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching parents
	 */
	public static List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Parent> orderByComparator) {

		return getPersistence().findByC_G(
			companyId, grandParentId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching parents
	 */
	public static List<Parent> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Parent> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_G(
			companyId, grandParentId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public static Parent findByC_G_First(
			long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByC_G_First(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the first parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public static Parent fetchByC_G_First(
		long companyId, long grandParentId,
		OrderByComparator<Parent> orderByComparator) {

		return getPersistence().fetchByC_G_First(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent
	 * @throws NoSuchParentException if a matching parent could not be found
	 */
	public static Parent findByC_G_Last(
			long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByC_G_Last(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the last parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching parent, or <code>null</code> if a matching parent could not be found
	 */
	public static Parent fetchByC_G_Last(
		long companyId, long grandParentId,
		OrderByComparator<Parent> orderByComparator) {

		return getPersistence().fetchByC_G_Last(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the parents before and after the current parent in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param parentId the primary key of the current parent
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public static Parent[] findByC_G_PrevAndNext(
			long parentId, long companyId, long grandParentId,
			OrderByComparator<Parent> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByC_G_PrevAndNext(
			parentId, companyId, grandParentId, orderByComparator);
	}

	/**
	 * Removes all the parents where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	public static void removeByC_G(long companyId, long grandParentId) {
		getPersistence().removeByC_G(companyId, grandParentId);
	}

	/**
	 * Returns the number of parents where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching parents
	 */
	public static int countByC_G(long companyId, long grandParentId) {
		return getPersistence().countByC_G(companyId, grandParentId);
	}

	/**
	 * Caches the parent in the entity cache if it is enabled.
	 *
	 * @param parent the parent
	 */
	public static void cacheResult(Parent parent) {
		getPersistence().cacheResult(parent);
	}

	/**
	 * Caches the parents in the entity cache if it is enabled.
	 *
	 * @param parents the parents
	 */
	public static void cacheResult(List<Parent> parents) {
		getPersistence().cacheResult(parents);
	}

	/**
	 * Creates a new parent with the primary key. Does not add the parent to the database.
	 *
	 * @param parentId the primary key for the new parent
	 * @return the new parent
	 */
	public static Parent create(long parentId) {
		return getPersistence().create(parentId);
	}

	/**
	 * Removes the parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent that was removed
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public static Parent remove(long parentId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().remove(parentId);
	}

	public static Parent updateImpl(Parent parent) {
		return getPersistence().updateImpl(parent);
	}

	/**
	 * Returns the parent with the primary key or throws a <code>NoSuchParentException</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent
	 * @throws NoSuchParentException if a parent with the primary key could not be found
	 */
	public static Parent findByPrimaryKey(long parentId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchParentException {

		return getPersistence().findByPrimaryKey(parentId);
	}

	/**
	 * Returns the parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param parentId the primary key of the parent
	 * @return the parent, or <code>null</code> if a parent with the primary key could not be found
	 */
	public static Parent fetchByPrimaryKey(long parentId) {
		return getPersistence().fetchByPrimaryKey(parentId);
	}

	/**
	 * Returns all the parents.
	 *
	 * @return the parents
	 */
	public static List<Parent> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @return the range of parents
	 */
	public static List<Parent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of parents
	 */
	public static List<Parent> findAll(
		int start, int end, OrderByComparator<Parent> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of parents
	 * @param end the upper bound of the range of parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of parents
	 */
	public static List<Parent> findAll(
		int start, int end, OrderByComparator<Parent> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the parents from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of parents.
	 *
	 * @return the number of parents
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ParentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ParentPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ParentPersistence _persistence;

}