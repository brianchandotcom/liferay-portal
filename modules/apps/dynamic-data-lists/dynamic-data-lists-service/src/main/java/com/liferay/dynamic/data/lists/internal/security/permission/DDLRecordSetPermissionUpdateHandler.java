/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.security.permission;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = PermissionUpdateHandler.class
)
public class DDLRecordSetPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.fetchDDLRecordSet(
			GetterUtil.getLong(primKey));

		if (ddlRecordSet == null) {
			return;
		}

		ddlRecordSet.setModifiedDate(new Date());

		_ddlRecordSetLocalService.updateDDLRecordSet(ddlRecordSet);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

}