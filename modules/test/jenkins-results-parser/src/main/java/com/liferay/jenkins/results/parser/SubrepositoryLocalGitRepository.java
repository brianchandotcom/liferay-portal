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
public class SubrepositoryLocalGitRepository extends LocalGitRepository {

	protected SubrepositoryLocalGitRepository(
		String name, String upstreamBranchName) {

		super(name, upstreamBranchName);

		if (upstreamBranchName.endsWith("-private") &&
			!name.endsWith("-private")) {

			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"The local repository, ", name,
					" should not be used with upstream branch ",
					upstreamBranchName, ". Use ", name, "-private."));
		}
	}

	@Override
	protected String getDefaultRelativeGitRepositoryDirPath() {
		String name = getName();

		if (!name.endsWith("-private")) {
			return name + "-private";
		}

		return name;
	}

}