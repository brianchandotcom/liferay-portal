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

package com.liferay.search.experiences.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = ModelResourcePermission.class
)
public class SXPBlueprintModelResourcePermission
	implements ModelResourcePermission<SXPBlueprint> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long sxpBlueprintId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sxpBlueprintId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SXPBlueprint.class.getName(), sxpBlueprintId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, SXPBlueprint sxpBlueprint,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sxpBlueprint, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SXPBlueprint.class.getName(),
				sxpBlueprint.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long sxpBlueprintId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_sxpBlueprintLocalService.getSXPBlueprint(sxpBlueprintId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, SXPBlueprint sxpBlueprint,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), SXPBlueprint.class.getName(),
				sxpBlueprint.getSXPBlueprintId(), sxpBlueprint.getUserId(),
				actionId) ||
			(permissionChecker.getUserId() == sxpBlueprint.getUserId()) ||
			permissionChecker.hasPermission(
				null, SXPBlueprint.class.getName(),
				sxpBlueprint.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return SXPBlueprint.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}