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

import java.io.File;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkspace extends BaseWorkspace {

	@Override
	public String getUpstreamRepositoryName() {
		String repositoryType = getRepositoryType();
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return repositoryType;
		}

		return repositoryType + "-ee";
	}

	protected PluginsWorkspace(
		String repositoryType, String upstreamBranchName) {

		super(repositoryType, upstreamBranchName);
	}

	@Override
	protected File getDefaultRepositoryDir() {
		String repositoryType = getRepositoryType();
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return new File(getBaseRepositoryDir(), repositoryType);
		}

		return new File(
			getBaseRepositoryDir(), repositoryType + "-" + upstreamBranchName);
	}

	@Override
	protected void validateRepositoryType(String repositoryType) {
		super.validateRepositoryType(repositoryType);

		if (!repositoryType.equals("liferay-plugins")) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"The repositoryType should be liferay-plugins instead of ",
					repositoryType));
		}
	}

}