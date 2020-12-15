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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.PortalFixpackRelease;
import com.liferay.jenkins.results.parser.PortalRelease;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseProductVersion;
import com.liferay.jenkins.results.parser.spira.SpiraTestSet;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public interface SpiraBuildResult {

	public PortalFixpackRelease getPortalFixpackRelease();

	public PortalRelease getPortalRelease();

	public Properties getPortalTestProperties();

	public PullRequest getPullRequest();

	public SpiraProject getSpiraProject();

	public SpiraRelease getSpiraRelease();

	public SpiraReleaseBuild getSpiraReleaseBuild();

	public SpiraTestCaseFolder getSpiraTestCaseFolder();

	public SpiraTestCaseProductVersion getSpiraTestCaseProductVersion();

	public SpiraTestSet getSpiraTestSet();

	public TopLevelBuild getTopLevelBuild();

}