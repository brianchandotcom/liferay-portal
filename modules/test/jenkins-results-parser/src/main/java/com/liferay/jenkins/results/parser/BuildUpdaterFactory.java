/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class BuildUpdaterFactory {

	public static BuildUpdater newBuildUpdater(Build build) {
		synchronized (_buildUpdaters) {
			String buildName = build.getBuildName();

			if (_buildUpdaters.containsKey(buildName)) {
				return _buildUpdaters.get(buildName);
			}

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

			_buildUpdaters.put(buildName, buildUpdater);

			return _buildUpdaters.get(buildName);
		}
	}

	private static final Map<String, BuildUpdater> _buildUpdaters =
		new HashMap<>();

}