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

import com.liferay.jenkins.results.parser.util.TestPropertiesUtil;
import com.liferay.jenkins.results.parser.util.TestPropertiesValues;

import java.util.Properties;

import org.junit.BeforeClass;

/**
 * @author Michael Hashimoto
 */
public class GitRepositoryTest extends Test {

	@BeforeClass
	public static void setUpClass() {
		TestPropertiesUtil.printProperties();

		Properties repositoryProperties = new Properties();

		repositoryProperties.put(
			JenkinsResultsParserUtil.combine(
				"repository.dir[", REPOSITORY_NAME, "][",
				REPOSITORY_UPSTREAM_BRANCH_NAME, "]"),
			REPOSITORY_DIR);

		BaseGitRepository.setRepositoryProperties(repositoryProperties);
	}

	protected static final String REPOSITORY_DIR =
		TestPropertiesValues.REPOSITORY_DIR;

	protected static final String REPOSITORY_HOSTNAME =
		TestPropertiesValues.REPOSITORY_HOSTNAME;

	protected static final String REPOSITORY_NAME =
		TestPropertiesValues.REPOSITORY_NAME;

	protected static final String REPOSITORY_UPSTREAM_BRANCH_NAME =
		TestPropertiesValues.REPOSITORY_UPSTREAM_BRANCH_NAME;

	protected static final String REPOSITORY_USERNAME =
		TestPropertiesValues.REPOSITORY_USERNAME;

}