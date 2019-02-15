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
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class ParsedJavaClass {

	public void addJavaTerm(
		String content, Position startPosition, Position endPosition) {

		ParsedJavaTerm parsedJavaTerm = _parsedJavaTermsMap.get(startPosition);

		if (parsedJavaTerm != null) {
			parsedJavaTerm.setContent(content);
			parsedJavaTerm.setEndPosition(endPosition);
		}
		else {
			parsedJavaTerm = new ParsedJavaTerm(
				content, startPosition, endPosition);
		}

		_parsedJavaTermsMap.put(startPosition, parsedJavaTerm);
	}

	public void addPrecedingCommentToken(
		CommonHiddenStreamToken precedingCommentToken, Position startPosition) {

		ParsedJavaTerm parsedJavaTerm = _parsedJavaTermsMap.get(startPosition);

		if (parsedJavaTerm != null) {
			parsedJavaTerm.setPrecedingCommentToken(precedingCommentToken);
		}
		else {
			parsedJavaTerm = new ParsedJavaTerm(
				precedingCommentToken, startPosition);
		}

		_parsedJavaTermsMap.put(startPosition, parsedJavaTerm);
	}

	public Map<Position, ParsedJavaTerm> getParsedJavaTermsMap() {
		return _parsedJavaTermsMap;
	}

	private final Map<Position, ParsedJavaTerm> _parsedJavaTermsMap =
		new TreeMap<>(Collections.reverseOrder());

}