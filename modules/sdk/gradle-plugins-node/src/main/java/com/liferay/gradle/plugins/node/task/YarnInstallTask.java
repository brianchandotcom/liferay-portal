/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

/**
 * @author Peter Shin
 * @author David Truong
 */
public class YarnInstallTask extends ExecutePackageManagerTask {

	public YarnInstallTask() {
		String installArgs = GradleUtil.getProperty(
			getProject(), "nodejs.yarn.install.args", "");

		if (Validator.isNotNull(installArgs)) {
			installArgs = installArgs.trim();

			setInstallArgs(installArgs.split("\\s+"));
		}

		String networkTimeout = GradleUtil.getProperty(
			getProject(), "nodejs.yarn.install.network.timeout", (String)null);

		if (Validator.isNotNull(networkTimeout)) {
			_networkTimeout = Long.parseLong(networkTimeout);
		}

		_preferOffline = GradleUtil.getProperty(
			getProject(), "nodejs.yarn.install.prefer.offline", true);
	}

	@Override
	public synchronized void executeNode() throws Exception {
		File yarnrcFile = _getYarnrcFile();

		if (!yarnrcFile.exists()) {
			_createYarnrcFile(yarnrcFile);
		}

		super.executeNode();
	}

	@Input
	@Optional
	public List<String> getInstallArgs() {
		return GradleUtil.toStringList(_installArgs);
	}

	@Input
	public long getNetworkTimeout() {
		return _networkTimeout;
	}

	@Input
	public boolean isFrozenLockFile() {
		return GradleUtil.toBoolean(_frozenLockFile);
	}

	@Input
	public boolean isPreferOffline() {
		return _preferOffline;
	}

	public void setFrozenLockFile(Object frozenLockFile) {
		_frozenLockFile = frozenLockFile;
	}

	public void setInstallArgs(Iterable<?> installArgs) {
		_installArgs.clear();

		for (Object installArg : installArgs) {
			_installArgs.add(installArg);
		}
	}

	public void setInstallArgs(Object... installArgs) {
		setInstallArgs(Arrays.asList(installArgs));
	}

	public void setNetworkTimeout(long networkTimeout) {
		_networkTimeout = networkTimeout;
	}

	public void setPreferOffline(boolean preferOffline) {
		_preferOffline = preferOffline;
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		List<String> installArgs = getInstallArgs();

		if (!installArgs.isEmpty()) {
			completeArgs.addAll(installArgs);

			return completeArgs;
		}

		completeArgs.add("install");

		if (isFrozenLockFile()) {
			completeArgs.add("--frozen-lockfile");
		}

		completeArgs.add("--ignore-engines");

		long networkTimeout = getNetworkTimeout();

		if (networkTimeout > 0) {
			completeArgs.add("--network-timeout");
			completeArgs.add(String.valueOf(networkTimeout));
		}

		if (isPreferOffline()) {
			completeArgs.add("--prefer-offline");
		}

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
	private final List<Object> _installArgs = new ArrayList<>();
	private long _networkTimeout = 120000;
	private boolean _preferOffline;

}