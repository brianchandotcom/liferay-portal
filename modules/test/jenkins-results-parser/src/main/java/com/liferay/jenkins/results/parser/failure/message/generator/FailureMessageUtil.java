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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import org.apache.tools.ant.Project;

/**
 * @author Kevin Yen
 * @author Peter Yoo
 */
public class FailureMessageUtil {

	public static String getFailureMessage(Project project, String buildURL)
		throws Exception {

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"));

		for (FailureMessageGenerator failureMessageGenerator :
				_failureMessageGenerators) {

			String message = failureMessageGenerator.getMessage(
				buildURL, consoleOutput, project.getProperties());

			if (message != null) {
				return message;
			}
		}

		return _genericFailureMessageGenerator.getMessage(
			buildURL, consoleOutput, project.getProperties());
	}

	private static final FailureMessageGenerator[] _failureMessageGenerators = {
		new PoshiValidationFailureMessageGenerator(),

		new DownstreamFailureMessageGenerator(),

		new IntegrationTestTimeoutFailureMessageGenerator(),
		new LocalGitMirrorFailureMessageGenerator(),
		new PluginFailureMessageGenerator(),
		new PluginGitIDFailureMessageGenerator(),
		new RebaseFailureMessageGenerator(),
		new SemanticVersioningFailureMessageGenerator(),
		new SourceFormatFailureMessageGenerator()
	};

	private static final GenericFailureMessageGenerator
		_genericFailureMessageGenerator = new GenericFailureMessageGenerator();

}