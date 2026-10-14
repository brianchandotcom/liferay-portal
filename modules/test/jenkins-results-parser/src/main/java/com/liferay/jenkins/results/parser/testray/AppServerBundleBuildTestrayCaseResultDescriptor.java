/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;

/**
 * @author Michael Hashimoto
 */
public class AppServerBundleBuildTestrayCaseResultDescriptor
	extends BaseBuildTestrayCaseResultDescriptor {

	public AppServerBundleBuildTestrayCaseResultDescriptor(
		String appServerType, Job job,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(job, parentTestrayCaseResultDescriptor);

		_appServerType = appServerType;
	}

	@Override
	public String getBatchName() {
		return "app-server-bundle-builder";
	}

	@Override
	public String getComponentName() {
		String componentName = super.getComponentName();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return componentName;
		}

		return "App Server Bundle Builder";
	}

	@Override
	public String getName() {
		return "App Server Bundle Builder > " + _appServerType;
	}

	private final String _appServerType;

}