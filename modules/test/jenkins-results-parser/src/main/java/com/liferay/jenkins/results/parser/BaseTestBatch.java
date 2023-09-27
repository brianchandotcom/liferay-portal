/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestBatch<T extends BatchBuildData>
	implements TestBatch<T> {

	public JDK getJDK() {
		return _jdk;
	}

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

		_jdk = JDKFactory.getJDK("jdk8");
	}

	protected abstract void executeBatch() throws AntException;

	protected String getAntOpts(String batchName) {
		return _jdk.getAntOpts();
	}

	protected T getBatchBuildData() {
		return _batchBuildData;
	}

	protected String getJavaHome(String batchName) {
		return _jdk.getJavaHome();
	}

	protected String getPath(String batchName) {
		String path = System.getenv("PATH");

		return path.replaceAll("jdk", _jdk.getName());
	}

	protected Workspace getWorkspace() {
		return _workspace;
	}

	protected abstract void publishResults();

	private final T _batchBuildData;
	private final JDK _jdk;
	private final Workspace _workspace;

}