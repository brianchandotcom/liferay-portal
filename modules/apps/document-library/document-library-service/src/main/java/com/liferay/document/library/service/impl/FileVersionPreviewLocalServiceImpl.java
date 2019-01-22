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

import com.liferay.document.library.model.FileVersionPreview;
import com.liferay.document.library.service.base.FileVersionPreviewLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
public class FileVersionPreviewLocalServiceImpl
	extends FileVersionPreviewLocalServiceBaseImpl {

	@Override
	public void addFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws PortalException {

		FileVersionPreview fileVersionPreview =
			fileVersionPreviewPersistence.create(
				counterLocalService.increment());

		fileVersionPreview.setFileEntryId(fileEntryId);
		fileVersionPreview.setFileVersionId(fileVersionId);
		fileVersionPreview.setPreviewStatus(previewStatus);

		fileVersionPreviewPersistence.update(fileVersionPreview);
	}

	@Override
	public void deleteFileEntryFileVersionPreviews(long fileEntryId) {
		fileVersionPreviewPersistence.removeByFileEntryId(fileEntryId);
	}

	@Override
	public FileVersionPreview fetchFileVersionPreview(
		long fileEntryId, long fileVersionId) {

		return fileVersionPreviewPersistence.fetchByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public FileVersionPreview fetchFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		return fileVersionPreviewPersistence.fetchByF_F_P(
			fileEntryId, fileVersionId, previewStatus);
	}

	@Override
	public FileVersionPreview getFileVersionPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		return fileVersionPreviewPersistence.findByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public FileVersionPreview getFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws PortalException {

		return fileVersionPreviewPersistence.findByF_F_P(
			fileEntryId, fileVersionId, previewStatus);
	}

	@Override
	public List<FileVersionPreview> getFileEntryFileVersionPreviews(
		long fileEntryId) {

		return fileVersionPreviewPersistence.findByFileEntryId(fileEntryId);
	}

	@Override
	public boolean hasFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		FileVersionPreview fileVersionPreview =
			fileVersionPreviewPersistence.fetchByF_F_P(
				fileEntryId, fileVersionId, previewStatus);

		if (fileVersionPreview == null) {
			return false;
		}

		return true;
	}

	@Override
	public void updateFileVersionPreview(
			long fileVersionPreviewId, int previewStatus)
		throws PortalException {

		FileVersionPreview fileVersionPreview =
			fileVersionPreviewPersistence.findByPrimaryKey(
				fileVersionPreviewId);

		fileVersionPreview.setPreviewStatus(previewStatus);

		fileVersionPreviewPersistence.update(fileVersionPreview);
	}


}