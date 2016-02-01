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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.calendar.exception.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;

/**
 * The persistence interface for the cal event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.calendar.service.persistence.impl.CalEventPersistenceImpl
 * @see CalEventUtil
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public interface CalEventPersistence extends BasePersistence<CalEvent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CalEventUtil} to access the cal event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByUuid(java.lang.String uuid);

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
	public java.util.List<CalEvent> findByUuid(java.lang.String uuid,
		int start, int end);

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
	public java.util.List<CalEvent> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the cal events before and after the current cal event in the ordered set where uuid = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent[] findByUuid_PrevAndNext(long eventId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cal events where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cal events
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEventException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchEventException;

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cal event where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cal event that was removed
	*/
	public CalEvent removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchEventException;

	/**
	* Returns the number of cal events where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cal events
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cal events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId);

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
	public java.util.List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

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
	public java.util.List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public CalEvent[] findByUuid_C_PrevAndNext(long eventId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cal events where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cal events
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByGroupId(long groupId);

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
	public java.util.List<CalEvent> findByGroupId(long groupId, int start,
		int end);

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
	public java.util.List<CalEvent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the cal events before and after the current cal event in the ordered set where groupId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent[] findByGroupId_PrevAndNext(long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cal events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cal events
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByCompanyId(long companyId);

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
	public java.util.List<CalEvent> findByCompanyId(long companyId, int start,
		int end);

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
	public java.util.List<CalEvent> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the cal events before and after the current cal event in the ordered set where companyId = &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent[] findByCompanyId_PrevAndNext(long eventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cal events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cal events
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByNotRemindBy(int remindBy);

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
	public java.util.List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end);

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
	public java.util.List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByNotRemindBy(int remindBy, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByNotRemindBy_First(int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByNotRemindBy_First(int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByNotRemindBy_Last(int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByNotRemindBy_Last(int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the cal events before and after the current cal event in the ordered set where remindBy &ne; &#63;.
	*
	* @param eventId the primary key of the current cal event
	* @param remindBy the remind by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent[] findByNotRemindBy_PrevAndNext(long eventId, int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where remindBy &ne; &#63; from the database.
	*
	* @param remindBy the remind by
	*/
	public void removeByNotRemindBy(int remindBy);

	/**
	* Returns the number of cal events where remindBy &ne; &#63;.
	*
	* @param remindBy the remind by
	* @return the number of matching cal events
	*/
	public int countByNotRemindBy(int remindBy);

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String type);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String type, int start, int end);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByG_T_First(long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_T_First(long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByG_T_Last(long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_T_Last(long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public CalEvent[] findByG_T_PrevAndNext(long eventId, long groupId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByG_T(long groupId,
		java.lang.String[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, java.lang.String type);

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cal events
	*/
	public int countByG_T(long groupId, java.lang.String type);

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @return the number of matching cal events
	*/
	public int countByG_T(long groupId, java.lang.String[] types);

	/**
	* Returns all the cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByG_R(long groupId, boolean repeating);

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
	public java.util.List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end);

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
	public java.util.List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByG_R(long groupId, boolean repeating,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByG_R_First(long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_R_First(long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event
	* @throws NoSuchEventException if a matching cal event could not be found
	*/
	public CalEvent findByG_R_Last(long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_R_Last(long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public CalEvent[] findByG_R_PrevAndNext(long eventId, long groupId,
		boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	*/
	public void removeByG_R(long groupId, boolean repeating);

	/**
	* Returns the number of cal events where groupId = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public int countByG_R(long groupId, boolean repeating);

	/**
	* Returns all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the matching cal events
	*/
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String type, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

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
	public CalEvent findByG_T_R_First(long groupId, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_T_R_First(long groupId, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public CalEvent findByG_T_R_Last(long groupId, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

	/**
	* Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	*/
	public CalEvent fetchByG_T_R_Last(long groupId, java.lang.String type,
		boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public CalEvent[] findByG_T_R_PrevAndNext(long eventId, long groupId,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException;

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findByG_T_R(long groupId,
		java.lang.String[] types, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cal events where groupId = &#63; and type = &#63; and repeating = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	*/
	public void removeByG_T_R(long groupId, java.lang.String type,
		boolean repeating);

	/**
	* Returns the number of cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public int countByG_T_R(long groupId, java.lang.String type,
		boolean repeating);

	/**
	* Returns the number of cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	*
	* @param groupId the group ID
	* @param types the types
	* @param repeating the repeating
	* @return the number of matching cal events
	*/
	public int countByG_T_R(long groupId, java.lang.String[] types,
		boolean repeating);

	/**
	* Caches the cal event in the entity cache if it is enabled.
	*
	* @param calEvent the cal event
	*/
	public void cacheResult(CalEvent calEvent);

	/**
	* Caches the cal events in the entity cache if it is enabled.
	*
	* @param calEvents the cal events
	*/
	public void cacheResult(java.util.List<CalEvent> calEvents);

	/**
	* Creates a new cal event with the primary key. Does not add the cal event to the database.
	*
	* @param eventId the primary key for the new cal event
	* @return the new cal event
	*/
	public CalEvent create(long eventId);

	/**
	* Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event that was removed
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent remove(long eventId) throws NoSuchEventException;

	public CalEvent updateImpl(CalEvent calEvent);

	/**
	* Returns the cal event with the primary key or throws a {@link NoSuchEventException} if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event
	* @throws NoSuchEventException if a cal event with the primary key could not be found
	*/
	public CalEvent findByPrimaryKey(long eventId) throws NoSuchEventException;

	/**
	* Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param eventId the primary key of the cal event
	* @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	*/
	public CalEvent fetchByPrimaryKey(long eventId);

	@Override
	public java.util.Map<java.io.Serializable, CalEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cal events.
	*
	* @return the cal events
	*/
	public java.util.List<CalEvent> findAll();

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
	public java.util.List<CalEvent> findAll(int start, int end);

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
	public java.util.List<CalEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator);

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
	public java.util.List<CalEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cal events from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cal events.
	*
	* @return the number of cal events
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}