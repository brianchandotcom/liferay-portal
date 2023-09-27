/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.net.URL;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface TestrayServer {

	public JenkinsResultsParserUtil.HTTPAuthorization getHTTPAuthorization();

	public TestrayCaseType getTestrayCaseType(String testrayCaseTypeName);

	public TestrayProject getTestrayProjectByID(long projectID);

	public TestrayProject getTestrayProjectByName(String projectName);

	public List<TestrayProject> getTestrayProjects();

	public URL getURL();

	public void importCaseResults(TopLevelBuild topLevelBuild);

	public void setHTTPAuthorization(
		JenkinsResultsParserUtil.HTTPAuthorization httpAuthorization);

	public void writeCaseResult(String fileName, String fileContent);

}