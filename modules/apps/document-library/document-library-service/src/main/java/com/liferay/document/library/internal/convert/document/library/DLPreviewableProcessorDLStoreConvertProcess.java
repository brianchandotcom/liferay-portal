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

import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLStoreConvertProcess.class)
public class DLPreviewableProcessorDLStoreConvertProcess
	implements DLStoreConvertProcess {

	@Override
	public void copy(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(
			sourceStore, targetStore, DLPreviewableProcessor.THUMBNAIL_PATH,
			false);
		_transfer(
			sourceStore, targetStore, DLPreviewableProcessor.PREVIEW_PATH,
			false);
	}

	@Override
	public void move(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(
			sourceStore, targetStore, DLPreviewableProcessor.THUMBNAIL_PATH,
			true);
		_transfer(
			sourceStore, targetStore, DLPreviewableProcessor.PREVIEW_PATH,
			true);
	}

	private void _transfer(
			Store sourceStore, Store targetStore, String path, boolean delete)
		throws PortalException {

		MaintenanceUtil.appendStatus("Migrating files from " + path);

		ActionableDynamicQuery actionableDynamicQuery =
			_companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				long companyId = company.getCompanyId();

				String[] fileNames = sourceStore.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID, path);

				for (String fileName : fileNames) {

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, StringPool.DOUBLE_SLASH, StringPool.SLASH);

					try {
						transferFile(
							sourceStore, targetStore, companyId,
							DLPreviewableProcessor.REPOSITORY_ID,
							actualFileName, Store.VERSION_DEFAULT, delete);
					}
					catch (Exception e) {
						_log.error("Unable to migrate " + fileName, e);
					}
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPreviewableProcessorDLStoreConvertProcess.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}