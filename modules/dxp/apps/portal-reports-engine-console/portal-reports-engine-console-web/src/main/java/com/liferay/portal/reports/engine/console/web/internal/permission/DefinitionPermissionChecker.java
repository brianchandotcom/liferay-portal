/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Definition;

/**
 * @author Leon Chi
 */
public class DefinitionPermissionChecker {

	public static boolean contains(
			PermissionChecker permissionChecker, Definition definition,
			String actionId)
		throws PortalException {

		ModelResourcePermission<Definition> modelResourcePermission =
			_definitionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, definition, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long definitionId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<Definition> modelResourcePermission =
			_definitionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, definitionId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<Definition>>
		_definitionModelResourcePermissionSnapshot = new Snapshot<>(
			DefinitionPermissionChecker.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.reports.engine.console." +
				"model.Definition)");

}