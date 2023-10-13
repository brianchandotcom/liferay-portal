/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.jethr0.Jethr0Client;
import com.liferay.jenkins.results.parser.jethr0.Jethr0ClientFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class Jethr0BuildUpdater extends BaseBuildUpdater {

	@Override
	public void invoke() {
		Build build = getBuild();

		_invoke(build.getMinimumSlaveRAM(), build.getMaximumSlavesPerHost());
	}

	@Override
	public void reinvoke() {
		Build build = getBuild();

		_invoke(24, build.getMaximumSlavesPerHost());
	}

	protected Jethr0BuildUpdater(Build build, long jethr0JobId) {
		super(build);

		_jethr0JobId = jethr0JobId;

		_jethr0Client = Jethr0ClientFactory.newJethr0Client(
			build.getJenkinsMaster());
	}

	@Override
	protected boolean isBuildCompleted() {
		return true;
	}

	@Override
	protected boolean isBuildFailing() {
		return false;
	}

	@Override
	protected boolean isBuildQueued() {
		return false;
	}

	protected boolean isBuildRunning() {
		return false;
	}

	private void _invoke(int maximumSlavesPerHost, int minimumSlaveRAM) {
		Build build = getBuild();

		Map<String, String> buildParameters = new HashMap<>(
			build.getParameters());

		buildParameters.put(
			"MAX_NODE_COUNT", String.valueOf(maximumSlavesPerHost));
		buildParameters.put("MIN_NODE_RAM", String.valueOf(minimumSlaveRAM));

		_jethr0Client.createBuild(
			build.getJobName(), buildParameters, _jethr0JobId,
			build.getBuildName());
	}

	private final Jethr0Client _jethr0Client;
	private final long _jethr0JobId;

}