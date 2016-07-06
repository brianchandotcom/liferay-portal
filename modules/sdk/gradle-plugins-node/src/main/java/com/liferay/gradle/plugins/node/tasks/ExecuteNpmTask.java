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

import com.liferay.gradle.plugins.node.util.FileUtil;
import com.liferay.gradle.plugins.node.util.GradleUtil;

import java.io.File;

import java.util.List;
import java.util.concurrent.Callable;

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

	public File getCacheDir() {
		return GradleUtil.toFile(getProject(), _cacheDir);
	}

	public void setCacheDir(Object cacheDir) {
		_cacheDir = cacheDir;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		File cacheDir = getCacheDir();

		if (cacheDir != null) {
			completeArgs.add("--cache");
			completeArgs.add(FileUtil.getAbsolutePath(cacheDir));
		}

		return completeArgs;
	}

	private Object _cacheDir;

}