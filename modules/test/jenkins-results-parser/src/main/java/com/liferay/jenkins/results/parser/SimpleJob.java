/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Collections;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SimpleJob extends BaseJob {

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	protected SimpleJob(BuildProfile buildProfile, String jobName) {
		super(buildProfile, jobName);
	}

	protected SimpleJob(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Set<String> getRawBatchNames() {
		return Collections.emptySet();
	}

}