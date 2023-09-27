/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;

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
	public BackgroundTaskExecutor clone() {
		return new DelegatingBackgroundTaskExecutor(
			getBackgroundTaskExecutor());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		return _backgroundTaskExecutor.execute(backgroundTask);
	}

	@Override
	public String generateLockKey(BackgroundTask backgroundTask) {
		return _backgroundTaskExecutor.generateLockKey(backgroundTask);
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return _backgroundTaskExecutor.getBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator() {

		return _backgroundTaskExecutor.
			getBackgroundTaskStatusMessageTranslator();
	}

	@Override
	public int getIsolationLevel() {
		return _backgroundTaskExecutor.getIsolationLevel();
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception) {

		return _backgroundTaskExecutor.handleException(
			backgroundTask, exception);
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