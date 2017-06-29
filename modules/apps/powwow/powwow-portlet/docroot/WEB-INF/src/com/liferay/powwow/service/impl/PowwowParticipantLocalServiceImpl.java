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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.service.base.PowwowParticipantLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class PowwowParticipantLocalServiceImpl
	extends PowwowParticipantLocalServiceBaseImpl {

	@Override
	public PowwowParticipant addPowwowParticipant(
			long userId, long groupId, long powwowMeetingId, String name,
			long participantUserId, String emailAddress, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		long powwowParticipantId = counterLocalService.increment();

		PowwowParticipant powwowParticipant =
			powwowParticipantPersistence.create(powwowParticipantId);

		powwowParticipant.setGroupId(groupId);
		powwowParticipant.setCompanyId(user.getCompanyId());
		powwowParticipant.setUserId(user.getUserId());
		powwowParticipant.setUserName(user.getFullName());
		powwowParticipant.setCreateDate(serviceContext.getCreateDate(now));
		powwowParticipant.setModifiedDate(serviceContext.getModifiedDate(now));
		powwowParticipant.setPowwowMeetingId(powwowMeetingId);
		powwowParticipant.setName(name);
		powwowParticipant.setParticipantUserId(participantUserId);
		powwowParticipant.setEmailAddress(emailAddress);
		powwowParticipant.setType(type);
		powwowParticipant.setStatus(status);

		powwowParticipantPersistence.update(powwowParticipant);

		return powwowParticipant;
	}

	@Override
	public PowwowParticipant deletePowwowParticipant(
		PowwowParticipant powwowParticipant) {

		return powwowParticipantPersistence.remove(powwowParticipant);
	}

	@Override
	public PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, long participantUserId) {

		return powwowParticipantPersistence.fetchByPMI_PUI(
			powwowMeetingId, participantUserId);
	}

	@Override
	public PowwowParticipant fetchPowwowParticipant(
		long powwowMeetingId, String emailAddress) {

		return powwowParticipantPersistence.fetchByPMI_EA(
			powwowMeetingId, emailAddress);
	}

	@Override
	public List<PowwowParticipant> getPowwowParticipants(long powwowMeetingId) {
		return powwowParticipantPersistence.findByPowwowMeetingId(
			powwowMeetingId);
	}

	@Override
	public List<PowwowParticipant> getPowwowParticipants(
		long powwowMeetingId, int type) {

		return powwowParticipantPersistence.findByPMI_T(powwowMeetingId, type);
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId) {
		return powwowParticipantPersistence.countByPowwowMeetingId(
			powwowMeetingId);
	}

	@Override
	public int getPowwowParticipantsCount(long powwowMeetingId, int type) {
		return powwowParticipantPersistence.countByPMI_T(powwowMeetingId, type);
	}

	@Override
	public PowwowParticipant updatePowwowParticipant(
			long powwowParticipantId, long powwowMeetingId, String name,
			long participantUserId, String emailAddress, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		PowwowParticipant powwowParticipant =
			powwowParticipantPersistence.findByPrimaryKey(powwowParticipantId);

		powwowParticipant.setModifiedDate(serviceContext.getModifiedDate(null));

		if (powwowMeetingId > 0) {
			powwowParticipant.setPowwowMeetingId(powwowMeetingId);
		}

		powwowParticipant.setName(name);
		powwowParticipant.setParticipantUserId(participantUserId);

		if (Validator.isNotNull(emailAddress)) {
			powwowParticipant.setEmailAddress(emailAddress);
		}

		powwowParticipant.setType(type);
		powwowParticipant.setStatus(status);

		return powwowParticipantPersistence.update(powwowParticipant);
	}

	@Override
	public PowwowParticipant updateStatus(long powwowParticipantId, int status)
		throws PortalException {

		PowwowParticipant powwowParticipant =
			powwowParticipantPersistence.findByPrimaryKey(powwowParticipantId);

		powwowParticipant.setStatus(status);

		powwowParticipantPersistence.update(powwowParticipant);

		return powwowParticipant;
	}

}