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

package com.liferay.document.library.internal.repository.event;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.model.DLFileVersionPreview;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.event.FileVersionPreviewEventListener;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
@Component(service = FileVersionPreviewEventListener.class)
public class FileVersionPreviewEventListenerImpl
	implements FileVersionPreviewEventListener {

	@Override
	public void onFailure(FileVersion fileVersion) {
		_addDLFileEntryPreview(
			fileVersion, DLFileVersionPreviewConstants.STATUS_FAILURE);
	}

	@Override
	public void onSuccess(FileVersion fileVersion) {
		_addDLFileEntryPreview(
			fileVersion, DLFileVersionPreviewConstants.STATUS_SUCCESS);
	}

	private void _addDLFileEntryPreview(
		FileVersion fileVersion, int previewStatus) {

		try {
			DLFileVersionPreview dlFileVersionPreview =
				_dlFileVersionPreviewLocalService.fetchDLFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId());

			if (dlFileVersionPreview == null) {
				_dlFileVersionPreviewLocalService.addDLFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId(), previewStatus);
			}
			else {
				_dlFileVersionPreviewLocalService.updateDLFileVersionPreview(
					dlFileVersionPreview.getDlFileVersionPreviewId(),
					previewStatus);
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
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

}