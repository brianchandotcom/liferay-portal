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

package com.liferay.gradle.plugins.workspace.configurators;

import java.io.File;

import java.util.Collections;

import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;

/**
 * @author Andrea Di Giorgi
 */
public class PluginsProjectConfigurator extends BaseProjectConfigurator {

	public PluginsProjectConfigurator(Settings settings) {
		super(settings);
	}

	@Override
	public void apply(Project project) {
	}

	@Override
	public String getName() {
		return _NAME;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		return Collections.emptySet();
	}

	private static final String _NAME = "plugins";

}