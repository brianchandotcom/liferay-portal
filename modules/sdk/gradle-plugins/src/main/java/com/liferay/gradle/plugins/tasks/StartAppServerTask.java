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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

/**
 * @author Andrea Di Giorgi
 */
public class StartAppServerTask extends DefaultTask implements AppServerTask {

	@InputDirectory
	public File getAppServerBinDir() {
		return _appServer.getBinDir();
	}

	public String getAppServerStartExecutable() {
		return _appServer.getStartExecutable();
	}

	public List<String> getAppServerStartExecutableArgs() {
		return _appServer.getStartExecutableArgs();
	}

	@InputFile
	public File getAppServerStartExecutableFile() {
		return new File(getAppServerBinDir(), getAppServerStartExecutable());
	}

	@Override
	public String getAppServerType() {
		return _appServerType;
	}

	public long getCheckInterval() {
		return _checkInterval;
	}

	public String getCheckPath() {
		return _appServer.getCheckPath();
	}

	public long getTimeout() {
		return _timeout;
	}

	public boolean isAppServerReachable() {
		return _appServer.isReachable();
	}

	@Override
	public void merge(AppServer appServer) {
		if (getAppServerBinDir() == null) {
			setAppServerBinDir(appServer.getBinDir());
		}

		if (Validator.isNull(getAppServerStartExecutable())) {
			setAppServerStartExecutable(appServer.getStartExecutable());
		}

		List<String> appServerStartExecutableArgs =
			getAppServerStartExecutableArgs();

		if (appServerStartExecutableArgs.isEmpty()) {
			setAppServerStartExecutableArgs(appServer.getStartExecutableArgs());
		}
	}

	public void setAppServerBinDir(Object appServerBinDir) {
		_appServer.setBinDir(appServerBinDir);
	}

	public void setAppServerStartExecutable(Object appServerStartExecutable) {
		_appServer.setStartExecutable(appServerStartExecutable);
	}

	public void setAppServerStartExecutableArgs(
		Iterable<?> appServerStartExecutableArgs) {

		_appServer.setStartExecutableArgs(appServerStartExecutableArgs);
	}

	public void setAppServerType(String appServerType) {
		_appServerType = appServerType;
	}

	public void setCheckInterval(long checkInterval) {
		_checkInterval = checkInterval;
	}

	public void setCheckPath(Object checkPath) {
		_appServer.setCheckPath(checkPath);
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	@TaskAction
	public void startAppServer() throws Exception {
		if (isAppServerReachable()) {
			return;
		}

		List<String> commands = new ArrayList<>();

		File appServerStartExecutableFile = getAppServerStartExecutableFile();

		commands.add(appServerStartExecutableFile.getAbsolutePath());
		commands.addAll(getAppServerStartExecutableArgs());

		ProcessExecutor processExecutor = new ProcessExecutor(commands);

		processExecutor.directory(getAppServerBinDir());

		Slf4jStream slf4jStream = Slf4jStream.ofCaller();

		processExecutor.redirectError(slf4jStream.asWarn());
		processExecutor.redirectOutput(slf4jStream.asWarn());

		processExecutor.start();

		waitForAppServer();
	}

	public void waitForAppServer() {
		Callable<Boolean> callable = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				if (isAppServerReachable()) {
					return true;
				}

				return false;
			}

		};

		boolean success = false;

		try {
			success = GradleUtil.waitFor(
				callable, getCheckInterval(), getTimeout());
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to wait for the application server", e);
		}

		if (!success) {
			throw new GradleException(
				"Timeout while starting the application server");
		}
	}

	private final AppServer _appServer = new AppServer(null, getProject());
	private String _appServerType;
	private long _checkInterval = 500;
	private long _timeout = 5 * 60 * 1000;

}