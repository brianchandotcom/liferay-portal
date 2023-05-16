/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.go.task;

import com.liferay.gradle.plugins.go.GoExtension;
import com.liferay.gradle.plugins.go.GoPlugin;
import com.liferay.gradle.plugins.go.internal.GoExecutor;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Peter Shin
 */
@CacheableTask
public class ExecuteGoTask extends DefaultTask {

	public ExecuteGoTask() {
		_goExecutor = new GoExecutor(getProject());

		dependsOn(GoPlugin.DOWNLOAD_GO_TASK_NAME);

		GoExtension goExtension = GradleUtil.getExtension(
			getProject(), GoExtension.class);

		setGoDir(goExtension.getGoDir());
		setWorkingDir(goExtension.getWorkingDir());
	}

	public ExecuteGoTask args(Iterable<?> args) {
		_goExecutor.args(args);

		return this;
	}

	public ExecuteGoTask args(Object... args) {
		_goExecutor.args(args);

		return this;
	}

	public ExecuteGoTask environment(Map<?, ?> environment) {
		_goExecutor.environment(environment);

		return this;
	}

	public ExecuteGoTask environment(Object key, Object value) {
		_goExecutor.environment(key, value);

		return this;
	}

	@TaskAction
	public void executeGo() throws Exception {
		_goExecutor.execute();
	}

	@Input
	public List<Object> getArgs() {
		return _goExecutor.getArgs();
	}

	@Input
	public String getCommand() {
		return _goExecutor.getCommand();
	}

	@Input
	public Map<?, ?> getEnvironment() {
		return _goExecutor.getEnvironment();
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getGoDir() {
		return _goExecutor.getGoDir();
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getWorkingDir() {
		return _goExecutor.getWorkingDir();
	}

	@Input
	public boolean isInheritProxy() {
		return _goExecutor.isInheritProxy();
	}

	@Input
	public boolean isUseGradleExec() {
		return _goExecutor.isUseGradleExec();
	}

	public void setArgs(Iterable<?> args) {
		_goExecutor.setArgs(args);
	}

	public void setArgs(Object... args) {
		_goExecutor.setArgs(args);
	}

	public void setCommand(Object command) {
		_goExecutor.setCommand(command);
	}

	public void setEnvironment(Map<?, ?> environment) {
		_goExecutor.setEnvironment(environment);
	}

	public void setGoDir(Object goDir) {
		_goExecutor.setGoDir(goDir);
	}

	public void setInheritProxy(boolean inheritProxy) {
		_goExecutor.setInheritProxy(inheritProxy);
	}

	public void setUseGradleExec(boolean useGradleExec) {
		_goExecutor.setUseGradleExec(useGradleExec);
	}

	public void setWorkingDir(Object workingDir) {
		_goExecutor.setWorkingDir(workingDir);
	}

	private final GoExecutor _goExecutor;

}