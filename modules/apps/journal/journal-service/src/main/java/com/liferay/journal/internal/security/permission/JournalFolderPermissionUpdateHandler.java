/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.security.permission;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFolder",
	service = PermissionUpdateHandler.class
)
public class JournalFolderPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		JournalFolder folder = _journalFolderLocalService.fetchJournalFolder(
			GetterUtil.getLong(primKey));

		if (folder == null) {
			return;
		}

		folder.setModifiedDate(new Date());

		_journalFolderLocalService.updateJournalFolder(folder);
	}

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

}