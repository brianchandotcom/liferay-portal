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

import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce shipment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceShipmentPersistenceImpl
 * @see CommerceShipmentUtil
 * @generated
 */
@ProviderType
public interface CommerceShipmentPersistence extends BasePersistence<CommerceShipment> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShipmentUtil} to access the commerce shipment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce shipments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce shipments
	*/
	public java.util.List<CommerceShipment> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce shipments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @return the range of matching commerce shipments
	*/
	public java.util.List<CommerceShipment> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce shipments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipments
	*/
	public java.util.List<CommerceShipment> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipments
	*/
	public java.util.List<CommerceShipment> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce shipment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment
	* @throws NoSuchShipmentException if a matching commerce shipment could not be found
	*/
	public CommerceShipment findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException;

	/**
	* Returns the first commerce shipment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	*/
	public CommerceShipment fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator);

	/**
	* Returns the last commerce shipment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment
	* @throws NoSuchShipmentException if a matching commerce shipment could not be found
	*/
	public CommerceShipment findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException;

	/**
	* Returns the last commerce shipment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	*/
	public CommerceShipment fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator);

	/**
	* Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63;.
	*
	* @param commerceShipmentId the primary key of the current commerce shipment
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipment
	* @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	*/
	public CommerceShipment[] findByGroupId_PrevAndNext(
		long commerceShipmentId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException;

	/**
	* Removes all the commerce shipments where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce shipments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce shipments
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the commerce shipment in the entity cache if it is enabled.
	*
	* @param commerceShipment the commerce shipment
	*/
	public void cacheResult(CommerceShipment commerceShipment);

	/**
	* Caches the commerce shipments in the entity cache if it is enabled.
	*
	* @param commerceShipments the commerce shipments
	*/
	public void cacheResult(java.util.List<CommerceShipment> commerceShipments);

	/**
	* Creates a new commerce shipment with the primary key. Does not add the commerce shipment to the database.
	*
	* @param commerceShipmentId the primary key for the new commerce shipment
	* @return the new commerce shipment
	*/
	public CommerceShipment create(long commerceShipmentId);

	/**
	* Removes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentId the primary key of the commerce shipment
	* @return the commerce shipment that was removed
	* @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	*/
	public CommerceShipment remove(long commerceShipmentId)
		throws NoSuchShipmentException;

	public CommerceShipment updateImpl(CommerceShipment commerceShipment);

	/**
	* Returns the commerce shipment with the primary key or throws a {@link NoSuchShipmentException} if it could not be found.
	*
	* @param commerceShipmentId the primary key of the commerce shipment
	* @return the commerce shipment
	* @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	*/
	public CommerceShipment findByPrimaryKey(long commerceShipmentId)
		throws NoSuchShipmentException;

	/**
	* Returns the commerce shipment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShipmentId the primary key of the commerce shipment
	* @return the commerce shipment, or <code>null</code> if a commerce shipment with the primary key could not be found
	*/
	public CommerceShipment fetchByPrimaryKey(long commerceShipmentId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceShipment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce shipments.
	*
	* @return the commerce shipments
	*/
	public java.util.List<CommerceShipment> findAll();

	/**
	* Returns a range of all the commerce shipments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @return the range of commerce shipments
	*/
	public java.util.List<CommerceShipment> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce shipments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipments
	*/
	public java.util.List<CommerceShipment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator);

	/**
	* Returns an ordered range of all the commerce shipments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipments
	*/
	public java.util.List<CommerceShipment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce shipments from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce shipments.
	*
	* @return the number of commerce shipments
	*/
	public int countAll();
}