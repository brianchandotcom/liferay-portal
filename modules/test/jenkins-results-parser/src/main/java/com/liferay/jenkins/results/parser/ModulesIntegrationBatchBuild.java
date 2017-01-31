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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class ModulesIntegrationBatchBuild extends BatchBuild {

	public ModulesIntegrationBatchBuild(String url) {
		super(url);
	}

	public ModulesIntegrationBatchBuild(
		String url, TopLevelBuild topLevelBuild) {

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

		if (verifiedAxisBuilds == null) {
			verifiedAxisBuilds = new ArrayList<>();
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
			String body;
			String subject = "Arquillian broken connection failure";

			if (badBuildNumbers.size() == 0) {
				body = JenkinsResultsParserUtil.combine(
					"Arquillian broken connection failure detected at ",
					reinvokeErrorAxisBuild.getBuildURL(),
					". This batch will be reinvoked.\n\nError marker:\n",
					reinvokeErrorMarker);

				System.out.println(body);

				reinvoke();
			}
			else {
				subject = "Second " + subject;

				List<String> badBuildURLs = getBadBuildURLs();

				body = JenkinsResultsParserUtil.combine(
					"Second Arquillian broken connection failure detected at ",
					reinvokeErrorAxisBuild.getBuildURL(),
					". Previous failure was at ", badBuildURLs.get(0),
					"\n\nError marker:\n", reinvokeErrorMarker);

				System.out.println(body);

				_notificationsComplete = true;
			}

			/*try {
				JenkinsResultsParserUtil.sendEmail(
					body,
					"root@" + JenkinsResultsParserUtil.getHostName("UNKNOWN"),
					subject, "peter.yoo@liferay.com, shuyang.zhou@liferay.com");
			}
			catch (Exception e) {
				System.out.println(
					"Unable to send email notification: " + e.getMessage());
			}*/
		}
	}

	protected String getReinvokedErrorMarkerPropertyName(int index) {
		return _REINVOKE_ERROR_MARKER_TEMPLATE.replace(
			"?", Integer.toString(index));
	}

	protected String getReinvokeErrorMarker(int index) {
		if (buildProperties == null) {
			loadBuildProperties();
		}

		return buildProperties.getProperty(
			getReinvokedErrorMarkerPropertyName(index));
	}

	protected boolean hasReinvokeErrorMarker(int index) {
		if (buildProperties == null) {
			loadBuildProperties();
		}

		return buildProperties.containsKey(
			getReinvokedErrorMarkerPropertyName(index));
	}

	protected void loadBuildProperties() {
		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build.properties.", ioe);
		}
	}

	protected Properties buildProperties;
	protected List<Build> verifiedAxisBuilds;

	private static final String _REINVOKE_ERROR_MARKER_TEMPLATE =
		"reinvoke.error.marker[modules-integration-?]";

	private boolean _notificationsComplete;

}