/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = TempAssetCategoryAttachmentsUploadFileEntryHandler.class)
public class TempAssetCategoryAttachmentsUploadFileEntryHandler
	extends AssetCategoryAttachmentsUploadFileEntryHandler {

	@Override
	protected FileEntry addFileEntry(
			long categoryId, String fileName, String contentType,
			InputStream inputStream, ThemeDisplay themeDisplay)
		throws PortalException {

		String uniqueFileName = _uniqueFileNameProvider.provide(
			fileName, curFileName -> _exists(themeDisplay, curFileName));

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			_TEMP_FOLDER_NAME, uniqueFileName, inputStream, contentType);
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			if (TempFileEntryUtil.getTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					_TEMP_FOLDER_NAME, curFileName) != null) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	private static final String _TEMP_FOLDER_NAME =
		TempAssetCategoryAttachmentsUploadFileEntryHandler.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		TempAssetCategoryAttachmentsUploadFileEntryHandler.class);

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}