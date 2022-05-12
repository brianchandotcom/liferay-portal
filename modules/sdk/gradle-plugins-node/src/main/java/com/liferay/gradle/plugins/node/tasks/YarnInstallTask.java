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

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Shin
 * @author David Truong
 */
public class YarnInstallTask extends ExecutePackageManagerTask {

	@Override
	public synchronized void executeNode() throws Exception {
		File yarnrcFile = _getYarnrcFile();

		if (!yarnrcFile.exists()) {
			_createYarnrcFile(yarnrcFile);
		}

		super.executeNode();
	}

	public boolean isFrozenLockFile() {
		return GradleUtil.toBoolean(_frozenLockFile);
	}

	public void setFrozenLockFile(Object frozenLockFile) {
		_frozenLockFile = frozenLockFile;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("install");

		if (isFrozenLockFile()) {
			completeArgs.add("--frozen-lockfile");
		}

		completeArgs.add("--ignore-engines");

		return completeArgs;
	}

	private void _createYarnrcFile(File yarnrcFile) throws Exception {
		List<String> contents = new ArrayList<>();

		contents.add("disable-self-update-check true");
		contents.add("yarn-offline-mirror \"./node_modules_cache\"");
		contents.add("yarn-offline-mirror-pruning true");

		FileUtil.write(yarnrcFile, contents);
	}

	private File _getYarnrcFile() {
		return new File(getWorkingDir(), ".yarnrc");
	}

	private Object _frozenLockFile;

}