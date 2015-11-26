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

package com.liferay.portal.background.task.internal.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(immediate = true, service = BackgroundTaskDisplayFactory.class)
public class BackgroundTaskDisplayFactoryImpl
	implements BackgroundTaskDisplayFactory {

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		if (backgroundTask == null) {
			return null;
		}

		BackgroundTaskExecutor backgroundTaskExecutor =
			_backgroundTaskExecutorRegistry.getBackgroundTaskExecutor(
				backgroundTask.getTaskExecutorClassName());

		return backgroundTaskExecutor.getBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		long backgroundTaskId) {

		BackgroundTask backgroundTask =
			_backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);

		return getBackgroundTaskDisplay(backgroundTask);
	}

	@Reference(unbind = "-")
	protected void setBackgroundTaskExecutorRegistry(
		BackgroundTaskExecutorRegistry backgroundTaskExecutorRegistry) {

		_backgroundTaskExecutorRegistry = backgroundTaskExecutorRegistry;
	}

	@Reference(unbind = "-")
	protected void setBackgroundTaskManager(
		BackgroundTaskManager backgroundTaskManager) {

		_backgroundTaskManager = backgroundTaskManager;
	}

	private volatile BackgroundTaskExecutorRegistry
		_backgroundTaskExecutorRegistry;
	private volatile BackgroundTaskManager _backgroundTaskManager;

}