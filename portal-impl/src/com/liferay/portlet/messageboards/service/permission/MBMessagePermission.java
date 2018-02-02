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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.service.permission.
 *             MBMessagePermission}
 */
@Deprecated
public class MBMessagePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long messageId,
			String actionId)
		throws PortalException {

		MBMessage message = _getMBMessage(messageId);

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, message.getGroupId(),
				MBMessage.class.getName(), message.getMessageId(), actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBMessage.class.getName(),
				message.getMessageId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, message.getGroupId(),
				MBMessage.class.getName(), message.getMessageId(), actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBMessage.class.getName(),
				message.getMessageId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		MBMessage message = _getMBMessage(classPK);

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, message.getGroupId(),
				MBMessage.class.getName(), message.getMessageId(), actionId);

		if ((containsBaseModelPermission != null) &&
			containsBaseModelPermission) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, message.getGroupId(),
				MBMessage.class.getName(), message.getMessageId(), actionId);

		if ((containsBaseModelPermission != null) &&
			containsBaseModelPermission) {

			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.message.boards.internal.service.permission.MBMessagePermission#checkBaseModel(PermissionChecker, long, long, String)}
	 */
	@Deprecated
	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, MBMessage.class.getName(),
				primaryKey, actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBMessage.class.getName(), primaryKey,
				actionId);
		}
	}

	private static MBMessage _getMBMessage(long classPK)
		throws PortalException {

		MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(classPK);

		if (mbThread == null) {
			return MBMessageLocalServiceUtil.getMessage(classPK);
		}

		return MBMessageLocalServiceUtil.getMessage(
			mbThread.getRootMessageId());
	}

}