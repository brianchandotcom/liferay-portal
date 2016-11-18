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

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.internal.NodeExecutor;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteNodeTask extends DefaultTask {

	public ExecuteNodeTask() {
		_nodeExecutor = new NodeExecutor(getProject());

		dependsOn(NodePlugin.DOWNLOAD_NODE_TASK_NAME);
	}

	public ExecuteNodeTask args(Iterable<?> args) {
		_nodeExecutor.args(args);

		return this;
	}

	public ExecuteNodeTask args(Object... args) {
		_nodeExecutor.args(args);

		return this;
	}

	@TaskAction
	public void executeNode() throws Exception {
		int npmInstallRetries = getNpmInstallRetries();

		NpmInstallTask npmInstallTask = GradleUtil.fetchTask(
			getProject(), NodePlugin.NPM_INSTALL_TASK_NAME,
			NpmInstallTask.class);

		if ((this instanceof ExecuteNpmTask) || (npmInstallRetries <= 0) ||
			(npmInstallTask == null)) {

			_nodeExecutor.execute();

			return;
		}

		Logger logger = getLogger();

		for (int i = 0; i < npmInstallRetries; i++) {
			try {
				_nodeExecutor.execute();

				break;
			}
			catch (IOException ioe) {
				if (logger.isWarnEnabled()) {
					logger.warn(
						ioe.getMessage() + ". Running \"npm install\" again");
				}

				npmInstallTask.executeNpmInstall(true);
			}
		}
	}

	public List<String> getArgs() {
		return _nodeExecutor.getArgs();
	}

	public String getCommand() {
		return _nodeExecutor.getCommand();
	}

	public File getNodeDir() {
		return _nodeExecutor.getNodeDir();
	}

	public int getNpmInstallRetries() {
		return _npmInstallRetries;
	}

	public File getWorkingDir() {
		return _nodeExecutor.getWorkingDir();
	}

	public boolean isInheritProxy() {
		return _nodeExecutor.isInheritProxy();
	}

	public boolean isUseGradleExec() {
		return _nodeExecutor.isUseGradleExec();
	}

	public void setArgs(Iterable<?> args) {
		_nodeExecutor.setArgs(args);
	}

	public void setArgs(Object... args) {
		_nodeExecutor.setArgs(args);
	}

	public void setCommand(Object command) {
		_nodeExecutor.setCommand(command);
	}

	public void setInheritProxy(boolean inheritProxy) {
		_nodeExecutor.setInheritProxy(inheritProxy);
	}

	public void setNodeDir(Object nodeDir) {
		_nodeExecutor.setNodeDir(nodeDir);
	}

	public void setNpmInstallRetries(int npmInstallRetries) {
		_npmInstallRetries = npmInstallRetries;
	}

	public void setUseGradleExec(boolean useGradleExec) {
		_nodeExecutor.setUseGradleExec(useGradleExec);
	}

	public void setWorkingDir(Object workingDir) {
		_nodeExecutor.setWorkingDir(workingDir);
	}

	private final NodeExecutor _nodeExecutor;
	private int _npmInstallRetries;

}