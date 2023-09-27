/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class DLFileEntryTypePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileEntryType fileEntryType,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DLFileEntryType> modelResourcePermission =
			_dlFileEntryTypeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, fileEntryType, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileEntryTypeId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DLFileEntryType> modelResourcePermission =
			_dlFileEntryTypeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, fileEntryTypeId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DLFileEntryType>>
		_dlFileEntryTypeModelResourcePermissionSnapshot = new Snapshot<>(
			DLFileEntryTypePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.document.library.kernel.model." +
				"DLFileEntryType)");

}