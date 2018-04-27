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

import com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException;
import com.liferay.commerce.model.CPDefinitionInventory;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition inventory service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CPDefinitionInventoryPersistenceImpl
 * @see CPDefinitionInventoryUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionInventoryPersistence extends BasePersistence<CPDefinitionInventory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionInventoryUtil} to access the cp definition inventory persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition inventories
	*/
	public java.util.List<CPDefinitionInventory> findByUuid(String uuid);

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
	public java.util.List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end);

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
	public java.util.List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

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
	public java.util.List<CPDefinitionInventory> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

	/**
	* Returns the cp definition inventories before and after the current cp definition inventory in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionInventoryId the primary key of the current cp definition inventory
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public CPDefinitionInventory[] findByUuid_PrevAndNext(
		long CPDefinitionInventoryId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Removes all the cp definition inventories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp definition inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition inventories
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition inventory where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition inventory that was removed
	*/
	public CPDefinitionInventory removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the number of cp definition inventories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition inventories
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition inventories
	*/
	public java.util.List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId);

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
	public java.util.List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end);

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
	public java.util.List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

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
	public java.util.List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

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
	public CPDefinitionInventory[] findByUuid_C_PrevAndNext(
		long CPDefinitionInventoryId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Removes all the cp definition inventories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp definition inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition inventories
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public CPDefinitionInventory fetchByCPDefinitionId(long CPDefinitionId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition inventory where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition inventory that was removed
	*/
	public CPDefinitionInventory removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the number of cp definition inventories where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition inventories
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Caches the cp definition inventory in the entity cache if it is enabled.
	*
	* @param cpDefinitionInventory the cp definition inventory
	*/
	public void cacheResult(CPDefinitionInventory cpDefinitionInventory);

	/**
	* Caches the cp definition inventories in the entity cache if it is enabled.
	*
	* @param cpDefinitionInventories the cp definition inventories
	*/
	public void cacheResult(
		java.util.List<CPDefinitionInventory> cpDefinitionInventories);

	/**
	* Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	*
	* @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	* @return the new cp definition inventory
	*/
	public CPDefinitionInventory create(long CPDefinitionInventoryId);

	/**
	* Removes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory that was removed
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public CPDefinitionInventory remove(long CPDefinitionInventoryId)
		throws NoSuchCPDefinitionInventoryException;

	public CPDefinitionInventory updateImpl(
		CPDefinitionInventory cpDefinitionInventory);

	/**
	* Returns the cp definition inventory with the primary key or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory
	* @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	*/
	public CPDefinitionInventory findByPrimaryKey(long CPDefinitionInventoryId)
		throws NoSuchCPDefinitionInventoryException;

	/**
	* Returns the cp definition inventory with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory, or <code>null</code> if a cp definition inventory with the primary key could not be found
	*/
	public CPDefinitionInventory fetchByPrimaryKey(long CPDefinitionInventoryId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionInventory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition inventories.
	*
	* @return the cp definition inventories
	*/
	public java.util.List<CPDefinitionInventory> findAll();

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
	public java.util.List<CPDefinitionInventory> findAll(int start, int end);

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
	public java.util.List<CPDefinitionInventory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator);

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
	public java.util.List<CPDefinitionInventory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition inventories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition inventories.
	*
	* @return the number of cp definition inventories
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}