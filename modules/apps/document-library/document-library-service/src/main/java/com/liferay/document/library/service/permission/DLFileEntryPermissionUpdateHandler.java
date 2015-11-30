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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.documentlibrary.model.DLFileEntry"},
	service = PermissionUpdateHandler.class
)
public class DLFileEntryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DLFileEntry dlFileEntry = _dLFileEntryLocalService.fetchDLFileEntry(
			GetterUtil.getLong(primKey));

		if (dlFileEntry == null) {
			return;
		}

		dlFileEntry.setModifiedDate(new Date());

		_dLFileEntryLocalService.updateDLFileEntry(dlFileEntry);
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dLFileEntryLocalService) {

		_dLFileEntryLocalService = dLFileEntryLocalService;
	}

	private volatile DLFileEntryLocalService _dLFileEntryLocalService;

}