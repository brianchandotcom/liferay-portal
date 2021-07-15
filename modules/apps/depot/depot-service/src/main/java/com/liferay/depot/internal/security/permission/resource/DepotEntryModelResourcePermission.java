/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	immediate = true,
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