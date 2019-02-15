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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.java.parser.util.DetailASTUtil;
import com.liferay.portal.tools.java.parser.util.FileUtil;
import com.liferay.portal.tools.java.parser.util.JavaParserUtil;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class JavaParser {

	public static boolean parse(File file, int maxLineLength)
		throws CheckstyleException, IOException {

		_maxLineLength = maxLineLength;

		return _parse(file, false);
	}

	private static ParsedJavaClass _addClosingJavaTerm(
		ParsedJavaClass parsedJavaClass, DetailAST closingDetailAST,
		FileContents fileContents) {

		DetailAST rcurlyDetailAST = null;

		if (closingDetailAST.getType() == TokenTypes.LCURLY) {
			DetailAST parentDetailAST = closingDetailAST.getParent();

			rcurlyDetailAST = parentDetailAST.getLastChild();
		}
		else if (closingDetailAST.getType() == TokenTypes.SLIST) {
			rcurlyDetailAST = closingDetailAST.getLastChild();
		}

		if (rcurlyDetailAST != null) {
			JavaClosingBrace javaClosingBrace = new JavaClosingBrace();

			String curlyExpecedIndent = _getExpectedIndent(
				rcurlyDetailAST, fileContents);

			String content = javaClosingBrace.toString(
				curlyExpecedIndent, StringPool.BLANK, _maxLineLength);

			parsedJavaClass.addJavaTerm(
				content, DetailASTUtil.getStartPosition(rcurlyDetailAST),
				DetailASTUtil.getEndPosition(rcurlyDetailAST, fileContents));
		}

		return parsedJavaClass;
	}

	private static ParsedJavaClass _addJavaTerm(
		ParsedJavaClass parsedJavaClass, DetailAST detailAST, JavaTerm javaTerm,
		FileContents fileContents) {

		if (javaTerm == null) {
			return parsedJavaClass;
		}

		DetailAST closingDetailAST = DetailASTUtil.getClosingDetailAST(
			detailAST);

		if (closingDetailAST != null) {
			parsedJavaClass = _addClosingJavaTerm(
				parsedJavaClass, closingDetailAST, fileContents);
		}

		Position startPosition = DetailASTUtil.getStartPosition(detailAST);

		String expectedIndent = _getExpectedIndent(detailAST, fileContents);

		String content = javaTerm.toString(
			expectedIndent, StringPool.BLANK, _maxLineLength);

		if (content.contains(BaseJavaTerm.CODE_BLOCK)) {
			String[] parts = _getParts(
				content, "\n" + BaseJavaTerm.CODE_BLOCK + "\n");

			List<Position> curlyBracePositionList = _getCurlyBracePositionList(
				new ArrayList<>(), detailAST);

			Collections.sort(curlyBracePositionList);

			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];

				Position partStartPosition = null;

				if (i == 0) {
					partStartPosition = startPosition;
				}
				else {
					partStartPosition = curlyBracePositionList.get((i * 2) - 1);
				}

				Position partEndPosition = null;

				if (i == (parts.length - 1)) {
					partEndPosition = DetailASTUtil.getEndPosition(
						detailAST, fileContents);
				}
				else {
					partEndPosition = curlyBracePositionList.get(i * 2);
				}

				parsedJavaClass.addJavaTerm(
					part, partStartPosition, partEndPosition);
			}
		}
		else if (detailAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
			parsedJavaClass.addJavaTerm(
				content, startPosition,
				DetailASTUtil.getEndPosition(
					_getLastEnumConstantDefinitionDetailAST(detailAST),
					fileContents));
		}
		else {
			parsedJavaClass.addJavaTerm(
				content, startPosition,
				DetailASTUtil.getEndPosition(detailAST, fileContents));
		}

		return parsedJavaClass;
	}

	private static String _fixIndent(
		String content, int lineNumber, String actualIndent,
		String expectedIndent) {

		int x = _getLineStartPos(content, lineNumber);

		return StringUtil.replaceFirst(
			content, actualIndent, expectedIndent, x);
	}

	private static List<Position> _getCurlyBracePositionList(
		List<Position> curlyBracePositionList, DetailAST detailAST) {

		if (detailAST == null) {
			return curlyBracePositionList;
		}

		if ((detailAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
			(detailAST.getType() == TokenTypes.LITERAL_NEW)) {

			DetailAST objblockDetailAST = detailAST.findFirstToken(
				TokenTypes.OBJBLOCK);

			if ((objblockDetailAST != null) &&
				(objblockDetailAST.getChildCount() > 2)) {

				DetailAST leftCurlyDetailAST =
					objblockDetailAST.getFirstChild();

				curlyBracePositionList.add(
					new Position(
						leftCurlyDetailAST.getLineNo(),
						leftCurlyDetailAST.getColumnNo() + 1));

				DetailAST rightCurlyDetailAST =
					objblockDetailAST.getLastChild();

				curlyBracePositionList.add(
					new Position(
						rightCurlyDetailAST.getLineNo(),
						rightCurlyDetailAST.getColumnNo()));
			}
		}
		else if (detailAST.getType() == TokenTypes.LAMBDA) {
			DetailAST lastChildDetailAST = detailAST.getLastChild();

			if (lastChildDetailAST.getType() == TokenTypes.SLIST) {
				curlyBracePositionList.add(
					new Position(
						lastChildDetailAST.getLineNo(),
						lastChildDetailAST.getColumnNo() + 1));

				lastChildDetailAST = lastChildDetailAST.getLastChild();

				curlyBracePositionList.add(
					new Position(
						lastChildDetailAST.getLineNo(),
						lastChildDetailAST.getColumnNo()));
			}
		}

		if ((detailAST.getType() != TokenTypes.OBJBLOCK) &&
			(detailAST.getType() != TokenTypes.SLIST)) {

			curlyBracePositionList = _getCurlyBracePositionList(
				curlyBracePositionList, detailAST.getFirstChild());
		}

		curlyBracePositionList = _getCurlyBracePositionList(
			curlyBracePositionList, detailAST.getNextSibling());

		return curlyBracePositionList;
	}

	private static String _getExpectedIndent(
		DetailAST detailAST, FileContents fileContents) {

		String indent = StringPool.BLANK;

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				break;
			}

			if ((parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.ELIST) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				indent += StringPool.TAB;
			}

			if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LITERAL_NEW) {
					String expectedIndent = _getExpectedIndent(
						parentDetailAST, fileContents);

					String actualIndent = _getIndent(
						fileContents.getLine(parentDetailAST.getLineNo() - 1));

					if (actualIndent.startsWith(expectedIndent)) {
						indent += StringUtil.replaceFirst(
							actualIndent, expectedIndent, StringPool.BLANK);
					}
				}

				continue;
			}

			if (parentDetailAST.getType() ==
					TokenTypes.RESOURCE_SPECIFICATION) {

				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LITERAL_TRY) {
					indent += "\t";
				}

				continue;
			}

			if (parentDetailAST.getType() == TokenTypes.SLIST) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LAMBDA) {
					String expectedIndent = _getExpectedIndent(
						parentDetailAST, fileContents);

					String actualIndent = _getIndent(
						fileContents.getLine(parentDetailAST.getLineNo() - 1));

					if (actualIndent.startsWith(expectedIndent)) {
						indent += StringUtil.replaceFirst(
							actualIndent, expectedIndent, StringPool.BLANK);
					}
				}

				continue;
			}

			if ((parentDetailAST.getType() != TokenTypes.LITERAL_ELSE) &&
				(parentDetailAST.getType() != TokenTypes.LITERAL_IF) &&
				(parentDetailAST.getType() != TokenTypes.SLIST)) {

				DetailAST grandParentDetailAST = parentDetailAST.getParent();

				if ((grandParentDetailAST != null) &&
					((grandParentDetailAST.getType() ==
						TokenTypes.LITERAL_ELSE) ||
					 (grandParentDetailAST.getType() ==
						 TokenTypes.LITERAL_IF) ||
					 (grandParentDetailAST.getType() ==
						 TokenTypes.LITERAL_WHILE))) {

					indent += "\t";
				}
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		if (detailAST.getType() != TokenTypes.RCURLY) {
			return indent;
		}

		return StringUtil.replaceFirst(indent, "\t", "");
	}

	private static String _getIndent(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != CharPool.TAB) {
				break;
			}

			sb.append(CharPool.TAB);
		}

		return sb.toString();
	}

	private static DetailAST _getLastEnumConstantDefinitionDetailAST(
		DetailAST enumConstantDefinitionDetailAST) {

		DetailAST lastEnumConstantDefinitionDetailAST =
			enumConstantDefinitionDetailAST;

		DetailAST nextSiblingDetailAST =
			enumConstantDefinitionDetailAST.getNextSibling();

		while (nextSiblingDetailAST != null) {
			if (nextSiblingDetailAST.getType() ==
					TokenTypes.ENUM_CONSTANT_DEF) {

				lastEnumConstantDefinitionDetailAST = nextSiblingDetailAST;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		return lastEnumConstantDefinitionDetailAST;
	}

	private static String _getLine(String content, int lineNumber) {
		int nextLineStartPos = _getLineStartPos(content, lineNumber);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	private static int _getLineStartPos(String content, int lineNumber) {
		if (lineNumber <= 0) {
			return -1;
		}

		if (lineNumber == 1) {
			return 0;
		}

		int x = 0;

		for (int i = 1; i < lineNumber; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	private static ParsedJavaClass _getParsedJavaClass(
		DetailAST rootDetailAST, FileContents fileContents) {

		ParsedJavaClass parsedJavaClass = _walk(
			new ParsedJavaClass(), rootDetailAST, fileContents);

		parsedJavaClass.processCommentTokens();

		return parsedJavaClass;
	}

	private static String[] _getParts(String s, String delimeter) {
		List<String> parts = new ArrayList<>();

		int x = 0;

		int y = s.indexOf(delimeter, x);

		while (y != -1) {
			parts.add(s.substring(x, y));

			x = y + delimeter.length();

			y = s.indexOf(delimeter, x);
		}

		if (x < s.length()) {
			parts.add(s.substring(x));
		}

		return parts.toArray(new String[parts.size()]);
	}

	private static boolean _isAtLineEnd(Position position, String line) {
		if (line.length() == position.getLinePosition()) {
			return true;
		}

		return false;
	}

	private static boolean _isAtLineStart(Position position, String line) {
		String s = StringUtil.trim(
			line.substring(0, position.getLinePosition()));

		if (Validator.isNull(s)) {
			return true;
		}

		return false;
	}

	private static boolean _parse(File file, boolean modified)
		throws CheckstyleException, IOException {

		FileText fileText = new FileText(
			file.getAbsoluteFile(), StandardCharsets.UTF_8.name());

		FileContents fileContents = new FileContents(fileText);

		DetailAST rootDetailAST =
			com.puppycrawl.tools.checkstyle.JavaParser.parse(fileContents);

		ParsedJavaClass parsedJavaClass = _getParsedJavaClass(
			rootDetailAST, fileContents);

		String content = FileUtil.read(file);

		String newContent = _parseContent(
			content, parsedJavaClass, fileContents);

		if (!newContent.equals(content)) {
			FileUtil.write(file, newContent);

			return _parse(file, true);
		}

		return modified;
	}

	private static String _parseContent(
		ParsedJavaTerm parsedJavaTerm, String content,
		FileContents fileContents) {

		if (parsedJavaTerm.getContent() == null) {
			return content;
		}

		Position startPosition = parsedJavaTerm.getStartPosition();

		int startLineNumber = startPosition.getLineNumber();

		if (!_isAtLineStart(
				startPosition, fileContents.getLine(startLineNumber - 1))) {

			int lineStartPos = _getLineStartPos(
				content, startPosition.getLineNumber());

			return StringUtil.insert(
				content, "\n", lineStartPos + startPosition.getLinePosition());
		}

		Position endPosition = parsedJavaTerm.getEndPosition();

		int endLineNumber = endPosition.getLineNumber();

		String endLine = fileContents.getLine(endLineNumber - 1);

		if (!_isAtLineEnd(endPosition, endLine)) {
			String remainder = StringUtil.trim(
				endLine.substring(endPosition.getLinePosition()));

			if (Validator.isNull(remainder)) {
				return _trimLine(content, endLine, endLineNumber);
			}

			return content;
		}

		String actualIndent = _getIndent(
			_getLine(content, startPosition.getLineNumber()));

		String javaTermContent = parsedJavaTerm.getContent();

		String expectedIndent = _getIndent(javaTermContent);

		if (!actualIndent.equals(expectedIndent)) {
			return _fixIndent(
				content, startPosition.getLineNumber(), actualIndent,
				expectedIndent);
		}

		StringBundler sb = new StringBundler();

		for (int i = startLineNumber; i <= endLineNumber; i++) {
			String line = fileContents.getLine(i - 1);

			String trimmedLine = StringUtil.trimLeading(line);

			if (trimmedLine.startsWith("//") || line.contains("\t//")) {
				return content;
			}

			sb.append(line);
			sb.append("\n");
		}

		String astDetailContent = sb.toString();

		if (!astDetailContent.contains(javaTermContent) &&
			!astDetailContent.contains("\n\n")) {

			int startPos = _getLineStartPos(content, startLineNumber);
			int endPos = _getLineStartPos(content, endLineNumber + 1) + 1;

			return StringUtil.replaceFirst(
				content, content.substring(startPos, endPos), javaTermContent,
				startPos - 1);
		}

		return content;
	}

	private static String _parseContent(
		String content, ParsedJavaClass parsedJavaClass,
		FileContents fileContents) {

		Map<Position, ParsedJavaTerm> parsedJavaTermsMap =
			parsedJavaClass.getParsedJavaTermsMap();

		for (Map.Entry<Position, ParsedJavaTerm> entry :
				parsedJavaTermsMap.entrySet()) {

			content = _parseContent(entry.getValue(), content, fileContents);
		}

		return content;
	}

	private static ParsedJavaClass _parseDetailAST(
		ParsedJavaClass parsedJavaClass, DetailAST detailAST,
		FileContents fileContents) {

		if (detailAST == null) {
			return parsedJavaClass;
		}

		if (detailAST.getType() == TokenTypes.VARIABLE_DEF) {
			DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

			if ((previousSiblingDetailAST != null) &&
				(previousSiblingDetailAST.getType() == TokenTypes.COMMA)) {

				return parsedJavaClass;
			}
		}

		JavaTerm javaTerm = JavaParserUtil.parseJavaTerm(detailAST);

		if (javaTerm != null) {
			parsedJavaClass = _addJavaTerm(
				parsedJavaClass, detailAST, javaTerm, fileContents);
		}

		if (detailAST.getType() == TokenTypes.LITERAL_DO) {
			DetailAST doWhileDetailAST = detailAST.findFirstToken(
				TokenTypes.DO_WHILE);

			parsedJavaClass = _parseDetailAST(
				parsedJavaClass, doWhileDetailAST, fileContents);
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_ELSE) {
			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.LITERAL_IF) {
				if (firstChildDetailAST.findFirstToken(TokenTypes.SLIST) ==
						null) {

					DetailAST rparentDetailAST =
						firstChildDetailAST.findFirstToken(TokenTypes.RPAREN);

					if (rparentDetailAST != null) {
						parsedJavaClass = _parseDetailAST(
							parsedJavaClass, rparentDetailAST.getNextSibling(),
							fileContents);
					}
				}
			}
			else if (firstChildDetailAST.getType() != TokenTypes.SLIST) {
				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, firstChildDetailAST, fileContents);
			}
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_IF) {
			DetailAST literalIfDetailAST = detailAST;

			while (true) {
				DetailAST literalElseDetailAST =
					literalIfDetailAST.findFirstToken(TokenTypes.LITERAL_ELSE);

				if (literalElseDetailAST == null) {
					break;
				}

				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, literalElseDetailAST, fileContents);

				literalIfDetailAST = literalElseDetailAST.findFirstToken(
					TokenTypes.LITERAL_IF);

				if (literalIfDetailAST == null) {
					break;
				}
			}
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_SWITCH) {
			List<DetailAST> caseGroupDetailASTList =
				DetailASTUtil.getAllChildTokens(
					detailAST, false, TokenTypes.CASE_GROUP);

			for (DetailAST caseGroupDetailAST : caseGroupDetailASTList) {
				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, caseGroupDetailAST, fileContents);
			}
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_TRY) {
			List<DetailAST> literalCatchDetailASTList =
				DetailASTUtil.getAllChildTokens(
					detailAST, false, TokenTypes.LITERAL_CATCH);

			for (DetailAST literalCatchDetailAST : literalCatchDetailASTList) {
				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, literalCatchDetailAST, fileContents);
			}

			DetailAST literalFinallyDetailAST = detailAST.findFirstToken(
				TokenTypes.LITERAL_FINALLY);

			if (literalFinallyDetailAST != null) {
				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, literalFinallyDetailAST, fileContents);
			}
		}

		if (((detailAST.getType() == TokenTypes.LITERAL_FOR) ||
			 (detailAST.getType() == TokenTypes.LITERAL_IF) ||
			 (detailAST.getType() == TokenTypes.LITERAL_WHILE)) &&
			(detailAST.findFirstToken(TokenTypes.SLIST) == null)) {

			DetailAST rparentDetailAST = detailAST.findFirstToken(
				TokenTypes.RPAREN);

			if (rparentDetailAST != null) {
				parsedJavaClass = _parseDetailAST(
					parsedJavaClass, rparentDetailAST.getNextSibling(),
					fileContents);
			}
		}

		return parsedJavaClass;
	}

	private static String _trimLine(
		String content, String line, int lineNumber) {

		int x = _getLineStartPos(content, lineNumber);

		return StringUtil.replaceFirst(
			content, line, StringUtil.trimTrailing(line), x);
	}

	private static ParsedJavaClass _walk(
		ParsedJavaClass parsedJavaClass, DetailAST detailAST,
		FileContents fileContents) {

		if (detailAST == null) {
			return parsedJavaClass;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (((detailAST.getType() == TokenTypes.ANNOTATION_DEF) ||
			 (detailAST.getType() == TokenTypes.CLASS_DEF) ||
			 (detailAST.getType() == TokenTypes.ENUM_DEF) ||
			 (detailAST.getType() == TokenTypes.INTERFACE_DEF)) &&
			((parentDetailAST == null) ||
			 (parentDetailAST.getType() != TokenTypes.OBJBLOCK))) {

			parsedJavaClass = _parseDetailAST(
				parsedJavaClass, detailAST, fileContents);
		}
		else if ((detailAST.getType() == TokenTypes.IMPORT) ||
				 (detailAST.getType() == TokenTypes.PACKAGE_DEF) ||
				 (detailAST.getType() == TokenTypes.STATIC_IMPORT)) {

			parsedJavaClass = _parseDetailAST(
				parsedJavaClass, detailAST, fileContents);
		}

		if ((parentDetailAST != null) &&
			((parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
			 (parentDetailAST.getType() == TokenTypes.SLIST))) {

			parsedJavaClass = _parseDetailAST(
				parsedJavaClass, detailAST, fileContents);
		}

		CommonHiddenStreamToken commonHiddenStreamToken =
			detailAST.getHiddenBefore();

		if (commonHiddenStreamToken != null) {
			parsedJavaClass.addPrecedingCommentToken(
				commonHiddenStreamToken,
				DetailASTUtil.getStartPosition(detailAST));
		}

		parsedJavaClass = _walk(
			parsedJavaClass, detailAST.getFirstChild(), fileContents);
		parsedJavaClass = _walk(
			parsedJavaClass, detailAST.getNextSibling(), fileContents);

		return parsedJavaClass;
	}

	private static int _maxLineLength;

}