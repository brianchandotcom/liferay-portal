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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yi-Chen Tsai
 */
public class ModulesJUnitBatchTestClassGroup extends JUnitBatchTestClassGroup {

	protected ModulesJUnitBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);
	}

	@Override
	protected List<String> getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		List<File> modifiedModuleDirsList = null;

		try {
			modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		for (File modifiedModuleDir : modifiedModuleDirsList) {
			String modifiedModuleAbsolutePath =
				modifiedModuleDir.getAbsolutePath();

			String modifiedModuleRelativePath =
				modifiedModuleAbsolutePath.substring(
					modifiedModuleAbsolutePath.indexOf("modules/"));

			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeGlobs) {

				relevantTestClassNameRelativeGlobs.add(
					JenkinsResultsParserUtil.combine(
						modifiedModuleRelativePath, "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					relevantTestClassNameRelativeGlobs.add(
						JenkinsResultsParserUtil.combine(
							modifiedModuleRelativePath, "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}
		}

		return relevantTestClassNameRelativeGlobs;
	}

}