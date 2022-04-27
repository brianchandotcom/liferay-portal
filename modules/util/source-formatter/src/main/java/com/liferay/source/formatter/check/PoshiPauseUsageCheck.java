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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiPauseUsageCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith(".path")) {
			return content;
		}

		List<String> jiraProjectKeys = _getJIRAProjectKeys();

		if (jiraProjectKeys.isEmpty()) {
			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = StringPool.BLANK;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (Validator.isNull(line)) {
					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (!trimmedLine.startsWith("Pause(")) {
					previousLine = trimmedLine;

					continue;
				}

				if (previousLine.startsWith("//")) {
					String jiraTicketId = _getJIRATicketId(
						previousLine, jiraProjectKeys);

					if (Validator.isNull(jiraTicketId)) {
						addMessage(
							fileName,
							"Missing a required JIRA project in comment " +
								"before using 'Pause'",
							lineNumber);
					}
				}
				else {
					addMessage(
						fileName, "Missing a comment before using 'Pause'",
						lineNumber);
				}

				previousLine = trimmedLine;
			}
		}

		return content;
	}

	private List<String> _getJIRAProjectKeys() throws Exception {
		File propertiesFile = getFile("ci.properties", getMaxDirLevel());

		if (propertiesFile != null) {
			Properties properties = new Properties();

			properties.load(new FileInputStream(propertiesFile));

			if (properties.containsKey("jira.project.keys")) {
				return ListUtil.fromString(
					properties.getProperty("jira.project.keys"),
					StringPool.COMMA);
			}
		}

		return Collections.emptyList();
	}

	private String _getJIRATicketId(
		String comment, List<String> jiraProjectKeys) {

		for (String jiraProjectKey : jiraProjectKeys) {
			Pattern pattern = Pattern.compile(
				".*?(\\b" + jiraProjectKey + "-\\d+)");

			Matcher matcher = pattern.matcher(comment);

			if (matcher.find()) {
				return matcher.group(1);
			}
		}

		return null;
	}

}