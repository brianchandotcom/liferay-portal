/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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