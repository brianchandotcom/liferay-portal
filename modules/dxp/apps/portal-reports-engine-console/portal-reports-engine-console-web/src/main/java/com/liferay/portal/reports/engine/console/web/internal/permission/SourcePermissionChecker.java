/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Source;

/**
 * @author Leon Chi
 */
public class SourcePermissionChecker {

	public static boolean contains(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		ModelResourcePermission<Source> modelResourcePermission =
			_sourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, sourceId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Source source, String actionId)
		throws PortalException {

		ModelResourcePermission<Source> modelResourcePermission =
			_sourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, source, actionId);
	}

	private static final Snapshot<ModelResourcePermission<Source>>
		_sourceModelResourcePermissionSnapshot = new Snapshot<>(
			SourcePermissionChecker.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.reports.engine.console." +
				"model.Source)");

}