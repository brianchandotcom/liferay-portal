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
	<T extends BatchBuildData, S extends Workspace> implements TestBatch<T, S> {

	@Override
	public void run() {
		executeBatch();

		publishResults();
	}

	protected BaseTestBatch(T batchBuildData, S workspace) {
		_batchBuildData = batchBuildData;
		_workspace = workspace;
	}

	protected abstract void executeBatch();

	protected String getAntOpts(String batchName) {
		String antOpts = System.getenv("ANT_OPTS");

		if (batchName.endsWith("-jdk7")) {
			return antOpts.replace("MetaspaceSize", "PermSize");
		}

		if (batchName.endsWith("-jdk8")) {
			return antOpts.replace("PermSize", "MetaspaceSize");
		}

		return antOpts;
	}

	protected T getBatchBuildData() {
		return _batchBuildData;
	}

	protected String getJavaHome(String batchName) {
		if (batchName.endsWith("-jdk7")) {
			return "/opt/java/jdk8";
		}

		if (batchName.endsWith("-jdk8")) {
			return "/opt/java/jdk8";
		}

		return "/opt/java/jdk";
	}

	protected String getPath(String batchName) {
		String path = System.getenv("PATH");

		if (batchName.endsWith("-jdk7")) {
			return path.replace("jdk", "jdk7");
		}

		if (batchName.endsWith("-jdk8")) {
			return path.replace("jdk", "jdk8");
		}

		return path;
	}

	protected S getWorkspace() {
		return _workspace;
	}

	protected abstract void publishResults();

	private final T _batchBuildData;
	private final S _workspace;

}