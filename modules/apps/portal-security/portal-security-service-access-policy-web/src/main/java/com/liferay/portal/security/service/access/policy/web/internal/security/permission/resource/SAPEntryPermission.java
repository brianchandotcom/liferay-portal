/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

/**
 * @author Preston Crary
 */
public class SAPEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long sapEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<SAPEntry> modelResourcePermission =
			_sapEntryFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, sapEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<SAPEntry> modelResourcePermission =
			_sapEntryFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, sapEntry, actionId);
	}

	private static final Snapshot<ModelResourcePermission<SAPEntry>>
		_sapEntryFolderModelResourcePermissionSnapshot = new Snapshot<>(
			SAPEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.security.service.access." +
				"policy.model.SAPEntry)");

}