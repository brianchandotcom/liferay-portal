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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.powwow.exception.NoSuchParticipantException;
import com.liferay.powwow.model.PowwowParticipant;

/**
 * The persistence interface for the powwow participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see com.liferay.powwow.service.persistence.impl.PowwowParticipantPersistenceImpl
 * @see PowwowParticipantUtil
 * @generated
 */
@ProviderType
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
	*/
	public java.util.List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId);

	/**
	* Returns a range of all the powwow participants where powwowMeetingId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end);

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPowwowMeetingId_First(long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPowwowMeetingId_Last(long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPowwowMeetingId_Last(long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowParticipantId the primary key of the current powwow participant
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow participant
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	*/
	public void removeByPowwowMeetingId(long powwowMeetingId);

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the number of matching powwow participants
	*/
	public int countByPowwowMeetingId(long powwowMeetingId);

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPMI_PUI(long powwowMeetingId,
		long participantUserId) throws NoSuchParticipantException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId);

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId, boolean retrieveFromCache);

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the powwow participant that was removed
	*/
	public PowwowParticipant removeByPMI_PUI(long powwowMeetingId,
		long participantUserId) throws NoSuchParticipantException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the number of matching powwow participants
	*/
	public int countByPMI_PUI(long powwowMeetingId, long participantUserId);

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress) throws NoSuchParticipantException;

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress);

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress, boolean retrieveFromCache);

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the powwow participant that was removed
	*/
	public PowwowParticipant removeByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress) throws NoSuchParticipantException;

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the number of matching powwow participants
	*/
	public int countByPMI_EA(long powwowMeetingId, java.lang.String emailAddress);

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type);

	/**
	* Returns a range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end);

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow participants
	*/
	public java.util.List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPMI_T_First(long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_T_First(long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public PowwowParticipant findByPMI_T_Last(long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public PowwowParticipant fetchByPMI_T_Last(long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowParticipantId the primary key of the current powwow participant
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow participant
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException;

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; and type = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	*/
	public void removeByPMI_T(long powwowMeetingId, int type);

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the number of matching powwow participants
	*/
	public int countByPMI_T(long powwowMeetingId, int type);

	/**
	* Caches the powwow participant in the entity cache if it is enabled.
	*
	* @param powwowParticipant the powwow participant
	*/
	public void cacheResult(PowwowParticipant powwowParticipant);

	/**
	* Caches the powwow participants in the entity cache if it is enabled.
	*
	* @param powwowParticipants the powwow participants
	*/
	public void cacheResult(
		java.util.List<PowwowParticipant> powwowParticipants);

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public PowwowParticipant create(long powwowParticipantId);

	/**
	* Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public PowwowParticipant remove(long powwowParticipantId)
		throws NoSuchParticipantException;

	public PowwowParticipant updateImpl(PowwowParticipant powwowParticipant);

	/**
	* Returns the powwow participant with the primary key or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public PowwowParticipant findByPrimaryKey(long powwowParticipantId)
		throws NoSuchParticipantException;

	/**
	* Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	*/
	public PowwowParticipant fetchByPrimaryKey(long powwowParticipantId);

	@Override
	public java.util.Map<java.io.Serializable, PowwowParticipant> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the powwow participants.
	*
	* @return the powwow participants
	*/
	public java.util.List<PowwowParticipant> findAll();

	/**
	* Returns a range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of powwow participants
	*/
	public java.util.List<PowwowParticipant> findAll(int start, int end);

	/**
	* Returns an ordered range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow participants
	*/
	public java.util.List<PowwowParticipant> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator);

	/**
	* Returns an ordered range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of powwow participants
	*/
	public java.util.List<PowwowParticipant> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the powwow participants from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}