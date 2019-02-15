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

	public void addJavaTerm(
		String content, Position startPosition, Position endPosition) {

		_parsedJavaTermsMap.put(
			startPosition,
			new ParsedJavaTerm(content, startPosition, endPosition));
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

	private final Map<Position, ParsedJavaTerm> _parsedJavaTermsMap =
		new TreeMap<>(Collections.reverseOrder());
	private final Map<Position, CommonHiddenStreamToken>
		_precedingCommentTokensMap = new HashMap<>();

}