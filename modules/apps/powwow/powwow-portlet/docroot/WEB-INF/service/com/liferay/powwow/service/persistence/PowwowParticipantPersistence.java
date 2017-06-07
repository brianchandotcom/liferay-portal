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

import com.liferay.powwow.model.PowwowParticipant;

/**
 * The persistence interface for the powwow participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipantPersistenceImpl
 * @see PowwowParticipantUtil
 * @generated
 */
public interface PowwowParticipantPersistence extends BasePersistence<PowwowParticipant> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowParticipantUtil} to access the powwow participant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow participants where powwowMeetingId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPowwowMeetingId_First(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPowwowMeetingId_Last(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPowwowMeetingId_Last(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowParticipantId the primary key of the current powwow participant
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPowwowMeetingId(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByPowwowMeetingId(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the powwow participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant removeByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByPMI_PUI(long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the powwow participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant removeByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByPMI_EA(long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPMI_T_First(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_T_First(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPMI_T_Last(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPMI_T_Last(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowParticipantId the primary key of the current powwow participant
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; and type = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPMI_T(long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByPMI_T(long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the powwow participant in the entity cache if it is enabled.
	*
	* @param powwowParticipant the powwow participant
	*/
	public void cacheResult(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant);

	/**
	* Caches the powwow participants in the entity cache if it is enabled.
	*
	* @param powwowParticipants the powwow participants
	*/
	public void cacheResult(
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants);

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public com.liferay.powwow.model.PowwowParticipant create(
		long powwowParticipantId);

	/**
	* Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant remove(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	public com.liferay.powwow.model.PowwowParticipant updateImpl(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the powwow participant with the primary key or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant findByPrimaryKey(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException;

	/**
	* Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.powwow.model.PowwowParticipant fetchByPrimaryKey(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the powwow participants.
	*
	* @return the powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the powwow participants from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}