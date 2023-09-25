/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource.util;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistryUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Leonardo Barros
 */
public class DataRecordCollectionPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, DDLRecordSet ddlRecordSet,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddlRecordSet, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getModelResourceName(ddlRecordSet),
				ddlRecordSet.getRecordSetId(), actionId);
		}
	}

	public static boolean contains(
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

	private static String _getModelResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			PortalUtil.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

}