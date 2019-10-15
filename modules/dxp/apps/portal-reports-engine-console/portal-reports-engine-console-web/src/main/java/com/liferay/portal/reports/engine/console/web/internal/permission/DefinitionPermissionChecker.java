/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Definition;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leon Chi
 */
@Component(immediate = true, service = {})
public class DefinitionPermissionChecker {

	public static boolean contains(
			PermissionChecker permissionChecker, Definition definition,
			String actionId)
		throws PortalException {

		return _definitionModelResourcePermission.contains(
			permissionChecker, definition, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long definitionId,
			String actionId)
		throws PortalException {

		return _definitionModelResourcePermission.contains(
			permissionChecker, definitionId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.reports.engine.console.model.Definition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<Definition> modelResourcePermission) {

		_definitionModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Definition>
		_definitionModelResourcePermission;

}