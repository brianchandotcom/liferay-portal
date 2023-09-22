/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistryUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = DataDefinitionModelResourcePermission.class
)
public class DataDefinitionModelResourcePermission {

	public void check(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddmStructure, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getModelResourceName(ddmStructure),
				ddmStructure.getStructureId(), actionId);
		}
	}

	public void checkPortletPermission(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		_checkPortletPermission(
			permissionChecker,
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				ddmStructure.getClassNameId()),
			ddmStructure.getGroupId(), actionId);
	}

	public void checkPortletPermission(
			PermissionChecker permissionChecker, String contentType,
			long groupId, String actionId)
		throws Exception {

		_checkPortletPermission(
			permissionChecker,
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				contentType),
			groupId, actionId);
	}

	public boolean contains(
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

	private void _checkPortletPermission(
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

	private String _getModelResourceName(DDMStructure ddmStructure) {
		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

	@Reference
	private Portal _portal;

}