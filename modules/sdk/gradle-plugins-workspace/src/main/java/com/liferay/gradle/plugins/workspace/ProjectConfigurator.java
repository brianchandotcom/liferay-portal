/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public interface ProjectConfigurator extends Plugin<Project> {

	public void configureRootProject(
		Project project, WorkspaceExtension workspaceExtension);

	public Iterable<File> getDefaultRootDirs();

	public String getName();

	public Iterable<File> getProjectDirs(File rootDir);

}