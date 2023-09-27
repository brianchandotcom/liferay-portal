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
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.search.experiences.model.SXPElement",
	service = ModelResourcePermission.class
)
public class SXPElementModelResourcePermission
	implements ModelResourcePermission<SXPElement> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long sxpBlueprintId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sxpBlueprintId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SXPElement.class.getName(), sxpBlueprintId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, SXPElement sxpBlueprint,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sxpBlueprint, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SXPElement.class.getName(),
				sxpBlueprint.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long sxpBlueprintId,
			String actionId)
		throws PortalException {

		SXPElement sxpBlueprint = _sxpBlueprintLocalService.getSXPElement(
			sxpBlueprintId);

		return contains(permissionChecker, sxpBlueprint, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, SXPElement sxpBlueprint,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), SXPElement.class.getName(),
				sxpBlueprint.getSXPElementId(), sxpBlueprint.getUserId(),
				actionId) ||
			(permissionChecker.getUserId() == sxpBlueprint.getUserId()) ||
			permissionChecker.hasPermission(
				null, SXPElement.class.getName(), sxpBlueprint.getPrimaryKey(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return SXPElement.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SXPElementLocalService _sxpBlueprintLocalService;

}