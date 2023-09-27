/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalFixpackEnvironmentJob extends PortalEnvironmentJob {

	public PortalFixpackEnvironmentJob(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PortalFixpackEnvironmentJob(
		BuildProfile buildProfile, String jobName,
		String portalUpstreamBranchName) {

		super(buildProfile, jobName, portalUpstreamBranchName);
	}

	@Override
	protected Set<String> getRawBatchNames() {
		JobProperty jobProperty = getJobProperty(
			"fixpack.environment.job.names");

		recordJobProperty(jobProperty);

		return getSetFromString(jobProperty.getValue());
	}

}