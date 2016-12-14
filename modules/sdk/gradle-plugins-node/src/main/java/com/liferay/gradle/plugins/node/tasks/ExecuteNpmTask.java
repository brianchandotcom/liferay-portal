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

import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteNpmTask extends ExecuteNodeScriptTask {

	public ExecuteNpmTask() {
		setCacheDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File nodeDir = getNodeDir();

					if (nodeDir == null) {
						return null;
					}

					return new File(getNodeDir(), ".cache");
				}

			});

		setCommand(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					if (getNodeDir() == null) {
						return "npm";
					}

					return "node";
				}

			});

		setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File nodeDir = getNodeDir();

					if (nodeDir == null) {
						return null;
					}

					return new File(
						getNodeDir(), "lib/node_modules/npm/bin/npm-cli.js");
				}

			});
	}

	@Override
	public void executeNode() throws Exception {
		Project project = getProject();

		File cacheDir = getCacheDir();

		if ((cacheDir != null) &&
			FileUtil.isChild(cacheDir, project.getProjectDir())) {

			super.executeNode();
		}
		else {
			synchronized (ExecuteNpmTask.class) {
				super.executeNode();
			}
		}
	}

	public File getCacheDir() {
		return GradleUtil.toFile(getProject(), _cacheDir);
	}

	public String getRegistry() {
		return GradleUtil.toString(_registry);
	}

	public boolean isProgress() {
		return _progress;
	}

	public void setCacheDir(Object cacheDir) {
		_cacheDir = cacheDir;
	}

	public void setProgress(boolean progress) {
		_progress = progress;
	}

	public void setRegistry(Object registry) {
		_registry = registry;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		File cacheDir = getCacheDir();

		if (cacheDir != null) {
			completeArgs.add("--cache");
			completeArgs.add(FileUtil.getAbsolutePath(cacheDir));
		}

		String logLevel = null;

		Logger logger = getLogger();

		if (logger.isTraceEnabled()) {
			logLevel = "silly";
		}
		else if (logger.isDebugEnabled()) {
			logLevel = "verbose";
		}
		else if (logger.isInfoEnabled()) {
			logLevel = "info";
		}
		else if (logger.isWarnEnabled()) {
			logLevel = "warn";
		}
		else if (logger.isErrorEnabled()) {
			logLevel = "error";
		}

		if (logLevel != null) {
			completeArgs.add("--loglevel");
			completeArgs.add(logLevel);
		}

		completeArgs.add("--progress");
		completeArgs.add(Boolean.toString(isProgress()));

		String registry = getRegistry();

		if (Validator.isNotNull(registry)) {
			completeArgs.add("--registry");
			completeArgs.add(registry);
		}

		return completeArgs;
	}

	private Object _cacheDir;
	private boolean _progress = true;
	private Object _registry;

}