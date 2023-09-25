/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.security.permission.resource.util;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistryUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Inácio Nery
 */
public class DataRecordPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, DDLRecord ddlRecord,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ddlRecord, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getModelResourceName(ddlRecord),
				ddlRecord.getRecordId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDLRecord ddlRecord,
			String actionId)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecord.getRecordSet();

		boolean hasPermission = DataRecordCollectionPermissionUtil.contains(
			permissionChecker, recordSet, actionId);

		if (hasPermission) {
			return true;
		}

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DataDefinitionContentType dataDefinitionContentType =
			DataDefinitionContentTypeRegistryUtil.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		if (dataDefinitionContentType == null) {
			return false;
		}

		return dataDefinitionContentType.hasPermission(
			permissionChecker, ddlRecord.getCompanyId(), ddlRecord.getGroupId(),
			_getModelResourceName(ddlRecord), ddlRecord.getRecordId(),
			ddlRecord.getUserId(), actionId);
	}

	private static String _getModelResourceName(DDLRecord ddlRecord)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			PortalUtil.getClassName(ddmStructure.getClassNameId()),
			DDLRecord.class.getName());
	}

}