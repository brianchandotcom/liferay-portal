/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class ExecutePackageManagerTask extends ExecuteNodeScriptTask {

	public ExecutePackageManagerTask() {
		_offline = GradleUtil.getProperty(
			getProject(), "nodejs.yarn.offline", false);

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

		setLogLevel(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
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

					return logLevel;
				}

			});
	}

	@Override
	public void executeNode() throws Exception {
		Project project = getProject();

		File cacheDir = getCacheDir();

		if (isCacheConcurrent() ||
			((cacheDir != null) &&
			 FileUtil.isChild(cacheDir, project.getProjectDir()))) {

			super.executeNode();
		}
		else {
			synchronized (ExecutePackageManagerTask.class) {
				super.executeNode();
			}
		}
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getCacheDir() {
		File dir = GradleUtil.toFile(getProject(), _cacheDir);

		if (dir == null) {
			return null;
		}

		Project project = getProject();

		project.mkdir(dir);

		return dir;
	}

	@Input
	@Optional
	public String getLogLevel() {
		return GradleUtil.toString(_logLevel);
	}

	@Internal
	public File getNodeModulesDir() {
		return GradleUtil.toFile(getProject(), _nodeModulesDir);
	}

	@Input
	@Optional
	public String getRegistry() {
		return GradleUtil.toString(_registry);
	}

	@Input
	public boolean isCacheConcurrent() {
		return GradleUtil.toBoolean(_cacheConcurrent);
	}

	@Internal
	public boolean isOffline() {
		return _offline;
	}

	@Input
	public boolean isProduction() {
		return _production;
	}

	@Input
	public boolean isProgress() {
		return _progress;
	}

	@Internal
	public boolean isUseNpm() {
		return GradleUtil.toBoolean(_useNpm);
	}

	public void setCacheConcurrent(Object cacheConcurrent) {
		_cacheConcurrent = cacheConcurrent;
	}

	public void setCacheDir(Object cacheDir) {
		_cacheDir = cacheDir;
	}

	public void setLogLevel(Object logLevel) {
		_logLevel = logLevel;
	}

	public void setNodeModulesDir(Object nodeModulesDir) {
		_nodeModulesDir = nodeModulesDir;
	}

	public void setOffline(boolean offline) {
		_offline = offline;
	}

	public void setProduction(boolean production) {
		_production = production;
	}

	public void setProgress(boolean progress) {
		_progress = progress;
	}

	public void setRegistry(Object registry) {
		_registry = registry;
	}

	public void setUseNpm(Object useNpm) {
		_useNpm = useNpm;
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("--production");
		completeArgs.add(Boolean.toString(isProduction()));

		String registry = getRegistry();

		if (Validator.isNotNull(registry)) {
			completeArgs.add("--registry");
			completeArgs.add(registry);
		}

		if (!isUseNpm()) {
			if (isOffline()) {
				completeArgs.add("--offline");
			}

			return completeArgs;
		}

		File cacheDir = getCacheDir();

		if (cacheDir != null) {
			completeArgs.add("--cache");
			completeArgs.add(FileUtil.getAbsolutePath(cacheDir));
		}

		String logLevel = getLogLevel();

		if (Validator.isNotNull(logLevel)) {
			completeArgs.add("--loglevel");
			completeArgs.add(logLevel);
		}

		completeArgs.add("--progress");
		completeArgs.add(Boolean.toString(isProgress()));

		return completeArgs;
	}

	private Object _cacheConcurrent;
	private Object _cacheDir;
	private Object _logLevel;
	private Object _nodeModulesDir;
	private boolean _offline;
	private boolean _production;
	private boolean _progress = true;
	private Object _registry;
	private Object _useNpm;

}