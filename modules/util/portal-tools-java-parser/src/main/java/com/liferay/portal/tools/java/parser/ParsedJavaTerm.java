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

/**
 * @author Hugo Huijser
 */
public class ParsedJavaTerm implements Comparable<ParsedJavaTerm> {

	public ParsedJavaTerm(
		CommonHiddenStreamToken precedingCommentToken, Position startPosition) {

		_precedingCommentToken = precedingCommentToken;
		_startPosition = startPosition;
	}

	public ParsedJavaTerm(
		String content, Position startPosition, Position endPosition) {

		_content = content;
		_startPosition = startPosition;
		_endPosition = endPosition;
	}

	@Override
	public int compareTo(ParsedJavaTerm parsedJavaTerm) {
		return _startPosition.compareTo(parsedJavaTerm.getStartPosition());
	}

	public String getContent() {
		return _content;
	}

	public Position getEndPosition() {
		return _endPosition;
	}

	public CommonHiddenStreamToken getPrecedingCommentToken() {
		return _precedingCommentToken;
	}

	public Position getStartPosition() {
		return _startPosition;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setEndPosition(Position endPosition) {
		_endPosition = endPosition;
	}

	public void setPrecedingCommentToken(
		CommonHiddenStreamToken precedingCommentToken) {

		_precedingCommentToken = precedingCommentToken;
	}

	private String _content;
	private Position _endPosition;
	private CommonHiddenStreamToken _precedingCommentToken;
	private final Position _startPosition;

}