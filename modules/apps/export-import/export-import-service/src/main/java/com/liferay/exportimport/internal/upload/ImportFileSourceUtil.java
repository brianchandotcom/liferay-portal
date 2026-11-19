/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.upload;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.upload.ImportUpload;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jaime León
 */
public class ImportFileSourceUtil {

	public static List<ImportFileSource> getImportFileSources(
			BackgroundTask backgroundTask, Locale locale)
		throws Exception {

		List<ImportFileSource> importFileSources = TransformUtil.transform(
			backgroundTask.getAttachmentsFileEntries(),
			ImportFileSourceUtil::_toImportFileSource);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long tempFileEntryId = MapUtil.getLong(
			taskContextMap, "tempFileEntryId");

		if (tempFileEntryId > 0) {
			try {
				importFileSources.add(
					_toImportFileSource(
						DLAppLocalServiceUtil.getFileEntry(tempFileEntryId)));
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				throw new PortalException(
					LanguageUtil.get(
						locale,
						"the-uploaded-file-has-expired-please-upload-it-again"),
					noSuchFileEntryException);
			}
		}

		String importUploadId = MapUtil.getString(
			taskContextMap, "importUploadId");

		if (Validator.isNull(importUploadId)) {
			return importFileSources;
		}

		long uploadGroupId = MapUtil.getLong(taskContextMap, "uploadGroupId");

		ImportUploadManager importUploadManager =
			_importUploadManagerSnapshot.get();

		ImportUpload importUpload = importUploadManager.fetchImportUpload(
			uploadGroupId, importUploadId, backgroundTask.getUserId());

		if ((importUpload == null) || !importUpload.isComplete()) {
			throw new PortalException(
				LanguageUtil.get(
					locale,
					"the-uploaded-file-has-expired-please-upload-it-again"));
		}

		importFileSources.add(
			new ImportFileSource(
				() -> importUploadManager.deleteImportUpload(
					uploadGroupId, importUploadId, backgroundTask.getUserId()),
				importUpload.getFileName(),
				file -> importUploadManager.writeTo(
					file, uploadGroupId, importUploadId,
					backgroundTask.getUserId())));

		return importFileSources;
	}

	private static ImportFileSource _toImportFileSource(FileEntry fileEntry) {
		return new ImportFileSource(
			() -> PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId()),
			fileEntry.getFileName(),
			file -> FileUtil.write(file, fileEntry.getContentStream()));
	}

	private static final Snapshot<ImportUploadManager>
		_importUploadManagerSnapshot = new Snapshot<>(
			ImportFileSourceUtil.class, ImportUploadManager.class);

}