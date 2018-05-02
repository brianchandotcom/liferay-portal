/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.internal.security.permission;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = PermissionUpdateHandler.class
)
public class DLFileEntryTypePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DLFileEntryType dlFileEntryType =
			_dLFileEntryTypeLocalService.fetchDLFileEntryType(
				GetterUtil.getLong(primKey));

		if (dlFileEntryType == null) {
			return;
		}

		dlFileEntryType.setModifiedDate(new Date());

		_dLFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dLFileEntryTypeLocalService) {

		_dLFileEntryTypeLocalService = dLFileEntryTypeLocalService;
	}

	private DLFileEntryTypeLocalService _dLFileEntryTypeLocalService;

}