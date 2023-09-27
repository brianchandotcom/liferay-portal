/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.roles.admin.group.type.contributor;

import com.liferay.depot.model.DepotEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long depotEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DepotEntry> modelResourcePermission =
			_depotEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, depotEntryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DepotEntry>>
		_depotEntryModelResourcePermissionSnapshot = new Snapshot<>(
			DepotEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.depot.model.DepotEntry)");

}