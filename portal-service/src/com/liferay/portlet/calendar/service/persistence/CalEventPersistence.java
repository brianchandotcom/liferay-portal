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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.calendar.model.CalEvent;

/**
 * The persistence interface for the cal event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventPersistenceImpl
 * @see CalEventUtil
 * @generated
 */
public interface CalEventPersistence extends BasePersistence<CalEvent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CalEventUtil} to access the cal event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the cal event in the entity cache if it is enabled.
	*
	* @param calEvent the cal event
	*/
	public void cacheResult(
		com.liferay.portlet.calendar.model.CalEvent calEvent);

	/**
	* Caches the cal events in the entity cache if it is enabled.
	*
	* @param calEvents the cal events
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> calEvents);

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public com.liferay.portlet.calendar.model.CalEvent create(long eventId);

	/**
	* Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent remove(long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent updateImpl(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal event with the primary key or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent fetchByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByUuid_PrevAndNext(
		long eventId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByCompanyId_PrevAndNext(
		long eventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] filterFindByGroupId_PrevAndNext(
		long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNotRemindBy(
		int remindBy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByNotRemindBy_First(
		int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByNotRemindBy_Last(
		int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where remindBy &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByNotRemindBy_PrevAndNext(
		long eventId, int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		long eventId, long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String[] types, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] filterFindByG_T_PrevAndNext(
		long eventId, long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String[] types, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T(
		long groupId, java.lang.String[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		long eventId, long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] filterFindByG_R_PrevAndNext(
		long eventId, long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String type, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String type, boolean repeating, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String type, boolean repeating, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_T_R_First(
		long groupId, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent findByG_T_R_Last(
		long groupId, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] findByG_T_R_PrevAndNext(
		long eventId, long groupId, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String type, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String type, boolean repeating, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String type, boolean repeating, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent[] filterFindByG_T_R_PrevAndNext(
		long eventId, long groupId, java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Returns all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> filterFindByG_T_R(
		long groupId, java.lang.String[] types, boolean repeating, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the cal events.
	*
	* @return the cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @return the range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the cal events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cal events
	* @param end the upper bound of the range of cal events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cal events
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the cal event where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cal event that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.calendar.model.CalEvent removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	/**
	* Removes all the cal events where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where remindBy &ne; &#63; from the database.
	*
	* @param remindBy the remind by
	* @throws SystemException if a system exception occurred
	*/
	public void removeByNotRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_T_R(long groupId, java.lang.String type,
		boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the cal events from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByNotRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T(long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_T(long groupId, java.lang.String[] types)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T_R(long groupId, java.lang.String type,
		boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the number of matching cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T_R(long groupId, java.lang.String[] types,
		boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_T_R(long groupId, java.lang.String type,
		boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the number of matching cal events that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_T_R(long groupId, java.lang.String[] types,
		boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}