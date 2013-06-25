/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the user notification delivery service. This utility wraps {@link UserNotificationDeliveryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryPersistence
 * @see UserNotificationDeliveryPersistenceImpl
 * @generated
 */
public class UserNotificationDeliveryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(
		UserNotificationDelivery userNotificationDelivery) {
		getPersistence().clearCache(userNotificationDelivery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserNotificationDelivery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static UserNotificationDelivery update(
		UserNotificationDelivery userNotificationDelivery)
		throws SystemException {
		return getPersistence().update(userNotificationDelivery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static UserNotificationDelivery update(
		UserNotificationDelivery userNotificationDelivery,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(userNotificationDelivery, serviceContext);
	}

	/**
	* Returns all the user notification deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the user notification deliveries before and after the current user notification delivery in the ordered set where userId = &#63;.
	*
	* @param userNotificationDeliveryId the primary key of the current user notification delivery
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery[] findByUserId_PrevAndNext(
		long userNotificationDeliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userNotificationDeliveryId,
			userId, orderByComparator);
	}

	/**
	* Removes all the user notification deliveries where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of user notification deliveries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the user notification delivery where userId = &#63; and classNameId = &#63; and type = &#63; or throws a {@link com.liferay.portal.NoSuchUserNotificationDeliveryException} if it could not be found.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery findByU_C_T(
		long userId, long classNameId, int type)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_C_T(userId, classNameId, type);
	}

	/**
	* Returns the user notification delivery where userId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery fetchByU_C_T(
		long userId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_C_T(userId, classNameId, type);
	}

	/**
	* Returns the user notification delivery where userId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery fetchByU_C_T(
		long userId, long classNameId, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_C_T(userId, classNameId, type, retrieveFromCache);
	}

	/**
	* Removes the user notification delivery where userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the user notification delivery that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery removeByU_C_T(
		long userId, long classNameId, int type)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByU_C_T(userId, classNameId, type);
	}

	/**
	* Returns the number of user notification deliveries where userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the number of matching user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_C_T(long userId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_C_T(userId, classNameId, type);
	}

	/**
	* Caches the user notification delivery in the entity cache if it is enabled.
	*
	* @param userNotificationDelivery the user notification delivery
	*/
	public static void cacheResult(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		getPersistence().cacheResult(userNotificationDelivery);
	}

	/**
	* Caches the user notification deliveries in the entity cache if it is enabled.
	*
	* @param userNotificationDeliveries the user notification deliveries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserNotificationDelivery> userNotificationDeliveries) {
		getPersistence().cacheResult(userNotificationDeliveries);
	}

	/**
	* Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	*
	* @param userNotificationDeliveryId the primary key for the new user notification delivery
	* @return the new user notification delivery
	*/
	public static com.liferay.portal.model.UserNotificationDelivery create(
		long userNotificationDeliveryId) {
		return getPersistence().create(userNotificationDeliveryId);
	}

	/**
	* Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery that was removed
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery remove(
		long userNotificationDeliveryId)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userNotificationDeliveryId);
	}

	public static com.liferay.portal.model.UserNotificationDelivery updateImpl(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userNotificationDelivery);
	}

	/**
	* Returns the user notification delivery with the primary key or throws a {@link com.liferay.portal.NoSuchUserNotificationDeliveryException} if it could not be found.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery
	* @throws com.liferay.portal.NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery findByPrimaryKey(
		long userNotificationDeliveryId)
		throws com.liferay.portal.NoSuchUserNotificationDeliveryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userNotificationDeliveryId);
	}

	/**
	* Returns the user notification delivery with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userNotificationDeliveryId the primary key of the user notification delivery
	* @return the user notification delivery, or <code>null</code> if a user notification delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserNotificationDelivery fetchByPrimaryKey(
		long userNotificationDeliveryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userNotificationDeliveryId);
	}

	/**
	* Returns all the user notification deliveries.
	*
	* @return the user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserNotificationDelivery> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the user notification deliveries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user notification deliveries.
	*
	* @return the number of user notification deliveries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserNotificationDeliveryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserNotificationDeliveryPersistence)PortalBeanLocatorUtil.locate(UserNotificationDeliveryPersistence.class.getName());

			ReferenceRegistry.registerReference(UserNotificationDeliveryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(UserNotificationDeliveryPersistence persistence) {
	}

	private static UserNotificationDeliveryPersistence _persistence;
}