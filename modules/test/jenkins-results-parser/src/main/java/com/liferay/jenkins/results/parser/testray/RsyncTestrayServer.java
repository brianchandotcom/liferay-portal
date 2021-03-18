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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestrayResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class RsyncTestrayServer extends BaseTestrayServer {

	public RsyncTestrayServer(String urlString) {
		super(urlString);
	}

	@Override
	public void importCaseResults(JenkinsMaster jenkinsMaster) {
		TestrayResultsParserUtil.processTestrayResultFiles(getResultsDir());

		String command = JenkinsResultsParserUtil.combine(
			"rsync -aqz --chmod=go=rx \"",
			JenkinsResultsParserUtil.getCanonicalPath(getResultsDir()),
			"\"/* \"", jenkinsMaster.getName(),
			"::testray-results/production/\"");

		try {
			JenkinsResultsParserUtil.executeBashCommands(command);
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		for (File resultFile :
				JenkinsResultsParserUtil.findFiles(getResultsDir(), ".*.xml")) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ",
					JenkinsResultsParserUtil.getCanonicalPath(resultFile),
					" by Rsync"));

			JenkinsResultsParserUtil.delete(resultFile);
		}
	}

}