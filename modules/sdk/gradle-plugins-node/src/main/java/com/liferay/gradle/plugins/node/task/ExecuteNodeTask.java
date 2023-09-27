/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.internal.NodeExecutor;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
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

	public ExecuteNodeTask environment(Map<?, ?> environment) {
		_nodeExecutor.environment(environment);

		return this;
	}

	public ExecuteNodeTask environment(Object key, Object value) {
		_nodeExecutor.environment(key, value);

		return this;
	}

	@TaskAction
	public void executeNode() throws Exception {
		int npmInstallRetries = getNpmInstallRetries();

		NpmInstallTask npmInstallTask = GradleUtil.fetchTask(
			getProject(), NodePlugin.NPM_INSTALL_TASK_NAME,
			NpmInstallTask.class);

		if ((this instanceof ExecutePackageManagerTask) ||
			(npmInstallRetries <= 0) || (npmInstallTask == null)) {

			_result = _nodeExecutor.execute();

			return;
		}

		Logger logger = getLogger();

		for (int i = 1; i <= npmInstallRetries; i++) {
			try {
				_result = _nodeExecutor.execute();

				break;
			}
			catch (IOException ioException) {
				if (i == npmInstallRetries) {
					throw ioException;
				}

				if (logger.isWarnEnabled()) {
					logger.warn(
						ioException.getMessage() +
							". Running \"npm install\" again");
				}

				npmInstallTask.executeNpmInstall(true);
			}
		}
	}

	@Input
	@Optional
	public List<Object> getArgs() {
		return _nodeExecutor.getArgs();
	}

	@Input
	@Optional
	public String getCommand() {
		return _nodeExecutor.getCommand();
	}

	@Input
	@Optional
	public Map<?, ?> getEnvironment() {
		return _nodeExecutor.getEnvironment();
	}

	@Internal
	public File getNodeDir() {
		return _nodeExecutor.getNodeDir();
	}

	@Input
	public int getNpmInstallRetries() {
		return _npmInstallRetries;
	}

	@Internal
	public String getResult() {
		if (_result == null) {
			return "";
		}

		return _result;
	}

	@Internal
	public File getWorkingDir() {
		return _nodeExecutor.getWorkingDir();
	}

	@Input
	public boolean isInheritProxy() {
		return _nodeExecutor.isInheritProxy();
	}

	@Input
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

	public void setEnvironment(Map<?, ?> environment) {
		_nodeExecutor.setEnvironment(environment);
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
	private String _result;

}