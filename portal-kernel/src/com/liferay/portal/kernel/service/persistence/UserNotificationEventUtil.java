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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the user notification event service. This utility wraps <code>com.liferay.portal.service.persistence.impl.UserNotificationEventPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventPersistence
 * @generated
 */
@ProviderType
public class UserNotificationEventUtil {
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
	public static void clearCache(UserNotificationEvent userNotificationEvent) {
		getPersistence().clearCache(userNotificationEvent);
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
	public static Map<Serializable, UserNotificationEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserNotificationEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UserNotificationEvent update(
		UserNotificationEvent userNotificationEvent) {
		return getPersistence().update(userNotificationEvent);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UserNotificationEvent update(
		UserNotificationEvent userNotificationEvent,
		ServiceContext serviceContext) {
		return getPersistence().update(userNotificationEvent, serviceContext);
	}

	/**
	* Returns all the user notification events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the user notification events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUuid_First(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUuid_First(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUuid_Last(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUuid_Last(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByUuid_PrevAndNext(
		long userNotificationEventId, String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUuid_PrevAndNext(userNotificationEventId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the user notification events where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of user notification events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching user notification events
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the user notification events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the user notification events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByUuid_C_PrevAndNext(
		long userNotificationEventId, String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(userNotificationEventId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the user notification events where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of user notification events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching user notification events
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the user notification events where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUserId(long userId,
		int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUserId(long userId,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByUserId(long userId,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUserId_First(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUserId_First(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByUserId_Last(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByUserId_Last(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByUserId_PrevAndNext(
		long userNotificationEventId, long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userNotificationEventId, userId,
			orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of user notification events where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user notification events
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the user notification events where type = &#63;.
	*
	* @param type the type
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByType(String type) {
		return getPersistence().findByType(type);
	}

	/**
	* Returns a range of all the user notification events where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByType(String type,
		int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByType(String type,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByType(String type,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByType_First(String type,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByType_First(String type,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByType_Last(String type,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByType_Last(String type,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where type = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByType_PrevAndNext(
		long userNotificationEventId, String type,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByType_PrevAndNext(userNotificationEventId, type,
			orderByComparator);
	}

	/**
	* Removes all the user notification events where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(String type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of user notification events where type = &#63;.
	*
	* @param type the type
	* @return the number of matching user notification events
	*/
	public static int countByType(String type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns all the user notification events where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType) {
		return getPersistence().findByU_DT(userId, deliveryType);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType, int start, int end) {
		return getPersistence().findByU_DT(userId, deliveryType, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_DT(userId, deliveryType, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_DT(userId, deliveryType, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_First(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_First(userId, deliveryType, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_First(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_First(userId, deliveryType, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_Last(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_Last(userId, deliveryType, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_Last(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_Last(userId, deliveryType, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_DT_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_PrevAndNext(userNotificationEventId, userId,
			deliveryType, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and deliveryType = &#63; from the database.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	*/
	public static void removeByU_DT(long userId, int deliveryType) {
		getPersistence().removeByU_DT(userId, deliveryType);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and deliveryType = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @return the number of matching user notification events
	*/
	public static int countByU_DT(long userId, int deliveryType) {
		return getPersistence().countByU_DT(userId, deliveryType);
	}

	/**
	* Returns all the user notification events where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered) {
		return getPersistence().findByU_D(userId, delivered);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered, int start, int end) {
		return getPersistence().findByU_D(userId, delivered, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_D(userId, delivered, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_D(userId, delivered, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_D_First(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_First(userId, delivered, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_D_First(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_D_First(userId, delivered, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_D_Last(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_Last(userId, delivered, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_D_Last(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_D_Last(userId, delivered, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_D_PrevAndNext(
		long userNotificationEventId, long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_PrevAndNext(userNotificationEventId, userId,
			delivered, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and delivered = &#63; from the database.
	*
	* @param userId the user ID
	* @param delivered the delivered
	*/
	public static void removeByU_D(long userId, boolean delivered) {
		getPersistence().removeByU_D(userId, delivered);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @return the number of matching user notification events
	*/
	public static int countByU_D(long userId, boolean delivered) {
		return getPersistence().countByU_D(userId, delivered);
	}

	/**
	* Returns all the user notification events where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A(long userId,
		boolean archived) {
		return getPersistence().findByU_A(userId, archived);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A(long userId,
		boolean archived, int start, int end) {
		return getPersistence().findByU_A(userId, archived, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A(long userId,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_A(userId, archived, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A(long userId,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_A(userId, archived, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_A_First(long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_First(userId, archived, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_A_First(long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_A_First(userId, archived, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_A_Last(long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_Last(userId, archived, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_A_Last(long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_A_Last(userId, archived, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and archived = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_PrevAndNext(userNotificationEventId, userId,
			archived, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and archived = &#63; from the database.
	*
	* @param userId the user ID
	* @param archived the archived
	*/
	public static void removeByU_A(long userId, boolean archived) {
		getPersistence().removeByU_A(userId, archived);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param archived the archived
	* @return the number of matching user notification events
	*/
	public static int countByU_A(long userId, boolean archived) {
		return getPersistence().countByU_A(userId, archived);
	}

	/**
	* Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered) {
		return getPersistence().findByU_DT_D(userId, deliveryType, delivered);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered, int start, int end) {
		return getPersistence()
				   .findByU_DT_D(userId, deliveryType, delivered, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_DT_D(userId, deliveryType, delivered, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_DT_D(userId, deliveryType, delivered, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_D_First(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_First(userId, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_D_First(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_D_First(userId, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_D_Last(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_Last(userId, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_D_Last(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_D_Last(userId, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_DT_D_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_PrevAndNext(userNotificationEventId, userId,
			deliveryType, delivered, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; from the database.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	*/
	public static void removeByU_DT_D(long userId, int deliveryType,
		boolean delivered) {
		getPersistence().removeByU_DT_D(userId, deliveryType, delivered);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @return the number of matching user notification events
	*/
	public static int countByU_DT_D(long userId, int deliveryType,
		boolean delivered) {
		return getPersistence().countByU_DT_D(userId, deliveryType, delivered);
	}

	/**
	* Returns all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived) {
		return getPersistence().findByU_DT_A(userId, deliveryType, archived);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived, int start, int end) {
		return getPersistence()
				   .findByU_DT_A(userId, deliveryType, archived, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_DT_A(userId, deliveryType, archived, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_DT_A(userId, deliveryType, archived, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_A_First(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_First(userId, deliveryType, archived,
			orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_A_First(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_A_First(userId, deliveryType, archived,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_A_Last(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_Last(userId, deliveryType, archived,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_A_Last(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_A_Last(userId, deliveryType, archived,
			orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_DT_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_PrevAndNext(userNotificationEventId, userId,
			deliveryType, archived, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63; from the database.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	*/
	public static void removeByU_DT_A(long userId, int deliveryType,
		boolean archived) {
		getPersistence().removeByU_DT_A(userId, deliveryType, archived);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param archived the archived
	* @return the number of matching user notification events
	*/
	public static int countByU_DT_A(long userId, int deliveryType,
		boolean archived) {
		return getPersistence().countByU_DT_A(userId, deliveryType, archived);
	}

	/**
	* Returns all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired) {
		return getPersistence().findByU_D_A(userId, delivered, actionRequired);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired, int start, int end) {
		return getPersistence()
				   .findByU_D_A(userId, delivered, actionRequired, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_D_A(userId, delivered, actionRequired, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_D_A(userId, delivered, actionRequired, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_D_A_First(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_A_First(userId, delivered, actionRequired,
			orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_D_A_First(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_D_A_First(userId, delivered, actionRequired,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_D_A_Last(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_A_Last(userId, delivered, actionRequired,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_D_A_Last(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_D_A_Last(userId, delivered, actionRequired,
			orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_D_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean delivered,
		boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_D_A_PrevAndNext(userNotificationEventId, userId,
			delivered, actionRequired, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; from the database.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	*/
	public static void removeByU_D_A(long userId, boolean delivered,
		boolean actionRequired) {
		getPersistence().removeByU_D_A(userId, delivered, actionRequired);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param delivered the delivered
	* @param actionRequired the action required
	* @return the number of matching user notification events
	*/
	public static int countByU_D_A(long userId, boolean delivered,
		boolean actionRequired) {
		return getPersistence().countByU_D_A(userId, delivered, actionRequired);
	}

	/**
	* Returns all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived) {
		return getPersistence().findByU_A_A(userId, actionRequired, archived);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived, int start, int end) {
		return getPersistence()
				   .findByU_A_A(userId, actionRequired, archived, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_A_A(userId, actionRequired, archived, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_A_A(userId, actionRequired, archived, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_A_A_First(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_A_First(userId, actionRequired, archived,
			orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_A_A_First(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_A_A_First(userId, actionRequired, archived,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_A_A_Last(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_A_Last(userId, actionRequired, archived,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_A_A_Last(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_A_A_Last(userId, actionRequired, archived,
			orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_A_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean actionRequired,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_A_A_PrevAndNext(userNotificationEventId, userId,
			actionRequired, archived, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	*/
	public static void removeByU_A_A(long userId, boolean actionRequired,
		boolean archived) {
		getPersistence().removeByU_A_A(userId, actionRequired, archived);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param actionRequired the action required
	* @param archived the archived
	* @return the number of matching user notification events
	*/
	public static int countByU_A_A(long userId, boolean actionRequired,
		boolean archived) {
		return getPersistence().countByU_A_A(userId, actionRequired, archived);
	}

	/**
	* Returns all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_T_DT_D(long userId,
		String type, int deliveryType, boolean delivered) {
		return getPersistence()
				   .findByU_T_DT_D(userId, type, deliveryType, delivered);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_T_DT_D(long userId,
		String type, int deliveryType, boolean delivered, int start, int end) {
		return getPersistence()
				   .findByU_T_DT_D(userId, type, deliveryType, delivered,
			start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_T_DT_D(long userId,
		String type, int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_T_DT_D(userId, type, deliveryType, delivered,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_T_DT_D(long userId,
		String type, int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_T_DT_D(userId, type, deliveryType, delivered,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_T_DT_D_First(long userId,
		String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_T_DT_D_First(userId, type, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_T_DT_D_First(long userId,
		String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_T_DT_D_First(userId, type, deliveryType,
			delivered, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_T_DT_D_Last(long userId,
		String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_T_DT_D_Last(userId, type, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_T_DT_D_Last(long userId,
		String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_T_DT_D_Last(userId, type, deliveryType, delivered,
			orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_T_DT_D_PrevAndNext(
		long userNotificationEventId, long userId, String type,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_T_DT_D_PrevAndNext(userNotificationEventId, userId,
			type, deliveryType, delivered, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	*/
	public static void removeByU_T_DT_D(long userId, String type,
		int deliveryType, boolean delivered) {
		getPersistence().removeByU_T_DT_D(userId, type, deliveryType, delivered);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @return the number of matching user notification events
	*/
	public static int countByU_T_DT_D(long userId, String type,
		int deliveryType, boolean delivered) {
		return getPersistence()
				   .countByU_T_DT_D(userId, type, deliveryType, delivered);
	}

	/**
	* Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired) {
		return getPersistence()
				   .findByU_DT_D_A(userId, deliveryType, delivered,
			actionRequired);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired, int start,
		int end) {
		return getPersistence()
				   .findByU_DT_D_A(userId, deliveryType, delivered,
			actionRequired, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_DT_D_A(userId, deliveryType, delivered,
			actionRequired, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_DT_D_A(userId, deliveryType, delivered,
			actionRequired, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_D_A_First(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_A_First(userId, deliveryType, delivered,
			actionRequired, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_D_A_First(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_D_A_First(userId, deliveryType, delivered,
			actionRequired, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_D_A_Last(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_A_Last(userId, deliveryType, delivered,
			actionRequired, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_D_A_Last(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_D_A_Last(userId, deliveryType, delivered,
			actionRequired, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_DT_D_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_D_A_PrevAndNext(userNotificationEventId, userId,
			deliveryType, delivered, actionRequired, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; from the database.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	*/
	public static void removeByU_DT_D_A(long userId, int deliveryType,
		boolean delivered, boolean actionRequired) {
		getPersistence()
			.removeByU_DT_D_A(userId, deliveryType, delivered, actionRequired);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param delivered the delivered
	* @param actionRequired the action required
	* @return the number of matching user notification events
	*/
	public static int countByU_DT_D_A(long userId, int deliveryType,
		boolean delivered, boolean actionRequired) {
		return getPersistence()
				   .countByU_DT_D_A(userId, deliveryType, delivered,
			actionRequired);
	}

	/**
	* Returns all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @return the matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived) {
		return getPersistence()
				   .findByU_DT_A_A(userId, deliveryType, actionRequired,
			archived);
	}

	/**
	* Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived, int start,
		int end) {
		return getPersistence()
				   .findByU_DT_A_A(userId, deliveryType, actionRequired,
			archived, start, end);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .findByU_DT_A_A(userId, deliveryType, actionRequired,
			archived, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user notification events
	*/
	public static List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_DT_A_A(userId, deliveryType, actionRequired,
			archived, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_A_A_First(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_A_First(userId, deliveryType, actionRequired,
			archived, orderByComparator);
	}

	/**
	* Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_A_A_First(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_A_A_First(userId, deliveryType, actionRequired,
			archived, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event
	* @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	*/
	public static UserNotificationEvent findByU_DT_A_A_Last(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_A_Last(userId, deliveryType, actionRequired,
			archived, orderByComparator);
	}

	/**
	* Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	*/
	public static UserNotificationEvent fetchByU_DT_A_A_Last(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence()
				   .fetchByU_DT_A_A_Last(userId, deliveryType, actionRequired,
			archived, orderByComparator);
	}

	/**
	* Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userNotificationEventId the primary key of the current user notification event
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent[] findByU_DT_A_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence()
				   .findByU_DT_A_A_PrevAndNext(userNotificationEventId, userId,
			deliveryType, actionRequired, archived, orderByComparator);
	}

	/**
	* Removes all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	*/
	public static void removeByU_DT_A_A(long userId, int deliveryType,
		boolean actionRequired, boolean archived) {
		getPersistence()
			.removeByU_DT_A_A(userId, deliveryType, actionRequired, archived);
	}

	/**
	* Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	*
	* @param userId the user ID
	* @param deliveryType the delivery type
	* @param actionRequired the action required
	* @param archived the archived
	* @return the number of matching user notification events
	*/
	public static int countByU_DT_A_A(long userId, int deliveryType,
		boolean actionRequired, boolean archived) {
		return getPersistence()
				   .countByU_DT_A_A(userId, deliveryType, actionRequired,
			archived);
	}

	/**
	* Caches the user notification event in the entity cache if it is enabled.
	*
	* @param userNotificationEvent the user notification event
	*/
	public static void cacheResult(UserNotificationEvent userNotificationEvent) {
		getPersistence().cacheResult(userNotificationEvent);
	}

	/**
	* Caches the user notification events in the entity cache if it is enabled.
	*
	* @param userNotificationEvents the user notification events
	*/
	public static void cacheResult(
		List<UserNotificationEvent> userNotificationEvents) {
		getPersistence().cacheResult(userNotificationEvents);
	}

	/**
	* Creates a new user notification event with the primary key. Does not add the user notification event to the database.
	*
	* @param userNotificationEventId the primary key for the new user notification event
	* @return the new user notification event
	*/
	public static UserNotificationEvent create(long userNotificationEventId) {
		return getPersistence().create(userNotificationEventId);
	}

	/**
	* Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userNotificationEventId the primary key of the user notification event
	* @return the user notification event that was removed
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent remove(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().remove(userNotificationEventId);
	}

	public static UserNotificationEvent updateImpl(
		UserNotificationEvent userNotificationEvent) {
		return getPersistence().updateImpl(userNotificationEvent);
	}

	/**
	* Returns the user notification event with the primary key or throws a <code>NoSuchUserNotificationEventException</code> if it could not be found.
	*
	* @param userNotificationEventId the primary key of the user notification event
	* @return the user notification event
	* @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent findByPrimaryKey(
		long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException {
		return getPersistence().findByPrimaryKey(userNotificationEventId);
	}

	/**
	* Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userNotificationEventId the primary key of the user notification event
	* @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	*/
	public static UserNotificationEvent fetchByPrimaryKey(
		long userNotificationEventId) {
		return getPersistence().fetchByPrimaryKey(userNotificationEventId);
	}

	/**
	* Returns all the user notification events.
	*
	* @return the user notification events
	*/
	public static List<UserNotificationEvent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @return the range of user notification events
	*/
	public static List<UserNotificationEvent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user notification events
	*/
	public static List<UserNotificationEvent> findAll(int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user notification events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user notification events
	* @param end the upper bound of the range of user notification events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of user notification events
	*/
	public static List<UserNotificationEvent> findAll(int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the user notification events from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user notification events.
	*
	* @return the number of user notification events
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UserNotificationEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserNotificationEventPersistence)PortalBeanLocatorUtil.locate(UserNotificationEventPersistence.class.getName());

			ReferenceRegistry.registerReference(UserNotificationEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static UserNotificationEventPersistence _persistence;
}