/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.sync;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = "destination.name=" + DestinationNames.BACKGROUND_TASK_STATUS,
	service = MessageListener.class
)
public class LayoutSetPrototypeSyncBackgroundTaskStatusMessageListener
	implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		if (!Objects.equals(
				message.getString("taskExecutorClassName"),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_SYNC_BACKGROUND_TASK_EXECUTOR)) {

			return;
		}

		int backgroundTaskStatus = message.getInteger("status");

		if ((backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_CANCELLED) &&
			(backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS) &&
			(backgroundTaskStatus != BackgroundTaskConstants.STATUS_FAILED) &&
			(backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			return;
		}

		BackgroundTask backgroundTask =
			_backgroundTaskManager.fetchBackgroundTask(
				message.getLong("backgroundTaskId"));

		if (backgroundTask == null) {
			return;
		}

		long exportImportConfigurationId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "exportImportConfigurationId");

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				fetchExportImportConfiguration(exportImportConfigurationId);

		if (exportImportConfiguration == null) {
			return;
		}

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		LayoutSetPrototypeSyncSessionManagerUtil.recordBackgroundTaskStatus(
			backgroundTaskStatus,
			(Map<String, String[]>)settingsMap.get("parameterMap"));
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}