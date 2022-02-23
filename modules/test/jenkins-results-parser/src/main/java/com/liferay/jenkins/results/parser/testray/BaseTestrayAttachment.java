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

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayAttachment implements TestrayAttachment {

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public URL getURL() {
		if (_url != null) {
			return _url;
		}

		TestrayServer testrayServer = _testrayCaseResult.getTestrayServer();

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

	@Override
	public String getValue() {
		String urlString = String.valueOf(getURL());

		if (urlString.contains(".gz")) {
			String timeStamp = JenkinsResultsParserUtil.getDistinctTimeStamp();

			File file = new File(timeStamp);
			File gzipFile = new File(timeStamp + ".gz");

			try {
				JenkinsResultsParserUtil.toFile(getURL(), gzipFile);

				JenkinsResultsParserUtil.unGzip(gzipFile, file);

				return JenkinsResultsParserUtil.read(file);
			}
			catch (Exception exception) {
				System.out.println("Unable to download " + getURL());

				return null;
			}
			finally {
				JenkinsResultsParserUtil.delete(file);
				JenkinsResultsParserUtil.delete(gzipFile);
			}
		}

		try {
			return JenkinsResultsParserUtil.toString(urlString);
		}
		catch (IOException ioException) {
			System.out.println("Unable to download " + getURL());

			return null;
		}
	}

	protected BaseTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		this(testrayCaseResult, name, key, null);
	}

	protected BaseTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key, URL url) {

		_testrayCaseResult = testrayCaseResult;
		_name = name;
		_key = key;
		_url = url;
	}

	protected TestrayCaseResult getTestrayCaseResult() {
		return _testrayCaseResult;
	}

	private final String _key;
	private final String _name;
	private final TestrayCaseResult _testrayCaseResult;
	private final URL _url;

}