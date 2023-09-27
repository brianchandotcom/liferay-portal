/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	enabled = false,
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