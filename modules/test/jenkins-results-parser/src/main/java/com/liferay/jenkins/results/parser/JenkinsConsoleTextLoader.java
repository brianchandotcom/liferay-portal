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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class JenkinsConsoleTextLoader {

	public JenkinsConsoleTextLoader(String buildURL) {
		this.buildURL = buildURL;

		logStringBuilder = new StringBuilder();
		serverLogSize = 0;
	}

	public String getConsoleText() {
		update();

		return logStringBuilder.toString();
	}

	public int getLineCount() {
		String consoleLog = logStringBuilder.toString();

		String[] consoleLogLines = consoleLog.split("\n");

		return consoleLogLines.length;
	}

	public boolean hasMoreData() {
		return hasMoreData;
	}

	protected void update() {
		StringBuilder sb = new StringBuilder();

		boolean behindLatest = true;

		while (behindLatest) {
			String url =
				buildURL + "/logText/progressiveHtml?start=" + serverLogSize;

			try {
				URL urlObject = new URL(
					JenkinsResultsParserUtil.getLocalURL(url));

				HttpURLConnection httpURLConnection =
					(HttpURLConnection)urlObject.openConnection();

				long latestServerLogSize = httpURLConnection.getHeaderFieldLong(
					"X-Text-Size", serverLogSize);

				if (latestServerLogSize > serverLogSize) {
					try (BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(
								httpURLConnection.getInputStream()))) {

						String line = bufferedReader.readLine();

						while (line != null) {
							Matcher matcher = _anchorPattern.matcher(line);

							line = matcher.replaceAll("$1");

							sb.append(line);

							sb.append("\n");

							line = bufferedReader.readLine();
						}
					}
				}

				hasMoreData = Boolean.parseBoolean(
					httpURLConnection.getHeaderField("X-More-Data"));

				if (((latestServerLogSize - serverLogSize) < 5000) ||
					!hasMoreData) {

					behindLatest = false;
				}

				serverLogSize = latestServerLogSize;
			}
			catch (MalformedURLException murle) {
				throw new IllegalArgumentException(
					"Invalid buildURL " + buildURL, murle);
			}
			catch (IOException ioe) {
				throw new RuntimeException("Unable to update console log", ioe);
			}
		}

		if (sb.length() > 0) {
			logStringBuilder.append(sb);
		}
	}

	protected String buildURL;
	protected boolean hasMoreData = true;
	protected StringBuilder logStringBuilder;
	protected long serverLogSize;

	private static final Pattern _anchorPattern = Pattern.compile(
		"\\<a[^>]*\\>(?<text>[^<]*)\\</a\\>");

}