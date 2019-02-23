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