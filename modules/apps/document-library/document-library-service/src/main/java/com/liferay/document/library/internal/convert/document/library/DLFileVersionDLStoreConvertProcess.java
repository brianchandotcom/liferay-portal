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

package com.liferay.document.library.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLStoreConvertProcess.class)
public class DLFileVersionDLStoreConvertProcess
	implements DLStoreConvertProcess {

	@Override
	public void copy(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, false);
	}

	@Override
	public void move(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, true);
	}

	private void _transfer(Store sourceStore, Store targetStore, boolean delete)
		throws PortalException {

		int count = _dlFileVersionLocalService.getDLFileVersionsCount();

		MaintenanceUtil.appendStatus(
			"Migrating " + count + " documents and media files");

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileVersionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileVersion dlFileVersion) -> {
				DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

				long repositoryId = DLFolderConstants.getDataRepositoryId(
					dlFileVersion.getRepositoryId(),
					dlFileVersion.getFolderId());

				try {
					InputStream is = sourceStore.getFileAsStream(
						dlFileVersion.getCompanyId(), repositoryId,
						dlFileEntry.getName(), dlFileVersion.getVersion());

					if (Store.VERSION_DEFAULT.equals(
							dlFileVersion.getVersion())) {

						targetStore.addFile(
							dlFileVersion.getCompanyId(), repositoryId,
							dlFileEntry.getName(), is);
					}
					else {
						targetStore.updateFile(
							dlFileVersion.getCompanyId(), repositoryId,
							dlFileEntry.getName(), dlFileVersion.getVersion(),
							is);
					}

					if (delete) {
						sourceStore.deleteFile(
							dlFileVersion.getCompanyId(), repositoryId,
							dlFileEntry.getName(), dlFileVersion.getVersion());
					}
				}
				catch (Exception e) {
					_log.error(
						"Migration failed for " + dlFileEntry.getName(), e);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionDLStoreConvertProcess.class);

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}