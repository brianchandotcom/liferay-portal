/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.parser.bbcode.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Iliyan Peychev
 */
public class BBCodeLexer {

	public BBCodeLexer(String data) {
		_matcher = _pattern.matcher(data);
	}

	public int getLastIndex() {
		return _matcher.end();
	}

	public BBCodeToken getNextBBCodeToken() {
		if (!_matcher.find()) {
			return null;
		}

		return new BBCodeToken(
			_matcher.group(1), _matcher.group(2), _matcher.group(3),
			_matcher.start(), _matcher.end());
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?:\\[((?:[a-z]|\\*){1,16})(?:[=\\s]([^\\x00-\\x1F'<>\\[\\]]" +
			"{1,2083}))?\\])|(?:\\[\\/([a-z]{1,16})\\])",
		Pattern.CASE_INSENSITIVE);

	private final Matcher _matcher;

}