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

package com.liferay.portal.reports.engine.console.internal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Michael C. Han
 * @author     Gavin Wan
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Source",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class SourcePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		_sourceModelResourcePermission.check(
			permissionChecker, sourceId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Source source, String actionId)
		throws PortalException {

		_sourceModelResourcePermission.check(
			permissionChecker, source, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		return _sourceModelResourcePermission.contains(
			permissionChecker, sourceId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Source source, String actionId)
		throws PortalException {

		return _sourceModelResourcePermission.contains(
			permissionChecker, source, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_sourceModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.reports.engine.console.model.Source)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<Source> modelResourcePermission) {

		_sourceModelResourcePermission = modelResourcePermission;
	}

	protected void setSourceLocalService(
		SourceLocalService sourceLocalService) {
	}

	private static ModelResourcePermission<Source>
		_sourceModelResourcePermission;

}