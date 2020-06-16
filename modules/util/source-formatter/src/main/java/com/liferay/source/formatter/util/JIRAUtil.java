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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.net.UnknownHostException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JIRAUtil {

	public static void validateJIRAProjectNames(
			List<String> commitMessages, List<String> projectNames)
		throws Exception {

		outerLoop:
		for (String commitMessage : commitMessages) {
			for (String projectName : projectNames) {
				if (commitMessage.startsWith(projectName + "-")) {
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

	public static void validateJIRATicketIds(
			List<String> commitMessages, int maxNumberOfTickets)
		throws Exception {

		Set<String> validatedTicketIds = new HashSet<>();

		for (String commitMessage : commitMessages) {
			if (validatedTicketIds.size() == maxNumberOfTickets) {
				return;
			}

			Matcher matcher = _jiraTicketIdPattern.matcher(commitMessage);

			if (!matcher.find()) {
				continue;
			}

			String ticketId = matcher.group();

			if (!validatedTicketIds.add(ticketId)) {
				continue;
			}

			try {
				URL url = new URL(
					"https://issues.liferay.com/rest/api/2/issue/" + ticketId);

				url.openStream();
			}
			catch (IOException ioException) {
				if (ioException instanceof FileNotFoundException) {
					throw new Exception(
						"Commit message is pointing to non-existing JIRA " +
							"issue: " + ticketId);
				}

				if (ioException instanceof UnknownHostException) {

					// No Internet connection

					return;
				}

				// Ticket exists, but user has no permission to view

			}
		}
	}

	private static final Pattern _jiraTicketIdPattern = Pattern.compile(
		"^[^-]+-[0-9]+");

}