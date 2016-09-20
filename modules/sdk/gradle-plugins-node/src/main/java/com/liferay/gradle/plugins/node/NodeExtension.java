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

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
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
		_download = GradleUtil.getProperty(project, "nodeDownload", true);

		_nodeDir = new Callable<File>() {

			@Override
			public File call() throws Exception {
				Project curProject = project;

				if (isGlobal()) {
					curProject = curProject.getRootProject();
				}

				return new File(curProject.getBuildDir(), "node");
			}

		};

		_nodeExeUrl = new Callable<String>() {

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

				if (bitmode.equals("64")) {
					if (nodeVersion.charAt(0) != '0') {
						sb.append("win-x64");
					}
					else {
						sb.append("x64");
					}
				}
				else if (nodeVersion.charAt(0) != '0') {
					sb.append("win-x86");
				}

				sb.append("/node.exe");

				return sb.toString();
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
				sb.append("/node-v");
				sb.append(nodeVersion);
				sb.append('-');

				String os = "linux";

				if (OSDetector.isApple()) {
					os = "darwin";
				}

				sb.append(os);
				sb.append("-x");

				String bitmode = OSDetector.getBitmode();

				if (bitmode.equals("32") || OSDetector.isWindows()) {
					bitmode = "86";
				}

				sb.append(bitmode);
				sb.append(".tar.gz");

				return sb.toString();
			}

		};

		_project = project;
	}

	public File getNodeDir() {
		return GradleUtil.toFile(_project, _nodeDir);
	}

	public String getNodeExeUrl() {
		return GradleUtil.toString(_nodeExeUrl);
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

	public boolean isDownload() {
		return _download;
	}

	public boolean isGlobal() {
		return _global;
	}

	public NodeExtension npmArgs(Iterable<?> npmArgs) {
		GUtil.addToCollection(_npmArgs, npmArgs);

		return this;
	}

	public NodeExtension npmArgs(Object... npmArgs) {
		return npmArgs(Arrays.asList(npmArgs));
	}

	public void setDownload(boolean download) {
		_download = download;
	}

	public void setGlobal(boolean global) {
		_global = global;
	}

	public void setNodeDir(Object nodeDir) {
		_nodeDir = nodeDir;
	}

	public void setNodeExeUrl(Object nodeExeUrl) {
		_nodeExeUrl = nodeExeUrl;
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

	public void setNpmArgs(Object... npmArgs) {
		setNpmArgs(Arrays.asList(npmArgs));
	}

	private boolean _download;
	private boolean _global;
	private Object _nodeDir;
	private Object _nodeExeUrl;
	private Object _nodeUrl;
	private Object _nodeVersion = "5.5.0";
	private final List<Object> _npmArgs = new ArrayList<>();
	private final Project _project;

}