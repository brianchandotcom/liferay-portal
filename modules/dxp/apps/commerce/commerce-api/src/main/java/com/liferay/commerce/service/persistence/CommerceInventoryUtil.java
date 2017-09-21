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

import com.liferay.commerce.model.CommerceInventory;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce inventory service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceInventoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceInventoryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceInventoryUtil {
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
	public static void clearCache(CommerceInventory commerceInventory) {
		getPersistence().clearCache(commerceInventory);
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
	public static List<CommerceInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceInventory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceInventory update(CommerceInventory commerceInventory) {
		return getPersistence().update(commerceInventory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceInventory update(
		CommerceInventory commerceInventory, ServiceContext serviceContext) {
		return getPersistence().update(commerceInventory, serviceContext);
	}

	/**
	* Returns all the commerce inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @return the range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce inventories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce inventories before and after the current commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param commerceInventoryId the primary key of the current commerce inventory
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce inventory
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public static CommerceInventory[] findByUuid_PrevAndNext(
		long commerceInventoryId, java.lang.String uuid,
		OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceInventoryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce inventories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce inventories
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce inventory where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce inventory that was removed
	*/
	public static CommerceInventory removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce inventories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce inventories
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @return the range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce inventories
	*/
	public static List<CommerceInventory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce inventories before and after the current commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceInventoryId the primary key of the current commerce inventory
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce inventory
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public static CommerceInventory[] findByUuid_C_PrevAndNext(
		long commerceInventoryId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceInventory> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceInventoryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce inventories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce inventories
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public static CommerceInventory findByCPDefinitionId(long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static CommerceInventory fetchByCPDefinitionId(long CPDefinitionId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId(CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the commerce inventory where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the commerce inventory that was removed
	*/
	public static CommerceInventory removeByCPDefinitionId(long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of commerce inventories where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching commerce inventories
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Caches the commerce inventory in the entity cache if it is enabled.
	*
	* @param commerceInventory the commerce inventory
	*/
	public static void cacheResult(CommerceInventory commerceInventory) {
		getPersistence().cacheResult(commerceInventory);
	}

	/**
	* Caches the commerce inventories in the entity cache if it is enabled.
	*
	* @param commerceInventories the commerce inventories
	*/
	public static void cacheResult(List<CommerceInventory> commerceInventories) {
		getPersistence().cacheResult(commerceInventories);
	}

	/**
	* Creates a new commerce inventory with the primary key. Does not add the commerce inventory to the database.
	*
	* @param commerceInventoryId the primary key for the new commerce inventory
	* @return the new commerce inventory
	*/
	public static CommerceInventory create(long commerceInventoryId) {
		return getPersistence().create(commerceInventoryId);
	}

	/**
	* Removes the commerce inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory that was removed
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public static CommerceInventory remove(long commerceInventoryId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().remove(commerceInventoryId);
	}

	public static CommerceInventory updateImpl(
		CommerceInventory commerceInventory) {
		return getPersistence().updateImpl(commerceInventory);
	}

	/**
	* Returns the commerce inventory with the primary key or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public static CommerceInventory findByPrimaryKey(long commerceInventoryId)
		throws com.liferay.commerce.exception.NoSuchInventoryException {
		return getPersistence().findByPrimaryKey(commerceInventoryId);
	}

	/**
	* Returns the commerce inventory with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory, or <code>null</code> if a commerce inventory with the primary key could not be found
	*/
	public static CommerceInventory fetchByPrimaryKey(long commerceInventoryId) {
		return getPersistence().fetchByPrimaryKey(commerceInventoryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceInventory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce inventories.
	*
	* @return the commerce inventories
	*/
	public static List<CommerceInventory> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @return the range of commerce inventories
	*/
	public static List<CommerceInventory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce inventories
	*/
	public static List<CommerceInventory> findAll(int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce inventories
	*/
	public static List<CommerceInventory> findAll(int start, int end,
		OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce inventories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce inventories.
	*
	* @return the number of commerce inventories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceInventoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceInventoryPersistence, CommerceInventoryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceInventoryPersistence.class);
}