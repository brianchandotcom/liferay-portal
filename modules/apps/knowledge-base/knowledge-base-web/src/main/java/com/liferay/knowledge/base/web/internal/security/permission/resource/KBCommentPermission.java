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

package com.liferay.knowledge.base.web.internal.security.permission.resource;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class KBCommentPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, KBComment kbComment,
			String actionId)
		throws PortalException {

		ModelResourcePermission<KBComment> modelResourcePermission =
			_kbCommentModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, kbComment, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kbCommentId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<KBComment> modelResourcePermission =
			_kbCommentModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, kbCommentId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<KBComment>>
		_kbCommentModelResourcePermissionSnapshot = new Snapshot<>(
			KBCommentPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.knowledge.base.model.KBComment)");

}