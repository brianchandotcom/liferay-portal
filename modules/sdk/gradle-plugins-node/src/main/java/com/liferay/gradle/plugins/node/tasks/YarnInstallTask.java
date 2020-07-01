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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.AntBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Peter Shin
 * @author David Truong
 */
@CacheableTask
public class YarnInstallTask extends ExecutePackageManagerTask {

	@Override
	public synchronized void executeNode() throws Exception {
		final File scriptFile = getScriptFile();

		if (scriptFile == null || !scriptFile.exists()) {
			File yarnFile = _download(getYarnUrl(), getYarnDir());

			setScriptFile(yarnFile);
		}

		File yarnrcFile = _getYarnrcFile();

		if (!yarnrcFile.exists()) {
			_createYarnrcFile(yarnrcFile);
		}

		super.executeNode();
	}

	@OutputDirectory
	public File getYarnDir() {
		return GradleUtil.toFile(getProject(), _yarnDir);
	}

	@Input
	public String getYarnUrl() {
		return GradleUtil.toString(_yarnUrl);
	}

	public boolean isFrozenLockFile() {
		return GradleUtil.toBoolean(_frozenLockFile);
	}

	public void setFrozenLockFile(Object frozenLockFile) {
		_frozenLockFile = frozenLockFile;
	}

	public void setYarnDir(Object yarnDir) {
		_yarnDir = yarnDir;
	}

	public void setYarnUrl(Object yarnUrl) {
		_yarnUrl = yarnUrl;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("install");

		if (isFrozenLockFile()) {
			completeArgs.add("--frozen-lockfile");
		}

		return completeArgs;
	}

	private void _createYarnrcFile(File yarnrcFile) throws Exception {
		List<String> contents = new ArrayList<>(2);

		contents.add("disable-self-update-check true");
		contents.add("yarn-offline-mirror \"./node_modules_cache\"");
		contents.add("yarn-offline-mirror-pruning true");

		FileUtil.write(yarnrcFile, contents);
	}

	private File _download(String url, File destinationFile) throws Exception {
		if (!url.endsWith(".js")) {
			throw new GradleException("Please provide the JS release url");
		}

		String protocol = url.substring(0, url.indexOf(':'));

		String proxyPassword = System.getProperty(protocol + ".proxyPassword");
		String proxyUser = System.getProperty(protocol + ".proxyUser");

		if (Validator.isNotNull(proxyPassword) &&
			Validator.isNotNull(proxyUser)) {

			Project project = getProject();

			String nonProxyHosts = System.getProperty(
				protocol + ".nonProxyHosts");
			String proxyHost = System.getProperty(protocol + ".proxyHost");
			String proxyPort = System.getProperty(protocol + ".proxyPort");

			AntBuilder antBuilder = project.getAnt();

			Map<String, String> args = new HashMap<>();

			args.put("nonproxyhosts", nonProxyHosts);
			args.put("proxyhost", proxyHost);
			args.put("proxypassword", proxyPassword);
			args.put("proxyport", proxyPort);
			args.put("proxyuser", proxyUser);

			antBuilder.invokeMethod("setproxy", args);
		}

		return FileUtil.get(getProject(), url, destinationFile);
	}

	private File _getYarnrcFile() {
		return new File(getWorkingDir(), ".yarnrc");
	}

	private Object _frozenLockFile;
	private Object _yarnDir;
	private Object _yarnUrl;

}