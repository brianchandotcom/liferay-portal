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

package com.liferay.user.associated.data.web.internal.export.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.user.associated.data.web.internal.export.controller.UADApplicationExportController;

import java.io.File;
import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class UADExportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String applicationKey = (String)taskContextMap.get("applicationKey");

		File file = _uadApplicationExportController.export(
			applicationKey, Long.valueOf(backgroundTask.getName()));

		_backgroundTaskManager.addBackgroundTaskAttachment(
			backgroundTask.getUserId(), backgroundTask.getBackgroundTaskId(),
			file.getName(), file);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator() {

		if (_backgroundTaskStatusMessageTranslator == null) {
			_backgroundTaskStatusMessageTranslator =
				new UADExportBackgroundTaskStatusMessageTranslator();
		}

		return _backgroundTaskStatusMessageTranslator;
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	private BackgroundTaskStatusMessageTranslator
		_backgroundTaskStatusMessageTranslator;

	@Reference
	private UADApplicationExportController _uadApplicationExportController;

}