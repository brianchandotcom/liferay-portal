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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.service.base.PowwowParticipantServiceBaseImpl;
import com.liferay.powwow.service.permission.PowwowMeetingPermission;
import com.liferay.powwow.util.ActionKeys;

import java.util.List;

/**
 * @author Shinn Lok
 */
public class PowwowParticipantServiceImpl
	extends PowwowParticipantServiceBaseImpl {

	@Override
	public PowwowParticipant deletePowwowParticipant(
			PowwowParticipant powwowParticipant)
		throws PortalException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowParticipant.getPowwowMeetingId(),
			ActionKeys.UPDATE);

		return powwowParticipantPersistence.remove(powwowParticipant);
	}

	@Override
	public List<PowwowParticipant> getPowwowParticipants(long powwowMeetingId)
		throws PortalException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.VIEW);

		return powwowParticipantLocalService.getPowwowParticipants(
			powwowMeetingId);
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId)
		throws PortalException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.VIEW);

		return powwowParticipantLocalService.getPowwowParticipantsCount(
			powwowMeetingId);
	}

	@Override
	public PowwowParticipant updatePowwowParticipant(
			long powwowParticipantId, long powwowMeetingId, String name,
			long participantUserId, String emailAddress, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		PowwowMeetingPermission.check(
			getPermissionChecker(), powwowMeetingId, ActionKeys.UPDATE);

		return powwowParticipantLocalService.updatePowwowParticipant(
			powwowParticipantId, powwowMeetingId, name, participantUserId,
			emailAddress, type, status, serviceContext);
	}

}