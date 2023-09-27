/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce inventory warehouse service. This utility wraps <code>com.liferay.commerce.inventory.service.persistence.impl.CommerceInventoryWarehousePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehousePersistence
 * @generated
 */
public class CommerceInventoryWarehouseUtil {

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
	public static void clearCache(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		getPersistence().clearCache(commerceInventoryWarehouse);
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
	public static Map<Serializable, CommerceInventoryWarehouse>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceInventoryWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceInventoryWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceInventoryWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceInventoryWarehouse update(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		return getPersistence().update(commerceInventoryWarehouse);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceInventoryWarehouse update(
		CommerceInventoryWarehouse commerceInventoryWarehouse,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceInventoryWarehouse, serviceContext);
	}

	/**
	 * Returns all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByUuid_First(
			String uuid,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByUuid_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceInventoryWarehouseId, uuid, orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid) {

		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] filterFindByUuid_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			commerceInventoryWarehouseId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce inventory warehouses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByUuid_C_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceInventoryWarehouseId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] filterFindByUuid_C_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			commerceInventoryWarehouseId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId) {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceInventoryWarehouseId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[]
			filterFindByCompanyId_PrevAndNext(
				long commerceInventoryWarehouseId, long companyId,
				OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceInventoryWarehouseId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceInventoryWarehouseId, companyId, active, orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active) {

		return getPersistence().filterFindByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().filterFindByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] filterFindByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByC_A_PrevAndNext(
			commerceInventoryWarehouseId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByC_A(long companyId, boolean active) {
		return getPersistence().filterCountByC_A(companyId, active);
	}

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return getPersistence().findByC_C(companyId, countryTwoLettersISOCode);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end) {

		return getPersistence().findByC_C(
			companyId, countryTwoLettersISOCode, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByC_C(
			companyId, countryTwoLettersISOCode, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			companyId, countryTwoLettersISOCode, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_C_First(
			long companyId, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_C_First(
			companyId, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_C_First(
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			companyId, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_C_Last(
			long companyId, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_C_Last(
			companyId, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_C_Last(
		long companyId, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			companyId, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceInventoryWarehouseId, companyId, countryTwoLettersISOCode,
			orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return getPersistence().filterFindByC_C(
			companyId, countryTwoLettersISOCode);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end) {

		return getPersistence().filterFindByC_C(
			companyId, countryTwoLettersISOCode, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByC_C(
			companyId, countryTwoLettersISOCode, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] filterFindByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByC_C_PrevAndNext(
			commerceInventoryWarehouseId, companyId, countryTwoLettersISOCode,
			orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	public static void removeByC_C(
		long companyId, String countryTwoLettersISOCode) {

		getPersistence().removeByC_C(companyId, countryTwoLettersISOCode);
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return getPersistence().countByC_C(companyId, countryTwoLettersISOCode);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByC_C(
		long companyId, String countryTwoLettersISOCode) {

		return getPersistence().filterCountByC_C(
			companyId, countryTwoLettersISOCode);
	}

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return getPersistence().findByC_A_C(
			companyId, active, countryTwoLettersISOCode);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end) {

		return getPersistence().findByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_A_C_First(
			long companyId, boolean active, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_C_First(
			companyId, active, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_A_C_First(
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_A_C_First(
			companyId, active, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByC_A_C_Last(
			long companyId, boolean active, String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_C_Last(
			companyId, active, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByC_A_C_Last(
		long companyId, boolean active, String countryTwoLettersISOCode,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().fetchByC_A_C_Last(
			companyId, active, countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] findByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByC_A_C_PrevAndNext(
			commerceInventoryWarehouseId, companyId, active,
			countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return getPersistence().filterFindByC_A_C(
			companyId, active, countryTwoLettersISOCode);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end) {

		return getPersistence().filterFindByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public static List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().filterFindByC_A_C(
			companyId, active, countryTwoLettersISOCode, start, end,
			orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse[] filterFindByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().filterFindByC_A_C_PrevAndNext(
			commerceInventoryWarehouseId, companyId, active,
			countryTwoLettersISOCode, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	public static void removeByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		getPersistence().removeByC_A_C(
			companyId, active, countryTwoLettersISOCode);
	}

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return getPersistence().countByC_A_C(
			companyId, active, countryTwoLettersISOCode);
	}

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public static int filterCountByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode) {

		return getPersistence().filterCountByC_A_C(
			companyId, active, countryTwoLettersISOCode);
	}

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce inventory warehouse that was removed
	 */
	public static CommerceInventoryWarehouse removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of commerce inventory warehouses where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the commerce inventory warehouse in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 */
	public static void cacheResult(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		getPersistence().cacheResult(commerceInventoryWarehouse);
	}

	/**
	 * Caches the commerce inventory warehouses in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouses the commerce inventory warehouses
	 */
	public static void cacheResult(
		List<CommerceInventoryWarehouse> commerceInventoryWarehouses) {

		getPersistence().cacheResult(commerceInventoryWarehouses);
	}

	/**
	 * Creates a new commerce inventory warehouse with the primary key. Does not add the commerce inventory warehouse to the database.
	 *
	 * @param commerceInventoryWarehouseId the primary key for the new commerce inventory warehouse
	 * @return the new commerce inventory warehouse
	 */
	public static CommerceInventoryWarehouse create(
		long commerceInventoryWarehouseId) {

		return getPersistence().create(commerceInventoryWarehouseId);
	}

	/**
	 * Removes the commerce inventory warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse remove(
			long commerceInventoryWarehouseId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().remove(commerceInventoryWarehouseId);
	}

	public static CommerceInventoryWarehouse updateImpl(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		return getPersistence().updateImpl(commerceInventoryWarehouse);
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse findByPrimaryKey(
			long commerceInventoryWarehouseId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseException {

		return getPersistence().findByPrimaryKey(commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse, or <code>null</code> if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse fetchByPrimaryKey(
		long commerceInventoryWarehouseId) {

		return getPersistence().fetchByPrimaryKey(commerceInventoryWarehouseId);
	}

	/**
	 * Returns all the commerce inventory warehouses.
	 *
	 * @return the commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce inventory warehouses from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce inventory warehouses.
	 *
	 * @return the number of commerce inventory warehouses
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceInventoryWarehousePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceInventoryWarehousePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceInventoryWarehousePersistence _persistence;

}