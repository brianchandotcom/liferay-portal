/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					if (Validator.isNull(
							_getJIRATicketId(previousLine, jiraProjectKeys))) {

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