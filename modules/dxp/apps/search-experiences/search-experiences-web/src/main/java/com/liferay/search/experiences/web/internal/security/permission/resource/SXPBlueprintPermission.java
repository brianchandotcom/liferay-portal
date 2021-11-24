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

package com.liferay.search.experiences.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.search.experiences.model.SXPBlueprint;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true, service = SXPBlueprintPermission.class
)
public class SXPBlueprintPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionKey)
		throws PortalException {

		return _sxpBlueprintModelResourcePermission.contains(
			permissionChecker, entryId, actionKey);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SXPBlueprint entry,
			String actionKey)
		throws PortalException {

		return _sxpBlueprintModelResourcePermission.contains(
			permissionChecker, entry, actionKey);
	}

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPBlueprint)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<SXPBlueprint> modelResourcePermission) {

		_sxpBlueprintModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<SXPBlueprint>
		_sxpBlueprintModelResourcePermission;

}