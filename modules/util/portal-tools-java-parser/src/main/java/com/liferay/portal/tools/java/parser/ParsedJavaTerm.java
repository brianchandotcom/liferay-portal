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

import com.liferay.petra.string.StringPool;
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

	public int getFollowingLineAction() {
		if (_className.equals(JavaMethodDefinition.class.getName()) &&
			_content.endsWith(StringPool.SEMICOLON)) {

			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		return NO_ACTION_REQUIRED;
	}

	public ParsedJavaTerm getNextParsedJavaTerm() {
		return _nextParsedJavaTerm;
	}

	public CommonHiddenStreamToken getPrecedingCommentToken() {
		return _precedingCommentToken;
	}

	public int getPrecedingLineAction() {
		if ((_precedingCommentToken != null) &&
			StringUtil.startsWith(
				StringUtil.trim(_precedingCommentToken.getText()), "*")) {

			return SINGLE_LINE_BREAK_REQUIRED;
		}

		if (_className.equals(JavaMethodDefinition.class.getName()) &&
			_content.endsWith(StringPool.SEMICOLON)) {

			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		return NO_ACTION_REQUIRED;
	}

	public ParsedJavaTerm getPreviousParsedJavaTerm() {
		return _previousParsedJavaTerm;
	}

	public Position getStartPosition() {
		return _startPosition;
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

	protected static final int DOUBLE_LINE_BREAK_REQUIRED = 0;

	protected static final int NO_ACTION_REQUIRED = 1;

	protected static final int SINGLE_LINE_BREAK_REQUIRED = 2;

	private final String _className;
	private boolean _containsCommentToken;
	private final String _content;
	private final Position _endPosition;
	private ParsedJavaTerm _nextParsedJavaTerm;
	private CommonHiddenStreamToken _precedingCommentToken;
	private ParsedJavaTerm _previousParsedJavaTerm;
	private final Position _startPosition;

}