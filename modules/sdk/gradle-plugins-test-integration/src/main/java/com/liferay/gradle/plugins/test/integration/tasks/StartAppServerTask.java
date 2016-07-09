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

package com.liferay.gradle.plugins.test.integration.tasks;

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