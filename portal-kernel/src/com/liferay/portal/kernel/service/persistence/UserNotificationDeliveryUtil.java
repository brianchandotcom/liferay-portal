/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the user notification delivery service. This utility wraps <code>com.liferay.portal.service.persistence.impl.UserNotificationDeliveryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryPersistence
 * @generated
 */
public class UserNotificationDeliveryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		UserNotificationDelivery userNotificationDelivery) {

		getPersistence().clearCache(userNotificationDelivery);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, UserNotificationDelivery>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UserNotificationDelivery update(
		UserNotificationDelivery userNotificationDelivery) {

		return getPersistence().update(userNotificationDelivery);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UserNotificationDelivery update(
		UserNotificationDelivery userNotificationDelivery,
		ServiceContext serviceContext) {

		return getPersistence().update(
			userNotificationDelivery, serviceContext);
	}

	/**
	 * Returns all the user notification deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user notification deliveries
	 */
	public static List<UserNotificationDelivery> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

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
	public static List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

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
	public static List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	public static UserNotificationDelivery findByUserId_First(
			long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	public static UserNotificationDelivery fetchByUserId_First(
		long userId,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	public static UserNotificationDelivery findByUserId_Last(
			long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	public static UserNotificationDelivery fetchByUserId_Last(
		long userId,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the user notification deliveries before and after the current user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userNotificationDeliveryId the primary key of the current user notification delivery
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public static UserNotificationDelivery[] findByUserId_PrevAndNext(
			long userNotificationDeliveryId, long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().findByUserId_PrevAndNext(
			userNotificationDeliveryId, userId, orderByComparator);
	}

	/**
	 * Removes all the user notification deliveries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of user notification deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user notification deliveries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

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
	public static UserNotificationDelivery findByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().findByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);
	}

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
	public static UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		return getPersistence().fetchByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);
	}

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
	public static UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType, boolean useFinderCache) {

		return getPersistence().fetchByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType,
			useFinderCache);
	}

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
	public static UserNotificationDelivery removeByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().removeByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);
	}

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
	public static int countByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		return getPersistence().countByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);
	}

	/**
	 * Caches the user notification delivery in the entity cache if it is enabled.
	 *
	 * @param userNotificationDelivery the user notification delivery
	 */
	public static void cacheResult(
		UserNotificationDelivery userNotificationDelivery) {

		getPersistence().cacheResult(userNotificationDelivery);
	}

	/**
	 * Caches the user notification deliveries in the entity cache if it is enabled.
	 *
	 * @param userNotificationDeliveries the user notification deliveries
	 */
	public static void cacheResult(
		List<UserNotificationDelivery> userNotificationDeliveries) {

		getPersistence().cacheResult(userNotificationDeliveries);
	}

	/**
	 * Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	 *
	 * @param userNotificationDeliveryId the primary key for the new user notification delivery
	 * @return the new user notification delivery
	 */
	public static UserNotificationDelivery create(
		long userNotificationDeliveryId) {

		return getPersistence().create(userNotificationDeliveryId);
	}

	/**
	 * Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery that was removed
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public static UserNotificationDelivery remove(
			long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().remove(userNotificationDeliveryId);
	}

	public static UserNotificationDelivery updateImpl(
		UserNotificationDelivery userNotificationDelivery) {

		return getPersistence().updateImpl(userNotificationDelivery);
	}

	/**
	 * Returns the user notification delivery with the primary key or throws a <code>NoSuchUserNotificationDeliveryException</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	public static UserNotificationDelivery findByPrimaryKey(
			long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserNotificationDeliveryException {

		return getPersistence().findByPrimaryKey(userNotificationDeliveryId);
	}

	/**
	 * Returns the user notification delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery, or <code>null</code> if a user notification delivery with the primary key could not be found
	 */
	public static UserNotificationDelivery fetchByPrimaryKey(
		long userNotificationDeliveryId) {

		return getPersistence().fetchByPrimaryKey(userNotificationDeliveryId);
	}

	/**
	 * Returns all the user notification deliveries.
	 *
	 * @return the user notification deliveries
	 */
	public static List<UserNotificationDelivery> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<UserNotificationDelivery> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<UserNotificationDelivery> findAll(
		int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<UserNotificationDelivery> findAll(
		int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the user notification deliveries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of user notification deliveries.
	 *
	 * @return the number of user notification deliveries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UserNotificationDeliveryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		UserNotificationDeliveryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile UserNotificationDeliveryPersistence _persistence;

}