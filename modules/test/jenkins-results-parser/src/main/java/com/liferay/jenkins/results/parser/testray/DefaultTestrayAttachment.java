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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestrayAttachment extends BaseTestrayAttachment {

	public DefaultTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		super(testrayCaseResult, name, key);
	}

	@Override
	public URL getURL() {
		TestrayCaseResult testrayCaseResult = getTestrayCaseResult();

		TestrayServer testrayServer = testrayCaseResult.getTestrayServer();

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(testrayServer.getURL()),
					"/reports/production/logs/", getKey()));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

}