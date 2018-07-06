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

/**
 * @author Michael Hashimoto
 */
public class PortalLocalRepository extends LocalRepository {

	protected PortalLocalRepository(String name, String upstreamBranchName) {
		super(name, upstreamBranchName);

		if (upstreamBranchName.startsWith("ee-") ||
			upstreamBranchName.endsWith("-private")) {

			if (!name.endsWith("-ee")) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"The local repository, ", name,
						" should not be used with upstream branch ",
						upstreamBranchName, ". Use ", name, "-ee."));
			}
		}
	}

	@Override
	protected String getDefaultRelativeRepositoryDirPath() {
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return name.replace("-ee", "");
		}

		return JenkinsResultsParserUtil.combine(
			name.replace("-ee", ""), "-", upstreamBranchName);
	}

}