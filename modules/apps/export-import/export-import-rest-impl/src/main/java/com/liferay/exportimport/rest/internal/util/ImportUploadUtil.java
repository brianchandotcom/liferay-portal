/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.constants.ExportImportBackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.portal.background.task.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.background.task.util.comparator.BackgroundTaskCreateDateComparator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Jaime León
 */
public class ImportUploadUtil {

	public static BackgroundTask fetchPreviewBackgroundTask(
		long groupId, String importUploadId) {

		List<BackgroundTask> backgroundTasks =
			BackgroundTaskManagerUtil.getBackgroundTasks(
				groupId, importUploadId,
				ExportImportBackgroundTaskExecutorNames.
					IMPORT_PREVIEW_BACKGROUND_TASK_EXECUTOR,
				0, 1, BackgroundTaskCreateDateComparator.getInstance(false));

		if (ListUtil.isEmpty(backgroundTasks)) {
			return null;
		}

		return backgroundTasks.get(0);
	}

	public static long fetchPreviewBackgroundTaskId(
		long groupId, String importUploadId) {

		BackgroundTask backgroundTask = fetchPreviewBackgroundTask(
			groupId, importUploadId);

		if (backgroundTask == null) {
			return 0;
		}

		return backgroundTask.getBackgroundTaskId();
	}

	public static String getErrorMessage(String statusMessage) {
		if (Validator.isNull(statusMessage)) {
			return null;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				statusMessage);

			return jsonObject.getString("message", statusMessage);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return statusMessage;
		}
	}

	public static boolean hasPendingImport(
		long[] groupIds, String importUploadId) {

		return ListUtil.exists(
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupIds,
				new String[] {
					BackgroundTaskExecutorNames.
						LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
					BackgroundTaskExecutorNames.
						PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
				},
				false),
			backgroundTask -> Objects.equals(
				MapUtil.getString(
					backgroundTask.getTaskContextMap(), "importUploadId"),
				importUploadId));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportUploadUtil.class);

}