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

package com.liferay.gradle.plugins.node.util;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.process.ExecResult;
import org.gradle.process.ExecSpec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class NodeExecutor {

	public NodeExecutor(Project project) {
		_project = project;
		_workingDir = _project.getProjectDir();
	}

	public NodeExecutor args(Iterable<?> args) {
		GUtil.addToCollection(_args, args);

		return this;
	}

	public NodeExecutor args(Object ... args) {
		return args(Arrays.asList(args));
	}

	public ExecResult execute() {
		return _project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					if (OSDetector.isWindows()) {
						execSpec.setArgs(getWindowsArgs());
						execSpec.setExecutable("cmd");
					}
					else {
						execSpec.setArgs(_args);
						execSpec.setExecutable(getExecutable());
					}

					execSpec.setEnvironment(getEnvironment());
					execSpec.setWorkingDir(_workingDir);
				}

			});
	}

	public List<String> getArgs() {
		return GradleUtil.toStringList(_args);
	}

	public String getCommand() {
		return GradleUtil.toString(_command);
	}

	public File getNodeDir() {
		return GradleUtil.toFile(_project, _nodeDir);
	}

	public File getWorkingDir() {
		return GradleUtil.toFile(_project, _workingDir);
	}

	public void setArgs(Iterable<?> args) {
		_args.clear();

		args(args);
	}

	public void setArgs(Object ... args) {
		setArgs(Arrays.asList(args));
	}

	public void setCommand(Object command) {
		_command = command;
	}

	public void setNodeDir(Object nodeDir) {
		_nodeDir = nodeDir;
	}

	public void setWorkingDir(Object workingDir) {
		_workingDir = workingDir;
	}

	protected Map<String, String> getEnvironment() {
		Map<String, String> environment = new HashMap<>(System.getenv());

		File executableDir = getExecutableDir();

		for (String pathKey : _PATH_KEYS) {
			String path = environment.get(pathKey);

			if (Validator.isNull(path)) {
				continue;
			}

			path = executableDir.getAbsolutePath() + File.pathSeparator + path;

			environment.put(pathKey, path);
		}

		return environment;
	}

	protected File getExecutable() {
		return new File(getExecutableDir(), GradleUtil.toString(_command));
	}

	protected File getExecutableDir() {
		File executableDir = GradleUtil.toFile(_project, _nodeDir);

		if (!OSDetector.isWindows()) {
			executableDir = new File(executableDir, "bin");
		}

		return executableDir;
	}

	protected List<String> getWindowsArgs() {
		List<String> windowsArgs = new ArrayList<>(2);

		windowsArgs.add("/c");

		StringBuilder sb = new StringBuilder();

		sb.append("\"\"");
		sb.append(getExecutable());
		sb.append('\"');

		for (String arg : getArgs()) {
			sb.append(" \"");

			if (Validator.isNotNull(arg)) {
				sb.append(arg);
			}

			sb.append('\"');
		}

		sb.append('\"');

		windowsArgs.add(sb.toString());

		return windowsArgs;
	}

	private static final String[] _PATH_KEYS = {"Path", "PATH"};

	private final List<Object> _args = new ArrayList<>();
	private Object _command = "node";
	private Object _nodeDir;
	private final Project _project;
	private Object _workingDir;

}