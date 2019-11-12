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
						sourceStore, targetStore, 0L, 0L, fileName,
						Store.VERSION_DEFAULT, delete);
				}
				catch (Exception e) {
					_log.error("Unable to migrate " + fileName, e);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageDLStoreConvertProcess.class);

	@Reference
	private ImageLocalService _imageLocalService;

}