/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public enum JavaOperator {

	ADD_ASSIGNMENT_OPERATOR(TokenTypes.PLUS_ASSIGN, "+=", Category.ASSIGNMENT),
	ADDITION_OPERATOR(TokenTypes.PLUS, "+", Category.ARITHMETIC),
	BITWISE_AND_ASSIGNMENT_OPERATOR(
		TokenTypes.BAND_ASSIGN, "&=", Category.ASSIGNMENT),
	BITWISE_AND_OPERATOR(TokenTypes.BAND, "&", Category.CONDITIONAL),
	BITWISE_EXCLUSIVE_OR_ASSIGNMENT_OPERATOR(
		TokenTypes.BXOR_ASSIGN, "^=", Category.ASSIGNMENT),
	BITWISE_EXCLUSIVE_OR_OPERATOR(TokenTypes.BXOR, "^", Category.CONDITIONAL),
	BITWISE_INCLUSIVE_OR_ASSIGNMENT_OPERATOR(
		TokenTypes.BOR_ASSIGN, "|=", Category.ASSIGNMENT),
	BITWISE_INCLUSIVE_OR_OPERATOR(TokenTypes.BOR, "|", Category.CONDITIONAL),
	CONDITIONAL_AND_OPERATOR(TokenTypes.LAND, "&&", Category.CONDITIONAL),
	CONDITIONAL_OR_OPERATOR(TokenTypes.LOR, "||", Category.CONDITIONAL),
	DECREMENT_OPERATOR(TokenTypes.DEC, "--", Category.UNARY, false, true),
	DECREMENT_POST_OPERATOR(
		TokenTypes.POST_DEC, "--", Category.UNARY, true, false),
	DIVIDE_ASSIGNMENT_OPERATOR(
		TokenTypes.DIV_ASSIGN, "/=", Category.ASSIGNMENT),
	DIVISION_OPERATOR(TokenTypes.DIV, "/", Category.ARITHMETIC),
	EQUAL_OPERATOR(TokenTypes.EQUAL, "==", Category.RELATIONAL),
	GREATER_THAN_EQUAL_OPERATOR(TokenTypes.GE, ">=", Category.RELATIONAL),
	GREATER_THAN_OPERATOR(TokenTypes.GT, ">", Category.RELATIONAL),
	INCREMENT_OPERATOR(TokenTypes.INC, "++", Category.UNARY, false, true),
	INCREMENT_POST_OPERATOR(
		TokenTypes.POST_INC, "++", Category.UNARY, true, false),
	LEFT_SHIFT_ASSIGNMENT_OPERATOR(
		TokenTypes.SL_ASSIGN, "<<=", Category.ASSIGNMENT),
	LEFT_SHIFT_OPERATOR(TokenTypes.SL, "<<", Category.BITWISE),
	LESS_THAN_EQUAL_OPERATOR(TokenTypes.LE, "<=", Category.RELATIONAL),
	LESS_THAN_OPERATOR(TokenTypes.LT, "<", Category.RELATIONAL),
	LOGICAL_COMPLEMENT_OPERATOR(
		TokenTypes.LNOT, "!", Category.UNARY, false, true),
	MODULUS_ASSIGNMENT_OPERATOR(
		TokenTypes.MOD_ASSIGN, "%=", Category.ASSIGNMENT),
	MODULUS_OPERATOR(TokenTypes.MOD, "%", Category.ARITHMETIC),
	MULTIPLICATION_OPERATOR(TokenTypes.STAR, "*", Category.ARITHMETIC),
	MULTIPLY_ASSIGNMENT_OPERATOR(
		TokenTypes.STAR_ASSIGN, "*=", Category.ASSIGNMENT),
	NOT_EQUAL_OPERATOR(TokenTypes.NOT_EQUAL, "!=", Category.RELATIONAL),
	RIGHT_SHIFT_ASSIGNMENT_OPERATOR(
		TokenTypes.SR_ASSIGN, ">>=", Category.ASSIGNMENT),
	RIGHT_SHIFT_OPERATOR(TokenTypes.SR, ">>", Category.BITWISE),
	SIMPLE_ASSIGNMENT_OPERATOR(TokenTypes.ASSIGN, "=", Category.ASSIGNMENT),
	SUBTRACT_ASSIGNMENT_OPERATOR(
		TokenTypes.MINUS_ASSIGN, "-=", Category.ASSIGNMENT),
	SUBTRACTION_OPERATOR(TokenTypes.MINUS, "-", Category.ARITHMETIC),
	UNARY_BITWISE_OPERATOR(TokenTypes.BNOT, "~", Category.BITWISE, false, true),
	UNARY_MINUS(TokenTypes.UNARY_MINUS, "-", Category.UNARY, false, true),
	UNARY_PLUS(TokenTypes.UNARY_PLUS, "+", Category.UNARY, false, true),
	UNSIGNED_RIGHT_SHIFT_ASSIGNMENT_OPERATOR(
		TokenTypes.BSR_ASSIGN, ">>>=", Category.BITWISE),
	UNSIGNED_RIGHT_SHIFT_OPERATOR(TokenTypes.BSR, ">>>", Category.BITWISE);

	public Category getCategory() {
		return _category;
	}

	public int getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	public boolean hasLeftHandExpression() {
		return _hasLeftHandExpression;
	}

	public boolean hasRightHandExpression() {
		return _hasRightHandExpression;
	}

	protected enum Category {

		ARITHMETIC, ASSIGNMENT, BITWISE, CONDITIONAL, RELATIONAL, UNARY

	}

	private JavaOperator(int type, String value, Category category) {
		this(type, value, category, true, true);
	}

	private JavaOperator(
		int type, String value, Category category,
		boolean hasLeftHandExpression, boolean hasRightHandExpression) {

		_type = type;
		_value = value;
		_category = category;
		_hasLeftHandExpression = hasLeftHandExpression;
		_hasRightHandExpression = hasRightHandExpression;
	}

	private Category _category;
	private boolean _hasLeftHandExpression;
	private boolean _hasRightHandExpression;
	private int _type;
	private String _value;

}