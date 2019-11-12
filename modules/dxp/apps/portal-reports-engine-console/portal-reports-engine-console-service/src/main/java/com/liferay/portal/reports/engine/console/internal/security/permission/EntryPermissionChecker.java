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
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Gavin Wan
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Entry",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class EntryPermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, Entry entry, String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(permissionChecker, entry, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, entryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Entry entry, String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, entry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	protected void setEntryLocalService(EntryLocalService entryLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.reports.engine.console.model.Entry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<Entry> modelResourcePermission) {

		_entryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Entry> _entryModelResourcePermission;

}