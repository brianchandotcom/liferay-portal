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

package com.liferay.portal.reports.engine.console.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Gavin Wan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Source",
	service = BaseModelPermissionChecker.class
)
public class SourcePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sourceId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Source source, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, source, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		Source source = _sourceLocalService.getSource(sourceId);

		return contains(permissionChecker, source, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, Source source, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				source.getCompanyId(), Source.class.getName(),
				source.getSourceId(), source.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			source.getGroupId(), Source.class.getName(), source.getSourceId(),
			actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setSourceLocalService(
		SourceLocalService sourceLocalService) {

		_sourceLocalService = sourceLocalService;
	}

	private static SourceLocalService _sourceLocalService;

}