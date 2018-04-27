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

import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.model.CommerceWarehouse;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce warehouse service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceWarehousePersistenceImpl
 * @see CommerceWarehouseUtil
 * @generated
 */
@ProviderType
public interface CommerceWarehousePersistence extends BasePersistence<CommerceWarehouse> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWarehouseUtil} to access the commerce warehouse persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce warehouses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce warehouses
	*/
	public java.util.List<CommerceWarehouse> findByGroupId(long groupId);

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
	public java.util.List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public java.util.List<CommerceWarehouse> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

	/**
	* Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63;.
	*
	* @param commerceWarehouseId the primary key of the current commerce warehouse
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public CommerceWarehouse[] findByGroupId_PrevAndNext(
		long commerceWarehouseId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Removes all the commerce warehouses where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce warehouses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce warehouses
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce warehouses
	*/
	public java.util.List<CommerceWarehouse> findByG_A(long groupId,
		boolean active);

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
	public java.util.List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end);

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
	public java.util.List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public java.util.List<CommerceWarehouse> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public CommerceWarehouse[] findByG_A_PrevAndNext(long commerceWarehouseId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Removes all the commerce warehouses where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce warehouses
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Returns all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce warehouses
	*/
	public java.util.List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId);

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
	public java.util.List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end);

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
	public java.util.List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public java.util.List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByG_C_First(long groupId,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_C_First(long groupId,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse
	* @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse findByG_C_Last(long groupId,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_C_Last(long groupId,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public CommerceWarehouse[] findByG_C_PrevAndNext(long commerceWarehouseId,
		long groupId, long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Removes all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	*/
	public void removeByG_C(long groupId, long commerceCountryId);

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce warehouses
	*/
	public int countByG_C(long groupId, long commerceCountryId);

	/**
	* Returns all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce warehouses
	*/
	public java.util.List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId);

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
	public java.util.List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end);

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
	public java.util.List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public java.util.List<CommerceWarehouse> findByG_A_C(long groupId,
		boolean active, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache);

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
	public CommerceWarehouse findByG_A_C_First(long groupId, boolean active,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the first commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_A_C_First(long groupId, boolean active,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public CommerceWarehouse findByG_A_C_Last(long groupId, boolean active,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Returns the last commerce warehouse in the ordered set where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	*/
	public CommerceWarehouse fetchByG_A_C_Last(long groupId, boolean active,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public CommerceWarehouse[] findByG_A_C_PrevAndNext(
		long commerceWarehouseId, long groupId, boolean active,
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException;

	/**
	* Removes all the commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	*/
	public void removeByG_A_C(long groupId, boolean active,
		long commerceCountryId);

	/**
	* Returns the number of commerce warehouses where groupId = &#63; and active = &#63; and commerceCountryId = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce warehouses
	*/
	public int countByG_A_C(long groupId, boolean active, long commerceCountryId);

	/**
	* Caches the commerce warehouse in the entity cache if it is enabled.
	*
	* @param commerceWarehouse the commerce warehouse
	*/
	public void cacheResult(CommerceWarehouse commerceWarehouse);

	/**
	* Caches the commerce warehouses in the entity cache if it is enabled.
	*
	* @param commerceWarehouses the commerce warehouses
	*/
	public void cacheResult(
		java.util.List<CommerceWarehouse> commerceWarehouses);

	/**
	* Creates a new commerce warehouse with the primary key. Does not add the commerce warehouse to the database.
	*
	* @param commerceWarehouseId the primary key for the new commerce warehouse
	* @return the new commerce warehouse
	*/
	public CommerceWarehouse create(long commerceWarehouseId);

	/**
	* Removes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse that was removed
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public CommerceWarehouse remove(long commerceWarehouseId)
		throws NoSuchWarehouseException;

	public CommerceWarehouse updateImpl(CommerceWarehouse commerceWarehouse);

	/**
	* Returns the commerce warehouse with the primary key or throws a {@link NoSuchWarehouseException} if it could not be found.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse
	* @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	*/
	public CommerceWarehouse findByPrimaryKey(long commerceWarehouseId)
		throws NoSuchWarehouseException;

	/**
	* Returns the commerce warehouse with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse, or <code>null</code> if a commerce warehouse with the primary key could not be found
	*/
	public CommerceWarehouse fetchByPrimaryKey(long commerceWarehouseId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceWarehouse> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce warehouses.
	*
	* @return the commerce warehouses
	*/
	public java.util.List<CommerceWarehouse> findAll();

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
	public java.util.List<CommerceWarehouse> findAll(int start, int end);

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
	public java.util.List<CommerceWarehouse> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator);

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
	public java.util.List<CommerceWarehouse> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce warehouses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce warehouses.
	*
	* @return the number of commerce warehouses
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}