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

package com.liferay.powwow.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.powwow.model.PowwowMeeting;

/**
 * The persistence interface for the powwow meeting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeetingPersistenceImpl
 * @see PowwowMeetingUtil
 * @generated
 */
public interface PowwowMeetingPersistence extends BasePersistence<PowwowMeeting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowMeetingUtil} to access the powwow meeting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the powwow meetings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the first powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the last powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where groupId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] findByGroupId_PrevAndNext(
		long powwowMeetingId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns all the powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching powwow meetings that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set of powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] filterFindByGroupId_PrevAndNext(
		long powwowMeetingId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Removes all the powwow meetings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching powwow meetings that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow meetings where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @return the matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPowwowServerId(
		long powwowServerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings where powwowServerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPowwowServerId(
		long powwowServerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPowwowServerId(
		long powwowServerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByPowwowServerId_First(
		long powwowServerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByPowwowServerId_First(
		long powwowServerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByPowwowServerId_Last(
		long powwowServerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByPowwowServerId_Last(
		long powwowServerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param powwowServerId the powwow server ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] findByPowwowServerId_PrevAndNext(
		long powwowMeetingId, long powwowServerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Removes all the powwow meetings where powwowServerId = &#63; from the database.
	*
	* @param powwowServerId the powwow server ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPowwowServerId(long powwowServerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings where powwowServerId = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @return the number of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countByPowwowServerId(long powwowServerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow meetings where status = &#63;.
	*
	* @param status the status
	* @return the matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByStatus(
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByStatus(
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByStatus(
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByStatus_First(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the first powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByStatus_First(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByStatus_Last(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the last powwow meeting in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByStatus_Last(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] findByStatus_PrevAndNext(
		long powwowMeetingId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Removes all the powwow meetings where status = &#63; from the database.
	*
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStatus(int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings where status = &#63;.
	*
	* @param status the status
	* @return the number of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countByStatus(int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByU_S(
		long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByU_S(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByU_S(
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByU_S_First(long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the first powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByU_S_First(
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByU_S_Last(long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the last powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByU_S_Last(long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] findByU_S_PrevAndNext(
		long powwowMeetingId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Removes all the powwow meetings where userId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_S(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the number of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_S(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @return the matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPSI_S(
		long powwowServerId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPSI_S(
		long powwowServerId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findByPSI_S(
		long powwowServerId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByPSI_S_First(
		long powwowServerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the first powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByPSI_S_First(
		long powwowServerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByPSI_S_Last(
		long powwowServerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the last powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow meeting, or <code>null</code> if a matching powwow meeting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByPSI_S_Last(
		long powwowServerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meetings before and after the current powwow meeting in the ordered set where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowMeetingId the primary key of the current powwow meeting
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting[] findByPSI_S_PrevAndNext(
		long powwowMeetingId, long powwowServerId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Removes all the powwow meetings where powwowServerId = &#63; and status = &#63; from the database.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPSI_S(long powwowServerId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings where powwowServerId = &#63; and status = &#63;.
	*
	* @param powwowServerId the powwow server ID
	* @param status the status
	* @return the number of matching powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countByPSI_S(long powwowServerId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the powwow meeting in the entity cache if it is enabled.
	*
	* @param powwowMeeting the powwow meeting
	*/
	public void cacheResult(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting);

	/**
	* Caches the powwow meetings in the entity cache if it is enabled.
	*
	* @param powwowMeetings the powwow meetings
	*/
	public void cacheResult(
		java.util.List<com.liferay.powwow.model.PowwowMeeting> powwowMeetings);

	/**
	* Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	*
	* @param powwowMeetingId the primary key for the new powwow meeting
	* @return the new powwow meeting
	*/
	public com.liferay.powwow.model.PowwowMeeting create(long powwowMeetingId);

	/**
	* Removes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting that was removed
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting remove(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	public com.liferay.powwow.model.PowwowMeeting updateImpl(
		com.liferay.powwow.model.PowwowMeeting powwowMeeting)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow meeting with the primary key or throws a {@link com.liferay.powwow.NoSuchMeetingException} if it could not be found.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting
	* @throws com.liferay.powwow.NoSuchMeetingException if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting findByPrimaryKey(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchMeetingException;

	/**
	* Returns the powwow meeting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowMeetingId the primary key of the powwow meeting
	* @return the powwow meeting, or <code>null</code> if a powwow meeting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowMeeting fetchByPrimaryKey(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow meetings.
	*
	* @return the powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @return the range of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow meetings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowMeetingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow meetings
	* @param end the upper bound of the range of powwow meetings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the powwow meetings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow meetings.
	*
	* @return the number of powwow meetings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}