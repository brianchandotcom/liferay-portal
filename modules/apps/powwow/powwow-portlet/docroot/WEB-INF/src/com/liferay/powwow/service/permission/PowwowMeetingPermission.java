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

package com.liferay.powwow.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;
import com.liferay.powwow.service.PowwowParticipantLocalServiceUtil;

/**
 * @author Shinn Lok
 */
public class PowwowMeetingPermission {

	public static void check(
			PermissionChecker permissionChecker, long powwowMeetingId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, powwowMeetingId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, PowwowMeeting powwowMeeting,
			PowwowParticipant powwowParticipant, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, powwowMeeting, powwowParticipant,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long powwowMeetingId,
			String actionId)
		throws PortalException {

		PowwowMeeting powwowMeeting =
			PowwowMeetingLocalServiceUtil.getPowwowMeeting(powwowMeetingId);

		User user = permissionChecker.getUser();

		PowwowParticipant powwowParticipant = null;

		if (user.getUserId() > 0) {
			powwowParticipant =
				PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(
					powwowMeeting.getPowwowMeetingId(), user.getUserId());
		}
		else {
			powwowParticipant =
				PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(
					powwowMeeting.getPowwowMeetingId(), user.getEmailAddress());
		}

		return contains(
			permissionChecker, powwowMeeting, powwowParticipant, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, PowwowMeeting powwowMeeting,
		PowwowParticipant powwowParticipant, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				powwowMeeting.getCompanyId(), PowwowMeeting.class.getName(),
				powwowMeeting.getPowwowMeetingId(), powwowMeeting.getUserId(),
				actionId)) {

			return true;
		}

		if (powwowParticipant == null) {
			return false;
		}

		if (powwowParticipant.getType() ==
				PowwowParticipantConstants.TYPE_HOST) {

			return true;
		}

		return permissionChecker.hasPermission(
			powwowMeeting.getGroupId(), PowwowMeeting.class.getName(),
			powwowMeeting.getPowwowServerId(), actionId);
	}

}