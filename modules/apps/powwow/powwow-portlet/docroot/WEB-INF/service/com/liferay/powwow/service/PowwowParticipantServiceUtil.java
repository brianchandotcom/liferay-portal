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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.service.InvokableService;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for PowwowParticipant. This utility wraps
 * {@link com.liferay.powwow.service.impl.PowwowParticipantServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowParticipantService
 * @see com.liferay.powwow.service.base.PowwowParticipantServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowParticipantServiceImpl
 * @generated
 */
@ProviderType
public class PowwowParticipantServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowParticipantServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePowwowParticipant(powwowParticipant);
	}

	public static com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		long powwowParticipantId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePowwowParticipant(powwowParticipantId,
			powwowMeetingId, name, participantUserId, emailAddress, type,
			status, serviceContext);
	}

	public static int getPowwowParticipantsCount(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPowwowParticipantsCount(powwowMeetingId);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPowwowParticipants(powwowMeetingId);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowParticipantService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PowwowParticipantService.class.getName());

			if (invokableService instanceof PowwowParticipantService) {
				_service = (PowwowParticipantService)invokableService;
			}
			else {
				_service = new PowwowParticipantServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(PowwowParticipantServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PowwowParticipantService _service;
}