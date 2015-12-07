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

package com.liferay.gradle.plugins.extensions;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;

import java.io.File;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class AppServer {

	public AppServer(String name, Project project) {
		_name = name;
		_project = project;
	}

	public File getBinDir() {
		return GradleUtil.toFile(_project, _binDir);
	}

	public String getCheckPath() {
		return GradleUtil.toString(_checkPath);
	}

	public File getDeployDir() {
		return GradleUtil.toFile(_project, _deployDir);
	}

	public File getDir() {
		return GradleUtil.toFile(_project, _dir);
	}

	public File getLibGlobalDir() {
		return GradleUtil.toFile(_project, _libGlobalDir);
	}

	public String getName() {
		return _name;
	}

	public File getPortalDir() {
		return GradleUtil.toFile(_project, _portalDir);
	}

	public int getPortNumber() {
		return GradleUtil.toInteger(_portNumber);
	}

	public String getStartExecutable() {
		return GradleUtil.toString(_startExecutable);
	}

	public List<String> getStartExecutableArgs() {
		return GradleUtil.toStringList(_startExecutableArgs);
	}

	public String getStopExecutable() {
		return GradleUtil.toString(_stopExecutable);
	}

	public List<String> getStopExecutableArgs() {
		return GradleUtil.toStringList(_stopExecutableArgs);
	}

	public String getVersion() {
		return GradleUtil.toString(_version);
	}

	public String getZipUrl() {
		return GradleUtil.toString(_zipUrl);
	}

	public boolean isReachable() {
		try {
			URL url = new URL(
				"http", "localhost", getPortNumber(), getCheckPath());

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)url.openConnection();

			httpURLConnection.setRequestMethod("GET");

			int responseCode = httpURLConnection.getResponseCode();

			if ((responseCode > 0) && (responseCode < 400)) {
				return true;
			}
		}
		catch (IOException ioe) {
		}

		return false;
	}

	public void setBinDir(Object binDir) {
		_binDir = binDir;
	}

	public void setCheckPath(Object checkPath) {
		_checkPath = checkPath;
	}

	public void setDeployDir(Object deployDir) {
		_deployDir = deployDir;
	}

	public void setDir(Object dir) {
		_dir = dir;
	}

	public void setLibGlobalDir(Object libGlobalDir) {
		_libGlobalDir = libGlobalDir;
	}

	public void setPortalDir(Object portalDir) {
		_portalDir = portalDir;
	}

	public void setPortNumber(Object portNumber) {
		_portNumber = portNumber;
	}

	public void setStartExecutable(Object startExecutable) {
		_startExecutable = startExecutable;
	}

	public void setStartExecutableArgs(Iterable<?> startExecutableArgs) {
		_startExecutableArgs.clear();

		GUtil.addToCollection(_startExecutableArgs, startExecutableArgs);
	}

	public void setStopExecutable(Object stopExecutable) {
		_stopExecutable = stopExecutable;
	}

	public void setStopExecutableArgs(Iterable<?> stopExecutableArgs) {
		_stopExecutableArgs.clear();

		GUtil.addToCollection(_stopExecutableArgs, stopExecutableArgs);
	}

	public void setVersion(Object version) {
		_version = version;
	}

	public void setZipUrl(Object zipUrl) {
		_zipUrl = zipUrl;
	}

	protected String getFileSuffixBat() {
		if (OSDetector.isWindows()) {
			return ".bat";
		}
		else {
			return ".sh";
		}
	}

	private Object _binDir;
	private Object _checkPath = "/web/guest";
	private Object _deployDir;
	private Object _dir;
	private Object _libGlobalDir;
	private final String _name;
	private Object _portalDir;
	private Object _portNumber = 8080;
	private final Project _project;
	private Object _startExecutable;
	private final List<Object> _startExecutableArgs = new ArrayList<>();
	private Object _stopExecutable;
	private final List<Object> _stopExecutableArgs = new ArrayList<>();
	private Object _version;
	private Object _zipUrl;

}