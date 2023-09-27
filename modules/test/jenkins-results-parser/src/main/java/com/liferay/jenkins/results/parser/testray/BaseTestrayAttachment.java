/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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