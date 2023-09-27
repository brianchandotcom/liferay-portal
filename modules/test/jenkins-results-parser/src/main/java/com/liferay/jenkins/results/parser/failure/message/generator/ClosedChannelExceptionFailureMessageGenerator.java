/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ClosedChannelExceptionFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!(consoleText.contains(_TOKEN_CLOSED_CHANNEL_EXCEPTION) &&
			  consoleText.contains(_TOKEN_REQUEST_ABORTED_EXCEPTION))) {

			return null;
		}

		int start = consoleText.indexOf(_TOKEN_CLOSED_CHANNEL_EXCEPTION);

		start = consoleText.lastIndexOf(_TOKEN_CLOSED_CHANNEL_EXCEPTION, start);

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf(_TOKEN_REQUEST_ABORTED_EXCEPTION, start);

		end = consoleText.lastIndexOf("\n", end);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Irrecoverable Jenkins Error: ",
				Dom4JUtil.getNewAnchorElement(
					"https://issues.liferay.com/browse/LRCI-1422", null,
					"ClosedChannelException")),
			getConsoleTextSnippetElement(consoleText, true, start, end));
	}

	private static final String _TOKEN_CLOSED_CHANNEL_EXCEPTION =
		"FATAL: java.nio.channels.ClosedChannelException";

	private static final String _TOKEN_REQUEST_ABORTED_EXCEPTION =
		"Caused: hudson.remoting.RequestAbortedException";

}