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

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class ParsedJavaTerm implements Comparable<ParsedJavaTerm> {

	public ParsedJavaTerm(
		String content, Position startPosition, Position endPosition,
		String className) {

		_content = content;
		_startPosition = startPosition;
		_endPosition = endPosition;
		_className = className;
	}

	@Override
	public int compareTo(ParsedJavaTerm parsedJavaTerm) {
		return _startPosition.compareTo(parsedJavaTerm.getStartPosition());
	}

	public boolean containsCommentToken() {
		return _containsCommentToken;
	}

	public String getClassName() {
		return _className;
	}

	public String getContent() {
		return _content;
	}

	public Position getEndPosition() {
		return _endPosition;
	}

	public ParsedJavaTerm getNextParsedJavaTerm() {
		return _nextParsedJavaTerm;
	}

	public CommonHiddenStreamToken getPrecedingCommentToken() {
		return _precedingCommentToken;
	}

	public ParsedJavaTerm getPreviousParsedJavaTerm() {
		return _previousParsedJavaTerm;
	}

	public Position getStartPosition() {
		return _startPosition;
	}

	public boolean requireFollowingEmptyLine() {
		return _requireFollowingEmptyLine;
	}

	public boolean requireNoFollowingEmptyLine() {
		return _requireNoFollowingEmptyLine;
	}

	public boolean requireNoPrecedingEmptyLine() {
		return _requireNoPrecedingEmptyLine;
	}

	public boolean requirePrecedingEmptyLine() {
		if (!_requirePrecedingEmptyLine) {
			return _requirePrecedingEmptyLine;
		}

		if (_precedingCommentToken == null) {
			return true;
		}

		if (StringUtil.startsWith(
				StringUtil.trim(_precedingCommentToken.getText()), "*")) {

			return false;
		}

		return true;
	}

	public void setContainsCommentToken(boolean containsCommentToken) {
		_containsCommentToken = containsCommentToken;
	}

	public void setNextParsedJavaTerm(ParsedJavaTerm nextParsedJavaTerm) {
		_nextParsedJavaTerm = nextParsedJavaTerm;
	}

	public void setPrecedingCommentToken(
		CommonHiddenStreamToken precedingCommentToken) {

		_precedingCommentToken = precedingCommentToken;
	}

	public void setPreviousParsedJavaTerm(
		ParsedJavaTerm previousParsedJavaTerm) {

		_previousParsedJavaTerm = previousParsedJavaTerm;
	}

	public void setRequireFollowingEmptyLine(
		boolean requireFollowingEmptyLine) {

		_requireFollowingEmptyLine = requireFollowingEmptyLine;
	}

	public void setRequireNoFollowingEmptyLine(
		boolean requireNoFollowingEmptyLine) {

		_requireNoFollowingEmptyLine = requireNoFollowingEmptyLine;
	}

	public void setRequireNoPrecedingEmptyLine(
		boolean requireNoPrecedingEmptyLine) {

		_requireNoPrecedingEmptyLine = requireNoPrecedingEmptyLine;
	}

	public void setRequirePrecedingEmptyLine(
		boolean requirePrecedingEmptyLine) {

		_requirePrecedingEmptyLine = requirePrecedingEmptyLine;
	}

	private final String _className;
	private boolean _containsCommentToken;
	private final String _content;
	private final Position _endPosition;
	private ParsedJavaTerm _nextParsedJavaTerm;
	private CommonHiddenStreamToken _precedingCommentToken;
	private ParsedJavaTerm _previousParsedJavaTerm;
	private boolean _requireFollowingEmptyLine;
	private boolean _requireNoFollowingEmptyLine;
	private boolean _requireNoPrecedingEmptyLine;
	private boolean _requirePrecedingEmptyLine;
	private final Position _startPosition;

}