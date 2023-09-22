/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource.util;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistryUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmStructure, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getModelResourceName(ddmStructure),
				ddmStructure.getStructureId(), actionId);
		}
	}

	public static void checkPortletPermission(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		_checkPortletPermission(
			permissionChecker,
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				ddmStructure.getClassNameId()),
			ddmStructure.getGroupId(), actionId);
	}

	public static void checkPortletPermission(
			PermissionChecker permissionChecker, String contentType,
			long groupId, String actionId)
		throws Exception {

		_checkPortletPermission(
			permissionChecker,
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				contentType),
			groupId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		DataDefinitionContentType dataDefinitionContentType =
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		if (dataDefinitionContentType == null) {
			return false;
		}

		return dataDefinitionContentType.hasPermission(
			permissionChecker, ddmStructure.getCompanyId(),
			ddmStructure.getGroupId(), _getModelResourceName(ddmStructure),
			ddmStructure.getStructureId(), ddmStructure.getUserId(), actionId);
	}

	private static void _checkPortletPermission(
			PermissionChecker permissionChecker,
			DataDefinitionContentType dataDefinitionContentType, long groupId,
			String actionId)
		throws PortalException {

		if (dataDefinitionContentType == null) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, actionId);
		}

		if (!dataDefinitionContentType.hasPortletPermission(
				permissionChecker, groupId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, dataDefinitionContentType.getContentType(),
				groupId, actionId);
		}
	}

	private static String _getModelResourceName(DDMStructure ddmStructure) {
		return ResourceActionsUtil.getCompositeModelName(
			PortalUtil.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

}