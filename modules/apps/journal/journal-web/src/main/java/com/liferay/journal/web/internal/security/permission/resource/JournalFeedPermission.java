/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.security.permission.resource;

import com.liferay.journal.model.JournalFeed;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class JournalFeedPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, JournalFeed feed,
			String actionId)
		throws PortalException {

		ModelResourcePermission<JournalFeed> modelResourcePermission =
			_journalFeedModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, feed, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long feedId, String actionId)
		throws PortalException {

		ModelResourcePermission<JournalFeed> modelResourcePermission =
			_journalFeedModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, feedId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<JournalFeed>>
		_journalFeedModelResourcePermissionSnapshot = new Snapshot<>(
			JournalFeedPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.journal.model.JournalFeed)");

}