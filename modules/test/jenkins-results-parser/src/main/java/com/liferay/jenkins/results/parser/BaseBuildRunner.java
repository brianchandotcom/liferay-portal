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
public abstract class BaseBuildRunner implements BuildRunner {

	public static final String DIST_ROOT_PATH = "/tmp/dist";

	@Override
	public void run() {
		initWorkspace();

		setUpWorkspace();
	}

	@Override
	public void setUp() {
		setUpWorkspace();
	}

	@Override
	public void tearDown() {
		setUpWorkspace();
	}

	protected BaseBuildRunner(Job job) {
		_job = job;
	}

	protected Job getJob() {
		return _job;
	}

	protected abstract void initWorkspace();

	protected void setUpWorkspace() {
		if (workspace == null) {
			throw new RuntimeException("Workspace is null");
		}

		workspace.setUp(getJob());
	}

	protected Workspace workspace;

	private final Job _job;

}