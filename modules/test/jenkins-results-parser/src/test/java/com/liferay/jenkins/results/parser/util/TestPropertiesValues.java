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

package com.liferay.jenkins.results.parser.util;

/**
 * @author Michael Hashimoto
 */
public class TestPropertiesValues {

	public static final String FILE_PATH_REPOSITORY = TestPropertiesUtil.get(
		"repository.dir");

	public static final String HOSTNAME_REPOSITORY = TestPropertiesUtil.get(
		"repository.hostname");

	public static final String NAME_REPOSITORY = TestPropertiesUtil.get(
		"repository.name");

	public static final String NAME_REPOSITORY_UPSTREAM_BRANCH =
		TestPropertiesUtil.get("repository.upstream.branch.name");

	public static final String USERNAME_REPOSITORY = TestPropertiesUtil.get(
		"repository.username");

}