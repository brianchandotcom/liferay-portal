/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;
import com.liferay.gradle.util.GUtil;

import java.io.File;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseAppServerTask extends DefaultTask {

	public BaseAppServerTask environment(Map<String, String> environment) {
		_environment.putAll(environment);

		return this;
	}

	public BaseAppServerTask environment(String key, String value) {
		_environment.put(key, value);

		return this;
	}

	public void executableArgs(Iterable<?> executableArgs) {
		GUtil.addToCollection(_executableArgs, executableArgs);
	}

	public void executableArgs(Object... executableArgs) {
		executableArgs(Arrays.asList(executableArgs));
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getBinDir() {
		return GradleUtil.toFile(getProject(), _binDir);
	}

	@Input
	public long getCheckInterval() {
		return _checkInterval;
	}

	@Input
	public String getCheckPath() {
		return GradleUtil.toString(_checkPath);
	}

	@Input
	public long getCheckTimeout() {
		return _checkTimeout;
	}

	@Input
	public Map<String, String> getEnvironment() {
		return _environment;
	}

	@Input
	public String getExecutable() {
		return GradleUtil.toString(_executable);
	}

	@Input
	public List<String> getExecutableArgs() {
		List<Object> executableArgs = new ArrayList<>(_executableArgs.size());

		for (Object object : _executableArgs) {
			executableArgs.add(GradleUtil.toObject(object));
		}

		executableArgs = GUtil.flatten(executableArgs);

		return GradleUtil.toStringList(executableArgs);
	}

	@Input
	public String getHostName() {
		return GradleUtil.toString(_hostName);
	}

	@Input
	public int getPortNumber() {
		return GradleUtil.toInteger(_portNumber);
	}

	@Input
	public boolean isReachable() {
		try {
			URL url = new URL(
				"http", getHostName(), getPortNumber(), getCheckPath());

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)url.openConnection();

			httpURLConnection.setRequestMethod("HEAD");

			int responseCode = httpURLConnection.getResponseCode();

			if ((responseCode > 0) && (responseCode < 400)) {
				return true;
			}
		}
		catch (IOException ioException) {
		}

		return false;
	}

	public void setBinDir(Object binDir) {
		_binDir = binDir;
	}

	public void setCheckInterval(long checkInterval) {
		_checkInterval = checkInterval;
	}

	public void setCheckPath(Object checkPath) {
		_checkPath = checkPath;
	}

	public void setCheckTimeout(long checkTimeout) {
		_checkTimeout = checkTimeout;
	}

	public void setEnvironment(Map<String, String> environment) {
		_environment.clear();

		environment(environment);
	}

	public void setExecutable(Object executable) {
		_executable = executable;
	}

	public void setExecutableArgs(Iterable<?> executableArgs) {
		_executableArgs.clear();

		executableArgs(executableArgs);
	}

	public void setExecutableArgs(Object... executableArgs) {
		setExecutableArgs(Arrays.asList(executableArgs));
	}

	public void setHostName(Object hostName) {
		_hostName = hostName;
	}

	public void setPortNumber(Object portNumber) {
		_portNumber = portNumber;
	}

	@Internal
	protected ProcessExecutor getProcessExecutor() {
		List<String> commands = new ArrayList<>();

		File executableFile = new File(getBinDir(), getExecutable());

		commands.add(executableFile.getAbsolutePath());

		commands.addAll(getExecutableArgs());

		ProcessExecutor processExecutor = new ProcessExecutor(commands);

		processExecutor.directory(getBinDir());

		processExecutor.environment(getEnvironment());

		Slf4jStream slf4jStream = Slf4jStream.ofCaller();

		processExecutor.redirectOutput(slf4jStream.asWarn());

		return processExecutor;
	}

	protected void waitFor(Callable<Boolean> callable) {
		boolean success = false;

		try {
			success = GradleUtil.waitFor(
				callable, getCheckInterval(), getCheckTimeout());
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to wait for the application server", exception);
		}

		if (!success) {
			throw new GradleException(
				"Timeout while waiting for the application server");
		}
	}

	private Object _binDir;
	private long _checkInterval = 500;
	private Object _checkPath;
	private long _checkTimeout = 10 * 60 * 1000;
	private final Map<String, String> _environment = new LinkedHashMap<>();
	private Object _executable;
	private final List<Object> _executableArgs = new ArrayList<>();
	private Object _hostName = "localhost";
	private Object _portNumber;

}