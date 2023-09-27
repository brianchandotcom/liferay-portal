/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.internal.background.task.display.PortletExportImportBackgroundTaskDisplay;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 * @author Akos Thurzo
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.PortletExportBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class PortletExportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public PortletExportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new PortletExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		String fileName = MapUtil.getString(settingsMap, "fileName");

		File larFile = _exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);

		_backgroundTaskManager.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), fileName, larFile);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new PortletExportImportBackgroundTaskDisplay(backgroundTask);
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

}