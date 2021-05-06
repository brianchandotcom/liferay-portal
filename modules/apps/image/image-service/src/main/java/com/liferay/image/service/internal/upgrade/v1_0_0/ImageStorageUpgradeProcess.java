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

package com.liferay.image.service.internal.upgrade.v1_0_0;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class ImageStorageUpgradeProcess extends UpgradeProcess {

	public ImageStorageUpgradeProcess(
		ImageLocalService imageLocalService, StoreFactory storeFactory) {

		_imageLocalService = imageLocalService;
		_storeFactory = storeFactory;
	}

	@Override
	protected void doUpgrade() throws PortalException {
		Store store = _storeFactory.getStore();

		ActionableDynamicQuery actionableDynamicQuery =
			_imageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Image image) -> {
				String fileName = _getFileName(image);

				try (InputStream inputStream = store.getFileAsStream(
						CompanyConstants.SYSTEM, _DEFAULT_REPOSITORY_ID,
						fileName, StringPool.BLANK)) {

					store.addFile(
						image.getCompanyId(), _DEFAULT_REPOSITORY_ID, fileName,
						Store.VERSION_DEFAULT, inputStream);

					store.deleteFile(
						CompanyConstants.SYSTEM, _DEFAULT_REPOSITORY_ID,
						fileName, Store.VERSION_DEFAULT);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private String _getFileName(Image image) {
		return image.getImageId() + StringPool.PERIOD + image.getType();
	}

	private static final long _DEFAULT_REPOSITORY_ID = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageStorageUpgradeProcess.class);

	private final ImageLocalService _imageLocalService;
	private final StoreFactory _storeFactory;

}