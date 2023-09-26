/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FormatFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.RebaseFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SourceFormatFailureMessageGenerator;

import java.net.URL;

import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class SourceBuild extends BaseBuild {

	public SourceBuild(String url) {
		super(url);
	}

	public SourceBuild(String url, Build parentBuild) {
		super(url, parentBuild);
	}

	@Override
	public void addTimelineData(TimelineData timelineData) {
		timelineData.addTimelineData(this);
	}

	@Override
	public void findDownstreamBuilds() {
	}

	@Override
	public URL getArtifactsBaseURL() {
		return null;
	}

	@Override
	public JSONObject getTestReportJSONObject(boolean cache) {
		return null;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		return Collections.emptyList();
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new FormatFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator(),
			//
			new GenericFailureMessageGenerator()
		};

}