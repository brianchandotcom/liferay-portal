/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistry;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
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
public class DataDefinitionModelResourcePermission
	implements ModelResourcePermission<DDMStructure> {

	@Override
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

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_ddmStructureLocalService.getDDMStructure(primaryKey), actionId);
	}

	public void checkPortletPermission(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		checkPortletPermission(
			permissionChecker,
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId()),
			ddmStructure.getGroupId(), actionId);
	}

	public void checkPortletPermission(
			PermissionChecker permissionChecker, String contentType,
			long groupId, String actionId)
		throws Exception {

		checkPortletPermission(
			permissionChecker,
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				contentType),
			groupId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		if (dataDefinitionContentType == null) {
			return false;
		}

		return dataDefinitionContentType.hasPermission(
			permissionChecker, ddmStructure.getCompanyId(),
			ddmStructure.getGroupId(), _getModelResourceName(ddmStructure),
			ddmStructure.getStructureId(), ddmStructure.getUserId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_ddmStructureLocalService.getDDMStructure(primaryKey), actionId);
	}

	@Override
	public String getModelName() {
		return DDMStructure.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	protected void checkPortletPermission(
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
			getModelName());
	}

	@Reference
	private DataDefinitionContentTypeRegistry
		_dataDefinitionContentTypeRegistry;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}