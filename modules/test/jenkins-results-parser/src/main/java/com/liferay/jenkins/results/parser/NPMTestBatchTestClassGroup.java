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

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class NPMTestBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(axisId);

		if (axisTestClassGroup != null) {
			return axisTestClassGroups.get(axisId);
		}

		return new AxisTestClassGroup(this, axisId);
	}

	protected NPMTestBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		_setTestClassFiles();

		_setAxisTestClassGroups();
	}

	private void _setAxisTestClassGroups() {
		if (!testClassFiles.isEmpty()) {
			AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(
				this, 0);

			for (File testClassFile : testClassFiles) {
				axisTestClassGroup.addTestClassFile(testClassFile);
			}

			axisTestClassGroups.put(0, axisTestClassGroup);
		}
	}

	private void _setTestClassFiles() {
		if (testRelevantChanges) {
			try {
				List<File> moduleDirs =
					portalGitWorkingDirectory.
						getModifiedNPMTestModuleDirsList();

				for (File moduleDir : moduleDirs) {
					testClassFiles.add(moduleDir);
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
		else {
			testClassFiles.add(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					"modules"));
		}
	}

}