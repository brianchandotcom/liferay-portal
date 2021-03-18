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

import java.net.URLEncoder;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestrayServer extends BaseTestrayServer {

	public DefaultTestrayServer(String urlString) {
		super(urlString);
	}

	@Override
	public void importCaseResults(JenkinsMaster jenkinsMaster) {
		TestrayResultsParserUtil.processTestrayResultFiles(getResultsDir());

		for (File resultFile :
				JenkinsResultsParserUtil.findFiles(getResultsDir(), ".*.xml")) {

			try {
				String result = JenkinsResultsParserUtil.read(resultFile);

				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						String.valueOf(getURL()),
						"/web/guest/home/-/testray/case_results",
						"/importResults.json"),
					JenkinsResultsParserUtil.combine(
						"results=", URLEncoder.encode(result, "UTF-8"),
						"&type=poshi"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ",
					JenkinsResultsParserUtil.getCanonicalPath(resultFile),
					" by REST API"));

			JenkinsResultsParserUtil.delete(resultFile);
		}
	}

}