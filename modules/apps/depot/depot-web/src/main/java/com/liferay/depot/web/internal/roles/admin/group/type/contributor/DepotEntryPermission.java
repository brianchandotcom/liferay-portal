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

package com.liferay.depot.web.internal.roles.admin.group.type.contributor;

import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class DepotEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long depotEntryId,
			String actionId)
		throws PortalException {

		return _depotEntryModelResourcePermission.contains(
			permissionChecker, depotEntryId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.depot.model.DepotEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DepotEntry> modelResourcePermission) {

		_depotEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;

}