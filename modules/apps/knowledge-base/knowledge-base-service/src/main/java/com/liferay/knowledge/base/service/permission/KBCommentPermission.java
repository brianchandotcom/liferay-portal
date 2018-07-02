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

package com.liferay.knowledge.base.service.permission;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Shinn Lok
 * @author     Roberto Díaz
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBComment",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class KBCommentPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, KBComment kbComment,
			String actionId)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			permissionChecker, kbComment, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long kbCommentId,
			String actionId)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			permissionChecker, kbCommentId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, KBComment kbComment,
			String actionId)
		throws PortalException {

		return _kbCommentModelResourcePermission.contains(
			permissionChecker, kbComment, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kbCommentId,
			String actionId)
		throws PortalException {

		return _kbCommentModelResourcePermission.contains(
			permissionChecker, kbCommentId, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBComment)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<KBComment> modelResourcePermission) {

		_kbCommentModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<KBComment>
		_kbCommentModelResourcePermission;

}