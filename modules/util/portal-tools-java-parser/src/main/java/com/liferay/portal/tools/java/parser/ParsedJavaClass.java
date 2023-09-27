/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import antlr.CommonHiddenStreamToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class ParsedJavaClass {

	public void addJavaTerm(
		String content, Position startPosition, Position endPosition,
		String className) {

		addJavaTerm(content, startPosition, endPosition, className, null, null);
	}

	public void addJavaTerm(
		String content, Position startPosition, Position endPosition,
		String className, String precedingNestedCodeBlockClassName,
		String followingNestedCodeBlockClassName) {

		ParsedJavaTerm parsedJavaTerm = new ParsedJavaTerm(
			content, startPosition, endPosition, className,
			precedingNestedCodeBlockClassName,
			followingNestedCodeBlockClassName);

		if (_firstParsedJavaTerm == null) {
			_firstParsedJavaTerm = parsedJavaTerm;
			_lastParsedJavaTerm = parsedJavaTerm;

			return;
		}

		if (parsedJavaTerm.compareTo(_lastParsedJavaTerm) > 0) {
			_lastParsedJavaTerm.setNextParsedJavaTerm(parsedJavaTerm);

			parsedJavaTerm.setPreviousParsedJavaTerm(_lastParsedJavaTerm);

			_lastParsedJavaTerm = parsedJavaTerm;

			return;
		}

		ParsedJavaTerm previousParsedJavaTerm =
			_lastParsedJavaTerm.getPreviousParsedJavaTerm();

		while (true) {
			if (previousParsedJavaTerm == null) {
				_firstParsedJavaTerm.setPreviousParsedJavaTerm(parsedJavaTerm);

				parsedJavaTerm.setNextParsedJavaTerm(_firstParsedJavaTerm);

				_firstParsedJavaTerm = parsedJavaTerm;

				return;
			}

			int value = parsedJavaTerm.compareTo(previousParsedJavaTerm);

			if (value == 0) {
				return;
			}

			if (value > 0) {
				parsedJavaTerm.setPreviousParsedJavaTerm(
					previousParsedJavaTerm);

				ParsedJavaTerm nextParsedJavaTerm =
					previousParsedJavaTerm.getNextParsedJavaTerm();

				parsedJavaTerm.setNextParsedJavaTerm(nextParsedJavaTerm);

				previousParsedJavaTerm.setNextParsedJavaTerm(parsedJavaTerm);

				nextParsedJavaTerm.setPreviousParsedJavaTerm(parsedJavaTerm);

				return;
			}

			previousParsedJavaTerm =
				previousParsedJavaTerm.getPreviousParsedJavaTerm();
		}
	}

	public void addPrecedingCommentToken(
		CommonHiddenStreamToken precedingCommentToken, Position startPosition) {

		ParsedJavaTerm parsedJavaTerm = _lastParsedJavaTerm;

		while (true) {
			if (parsedJavaTerm == null) {
				_precedingCommentTokensMap.put(
					startPosition, precedingCommentToken);

				return;
			}

			if (startPosition.equals(parsedJavaTerm.getStartPosition())) {
				parsedJavaTerm.setPrecedingCommentToken(precedingCommentToken);

				return;
			}

			parsedJavaTerm = parsedJavaTerm.getPreviousParsedJavaTerm();
		}
	}

	public boolean containsNestedCommentToken() {
		ParsedJavaTerm parsedJavaTerm = _lastParsedJavaTerm;

		while (true) {
			if (parsedJavaTerm == null) {
				return false;
			}

			if (parsedJavaTerm.containsCommentToken()) {
				return true;
			}

			parsedJavaTerm = parsedJavaTerm.getPreviousParsedJavaTerm();
		}
	}

	public ParsedJavaTerm getLastParsedJavaTerm() {
		return _lastParsedJavaTerm;
	}

	public void processCommentTokens() {
		for (Map.Entry<Position, CommonHiddenStreamToken> entry :
				_precedingCommentTokensMap.entrySet()) {

			Position startPosition = entry.getKey();

			ParsedJavaTerm parsedJavaTerm = _lastParsedJavaTerm;

			while (true) {
				if (parsedJavaTerm == null) {
					break;
				}

				if (startPosition.compareTo(parsedJavaTerm.getStartPosition()) >
						0) {

					parsedJavaTerm.setContainsCommentToken(true);

					break;
				}

				parsedJavaTerm = parsedJavaTerm.getPreviousParsedJavaTerm();
			}
		}
	}

	private ParsedJavaTerm _firstParsedJavaTerm;
	private ParsedJavaTerm _lastParsedJavaTerm;
	private final Map<Position, CommonHiddenStreamToken>
		_precedingCommentTokensMap = new HashMap<>();

}