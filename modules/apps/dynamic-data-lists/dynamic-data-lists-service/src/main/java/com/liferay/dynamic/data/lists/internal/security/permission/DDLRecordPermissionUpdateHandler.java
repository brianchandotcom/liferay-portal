/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.security.permission;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = PermissionUpdateHandler.class
)
public class DDLRecordPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DDLRecord ddlRecord = _ddlRecordLocalService.fetchDDLRecord(
			GetterUtil.getLong(primKey));

		if (ddlRecord == null) {
			return;
		}

		ddlRecord.setModifiedDate(new Date());

		_ddlRecordLocalService.updateDDLRecord(ddlRecord);
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

}