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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.net.URL;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface TestrayServer {

	public TestrayProject getTestrayProjectByID(int projectID);

	public TestrayProject getTestrayProjectByName(String projectName);

	public List<TestrayProject> getTestrayProjects();

	public URL getURL();

	public void importCaseResults(TopLevelBuild topLevelBuild);

	public void writeCaseResult(String fileName, String fileContent);

}