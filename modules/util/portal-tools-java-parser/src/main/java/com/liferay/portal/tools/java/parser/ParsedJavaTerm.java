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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.tools.java.parser.util.JavaParserUtil;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

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
		ParsedJavaTerm nextParsedJavaTerm = getNextParsedJavaTerm();

		if (nextParsedJavaTerm == null) {
			return NO_ACTION_REQUIRED;
		}

		CommonHiddenStreamToken precedingCommentToken =
			nextParsedJavaTerm.getPrecedingCommentToken();

		if (precedingCommentToken != null) {
			while (precedingCommentToken.getHiddenBefore() != null) {
				precedingCommentToken = precedingCommentToken.getHiddenBefore();
			}

			if (((precedingCommentToken.getType() ==
					TokenTypes.BLOCK_COMMENT_BEGIN) &&
				 StringUtil.startsWith(
					 precedingCommentToken.getText(), CharPool.STAR)) ||
				((precedingCommentToken.getType() ==
					TokenTypes.SINGLE_LINE_COMMENT) &&
				 StringUtil.startsWith(
					 precedingCommentToken.getText(), CharPool.SPACE))) {

				// TODO

				return NO_ACTION_REQUIRED;
			}
		}

		if (_content.endsWith("-> {")) {
			return SINGLE_LINE_BREAK_REQUIRED;
		}

		String trimmedNextJavaTermContent = StringUtil.trim(
			nextParsedJavaTerm.getContent());

		if (StringUtil.startsWith(
				StringUtil.trim(_content), CharPool.CLOSE_CURLY_BRACE)) {

			if (!_content.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				// TODO

				return NO_ACTION_REQUIRED;
			}

			if (!trimmedNextJavaTermContent.equals(
					StringPool.OPEN_CURLY_BRACE)) {

				return DOUBLE_LINE_BREAK_REQUIRED;
			}
		}

		if (_className.equals(JavaAnnotationFieldDefinition.class.getName())) {
			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		int lineCount = StringUtil.count(_content, CharPool.NEW_LINE) + 1;

		if (_className.equals(JavaEnumConstantDefinitions.class.getName()) &&
			(!_content.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			 (lineCount == 1) ||
			 Objects.equals(
				 _getIndent(_content, 1), _getIndent(_content, lineCount)))) {

			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		if (_content.endsWith(StringPool.SEMICOLON)) {
			if (_className.equals(JavaMethodDefinition.class.getName())) {
				return DOUBLE_LINE_BREAK_REQUIRED;
			}

			if (_className.equals(JavaBreakStatement.class.getName()) ||
				_className.equals(JavaContinueStatement.class.getName()) ||
				_className.equals(JavaReturnStatement.class.getName()) ||
				_className.equals(JavaThrowStatement.class.getName())) {

				return SINGLE_LINE_BREAK_REQUIRED;
			}

			return NO_ACTION_REQUIRED;
		}

		if (!_content.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			trimmedNextJavaTermContent.startsWith(
				StringPool.CLOSE_CURLY_BRACE)) {

			// TODO

			return NO_ACTION_REQUIRED;
		}

		if (_className.equals(JavaClassDefinition.class.getName())) {
			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		if (trimmedNextJavaTermContent.equals(StringPool.OPEN_CURLY_BRACE)) {
			String lastLine = StringUtil.trim(
				JavaParserUtil.getLastLine(_content));

			if (lastLine.startsWith("new ") ||
				_containsUnquoted(lastLine, " new ")) {

				return SINGLE_LINE_BREAK_REQUIRED;
			}

			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		if ((lineCount == 1) ||
			Objects.equals(
				_getIndent(_content, 1), _getIndent(_content, lineCount))) {

			String lastLine = StringUtil.trim(
				JavaParserUtil.getLastLine(_content));

			int x = lastLine.lastIndexOf(" new ");

			if ((x != -1) && !ToolsUtil.isInsideQuotes(lastLine, x) &&
				(ToolsUtil.getLevel(lastLine.substring(x)) == 0)) {

				return DOUBLE_LINE_BREAK_REQUIRED;
			}

			return SINGLE_LINE_BREAK_REQUIRED;
		}

		return DOUBLE_LINE_BREAK_REQUIRED;
	}

	public ParsedJavaTerm getNextParsedJavaTerm() {
		return _nextParsedJavaTerm;
	}

	public CommonHiddenStreamToken getPrecedingCommentToken() {
		return _precedingCommentToken;
	}

	public int getPrecedingLineAction() {
		if (((_precedingCommentToken != null) &&
			 StringUtil.startsWith(
				 StringUtil.trim(_precedingCommentToken.getText()),
				 CharPool.STAR)) ||
			StringUtil.startsWith(
				StringUtil.trim(_content), StringPool.CLOSE_CURLY_BRACE)) {

			// TODO

			return NO_ACTION_REQUIRED;
		}

		ParsedJavaTerm previousParsedJavaTerm = getPreviousParsedJavaTerm();

		if (StringUtil.endsWith(
				previousParsedJavaTerm.getContent(),
				CharPool.OPEN_CURLY_BRACE)) {

			return NO_ACTION_REQUIRED;
		}

		if (_className.equals(JavaAnnotationFieldDefinition.class.getName()) ||
			_className.equals(JavaBreakStatement.class.getName()) ||
			_className.equals(JavaConstructorDefinition.class.getName()) ||
			_className.equals(JavaContinueStatement.class.getName()) ||
			_className.equals(JavaDoStatement.class.getName()) ||
			_className.equals(JavaEnhancedForStatement.class.getName()) ||
			_className.equals(JavaEnumConstantDefinitions.class.getName()) ||
			_className.equals(JavaForStatement.class.getName()) ||
			_className.equals(JavaIfStatement.class.getName()) ||
			_className.equals(JavaMethodDefinition.class.getName()) ||
			_className.equals(JavaReturnStatement.class.getName()) ||
			_className.equals(JavaStaticInitialization.class.getName()) ||
			_className.equals(JavaSynchronizedStatement.class.getName()) ||
			_className.equals(JavaThrowStatement.class.getName()) ||
			_className.equals(JavaTryStatement.class.getName())) {

			return DOUBLE_LINE_BREAK_REQUIRED;
		}

		if (_className.equals(JavaCatchStatement.class.getName()) ||
			_className.equals(JavaElseStatement.class.getName()) ||
			_className.equals(JavaFinallyStatement.class.getName())) {

			return SINGLE_LINE_BREAK_REQUIRED;
		}

		if (_className.equals(JavaWhileStatement.class.getName())) {
			if (_content.endsWith(StringPool.OPEN_CURLY_BRACE)) {
				return DOUBLE_LINE_BREAK_REQUIRED;
			}

			if (Objects.equals(
					previousParsedJavaTerm.getClassName(),
					JavaDoStatement.class.getName())) {

				return SINGLE_LINE_BREAK_REQUIRED;
			}
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

	private boolean _containsUnquoted(String s, String text) {
		int x = -1;

		while (true) {
			x = s.indexOf(text, x + 1);

			if (x == -1) {
				return false;
			}

			if (!ToolsUtil.isInsideQuotes(s, x)) {
				return true;
			}
		}
	}

	private String _getIndent(String s, int lineNumber) {
		int x = -1;

		for (int i = 1; i < lineNumber; i++) {
			x = s.indexOf(CharPool.NEW_LINE, x + 1);
		}

		StringBundler sb = new StringBundler();

		for (int i = x + 1;; i++) {
			if (s.charAt(i) != CharPool.TAB) {
				return sb.toString();
			}

			sb.append(CharPool.TAB);
		}
	}

	private final String _className;
	private boolean _containsCommentToken;
	private final String _content;
	private final Position _endPosition;
	private ParsedJavaTerm _nextParsedJavaTerm;
	private CommonHiddenStreamToken _precedingCommentToken;
	private ParsedJavaTerm _previousParsedJavaTerm;
	private final Position _startPosition;

}