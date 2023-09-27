/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.security.permission.resource;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotEntry",
	service = ModelResourcePermission.class
)
public class DepotEntryModelResourcePermission
	implements ModelResourcePermission<DepotEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, DepotEntry depotEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, depotEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DepotEntry.class.getName(),
				depotEntry.getDepotEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long depotEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, depotEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DepotEntry.class.getName(), depotEntryId,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, DepotEntry depotEntry,
		String actionId) {

		return _contains(permissionChecker, depotEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long depotEntryId,
			String actionId)
		throws PortalException {

		return _contains(
			permissionChecker,
			_depotEntryLocalService.getDepotEntry(depotEntryId), actionId);
	}

	@Override
	public String getModelName() {
		return DepotEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, DepotEntry depotEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				depotEntry.getCompanyId(), DepotEntry.class.getName(),
				depotEntry.getDepotEntryId(), depotEntry.getUserId(),
				actionId) ||
			(permissionChecker.isGroupMember(depotEntry.getGroupId()) &&
			 actionId.equals(ActionKeys.VIEW))) {

			return true;
		}

		return permissionChecker.hasPermission(
			depotEntry.getGroupId(), DepotEntry.class.getName(),
			depotEntry.getDepotEntryId(), actionId);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(target = "(resource.name=" + DepotConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}