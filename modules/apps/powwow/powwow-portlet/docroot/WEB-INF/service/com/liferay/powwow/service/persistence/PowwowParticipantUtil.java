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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import com.liferay.powwow.model.PowwowParticipant;

import java.util.List;

/**
 * The persistence utility for the powwow participant service. This utility wraps {@link com.liferay.powwow.service.persistence.impl.PowwowParticipantPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipantPersistence
 * @see com.liferay.powwow.service.persistence.impl.PowwowParticipantPersistenceImpl
 * @generated
 */
@ProviderType
public class PowwowParticipantUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(PowwowParticipant powwowParticipant) {
		getPersistence().clearCache(powwowParticipant);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PowwowParticipant update(PowwowParticipant powwowParticipant) {
		return getPersistence().update(powwowParticipant);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PowwowParticipant update(
		PowwowParticipant powwowParticipant, ServiceContext serviceContext) {
		return getPersistence().update(powwowParticipant, serviceContext);
	}

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the matching powwow participants
	*/
	public static List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId) {
		return getPersistence().findByPowwowMeetingId(powwowMeetingId);
	}

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
	public static List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end) {
		return getPersistence()
				   .findByPowwowMeetingId(powwowMeetingId, start, end);
	}

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
	public static List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .findByPowwowMeetingId(powwowMeetingId, start, end,
			orderByComparator);
	}

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
	public static List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPowwowMeetingId(powwowMeetingId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPowwowMeetingId_First(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPowwowMeetingId_First(powwowMeetingId,
			orderByComparator);
	}

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .fetchByPowwowMeetingId_First(powwowMeetingId,
			orderByComparator);
	}

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPowwowMeetingId_Last(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPowwowMeetingId_Last(powwowMeetingId,
			orderByComparator);
	}

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPowwowMeetingId_Last(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .fetchByPowwowMeetingId_Last(powwowMeetingId,
			orderByComparator);
	}

	/**
	* Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowParticipantId the primary key of the current powwow participant
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow participant
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public static PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPowwowMeetingId_PrevAndNext(powwowParticipantId,
			powwowMeetingId, orderByComparator);
	}

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	*/
	public static void removeByPowwowMeetingId(long powwowMeetingId) {
		getPersistence().removeByPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the number of matching powwow participants
	*/
	public static int countByPowwowMeetingId(long powwowMeetingId) {
		return getPersistence().countByPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPMI_PUI(long powwowMeetingId,
		long participantUserId)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence().findByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId) {
		return getPersistence()
				   .fetchByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByPMI_PUI(powwowMeetingId, participantUserId,
			retrieveFromCache);
	}

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the powwow participant that was removed
	*/
	public static PowwowParticipant removeByPMI_PUI(long powwowMeetingId,
		long participantUserId)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .removeByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the number of matching powwow participants
	*/
	public static int countByPMI_PUI(long powwowMeetingId,
		long participantUserId) {
		return getPersistence()
				   .countByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence().findByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress) {
		return getPersistence().fetchByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByPMI_EA(powwowMeetingId, emailAddress,
			retrieveFromCache);
	}

	/**
	* Removes the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the powwow participant that was removed
	*/
	public static PowwowParticipant removeByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence().removeByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the number of matching powwow participants
	*/
	public static int countByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress) {
		return getPersistence().countByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the matching powwow participants
	*/
	public static List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type) {
		return getPersistence().findByPMI_T(powwowMeetingId, type);
	}

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
	public static List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end) {
		return getPersistence().findByPMI_T(powwowMeetingId, type, start, end);
	}

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
	public static List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .findByPMI_T(powwowMeetingId, type, start, end,
			orderByComparator);
	}

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
	public static List<PowwowParticipant> findByPMI_T(long powwowMeetingId,
		int type, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPMI_T(powwowMeetingId, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPMI_T_First(long powwowMeetingId,
		int type, OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPMI_T_First(powwowMeetingId, type, orderByComparator);
	}

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_T_First(long powwowMeetingId,
		int type, OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .fetchByPMI_T_First(powwowMeetingId, type, orderByComparator);
	}

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant
	* @throws NoSuchParticipantException if a matching powwow participant could not be found
	*/
	public static PowwowParticipant findByPMI_T_Last(long powwowMeetingId,
		int type, OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPMI_T_Last(powwowMeetingId, type, orderByComparator);
	}

	/**
	* Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	*/
	public static PowwowParticipant fetchByPMI_T_Last(long powwowMeetingId,
		int type, OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence()
				   .fetchByPMI_T_Last(powwowMeetingId, type, orderByComparator);
	}

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
	public static PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence()
				   .findByPMI_T_PrevAndNext(powwowParticipantId,
			powwowMeetingId, type, orderByComparator);
	}

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; and type = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	*/
	public static void removeByPMI_T(long powwowMeetingId, int type) {
		getPersistence().removeByPMI_T(powwowMeetingId, type);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the number of matching powwow participants
	*/
	public static int countByPMI_T(long powwowMeetingId, int type) {
		return getPersistence().countByPMI_T(powwowMeetingId, type);
	}

	/**
	* Caches the powwow participant in the entity cache if it is enabled.
	*
	* @param powwowParticipant the powwow participant
	*/
	public static void cacheResult(PowwowParticipant powwowParticipant) {
		getPersistence().cacheResult(powwowParticipant);
	}

	/**
	* Caches the powwow participants in the entity cache if it is enabled.
	*
	* @param powwowParticipants the powwow participants
	*/
	public static void cacheResult(List<PowwowParticipant> powwowParticipants) {
		getPersistence().cacheResult(powwowParticipants);
	}

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public static PowwowParticipant create(long powwowParticipantId) {
		return getPersistence().create(powwowParticipantId);
	}

	/**
	* Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public static PowwowParticipant remove(long powwowParticipantId)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence().remove(powwowParticipantId);
	}

	public static PowwowParticipant updateImpl(
		PowwowParticipant powwowParticipant) {
		return getPersistence().updateImpl(powwowParticipant);
	}

	/**
	* Returns the powwow participant with the primary key or throws a {@link NoSuchParticipantException} if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	*/
	public static PowwowParticipant findByPrimaryKey(long powwowParticipantId)
		throws com.liferay.powwow.exception.NoSuchParticipantException {
		return getPersistence().findByPrimaryKey(powwowParticipantId);
	}

	/**
	* Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	*/
	public static PowwowParticipant fetchByPrimaryKey(long powwowParticipantId) {
		return getPersistence().fetchByPrimaryKey(powwowParticipantId);
	}

	public static java.util.Map<java.io.Serializable, PowwowParticipant> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the powwow participants.
	*
	* @return the powwow participants
	*/
	public static List<PowwowParticipant> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<PowwowParticipant> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<PowwowParticipant> findAll(int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<PowwowParticipant> findAll(int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the powwow participants from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static PowwowParticipantPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PowwowParticipantPersistence)PortletBeanLocatorUtil.locate(com.liferay.powwow.service.ClpSerializer.getServletContextName(),
					PowwowParticipantPersistence.class.getName());

			ReferenceRegistry.registerReference(PowwowParticipantUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PowwowParticipantPersistence _persistence;
}