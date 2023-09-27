/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.security.permission;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
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

	@Reference
	private DLFileEntryLocalService _dLFileEntryLocalService;

}