/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.background.task;

import com.liferay.document.library.configuration.DLFileEntryConfigurationProvider;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.util.Map;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BasePreviewBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		try {
			generatePreviews(backgroundTask.getCompanyId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	protected abstract void generatePreview(FileVersion fileVersion)
		throws Exception;

	protected void generatePreviews(long companyId) throws PortalException {
		long previewableProcessorMaxSize =
			dlFileEntryConfigurationProvider.
				getCompanyPreviewableProcessorMaxSize(companyId);

		if (previewableProcessorMaxSize == 0) {
			return;
		}

		dlFileEntryLocalService.forEachFileEntry(
			companyId, 0, _getProcessDLFileEntryConsumer(),
			previewableProcessorMaxSize, getMimeTypes());
	}

	protected abstract String[] getMimeTypes();

	@Reference
	protected DLFileEntryConfigurationProvider dlFileEntryConfigurationProvider;

	@Reference
	protected DLFileEntryLocalService dlFileEntryLocalService;

	private Consumer<DLFileEntry> _getProcessDLFileEntryConsumer() {
		Map<Long, Long> groupPreviewableProcessorMaxSizeMap =
			dlFileEntryConfigurationProvider.
				getGroupPreviewableProcessorMaxSizeMap();

		return dlFileEntry -> {
			Long previewableProcessorMaxSize =
				groupPreviewableProcessorMaxSizeMap.get(
					dlFileEntry.getGroupId());

			if ((previewableProcessorMaxSize == null) ||
				(previewableProcessorMaxSize ==
					DLFileEntryConfigurationConstants.
						PREVIEWABLE_PROCESSOR_MAX_SIZE_UNLIMITED) ||
				(dlFileEntry.getSize() <= previewableProcessorMaxSize)) {

				_processDLFileEntry(dlFileEntry);
			}
		};
	}

	private void _processDLFileEntry(DLFileEntry dlFileEntry) {
		FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

		try {
			generatePreview(fileEntry.getLatestFileVersion());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process file entry " +
						fileEntry.getFileEntryId(),
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePreviewBackgroundTaskExecutor.class);

}