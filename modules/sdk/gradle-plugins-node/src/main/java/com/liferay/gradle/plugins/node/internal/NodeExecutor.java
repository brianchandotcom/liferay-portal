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

package com.liferay.gradle.plugins.node.internal;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.process.ExecSpec;
import org.gradle.util.CollectionUtils;
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

	public NodeExecutor args(Object... args) {
		return args(Arrays.asList(args));
	}

	public void execute() throws Exception {
		File workingDir = getWorkingDir();

		workingDir.mkdirs();

		if (isUseGradleExec()) {
			_executeGradleExec();
		}
		else {
			_executeProcessBuilder();
		}
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

	public boolean isInheritProxy() {
		return _inheritProxy;
	}

	public boolean isUseGradleExec() {
		return _useGradleExec;
	}

	public void setArgs(Iterable<?> args) {
		_args.clear();

		args(args);
	}

	public void setArgs(Object... args) {
		setArgs(Arrays.asList(args));
	}

	public void setCommand(Object command) {
		_command = command;
	}

	public void setInheritProxy(boolean inheritProxy) {
		_inheritProxy = inheritProxy;
	}

	public void setNodeDir(Object nodeDir) {
		_nodeDir = nodeDir;
	}

	public void setUseGradleExec(boolean useGradleExec) {
		_useGradleExec = useGradleExec;
	}

	public void setWorkingDir(Object workingDir) {
		_workingDir = workingDir;
	}

	private void _executeGradleExec() {
		_project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.setCommandLine(_getCommandLine());
					execSpec.setEnvironment(
						_getEnvironment(execSpec.getEnvironment()));
					execSpec.setWorkingDir(getWorkingDir());
				}

			});
	}

	private void _executeProcessBuilder() throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(_getCommandLine());

		processBuilder.directory(getWorkingDir());
		processBuilder.inheritIO();

		_updateEnvironment(processBuilder.environment());

		if (_logger.isInfoEnabled()) {
			_logger.info(
				"Running {} from {}", processBuilder.command(),
				processBuilder.directory());
		}

		Process process = processBuilder.start();

		int exitValue = process.waitFor();

		if (exitValue != 0) {
			throw new IOException(
				"Process '" + processBuilder.command() +
					"' finished with non-zero exit value " + exitValue);
		}
	}

	private List<String> _getCommandLine() {
		List<String> commandLine = new ArrayList<>();

		if (OSDetector.isWindows()) {
			commandLine.add("cmd");
			commandLine.addAll(_getWindowsArgs());
		}
		else {
			commandLine.add(_getExecutable());
			commandLine.addAll(getArgs());
		}

		return commandLine;
	}

	private Map<String, String> _getEnvironment(Map<?, ?> environment) {
		Map<String, String> newEnvironment = new HashMap<>();

		GUtil.addToMap(newEnvironment, environment);

		_updateEnvironment(newEnvironment);

		return newEnvironment;
	}

	private String _getExecutable() {
		String executable = GradleUtil.toString(_command);

		File executableDir = _getExecutableDir();

		if (executableDir != null) {
			File executableFile = new File(executableDir, executable);

			executable = executableFile.getAbsolutePath();
		}

		return executable;
	}

	private File _getExecutableDir() {
		File nodeDir = getNodeDir();

		if (nodeDir == null) {
			return null;
		}

		return new File(nodeDir, "bin");
	}

	private List<String> _getWindowsArgs() {
		List<String> windowsArgs = new ArrayList<>(2);

		windowsArgs.add("/c");

		StringBuilder sb = new StringBuilder();

		sb.append('"');

		String executable = _getExecutable();

		if (executable.indexOf(File.separatorChar) == -1) {
			sb.append(executable);
		}
		else {
			sb.append('"');
			sb.append(executable);
			sb.append('"');
		}

		for (String arg : getArgs()) {
			sb.append(" \"");

			if (Validator.isNotNull(arg)) {
				sb.append(arg);
			}

			sb.append('"');
		}

		sb.append('"');

		windowsArgs.add(sb.toString());

		return windowsArgs;
	}

	private void _setNonProxyHosts(Map<String, String> environment) {
		if (environment.containsKey(_NO_PROXY_KEY) ||
			environment.containsKey(_NO_PROXY_KEY.toUpperCase())) {

			if (_logger.isInfoEnabled()) {
				_logger.info("Non-proxy hosts are already set");
			}

			return;
		}

		Set<String> nonProxyHosts = new LinkedHashSet<>();

		String hosts = System.getProperty("http.nonProxyHosts");

		if (Validator.isNotNull(hosts)) {
			nonProxyHosts.addAll(Arrays.asList(hosts.split("\\|")));
		}

		hosts = System.getProperty("https.nonProxyHosts");

		if (Validator.isNotNull(hosts)) {
			nonProxyHosts.addAll(Arrays.asList(hosts.split("\\|")));
		}

		if (nonProxyHosts.isEmpty()) {
			return;
		}

		hosts = CollectionUtils.join(",", nonProxyHosts);

		environment.put(_NO_PROXY_KEY, hosts);

		if (_logger.isInfoEnabled()) {
			_logger.info("Non-proxy hosts set to {}", hosts);
		}
	}

	private void _setProxy(Map<String, String> environment, String protocol) {
		String key = protocol + "_proxy";

		if (environment.containsKey(key) ||
			environment.containsKey(key.toUpperCase())) {

			if (_logger.isInfoEnabled()) {
				_logger.info("{} proxy is already set", protocol.toUpperCase());
			}

			return;
		}

		String host = System.getProperty(protocol + ".proxyHost");
		String port = System.getProperty(protocol + ".proxyPort");

		if (Validator.isNull(host) || Validator.isNull(port)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(protocol);
		sb.append("://");

		String user = System.getProperty(protocol + ".proxyUser");

		if (Validator.isNotNull(user)) {
			sb.append(user);

			String password = System.getProperty(protocol + ".proxyPassword");

			if (Validator.isNotNull(password)) {
				sb.append(':');
				sb.append(password);
				sb.append('@');
			}
		}

		sb.append(host);
		sb.append(':');
		sb.append(port);

		String url = sb.toString();

		if (_logger.isInfoEnabled()) {
			_logger.info("{} proxy set to {}", protocol.toUpperCase(), url);
		}

		environment.put(key, sb.toString());
	}

	private void _updateEnvironment(Map<String, String> environment) {
		if (isInheritProxy()) {
			_setNonProxyHosts(environment);
			_setProxy(environment, "http");
			_setProxy(environment, "https");
		}

		File executableDir = _getExecutableDir();

		if (executableDir != null) {
			for (String pathKey : _PATH_KEYS) {
				String path = environment.get(pathKey);

				if (Validator.isNull(path)) {
					continue;
				}

				path =
					executableDir.getAbsolutePath() + File.pathSeparator + path;

				environment.put(pathKey, path);
			}
		}
	}

	private static final String _NO_PROXY_KEY = "no_proxy";

	private static final String[] _PATH_KEYS = {"Path", "PATH"};

	private static final Logger _logger = Logging.getLogger(NodeExecutor.class);

	private final List<Object> _args = new ArrayList<>();
	private Object _command = "node";
	private boolean _inheritProxy = true;
	private Object _nodeDir;
	private final Project _project;
	private boolean _useGradleExec;
	private Object _workingDir;

}