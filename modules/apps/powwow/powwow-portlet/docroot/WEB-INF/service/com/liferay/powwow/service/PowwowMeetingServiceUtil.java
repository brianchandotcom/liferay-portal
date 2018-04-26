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
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for PowwowMeeting. This utility wraps
 * {@link com.liferay.powwow.service.impl.PowwowMeetingServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowMeetingService
 * @see com.liferay.powwow.service.base.PowwowMeetingServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowMeetingServiceImpl
 * @generated
 */
@ProviderType
public class PowwowMeetingServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowMeetingServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long groupId, String portletId, long powwowServerId, String name,
		String description, String providerType,
		java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
		String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPowwowMeeting(groupId, portletId, powwowServerId, name,
			description, providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	public static com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
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

	public static com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPowwowMeeting(powwowMeetingId);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc) {
		return getService().getPowwowMeetings(groupId, start, end, obc);
	}

	public static int getPowwowMeetingsCount(long groupId) {
		return getService().getPowwowMeetingsCount(groupId);
	}

	public static com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, String name,
		String description, String providerType,
		java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
		String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePowwowMeeting(powwowMeetingId, powwowServerId, name,
			description, providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	public static void clearService() {
		_service = null;
	}

	public static PowwowMeetingService getService() {
		if (_service == null) {
			_service = (PowwowMeetingService)PortletBeanLocatorUtil.locate(ServletContextUtil.getServletContextName(),
					PowwowMeetingService.class.getName());

			ReferenceRegistry.registerReference(PowwowMeetingServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PowwowMeetingService _service;
}