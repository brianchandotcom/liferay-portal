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
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

/**
 * @author Andrea Di Giorgi
 */
public class StopAppServerTask extends DefaultTask implements AppServerTask {

	@InputDirectory
	public File getAppServerBinDir() {
		return _appServer.getBinDir();
	}

	public String getAppServerStopExecutable() {
		return _appServer.getStopExecutable();
	}

	public List<String> getAppServerStopExecutableArgs() {
		return _appServer.getStopExecutableArgs();
	}

	@InputFile
	public File getAppServerStopExecutableFile() {
		return new File(getAppServerBinDir(), getAppServerStopExecutable());
	}

	@Override
	public String getAppServerType() {
		return _appServerType;
	}

	public boolean isAppServerReachable() {
		return _appServer.isReachable();
	}

	@Override
	public void merge(AppServer appServer) {
		if (getAppServerBinDir() == null) {
			setAppServerBinDir(appServer.getBinDir());
		}

		if (Validator.isNull(getAppServerStopExecutable())) {
			setAppServerStopExecutable(appServer.getStopExecutable());
		}

		List<String> appServerStopExecutableArgs =
			getAppServerStopExecutableArgs();

		if (appServerStopExecutableArgs.isEmpty()) {
			setAppServerStopExecutableArgs(appServer.getStopExecutableArgs());
		}
	}

	public void setAppServerBinDir(Object appServerBinDir) {
		_appServer.setBinDir(appServerBinDir);
	}

	public void setAppServerStopExecutable(Object appServerStopExecutable) {
		_appServer.setStopExecutable(appServerStopExecutable);
	}

	public void setAppServerStopExecutableArgs(
		Iterable<?> appServerStopExecutableArgs) {

		_appServer.setStopExecutableArgs(appServerStopExecutableArgs);
	}

	public void setAppServerType(String appServerType) {
		_appServerType = appServerType;
	}

	@TaskAction
	public void stopAppServer() throws Exception {
		if (!isAppServerReachable()) {
			return;
		}

		List<String> commands = new ArrayList<>();

		File appServerStopExecutableFile = getAppServerStopExecutableFile();

		commands.add(appServerStopExecutableFile.getAbsolutePath());
		commands.addAll(getAppServerStopExecutableArgs());

		ProcessExecutor processExecutor = new ProcessExecutor(commands);

		processExecutor.directory(getAppServerBinDir());

		Slf4jStream slf4jStream = Slf4jStream.ofCaller();

		processExecutor.redirectError(slf4jStream.asWarn());
		processExecutor.redirectOutput(slf4jStream.asWarn());

		processExecutor.executeNoTimeout();
	}

	private final AppServer _appServer = new AppServer(null, getProject());
	private String _appServerType;

}