/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Job;

/**
 * @author Michael Hashimoto
 */
public class TopLevelBuildTestrayCaseResultDescriptor
	extends BaseBuildTestrayCaseResultDescriptor {

	public TopLevelBuildTestrayCaseResultDescriptor(Job job) {
		super(job, null);
	}

	@Override
	public String getBatchName() {
		return "top-level-build";
	}

	@Override
	public String getName() {
		return "Top Level Build";
	}

}