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

package com.liferay.jenkins.results.parser.junit;

import static org.junit.Assert.assertTrue;

import com.liferay.jenkins.results.parser.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.JenkinsUtility;

import java.io.File;

import java.net.URI;
import java.net.URL;

import org.apache.tools.ant.Project;

import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageGeneratorTest {

	public FailureMessageGeneratorTest() {
		_project = initProject();
	}

	public void createExpectedResultsFile(Project project, File testRootFile)
		throws Exception {

		URI uri = testRootFile.toURI();

		URL url = uri.toURL();

		String result = FailureMessageGenerator.getFailureMessage(
			project, url.toExternalForm());

		File expectedResultsDir = new File(
			testRootFile.getPath() + "/expected-results");

		expectedResultsDir.mkdir();

		File expectedResultsFile = new File(
			testRootFile.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		JenkinsUtility.writeFile(expectedResultsFile, result);
	}

	public Project getProject() {
		return _project;
	}

	@Test
	public void testAll() throws Exception {
		Project project = initProject();

		File rootFile = new File("test-data");

		File[] files = rootFile.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("\nTesting group: " + file.getName());

				assertTrue(validateGroup(project, file));
			}
		}
	}

	public boolean validateCase(
			Project project, String groupName, File testRootFile)
		throws Exception {

		System.out.print("Testing case: " + testRootFile.getName());

		File expectedResultsFile = new File(
			testRootFile.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		String expectedResults = JenkinsUtility.fileToString(
			expectedResultsFile);

		URI uri = testRootFile.toURI();

		URL url = uri.toURL();

		String actualResults = FailureMessageGenerator.getFailureMessage(
			project, url.toExternalForm());

		boolean passed = actualResults.equals(expectedResults);

		if (!passed) {
			System.out.println(" FAILED");
			System.out.println("actual results: \n" + actualResults);
			System.out.println("expected results: \n" + expectedResults);
		}
		else {
			System.out.println(" PASSED");
		}

		return passed;
	}

	public boolean validateGroup(Project project, File groupRootDir)
		throws Exception {

		String groupName = groupRootDir.getName();

		File[] files = groupRootDir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				if (!validateCase(project, groupName, file)) {
					return false;
				}
			}
		}

		return true;
	}

	protected static Project initProject() {
		Project project = new Project();

		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("repository", "junit-repository");

		return project;
	}

	private static final String _EXPECTED_RESULTS_FILE_PATH =
		"expected-results/FailureMessageGeneratorTest.html";

	private final Project _project;

}