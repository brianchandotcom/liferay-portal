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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

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
public class PowwowMeetingServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowMeetingServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long groupId, java.lang.String portletId, long powwowServerId,
		java.lang.String name, java.lang.String description,
		java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPowwowMeeting(groupId, portletId, powwowServerId, name,
			description, providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	public static com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePowwowMeeting(powwowMeetingId);
	}

	public static com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeeting(powwowMeetingId);
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetings(groupId, start, end, obc);
	}

	public static int getPowwowMeetingsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPowwowMeetingsCount(groupId);
	}

	public static com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PowwowMeetingService.class.getName());

			if (invokableService instanceof PowwowMeetingService) {
				_service = (PowwowMeetingService)invokableService;
			}
			else {
				_service = new PowwowMeetingServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(PowwowMeetingServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PowwowMeetingService service) {
	}

	private static PowwowMeetingService _service;
}