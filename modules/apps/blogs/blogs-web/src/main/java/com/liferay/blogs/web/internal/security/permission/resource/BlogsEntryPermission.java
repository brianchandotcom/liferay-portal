/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.security.permission.resource;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class BlogsEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, BlogsEntry entry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<BlogsEntry> blogsEntryModelResourcePermission =
			_blogsEntryModelResourcePermissionSnapshot.get();

		return blogsEntryModelResourcePermission.contains(
			permissionChecker, entry, actionId);
	}

	private static final Snapshot<ModelResourcePermission<BlogsEntry>>
		_blogsEntryModelResourcePermissionSnapshot = new Snapshot<>(
			BlogsEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.blogs.model.BlogsEntry)");

}