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

package com.liferay.sync.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.sync.model.SyncDLFileVersionDiff;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class SyncDLFileVersionDiffsDLStoreConvertProcess
	implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count =
			_syncDLFileVersionDiffLocalService.getSyncDLFileVersionDiffsCount();

		MaintenanceUtil.appendStatus(
			"Migrating files in " + count + " syncDlFileVersionDiffs");

		ActionableDynamicQuery actionableDynamicQuery =
			_syncDLFileVersionDiffLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(SyncDLFileVersionDiff syncDLFileVersionDiff) -> {
				long dataFileEntryId =
					syncDLFileVersionDiff.getDataFileEntryId();

				if (dataFileEntryId == 0) {
					return;
				}

				FileEntry fileEntry =
					PortletFileRepositoryUtil.getPortletFileEntry(
						dataFileEntryId);

				dlStoreConverter.migrateDLFileEntry(
					fileEntry.getCompanyId(),
					DLFolderConstants.getDataRepositoryId(
						fileEntry.getRepositoryId(), fileEntry.getFolderId()),
					fileEntry);
			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private SyncDLFileVersionDiffLocalService
		_syncDLFileVersionDiffLocalService;

}