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

import com.liferay.document.library.kernel.service.DLFileEntryPreviewHandler;
import com.liferay.document.library.model.DLFileEntryPreview;
import com.liferay.document.library.service.DLFileEntryPreviewLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = DLFileEntryPreviewHandler.class)
public class DLFileEntryPreviewHandlerImpl
	implements DLFileEntryPreviewHandler {

	@Override
	public void addDLFileEntryPreview(
			long fileEntryId, long fileVersionId,
			DLFileEntryPreviewType fileEntryPreviewType)
		throws PortalException {

		DLFileEntryPreview dlFileEntryPreview =
			_dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(
				fileEntryId, fileVersionId);

		if (dlFileEntryPreview == null) {
			_dlFileEntryPreviewLocalService.addDLFileEntryPreview(
				fileEntryId, fileVersionId, fileEntryPreviewType.toInteger());
		}
		else {
			_dlFileEntryPreviewLocalService.updateDLFileEntryPreview(
				dlFileEntryPreview.getFileEntryPreviewId(),
				fileEntryPreviewType.toInteger());
		}
	}

	@Override
	public void deleteDLFileEntryPreviews(long fileEntryId)
		throws PortalException {

		List<DLFileEntryPreview> fileEntryPreviews =
			_dlFileEntryPreviewLocalService.getDLFileEntryPreviews(fileEntryId);

		for (DLFileEntryPreview fileEntryPreview : fileEntryPreviews) {
			_dlFileEntryPreviewLocalService.deleteDLFileEntryPreview(
				fileEntryPreview.getFileEntryPreviewId());
		}
	}

	@Override
	public long getDLFileEntryPreviewId(
		long fileEntryId, long fileVersionId,
		DLFileEntryPreviewType fileEntryPreviewType) {

		DLFileEntryPreview dlFileEntryPreview =
			_dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(
				fileEntryId, fileVersionId, fileEntryPreviewType.toInteger());

		if (dlFileEntryPreview == null) {
			return 0;
		}

		return dlFileEntryPreview.getFileEntryPreviewId();
	}

	@Reference
	private DLFileEntryPreviewLocalService _dlFileEntryPreviewLocalService;

}