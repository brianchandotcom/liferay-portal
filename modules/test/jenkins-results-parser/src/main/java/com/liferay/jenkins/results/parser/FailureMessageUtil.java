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

import com.liferay.jenkins.results.parser.failure.BaseFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.RebaseFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.SourceFormatFailureMessageGenerator;

import org.apache.tools.ant.Project;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtil {

	public static String getFailureMessage(Project project, String buildURL)
		throws Exception {

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"));

		for (
			BaseFailureMessageGenerator messageGenerator : _messageGenerators) {

			String message = messageGenerator.getMessage(
				buildURL, consoleOutput, project);

			if (message != null) {
				return message;
			}
		}

		return _genericMessageGenerator.getMessage(
			buildURL, consoleOutput, project);
	}

	private static final GenericFailureMessageGenerator
		_genericMessageGenerator = new GenericFailureMessageGenerator();
	private static final BaseFailureMessageGenerator[] _messageGenerators =
		new BaseFailureMessageGenerator[] {
			new PluginFailureMessageGenerator(),
			new PluginGitIDFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator()
		};

}