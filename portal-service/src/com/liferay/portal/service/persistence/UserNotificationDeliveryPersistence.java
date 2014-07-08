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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.UserNotificationDelivery;

/**
 * The persistence interface for the user notification delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryPersistenceImpl
 * @see UserNotificationDeliveryUtil
 * @generated
 */
@ProviderType
public interface UserNotificationDeliveryPersistence extends BasePersistence<UserNotificationDelivery> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserNotificationDeliveryUtil} to access the user notification delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user notification deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId);

	/**
	* Returns a range of all the user notification deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user notification deliveries
	* @param end the upper bound of the range of user notification deliveries (not inclusive)
	* @return the range of matching user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the user notification deliveries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user notification deliveries
	* @param end the upper bound of the range of user notification deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Returns the first user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Returns the last user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user notification deliveries before and after the current user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userNotificationDeliveryId the primary key of the current user notification delivery
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery[] findByUserId_PrevAndNext(
		long userNotificationDeliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Removes all the user notification deliveries where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of user notification deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user notification deliveries
	*/
	public int countByUserId(long userId);

	/**
	* Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or throws a {@link com.liferay.portal.NoSuchUserNotificationDeliveryException} if it could not be found.
	*
	* @param userId the user ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param notificationType the notification type
	* @param deliveryType the delivery type
	* @return the matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery findByU_P_C_N_D(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param notificationType the notification type
	* @param deliveryType the delivery type
	* @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType);

	/**
	* Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param notificationType the notification type
	* @param deliveryType the delivery type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType, boolean retrieveFromCache);

	/**
	* Removes the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; from the database.
	*
	* @param userId the user ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param notificationType the notification type
	* @param deliveryType the delivery type
	* @return the user notification delivery that was removed
	*/
	public com.liferay.portal.model.UserNotificationDelivery removeByU_P_C_N_D(
		long userId, java.lang.String portletId, long classNameId,
		int notificationType, int deliveryType)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Returns the number of user notification deliveries where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param notificationType the notification type
	* @param deliveryType the delivery type
	* @return the number of matching user notification deliveries
	*/
	public int countByU_P_C_N_D(long userId, java.lang.String portletId,
		long classNameId, int notificationType, int deliveryType);

	/**
	* Caches the user notification delivery in the entity cache if it is enabled.
	*
	* @param userNotificationDelivery the user notification delivery
	*/
	public void cacheResult(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery);

	/**
	* Caches the user notification deliveries in the entity cache if it is enabled.
	*
	* @param userNotificationDeliveries the user notification deliveries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserNotificationDelivery> userNotificationDeliveries);

	/**
	* Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	*
	* @param userNotificationDeliveryId the primary key for the new user notification delivery
	* @return the new user notification delivery
	*/
	public com.liferay.portal.model.UserNotificationDelivery create(
		long userNotificationDeliveryId);

	/**
	* Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery that was removed
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery remove(
		long userNotificationDeliveryId)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	public com.liferay.portal.model.UserNotificationDelivery updateImpl(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery);

	/**
	* Returns the user notification delivery with the primary key or throws a {@link com.liferay.portal.NoSuchUserNotificationDeliveryException} if it could not be found.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery findByPrimaryKey(
		long userNotificationDeliveryId)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException;

	/**
	* Returns the user notification delivery with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery, or <code>null</code> if a user notification delivery with the primary key could not be found
	*/
	public com.liferay.portal.model.UserNotificationDelivery fetchByPrimaryKey(
		long userNotificationDeliveryId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.UserNotificationDelivery> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the user notification deliveries.
	*
	* @return the user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll();

	/**
	* Returns a range of all the user notification deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification deliveries
	* @param end the upper bound of the range of user notification deliveries (not inclusive)
	* @return the range of user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the user notification deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification deliveries
	* @param end the upper bound of the range of user notification deliveries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user notification deliveries
	*/
	public java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the user notification deliveries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user notification deliveries.
	*
	* @return the number of user notification deliveries
	*/
	public int countAll();
}