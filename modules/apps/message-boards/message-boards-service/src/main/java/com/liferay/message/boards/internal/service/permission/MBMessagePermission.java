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

package com.liferay.message.boards.internal.service.permission;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Adolfo Pérez
 * @deprecated As of 1.0.0, with no direct replacement
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.message.boards.model.MBMessage",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class MBMessagePermission implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long messageId,
			String actionId)
		throws PortalException {

		_messageModelResourcePermission.check(
			permissionChecker, messageId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<MBMessage> modelResourcePermission) {

		_messageModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<MBMessage>
		_messageModelResourcePermission;

}