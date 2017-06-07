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

package com.liferay.powwow.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.service.base.PowwowMeetingServiceBaseImpl;
import com.liferay.powwow.service.permission.MeetingsPermission;
import com.liferay.powwow.service.permission.PowwowMeetingPermission;
import com.liferay.powwow.util.ActionKeys;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class PowwowMeetingServiceImpl extends PowwowMeetingServiceBaseImpl {

	@Override
	public PowwowMeeting addPowwowMeeting(
			long groupId, String portletId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MeetingsPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_MEETING);

		return powwowMeetingLocalService.addPowwowMeeting(
			getUserId(), groupId, powwowServerId, name, description,
			providerType, providerTypeMetadataMap, languageId,
			calendarBookingId, status, powwowParticipants, serviceContext);
	}

	@Override
	public PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException, SystemException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.DELETE);

		return powwowMeetingLocalService.deletePowwowMeeting(powwowMeetingId);
	}

	@Override
	public PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException, SystemException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.VIEW);

		return powwowMeetingLocalService.getPowwowMeeting(powwowMeetingId);
	}

	@Override
	public List<PowwowMeeting> getPowwowMeetings(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return powwowMeetingPersistence.filterFindByGroupId(
			groupId, start, end, obc);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId) throws SystemException {
		return powwowMeetingPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.UPDATE);

		return powwowMeetingLocalService.updatePowwowMeeting(
			powwowMeetingId, powwowServerId, name, description, providerType,
			providerTypeMetadataMap, languageId, calendarBookingId, status,
			powwowParticipants, serviceContext);
	}

}