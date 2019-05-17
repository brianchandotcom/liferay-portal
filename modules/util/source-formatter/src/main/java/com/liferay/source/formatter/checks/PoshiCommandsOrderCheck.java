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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiCommandsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int commandStartLineNumber = 0;

		Matcher matcher = _commandPattern.matcher(content);

		List<String> commands = new ArrayList<>();

		while (matcher.find()) {
			if (commandStartLineNumber == 0) {
				commandStartLineNumber = getLineNumber(
					content, matcher.start());
			}

			commands.add(matcher.group());
		}

		if (commands.size() < 2) {
			return content;
		}

		List<String> oldCommands = new ArrayList<>(commands);

		Collections.sort(commands, new CommandComparator());

		if (oldCommands.equals(commands)) {
			return content;
		}

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (lineNumber == commandStartLineNumber) {
					break;
				}

				sb.append(line);
				sb.append("\n");
			}

			sb.append(ListUtil.toString(commands, StringPool.BLANK, "\n"));
			sb.append("}");
		}

		return sb.toString();
	}

	private static final Pattern _commandPattern = Pattern.compile(
		"(?<=\n)([\t ]*@.+?=.+?\n)*[\t ]*(function|macro|test)([\t ]+).+\n" +
			"(.*\n)*?\t\\}\n(?=\\s*(@|function|macro|test|\\s*\\}$))");

	private class CommandComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String s1, String s2) {
			return super.compare(_getCommandName(s1), _getCommandName(s2));
		}

		private String _getCommandName(String s) {
			Matcher matcher = _commandNamePattern.matcher(s);

			if (matcher.find()) {
				return matcher.group();
			}

			return StringPool.BLANK;
		}

		private final Pattern _commandNamePattern = Pattern.compile(
			"^[\t ]*(function|macro|test)([\t ]+).+(\n|\\Z)",
			Pattern.MULTILINE);

	}

}