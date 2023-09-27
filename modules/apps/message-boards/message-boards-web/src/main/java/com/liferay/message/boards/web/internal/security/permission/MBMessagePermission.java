/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.security.permission;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Sergio González
 */
public class MBMessagePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long messageId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MBMessage> modelResourcePermission =
			_messageModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, messageId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MBMessage> modelResourcePermission =
			_messageModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, message, actionId);
	}

	private static final Snapshot<ModelResourcePermission<MBMessage>>
		_messageModelResourcePermissionSnapshot = new Snapshot<>(
			MBMessagePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.message.boards.model.MBMessage)");

}