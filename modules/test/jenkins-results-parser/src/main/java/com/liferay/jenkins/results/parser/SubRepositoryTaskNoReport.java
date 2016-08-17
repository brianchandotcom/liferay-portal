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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leslie Wong
 */
public class SubrepositoryTaskNoReport extends SubrepositoryTask {

	public SubrepositoryTaskNoReport(String console) throws Exception {
		this.console = console;

		Matcher matcher = pattern.matcher(console);

		if (matcher.find()) {
			String taskStatus = matcher.group(1);

			if (taskStatus.equals("SUCCESSFUL")) {
				result = "SUCCESS";
			}
			else {
				String errorMessage = matcher.group(2);

				if (errorMessage.equals(": Parallel execution timed out")) {
					result = "ABORTED";
				}
				else {
					result = "FAILURE";
				}
			}
		}
		else {
			result = "FAILURE";
		}
	}

	@Override
	public String getGitHubMessage() throws Exception {
		StringBuilder sb = new StringBuilder();

		GenericFailureMessageGenerator genericFailureMessageGenerator =
			new GenericFailureMessageGenerator();

		sb.append("<pre><code>");

		sb.append(
			genericFailureMessageGenerator.getMessage(null, console, null));

		sb.append("</code></pre>");

		return sb.toString();
	}

	protected static final Pattern pattern = Pattern.compile(
		"Task (SUCCESSFUL|FAILED)(.*)");

	protected String console;

}