/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.internal.upgrade.v1_0_0;

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

import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class ImageStorageUpgradeProcess extends UpgradeProcess {

	public ImageStorageUpgradeProcess(
		ImageLocalService imageLocalService, Store store) {

		_imageLocalService = imageLocalService;
		_store = store;
	}

	@Override
	protected void doUpgrade() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_imageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Image image) -> {
				String fileName = _getFileName(image);

				if (!_store.hasFile(
						CompanyConstants.SYSTEM, _REPOSITORY_ID, fileName,
						StringPool.BLANK)) {

					return;
				}

				try (InputStream inputStream = _store.getFileAsStream(
						CompanyConstants.SYSTEM, _REPOSITORY_ID, fileName,
						StringPool.BLANK)) {

					_store.addFile(
						image.getCompanyId(), _REPOSITORY_ID, fileName,
						Store.VERSION_DEFAULT, inputStream);

					_store.deleteFile(
						CompanyConstants.SYSTEM, _REPOSITORY_ID, fileName,
						Store.VERSION_DEFAULT);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private String _getFileName(Image image) {
		return image.getImageId() + StringPool.PERIOD + image.getType();
	}

	private static final long _REPOSITORY_ID = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageStorageUpgradeProcess.class);

	private final ImageLocalService _imageLocalService;
	private final Store _store;

}