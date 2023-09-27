/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.security.permission;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = PermissionUpdateHandler.class
)
public class DDMStructurePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			GetterUtil.getLong(primKey));

		if (ddmStructure == null) {
			return;
		}

		ddmStructure.setModifiedDate(new Date());

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}