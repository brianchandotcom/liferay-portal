/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.background.task;

import com.liferay.exportimport.constants.ExportImportBackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.rest.internal.util.GroupUtil;
import com.liferay.exportimport.rest.internal.util.ImportPreviewUtil;
import com.liferay.exportimport.upload.ImportUpload;
import com.liferay.exportimport.upload.ImportUploadException;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import jakarta.ws.rs.BadRequestException;

import java.io.File;
import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaime León
 */
@Component(
	property = "background.task.executor.class.name=" + ExportImportBackgroundTaskExecutorNames.IMPORT_PREVIEW_BACKGROUND_TASK_EXECUTOR,
	service = BackgroundTaskExecutor.class
)
public class ImportPreviewBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public ImportPreviewBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_NOT_ISOLATED);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String importUploadId = MapUtil.getString(
			taskContextMap, "importUploadId");

		ImportUpload importUpload =
			_importUploadManager.fetchImportUpload(
				backgroundTask.getGroupId(), importUploadId,
				backgroundTask.getUserId());

		if (importUpload == null) {
			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED,
				JSONUtil.put(
					"message", "The upload " + importUploadId + " is missing"
				).toString());
		}

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			long size = importUpload.getSize();

			try {
				_importUploadManager.writeTo(
					bytesWritten -> _setPercentage(
						backgroundTask, (int)((bytesWritten * 80) / size)),
					file, backgroundTask.getGroupId(), importUploadId,
					backgroundTask.getUserId());
			}
			catch (ImportUploadException importUploadException) {
				return new BackgroundTaskResult(
					BackgroundTaskConstants.STATUS_FAILED,
					JSONUtil.put(
						"message", importUploadException.getMessage()
					).toString());
			}

			Locale locale = LocaleUtil.fromLanguageId(
				MapUtil.getString(taskContextMap, "languageId"));
			Group scopeGroup = GroupUtil.getScopeGroup(
				_groupLocalService.getGroup(backgroundTask.getGroupId()),
				_layoutLocalService.fetchLayout(importUpload.getPlid()),
				importUpload.getPortletId());
			User user = _userLocalService.getUser(backgroundTask.getUserId());

			try {
				ImportPreviewUtil.validateImportFile(
					file, scopeGroup.getGroupId(), locale,
					importUpload.getPlid(),
					importUpload.getPortletId(), user.getTimeZone(),
					backgroundTask.getUserId());
			}
			catch (BadRequestException badRequestException) {
				return new BackgroundTaskResult(
					BackgroundTaskConstants.STATUS_FAILED,
					JSONUtil.put(
						"message", badRequestException.getMessage()
					).toString());
			}

			_setPercentage(backgroundTask, 90);

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_SUCCESSFUL,
				ImportPreviewUtil.toJSON(
					ImportPreviewUtil.getImportPreview(
						user.getFullName(), file,
						importUpload.getFileName(),
						importUpload.getSize(), locale,
						importUpload.getPortletId(), scopeGroup,
						backgroundTask.getUserId())));
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception) {

		return JSONUtil.put(
			"message",
			ImportPreviewUtil.getMessage(
				_staging.getExceptionMessagesJSONObject(
					getLocale(backgroundTask), exception, null))
		).toString();
	}

	private void _setPercentage(BackgroundTask backgroundTask, int percentage) {
		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		if (backgroundTaskStatus == null) {
			return;
		}

		backgroundTaskStatus.setAttribute("percentage", percentage);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImportUploadManager _importUploadManager;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Staging _staging;

	@Reference
	private UserLocalService _userLocalService;

}