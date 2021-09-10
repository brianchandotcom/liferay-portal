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

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class CISystemHistoryReportUtil {

	public static void generateCISystemHistoryReport(
			String jobName, String testSuiteName)
		throws IOException {

		writeIndexHtmlFile();
	}

	protected static void writeIndexHtmlFile() throws IOException {
		File indexHtmlFile = new File(
			_CI_SYSTEM_HISTORY_REPORT_DIR, "index.html");

		if (!indexHtmlFile.exists()) {
			return;
		}

		String content = JenkinsResultsParserUtil.read(indexHtmlFile);

		StringBuilder sb = new StringBuilder();

		for (String dateString : _dateStrings) {
			sb.append("\t\t<script src=\"js/durations-");
			sb.append(dateString);
			sb.append(".js\"></script>\n");
		}

		sb.append("\n\t\t<script src=\"js/all-durations.js\"></script>\n");

		JenkinsResultsParserUtil.write(
			indexHtmlFile,
			content.replaceAll("\\t\\t<script-durations />\\n", sb.toString()));
	}

	private static final File _CI_SYSTEM_HISTORY_REPORT_DIR;

	private static final int _MONTHS_PER_YEAR = 12;

	private static final Properties _buildProperties;
	private static final List<String> _dateStrings;

	static {
		_buildProperties = new Properties() {
			{
				try {
					putAll(JenkinsResultsParserUtil.getBuildProperties());
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		};

		_CI_SYSTEM_HISTORY_REPORT_DIR = new File(
			_buildProperties.getProperty("ci.system.history.report.dir"));

		_dateStrings = new ArrayList() {
			{
				LocalDate currentLocalDate = LocalDate.now();

				for (int i = _MONTHS_PER_YEAR - 1; i >= 0; i--) {
					LocalDate localDate = currentLocalDate.minusMonths(i);

					add(
						localDate.format(
							DateTimeFormatter.ofPattern("yyyy-MM")));
				}
			}
		};
	}

}