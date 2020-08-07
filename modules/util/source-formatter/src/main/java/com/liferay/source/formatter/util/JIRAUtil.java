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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
					"At least one commit message is missing a reference to a ",
					"required JIRA project: ",
					StringUtil.merge(projectNames, StringPool.COMMA_AND_SPACE),
					". Please verify that the JIRA project keys are specified",
					"in ci.properties in the liferay-portal repository."));
		}
	}

	public static void validateJIRASecurityKeywords(
		List<String> commitMessages, List<String> keywords,
		int maxNumberOfTickets) {

		Map<String, Integer> responseCodeMap = new HashMap<>();

		Set<String> violatingCommitMessages = new TreeSet<>();
		Set<String> violatingWords = new TreeSet<>();

		for (String commitMessage : commitMessages) {
			if (responseCodeMap.size() == maxNumberOfTickets) {
				return;
			}

			String jiraTicketId = _getJIRATicketId(commitMessage);

			if (jiraTicketId == null) {
				continue;
			}

			Integer jiraTicketResponseCode = responseCodeMap.get(jiraTicketId);

			if (jiraTicketResponseCode == null) {
				try {
					jiraTicketResponseCode = _getJIRATicketResponseCode(
						jiraTicketId);

					responseCodeMap.put(jiraTicketId, jiraTicketResponseCode);
				}
				catch (IOException ioException) {
					return;
				}
			}

			if (jiraTicketResponseCode != HttpServletResponse.SC_UNAUTHORIZED) {
				continue;
			}

			for (String keyword : keywords) {
				Pattern pattern = Pattern.compile(
					"\\W(" + keyword + "\\w*)(\\W|\\Z)",
					Pattern.CASE_INSENSITIVE);

				Matcher matcher = pattern.matcher(commitMessage);

				while (matcher.find()) {
					violatingCommitMessages.add(commitMessage);
					violatingWords.add(matcher.group(1));
				}
			}
		}

		_printWarnings(
			ListUtil.fromCollection(violatingCommitMessages),
			ListUtil.fromCollection(violatingWords), 80);
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
							"Commit message is pointing to non-existing JIRA ",
							"issue: ", jiraTicketId));
				}
			}
			catch (IOException ioException) {
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

	private static void _printBorder(String delimeter, int lineLength) {
		System.out.print(delimeter);

		for (int i = 0; i < (lineLength - 2); i++) {
			System.out.print(StringPool.UNDERLINE);
		}

		System.out.println(delimeter);
	}

	private static void _printWarningLine(String line, int lineLength) {
		String newLine = StringBundler.concat("|  ", line, "  |");

		while (newLine.length() < lineLength) {
			newLine = StringUtil.replaceLast(newLine, '|', " |");
		}

		System.out.println(newLine);
	}

	private static void _printWarningLine(
		String line, int lineLength, boolean wrapLine) {

		if (wrapLine) {
			if (line.length() <= (lineLength - 6)) {
				_printWarningLine(line, lineLength);

				return;
			}

			int x = line.lastIndexOf(CharPool.SPACE, lineLength - 9);

			_printWarningLine(line.substring(0, x) + "...", lineLength);

			return;
		}

		while (true) {
			if (line.length() <= (lineLength - 6)) {
				_printWarningLine(line, lineLength);

				return;
			}

			int x = line.lastIndexOf(CharPool.SPACE, lineLength - 6);

			_printWarningLine(line.substring(0, x), lineLength);

			line = StringUtil.trim(line.substring(x));
		}
	}

	private static void _printWarnings(
		List<String> violatingCommitMessages, List<String> violatingWords,
		int lineLength) {

		if (violatingCommitMessages.isEmpty()) {
			return;
		}

		_printBorder(StringPool.SPACE, lineLength);

		_printWarningLine(StringPool.BLANK, lineLength);

		StringBundler sb = new StringBundler();

		sb.append("The following ");

		if (violatingCommitMessages.size() == 1) {
			sb.append("commit contains ");
		}
		else {
			sb.append("commits contain ");
		}

		if (violatingWords.size() == 1) {
			sb.append("the word ");
		}
		else {
			sb.append("the words ");
		}

		for (int i = 0; i < violatingWords.size(); i++) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(violatingWords.get(i));
			sb.append(StringPool.APOSTROPHE);

			if (i < (violatingWords.size() - 2)) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
			else if (i < (violatingWords.size() - 1)) {
				sb.append(" and ");
			}
		}

		sb.append(", which could reveal potential security vulnerablities:");

		_printWarningLine(sb.toString(), lineLength, false);

		_printWarningLine(StringPool.BLANK, lineLength);

		for (String violatingCommitMessage : violatingCommitMessages) {
			_printWarningLine("* " + violatingCommitMessage, lineLength, true);
		}

		_printBorder(StringPool.PIPE, lineLength);

		System.out.println();
	}

	private static final Pattern _jiraTicketIdPattern = Pattern.compile(
		"^[A-Z0-9]+-[0-9]+");

}