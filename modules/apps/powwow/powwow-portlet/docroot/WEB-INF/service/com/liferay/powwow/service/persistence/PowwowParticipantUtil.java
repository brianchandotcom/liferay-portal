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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.powwow.model.PowwowParticipant;

import java.util.List;

/**
 * The persistence utility for the powwow participant service. This utility wraps {@link PowwowParticipantPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipantPersistence
 * @see PowwowParticipantPersistenceImpl
 * @generated
 */
public class PowwowParticipantUtil {
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
	public static void clearCache(PowwowParticipant powwowParticipant) {
		getPersistence().clearCache(powwowParticipant);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PowwowParticipant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PowwowParticipant update(PowwowParticipant powwowParticipant)
		throws SystemException {
		return getPersistence().update(powwowParticipant);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PowwowParticipant update(
		PowwowParticipant powwowParticipant, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(powwowParticipant, serviceContext);
	}

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPowwowMeetingId(powwowMeetingId);
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPowwowMeetingId(powwowMeetingId, start, end);
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPowwowMeetingId(powwowMeetingId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPowwowMeetingId_First(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPowwowMeetingId_Last(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPowwowMeetingId_Last(
		long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence()
				   .findByPowwowMeetingId_PrevAndNext(powwowParticipantId,
			powwowMeetingId, orderByComparator);
	}

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPowwowMeetingId(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPowwowMeetingId(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence().findByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant removeByPMI_PUI(
		long powwowMeetingId, long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence()
				   .removeByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param participantUserId the participant user ID
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPMI_PUI(long powwowMeetingId,
		long participantUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByPMI_PUI(powwowMeetingId, participantUserId);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence().findByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant removeByPMI_EA(
		long powwowMeetingId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence().removeByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param emailAddress the email address
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPMI_EA(long powwowMeetingId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPMI_EA(powwowMeetingId, emailAddress);
	}

	/**
	* Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPMI_T(powwowMeetingId, type);
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPMI_T(powwowMeetingId, type, start, end);
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPMI_T(powwowMeetingId, type, start, end,
			orderByComparator);
	}

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
	public static com.liferay.powwow.model.PowwowParticipant findByPMI_T_First(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_T_First(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPMI_T_Last(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPMI_T_Last(
		long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence()
				   .findByPMI_T_PrevAndNext(powwowParticipantId,
			powwowMeetingId, type, orderByComparator);
	}

	/**
	* Removes all the powwow participants where powwowMeetingId = &#63; and type = &#63; from the database.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPMI_T(long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPMI_T(powwowMeetingId, type);
	}

	/**
	* Returns the number of powwow participants where powwowMeetingId = &#63; and type = &#63;.
	*
	* @param powwowMeetingId the powwow meeting ID
	* @param type the type
	* @return the number of matching powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPMI_T(long powwowMeetingId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPMI_T(powwowMeetingId, type);
	}

	/**
	* Caches the powwow participant in the entity cache if it is enabled.
	*
	* @param powwowParticipant the powwow participant
	*/
	public static void cacheResult(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant) {
		getPersistence().cacheResult(powwowParticipant);
	}

	/**
	* Caches the powwow participants in the entity cache if it is enabled.
	*
	* @param powwowParticipants the powwow participants
	*/
	public static void cacheResult(
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants) {
		getPersistence().cacheResult(powwowParticipants);
	}

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public static com.liferay.powwow.model.PowwowParticipant create(
		long powwowParticipantId) {
		return getPersistence().create(powwowParticipantId);
	}

	/**
	* Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant remove(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence().remove(powwowParticipantId);
	}

	public static com.liferay.powwow.model.PowwowParticipant updateImpl(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(powwowParticipant);
	}

	/**
	* Returns the powwow participant with the primary key or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant findByPrimaryKey(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.powwow.NoSuchParticipantException {
		return getPersistence().findByPrimaryKey(powwowParticipantId);
	}

	/**
	* Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.powwow.model.PowwowParticipant fetchByPrimaryKey(
		long powwowParticipantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(powwowParticipantId);
	}

	/**
	* Returns all the powwow participants.
	*
	* @return the powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the powwow participants from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
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

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(PowwowParticipantPersistence persistence) {
	}

	private static PowwowParticipantPersistence _persistence;
}