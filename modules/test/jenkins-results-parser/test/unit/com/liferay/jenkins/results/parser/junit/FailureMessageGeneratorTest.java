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

import org.apache.tools.ant.Project;

import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageGeneratorTest {

	public FailureMessageGeneratorTest() {
		_project = initProject();
	}

	public void createExpectedResultsFile(Project project, File testRoot)
		throws Exception {

		String result = FailureMessageGenerator.getFailureMessage(
			project, testRoot.toURI().toURL().toExternalForm());

		new File(testRoot.getPath() + "/expected-results").mkdir();

		File file = new File(
			testRoot.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		JenkinsUtility.writeFile(file, result);
	}

	public Project getProject() {
		return _project;
	}

	@Test
	public void testAll() throws Exception {
		Project project = initProject();

		File root = new File("test-data");

		File[] fileArray = root.listFiles();

		for (File file : fileArray) {
			if (file.isDirectory()) {
				System.out.println("\nTesting group: " + file.getName());

				assertTrue(validateGroup(project, file));
			}
		}
	}

	public boolean validateCase(
			Project project, String groupName, File testRoot)
		throws Exception {

		String name = testRoot.getName();

		System.out.print("Testing case: " + name);

		File expectedResultsFile = new File(
			testRoot.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		String expectedResults = JenkinsUtility.fileToString(
			expectedResultsFile);

		String url = testRoot.toURI().toURL().toExternalForm();
		String results = FailureMessageGenerator.getFailureMessage(
			project, url);

		boolean passed = results.equals(expectedResults);

		if (!passed) {
			System.out.println(" FAILED");
			System.out.println("results: \n" + results);
			System.out.println("expected results: \n" + expectedResults);
		}
		else {
			System.out.println(" PASSED");
		}

		return passed;
	}

	public boolean validateGroup(Project project, File groupRoot)
		throws Exception {

		String name = groupRoot.getName();

		File[] fileArray = groupRoot.listFiles();

		for (File file : fileArray) {
			if (file.isDirectory()) {
				if (!validateCase(project, name, file)) {
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