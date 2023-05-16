/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.util.concurrent.Callable;

import org.gradle.api.tasks.TaskAction;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.StartedProcess;

/**
 * @author Andrea Di Giorgi
 */
public class StartAppServerTask extends BaseAppServerTask {

	@TaskAction
	public void startAppServer() throws Exception {
		if (isReachable()) {
			return;
		}

		OutputStream outputStream = new ByteArrayOutputStream();

		ProcessExecutor processExecutor = getProcessExecutor();

		processExecutor.redirectOutputAlsoTo(outputStream);

		StartedProcess startedProcess = processExecutor.start();

		waitForStarted(startedProcess, outputStream);
	}

	public void waitForReachable() {
		waitFor(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return isReachable();
				}

			});
	}

	protected void waitForStarted(
		StartedProcess startedProcess, OutputStream outputStream) {

		waitForReachable();
	}

}