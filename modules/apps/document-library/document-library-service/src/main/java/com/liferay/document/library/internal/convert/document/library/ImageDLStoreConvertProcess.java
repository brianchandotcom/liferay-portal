/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.convert.document.library;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLStoreConvertProcess.class)
public class ImageDLStoreConvertProcess implements DLStoreConvertProcess {

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

		int count = _imageLocalService.getImagesCount();

		MaintenanceUtil.appendStatus("Migrating " + count + " images");

		ActionableDynamicQuery actionableDynamicQuery =
			_imageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Image image) -> {
				String fileName =
					image.getImageId() + StringPool.PERIOD + image.getType();

				try {
					transferFile(
						sourceStore, targetStore, image.getCompanyId(),
						_REPOSITORY_ID, fileName, Store.VERSION_DEFAULT,
						delete);
				}
				catch (Exception exception) {
					_log.error("Unable to migrate " + fileName, exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final long _REPOSITORY_ID = 0L;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageDLStoreConvertProcess.class);

	@Reference
	private ImageLocalService _imageLocalService;

}