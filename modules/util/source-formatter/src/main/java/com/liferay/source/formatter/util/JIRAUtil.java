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

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			if (commitMessage.startsWith("Revert ")) {
				continue;
			}

			if (commitMessage.endsWith("/ci-merge.")) {
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

		Map<String, Integer> ticketStatusMap = new HashMap<>();

		Set<String> violatingCommitMessages = new TreeSet<>();
		Set<String> violatingWords = new TreeSet<>();

		for (String commitMessage : commitMessages) {
			if (ticketStatusMap.size() == maxNumberOfTickets) {
				return;
			}

			String jiraTicketId = _getJIRATicketId(commitMessage);

			Integer jiraTicketStatus = ticketStatusMap.get(jiraTicketId);

			if (jiraTicketStatus == null) {
				jiraTicketStatus = _getJIRATicketStatus(jiraTicketId);

				ticketStatusMap.put(jiraTicketId, jiraTicketStatus);
			}

			if (jiraTicketStatus.intValue() != _STATUS_PRIVATE_TICKET) {
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
			if (commitMessage.startsWith("Revert ")) {
				continue;
			}

			if (commitMessage.endsWith("/ci-merge.")) {
				continue;
			}

			if (validatedTicketIds.size() == maxNumberOfTickets) {
				return;
			}

			String jiraTicketId = _getJIRATicketId(commitMessage);

			if (!validatedTicketIds.add(jiraTicketId)) {
				continue;
			}

			int jiraTicketStatus = _getJIRATicketStatus(jiraTicketId);

			if (jiraTicketStatus == _STATUS_NO_INTERNET_CONNECTION) {
				return;
			}

			if (jiraTicketStatus == _STATUS_NONEXISTING_TICKET) {
				throw new Exception(
					"Commit message is pointing to non-existing JIRA issue: " +
						jiraTicketId);
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

	private static int _getJIRATicketStatus(String jiraTicketId) {
		try {
			URL url = new URL(
				"https://issues.liferay.com/rest/api/2/issue/" + jiraTicketId);

			url.openStream();
		}
		catch (IOException ioException) {
			if (ioException instanceof FileNotFoundException) {
				return _STATUS_NONEXISTING_TICKET;
			}

			if (ioException instanceof UnknownHostException) {
				return _STATUS_NO_INTERNET_CONNECTION;
			}

			return _STATUS_PRIVATE_TICKET;
		}

		return _STATUS_PUBLIC_TICKET;
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

	private static final int _STATUS_NO_INTERNET_CONNECTION = 0;

	private static final int _STATUS_NONEXISTING_TICKET = 1;

	private static final int _STATUS_PRIVATE_TICKET = 2;

	private static final int _STATUS_PUBLIC_TICKET = 3;

	private static final Pattern _jiraTicketIdPattern = Pattern.compile(
		"^[A-Z0-9]+-[0-9]+");

}