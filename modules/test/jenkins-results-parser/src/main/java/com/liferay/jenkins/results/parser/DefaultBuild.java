/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.net.URL;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class DefaultBuild extends BaseBuild {

	public DefaultBuild(String url) {
		super(url);

		getDuration();
	}

	@Override
	public void addTimelineData(TimelineData timelineData) {
	}

	@Override
	public URL getArtifactsBaseURL() {
		return null;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

}