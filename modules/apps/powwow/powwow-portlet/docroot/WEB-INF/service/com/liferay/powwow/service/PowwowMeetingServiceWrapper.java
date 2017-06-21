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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PowwowMeetingService}.
 *
 * @author Shinn Lok
 * @see PowwowMeetingService
 * @generated
 */
@ProviderType
public class PowwowMeetingServiceWrapper implements PowwowMeetingService,
	ServiceWrapper<PowwowMeetingService> {
	public PowwowMeetingServiceWrapper(
		PowwowMeetingService powwowMeetingService) {
		_powwowMeetingService = powwowMeetingService;
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long groupId, java.lang.String portletId, long powwowServerId,
		java.lang.String name, java.lang.String description,
		java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowMeetingService.addPowwowMeeting(groupId, portletId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowMeetingService.deletePowwowMeeting(powwowMeetingId);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowMeetingService.getPowwowMeeting(powwowMeetingId);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowMeetingService.updatePowwowMeeting(powwowMeetingId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId) {
		return _powwowMeetingService.getPowwowMeetingsCount(groupId);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowMeetingService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _powwowMeetingService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _powwowMeetingService.getPowwowMeetings(groupId, start, end, obc);
	}

	@Override
	public PowwowMeetingService getWrappedService() {
		return _powwowMeetingService;
	}

	@Override
	public void setWrappedService(PowwowMeetingService powwowMeetingService) {
		_powwowMeetingService = powwowMeetingService;
	}

	private PowwowMeetingService _powwowMeetingService;
}