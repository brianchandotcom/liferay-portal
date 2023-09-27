/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

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

	@Input
	public boolean isFrozenLockFile() {
		return GradleUtil.toBoolean(_frozenLockFile);
	}

	public void setFrozenLockFile(Object frozenLockFile) {
		_frozenLockFile = frozenLockFile;
	}

	@Internal
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