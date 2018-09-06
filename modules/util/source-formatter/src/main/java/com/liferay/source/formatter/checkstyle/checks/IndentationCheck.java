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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class IndentationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ANNOTATION_ARRAY_INIT, TokenTypes.ARRAY_INIT,
			TokenTypes.AT, TokenTypes.BLOCK_COMMENT_BEGIN,
			TokenTypes.BLOCK_COMMENT_END, TokenTypes.CHAR_LITERAL,
			TokenTypes.CTOR_CALL, TokenTypes.DO_WHILE,
			TokenTypes.EXTENDS_CLAUSE, TokenTypes.FINAL,
			TokenTypes.GENERIC_START, TokenTypes.IDENT,
			TokenTypes.IMPLEMENTS_CLAUSE, TokenTypes.IMPORT, TokenTypes.INC,
			TokenTypes.INSTANCE_INIT, TokenTypes.LITERAL_BOOLEAN,
			TokenTypes.LITERAL_BREAK, TokenTypes.LITERAL_BYTE,
			TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_CATCH,
			TokenTypes.LITERAL_CHAR, TokenTypes.LITERAL_CLASS,
			TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_DEFAULT,
			TokenTypes.LITERAL_DO, TokenTypes.LITERAL_DOUBLE,
			TokenTypes.LITERAL_ELSE, TokenTypes.LITERAL_FALSE,
			TokenTypes.LITERAL_FINALLY, TokenTypes.LITERAL_FLOAT,
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF,
			TokenTypes.LITERAL_INT, TokenTypes.LITERAL_LONG,
			TokenTypes.LITERAL_NEW, TokenTypes.LITERAL_NULL,
			TokenTypes.LITERAL_PRIVATE, TokenTypes.LITERAL_PROTECTED,
			TokenTypes.LITERAL_PUBLIC, TokenTypes.LITERAL_RETURN,
			TokenTypes.LITERAL_SHORT, TokenTypes.LITERAL_STATIC,
			TokenTypes.LITERAL_SUPER, TokenTypes.LITERAL_SWITCH,
			TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LITERAL_THIS,
			TokenTypes.LITERAL_THROW, TokenTypes.LITERAL_THROWS,
			TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_TRY,
			TokenTypes.LITERAL_VOID, TokenTypes.LITERAL_WHILE, TokenTypes.LNOT,
			TokenTypes.LPAREN, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT,
			TokenTypes.NUM_INT, TokenTypes.NUM_LONG, TokenTypes.PACKAGE_DEF,
			TokenTypes.RCURLY, TokenTypes.RPAREN,
			TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.STATIC_IMPORT,
			TokenTypes.STATIC_INIT, TokenTypes.STRING_LITERAL,
			TokenTypes.SUPER_CTOR_CALL, TokenTypes.TYPECAST,
			TokenTypes.UNARY_MINUS, TokenTypes.WILDCARD_TYPE
		};
	}

	@Override
	public boolean isCommentNodesRequired() {
		return true;
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {

		// Only check types at the beginning of the line. We can skip if/while
		// statements since we have logic in JavaIfStatementCheck in place to
		// automatically fix incorrect indentations inside those. Indentations
		// for method parameter declarations are automatically fixed by
		// JavaSignatureStylingCheck.

		if (!_isAtLineStart(detailAST) ||
			_isCatchStatementParameter(detailAST) ||
			_isInsideChainedConcatMethod(detailAST) ||
			_isInsideDoIfOrWhileStatementCriterium(detailAST) ||
			_isInsideMethodParameterDeclaration(detailAST)) {

			return;
		}

		int expectedTabCount = _getExpectedTabCount(detailAST);
		int leadingTabCount = _getLeadingTabCount(detailAST);

		if (expectedTabCount != leadingTabCount) {
			if (_isInsideChain(detailAST)) {
				log(
					detailAST.getLineNo(),
					_MSG_INCORRECT_INDENTATION_INSIDE_CHAIN);
			}
			else {
				log(
					detailAST.getLineNo(), _MSG_INCORRECT_INDENTATION,
					leadingTabCount, expectedTabCount);
			}
		}
	}

	private int _addExtraTabForExtendsOrImplements(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST;

		DetailAST grandParentAST = parentAST.getParent();

		while (true) {
			if (grandParentAST == null) {
				return expectedTabCount;
			}

			if ((grandParentAST.getType() == TokenTypes.EXTENDS_CLAUSE) ||
				(grandParentAST.getType() == TokenTypes.IMPLEMENTS_CLAUSE)) {

				DetailAST previousSiblingAST = parentAST.getPreviousSibling();

				if ((previousSiblingAST == null) ||
					(previousSiblingAST.getType() != TokenTypes.COMMA)) {

					return expectedTabCount;
				}

				int lineNo = grandParentAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					return expectedTabCount + 1;
				}
			}

			parentAST = grandParentAST;
			grandParentAST = grandParentAST.getParent();
		}
	}

	private int _addExtraTabForForStatement(
		int expectedTabCount, DetailAST detailAST) {

		if (_findParent(detailAST, TokenTypes.LITERAL_FOR) != null) {
			return expectedTabCount + 1;
		}

		return expectedTabCount;
	}

	private int _addExtraTabForParameterWithThrows(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST;

		DetailAST grandParentAST = parentAST.getParent();

		while (true) {
			if (grandParentAST == null) {
				return expectedTabCount;
			}

			if ((grandParentAST.getType() == TokenTypes.CTOR_DEF) ||
				(grandParentAST.getType() == TokenTypes.METHOD_DEF)) {

				DetailAST literalThrowsAST = grandParentAST.findFirstToken(
					TokenTypes.LITERAL_THROWS);

				if (literalThrowsAST == null) {
					return expectedTabCount;
				}

				int literalThrowsLineNo = literalThrowsAST.getLineNo();
				int modifierLineNo = _getModifierLineNo(grandParentAST);

				if ((parentAST.getType() == TokenTypes.PARAMETERS) ||
					((detailAST.getLineNo() < literalThrowsLineNo) &&
					 (detailAST.getLineNo() > modifierLineNo))) {

					return expectedTabCount + 1;
				}

				return expectedTabCount;
			}

			parentAST = grandParentAST;
			grandParentAST = grandParentAST.getParent();
		}
	}

	private int _addExtraTabForSwitch(
		int expectedTabCount, DetailAST detailAST) {

		if ((detailAST.getType() == TokenTypes.LITERAL_CASE) ||
			(detailAST.getType() == TokenTypes.LITERAL_DEFAULT)) {

			return expectedTabCount;
		}

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.CASE_GROUP) {
				return expectedTabCount + 1;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _addExtraTabForTryStatement(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.RESOURCE) {
				DetailAST previousSiblingAST = parentAST.getPreviousSibling();

				if (previousSiblingAST != null) {
					return expectedTabCount;
				}
			}

			if (parentAST.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LITERAL_TRY) {
					return expectedTabCount + 1;
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _addExtraTabsForLambda(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.PARAMETERS) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LAMBDA) {
					DetailAST grandParentAST = parentAST.getParent();

					if (grandParentAST.getType() == TokenTypes.LITERAL_RETURN) {
						return expectedTabCount + 1;
					}
				}
			}

			if (parentAST.getType() == TokenTypes.SLIST) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LAMBDA) {
					DetailAST firstChildAST = parentAST.getFirstChild();

					if (firstChildAST.getLineNo() == parentAST.getLineNo()) {
						expectedTabCount += _getLineBreakTabs(parentAST);
					}
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _addExtraTabsForLiteralNew(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.OBJBLOCK) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
					expectedTabCount += _getLineBreakTabs(parentAST);
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private Set<Integer> _addTabsForArithmeticOperators(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		DetailAST firstChildAST = detailAST;

		while (true) {
			int lineNo = firstChildAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			if (!ArrayUtil.contains(
					_ARITHMETIC_OPERATORS, firstChildAST.getType())) {

				break;
			}

			firstChildAST = firstChildAST.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForDot(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		DetailAST firstChildAST = detailAST;

		while (true) {
			if (firstChildAST.getType() != TokenTypes.DOT) {
				break;
			}

			int lineNo = firstChildAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			firstChildAST = firstChildAST.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForGenerics(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		List<DetailAST> genericStartASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.GENERIC_START);

		for (DetailAST genericStartAST : genericStartASTList) {
			if (!_isAtLineStart(genericStartAST)) {
				continue;
			}

			DetailAST parentAST = genericStartAST.getParent();

			DetailAST genericEndAST = parentAST.findFirstToken(
				TokenTypes.GENERIC_END);

			if ((genericEndAST.getLineNo() < lineNumber) &&
				(genericStartAST.getLineNo() < lineNumber)) {

				DetailAST grandParentAST = parentAST.getParent();

				DetailAST nextSiblingAST = grandParentAST.getNextSibling();

				if ((nextSiblingAST != null) &&
					(nextSiblingAST.getType() == TokenTypes.COMMA)) {

					continue;
				}
			}

			int lineNo = genericStartAST.getLineNo() - 1;

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			DetailAST commaAST = parentAST.findFirstToken(TokenTypes.COMMA);

			if (commaAST != null) {
				if (genericEndAST.getLineNo() != genericStartAST.getLineNo()) {
					continue;
				}
			}

			DetailAST exprAST = DetailASTUtil.getParentWithTokenType(
				genericStartAST, TokenTypes.EXPR);

			if ((exprAST != null) &&
				!lineNumbers.contains(DetailASTUtil.getStartLine(exprAST))) {

				continue;
			}

			lineNo = genericStartAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}
		}

		List<DetailAST> genericEndtASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.GENERIC_END);

		for (DetailAST genericEndAST : genericEndtASTList) {
			String line = getLine(genericEndAST.getLineNo() - 1);

			if (!DetailASTUtil.isAtLineEnd(genericEndAST, line)) {
				continue;
			}

			DetailAST exprAST = DetailASTUtil.getParentWithTokenType(
				genericEndAST, TokenTypes.EXPR);

			if ((exprAST != null) &&
				!lineNumbers.contains(DetailASTUtil.getStartLine(exprAST))) {

				continue;
			}

			int lineNo = genericEndAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForTypecast(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		DetailAST previousSiblingAST = detailAST.getPreviousSibling();

		if ((previousSiblingAST == null) ||
			(previousSiblingAST.getType() != TokenTypes.TYPECAST)) {

			return lineNumbers;
		}

		int lineNo = previousSiblingAST.getLineNo();

		if (lineNo < lineNumber) {
			lineNumbers.add(lineNo);
		}

		return lineNumbers;
	}

	private int _adjustTabCount(int tabCount, DetailAST detailAST) {
		tabCount = _adjustTabCountForChains(tabCount, detailAST);
		tabCount = _adjustTabCountForEndOfLineLogicalOperator(
			tabCount, detailAST);

		return tabCount;
	}

	private int _adjustTabCountForChains(int tabCount, DetailAST detailAST) {
		boolean checkChaining = false;
		int methodCallLineNumber = -1;

		DetailAST parentAST = detailAST;

		while (true) {
			if ((parentAST == null) ||
				(parentAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				return tabCount;
			}

			if (checkChaining) {
				String line = StringUtil.trim(
					getLine(parentAST.getLineNo() - 1));

				if (line.endsWith("(") &&
					(parentAST.getLineNo() < methodCallLineNumber)) {

					DetailAST rparenAST = null;

					DetailAST methodCallAST = _findDetailAST(
						parentAST, parentAST.getLineNo(),
						TokenTypes.METHOD_CALL);

					if (methodCallAST != null) {
						rparenAST = methodCallAST.findFirstToken(
							TokenTypes.RPAREN);
					}
					else {
						DetailAST lparenAST = _findDetailAST(
							parentAST, parentAST.getLineNo(),
							TokenTypes.LPAREN);

						DetailAST nextSiblingAST = lparenAST.getNextSibling();

						while (true) {
							if ((nextSiblingAST == null) ||
								(nextSiblingAST.getType() ==
									TokenTypes.RPAREN)) {

								rparenAST = nextSiblingAST;

								break;
							}

							nextSiblingAST = nextSiblingAST.getNextSibling();
						}
					}

					if ((rparenAST != null) &&
						(rparenAST.getLineNo() < detailAST.getLineNo())) {

						tabCount--;
					}

					checkChaining = false;
				}
			}

			if (parentAST.getType() == TokenTypes.METHOD_CALL) {
				String line = StringUtil.trim(
					getLine(parentAST.getLineNo() - 1));

				if (line.startsWith(").") &&
					(parentAST.getLineNo() < detailAST.getLineNo())) {

					checkChaining = true;
					methodCallLineNumber = parentAST.getLineNo();
				}
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _adjustTabCountForEndOfLineLogicalOperator(
		int tabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST;

		while (true) {
			if ((parentAST == null) ||
				(parentAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				return tabCount;
			}

			if (((parentAST.getType() == TokenTypes.BAND) ||
				 (parentAST.getType() == TokenTypes.BOR) ||
				 (parentAST.getType() == TokenTypes.BXOR) ||
				 (parentAST.getType() == TokenTypes.LAND) ||
				 (parentAST.getType() == TokenTypes.LOR)) &&
				(parentAST.getLineNo() < detailAST.getLineNo())) {

				String text = parentAST.getText();

				String line = getLine(parentAST.getLineNo() - 1);

				String trimmedLine = StringUtil.trim(line);

				if (!trimmedLine.startsWith("return ") &&
					(parentAST.getColumnNo() + text.length()) ==
						line.length()) {

					tabCount--;
				}
			}

			parentAST = parentAST.getParent();
		}
	}

	private DetailAST _findDetailAST(
		DetailAST parentAST, int lineNo, int type) {

		if (parentAST.getType() == type) {
			return parentAST;
		}

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			parentAST, true, type);

		for (DetailAST methodCallAST : methodCallASTList) {
			if (methodCallAST.getLineNo() == lineNo) {
				return methodCallAST;
			}
		}

		return null;
	}

	private DetailAST _findParent(DetailAST detailAST, int type) {
		DetailAST match = null;

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if ((parentAST == null) ||
				(parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				return match;
			}

			if (parentAST.getType() == type) {
				match = parentAST;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _getChainLevel(DetailAST detailAST) {
		int level = 1;

		while (true) {
			DetailAST firstChildAST = detailAST.getFirstChild();

			if ((detailAST.getType() == TokenTypes.METHOD_CALL) &&
				(firstChildAST.getType() == TokenTypes.DOT)) {

				detailAST = firstChildAST;

				continue;
			}

			if ((detailAST.getType() == TokenTypes.DOT) &&
				(firstChildAST.getType() == TokenTypes.METHOD_CALL)) {

				level++;

				detailAST = firstChildAST;

				continue;
			}

			return level;
		}
	}

	private int _getExpectedTabCount(DetailAST detailAST) {
		DetailAST previousSiblingAST = detailAST.getPreviousSibling();

		if ((previousSiblingAST != null) &&
			(previousSiblingAST.getType() == TokenTypes.COMMA)) {

			previousSiblingAST = previousSiblingAST.getPreviousSibling();

			if (_isAtLineStart(previousSiblingAST)) {
				return _getExpectedTabCount(previousSiblingAST);
			}
		}

		int expectedTabCount =
			_getLevel(detailAST) + _getLineBreakTabs(detailAST);

		expectedTabCount = _addExtraTabForExtendsOrImplements(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForForStatement(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabsForLambda(expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabsForLiteralNew(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForParameterWithThrows(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForSwitch(expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForTryStatement(
			expectedTabCount, detailAST);

		if ((detailAST.getType() == TokenTypes.BLOCK_COMMENT_END) ||
			(detailAST.getType() == TokenTypes.RCURLY) ||
			(detailAST.getType() == TokenTypes.RPAREN)) {

			return expectedTabCount - 1;
		}

		return expectedTabCount;
	}

	private int _getLeadingTabCount(DetailAST detailAST) {
		String line = getLine(DetailASTUtil.getStartLine(detailAST) - 1);

		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	private int _getLevel(DetailAST detailAST) {
		int level = 0;

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return level;
			}

			if ((parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				level++;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _getLineBreakTabs(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.DO_WHILE) ||
			(detailAST.getType() == TokenTypes.LITERAL_CATCH) ||
			(detailAST.getType() == TokenTypes.LITERAL_ELSE) ||
			(detailAST.getType() == TokenTypes.LITERAL_FINALLY)) {

			return 0;
		}

		Set<Integer> lineNumbers = new HashSet<>();

		DetailAST parentAST = detailAST;

		while (true) {
			if ((parentAST == null) ||
				(parentAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				return _adjustTabCount(lineNumbers.size(), detailAST);
			}

			if ((parentAST.getType() == TokenTypes.ANNOTATION_DEF) ||
				(parentAST.getType() == TokenTypes.ANNOTATION_FIELD_DEF) ||
				(parentAST.getType() == TokenTypes.CLASS_DEF) ||
				(parentAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
				(parentAST.getType() == TokenTypes.ENUM_DEF) ||
				(parentAST.getType() == TokenTypes.INTERFACE_DEF) ||
				(parentAST.getType() == TokenTypes.METHOD_DEF) ||
				(parentAST.getType() == TokenTypes.MODIFIERS) ||
				(parentAST.getType() == TokenTypes.VARIABLE_DEF)) {

				DetailAST typeAST = parentAST.findFirstToken(TokenTypes.TYPE);

				int lineNo = _getModifierLineNo(parentAST);

				if ((lineNo == -1) && (typeAST != null)) {
					lineNo = typeAST.getLineNo();
				}

				if ((lineNo != -1) && (lineNo < detailAST.getLineNo())) {
					lineNumbers.add(lineNo);
				}

				if ((parentAST.getType() == TokenTypes.CLASS_DEF) ||
					(parentAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
					(parentAST.getType() == TokenTypes.ENUM_DEF) ||
					(parentAST.getType() == TokenTypes.INTERFACE_DEF)) {

					DetailAST nameAST = parentAST.findFirstToken(
						TokenTypes.IDENT);

					lineNo = nameAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeAST);

				DetailAST typeParametersAST = parentAST.findFirstToken(
					TokenTypes.TYPE_PARAMETERS);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeParametersAST);
			}
			else if ((parentAST.getType() == TokenTypes.ELIST) ||
					 (parentAST.getType() == TokenTypes.PARAMETERS)) {

				DetailAST lParenAST = parentAST.getPreviousSibling();

				if ((lParenAST != null) &&
					(lParenAST.getType() == TokenTypes.LPAREN) &&
					!_isAtLineStart(lParenAST)) {

					int lineNo = lParenAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}
			}
			else if ((parentAST.getType() == TokenTypes.TYPE_ARGUMENTS) ||
					 (parentAST.getType() == TokenTypes.TYPE_PARAMETERS)) {

				DetailAST commaAST = parentAST.findFirstToken(TokenTypes.COMMA);

				if ((commaAST == null) ||
					((commaAST.getLineNo() != parentAST.getLineNo()) &&
					 (commaAST.getLineNo() >= detailAST.getLineNo()))) {

					int lineNo = parentAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}
			}
			else if (parentAST.getType() != TokenTypes.CASE_GROUP) {
				int lineNo = parentAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					lineNumbers.add(lineNo);
				}
			}

			DetailAST dotAST = parentAST.findFirstToken(TokenTypes.DOT);

			lineNumbers = _addTabsForDot(
				lineNumbers, detailAST.getLineNo(), dotAST);

			if ((parentAST.getType() == TokenTypes.EXTENDS_CLAUSE) ||
				(parentAST.getType() == TokenTypes.IMPLEMENTS_CLAUSE)) {

				DetailAST nameAST = parentAST.findFirstToken(TokenTypes.IDENT);

				if (nameAST != null) {
					int lineNo = nameAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}
			}

			if (parentAST.getType() == TokenTypes.FOR_EACH_CLAUSE) {
				DetailAST colonAST = parentAST.findFirstToken(TokenTypes.COLON);

				if (colonAST != null) {
					int lineNo = colonAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentAST);
			}

			if (parentAST.getType() == TokenTypes.PARAMETER_DEF) {
				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentAST);
			}

			if (parentAST.getType() == TokenTypes.QUESTION) {
				DetailAST colonAST = parentAST.findFirstToken(TokenTypes.COLON);

				int lineNo = colonAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					lineNumbers.add(lineNo);
				}
			}

			if (ArrayUtil.contains(
					_ARITHMETIC_OPERATORS, parentAST.getType())) {

				lineNumbers = _addTabsForArithmeticOperators(
					lineNumbers, detailAST.getLineNo(), parentAST);
			}

			if (parentAST.getType() == TokenTypes.TYPE) {
				lineNumbers = _addTabsForTypecast(
					lineNumbers, detailAST.getLineNo(), parentAST);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentAST);
			}

			if (parentAST.getType() == TokenTypes.TYPECAST) {
				DetailAST rparenAST = parentAST.findFirstToken(
					TokenTypes.RPAREN);

				String line = getLine(rparenAST.getLineNo() - 1);

				if (DetailASTUtil.isAtLineEnd(rparenAST, line)) {
					int lineNo = rparenAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				DetailAST typeAST = parentAST.findFirstToken(TokenTypes.TYPE);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeAST);
			}

			if (parentAST.getType() == TokenTypes.ANNOTATION) {
				parentAST = parentAST.getParent();

				if ((parentAST.getType() == TokenTypes.MODIFIERS) &&
					(_findParent(parentAST, TokenTypes.PARAMETER_DEF) ==
						null)) {

					return _adjustTabCount(lineNumbers.size(), detailAST);
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private int _getModifierLineNo(DetailAST detailAST) {
		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST == null) {
			return -1;
		}

		DetailAST modifierAST = modifiersAST.findFirstToken(
			TokenTypes.LITERAL_PRIVATE);

		if (modifierAST == null) {
			modifierAST = modifiersAST.findFirstToken(
				TokenTypes.LITERAL_PROTECTED);
		}

		if (modifierAST == null) {
			modifierAST = modifiersAST.findFirstToken(
				TokenTypes.LITERAL_PUBLIC);
		}

		if (modifierAST != null) {
			return modifierAST.getLineNo();
		}

		return -1;
	}

	private boolean _isAtLineStart(DetailAST detailAST) {
		String line = getLine(DetailASTUtil.getStartLine(detailAST) - 1);

		for (int i = 0; i < detailAST.getColumnNo(); i++) {
			char c = line.charAt(i);

			if ((c != CharPool.SPACE) && (c != CharPool.TAB)) {
				return false;
			}
		}

		return true;
	}

	private boolean _isCatchStatementParameter(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() != TokenTypes.PARAMETER_DEF) {
				parentAST = parentAST.getParent();

				continue;
			}

			parentAST = parentAST.getParent();

			if (parentAST.getType() == TokenTypes.LITERAL_CATCH) {
				return true;
			}
		}
	}

	private boolean _isConcatMethod(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return false;
		}

		DetailAST firstChildAST = detailAST.getFirstChild();

		if ((firstChildAST == null) ||
			(firstChildAST.getType() != TokenTypes.DOT)) {

			return false;
		}

		DetailAST lastChildAST = firstChildAST.getLastChild();

		if ((lastChildAST == null) ||
			(lastChildAST.getType() != TokenTypes.IDENT)) {

			return false;
		}

		String name = lastChildAST.getText();

		if (name.equals("concat")) {
			return true;
		}

		return false;
	}

	private boolean _isInsideChain(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if ((parentAST == null) ||
				(parentAST.getType() == TokenTypes.SLIST)) {

				return false;
			}

			if (parentAST.getType() == TokenTypes.METHOD_CALL) {
				if (_getChainLevel(parentAST) > 2) {
					return true;
				}
			}

			parentAST = parentAST.getParent();
		}
	}

	private boolean _isInsideChainedConcatMethod(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (!_isConcatMethod(parentAST)) {
				parentAST = parentAST.getParent();

				continue;
			}

			DetailAST firstChildAST = parentAST.getFirstChild();

			firstChildAST = firstChildAST.getFirstChild();

			if (_isConcatMethod(firstChildAST)) {
				return true;
			}

			parentAST = parentAST.getParent();
		}
	}

	private boolean _isInsideDoIfOrWhileStatementCriterium(
		DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() != TokenTypes.EXPR) {
				parentAST = parentAST.getParent();

				continue;
			}

			parentAST = parentAST.getParent();

			if ((parentAST.getType() == TokenTypes.LITERAL_DO) ||
				(parentAST.getType() == TokenTypes.LITERAL_IF) ||
				(parentAST.getType() == TokenTypes.LITERAL_WHILE)) {

				return true;
			}
		}
	}

	private boolean _isInsideMethodParameterDeclaration(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() == TokenTypes.PARAMETER_DEF) {
				break;
			}

			parentAST = parentAST.getParent();
		}

		parentAST = parentAST.getParent();

		if (parentAST.getType() != TokenTypes.PARAMETERS) {
			return false;
		}

		parentAST = parentAST.getParent();

		if ((parentAST.getType() == TokenTypes.CTOR_DEF) ||
			(parentAST.getType() == TokenTypes.METHOD_DEF)) {

			return true;
		}

		return false;
	}

	private static final int[] _ARITHMETIC_OPERATORS = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

	private static final String _MSG_INCORRECT_INDENTATION =
		"indentation.incorrect";

	private static final String _MSG_INCORRECT_INDENTATION_INSIDE_CHAIN =
		"indentation.inside.chain.incorrect";

}