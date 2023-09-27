/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.web.internal.security.permission.resource;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class MicroblogsEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long microblogsEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MicroblogsEntry> modelResourcePermission =
			_microblogsEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, microblogsEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			MicroblogsEntry microblogsEntry, String actionId)
		throws PortalException {

		ModelResourcePermission<MicroblogsEntry> modelResourcePermission =
			_microblogsEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, microblogsEntry, actionId);
	}

	private static final Snapshot<ModelResourcePermission<MicroblogsEntry>>
		_microblogsEntryModelResourcePermissionSnapshot = new Snapshot<>(
			MicroblogsEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.microblogs.model.MicroblogsEntry)");

}