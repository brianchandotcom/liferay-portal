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

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.LayoutExportBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class LayoutExportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public LayoutExportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		long userId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "userId");

		File larFile = _exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);

		_backgroundTaskManager.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(),
			StringBundler.concat(
				StringUtil.replace(
					exportImportConfiguration.getName(), CharPool.SPACE,
					CharPool.UNDERLINE),
				StringPool.DASH, Time.getTimestamp(), ".lar"),
			larFile);

		return BackgroundTaskResult.SUCCESS;
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

}