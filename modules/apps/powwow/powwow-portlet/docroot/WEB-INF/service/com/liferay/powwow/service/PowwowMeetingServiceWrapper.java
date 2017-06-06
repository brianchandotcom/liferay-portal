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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PowwowMeetingService}.
 *
 * @author Shinn Lok
 * @see PowwowMeetingService
 * @generated
 */
public class PowwowMeetingServiceWrapper implements PowwowMeetingService,
	ServiceWrapper<PowwowMeetingService> {
	public PowwowMeetingServiceWrapper(
		PowwowMeetingService powwowMeetingService) {
		_powwowMeetingService = powwowMeetingService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _powwowMeetingService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_powwowMeetingService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowMeetingService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		long groupId, java.lang.String portletId, long powwowServerId,
		java.lang.String name, java.lang.String description,
		java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.addPowwowMeeting(groupId, portletId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.deletePowwowMeeting(powwowMeetingId);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.getPowwowMeeting(powwowMeetingId);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.getPowwowMeetings(groupId, start, end, obc);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.getPowwowMeetingsCount(groupId);
	}

	@Override
	public com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		long powwowMeetingId, long powwowServerId, java.lang.String name,
		java.lang.String description, java.lang.String providerType,
		java.util.Map<java.lang.String, java.io.Serializable> providerTypeMetadataMap,
		java.lang.String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _powwowMeetingService.updatePowwowMeeting(powwowMeetingId,
			powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PowwowMeetingService getWrappedPowwowMeetingService() {
		return _powwowMeetingService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPowwowMeetingService(
		PowwowMeetingService powwowMeetingService) {
		_powwowMeetingService = powwowMeetingService;
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