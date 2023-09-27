/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import java.util.concurrent.Callable;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import org.zeroturnaround.exec.ProcessExecutor;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class StopAppServerTask extends BaseAppServerTask {

	@Input
	public long getAdditionalWaitTime() {
		return _additionalWaitTime;
	}

	public void setAdditionalWaitTime(long additionalWaitTime) {
		_additionalWaitTime = additionalWaitTime;
	}

	@TaskAction
	public void stopAppServer() throws Exception {
		if (!isReachable()) {
			return;
		}

		ProcessExecutor processExecutor = getProcessExecutor();

		processExecutor.executeNoTimeout();

		waitFor(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return !isReachable();
				}

			});

		long additionalWaitTime = getAdditionalWaitTime();

		if (additionalWaitTime > 0) {
			Thread.sleep(additionalWaitTime);
		}
	}

	private long _additionalWaitTime = 2000;

}