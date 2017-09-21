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

import com.liferay.commerce.exception.NoSuchInventoryException;
import com.liferay.commerce.model.CommerceInventory;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce inventory service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceInventoryPersistenceImpl
 * @see CommerceInventoryUtil
 * @generated
 */
@ProviderType
public interface CommerceInventoryPersistence extends BasePersistence<CommerceInventory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceInventoryUtil} to access the commerce inventory persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce inventories
	*/
	public java.util.List<CommerceInventory> findByUuid(java.lang.String uuid);

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
	public java.util.List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end);

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
	public java.util.List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

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
	public java.util.List<CommerceInventory> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

	/**
	* Returns the commerce inventories before and after the current commerce inventory in the ordered set where uuid = &#63;.
	*
	* @param commerceInventoryId the primary key of the current commerce inventory
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce inventory
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public CommerceInventory[] findByUuid_PrevAndNext(
		long commerceInventoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Removes all the commerce inventories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce inventories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce inventories
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchInventoryException;

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce inventory where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce inventory that was removed
	*/
	public CommerceInventory removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchInventoryException;

	/**
	* Returns the number of commerce inventories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce inventories
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce inventories
	*/
	public java.util.List<CommerceInventory> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<CommerceInventory> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceInventory> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

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
	public java.util.List<CommerceInventory> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Returns the first commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Returns the last commerce inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

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
	public CommerceInventory[] findByUuid_C_PrevAndNext(
		long commerceInventoryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator)
		throws NoSuchInventoryException;

	/**
	* Removes all the commerce inventories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce inventories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce inventories
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce inventory
	* @throws NoSuchInventoryException if a matching commerce inventory could not be found
	*/
	public CommerceInventory findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchInventoryException;

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the commerce inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public CommerceInventory fetchByCPDefinitionId(long CPDefinitionId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce inventory where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the commerce inventory that was removed
	*/
	public CommerceInventory removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchInventoryException;

	/**
	* Returns the number of commerce inventories where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching commerce inventories
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Caches the commerce inventory in the entity cache if it is enabled.
	*
	* @param commerceInventory the commerce inventory
	*/
	public void cacheResult(CommerceInventory commerceInventory);

	/**
	* Caches the commerce inventories in the entity cache if it is enabled.
	*
	* @param commerceInventories the commerce inventories
	*/
	public void cacheResult(
		java.util.List<CommerceInventory> commerceInventories);

	/**
	* Creates a new commerce inventory with the primary key. Does not add the commerce inventory to the database.
	*
	* @param commerceInventoryId the primary key for the new commerce inventory
	* @return the new commerce inventory
	*/
	public CommerceInventory create(long commerceInventoryId);

	/**
	* Removes the commerce inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory that was removed
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public CommerceInventory remove(long commerceInventoryId)
		throws NoSuchInventoryException;

	public CommerceInventory updateImpl(CommerceInventory commerceInventory);

	/**
	* Returns the commerce inventory with the primary key or throws a {@link NoSuchInventoryException} if it could not be found.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory
	* @throws NoSuchInventoryException if a commerce inventory with the primary key could not be found
	*/
	public CommerceInventory findByPrimaryKey(long commerceInventoryId)
		throws NoSuchInventoryException;

	/**
	* Returns the commerce inventory with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory, or <code>null</code> if a commerce inventory with the primary key could not be found
	*/
	public CommerceInventory fetchByPrimaryKey(long commerceInventoryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceInventory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce inventories.
	*
	* @return the commerce inventories
	*/
	public java.util.List<CommerceInventory> findAll();

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
	public java.util.List<CommerceInventory> findAll(int start, int end);

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
	public java.util.List<CommerceInventory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator);

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
	public java.util.List<CommerceInventory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceInventory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce inventories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce inventories.
	*
	* @return the number of commerce inventories
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}