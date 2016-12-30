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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class IndentationCheck extends AbstractCheck {

	public static final String MSG_INCORRECT_INDENTATION =
		"indentation.incorrect";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.IDENT, TokenTypes.LITERAL_BOOLEAN,
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
			TokenTypes.LITERAL_THIS, TokenTypes.LITERAL_THROW,
			TokenTypes.LITERAL_THROWS, TokenTypes.LITERAL_TRUE,
			TokenTypes.LITERAL_TRY, TokenTypes.LITERAL_VOID,
			TokenTypes.LITERAL_WHILE
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!_isAtLineStart(detailAST) ||
			_isInsideChainedConcatMethod(detailAST) ||
			_isInsideDoIfTryOrWhileStatementCriterium(detailAST) ||
			_isInsideForStatementCriterium(detailAST) ||
			_isInsideOperatorCriterium(detailAST)) {

			return;
		}

		int expectedTabCount = _getExpectedTabCount(detailAST);
		int leadingTabCount = _getLeadingTabCount(detailAST);

		if (expectedTabCount != leadingTabCount) {
			log(
				detailAST.getLineNo(), MSG_INCORRECT_INDENTATION,
				leadingTabCount, expectedTabCount);
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

				DetailAST previousSibling = parentAST.getPreviousSibling();

				if ((previousSibling == null) ||
					(previousSibling.getType() != TokenTypes.COMMA)) {

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

	private int _addExtraTabsForLambda(
		int expectedTabCount, DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return expectedTabCount;
			}

			if (parentAST.getType() == TokenTypes.SLIST) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LAMBDA) {
					expectedTabCount += _getLineBreakTabs(parentAST);
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
		Set<Integer> lineNumbers, int lineCount, DetailAST detailAST) {

		DetailAST firstChild = detailAST;

		while (true) {
			int lineNo = firstChild.getLineNo();

			if (lineNo < lineCount) {
				lineNumbers.add(lineNo);
			}

			if (!ArrayUtil.contains(
					_ARITHMETIC_OPERATORS, firstChild.getType())) {

				break;
			}

			firstChild = firstChild.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForDot(
		Set<Integer> lineNumbers, int lineCount, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		DetailAST firstChild = detailAST;

		while (true) {
			if (firstChild.getType() != TokenTypes.DOT) {
				break;
			}

			int lineNo = firstChild.getLineNo();

			if (lineNo < lineCount) {
				lineNumbers.add(lineNo);
			}

			firstChild = firstChild.getFirstChild();
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForGenerics(
		Set<Integer> lineNumbers, int lineCount, DetailAST detailAST) {

		if (detailAST == null) {
			return lineNumbers;
		}

		List<DetailAST> genericASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.GENERIC_END, TokenTypes.GENERIC_START);

		for (DetailAST genericAST : genericASTList) {
			int lineNo = genericAST.getLineNo();

			if (lineNo < lineCount) {
				lineNumbers.add(lineNo);
			}
		}

		return lineNumbers;
	}

	private Set<Integer> _addTabsForTypecast(
		Set<Integer> lineNumbers, int lineCount, DetailAST detailAST) {

		DetailAST previousSibling = detailAST.getPreviousSibling();

		if ((previousSibling == null) ||
			(previousSibling.getType() != TokenTypes.TYPECAST)) {

			return lineNumbers;
		}

		int lineNo = previousSibling.getLineNo();

		if (lineNo < lineCount) {
			lineNumbers.add(lineNo);
		}

		return lineNumbers;
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

	private int _getExpectedTabCount(DetailAST detailAST) {
		int expectedTabCount =
			_getLevel(detailAST) + _getLineBreakTabs(detailAST);

		expectedTabCount = _addExtraTabForExtendsOrImplements(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabsForLambda(expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabsForLiteralNew(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForParameterWithThrows(
			expectedTabCount, detailAST);
		expectedTabCount = _addExtraTabForSwitch(expectedTabCount, detailAST);

		return expectedTabCount;
	}

	private int _getLeadingTabCount(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String line = fileContents.getLine(
			DetailASTUtil.getStartLine(detailAST) - 1);

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
		if ((detailAST.getType() == TokenTypes.LITERAL_CATCH) ||
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

				return lineNumbers.size();
			}

			if ((parentAST.getType() == TokenTypes.ANNOTATION_DEF) ||
				(parentAST.getType() == TokenTypes.ANNOTATION_FIELD_DEF) ||
				(parentAST.getType() == TokenTypes.CLASS_DEF) ||
				(parentAST.getType() == TokenTypes.CTOR_DEF) ||
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

				int lineNo = lParenAST.getLineNo();

				if (lineNo < detailAST.getLineNo()) {
					lineNumbers.add(lineNo);
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
				DetailAST typeAST = parentAST.findFirstToken(TokenTypes.TYPE);

				lineNumbers = _addTabsForGenerics(
					lineNumbers, detailAST.getLineNo(), typeAST);
			}

			if (parentAST.getType() == TokenTypes.ANNOTATION) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.MODIFIERS) {
					return lineNumbers.size();
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
		FileContents fileContents = getFileContents();

		String line = fileContents.getLine(
			DetailASTUtil.getStartLine(detailAST) - 1);

		for (int i = 0; i < detailAST.getColumnNo(); i++) {
			char c = line.charAt(i);

			if ((c != CharPool.SPACE) && (c != CharPool.TAB)) {
				return false;
			}
		}

		return true;
	}

	private boolean _isConcatMethod(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return false;
		}

		DetailAST firstChild = detailAST.getFirstChild();

		if ((firstChild == null) || (firstChild.getType() != TokenTypes.DOT)) {
			return false;
		}

		DetailAST lastChild = firstChild.getLastChild();

		if ((lastChild == null) || (lastChild.getType() != TokenTypes.IDENT)) {
			return false;
		}

		String name = lastChild.getText();

		if (name.equals("concat")) {
			return true;
		}

		return false;
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

			DetailAST firstChild = parentAST.getFirstChild();

			firstChild = firstChild.getFirstChild();

			if (_isConcatMethod(firstChild)) {
				return true;
			}

			parentAST = parentAST.getParent();
		}
	}

	private boolean _isInsideDoIfTryOrWhileStatementCriterium(
		DetailAST detailAST) {

		DetailAST parentAST = detailAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() == TokenTypes.EXPR) {
				parentAST = parentAST.getParent();

				if ((parentAST.getType() == TokenTypes.LITERAL_DO) ||
					(parentAST.getType() == TokenTypes.LITERAL_IF) ||
					(parentAST.getType() == TokenTypes.LITERAL_WHILE)) {

					return true;
				}

				continue;
			}

			if (parentAST.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
				parentAST = parentAST.getParent();

				if (parentAST.getType() == TokenTypes.LITERAL_TRY) {
					return true;
				}

				continue;
			}

			parentAST = parentAST.getParent();
		}
	}

	private boolean _isInsideForStatementCriterium(DetailAST detailAST) {
		if ((_findParent(detailAST, TokenTypes.FOR_CONDITION) != null) ||
			(_findParent(detailAST, TokenTypes.FOR_EACH_CLAUSE) != null) ||
			(_findParent(detailAST, TokenTypes.FOR_INIT) != null) ||
			(_findParent(detailAST, TokenTypes.FOR_ITERATOR) != null)) {

			return true;
		}

		return false;
	}

	private boolean _isInsideOperatorCriterium(DetailAST detailAST) {
		if ((_findParent(detailAST, TokenTypes.BAND) != null) ||
			(_findParent(detailAST, TokenTypes.BOR) != null) ||
			(_findParent(detailAST, TokenTypes.BXOR) != null) ||
			(_findParent(detailAST, TokenTypes.LAND) != null) ||
			(_findParent(detailAST, TokenTypes.LOR) != null)) {

			return true;
		}

		return false;
	}

	private static final int[] _ARITHMETIC_OPERATORS = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

}