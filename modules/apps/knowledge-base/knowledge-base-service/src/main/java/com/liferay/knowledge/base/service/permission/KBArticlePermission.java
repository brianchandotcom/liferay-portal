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

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Peter Shin
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class KBArticlePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws PortalException {

		_kbArticleModelResourcePermission.check(
			permissionChecker, kbArticle, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		_kbArticleModelResourcePermission.check(
			permissionChecker, classPK, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws PortalException {

		return _kbArticleModelResourcePermission.contains(
			permissionChecker, kbArticle, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return _kbArticleModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_kbArticleModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<KBArticle> modelResourcePermission) {

		_kbArticleModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

}