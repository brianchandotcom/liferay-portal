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

import com.liferay.gradle.plugins.test.integration.util.GradleUtil;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.util.concurrent.Callable;

import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.StartedProcess;

/**
 * @author Andrea Di Giorgi
 */
public class StartAppServerTask extends BaseAppServerTask {

	@Input
	public long getCheckInterval() {
		return _checkInterval;
	}

	@Input
	public long getCheckTimeout() {
		return _checkTimeout;
	}

	public void setCheckInterval(long checkInterval) {
		_checkInterval = checkInterval;
	}

	public void setCheckTimeout(long checkTimeout) {
		_checkTimeout = checkTimeout;
	}

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

	protected void waitFor(Callable<Boolean> callable) {
		boolean success = false;

		try {
			success = GradleUtil.waitFor(
				callable, getCheckInterval(), getCheckTimeout());
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to wait for the application server", e);
		}

		if (!success) {
			throw new GradleException(
				"Timeout while waiting for the application server");
		}
	}

	protected void waitForStarted(
		StartedProcess startedProcess, OutputStream outputStream) {

		waitForReachable();
	}

	private long _checkInterval = 500;
	private long _checkTimeout = 5 * 60 * 1000;

}