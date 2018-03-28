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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceWarehouse;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce warehouse service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceWarehousePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehousePersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceWarehousePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseUtil {
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
	public static void clearCache(CommerceWarehouse commerceWarehouse) {
		getPersistence().clearCache(commerceWarehouse);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceWarehouse> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceWarehouse update(CommerceWarehouse commerceWarehouse) {
		return getPersistence().update(commerceWarehouse);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceWarehouse update(
		CommerceWarehouse commerceWarehouse, ServiceContext serviceContext) {
		return getPersistence().update(commerceWarehouse, serviceContext);
	}

	/**
	* Returns all the commerce warehouses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce warehouses where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByGroupId_First(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByGroupId_Last(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param commerceWarehouseId the primary key of the current commerce warehouse
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse[] findByGroupId_PrevAndNext(
		long commerceWarehouseId, long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceWarehouseId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce warehouses where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce warehouses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce warehouses
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_A_Last(long groupId,
		boolean active, OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_A_Last(long groupId,
		boolean active, OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceWarehouseId the primary key of the current commerce warehouse
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse[] findByG_A_PrevAndNext(
		long commerceWarehouseId, long groupId, boolean active,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commerceWarehouseId, groupId, active,
			orderByComparator);
	}

	/**
	* Removes all the commerce warehouses where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce warehouses
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId) {
		return getPersistence().findByG_C(groupId, commerceCountryId);
	}

	/**
	* Returns a range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end) {
		return getPersistence().findByG_C(groupId, commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_C_First(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_C_First(groupId, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_C_First(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_C_Last(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_C_Last(groupId, commerceCountryId, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_C_Last(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param commerceWarehouseId the primary key of the current commerce warehouse
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse[] findByG_C_PrevAndNext(
		long commerceWarehouseId, long groupId, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_C_PrevAndNext(commerceWarehouseId, groupId,
			commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByG_C(long groupId, long commerceCountryId) {
		getPersistence().removeByG_C(groupId, commerceCountryId);
	}

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce warehouses
	*/
	public static int countByG_C(long groupId, long commerceCountryId) {
		return getPersistence().countByG_C(groupId, commerceCountryId);
	}

	/**
	* Returns all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId) {
		return getPersistence().findByG_A_C(groupId, active, commerceCountryId);
	}

	/**
	* Returns a range of all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end) {
		return getPersistence()
				   .findByG_A_C(groupId, active, commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .findByG_A_C(groupId, active, commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouses
	*/
	public static List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A_C(groupId, active, commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_A_C_First(long groupId,
		boolean active, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_C_First(groupId, active, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_A_C_First(long groupId,
		boolean active, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_C_First(groupId, active, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse findByG_A_C_Last(long groupId,
		boolean active, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_C_Last(groupId, active, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public static CommerceWarehouse fetchByG_A_C_Last(long groupId,
		boolean active, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_C_Last(groupId, active, commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param commerceWarehouseId the primary key of the current commerce warehouse
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse[] findByG_A_C_PrevAndNext(
		long commerceWarehouseId, long groupId, boolean active,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence()
				   .findByG_A_C_PrevAndNext(commerceWarehouseId, groupId,
			active, commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByG_A_C(long groupId, boolean active,
		long commerceCountryId) {
		getPersistence().removeByG_A_C(groupId, active, commerceCountryId);
	}

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce warehouses
	*/
	public static int countByG_A_C(long groupId, boolean active,
		long commerceCountryId) {
		return getPersistence().countByG_A_C(groupId, active, commerceCountryId);
	}

	/**
	* Caches the commerce warehouse in the entity cache if it is enabled.
	*
	* @param commerceWarehouse the commerce warehouse
	*/
	public static void cacheResult(CommerceWarehouse commerceWarehouse) {
		getPersistence().cacheResult(commerceWarehouse);
	}

	/**
	* Caches the commerce warehouses in the entity cache if it is enabled.
	*
	* @param commerceWarehouses the commerce warehouses
	*/
	public static void cacheResult(List<CommerceWarehouse> commerceWarehouses) {
		getPersistence().cacheResult(commerceWarehouses);
	}

	/**
	* Creates a new commerce warehouse with the primary key. Does not add the commerce warehouse to the database.
	*
	* @param commerceWarehouseId the primary key for the new commerce warehouse
	* @return the new commerce warehouse
	*/
	public static CommerceWarehouse create(long commerceWarehouseId) {
		return getPersistence().create(commerceWarehouseId);
	}

	/**
	* Removes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse that was removed
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse remove(long commerceWarehouseId)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence().remove(commerceWarehouseId);
	}

	public static CommerceWarehouse updateImpl(
		CommerceWarehouse commerceWarehouse) {
		return getPersistence().updateImpl(commerceWarehouse);
	}

	/**
	* Returns the commerce warehouse with the primary key or throws a {@link NoSuchWarehouseException} if it could not be found.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse findByPrimaryKey(long commerceWarehouseId)
		throws com.liferay.commerce.exception.NoSuchWarehouseException {
		return getPersistence().findByPrimaryKey(commerceWarehouseId);
	}

	/**
	* Returns the commerce warehouse with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse, or <code>null</code> if a commerce warehouse with the primary key could not be found
	*/
	public static CommerceWarehouse fetchByPrimaryKey(long commerceWarehouseId) {
		return getPersistence().fetchByPrimaryKey(commerceWarehouseId);
	}

	public static java.util.Map<java.io.Serializable, CommerceWarehouse> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce warehouses.
	*
	* @return the commerce warehouses
	*/
	public static List<CommerceWarehouse> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce warehouses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of commerce warehouses
	*/
	public static List<CommerceWarehouse> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce warehouses
	*/
	public static List<CommerceWarehouse> findAll(int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce warehouses
	*/
	public static List<CommerceWarehouse> findAll(int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce warehouses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce warehouses.
	*
	* @return the number of commerce warehouses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceWarehousePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehousePersistence, CommerceWarehousePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWarehousePersistence.class);

		ServiceTracker<CommerceWarehousePersistence, CommerceWarehousePersistence> serviceTracker =
			new ServiceTracker<CommerceWarehousePersistence, CommerceWarehousePersistence>(bundle.getBundleContext(),
				CommerceWarehousePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}