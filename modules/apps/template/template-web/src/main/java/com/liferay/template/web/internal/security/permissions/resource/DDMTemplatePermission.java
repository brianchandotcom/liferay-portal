/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.security.permissions.resource;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Lourdes Fernández Besada
 */
public class DDMTemplatePermission {

	public static void check(
			PermissionChecker permissionChecker, long ddmTemplateId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMTemplate> modelResourcePermission =
			_ddmTemplateModelResourcePermissionSnapshot.get();

		modelResourcePermission.check(
			permissionChecker, ddmTemplateId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMTemplate ddmTemplate,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMTemplate> modelResourcePermission =
			_ddmTemplateModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmTemplate, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ddmTemplateId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMTemplate> modelResourcePermission =
			_ddmTemplateModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmTemplateId, actionId);
	}

	public static boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException {

		DDMPermissionSupport ddmPermissionSupport =
			_ddmPermissionSupportSnapshot.get();

		return ddmPermissionSupport.containsAddTemplatePermission(
			permissionChecker, groupId, classNameId, resourceClassNameId);
	}

	private static final Snapshot<DDMPermissionSupport>
		_ddmPermissionSupportSnapshot = new Snapshot<>(
			DDMTemplatePermission.class, DDMPermissionSupport.class);
	private static final Snapshot<ModelResourcePermission<DDMTemplate>>
		_ddmTemplateModelResourcePermissionSnapshot = new Snapshot<>(
			DDMTemplatePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dynamic.data.mapping.model." +
				"DDMTemplate)");

}