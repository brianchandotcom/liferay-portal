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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.model.BackgroundTask;

/**
 * @author Michael C. Han
 */
public class DelegatingBackgroundTaskExecutor
	implements BackgroundTaskExecutor {

	public DelegatingBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor) {

		_backgroundTaskExecutor = backgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		return _backgroundTaskExecutor.execute(backgroundTask);
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator() {

		return _backgroundTaskExecutor.
			getBackgroundTaskStatusMessageTranslator();
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		return _backgroundTaskExecutor.handleException(backgroundTask, e);
	}

	@Override
	public boolean isSerial() {
		return _backgroundTaskExecutor.isSerial();
	}

	protected BackgroundTaskExecutor getBackgroundTaskExecutor() {
		return _backgroundTaskExecutor;
	}

	private final BackgroundTaskExecutor _backgroundTaskExecutor;

}