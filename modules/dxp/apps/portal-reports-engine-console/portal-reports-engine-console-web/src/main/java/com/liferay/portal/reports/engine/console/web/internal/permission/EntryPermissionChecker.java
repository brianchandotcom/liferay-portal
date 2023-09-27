/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Entry;

/**
 * @author Leon Chi
 */
public class EntryPermissionChecker {

	public static boolean contains(
			PermissionChecker permissionChecker, Entry entry, String actionId)
		throws PortalException {

		ModelResourcePermission<Entry> modelResourcePermission =
			_entryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		ModelResourcePermission<Entry> modelResourcePermission =
			_entryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<Entry>>
		_entryModelResourcePermissionSnapshot = new Snapshot<>(
			EntryPermissionChecker.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.reports.engine.console." +
				"model.Entry)");

}