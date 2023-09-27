/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, long layoutUtilityPageEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutUtilityPageEntry>
			modelResourcePermission =
				_layoutUtilityPageEntryModelResourcePermissionSnapshot.get();

		modelResourcePermission.check(
			permissionChecker, layoutUtilityPageEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			LayoutUtilityPageEntry layoutUtilityPageEntry, String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutUtilityPageEntry>
			modelResourcePermission =
				_layoutUtilityPageEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, layoutUtilityPageEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutUtilityPageEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutUtilityPageEntry>
			modelResourcePermission =
				_layoutUtilityPageEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, layoutUtilityPageEntryId, actionId);
	}

	private static final Snapshot
		<ModelResourcePermission<LayoutUtilityPageEntry>>
			_layoutUtilityPageEntryModelResourcePermissionSnapshot =
				new Snapshot<>(
					LayoutUtilityPageEntryPermission.class,
					Snapshot.cast(ModelResourcePermission.class),
					"(model.class.name=com.liferay.layout.utility.page.model." +
						"LayoutUtilityPageEntry)");

}