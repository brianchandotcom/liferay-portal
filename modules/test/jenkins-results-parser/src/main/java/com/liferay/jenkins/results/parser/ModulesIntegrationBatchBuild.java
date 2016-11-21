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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class ModulesIntegrationBatchBuild extends BatchBuild {

	public ModulesIntegrationBatchBuild(String url) throws Exception {
		super(url);
	}

	public ModulesIntegrationBatchBuild(String url, TopLevelBuild topLevelBuild)
		throws Exception {

		super(url, topLevelBuild);
	}

	@Override
	public void reinvoke() {
		super.reinvoke();

		verifiedAxisBuilds.clear();
	}

	@Override
	public void update() {
		super.update();

		if (_notificationsComplete) {
			return;
		}

		Build reinvokeErrorAxisBuild = null;
		String reinvokeErrorMarker = null;

		for (Build axisBuild : getDownstreamBuilds("completed")) {
			if (verifiedAxisBuilds.contains(axisBuild)) {
				continue;
			}

			String axisBuildResult = axisBuild.getResult();

			if ((axisBuildResult == null) ||
				axisBuildResult.equals("SUCCESS")) {

				continue;
			}

			String axisBuildConsoleText = axisBuild.getConsoleText();

			for (int i = 1; hasReinvokeErrorMarker(i); i++) {
				if (axisBuildConsoleText.contains(getReinvokeErrorMarker(i))) {
					reinvokeErrorAxisBuild = axisBuild;
					reinvokeErrorMarker = getReinvokeErrorMarker(i);

					break;
				}
			}

			if (reinvokeErrorAxisBuild == null) {
				verifiedAxisBuilds.add(axisBuild);
			}
		}

		if (reinvokeErrorAxisBuild != null) {
			StringBuilder sb = new StringBuilder();
			String subject = "Arquillian broken connection failure";

			if (badBuildNumbers.size() == 0) {
				sb.append("Arquillian broken connection failure ");
				sb.append("detected at ");
				sb.append(reinvokeErrorAxisBuild.getBuildURL());
				sb.append(". This batch will be reinvoked.");
				sb.append("\n\nError marker:\n");
				sb.append(reinvokeErrorMarker);

				System.out.println(sb);

				reinvoke();
			}
			else {
				subject = "Second " + subject;

				List<String> badBuildURLs = getBadBuildURLs();

				sb.append("Second Arquillian broken connection failure ");
				sb.append("detected at ");
				sb.append(reinvokeErrorAxisBuild.getBuildURL());
				sb.append(". Previous failure was at ");
				sb.append(badBuildURLs.get(0));
				sb.append("\n\nError marker:\n");
				sb.append(reinvokeErrorMarker);

				System.out.println(sb);

				_notificationsComplete = true;
			}

			try {
				JenkinsResultsParserUtil.sendEmail(
					sb.toString(),
					"root@" + JenkinsResultsParserUtil.getHostName("UNKNOWN"),
					subject, "peter.yoo@liferay.com, shuyang.zhou@liferay.com");
			}
			catch (Exception e) {
				System.out.println(
					"Unable to send email notification: " + e.getMessage());
			}
		}
	}

	protected String getReinvokedErrorMarkerPropertyName(int index) {
		return _REINVOKE_ERROR_MARKER_TEMPLATE.replace(
			"?", Integer.toString(index));
	}

	protected String getReinvokeErrorMarker(int index) {
		return buildProperties.getProperty(
			getReinvokedErrorMarkerPropertyName(index));
	}

	protected boolean hasReinvokeErrorMarker(int index) {
		return buildProperties.containsKey(
			getReinvokedErrorMarkerPropertyName(index));
	}

	protected Properties buildProperties =
		JenkinsResultsParserUtil.getBuildProperties();
	protected List<Build> verifiedAxisBuilds = new ArrayList<>();

	private static final String _REINVOKE_ERROR_MARKER_TEMPLATE =
		"reinvoke.error.marker[modules-integration-?]";

	private boolean _notificationsComplete;

}