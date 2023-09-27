/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.web.internal.security.permission.resource;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class BookmarksEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksEntry entry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<BookmarksEntry> modelResourcePermission =
			_bookmarksEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		ModelResourcePermission<BookmarksEntry> modelResourcePermission =
			_bookmarksEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<BookmarksEntry>>
		_bookmarksEntryModelResourcePermissionSnapshot = new Snapshot<>(
			BookmarksEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.bookmarks.model.BookmarksEntry)");

}