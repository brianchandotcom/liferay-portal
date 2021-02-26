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
import com.liferay.powwow.model.PowwowParticipant;

import java.util.List;

/**
 * Provides the remote service utility for PowwowParticipant. This utility wraps
 * <code>com.liferay.powwow.service.impl.PowwowParticipantServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowParticipantService
 * @generated
 */
public class PowwowParticipantServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.powwow.service.impl.PowwowParticipantServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PowwowParticipant deletePowwowParticipant(
			PowwowParticipant powwowParticipant)
		throws PortalException {

		return getService().deletePowwowParticipant(powwowParticipant);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<PowwowParticipant> getPowwowParticipants(
			long powwowMeetingId)
		throws PortalException {

		return getService().getPowwowParticipants(powwowMeetingId);
	}

	public static int getPowwowParticipantsCount(long powwowMeetingId)
		throws PortalException {

		return getService().getPowwowParticipantsCount(powwowMeetingId);
	}

	public static PowwowParticipant updatePowwowParticipant(
			long powwowParticipantId, long powwowMeetingId, String name,
			long participantUserId, String emailAddress, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updatePowwowParticipant(
			powwowParticipantId, powwowMeetingId, name, participantUserId,
			emailAddress, type, status, serviceContext);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowParticipantService getService() {
		return _service;
	}

	private static volatile PowwowParticipantService _service;

}