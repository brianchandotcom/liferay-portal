/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.upload.ImportUploadEntry;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaime León
 */
@Component(service = ImportFileSourceHelper.class)
public class ImportFileSourceHelper {

	public List<ImportFileSource> getImportFileSources(
			BackgroundTask backgroundTask, Locale locale)
		throws Exception {

		List<ImportFileSource> importFileSources = TransformUtil.transform(
			backgroundTask.getAttachmentsFileEntries(),
			this::_toImportFileSource);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long tempFileEntryId = MapUtil.getLong(
			taskContextMap, "tempFileEntryId");

		if (tempFileEntryId > 0) {
			try {
				importFileSources.add(
					_toImportFileSource(
						_dlAppLocalService.getFileEntry(tempFileEntryId)));
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				throw new PortalException(
					_language.get(
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

		ImportUploadEntry importUploadEntry =
			_importUploadManager.fetchImportUpload(
				uploadGroupId, importUploadId, backgroundTask.getUserId());

		if ((importUploadEntry == null) || !importUploadEntry.isComplete()) {
			throw new PortalException(
				_language.get(
					locale,
					"the-uploaded-file-has-expired-please-upload-it-again"));
		}

		importFileSources.add(
			new ImportFileSource(
				() -> _importUploadManager.deleteImportUpload(
					uploadGroupId, importUploadId, backgroundTask.getUserId()),
				importUploadEntry.getFileName(),
				file -> _importUploadManager.writeTo(
					file, uploadGroupId, importUploadId,
					backgroundTask.getUserId())));

		return importFileSources;
	}

	private ImportFileSource _toImportFileSource(FileEntry fileEntry) {
		return new ImportFileSource(
			() -> PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId()),
			fileEntry.getFileName(),
			file -> FileUtil.write(file, fileEntry.getContentStream()));
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ImportUploadManager _importUploadManager;

	@Reference
	private Language _language;

}