/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchUserNotificationDeliveryException;
import com.liferay.portal.kernel.model.UserNotificationDelivery;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the user notification delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryUtil
 * @generated
 */
@ProviderType
public interface UserNotificationDeliveryPersistence
	extends BasePersistence<UserNotificationDelivery> {

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
	public java.util.List<UserNotificationDelivery> findByUserId(long userId);

	/**
	 * Returns a range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @return the range of matching user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator);

	/**
	 * Returns an ordered range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException;

	/**
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator);

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException;

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator);

	/**
	 * Returns the user notification deliveries before and after the current user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userNotificationDeliveryId the primary key of the current user notification delivery
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public UserNotificationDelivery[] findByUserId_PrevAndNext(
			long userNotificationDeliveryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException;

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
	 * Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or throws a <code>NoSuchUserNotificationDeliveryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @return the matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery findByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws NoSuchUserNotificationDeliveryException;

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
	public UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType);

	/**
	 * Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	public UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType, boolean useFinderCache);

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
	public UserNotificationDelivery removeByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws NoSuchUserNotificationDeliveryException;

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
	public int countByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType);

	/**
	 * Caches the user notification delivery in the entity cache if it is enabled.
	 *
	 * @param userNotificationDelivery the user notification delivery
	 */
	public void cacheResult(UserNotificationDelivery userNotificationDelivery);

	/**
	 * Caches the user notification deliveries in the entity cache if it is enabled.
	 *
	 * @param userNotificationDeliveries the user notification deliveries
	 */
	public void cacheResult(
		java.util.List<UserNotificationDelivery> userNotificationDeliveries);

	/**
	 * Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	 *
	 * @param userNotificationDeliveryId the primary key for the new user notification delivery
	 * @return the new user notification delivery
	 */
	public UserNotificationDelivery create(long userNotificationDeliveryId);

	/**
	 * Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery that was removed
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public UserNotificationDelivery remove(long userNotificationDeliveryId)
		throws NoSuchUserNotificationDeliveryException;

	public UserNotificationDelivery updateImpl(
		UserNotificationDelivery userNotificationDelivery);

	/**
	 * Returns the user notification delivery with the primary key or throws a <code>NoSuchUserNotificationDeliveryException</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public UserNotificationDelivery findByPrimaryKey(
			long userNotificationDeliveryId)
		throws NoSuchUserNotificationDeliveryException;

	/**
	 * Returns the user notification delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery, or <code>null</code> if a user notification delivery with the primary key could not be found
	 */
	public UserNotificationDelivery fetchByPrimaryKey(
		long userNotificationDeliveryId);

	/**
	 * Returns all the user notification deliveries.
	 *
	 * @return the user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findAll();

	/**
	 * Returns a range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @return the range of user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator);

	/**
	 * Returns an ordered range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user notification deliveries
	 */
	public java.util.List<UserNotificationDelivery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UserNotificationDelivery> orderByComparator,
		boolean useFinderCache);

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