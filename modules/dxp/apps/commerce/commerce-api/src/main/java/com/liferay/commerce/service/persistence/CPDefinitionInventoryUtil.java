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

import com.liferay.commerce.model.CPDefinitionInventory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition inventory service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CPDefinitionInventoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CPDefinitionInventoryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryUtil {
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
	public static void clearCache(CPDefinitionInventory cpDefinitionInventory) {
		getPersistence().clearCache(cpDefinitionInventory);
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
	public static List<CPDefinitionInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionInventory update(
		CPDefinitionInventory cpDefinitionInventory) {
		return getPersistence().update(cpDefinitionInventory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionInventory update(
		CPDefinitionInventory cpDefinitionInventory,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionInventory, serviceContext);
	}

	/**
	* Returns all the cp definition inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @return the range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition inventories before and after the current cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionInventoryId the primary key of the current cp definition inventory
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public static CPDefinitionInventory[] findByUuid_PrevAndNext(
		long CPDefinitionInventoryId, String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionInventoryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition inventories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition inventories
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition inventory where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition inventory that was removed
	*/
	public static CPDefinitionInventory removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition inventories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition inventories
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @return the range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition inventories
	*/
	public static List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition inventories before and after the current cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionInventoryId the primary key of the current cp definition inventory
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public static CPDefinitionInventory[] findByUuid_C_PrevAndNext(
		long CPDefinitionInventoryId, String uuid, long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionInventoryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition inventories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition inventories
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory findByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static CPDefinitionInventory fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId(CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the cp definition inventory where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition inventory that was removed
	*/
	public static CPDefinitionInventory removeByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition inventories where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition inventories
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Caches the cp definition inventory in the entity cache if it is enabled.
	*
	* @param cpDefinitionInventory the cp definition inventory
	*/
	public static void cacheResult(CPDefinitionInventory cpDefinitionInventory) {
		getPersistence().cacheResult(cpDefinitionInventory);
	}

	/**
	* Caches the cp definition inventories in the entity cache if it is enabled.
	*
	* @param cpDefinitionInventories the cp definition inventories
	*/
	public static void cacheResult(
		List<CPDefinitionInventory> cpDefinitionInventories) {
		getPersistence().cacheResult(cpDefinitionInventories);
	}

	/**
	* Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	*
	* @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	* @return the new cp definition inventory
	*/
	public static CPDefinitionInventory create(long CPDefinitionInventoryId) {
		return getPersistence().create(CPDefinitionInventoryId);
	}

	/**
	* Removes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory that was removed
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public static CPDefinitionInventory remove(long CPDefinitionInventoryId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().remove(CPDefinitionInventoryId);
	}

	public static CPDefinitionInventory updateImpl(
		CPDefinitionInventory cpDefinitionInventory) {
		return getPersistence().updateImpl(cpDefinitionInventory);
	}

	/**
	* Returns the cp definition inventory with the primary key or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public static CPDefinitionInventory findByPrimaryKey(
		long CPDefinitionInventoryId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException {
		return getPersistence().findByPrimaryKey(CPDefinitionInventoryId);
	}

	/**
	* Returns the cp definition inventory with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory, or <code>null</code> if a cp definition inventory with the primary key could not be found
	*/
	public static CPDefinitionInventory fetchByPrimaryKey(
		long CPDefinitionInventoryId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionInventoryId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionInventory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition inventories.
	*
	* @return the cp definition inventories
	*/
	public static List<CPDefinitionInventory> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @return the range of cp definition inventories
	*/
	public static List<CPDefinitionInventory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition inventories
	*/
	public static List<CPDefinitionInventory> findAll(int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition inventories
	*/
	public static List<CPDefinitionInventory> findAll(int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition inventories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition inventories.
	*
	* @return the number of cp definition inventories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionInventoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionInventoryPersistence, CPDefinitionInventoryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionInventoryPersistence.class);

		ServiceTracker<CPDefinitionInventoryPersistence, CPDefinitionInventoryPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionInventoryPersistence, CPDefinitionInventoryPersistence>(bundle.getBundleContext(),
				CPDefinitionInventoryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}