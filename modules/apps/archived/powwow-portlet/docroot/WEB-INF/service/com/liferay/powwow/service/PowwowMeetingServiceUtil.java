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

package com.liferay.powwow.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.powwow.model.PowwowMeeting;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for PowwowMeeting. This utility wraps
 * <code>com.liferay.powwow.service.impl.PowwowMeetingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowMeetingService
 * @generated
 */
public class PowwowMeetingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.powwow.service.impl.PowwowMeetingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PowwowMeeting addPowwowMeeting(
			long groupId, String portletId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addPowwowMeeting(
			groupId, portletId, powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	public static PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException {

		return getService().deletePowwowMeeting(powwowMeetingId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException {

		return getService().getPowwowMeeting(powwowMeetingId);
	}

	public static List<PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator) {

		return getService().getPowwowMeetings(
			groupId, start, end, orderByComparator);
	}

	public static int getPowwowMeetingsCount(long groupId) {
		return getService().getPowwowMeetingsCount(groupId);
	}

	public static PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updatePowwowMeeting(
			powwowMeetingId, powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowMeetingService getService() {
		return _service;
	}

	private static volatile PowwowMeetingService _service;

}