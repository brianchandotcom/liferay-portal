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

import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class StartAppServerTask extends DefaultTask {

	public StartAppServerTask() {
		_project = getProject();

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					if (isPortalStarted()) {
						return false;
					}

					return true;
				}

			});
	}

	@InputDirectory
	public File getAppServerBinDir() {
		return GradleUtil.toFile(_project, _appServerBinDir);
	}

	public long getAppServerCheckInterval() {
		return _appServerCheckInterval;
	}

	@Input
	public int getAppServerPortNumber() {
		return _appServerPortNumber;
	}

	@Input
	public String getAppServerStartExecutable() {
		return GradleUtil.toString(_appServerStartExecutable);
	}

	@Input
	public List<String> getAppServerStartExecutableArgs() {
		return GradleUtil.toStringList(_appServerStartExecutableArgs);
	}

	@Input
	public String getAppServerStartPath() {
		return GradleUtil.toString(_appServerStartPath);
	}

	@Input
	public long getAppServerStartTimeout() {
		return _appServerStartTimeout;
	}

	@Input
	@Optional
	public String getAppServerType() {
		return GradleUtil.toString(_appServerType);
	}

	public boolean isPortalStarted() {
		try {
			new Socket("localhost", getAppServerPortNumber());
		}
		catch (IOException ioe) {
			return false;
		}

		return true;
	}

	public void setAppServerBinDir(Object appServerBinDir) {
		_appServerBinDir = appServerBinDir;
	}

	public void setAppServerCheckInterval(long appServerCheckInteval) {
		_appServerCheckInterval = appServerCheckInteval;
	}

	public void setAppServerPortNumber(int appServerPortNumber) {
		_appServerPortNumber = appServerPortNumber;
	}

	public void setAppServerStartExecutable(Object appServerStartExecutable) {
		_appServerStartExecutable = appServerStartExecutable;
	}

	public void setAppServerStartExecutableArgs(
		Iterable<?> appServerStartExecutableArgs) {

		_appServerStartExecutableArgs.clear();

		GUtil.addToCollection(
			_appServerStartExecutableArgs, appServerStartExecutableArgs);
	}

	public void setAppServerStartPath(Object appServerStartPath) {
		_appServerStartPath = appServerStartPath;
	}

	public void setAppServerStartTimeout(long appServerStartTimeout) {
		_appServerStartTimeout = appServerStartTimeout;
	}

	public void setAppServerType(Object appServerType) {
		_appServerType = appServerType;
	}

	@TaskAction
	public void startAppServer() throws Exception {
		List<String> commands = new ArrayList<>();

		File binDir = getAppServerBinDir();

		File startExecutableFile = new File(
			binDir, getAppServerStartExecutable());

		commands.add(startExecutableFile.getAbsolutePath());
		commands.addAll(getAppServerStartExecutableArgs());

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		processBuilder.directory(binDir);
		processBuilder.inheritIO();
		processBuilder.redirectErrorStream(true);

		processBuilder.start();

		boolean started = GradleUtil.waitFor(
			_appServerStartCheckCallable, getAppServerCheckInterval(),
			getAppServerStartTimeout());

		if (!started) {
			throw new GradleException(
				"App Server timeout on " + getAppServerStartURL());
		}
	}

	protected URL getAppServerStartURL() throws Exception {
		return new URL(
			"http", "localhost", getAppServerPortNumber(),
			getAppServerStartPath());
	}

	private Object _appServerBinDir;
	private long _appServerCheckInterval = 500;
	private int _appServerPortNumber = 8080;

	private final Callable<Boolean> _appServerStartCheckCallable =
		new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				try {
					URL url = getAppServerStartURL();

					HttpURLConnection httpURLConnection =
						(HttpURLConnection)url.openConnection();

					httpURLConnection.setRequestMethod("GET");

					int responseCode = httpURLConnection.getResponseCode();

					if ((responseCode > 0) && (responseCode < 400)) {
						return true;
					}

					return false;
				}
				catch (IOException ioe) {
					return false;
				}
			}

		};

	private Object _appServerStartExecutable;
	private final List<Object> _appServerStartExecutableArgs =
		new ArrayList<>();
	private Object _appServerStartPath = "/web/guest";
	private long _appServerStartTimeout = 5 * 60 * 1000;
	private Object _appServerType;
	private final Project _project;

}