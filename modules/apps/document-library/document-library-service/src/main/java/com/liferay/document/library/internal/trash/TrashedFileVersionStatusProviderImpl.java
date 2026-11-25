/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.trash;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.trash.TrashedFileVersionStatusProvider;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wulbern
 */
@Component(service = TrashedFileVersionStatusProvider.class)
public class TrashedFileVersionStatusProviderImpl
	implements TrashedFileVersionStatusProvider {

	@Override
	public int getOriginalStatus(FileVersion fileVersion) {
		TrashEntry trashEntry = _trashEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), fileVersion.getFileEntryId());

		if (trashEntry != null) {
			for (TrashVersion trashVersion :
					_trashVersionLocalService.getVersions(
						trashEntry.getEntryId())) {

				if (trashVersion.getClassPK() ==
						fileVersion.getFileVersionId()) {

					return trashVersion.getStatus();
				}
			}
		}

		TrashVersion trashVersion = _trashVersionLocalService.fetchVersion(
			DLFileVersion.class.getName(), fileVersion.getFileVersionId());

		if (trashVersion == null) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return trashVersion.getStatus();
	}

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}
