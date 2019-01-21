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

package com.liferay.gradle.plugins.go;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Project;

/**
 * @author Peter Shin
 */
public class GoExtension {

	public GoExtension(final Project project) {
		_goDir = new File(project.getBuildDir(), "go");

		_goUrl = new Callable<String>() {

			@Override
			public String call() throws Exception {
				String goVersion = getGoVersion();

				if (Validator.isNull(goVersion)) {
					return null;
				}

				StringBuilder sb = new StringBuilder();

				sb.append("https://dl.google.com/go/go");
				sb.append(goVersion);
				sb.append('.');

				String bitmode = OSDetector.getBitmode();

				if (OSDetector.isApple()) {
					sb.append("darwin-amd64.tar.gz");
				}
				else if (OSDetector.isWindows()) {
					if (bitmode.equals("64")) {
						sb.append("windows-amd64.zip");
					}
					else {
						sb.append("windows-386.zip");
					}
				}
				else {
					if (bitmode.equals("64")) {
						sb.append("linux-amd64.tar.gz");
					}
					else {
						sb.append("linux-386.tar.gz");
					}
				}

				return sb.toString();
			}

		};

		_workingDir = project.getProjectDir();

		_project = project;
	}

	public File getGoDir() {
		return GradleUtil.toFile(_project, _goDir);
	}

	public String getGoUrl() {
		return GradleUtil.toString(_goUrl);
	}

	public String getGoVersion() {
		return GradleUtil.toString(_goVersion);
	}

	public File getWorkingDir() {
		return GradleUtil.toFile(_project, _workingDir);
	}

	public void setGoDir(Object goDir) {
		_goDir = goDir;
	}

	public void setGoUrl(Object goUrl) {
		_goUrl = goUrl;
	}

	public void setGoVersion(Object goVersion) {
		_goVersion = goVersion;
	}

	public void setWorkingDir(Object workingDir) {
		_workingDir = workingDir;
	}

	private Object _goDir;
	private Object _goUrl;
	private Object _goVersion = "1.11.4";
	private final Project _project;
	private Object _workingDir;

}