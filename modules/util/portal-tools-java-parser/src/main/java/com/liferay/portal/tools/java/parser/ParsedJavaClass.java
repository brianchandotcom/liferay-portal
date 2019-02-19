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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class ParsedJavaClass {

	public void addParsedJavaTerm(ParsedJavaTerm parsedJavaTerm) {
		_parsedJavaTermsMap.put(
			parsedJavaTerm.getStartPosition(), parsedJavaTerm);
	}

	public void addPrecedingCommentToken(
		CommonHiddenStreamToken precedingCommentToken, Position startPosition) {

		ParsedJavaTerm parsedJavaTerm = _parsedJavaTermsMap.get(startPosition);

		if (parsedJavaTerm != null) {
			parsedJavaTerm.setPrecedingCommentToken(precedingCommentToken);

			_parsedJavaTermsMap.put(startPosition, parsedJavaTerm);
		}
		else {
			_precedingCommentTokensMap.put(
				startPosition, precedingCommentToken);
		}
	}

	public Map<Position, ParsedJavaTerm> getParsedJavaTermsMap() {
		return _parsedJavaTermsMap;
	}

	public void processCommentTokens() {
		for (Map.Entry<Position, CommonHiddenStreamToken> entry1 :
				_precedingCommentTokensMap.entrySet()) {

			Position startPosition = entry1.getKey();

			for (Map.Entry<Position, ParsedJavaTerm> entry2 :
					_parsedJavaTermsMap.entrySet()) {

				ParsedJavaTerm parsedJavaTerm = entry2.getValue();

				int value = startPosition.compareTo(
					parsedJavaTerm.getStartPosition());

				if (value < 0) {
					continue;
				}

				if (value == 0) {
					parsedJavaTerm.setPrecedingCommentToken(entry1.getValue());
				}
				else if (startPosition.compareTo(
							parsedJavaTerm.getEndPosition()) < 0) {

					parsedJavaTerm.setContainsCommentToken(true);
				}

				_parsedJavaTermsMap.put(entry2.getKey(), parsedJavaTerm);

				break;
			}
		}
	}

	private final Map<Position, ParsedJavaTerm> _parsedJavaTermsMap =
		new TreeMap<>(Collections.reverseOrder());
	private final Map<Position, CommonHiddenStreamToken>
		_precedingCommentTokensMap = new HashMap<>();

}