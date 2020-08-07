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

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce catalog service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CommerceCatalogPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCatalogPersistence
 * @generated
 */
public class CommerceCatalogUtil {

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
	public static void clearCache(CommerceCatalog commerceCatalog) {
		getPersistence().clearCache(commerceCatalog);
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
	public static Map<Serializable, CommerceCatalog> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceCatalog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCatalog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCatalog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCatalog update(CommerceCatalog commerceCatalog) {
		return getPersistence().update(commerceCatalog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCatalog update(
		CommerceCatalog commerceCatalog, ServiceContext serviceContext) {

		return getPersistence().update(commerceCatalog, serviceContext);
	}

	/**
	 * Returns all the commerce catalogs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog[] findByCompanyId_PrevAndNext(
			long commerceCatalogId, long companyId,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceCatalogId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog[] filterFindByCompanyId_PrevAndNext(
			long commerceCatalogId, long companyId,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceCatalogId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce catalogs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce catalogs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByC_S(
		long companyId, boolean system) {

		return getPersistence().findByC_S(companyId, system);
	}

	/**
	 * Returns a range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end) {

		return getPersistence().findByC_S(companyId, system, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().findByC_S(
			companyId, system, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public static List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			companyId, system, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog findByC_S_First(
			long companyId, boolean system,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByC_S_First(
			companyId, system, orderByComparator);
	}

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByC_S_First(
		long companyId, boolean system,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			companyId, system, orderByComparator);
	}

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog findByC_S_Last(
			long companyId, boolean system,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByC_S_Last(
			companyId, system, orderByComparator);
	}

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByC_S_Last(
		long companyId, boolean system,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			companyId, system, orderByComparator);
	}

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog[] findByC_S_PrevAndNext(
			long commerceCatalogId, long companyId, boolean system,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByC_S_PrevAndNext(
			commerceCatalogId, companyId, system, orderByComparator);
	}

	/**
	 * Returns all the commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system) {

		return getPersistence().filterFindByC_S(companyId, system);
	}

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system, int start, int end) {

		return getPersistence().filterFindByC_S(companyId, system, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public static List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system, int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().filterFindByC_S(
			companyId, system, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog[] filterFindByC_S_PrevAndNext(
			long commerceCatalogId, long companyId, boolean system,
			OrderByComparator<CommerceCatalog> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().filterFindByC_S_PrevAndNext(
			commerceCatalogId, companyId, system, orderByComparator);
	}

	/**
	 * Removes all the commerce catalogs where companyId = &#63; and system = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 */
	public static void removeByC_S(long companyId, boolean system) {
		getPersistence().removeByC_S(companyId, system);
	}

	/**
	 * Returns the number of commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the number of matching commerce catalogs
	 */
	public static int countByC_S(long companyId, boolean system) {
		return getPersistence().countByC_S(companyId, system);
	}

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public static int filterCountByC_S(long companyId, boolean system) {
		return getPersistence().filterCountByC_S(companyId, system);
	}

	/**
	 * Returns the commerce catalog where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCatalogException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce catalog where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce catalog where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public static CommerceCatalog fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce catalog where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce catalog that was removed
	 */
	public static CommerceCatalog removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce catalogs where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce catalogs
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce catalog in the entity cache if it is enabled.
	 *
	 * @param commerceCatalog the commerce catalog
	 */
	public static void cacheResult(CommerceCatalog commerceCatalog) {
		getPersistence().cacheResult(commerceCatalog);
	}

	/**
	 * Caches the commerce catalogs in the entity cache if it is enabled.
	 *
	 * @param commerceCatalogs the commerce catalogs
	 */
	public static void cacheResult(List<CommerceCatalog> commerceCatalogs) {
		getPersistence().cacheResult(commerceCatalogs);
	}

	/**
	 * Creates a new commerce catalog with the primary key. Does not add the commerce catalog to the database.
	 *
	 * @param commerceCatalogId the primary key for the new commerce catalog
	 * @return the new commerce catalog
	 */
	public static CommerceCatalog create(long commerceCatalogId) {
		return getPersistence().create(commerceCatalogId);
	}

	/**
	 * Removes the commerce catalog with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog that was removed
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog remove(long commerceCatalogId)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().remove(commerceCatalogId);
	}

	public static CommerceCatalog updateImpl(CommerceCatalog commerceCatalog) {
		return getPersistence().updateImpl(commerceCatalog);
	}

	/**
	 * Returns the commerce catalog with the primary key or throws a <code>NoSuchCatalogException</code> if it could not be found.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog findByPrimaryKey(long commerceCatalogId)
		throws com.liferay.commerce.product.exception.NoSuchCatalogException {

		return getPersistence().findByPrimaryKey(commerceCatalogId);
	}

	/**
	 * Returns the commerce catalog with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog, or <code>null</code> if a commerce catalog with the primary key could not be found
	 */
	public static CommerceCatalog fetchByPrimaryKey(long commerceCatalogId) {
		return getPersistence().fetchByPrimaryKey(commerceCatalogId);
	}

	/**
	 * Returns all the commerce catalogs.
	 *
	 * @return the commerce catalogs
	 */
	public static List<CommerceCatalog> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of commerce catalogs
	 */
	public static List<CommerceCatalog> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce catalogs
	 */
	public static List<CommerceCatalog> findAll(
		int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce catalogs
	 */
	public static List<CommerceCatalog> findAll(
		int start, int end,
		OrderByComparator<CommerceCatalog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce catalogs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce catalogs.
	 *
	 * @return the number of commerce catalogs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceCatalogPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceCatalogPersistence, CommerceCatalogPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceCatalogPersistence.class);

		ServiceTracker<CommerceCatalogPersistence, CommerceCatalogPersistence>
			serviceTracker =
				new ServiceTracker
					<CommerceCatalogPersistence, CommerceCatalogPersistence>(
						bundle.getBundleContext(),
						CommerceCatalogPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}