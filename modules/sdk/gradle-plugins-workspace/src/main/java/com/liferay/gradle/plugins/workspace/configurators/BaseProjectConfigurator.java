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

import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.util.GradleUtil;

import java.io.File;

import java.util.Collections;

import org.gradle.api.GradleException;
import org.gradle.api.initialization.Settings;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseProjectConfigurator implements ProjectConfigurator {

	public BaseProjectConfigurator(Settings settings) {
		File defaultRootDir = new File(
			settings.getRootDir(), getDefaultRootDirName());

		_defaultRootDir = GradleUtil.getProperty(
			settings, getDefaultRootDirPropertyName(), defaultRootDir);
	}

	@Override
	public File getDefaultRootDir() {
		return _defaultRootDir;
	}

	@Override
	public Iterable<File> getProjectDirs(File rootDir) {
		try {
			if (!rootDir.exists()) {
				return Collections.emptySet();
			}

			return doGetProjectDirs(rootDir);
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to get project directories from " + rootDir, e);
		}
	}

	protected abstract Iterable<File> doGetProjectDirs(File rootDir)
		throws Exception;

	protected String getDefaultRootDirName() {
		return getName();
	}

	protected String getDefaultRootDirPropertyName() {
		return WorkspacePlugin.PROPERTY_PREFIX + getName() + ".dir";
	}

	private final File _defaultRootDir;

}