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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintEntryPermission.class)
public class BlueprintEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, Blueprint entry,
			String actionKey)
		throws PortalException {

		return _blueprintEntryModelResourcePermission.contains(
			permissionChecker, entry, actionKey);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionKey)
		throws PortalException {

		return _blueprintEntryModelResourcePermission.contains(
			permissionChecker, entryId, actionKey);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.search.tuning.blueprints.model.Blueprint)",
		unbind = "-"
	)
	protected void setEntryModelPermission(
		ModelResourcePermission<Blueprint> modelResourcePermission) {

		_blueprintEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Blueprint>
		_blueprintEntryModelResourcePermission;

}