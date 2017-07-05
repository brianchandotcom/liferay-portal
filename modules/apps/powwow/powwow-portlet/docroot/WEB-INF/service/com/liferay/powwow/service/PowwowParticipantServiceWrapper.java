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
 * Provides a wrapper for {@link PowwowParticipantService}.
 *
 * @author Shinn Lok
 * @see PowwowParticipantService
 * @generated
 */
@ProviderType
public class PowwowParticipantServiceWrapper implements PowwowParticipantService,
	ServiceWrapper<PowwowParticipantService> {
	public PowwowParticipantServiceWrapper(
		PowwowParticipantService powwowParticipantService) {
		_powwowParticipantService = powwowParticipantService;
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant deletePowwowParticipant(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantService.deletePowwowParticipant(powwowParticipant);
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant updatePowwowParticipant(
		long powwowParticipantId, long powwowMeetingId, java.lang.String name,
		long participantUserId, java.lang.String emailAddress, int type,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantService.updatePowwowParticipant(powwowParticipantId,
			powwowMeetingId, name, participantUserId, emailAddress, type,
			status, serviceContext);
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantService.getPowwowParticipantsCount(powwowMeetingId);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowParticipantService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _powwowParticipantService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowParticipantService.getPowwowParticipants(powwowMeetingId);
	}

	@Override
	public PowwowParticipantService getWrappedService() {
		return _powwowParticipantService;
	}

	@Override
	public void setWrappedService(
		PowwowParticipantService powwowParticipantService) {
		_powwowParticipantService = powwowParticipantService;
	}

	private PowwowParticipantService _powwowParticipantService;
}