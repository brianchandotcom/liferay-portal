/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestrayResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class TestrayServer {

	public JenkinsResultsParserUtil.HTTPAuthorization getHTTPAuthorization() {
		return _httpAuthorization;
	}

	public TestrayCaseType getTestrayCaseType(String testrayCaseTypeName) {
		return null;
	}

	public TestrayProject getTestrayProjectByID(long projectID) {
		return null;
	}

	public TestrayProject getTestrayProjectByName(String projectName) {
		return null;
	}

	public List<TestrayProject> getTestrayProjects() {
		return null;
	}

	public URL getURL() {
		return _url;
	}

	public void importCaseResults(TopLevelBuild topLevelBuild) {
		TestrayResultsParserUtil.processTestrayResultFiles(getResultsDir());

		if (JenkinsResultsParserUtil.isCINode()) {
			_importCaseResultsFromCI(topLevelBuild);
		}

		if (TestrayS3Bucket.hasGoogleApplicationCredentials()) {
			_importCaseResultsToGCP(topLevelBuild);
		}
	}

	public String requestGet(String urlPath) throws IOException {
		return JenkinsResultsParserUtil.toString(
			getTestrayURL(urlPath), false,
			JenkinsResultsParserUtil.HttpRequestMethod.GET, null,
			getHTTPAuthorization());
	}

	public String requestPost(String urlPath, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.toString(
			getTestrayURL(urlPath), false,
			JenkinsResultsParserUtil.HttpRequestMethod.POST, requestData,
			getHTTPAuthorization());
	}

	public void setHTTPAuthorization(
		JenkinsResultsParserUtil.HTTPAuthorization httpAuthorization) {

		_httpAuthorization = httpAuthorization;
	}

	public void writeCaseResult(String fileName, String fileContent) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(fileName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(fileContent)) {

			return;
		}

		try {
			JenkinsResultsParserUtil.write(
				new File(getResultsDir(), fileName), fileContent);
		}
		catch (IOException ioException) {
		}
	}

	protected TestrayServer(String urlString) {
		try {
			Matcher matcher = _urlPattern.matcher(urlString);

			if (matcher.find()) {
				urlString = matcher.group("url");
			}

			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray server URL " + urlString,
				malformedURLException);
		}
	}

	protected File getResultsDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/results");
	}

	protected String getTestrayURL(String urlPath) {
		Matcher matcher = _urlPathPattern.matcher(urlPath);

		if (matcher.find()) {
			urlPath = matcher.group("urlPath");
		}

		return getURL() + "/" + urlPath;
	}

	private void _importCaseResultsFromCI(TopLevelBuild topLevelBuild) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		String command = JenkinsResultsParserUtil.combine(
			"rsync -aqz --chmod=go=rx \"",
			JenkinsResultsParserUtil.getCanonicalPath(getResultsDir()),
			"\"/* \"", jenkinsMaster.getName(),
			"::testray-results/production/\"");

		try {
			JenkinsResultsParserUtil.executeBashCommands(command);
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		for (File resultFile :
				JenkinsResultsParserUtil.findFiles(getResultsDir(), ".*.xml")) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ",
					JenkinsResultsParserUtil.getCanonicalPath(resultFile),
					" by Rsync"));
		}
	}

	private void _importCaseResultsToGCP(TopLevelBuild topLevelBuild) {
		if (!TestrayS3Bucket.hasGoogleApplicationCredentials()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("-");

		String jobName = topLevelBuild.getJobName();

		sb.append(jobName.replaceAll("[\\(\\)]", "_"));

		sb.append("-");
		sb.append(topLevelBuild.getBuildNumber());
		sb.append("-results.tar.gz");

		File resultsDir = getResultsDir();

		File gcpResultsDir = new File(
			resultsDir.getParentFile(), "gcp-results");

		try {
			JenkinsResultsParserUtil.copy(resultsDir, gcpResultsDir);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		for (File gcpResultFile :
				JenkinsResultsParserUtil.findFiles(gcpResultsDir, ".*.xml")) {

			try {
				Document document = Dom4JUtil.parse(
					JenkinsResultsParserUtil.read(gcpResultFile));

				Element rootElement = document.getRootElement();

				for (Element testcaseElement :
						rootElement.elements("testcase")) {

					Element propertiesElement = testcaseElement.element(
						"properties");

					for (Element propertyElement :
							propertiesElement.elements("property")) {

						String propertyName = propertyElement.attributeValue(
							"name");

						if ((propertyName == null) ||
							!propertyName.equals("testray.testcase.warnings")) {

							continue;
						}

						for (Element element : propertyElement.elements()) {
							propertyElement.remove(element);
						}
					}
				}

				String gcpResultFileContent = Dom4JUtil.format(
					rootElement, false);

				gcpResultFileContent = gcpResultFileContent.replaceAll(
					"(<property name=\"testray.testcase.warnings\" " +
						"value=\"\\d+\")>\\s+<\\/property>",
					"$1/>");
				gcpResultFileContent = gcpResultFileContent.replaceAll(
					getURL() + "/?reports/production/logs",
					testrayS3Bucket.getTestrayS3BaseURL());

				JenkinsResultsParserUtil.write(
					gcpResultFile, gcpResultFileContent);
			}
			catch (DocumentException | IOException exception) {
			}
		}

		File resultsTarGzFile = new File(
			gcpResultsDir.getParentFile(), sb.toString());

		JenkinsResultsParserUtil.tarGzip(gcpResultsDir, resultsTarGzFile);

		testrayS3Bucket.createTestrayS3Object(
			"inbox/" + resultsTarGzFile.getName(), resultsTarGzFile);
	}

	private static final Pattern _urlPathPattern = Pattern.compile(
		"/+(?<urlPath>.*)");
	private static final Pattern _urlPattern = Pattern.compile(
		"(?<url>https?://.*)/+");

	private JenkinsResultsParserUtil.HTTPAuthorization _httpAuthorization;
	private final URL _url;

}