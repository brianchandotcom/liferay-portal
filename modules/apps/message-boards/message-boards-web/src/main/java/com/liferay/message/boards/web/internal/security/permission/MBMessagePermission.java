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