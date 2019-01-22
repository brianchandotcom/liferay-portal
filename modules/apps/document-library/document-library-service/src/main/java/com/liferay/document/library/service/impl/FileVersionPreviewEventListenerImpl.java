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

package com.liferay.document.library.service.impl;

import com.liferay.document.library.constants.FileVersionPreviewConstants;
import com.liferay.document.library.kernel.service.FileVersionPreviewEventListener;
import com.liferay.document.library.model.FileVersionPreview;
import com.liferay.document.library.service.FileVersionPreviewLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = FileVersionPreviewEventListener.class)
public class FileVersionPreviewEventListenerImpl
	implements FileVersionPreviewEventListener {

	@Override
	public void onFailure(FileVersion fileVersion) {
		_addDLFileEntryPreview(
			fileVersion, FileVersionPreviewConstants.FAILURE);
	}

	@Override
	public void onSuccess(FileVersion fileVersion) {
		_addDLFileEntryPreview(
			fileVersion, FileVersionPreviewConstants.SUCCESS);
	}

	private void _addDLFileEntryPreview(
		FileVersion fileVersion, int previewType) {

		try {
			FileVersionPreview fileVersionPreview =
				_fileVersionPreviewLocalService.fetchFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId());

			if (fileVersionPreview == null) {
				_fileVersionPreviewLocalService.addFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId(), previewType);
			}
			else {
				_fileVersionPreviewLocalService.updateFileVersionPreview(
					fileVersionPreview.getFileVersionPreviewId(), previewType);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileVersionPreviewEventListenerImpl.class);

	@Reference
	private FileVersionPreviewLocalService _fileVersionPreviewLocalService;

}