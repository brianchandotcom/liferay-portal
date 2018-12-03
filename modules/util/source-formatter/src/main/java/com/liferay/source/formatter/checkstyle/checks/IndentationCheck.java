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

		if (!DetailASTUtil.isAtLineStart(
				detailAST,
				getLine(DetailASTUtil.getStartLineNumber(detailAST) - 1)) ||
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
				log(detailAST, _MSG_INCORRECT_INDENTATION_INSIDE_CHAIN);
			}
			else {
				log(
					detailAST, _MSG_INCORRECT_INDENTATION, leadingTabCount,
					expectedTabCount);
			}
		}
	}

	private int _addExtraTabForExtendsOrImplements(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST;

		DetailAST grandParentDetailAST = parentDetailAST.getParent();

		while (true) {
			if (grandParentDetailAST == null) {
				return expectedTabCount;
			}

			if ((grandParentDetailAST.getType() == TokenTypes.EXTENDS_CLAUSE) ||
				(grandParentDetailAST.getType() ==
					TokenTypes.IMPLEMENTS_CLAUSE)) {

				DetailAST previousSiblingDetailAST =
					parentDetailAST.getPreviousSibling();

				while (true) {
					if (previousSiblingDetailAST == null) {
						return expectedTabCount;
					}

					if (previousSiblingDetailAST.getType() ==
							TokenTypes.COMMA) {

						int lineNo = grandParentDetailAST.getLineNo();

						if (lineNo < detailAST.getLineNo()) {
							return expectedTabCount + 1;
						}
					}

					previousSiblingDetailAST =
						previousSiblingDetailAST.getPreviousSibling();
				}
			}

			parentDetailAST = grandParentDetailAST;
			grandParentDetailAST = grandParentDetailAST.getParent();
		}
	}

	private int _addExtraTabForForStatement(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = _findParent(
			detailAST, TokenTypes.LITERAL_FOR);

		if ((parentDetailAST != null) &&
			parentDetailAST.branchContains(TokenTypes.FOR_EACH_CLAUSE)) {

			return expectedTabCount + 1;
		}

		return expectedTabCount;
	}

	private int _addExtraTabForParameterWithThrows(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST;

		DetailAST grandParentDetailAST = parentDetailAST.getParent();

		while (true) {
			if (grandParentDetailAST == null) {
				return expectedTabCount;
			}

			if ((grandParentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				(grandParentDetailAST.getType() == TokenTypes.METHOD_DEF)) {

				DetailAST literalThrowsDetailAST =
					grandParentDetailAST.findFirstToken(
						TokenTypes.LITERAL_THROWS);

				if (literalThrowsDetailAST == null) {
					return expectedTabCount;
				}

				int literalThrowsLineNo = literalThrowsDetailAST.getLineNo();
				int modifierLineNo = _getModifierLineNo(grandParentDetailAST);

				if ((parentDetailAST.getType() == TokenTypes.PARAMETERS) ||
					((detailAST.getLineNo() < literalThrowsLineNo) &&
					 (detailAST.getLineNo() > modifierLineNo))) {

					return expectedTabCount + 1;
				}

				return expectedTabCount;
			}

			parentDetailAST = grandParentDetailAST;
			grandParentDetailAST = grandParentDetailAST.getParent();
		}
	}

	private int _addExtraTabForSwitch(
		int expectedTabCount, DetailAST detailAST) {

		if ((detailAST.getType() == TokenTypes.LITERAL_CASE) ||
			(detailAST.getType() == TokenTypes.LITERAL_DEFAULT)) {

			return expectedTabCount;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return expectedTabCount;
			}

			if (parentDetailAST.getType() == TokenTypes.CASE_GROUP) {
				return expectedTabCount + 1;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _addExtraTabForTryStatement(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return expectedTabCount;
			}

			if (parentDetailAST.getType() == TokenTypes.RESOURCE) {
				DetailAST previousSiblingDetailAST =
					parentDetailAST.getPreviousSibling();

				if (previousSiblingDetailAST != null) {
					return expectedTabCount;
				}
			}

			if (parentDetailAST.getType() ==
					TokenTypes.RESOURCE_SPECIFICATION) {

				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LITERAL_TRY) {
					return expectedTabCount + 1;
				}

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _addExtraTabsForLambda(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return expectedTabCount;
			}

			if (parentDetailAST.getType() == TokenTypes.PARAMETERS) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LAMBDA) {
					DetailAST grandParentDetailAST =
						parentDetailAST.getParent();

					if (grandParentDetailAST.getType() ==
							TokenTypes.LITERAL_RETURN) {

						return expectedTabCount + 1;
					}
				}
			}

			if (parentDetailAST.getType() == TokenTypes.SLIST) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LAMBDA) {
					DetailAST firstChildDetailAST =
						parentDetailAST.getFirstChild();

					if (firstChildDetailAST.getLineNo() ==
							parentDetailAST.getLineNo()) {

						expectedTabCount += _getLineBreakTabs(parentDetailAST);
					}
				}

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _addExtraTabsForLiteralNew(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return expectedTabCount;
			}

			if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
				parentDetailAST = parentDetailAST.getParent();

				if (parentDetailAST.getType() == TokenTypes.LITERAL_NEW) {
					expectedTabCount += _getLineBreakTabs(parentDetailAST);
				}

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private Set<Integer> _addTabsForArithmeticOperators(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		DetailAST firstChildDetailAST = detailAST;

		while (true) {
			int lineNo = firstChildDetailAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			if (!ArrayUtil.contains(
					_ARITHMETIC_OPERATORS, firstChildDetailAST.getType())) {

				break;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForDot(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		DetailAST firstChildDetailAST = detailAST;

		while (true) {
			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				break;
			}

			int lineNo = firstChildDetailAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForGenerics(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		List<DetailAST> genericStartDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.GENERIC_START);

		for (DetailAST genericStartDetailAST : genericStartDetailASTList) {
			String line = getLine(
				DetailASTUtil.getStartLineNumber(genericStartDetailAST) - 1);

			if (!DetailASTUtil.isAtLineStart(genericStartDetailAST, line)) {
				continue;
			}

			DetailAST parentDetailAST = genericStartDetailAST.getParent();

			DetailAST genericEndDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.GENERIC_END);

			if ((genericEndDetailAST.getLineNo() < lineNumber) &&
				(genericStartDetailAST.getLineNo() < lineNumber)) {

				DetailAST grandParentDetailAST = parentDetailAST.getParent();

				DetailAST nextSiblingDetailAST =
					grandParentDetailAST.getNextSibling();

				if ((nextSiblingDetailAST != null) &&
					(nextSiblingDetailAST.getType() == TokenTypes.COMMA)) {

					continue;
				}
			}

			int lineNo = genericStartDetailAST.getLineNo() - 1;

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}

			DetailAST commaDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.COMMA);

			if (commaDetailAST != null) {
				if (genericEndDetailAST.getLineNo() !=
						genericStartDetailAST.getLineNo()) {

					continue;
				}
			}

			DetailAST exprDetailAST = DetailASTUtil.getParentWithTokenType(
				genericStartDetailAST, TokenTypes.EXPR);

			if ((exprDetailAST != null) &&
				!lineNumbers.contains(
					DetailASTUtil.getStartLineNumber(exprDetailAST))) {

				continue;
			}

			lineNo = genericStartDetailAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}
		}

		List<DetailAST> genericEndtASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.GENERIC_END);

		for (DetailAST genericEndDetailAST : genericEndtASTList) {
			String line = getLine(genericEndDetailAST.getLineNo() - 1);

			if (!DetailASTUtil.isAtLineEnd(genericEndDetailAST, line)) {
				continue;
			}

			DetailAST exprDetailAST = DetailASTUtil.getParentWithTokenType(
				genericEndDetailAST, TokenTypes.EXPR);

			if ((exprDetailAST != null) &&
				!lineNumbers.contains(
					DetailASTUtil.getStartLineNumber(exprDetailAST))) {

				continue;
			}

			int lineNo = genericEndDetailAST.getLineNo();

			if (lineNo < lineNumber) {
				lineNumbers.add(lineNo);
			}
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForTypecast(
		Set<Integer> lineNumbers, int lineNumber, DetailAST detailAST) {

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.TYPECAST)) {

			return lineNumbers;
		}

		int lineNo = previousSiblingDetailAST.getLineNo();

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

		DetailAST parentDetailAST = detailAST;

		while (true) {
			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				return tabCount;
			}

			if (checkChaining) {
				String line = StringUtil.trim(
					getLine(parentDetailAST.getLineNo() - 1));

				if (line.endsWith("(") &&
					(parentDetailAST.getLineNo() < methodCallLineNumber)) {

					DetailAST rparenDetailAST = null;

					DetailAST methodCallDetailAST = _findDetailAST(
						parentDetailAST, parentDetailAST.getLineNo(),
						TokenTypes.METHOD_CALL);

					if (methodCallDetailAST != null) {
						rparenDetailAST = methodCallDetailAST.findFirstToken(
							TokenTypes.RPAREN);
					}
					else {
						DetailAST lparenDetailAST = _findDetailAST(
							parentDetailAST, parentDetailAST.getLineNo(),
							TokenTypes.LPAREN);

						DetailAST nextSiblingDetailAST =
							lparenDetailAST.getNextSibling();

						while (true) {
							if ((nextSiblingDetailAST == null) ||
								(nextSiblingDetailAST.getType() ==
									TokenTypes.RPAREN)) {

								rparenDetailAST = nextSiblingDetailAST;

								break;
							}

							nextSiblingDetailAST =
								nextSiblingDetailAST.getNextSibling();
						}
					}

					if ((rparenDetailAST != null) &&
						(rparenDetailAST.getLineNo() < detailAST.getLineNo())) {

						tabCount--;
					}

					checkChaining = false;
				}
			}

			if (parentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				String line = StringUtil.trim(
					getLine(parentDetailAST.getLineNo() - 1));

				if (line.startsWith(").") &&
					(parentDetailAST.getLineNo() < detailAST.getLineNo())) {

					checkChaining = true;
					methodCallLineNumber = parentDetailAST.getLineNo();
				}
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _adjustTabCountForEndOfLineLogicalOperator(
		int tabCount, DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST;

		while (true) {
			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				return tabCount;
			}

			if (((parentDetailAST.getType() == TokenTypes.BAND) ||
				 (parentDetailAST.getType() == TokenTypes.BOR) ||
				 (parentDetailAST.getType() == TokenTypes.BXOR) ||
				 (parentDetailAST.getType() == TokenTypes.LAND) ||
				 (parentDetailAST.getType() == TokenTypes.LOR)) &&
				(parentDetailAST.getLineNo() < detailAST.getLineNo())) {

				String text = parentDetailAST.getText();

				String line = getLine(parentDetailAST.getLineNo() - 1);

				String trimmedLine = StringUtil.trim(line);

				if (!trimmedLine.startsWith("return ") &&
					(parentDetailAST.getColumnNo() + text.length()) ==
						line.length()) {

					tabCount--;
				}
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private DetailAST _findDetailAST(
		DetailAST parentDetailAST, int lineNo, int type) {

		if (parentDetailAST.getType() == type) {
			return parentDetailAST;
		}

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(parentDetailAST, true, type);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			if (methodCallDetailAST.getLineNo() == lineNo) {
				return methodCallDetailAST;
			}
		}

		return null;
	}

	private DetailAST _findParent(DetailAST detailAST, int type) {
		DetailAST matchDetailAST = null;

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				return matchDetailAST;
			}

			if (parentDetailAST.getType() == type) {
				matchDetailAST = parentDetailAST;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _getChainLevel(DetailAST detailAST) {
		int level = 1;

		while (true) {
			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			if ((detailAST.getType() == TokenTypes.METHOD_CALL) &&
				(firstChildDetailAST.getType() == TokenTypes.DOT)) {

				detailAST = firstChildDetailAST;

				continue;
			}

			if ((detailAST.getType() == TokenTypes.DOT) &&
				(firstChildDetailAST.getType() == TokenTypes.METHOD_CALL)) {

				level++;

				detailAST = firstChildDetailAST;

				continue;
			}

			return level;
		}
	}

	private int _getExpectedTabCount(DetailAST detailAST) {
		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if ((previousSiblingDetailAST != null) &&
			(previousSiblingDetailAST.getType() == TokenTypes.COMMA)) {

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();

			String previousLine = getLine(
				DetailASTUtil.getStartLineNumber(previousSiblingDetailAST) - 1);

			if (DetailASTUtil.isAtLineStart(
					previousSiblingDetailAST, previousLine)) {

				return _getExpectedTabCount(previousSiblingDetailAST);
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
		String line = getLine(DetailASTUtil.getStartLineNumber(detailAST) - 1);

		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	private int _getLevel(DetailAST detailAST) {
		int level = 0;

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return level;
			}

			if ((parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				level++;
			}

			parentDetailAST = parentDetailAST.getParent();
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

		DetailAST childDetailAST = null;
		DetailAST parentDetailAST = detailAST;

		while (true) {
			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() == TokenTypes.LABELED_STAT) ||
				(parentDetailAST.getType() == TokenTypes.OBJBLOCK) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				return _adjustTabCount(lineNumbers.size(), detailAST);
			}

			if ((parentDetailAST.getType() == TokenTypes.ANNOTATION_DEF) ||
				(parentDetailAST.getType() ==
					TokenTypes.ANNOTATION_FIELD_DEF) ||
				(parentDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				(parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentDetailAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
				(parentDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				(parentDetailAST.getType() == TokenTypes.INTERFACE_DEF) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_DEF) ||
				(parentDetailAST.getType() == TokenTypes.MODIFIERS) ||
				(parentDetailAST.getType() == TokenTypes.VARIABLE_DEF)) {

				DetailAST typeDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.TYPE);

				int lineNo = _getModifierLineNo(parentDetailAST);

				if ((lineNo == -1) && (typeDetailAST != null)) {
					lineNo = typeDetailAST.getLineNo();
				}

				if ((lineNo != -1) && (lineNo < detailAST.getLineNo())) {
					lineNumbers.add(lineNo);
				}

				if ((parentDetailAST.getType() == TokenTypes.CLASS_DEF) ||
					(parentDetailAST.getType() ==
						TokenTypes.ENUM_CONSTANT_DEF) ||
					(parentDetailAST.getType() == TokenTypes.ENUM_DEF) ||
					(parentDetailAST.getType() == TokenTypes.INTERFACE_DEF)) {

					DetailAST nameDetailAST = parentDetailAST.findFirstToken(
						TokenTypes.IDENT);

					lineNo = nameDetailAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeDetailAST);

				DetailAST typeParametersDetailAST =
					parentDetailAST.findFirstToken(TokenTypes.TYPE_PARAMETERS);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(),
					typeParametersDetailAST);
			}
			else if ((parentDetailAST.getType() == TokenTypes.ELIST) ||
					 (parentDetailAST.getType() == TokenTypes.PARAMETERS)) {

				DetailAST lParenDetailAST =
					parentDetailAST.getPreviousSibling();

				if ((lParenDetailAST != null) &&
					(lParenDetailAST.getType() == TokenTypes.LPAREN)) {

					String line = getLine(
						DetailASTUtil.getStartLineNumber(lParenDetailAST) - 1);

					if (!DetailASTUtil.isAtLineStart(lParenDetailAST, line)) {
						int lineNo = lParenDetailAST.getLineNo();

						if (lineNo < detailAST.getLineNo()) {
							lineNumbers.add(lineNo);
						}
					}
				}
			}
			else if ((parentDetailAST.getType() == TokenTypes.TYPE_ARGUMENTS) ||
					 (parentDetailAST.getType() ==
						 TokenTypes.TYPE_PARAMETERS)) {

				DetailAST commaDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.COMMA);

				if ((commaDetailAST == null) ||
					((commaDetailAST.getLineNo() !=
						parentDetailAST.getLineNo()) &&
					 (commaDetailAST.getLineNo() >= detailAST.getLineNo()))) {

					int lineNo = parentDetailAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}
			}
			else if (parentDetailAST.getType() != TokenTypes.CASE_GROUP) {
				int lineNo = parentDetailAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					lineNumbers.add(lineNo);
				}
			}

			DetailAST dotDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.DOT);

			lineNumbers = _addTabsForDot(
				lineNumbers, detailAST.getLineNo(), dotDetailAST);

			if ((parentDetailAST.getType() == TokenTypes.EXTENDS_CLAUSE) ||
				(parentDetailAST.getType() == TokenTypes.IMPLEMENTS_CLAUSE)) {

				DetailAST previousSiblingDetailAST = childDetailAST;

				while (true) {
					if (previousSiblingDetailAST == null) {
						break;
					}

					if (previousSiblingDetailAST.getType() ==
							TokenTypes.IDENT) {

						int lineNo = previousSiblingDetailAST.getLineNo();

						if (lineNo < detailAST.getLineNo()) {
							lineNumbers.add(lineNo);
						}

						break;
					}

					previousSiblingDetailAST =
						previousSiblingDetailAST.getPreviousSibling();
				}
			}

			if (parentDetailAST.getType() == TokenTypes.FOR_EACH_CLAUSE) {
				DetailAST colonDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.COLON);

				if (colonDetailAST != null) {
					int lineNo = colonDetailAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentDetailAST);
			}

			if (parentDetailAST.getType() == TokenTypes.PARAMETER_DEF) {
				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentDetailAST);
			}

			if (parentDetailAST.getType() == TokenTypes.QUESTION) {
				DetailAST colonDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.COLON);

				int lineNo = colonDetailAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					lineNumbers.add(lineNo);
				}
			}

			if (ArrayUtil.contains(
					_ARITHMETIC_OPERATORS, parentDetailAST.getType())) {

				lineNumbers = _addTabsForArithmeticOperators(
					lineNumbers, detailAST.getLineNo(), parentDetailAST);
			}

			if (parentDetailAST.getType() == TokenTypes.TYPE) {
				lineNumbers = _addTabsForTypecast(
					lineNumbers, detailAST.getLineNo(), parentDetailAST);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), parentDetailAST);
			}

			if (parentDetailAST.getType() == TokenTypes.TYPECAST) {
				DetailAST rparenDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.RPAREN);

				String line = getLine(rparenDetailAST.getLineNo() - 1);

				if (DetailASTUtil.isAtLineEnd(rparenDetailAST, line)) {
					int lineNo = rparenDetailAST.getLineNo();

					if (lineNo < detailAST.getLineNo()) {
						lineNumbers.add(lineNo);
					}
				}

				DetailAST typeDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.TYPE);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeDetailAST);
			}

			if (parentDetailAST.getType() == TokenTypes.ANNOTATION) {
				parentDetailAST = parentDetailAST.getParent();

				if ((parentDetailAST.getType() == TokenTypes.MODIFIERS) &&
					(_findParent(parentDetailAST, TokenTypes.PARAMETER_DEF) ==
						null)) {

					return _adjustTabCount(lineNumbers.size(), detailAST);
				}

				continue;
			}

			childDetailAST = parentDetailAST;

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private int _getModifierLineNo(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST == null) {
			return -1;
		}

		DetailAST modifierDetailAST = modifiersDetailAST.findFirstToken(
			TokenTypes.LITERAL_PRIVATE);

		if (modifierDetailAST == null) {
			modifierDetailAST = modifiersDetailAST.findFirstToken(
				TokenTypes.LITERAL_PROTECTED);
		}

		if (modifierDetailAST == null) {
			modifierDetailAST = modifiersDetailAST.findFirstToken(
				TokenTypes.LITERAL_PUBLIC);
		}

		if (modifierDetailAST != null) {
			return modifierDetailAST.getLineNo();
		}

		return -1;
	}

	private boolean _isCatchStatementParameter(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.getType() != TokenTypes.PARAMETER_DEF) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.LITERAL_CATCH) {
				return true;
			}
		}
	}

	private boolean _isConcatMethod(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return false;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.DOT)) {

			return false;
		}

		DetailAST lastChildDetailAST = firstChildDetailAST.getLastChild();

		if ((lastChildDetailAST == null) ||
			(lastChildDetailAST.getType() != TokenTypes.IDENT)) {

			return false;
		}

		String name = lastChildDetailAST.getText();

		if (name.equals("concat")) {
			return true;
		}

		return false;
	}

	private boolean _isInsideChain(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() == TokenTypes.SLIST)) {

				return false;
			}

			if (parentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				if (_getChainLevel(parentDetailAST) > 2) {
					return true;
				}
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private boolean _isInsideChainedConcatMethod(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (!_isConcatMethod(parentDetailAST)) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			DetailAST firstChildDetailAST = parentDetailAST.getFirstChild();

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (_isConcatMethod(firstChildDetailAST)) {
				return true;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private boolean _isInsideDoIfOrWhileStatementCriterium(
		DetailAST detailAST) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.LITERAL_DO) ||
				(parentDetailAST.getType() == TokenTypes.LITERAL_IF) ||
				(parentDetailAST.getType() == TokenTypes.LITERAL_WHILE)) {

				return true;
			}
		}
	}

	private boolean _isInsideMethodParameterDeclaration(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.getType() == TokenTypes.PARAMETER_DEF) {
				break;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.PARAMETERS) {
			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
			(parentDetailAST.getType() == TokenTypes.METHOD_DEF)) {

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