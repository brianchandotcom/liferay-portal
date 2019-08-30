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

package com.liferay.gradle.plugins.snapshot.development;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * @author David Truong
 */
public class SnapshotDevelopmentPlugin implements Plugin<Project> {

	public static final String M2_REPO_PATH = ".m2-tmp/com/liferay/snapshot";

	@Override
	public void apply(Project project) {
		if (!_checkInstalledSnapshots(project)) {
			Logger logger = project.getLogger();

			logger.info("Please run `ant snapshot-repo` task first");

			return;
		}

		for (Project p : project.getAllprojects()) {
			GradleUtil.applyPlugin(p, SnapshotDevelopmentConfigurator.class);
		}
	}

	private boolean _checkInstalledSnapshots(Project project) {
		File rootDir = GradleUtil.getRootDir(project, "portal-impl");

		File snapshotDir = new File(rootDir, M2_REPO_PATH);

		return snapshotDir.exists();
	}

}