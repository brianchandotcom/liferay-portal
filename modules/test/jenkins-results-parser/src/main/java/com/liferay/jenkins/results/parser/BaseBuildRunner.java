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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner implements BuildRunner {

	@Override
	public Job getJob() {
		return job;
	}

	@Override
	public void setupWorkspace() {
		primaryWorkspace.setupWorkspace();

		for (Workspace workspace : _workspaces) {
			workspace.setupWorkspace();
		}
	}

	protected BaseBuildRunner(Job job) {
		this.job = job;
	}

	protected final Job job;
	protected Workspace primaryWorkspace;

	private final List<Workspace> _workspaces = new ArrayList<>();

}