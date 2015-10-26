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

package com.liferay.gradle.plugins.node;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class NodeExtension {

	public NodeExtension(final Project project) {
		_nodeDir = new Callable<File>() {

			@Override
			public File call() throws Exception {
				return new File(project.getBuildDir(), "node");
			}

		};

		_nodeUrl = new Callable<String>() {

			@Override
			public String call() throws Exception {
				String nodeVersion = getNodeVersion();

				if (Validator.isNull(nodeVersion)) {
					return null;
				}

				StringBuilder sb = new StringBuilder();

				sb.append("http://nodejs.org/dist/v");
				sb.append(nodeVersion);
				sb.append('/');

				String bitmode = OSDetector.getBitmode();

				if (OSDetector.isWindows()) {
					if (bitmode.equals("64")) {
						sb.append("x64/");
					}

					sb.append("node.exe");
				}
				else {
					sb.append("/node-v");
					sb.append(nodeVersion);
					sb.append('-');

					String os = "linux";

					if (OSDetector.isApple()) {
						os = "darwin";
					}

					sb.append(os);
					sb.append("-x");

					if (bitmode.equals("32")) {
						bitmode = "86";
					}

					sb.append(bitmode);
					sb.append(".tar.gz");
				}

				return sb.toString();
			}

		};

		_npmUrl = new Callable<String>() {

			@Override
			public String call() throws Exception {
				String npmVersion = getNpmVersion();

				if (Validator.isNull(npmVersion)) {
					return null;
				}

				return "http://nodejs.org/dist/npm/npm-" + npmVersion + ".zip";
			}

		};

		_project = project;

		setNpmArgs(
			"--cache",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return FileUtil.getAbsolutePath(getNpmCacheDir());
				}

			});
	}

	public File getNodeDir() {
		return GradleUtil.toFile(_project, _nodeDir);
	}

	public String getNodeUrl() {
		return GradleUtil.toString(_nodeUrl);
	}

	public String getNodeVersion() {
		return GradleUtil.toString(_nodeVersion);
	}

	public List<String> getNpmArgs() {
		return GradleUtil.toStringList(_npmArgs);
	}

	public String getNpmUrl() {
		return GradleUtil.toString(_npmUrl);
	}

	public String getNpmVersion() {
		return GradleUtil.toString(_npmVersion);
	}

	public NodeExtension npmArgs(Iterable<?> npmArgs) {
		GUtil.addToCollection(_npmArgs, npmArgs);

		return this;
	}

	public NodeExtension npmArgs(Object ... npmArgs) {
		return npmArgs(Arrays.asList(npmArgs));
	}

	public void setNodeDir(Object nodeDir) {
		_nodeDir = nodeDir;
	}

	public void setNodeUrl(Object nodeUrl) {
		_nodeUrl = nodeUrl;
	}

	public void setNodeVersion(Object nodeVersion) {
		_nodeVersion = nodeVersion;
	}

	public void setNpmArgs(Iterable<?> npmArgs) {
		_npmArgs.clear();

		npmArgs(npmArgs);
	}

	public void setNpmArgs(Object ... npmArgs) {
		setNpmArgs(Arrays.asList(npmArgs));
	}

	public void setNpmUrl(Object npmUrl) {
		_npmUrl = npmUrl;
	}

	public void setNpmVersion(Object npmVersion) {
		_npmVersion = npmVersion;
	}

	protected File getNpmCacheDir() {
		return new File(getNodeDir(), ".cache");
	}

	private Object _nodeDir;
	private Object _nodeUrl;
	private Object _nodeVersion = "0.12.6";
	private final List<Object> _npmArgs = new ArrayList<>();
	private Object _npmUrl;
	private Object _npmVersion = "1.4.9";
	private final Project _project;

}