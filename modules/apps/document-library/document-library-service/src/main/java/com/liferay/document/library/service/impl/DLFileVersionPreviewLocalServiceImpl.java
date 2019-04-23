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

import com.liferay.document.library.model.DLFileVersionPreview;
import com.liferay.document.library.service.base.DLFileVersionPreviewLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.model.DLFileVersionPreview",
	service = AopService.class
)
public class DLFileVersionPreviewLocalServiceImpl
	extends DLFileVersionPreviewLocalServiceBaseImpl {

	@Override
	public void addDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws PortalException {

		DLFileVersionPreview dlFileVersionPreview =
			dlFileVersionPreviewPersistence.create(
				counterLocalService.increment());

		dlFileVersionPreview.setFileEntryId(fileEntryId);
		dlFileVersionPreview.setFileVersionId(fileVersionId);
		dlFileVersionPreview.setPreviewStatus(previewStatus);

		dlFileVersionPreviewPersistence.update(dlFileVersionPreview);
	}

	@Override
	public void deleteDLFileEntryFileVersionPreviews(long fileEntryId) {
		dlFileVersionPreviewPersistence.removeByFileEntryId(fileEntryId);
	}

	@Override
	public DLFileVersionPreview fetchDLFileVersionPreview(
		long fileEntryId, long fileVersionId) {

		return dlFileVersionPreviewPersistence.fetchByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public DLFileVersionPreview fetchDLFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		return dlFileVersionPreviewPersistence.fetchByF_F_P(
			fileEntryId, fileVersionId, previewStatus);
	}

	@Override
	public DLFileVersionPreview getDLFileVersionPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		return dlFileVersionPreviewPersistence.findByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public DLFileVersionPreview getDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws PortalException {

		return dlFileVersionPreviewPersistence.findByF_F_P(
			fileEntryId, fileVersionId, previewStatus);
	}

	@Override
	public List<DLFileVersionPreview> getFileEntryDLFileVersionPreviews(
		long fileEntryId) {

		return dlFileVersionPreviewPersistence.findByFileEntryId(fileEntryId);
	}

	@Override
	public boolean hasDLFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		DLFileVersionPreview dlFileVersionPreview =
			dlFileVersionPreviewPersistence.fetchByF_F_P(
				fileEntryId, fileVersionId, previewStatus);

		if (dlFileVersionPreview == null) {
			return false;
		}

		return true;
	}

	@Override
	public void updateDLFileVersionPreview(
			long dlFileVersionPreviewId, int previewStatus)
		throws PortalException {

		DLFileVersionPreview dlFileVersionPreview =
			dlFileVersionPreviewPersistence.findByPrimaryKey(
				dlFileVersionPreviewId);

		dlFileVersionPreview.setPreviewStatus(previewStatus);

		dlFileVersionPreviewPersistence.update(dlFileVersionPreview);
	}

}