/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.convert.document.library;

import com.liferay.adaptive.media.image.internal.storage.AMStoreUtil;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.util.MaintenanceUtil;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class AMDLStoreConvertProcess implements DLStoreConvertProcess {

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

		int count = _amImageEntryLocalService.getAMImageEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating images in " + count + " adaptive media image entries");

		ActionableDynamicQuery actionableDynamicQuery =
			_amImageEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(AMImageEntry amImageEntry) -> {
				String fileVersionPath = AMStoreUtil.getFileVersionPath(
					_dlAppService.getFileVersion(
						amImageEntry.getFileVersionId()),
					amImageEntry.getConfigurationUuid());

				for (String versionLabel :
						sourceStore.getFileVersions(
							amImageEntry.getCompanyId(),
							CompanyConstants.SYSTEM, fileVersionPath)) {

					try (InputStream inputStream = sourceStore.getFileAsStream(
							amImageEntry.getCompanyId(),
							CompanyConstants.SYSTEM, fileVersionPath,
							versionLabel)) {

						targetStore.addFile(
							amImageEntry.getCompanyId(),
							CompanyConstants.SYSTEM, fileVersionPath,
							versionLabel, inputStream);

						if (delete) {
							sourceStore.deleteFile(
								amImageEntry.getCompanyId(),
								CompanyConstants.SYSTEM, fileVersionPath,
								versionLabel);
						}
					}
					catch (IOException ioException) {
						throw new PortalException(ioException);
					}
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private DLAppService _dlAppService;

}