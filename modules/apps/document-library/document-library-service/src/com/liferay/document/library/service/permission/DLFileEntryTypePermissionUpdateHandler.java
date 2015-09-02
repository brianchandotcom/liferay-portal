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

package com.liferay.document.library.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.documentlibrary.model.DLFileEntryType"},
	service = PermissionUpdateHandler.class
)
public class DLFileEntryTypePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DLFileEntryType fileEntryType =
			_dLFileEntryTypeLocalService.fetchDLFileEntryType(
				GetterUtil.getLong(primKey));

		if (fileEntryType == null) {
			return;
		}

		fileEntryType.setModifiedDate(new Date());

		_dLFileEntryTypeLocalService.updateDLFileEntryType(fileEntryType);
	}

	@Reference
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dLFileEntryTypeLocalService) {

		_dLFileEntryTypeLocalService = dLFileEntryTypeLocalService;
	}

	private DLFileEntryTypeLocalService _dLFileEntryTypeLocalService;

}