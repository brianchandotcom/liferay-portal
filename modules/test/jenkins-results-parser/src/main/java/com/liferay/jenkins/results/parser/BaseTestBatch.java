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

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestBatch
	<T extends BatchBuildData, S extends Workspace>
		implements TestBatch<T, S> {

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

	protected BaseTestBatch(T batchBuildData, S workspace) {
		_batchBuildData = batchBuildData;
		_jdk = JDKFactory.getJDK(batchBuildData.getBatchName());
		_workspace = workspace;
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

	protected S getWorkspace() {
		return _workspace;
	}

	protected abstract void publishResults();

	private final T _batchBuildData;
	private final JDK _jdk;
	private final S _workspace;

}