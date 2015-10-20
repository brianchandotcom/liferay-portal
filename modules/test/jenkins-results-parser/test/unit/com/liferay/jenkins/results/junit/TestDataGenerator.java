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

package com.liferay.jenkins.results.junit;

import com.liferay.jenkins.results.parser.JenkinsUtility;
import com.liferay.jenkins.results.parser.junit.FailureMessageGeneratorTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.net.URLDecoder;

import java.util.List;

import org.apache.tools.ant.Project;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class TestDataGenerator {

	public static void main(String[] args) {
		FailureMessageGeneratorTest test = new FailureMessageGeneratorTest();

		TestDataGenerator generator = new TestDataGenerator(test);

		try {
			URL url = new URL(args[0]);

			generator.createTestData(url);
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(-1);
		}
	}

	public TestDataGenerator(FailureMessageGeneratorTest test) {
		_test = test;
	}

	public void createTestData(URL jenkinsReport) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(jenkinsReport);

		String testDefinitionName = getTestDefinitionName(doc);
		List<Node> testNodeList = getTestNodeList(doc);

		Project project = _test.getProject();

		int i = 1;

		for (Node node : testNodeList) {
			DefaultElement elem = (DefaultElement)node;
			Element parent = elem.getParent();

			if (parent.getText().endsWith("FAILURE")) {
				String url = elem.attribute("href").getValue();

				try {
					String testGroupRootPath = downloadTestData(
						testDefinitionName, url);
					_test.createExpectedResultsFile(
						project, new File(testGroupRootPath));
					i++;
				}
				catch (FileNotFoundException e) {
					System.err.println(
						"Data was not found on server for " + url +
						" skipped.");
				}
			}
			else {
				System.out.println(
					"Skipping non-failure test: " + parent.getText());
			}
		}

		System.out.println(
			"Test case creation is complete. " + i +
			" test groups were created.");
	}

	protected static String getTestDefinitionName(Document doc) {
		Node userNameNode = doc.selectSingleNode("//body/h1/strong");
		Node prNumberNode = doc.selectSingleNode("//body/h1/a[1]");

		String userName = userNameNode.getText();
		String prNumber = prNumberNode.getText();

		return userName + "-" + prNumber;
	}

	protected String downloadTestData(String testName, String url)
		throws Exception {

		String urlString = URLDecoder.decode(url, "UTF8");
		System.out.println("downloading test data: " + testName);

		int jobIdx = urlString.indexOf("/job/") + 5;

		String testGroupName = urlString.substring(jobIdx);

		testGroupName = testGroupName.replace("/", "-");

		if (testGroupName.endsWith("-")) {
			testGroupName = testGroupName.substring(
				0, testGroupName.length() - 1);
		}

		String testRootPath = _testDataRoot.getPath() + "/" + testName;
		String testGroupRootPath = testRootPath + "/" + testGroupName;

		String testGroupLogTextPath = testGroupRootPath + "/logText";
		String testGroupApiPath = testGroupRootPath + "/api";

		File testGroupLogTextDir = new File(testGroupLogTextPath);
		File testGroupApiDir = new File(testGroupApiPath);

		testGroupLogTextDir.mkdirs();
		testGroupApiDir.mkdirs();

		try {
			String jsonURL = url + "/api/json";

			System.out.println(" downloading json from:" + jsonURL);

			String json = JenkinsUtility.toString(jsonURL);

			File jsonFile = new File(testGroupApiDir.getPath() + "/json");

			JenkinsUtility.writeFile(jsonFile, json);

			System.out.println(
				" wrote file: " + jsonFile.getPath() +
				" size; " + jsonFile.length());

			String consoleURL = url + "/logText/progressiveText";

			System.out.println(" downloading console from:" + consoleURL);

			String console = JenkinsUtility.toString(consoleURL);

			File consoleFile = new File(
				testGroupLogTextDir.getPath() + "/progressiveText");

			JenkinsUtility.writeFile(consoleFile, console);

			System.out.println(
				" wrote file: " + consoleFile.getPath() + " size; " +
				consoleFile.length());
		}
		catch (IOException ioe) {
			File testGroupRootDir = new File(testGroupRootPath);

			testGroupLogTextDir.delete();

			testGroupApiDir.delete();

			testGroupRootDir.delete();

			throw ioe;
		}

		return testGroupRootPath;
	}

	protected List<Node> getTestNodeList(Document doc) {
		@SuppressWarnings("unchecked")
		List<Node> nodeList = doc.selectNodes("//li/h3/a[1]");
		@SuppressWarnings("unchecked")
		List<Node> nodeList2 = doc.selectNodes("//li/a[1]");

		nodeList.addAll(nodeList2);

		return nodeList;
	}

	private static final File _testDataRoot = new File("test-data");

	private final FailureMessageGeneratorTest _test;

}