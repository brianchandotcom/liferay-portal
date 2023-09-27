/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistry;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
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
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = DataRecordCollectionModelResourcePermission.class
)
public class DataRecordCollectionModelResourcePermission
	implements ModelResourcePermission<DDLRecordSet> {

	@Override
	public void check(
			PermissionChecker permissionChecker, DDLRecordSet ddlRecordSet,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddlRecordSet, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getModelResourceName(ddlRecordSet),
				ddlRecordSet.getRecordSetId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_ddlRecordSetLocalService.getDDLRecordSet(primaryKey), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, DDLRecordSet ddlRecordSet,
			String actionId)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		if (dataDefinitionContentType == null) {
			return false;
		}

		return dataDefinitionContentType.hasPermission(
			permissionChecker, ddlRecordSet.getCompanyId(),
			ddlRecordSet.getGroupId(), _getModelResourceName(ddlRecordSet),
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getUserId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_ddlRecordSetLocalService.getDDLRecordSet(primaryKey), actionId);
	}

	@Override
	public String getModelName() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	private String _getModelResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	@Reference
	private DataDefinitionContentTypeRegistry
		_dataDefinitionContentTypeRegistry;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private Portal _portal;

}