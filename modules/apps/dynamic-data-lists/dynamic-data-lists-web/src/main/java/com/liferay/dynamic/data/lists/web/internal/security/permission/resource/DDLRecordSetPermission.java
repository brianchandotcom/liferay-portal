/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.security.permission.resource;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class DDLRecordSetPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, DDLRecordSet recordSet,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDLRecordSet> modelResourcePermission =
			_ddlRecordSetModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, recordSet, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long recordSetId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDLRecordSet> modelResourcePermission =
			_ddlRecordSetModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, recordSetId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DDLRecordSet>>
		_ddlRecordSetModelResourcePermissionSnapshot = new Snapshot<>(
			DDLRecordSetPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dynamic.data.lists.model." +
				"DDLRecordSet)");

}