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

import com.liferay.portal.exception.NoSuchSystemEventException;
import com.liferay.portal.model.SystemEvent;

/**
 * The persistence interface for the system event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.SystemEventPersistenceImpl
 * @see SystemEventUtil
 * @generated
 */
@ProviderType
public interface SystemEventPersistence extends BasePersistence<SystemEvent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SystemEventUtil} to access the system event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the system events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching system events
	*/
	public java.util.List<SystemEvent> findByGroupId(long groupId);

	/**
	* Returns a range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public java.util.List<SystemEvent> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns an ordered range of all the system events where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the first system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the last system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the last system event in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent[] findByGroupId_PrevAndNext(long systemEventId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Removes all the system events where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of system events where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching system events
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @return the matching system events
	*/
	public java.util.List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey);

	/**
	* Returns a range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_S(long groupId,
		long systemEventSetKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_S_First(long groupId, long systemEventSetKey,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_S_First(long groupId, long systemEventSetKey,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_S_Last(long groupId, long systemEventSetKey,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_S_Last(long groupId, long systemEventSetKey,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent[] findByG_S_PrevAndNext(long systemEventId,
		long groupId, long systemEventSetKey,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Removes all the system events where groupId = &#63; and systemEventSetKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	*/
	public void removeByG_S(long groupId, long systemEventSetKey);

	/**
	* Returns the number of system events where groupId = &#63; and systemEventSetKey = &#63;.
	*
	* @param groupId the group ID
	* @param systemEventSetKey the system event set key
	* @return the number of matching system events
	*/
	public int countByG_S(long groupId, long systemEventSetKey);

	/**
	* Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent[] findByG_C_C_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching system events
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type);

	/**
	* Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching system events
	*/
	public java.util.List<SystemEvent> findByG_C_C_T(long groupId,
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_C_C_T_First(long groupId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_C_C_T_First(long groupId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event
	* @throws NoSuchSystemEventException if a matching system event could not be found
	*/
	public SystemEvent findByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching system event, or <code>null</code> if a matching system event could not be found
	*/
	public SystemEvent fetchByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param systemEventId the primary key of the current system event
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent[] findByG_C_C_T_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException;

	/**
	* Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public void removeByG_C_C_T(long groupId, long classNameId, long classPK,
		int type);

	/**
	* Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching system events
	*/
	public int countByG_C_C_T(long groupId, long classNameId, long classPK,
		int type);

	/**
	* Caches the system event in the entity cache if it is enabled.
	*
	* @param systemEvent the system event
	*/
	public void cacheResult(SystemEvent systemEvent);

	/**
	* Caches the system events in the entity cache if it is enabled.
	*
	* @param systemEvents the system events
	*/
	public void cacheResult(java.util.List<SystemEvent> systemEvents);

	/**
	* Creates a new system event with the primary key. Does not add the system event to the database.
	*
	* @param systemEventId the primary key for the new system event
	* @return the new system event
	*/
	public SystemEvent create(long systemEventId);

	/**
	* Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event that was removed
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent remove(long systemEventId)
		throws NoSuchSystemEventException;

	public SystemEvent updateImpl(SystemEvent systemEvent);

	/**
	* Returns the system event with the primary key or throws a {@link NoSuchSystemEventException} if it could not be found.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event
	* @throws NoSuchSystemEventException if a system event with the primary key could not be found
	*/
	public SystemEvent findByPrimaryKey(long systemEventId)
		throws NoSuchSystemEventException;

	/**
	* Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param systemEventId the primary key of the system event
	* @return the system event, or <code>null</code> if a system event with the primary key could not be found
	*/
	public SystemEvent fetchByPrimaryKey(long systemEventId);

	@Override
	public java.util.Map<java.io.Serializable, SystemEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the system events.
	*
	* @return the system events
	*/
	public java.util.List<SystemEvent> findAll();

	/**
	* Returns a range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @return the range of system events
	*/
	public java.util.List<SystemEvent> findAll(int start, int end);

	/**
	* Returns an ordered range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of system events
	*/
	public java.util.List<SystemEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator);

	/**
	* Returns an ordered range of all the system events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of system events
	* @param end the upper bound of the range of system events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of system events
	*/
	public java.util.List<SystemEvent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SystemEvent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the system events from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of system events.
	*
	* @return the number of system events
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}