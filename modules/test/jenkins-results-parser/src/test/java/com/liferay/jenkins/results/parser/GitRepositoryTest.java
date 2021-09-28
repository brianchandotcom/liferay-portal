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

import org.junit.BeforeClass;

/**
 * @author Michael Hashimoto
 */
public class GitRepositoryTest extends Test {

	@BeforeClass
	public static void setUpClass() {
		TestPropertiesUtil.printProperties();
	}

	protected static final String FILE_PATH_REPOSITORY =
		TestPropertiesValues.FILE_PATH_REPOSITORY;

	protected static final String HOSTNAME_REPOSITORY =
		TestPropertiesValues.HOSTNAME_REPOSITORY;

	protected static final String NAME_REPOSITORY =
		TestPropertiesValues.NAME_REPOSITORY;

	protected static final String NAME_REPOSITORY_UPSTREAM_BRANCH =
		TestPropertiesValues.NAME_REPOSITORY_UPSTREAM_BRANCH;

	protected static final String USERNAME_REPOSITORY =
		TestPropertiesValues.USERNAME_REPOSITORY;

}