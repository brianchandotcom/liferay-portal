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

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PortalAcceptancePullRequestJob extends PortalRepositoryJob {

	public PortalAcceptancePullRequestJob(String url) {
		this(url, "default");
	}

	public PortalAcceptancePullRequestJob(String url, String testSuiteName) {
		super(url);

		_testSuiteName = testSuiteName;
	}

	@Override
	public List<String> getBatchNames() {
		String testBatchNames = portalTestProperies.getProperty(
			"test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			testBatchNames = portalTestProperies.getProperty(
				"test.batch.names");
		}

		return getListFromString(testBatchNames);
	}

	@Override
	public List<String> getDistTypes() {
		String testBatchDistAppServers = portalTestProperies.getProperty(
			"test.batch.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = portalTestProperies.getProperty(
				"test.batch.dist.app.servers");
		}

		return getListFromString(testBatchDistAppServers);
	}

	public String getTestSuiteName() {
		return _testSuiteName;
	}

	private final String _testSuiteName;

}