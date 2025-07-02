/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence;

import com.liferay.change.tracking.sample.model.Child;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the child service. This utility wraps <code>com.liferay.change.tracking.sample.service.persistence.impl.ChildPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChildPersistence
 * @generated
 */
public class ChildUtil {

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
	public static void clearCache(Child child) {
		getPersistence().clearCache(child);
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
	public static Map<Serializable, Child> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Child> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Child> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Child> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Child update(Child child) {
		return getPersistence().update(child);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Child update(Child child, ServiceContext serviceContext) {
		return getPersistence().update(child, serviceContext);
	}

	/**
	 * Returns all the childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching childs
	 */
	public static List<Child> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public static List<Child> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByCompanyId_First(
			long companyId, OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByCompanyId_First(
		long companyId, OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByCompanyId_Last(
			long companyId, OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByCompanyId_Last(
		long companyId, OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public static Child[] findByCompanyId_PrevAndNext(
			long childId, long companyId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByCompanyId_PrevAndNext(
			childId, companyId, orderByComparator);
	}

	/**
	 * Removes all the childs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of childs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching childs
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the matching childs
	 */
	public static List<Child> findByC_G(long companyId, long grandParentId) {
		return getPersistence().findByC_G(companyId, grandParentId);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public static List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end) {

		return getPersistence().findByC_G(companyId, grandParentId, start, end);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().findByC_G(
			companyId, grandParentId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByC_G(
		long companyId, long grandParentId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_G(
			companyId, grandParentId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByC_G_First(
			long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_G_First(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByC_G_First(
		long companyId, long grandParentId,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByC_G_First(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByC_G_Last(
			long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_G_Last(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByC_G_Last(
		long companyId, long grandParentId,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByC_G_Last(
			companyId, grandParentId, orderByComparator);
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public static Child[] findByC_G_PrevAndNext(
			long childId, long companyId, long grandParentId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_G_PrevAndNext(
			childId, companyId, grandParentId, orderByComparator);
	}

	/**
	 * Removes all the childs where companyId = &#63; and grandParentId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 */
	public static void removeByC_G(long companyId, long grandParentId) {
		getPersistence().removeByC_G(companyId, grandParentId);
	}

	/**
	 * Returns the number of childs where companyId = &#63; and grandParentId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param grandParentId the grand parent ID
	 * @return the number of matching childs
	 */
	public static int countByC_G(long companyId, long grandParentId) {
		return getPersistence().countByC_G(companyId, grandParentId);
	}

	/**
	 * Returns all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the matching childs
	 */
	public static List<Child> findByC_P(long companyId, long parentChildId) {
		return getPersistence().findByC_P(companyId, parentChildId);
	}

	/**
	 * Returns a range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of matching childs
	 */
	public static List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end) {

		return getPersistence().findByC_P(companyId, parentChildId, start, end);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().findByC_P(
			companyId, parentChildId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching childs
	 */
	public static List<Child> findByC_P(
		long companyId, long parentChildId, int start, int end,
		OrderByComparator<Child> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_P(
			companyId, parentChildId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByC_P_First(
			long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_P_First(
			companyId, parentChildId, orderByComparator);
	}

	/**
	 * Returns the first child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByC_P_First(
		long companyId, long parentChildId,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByC_P_First(
			companyId, parentChildId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child
	 * @throws NoSuchChildException if a matching child could not be found
	 */
	public static Child findByC_P_Last(
			long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_P_Last(
			companyId, parentChildId, orderByComparator);
	}

	/**
	 * Returns the last child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching child, or <code>null</code> if a matching child could not be found
	 */
	public static Child fetchByC_P_Last(
		long companyId, long parentChildId,
		OrderByComparator<Child> orderByComparator) {

		return getPersistence().fetchByC_P_Last(
			companyId, parentChildId, orderByComparator);
	}

	/**
	 * Returns the childs before and after the current child in the ordered set where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param childId the primary key of the current child
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public static Child[] findByC_P_PrevAndNext(
			long childId, long companyId, long parentChildId,
			OrderByComparator<Child> orderByComparator)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByC_P_PrevAndNext(
			childId, companyId, parentChildId, orderByComparator);
	}

	/**
	 * Removes all the childs where companyId = &#63; and parentChildId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 */
	public static void removeByC_P(long companyId, long parentChildId) {
		getPersistence().removeByC_P(companyId, parentChildId);
	}

	/**
	 * Returns the number of childs where companyId = &#63; and parentChildId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentChildId the parent child ID
	 * @return the number of matching childs
	 */
	public static int countByC_P(long companyId, long parentChildId) {
		return getPersistence().countByC_P(companyId, parentChildId);
	}

	/**
	 * Caches the child in the entity cache if it is enabled.
	 *
	 * @param child the child
	 */
	public static void cacheResult(Child child) {
		getPersistence().cacheResult(child);
	}

	/**
	 * Caches the childs in the entity cache if it is enabled.
	 *
	 * @param childs the childs
	 */
	public static void cacheResult(List<Child> childs) {
		getPersistence().cacheResult(childs);
	}

	/**
	 * Creates a new child with the primary key. Does not add the child to the database.
	 *
	 * @param childId the primary key for the new child
	 * @return the new child
	 */
	public static Child create(long childId) {
		return getPersistence().create(childId);
	}

	/**
	 * Removes the child with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param childId the primary key of the child
	 * @return the child that was removed
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public static Child remove(long childId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().remove(childId);
	}

	public static Child updateImpl(Child child) {
		return getPersistence().updateImpl(child);
	}

	/**
	 * Returns the child with the primary key or throws a <code>NoSuchChildException</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child
	 * @throws NoSuchChildException if a child with the primary key could not be found
	 */
	public static Child findByPrimaryKey(long childId)
		throws com.liferay.change.tracking.sample.exception.
			NoSuchChildException {

		return getPersistence().findByPrimaryKey(childId);
	}

	/**
	 * Returns the child with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param childId the primary key of the child
	 * @return the child, or <code>null</code> if a child with the primary key could not be found
	 */
	public static Child fetchByPrimaryKey(long childId) {
		return getPersistence().fetchByPrimaryKey(childId);
	}

	/**
	 * Returns all the childs.
	 *
	 * @return the childs
	 */
	public static List<Child> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @return the range of childs
	 */
	public static List<Child> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of childs
	 */
	public static List<Child> findAll(
		int start, int end, OrderByComparator<Child> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the childs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChildModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of childs
	 * @param end the upper bound of the range of childs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of childs
	 */
	public static List<Child> findAll(
		int start, int end, OrderByComparator<Child> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the childs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of childs.
	 *
	 * @return the number of childs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ChildPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ChildPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ChildPersistence _persistence;

}