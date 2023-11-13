/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class BuildUpdaterFactory {

	public static BuildUpdater newBuildUpdater(Build build) {
		BuildUpdater buildUpdater = null;

		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		if (topLevelBuild != null) {
			String jethr0JobId = topLevelBuild.getParameterValue(
				"JETHR0_JOB_ID");

			if (JenkinsResultsParserUtil.isInteger(jethr0JobId)) {
				buildUpdater = new Jethr0BuildUpdater(
					build, Long.parseLong(jethr0JobId));
			}
		}

		if (buildUpdater == null) {
			buildUpdater = new DefaultBuildUpdater(build);
		}

		return buildUpdater;
	}

}