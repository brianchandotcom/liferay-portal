/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestBatch<T extends BatchBuildData>
	implements TestBatch<T> {

	@Override
	public void run() {
		try {
			executeBatch();
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}
		finally {
			publishResults();
		}
	}

	protected BaseTestBatch(T batchBuildData, Workspace workspace) {
		_batchBuildData = batchBuildData;
		_workspace = workspace;
	}

	protected abstract void executeBatch() throws AntException;

	protected String getAntOpts(String batchName) {
		return JenkinsResultsParserUtil.combine(
			_getBuildProperty("java.jdk.opts.default.runtime", batchName),
			" -XX:+IgnoreUnrecognizedVMOptions");
	}

	protected T getBatchBuildData() {
		return _batchBuildData;
	}

	protected String getJavaHome(String batchName) {
		return _getBuildProperty("java.jdk.default.compile", batchName);
	}

	protected String getJavaOpts(String batchName) {
		return JenkinsResultsParserUtil.combine(
			_getBuildProperty("java.jdk.opts.default.runtime", batchName),
			" -XX:+IgnoreUnrecognizedVMOptions");
	}

	protected String getPath(String batchName) {
		String path = System.getenv("PATH");

		if (JenkinsResultsParserUtil.isNullOrEmpty(path)) {
			return null;
		}

		Matcher javaHomeMatcher = _javaHomePattern.matcher(path);

		if (javaHomeMatcher.find()) {
			path = path.replace(
				javaHomeMatcher.group(), getJavaHome(batchName));
		}

		return path;
	}

	protected Workspace getWorkspace() {
		return _workspace;
	}

	protected abstract void publishResults();

	private String _getBuildProperty(String baseProperty, String batchName) {
		WorkspaceGitRepository workspaceGitRepository =
			_workspace.getPrimaryWorkspaceGitRepository();

		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(), baseProperty,
				workspaceGitRepository.getUpstreamBranchName(), batchName);
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private static final Pattern _javaHomePattern = Pattern.compile(
		"/opt/java/(jdk|zulu)[^:]+");

	private final T _batchBuildData;
	private final Workspace _workspace;

}