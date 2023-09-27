/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileVersionPreviewEventListenerImpl.class);

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

}