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

import com.liferay.commerce.exception.NoSuchShipmentItemException;
import com.liferay.commerce.model.CommerceShipmentItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce shipment item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceShipmentItemPersistenceImpl
 * @see CommerceShipmentItemUtil
 * @generated
 */
@ProviderType
public interface CommerceShipmentItemPersistence extends BasePersistence<CommerceShipmentItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShipmentItemUtil} to access the commerce shipment item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce shipment items where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce shipment items where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param commerceShipmentItemId the primary key of the current commerce shipment item
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipment item
	* @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	*/
	public CommerceShipmentItem[] findByGroupId_PrevAndNext(
		long commerceShipmentItemId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Removes all the commerce shipment items where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce shipment items where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce shipment items
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce shipment items where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @return the matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByCommerceShipment(
		long commerceShipmentId);

	/**
	* Returns a range of all the commerce shipment items where commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByCommerceShipment(
		long commerceShipmentId, int start, int end);

	/**
	* Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByCommerceShipment(
		long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findByCommerceShipment(
		long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem findByCommerceShipment_First(
		long commerceShipmentId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem fetchByCommerceShipment_First(
		long commerceShipmentId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem findByCommerceShipment_Last(
		long commerceShipmentId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public CommerceShipmentItem fetchByCommerceShipment_Last(
		long commerceShipmentId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentItemId the primary key of the current commerce shipment item
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipment item
	* @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	*/
	public CommerceShipmentItem[] findByCommerceShipment_PrevAndNext(
		long commerceShipmentItemId, long commerceShipmentId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException;

	/**
	* Removes all the commerce shipment items where commerceShipmentId = &#63; from the database.
	*
	* @param commerceShipmentId the commerce shipment ID
	*/
	public void removeByCommerceShipment(long commerceShipmentId);

	/**
	* Returns the number of commerce shipment items where commerceShipmentId = &#63;.
	*
	* @param commerceShipmentId the commerce shipment ID
	* @return the number of matching commerce shipment items
	*/
	public int countByCommerceShipment(long commerceShipmentId);

	/**
	* Caches the commerce shipment item in the entity cache if it is enabled.
	*
	* @param commerceShipmentItem the commerce shipment item
	*/
	public void cacheResult(CommerceShipmentItem commerceShipmentItem);

	/**
	* Caches the commerce shipment items in the entity cache if it is enabled.
	*
	* @param commerceShipmentItems the commerce shipment items
	*/
	public void cacheResult(
		java.util.List<CommerceShipmentItem> commerceShipmentItems);

	/**
	* Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	*
	* @param commerceShipmentItemId the primary key for the new commerce shipment item
	* @return the new commerce shipment item
	*/
	public CommerceShipmentItem create(long commerceShipmentItemId);

	/**
	* Removes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item that was removed
	* @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	*/
	public CommerceShipmentItem remove(long commerceShipmentItemId)
		throws NoSuchShipmentItemException;

	public CommerceShipmentItem updateImpl(
		CommerceShipmentItem commerceShipmentItem);

	/**
	* Returns the commerce shipment item with the primary key or throws a {@link NoSuchShipmentItemException} if it could not be found.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item
	* @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	*/
	public CommerceShipmentItem findByPrimaryKey(long commerceShipmentItemId)
		throws NoSuchShipmentItemException;

	/**
	* Returns the commerce shipment item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item, or <code>null</code> if a commerce shipment item with the primary key could not be found
	*/
	public CommerceShipmentItem fetchByPrimaryKey(long commerceShipmentItemId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceShipmentItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce shipment items.
	*
	* @return the commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findAll();

	/**
	* Returns a range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipment items
	*/
	public java.util.List<CommerceShipmentItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce shipment items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce shipment items.
	*
	* @return the number of commerce shipment items
	*/
	public int countAll();
}