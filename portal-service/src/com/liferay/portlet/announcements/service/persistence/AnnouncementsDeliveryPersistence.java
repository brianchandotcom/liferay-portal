/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;

/**
 * The persistence interface for the announcements delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryPersistenceImpl
 * @see AnnouncementsDeliveryUtil
 * @generated
 */
public interface AnnouncementsDeliveryPersistence extends BasePersistence<AnnouncementsDelivery> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsDeliveryUtil} to access the announcements delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the announcements delivery in the entity cache if it is enabled.
	*
	* @param announcementsDelivery the announcements delivery
	*/
	public void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery);

	/**
	* Caches the announcements deliveries in the entity cache if it is enabled.
	*
	* @param announcementsDeliveries the announcements deliveries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDeliveries);

	/**
	* Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	*
	* @param deliveryId the primary key for the new announcements delivery
	* @return the new announcements delivery
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery create(
		long deliveryId);

	/**
	* Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery that was removed
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery remove(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the announcements delivery with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchDeliveryException} if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the announcements deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @return the range of matching announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the announcements deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first announcements delivery in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements delivery
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Returns the last announcements delivery in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements delivery
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Returns the announcements deliveries before and after the current announcements delivery in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param deliveryId the primary key of the current announcements delivery
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements delivery
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery[] findByUserId_PrevAndNext(
		long deliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.announcements.NoSuchDeliveryException} if it could not be found.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery
	* @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the announcements deliveries.
	*
	* @return the announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @return the range of announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the announcements deliveries where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @return the announcements delivery that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery removeByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	/**
	* Removes all the announcements deliveries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of announcements deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @return the number of matching announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of announcements deliveries.
	*
	* @return the number of announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}