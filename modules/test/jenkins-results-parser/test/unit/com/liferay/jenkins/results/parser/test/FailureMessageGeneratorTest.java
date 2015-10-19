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

package com.liferay.jenkins.results.parser.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.tools.ant.Project;
import org.junit.Test;

import com.liferay.jenkins.results.parser.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.JenkinsUtility;

/**
 * @author Peter Yoo
 */
public class FailureMessageGeneratorTest implements ResultsParserTest {

	public void createExpectedResultsFile(Project project, File testRoot) throws Exception {
		String result = FailureMessageGenerator.getFailureMessage(project, testRoot.toURI().toURL().toExternalForm());

		new File(testRoot.getPath() + "/expected-results").mkdir();
		
		File file = new File(testRoot.getPath() + "/" + _expectedResultsFilePath);
		
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
				System.out.println("Testing group: " + file.getName());
				
				testGroup(project, file);
			}
		}
	}
	
	public void testCase(Project project, String groupName, File testRoot) throws Exception {
		String name = testRoot.getName();
		
		System.out.print("Testing case: " + name);
		
		File expectedResultsFile = new File(testRoot.getPath() + "/" + _expectedResultsFilePath);
		
		String assertMessageHeader = "Test case: " + groupName + ":" + name + " - ";
		
		String expectedResults = JenkinsUtility.fileToString(expectedResultsFile);
		
		String url = testRoot.toURI().toURL().toExternalForm();
		String results = FailureMessageGenerator.getFailureMessage(project, url);
		
		assertEquals(assertMessageHeader +
				"actual results did not match expected results.",
				results, expectedResults);
		
		System.out.println(" PASSED");
	}	

	public void testGroup(Project project, File groupRoot) throws Exception {
		String name = groupRoot.getName();
		
		File[] fileArray = groupRoot.listFiles();
		
		for (File file : fileArray) {
			if (file.isDirectory()) {
				testCase(project, name, file);
			}
		}
	}
	
	public FailureMessageGeneratorTest() {
		_project = initProject();
	}
		
	protected static Project initProject() {
		Project project = new Project();

		project.setProperty("github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty("github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("repository", "junit-repository");
	
		return project;
	}
	
	private Project _project;

}
