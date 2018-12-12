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
				fileEntryId, fileVersionId,
				getDLFileEntryPreviewType(fileEntryPreviewType));
		}
		else {
			updateDLFileEntryPreview(
				dlFileEntryPreview.getFileEntryPreviewId(),
				fileEntryPreviewType);
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
	public long getDLFileEntryPreviewId(long fileEntryId, long fileVersionId)
		throws PortalException {

		DLFileEntryPreview dlFileEntryPreview =
			_dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(
				fileEntryId, fileVersionId);

		if (dlFileEntryPreview == null) {
			return 0;
		}

		return dlFileEntryPreview.getFileEntryPreviewId();
	}

	@Override
	public long getDLFileEntryPreviewId(
		long fileEntryId, long fileVersionId,
		DLFileEntryPreviewType fileEntryPreviewType) {

		DLFileEntryPreview dlFileEntryPreview =
			_dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(
				fileEntryId, fileVersionId,
				getDLFileEntryPreviewType(fileEntryPreviewType));

		if (dlFileEntryPreview == null) {
			return 0;
		}

		return dlFileEntryPreview.getFileEntryPreviewId();
	}

	@Override
	public void updateDLFileEntryPreview(
			long dlFileEntryPreviewId,
			DLFileEntryPreviewType fileEntryPreviewType)
		throws PortalException {

		_dlFileEntryPreviewLocalService.updateDLFileEntryPreview(
			dlFileEntryPreviewId,
			getDLFileEntryPreviewType(fileEntryPreviewType));
	}

	protected int getDLFileEntryPreviewType(
		DLFileEntryPreviewType fileEntryPreviewType) {

		if (fileEntryPreviewType == DLFileEntryPreviewType.FAIL) {
			return -1;
		}
		else if (fileEntryPreviewType == DLFileEntryPreviewType.NOT_GENERATED) {
			return 0;
		}
		else if (fileEntryPreviewType == DLFileEntryPreviewType.SUCCESS) {
			return 1;
		}

		return 0;
	}

	@Reference
	private DLFileEntryPreviewLocalService _dlFileEntryPreviewLocalService;

}