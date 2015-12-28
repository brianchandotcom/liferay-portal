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

package com.liferay.portlet.calendar.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;

/**
 * The persistence utility for the cal event service. This utility wraps {@link com.liferay.portlet.calendar.service.persistence.impl.CalEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventPersistence
 * @see com.liferay.portlet.calendar.service.persistence.impl.CalEventPersistenceImpl
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class CalEventUtil {
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
	public static void clearCache(CalEvent calEvent) {
		getPersistence().clearCache(calEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CalEvent> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CalEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CalEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static CalEvent update(CalEvent calEvent) {
		return getPersistence().update(calEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static CalEvent update(CalEvent calEvent,
		ServiceContext serviceContext) {
		return getPersistence().update(calEvent, serviceContext);
	}

	/**
	* Returns all the cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cal events
	*/
	public static List<CalEvent> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByUuid_First(java.lang.String uuid,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where uuid = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByUuid_PrevAndNext(long eventId,
		java.lang.String uuid, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByUuid_PrevAndNext(eventId, uuid, orderByComparator);
	}

	/**
	* Removes all the cal events where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cal events
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEventException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cal event where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cal event that was removed
	*/
	public static CalEvent removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cal events where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cal events
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cal events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cal events
	*/
	public static List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cal events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByUuid_C_PrevAndNext(long eventId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(eventId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cal events where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cal events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cal events
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cal events
	*/
	public static List<CalEvent> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByGroupId_First(long groupId,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByGroupId_First(long groupId,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByGroupId_Last(long groupId,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByGroupId_Last(long groupId,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByGroupId_PrevAndNext(long eventId,
		long groupId, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(eventId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cal events where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cal events
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cal events
	*/
	public static List<CalEvent> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByCompanyId_First(long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByCompanyId_First(long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByCompanyId_Last(long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByCompanyId_Last(long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where companyId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByCompanyId_PrevAndNext(long eventId,
		long companyId, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(eventId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cal events where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cal events
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the matching cal events
	*/
	public static List<CalEvent> findByNotRemindBy(int remindBy) {
		return getPersistence().findByNotRemindBy(remindBy);
	}

	/**
	* Returns a range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param remindBy the remind by
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end) {
		return getPersistence().findByNotRemindBy(remindBy, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param remindBy the remind by
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByNotRemindBy(remindBy, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param remindBy the remind by
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByNotRemindBy(remindBy, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByNotRemindBy_First(int remindBy,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_First(remindBy, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByNotRemindBy_First(int remindBy,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByNotRemindBy_First(remindBy, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByNotRemindBy_Last(int remindBy,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_Last(remindBy, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByNotRemindBy_Last(int remindBy,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByNotRemindBy_Last(remindBy, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByNotRemindBy_PrevAndNext(long eventId,
		int remindBy, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByNotRemindBy_PrevAndNext(eventId, remindBy,
			orderByComparator);
	}

	/**
	* Removes all the cal events where remindBy &ne; &#63; from the database.
	*
	* @param remindBy the remind by
	*/
	public static void removeByNotRemindBy(int remindBy) {
		getPersistence().removeByNotRemindBy(remindBy);
	}

	/**
	* Returns the number of cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the number of matching cal events
	*/
	public static int countByNotRemindBy(int remindBy) {
		return getPersistence().countByNotRemindBy(remindBy);
	}

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId, java.lang.String type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId, java.lang.String type,
		int start, int end) {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId, java.lang.String type,
		int start, int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId, java.lang.String type,
		int start, int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_T_First(long groupId, java.lang.String type,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_T_First(long groupId,
		java.lang.String type, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_T_Last(long groupId, java.lang.String type,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_T_Last(long groupId, java.lang.String type,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().fetchByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByG_T_PrevAndNext(long eventId, long groupId,
		java.lang.String type, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_PrevAndNext(eventId, groupId, type,
			orderByComparator);
	}

	/**
	* Returns all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @return the matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types) {
		return getPersistence().findByG_T(groupId, types);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end) {
		return getPersistence().findByG_T(groupId, types, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByG_T(groupId, types, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T(groupId, types, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public static void removeByG_T(long groupId, java.lang.String type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cal events
	*/
	public static int countByG_T(long groupId, java.lang.String type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @return the number of matching cal events
	*/
	public static int countByG_T(long groupId, java.lang.String[] types) {
		return getPersistence().countByG_T(groupId, types);
	}

	/**
	* Returns all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the matching cal events
	*/
	public static List<CalEvent> findByG_R(long groupId, boolean repeating) {
		return getPersistence().findByG_R(groupId, repeating);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end) {
		return getPersistence().findByG_R(groupId, repeating, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end, OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByG_R(groupId, repeating, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_R(groupId, repeating, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_R_First(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_First(groupId, repeating, orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_R_First(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_R_First(groupId, repeating, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_R_Last(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_Last(groupId, repeating, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_R_Last(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_R_Last(groupId, repeating, orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByG_R_PrevAndNext(long eventId, long groupId,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_PrevAndNext(eventId, groupId, repeating,
			orderByComparator);
	}

	/**
	* Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	*/
	public static void removeByG_R(long groupId, boolean repeating) {
		getPersistence().removeByG_R(groupId, repeating);
	}

	/**
	* Returns the number of cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public static int countByG_R(long groupId, boolean repeating) {
		return getPersistence().countByG_R(groupId, repeating);
	}

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating) {
		return getPersistence().findByG_T_R(groupId, type, repeating);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end) {
		return getPersistence().findByG_T_R(groupId, type, repeating, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByG_T_R(groupId, type, repeating, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T_R(groupId, type, repeating, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_T_R_First(long groupId,
		java.lang.String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_R_First(groupId, type, repeating,
			orderByComparator);
	}

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_T_R_First(long groupId,
		java.lang.String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_R_First(groupId, type, repeating,
			orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public static CalEvent findByG_T_R_Last(long groupId,
		java.lang.String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_R_Last(groupId, type, repeating, orderByComparator);
	}

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public static CalEvent fetchByG_T_R_Last(long groupId,
		java.lang.String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_R_Last(groupId, type, repeating,
			orderByComparator);
	}

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent[] findByG_T_R_PrevAndNext(long eventId,
		long groupId, java.lang.String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_R_PrevAndNext(eventId, groupId, type, repeating,
			orderByComparator);
	}

	/**
	* Returns all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating) {
		return getPersistence().findByG_T_R(groupId, types, repeating);
	}

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end) {
		return getPersistence()
				   .findByG_T_R(groupId, types, repeating, start, end);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence()
				   .findByG_T_R(groupId, types, repeating, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cal events
	*/
	public static List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T_R(groupId, types, repeating, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	*/
	public static void removeByG_T_R(long groupId, java.lang.String type,
		boolean repeating) {
		getPersistence().removeByG_T_R(groupId, type, repeating);
	}

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public static int countByG_T_R(long groupId, java.lang.String type,
		boolean repeating) {
		return getPersistence().countByG_T_R(groupId, type, repeating);
	}

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public static int countByG_T_R(long groupId, java.lang.String[] types,
		boolean repeating) {
		return getPersistence().countByG_T_R(groupId, types, repeating);
	}

	/**
	* Caches the cal event in the entity cache if it is enabled.
	*
	* @param calEvent the cal event
	*/
	public static void cacheResult(CalEvent calEvent) {
		getPersistence().cacheResult(calEvent);
	}

	/**
	* Caches the cal events in the entity cache if it is enabled.
	*
	* @param calEvents the cal events
	*/
	public static void cacheResult(List<CalEvent> calEvents) {
		getPersistence().cacheResult(calEvents);
	}

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public static CalEvent create(long eventId) {
		return getPersistence().create(eventId);
	}

	/**
	* Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent remove(long eventId)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().remove(eventId);
	}

	public static CalEvent updateImpl(CalEvent calEvent) {
		return getPersistence().updateImpl(calEvent);
	}

	/**
	* Returns the cal event with the primary key or throws a {@link NoSuchEventException} if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public static CalEvent findByPrimaryKey(long eventId)
		throws com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByPrimaryKey(eventId);
	}

	/**
	* Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	*/
	public static CalEvent fetchByPrimaryKey(long eventId) {
		return getPersistence().fetchByPrimaryKey(eventId);
	}

	public static java.util.Map<java.io.Serializable, CalEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cal events.
	*
	* @return the cal events
	*/
	public static List<CalEvent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of cal events
	*/
	public static List<CalEvent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cal events
	*/
	public static List<CalEvent> findAll(int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cal events
	*/
	public static List<CalEvent> findAll(int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cal events from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CalEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CalEventPersistence)PortalBeanLocatorUtil.locate(CalEventPersistence.class.getName());

			ReferenceRegistry.registerReference(CalEventUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static CalEventPersistence _persistence;
}