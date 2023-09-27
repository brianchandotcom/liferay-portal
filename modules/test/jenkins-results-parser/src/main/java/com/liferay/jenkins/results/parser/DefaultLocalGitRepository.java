/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public class DefaultLocalGitRepository extends BaseLocalGitRepository {

	protected DefaultLocalGitRepository(
		String name, String upstreamBranchName) {

		super(name, upstreamBranchName);
	}

	protected DefaultLocalGitRepository(
		String name, String upstreamBranchName, File repositoryDir) {

		super(name, upstreamBranchName, repositoryDir);
	}

}