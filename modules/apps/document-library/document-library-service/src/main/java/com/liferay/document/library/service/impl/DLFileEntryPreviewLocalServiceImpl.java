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

import com.liferay.document.library.model.DLFileEntryPreview;
import com.liferay.document.library.service.base.DLFileEntryPreviewLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * The implementation of the dl file entry preview local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.document.library.service.DLFileEntryPreviewLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreviewLocalServiceBaseImpl
 * @see com.liferay.document.library.service.DLFileEntryPreviewLocalServiceUtil
 */
public class DLFileEntryPreviewLocalServiceImpl
	extends DLFileEntryPreviewLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.document.library.service.DLFileEntryPreviewLocalServiceUtil} to access the dl file entry preview local service.
	 */
	@Override
	public void addDLFileEntryPreview(
			long fileEntryId, long fileVersionId, int previewType)
		throws PortalException {

		long fileEntryPreviewId = counterLocalService.increment();

		DLFileEntryPreview fileEntryPreview =
			dlFileEntryPreviewPersistence.create(fileEntryPreviewId);

		fileEntryPreview.setFileEntryId(fileEntryId);
		fileEntryPreview.setFileVersionId(fileVersionId);
		fileEntryPreview.setPreviewType(previewType);

		dlFileEntryPreviewPersistence.update(fileEntryPreview);
	}

	@Override
	public DLFileEntryPreview fetchDLFileEntryPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		return dlFileEntryPreviewPersistence.fetchByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public DLFileEntryPreview fetchDLFileEntryPreview(
		long fileEntryId, long fileVersionId, int previewType) {

		return dlFileEntryPreviewPersistence.fetchByF_F_P(
			fileEntryId, fileVersionId, previewType);
	}

	@Override
	public DLFileEntryPreview getDLFileEntryPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		return dlFileEntryPreviewPersistence.findByF_F(
			fileEntryId, fileVersionId);
	}

	@Override
	public DLFileEntryPreview getDLFileEntryPreview(
			long fileEntryId, long fileVersionId, int previewType)
		throws PortalException {

		return dlFileEntryPreviewPersistence.findByF_F_P(
			fileEntryId, fileVersionId, previewType);
	}

	@Override
	public List<DLFileEntryPreview> getDLFileEntryPreviews(long fileEntryId) {
		return dlFileEntryPreviewPersistence.findByFileEntryId(fileEntryId);
	}

	@Override
	public void updateDLFileEntryPreview(
			long fileEntryPreviewId, int fileEntryPreviewType)
		throws PortalException {

		DLFileEntryPreview fileEntryPreview =
			dlFileEntryPreviewPersistence.findByPrimaryKey(fileEntryPreviewId);

		fileEntryPreview.setPreviewType(fileEntryPreviewType);

		dlFileEntryPreviewPersistence.update(fileEntryPreview);
	}

}