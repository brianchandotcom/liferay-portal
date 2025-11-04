/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class ControllerTopLevelBuild extends BaseTopLevelBuild {

	public ControllerTopLevelBuild(String buildURL) {
		super(buildURL);
	}

	public ControllerTopLevelBuild(
		String buildURL, TopLevelBuild topLevelBuild) {

		super(buildURL, topLevelBuild);
	}

	@Override
	protected void findDownstreamBuilds() {
	}

}