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

package com.liferay.blogs.service.permission;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 1.2.0, with no direct replacement
 */
@Component(
	property = {"model.class.name=com.liferay.blogs.model.BlogsEntry"},
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class BlogsEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, BlogsEntry entry,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(permissionChecker, entry, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, entryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, BlogsEntry entry,
			String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<BlogsEntry> modelResourcePermission) {

		_entryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<BlogsEntry>
		_entryModelResourcePermission;

}