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

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.apache.tools.ant.Project;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadSlaveDependency(
			"AXIS_VARIABLE=0,label_exp=!master", "129", "generic-fail",
			"test-portal-acceptance-pullrequest-batch(master)", "test-4-1");
//		_downloadSlaveDependency(
//			"AXIS_VARIABLE=2,label_exp=!master", "1904", "generic-fail",
//			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19");
		_downloadSlaveDependency(
			null, "59", "rebase-fail",
			"test-portal-acceptance-pullrequest(ee-6.2.x)", "test-1-16");
	}

	public FailureMessageUtilTest() {
		_project = _initProject();
	}

	@Test
	public void testGetFailureMessage() throws Exception {
		File[] files = _testDependenciesDir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				assertTrue(validateCase(_project, file));
			}
		}
	}

	private static URL _createURL(String urlString) throws Exception {
		URL url = new URL(urlString);

		return _encode(url);
	}

	private static void _deleteFile(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		}
		else {
			File[] files = file.listFiles();

			for (File childFile : files) {
				_deleteFile(childFile);
			}

			file.delete();
		}
	}

	private static void _downloadSlaveDependency(
			String slaveDependencyIdentifier, URL slaveDependencyURL)
		throws Exception {

		System.out.println(
			"downloading slave results: " + slaveDependencyIdentifier);

		String slaveDependencyRootPath =
			_testDependenciesDir.getPath() + "/" + slaveDependencyIdentifier;

		String slaveLogTextPath = slaveDependencyRootPath + "/logText";
		String slaveApiPath = slaveDependencyRootPath + "/api";

		File slaveLogTextDir = new File(slaveLogTextPath);
		File slaveApiDir = new File(slaveApiPath);
		
		if (slaveLogTextDir.exists()) {
			return;
		}

		slaveLogTextDir.mkdirs();
		slaveApiDir.mkdirs();

		try {
			String jsonURL = slaveDependencyURL.toString() + "/api/json";

			System.out.println(" downloading json from:" + jsonURL);

			String jsonString = JenkinsResultsParserUtil.toString(jsonURL);

			File jsonFile = new File(slaveApiDir.getPath() + "/json");

			_write(jsonFile, jsonString);

			System.out.println(
				" wrote file: " + jsonFile.getPath() + " size; " +
					jsonFile.length());

			String consoleURL = slaveDependencyURL.toString() + "/logText/progressiveText";

			System.out.println(" downloading console from:" + consoleURL);

			String console = JenkinsResultsParserUtil.toString(consoleURL);

			File consoleFile = new File(
				slaveLogTextDir.getPath() + "/progressiveText");

			_write(consoleFile, console);

			System.out.println(
				" wrote file: " + consoleFile.getPath() + " size; " +
					consoleFile.length());
			
			FailureMessageUtilTest failureMessageUtilTest =
				new FailureMessageUtilTest();
			
			failureMessageUtilTest.createExpectedResultsFile(
				failureMessageUtilTest._project, new File(slaveDependencyRootPath));
			
		}
		catch (IOException ioe) {
			_deleteFile(slaveLogTextDir);

			_deleteFile(slaveApiDir);

			throw ioe;
		}
	}

	private static void _downloadSlaveDependency(
			String axis, String buildNumber, String description, String jobName,
			String hostName)
		throws Exception {
		
		String urlString =
			"https://${hostName}.liferay.com/userContent/jobs/${jobName}/" +
				"/builds/${buildNumber}/";

		String slaveDependencyIdentifier = 
			description + "_" + jobName + "_" + hostName + "_" + buildNumber; 

		if (axis != null) {
			urlString = "https://${hostName}.liferay.com/job/${jobName}/" + 
				"${axis}/${buildNumber}/";
			slaveDependencyIdentifier += "_" + axis;
			urlString = _replaceToken(urlString, "axis", axis);
		}

		urlString = _replaceToken(urlString, "buildNumber", buildNumber);
		urlString = _replaceToken(urlString, "hostName", hostName);
		urlString = _replaceToken(urlString, "jobName", jobName);

		URL url = _createURL(urlString);

		_downloadSlaveDependency(slaveDependencyIdentifier, url);
	}

	/*
	private static void _downloadPullRequestDependencies(
			String pullRequestIdentifier, URL jenkinsReportURL)
		throws Exception {

		FailureMessageUtilTest failureMessageUtilTest =
			new FailureMessageUtilTest();

		SAXReader saxReader = new SAXReader();

		File groupReportRootDir = null;

		try {
			Document document = saxReader.read(jenkinsReportURL);

			groupReportRootDir = new File(
				_testDependenciesDir.getPath() + "/" + pullRequestIdentifier);

			if (groupReportRootDir.exists()) {
				return;
			}

			groupReportRootDir.mkdir();

			File jenkinsReportFile = new File(
				groupReportRootDir.getPath() + "/jenkins-report.html");

			_write(jenkinsReportFile, document);

			List<Node> nodes = _selectNodes(document);

			Project project = failureMessageUtilTest._project;

			int i = 0;

			for (Node node : nodes) {
				DefaultElement defaultElement = (DefaultElement)node;

				Element parentElement = defaultElement.getParent();
				
				String text = parentElement.getText();  

				if (!text.endsWith("FAILURE")) {
					continue;
				}

				Attribute attribute = defaultElement.attribute("href");

				String urlString = attribute.getValue();

				try {
					String caseRootPath = _downloadSlaveDependency(
						pullRequestIdentifier, urlString);

					failureMessageUtilTest.createExpectedResultsFile(
						project, new File(caseRootPath));

					i++;
				}
				catch (FileNotFoundException fnfe) {
					System.err.println(
						"Data was not found on server for " +
							urlString + " skipped.");
				}
			}

			System.out.println(
				"Test data creation is complete. " + i +
				" test groups were created.");
		}
		catch (Exception e) {
			if ((groupReportRootDir != null) && groupReportRootDir.exists()) {
				_deleteFile(groupReportRootDir);
			}

			throw e;
		}
	}*/

	private static URL _encode(URL url) throws Exception {
		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		return new URL(uri.toASCIIString());
	}

	private static String _read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	/*
	private static List<Node> _selectNodes(Document document) {
		return document.selectNodes("//ul/li[not (ul)]//a[1]");
	}*/

	private static Project _initProject() {
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

	private static String _replaceToken(
		String string, String token, String value) {

		if (string == null) {
			return string;
		}

		return string.replace("${" + token + "}", value);
	}

	private static void _write(File file, String content) throws Exception {
		Files.write(Paths.get(file.toURI()), content.getBytes());
	}

	/*
	private static void _write(File file, Document document) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		XMLWriter xmlWriter = new XMLWriter(
			byteArrayOutputStream, OutputFormat.createPrettyPrint());

		xmlWriter.write(document);

		_write(file, new String(byteArrayOutputStream.toByteArray(), "UTF8"));
	}*/

	protected void createExpectedResultsFile(Project project, File testRoot)
		throws Exception {

		String failureMessage = FailureMessageUtil.getFailureMessage(
			project, testRoot.toURI().toURL().toExternalForm());

		new File(testRoot.getPath() + "/expected-results").mkdir();

		File file = new File(
			testRoot.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		_write(file, failureMessage);
	}

	protected boolean validateCase(
			Project project, File testRoot)
		throws Exception {

		String name = testRoot.getName();

		System.out.print("Testing case: " + name);

		File expectedResultsFile = new File(
			testRoot.getPath() + "/" + _EXPECTED_RESULTS_FILE_PATH);

		String expectedResults = _read(expectedResultsFile);

		String url = testRoot.toURI().toURL().toExternalForm();
		String results = FailureMessageUtil.getFailureMessage(project, url);

		boolean passed = results.equals(expectedResults);

		if (!passed) {
			System.out.println("name: " + ":" + name + ": FAILED");
			System.out.println("results: \n" + results);
			System.out.println("expected results: \n" + expectedResults);
		}
		else {
			System.out.println(" PASSED");
		}

		return passed;
	}

	/*
	protected boolean validateGroup(Project project, File dir)
		throws Exception {

		String name = dir.getName();

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				if (!validateCase(project, name, file)) {
					return false;
				}
			}
		}

		return true;
	}*/

	private static final String _EXPECTED_RESULTS_FILE_PATH =
		"expected-results/FailureMessageUtilTest.html";

	private static final File _testDependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies/");

	private final Project _project;

}