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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Hugo Huijser
 */
public class JIRAUtil {

	public static void validateJIRAProjectNames(
			List<String> commitMessages, List<String> projectNames)
		throws Exception {

		if (projectNames.isEmpty()) {
			return;
		}

		outerLoop:
		for (String commitMessage : commitMessages) {
			if (commitMessage.startsWith("Revert ") ||
				commitMessage.startsWith("artifact:ignore") ||
				commitMessage.startsWith("build.gradle auto SF") ||
				commitMessage.endsWith("/ci-merge.")) {

				continue;
			}

			for (String projectName : projectNames) {
				if (commitMessage.startsWith(projectName)) {
					continue outerLoop;
				}
			}

			throw new Exception(
				StringBundler.concat(
					"Found formatting issues:\n",
					"At least one commit message is missing a reference to a ",
					"required JIRA project: ",
					StringUtil.merge(projectNames, StringPool.COMMA_AND_SPACE),
					". Please verify that the JIRA project keys are specified",
					"in ci.properties in the liferay-portal repository."));
		}
	}

	public static void validateJIRASecurityKeywords(
			List<String> commitMessages, List<String> keywords)
		throws Exception {

		for (String commitMessage : commitMessages) {
			for (String keyword : keywords) {
				Pattern pattern = Pattern.compile(
					"\\b_*(" + keyword + ")_*\\b", Pattern.CASE_INSENSITIVE);

				Matcher matcher = pattern.matcher(commitMessage);

				if (matcher.find()) {
					throw new Exception(
						StringBundler.concat(
							"The commit '", commitMessage,
							"' contains the word '", keyword,
							"' , which could reveal potential security ",
							"vulnerablities."));
				}
			}
		}
	}

	public static void validateJIRATicketIds(
			List<String> commitMessages, int maxNumberOfTickets)
		throws Exception {

		Set<String> validatedTicketIds = new HashSet<>();

		for (String commitMessage : commitMessages) {
			if (validatedTicketIds.size() == maxNumberOfTickets) {
				return;
			}

			String jiraTicketId = _getJIRATicketId(commitMessage);

			if ((jiraTicketId == null) ||
				!validatedTicketIds.add(jiraTicketId)) {

				continue;
			}

			try {
				if (_getJIRATicketResponseCode(jiraTicketId) ==
						HttpServletResponse.SC_NOT_FOUND) {

					throw new Exception(
						StringBundler.concat(
							"Found formatting issues:\n",
							"Commit message is pointing to non-existing JIRA ",
							"issue: ", jiraTicketId));
				}
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}

				return;
			}
		}
	}

	private static String _getJIRATicketId(String commitMessage) {
		Matcher matcher = _jiraTicketIdPattern.matcher(commitMessage);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private static int _getJIRATicketResponseCode(String jiraTicketId)
		throws IOException {

		URL url = new URL(
			"https://issues.liferay.com/rest/api/2/issue/" + jiraTicketId);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);

		return httpURLConnection.getResponseCode();
	}

	private static final Log _log = LogFactoryUtil.getLog(JIRAUtil.class);

	private static final Pattern _jiraTicketIdPattern = Pattern.compile(
		"^[A-Z0-9]+-[0-9]+");

}