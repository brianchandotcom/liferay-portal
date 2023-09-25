/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistryUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
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
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = DataRecordCollectionModelResourcePermission.class
)
public class DataRecordCollectionModelResourcePermission {

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

	public boolean contains(
			PermissionChecker permissionChecker, DDLRecordSet ddlRecordSet,
			String actionId)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataDefinitionContentType dataDefinitionContentType =
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		if (dataDefinitionContentType == null) {
			return false;
		}

		return dataDefinitionContentType.hasPermission(
			permissionChecker, ddlRecordSet.getCompanyId(),
			ddlRecordSet.getGroupId(), _getModelResourceName(ddlRecordSet),
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getUserId(), actionId);
	}

	private String _getModelResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	@Reference
	private Portal _portal;

}