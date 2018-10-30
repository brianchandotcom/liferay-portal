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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public enum JavaOperator {

	ADD_ASSIGNMENT_OPERATOR(TokenTypes.PLUS_ASSIGN, "+="),
	ADDITION_OPERATOR(TokenTypes.PLUS, "+"),
	BITWISE_AND_ASSIGNMENT_OPERATOR(TokenTypes.BAND_ASSIGN, "&="),
	BITWISE_AND_OPERATOR(TokenTypes.BAND, "&"),
	BITWISE_EXCLUSIVE_OR_ASSIGNMENT_OPERATOR(TokenTypes.BXOR_ASSIGN, "^="),
	BITWISE_EXCLUSIVE_OR_OPERATOR(TokenTypes.BXOR, "^"),
	BITWISE_INCLUSIVE_OR_ASSIGNMENT_OPERATOR(TokenTypes.BOR_ASSIGN, "|="),
	BITWISE_INCLUSIVE_OR_OPERATOR(TokenTypes.BOR, "|"),
	CONDITIONAL_AND_OPERATOR(TokenTypes.LAND, "&&"),
	CONDITIONAL_OR_OPERATOR(TokenTypes.LOR, "||"),
	DECREMENT_OPERATOR(TokenTypes.DEC, "--", false, true),
	DECREMENT_POST_OPERATOR(TokenTypes.POST_DEC, "--", true, false),
	DIVIDE_ASSIGNMENT_OPERATOR(TokenTypes.DIV_ASSIGN, "/="),
	DIVISION_OPERATOR(TokenTypes.DIV, "/"),
	EQUAL_OPERATOR(TokenTypes.EQUAL, "=="),
	GREATER_THAN_EQUAL_OPERATOR(TokenTypes.GE, ">="),
	GREATER_THAN_OPERATOR(TokenTypes.GT, ">"),
	INCREMENT_OPERATOR(TokenTypes.INC, "++", false, true),
	INCREMENT_POST_OPERATOR(TokenTypes.POST_INC, "++", true, false),
	LEFT_SHIFT_ASSIGNMENT_OPERATOR(TokenTypes.SL_ASSIGN, "<<="),
	LEFT_SHIFT_OPERATOR(TokenTypes.SL, "<<"),
	LESS_THAN_EQUAL_OPERATOR(TokenTypes.LE, "<="),
	LESS_THAN_OPERATOR(TokenTypes.LT, "<"),
	LOGICAL_COMPLEMENT_OPERATOR(TokenTypes.LNOT, "!", false, true),
	MODULUS_ASSIGNMENT_OPERATOR(TokenTypes.MOD_ASSIGN, "%="),
	MODULUS_OPERATOR(TokenTypes.MOD, "%"),
	MULTIPLICATION_OPERATOR(TokenTypes.STAR, "*"),
	MULTIPLY_ASSIGNMENT_OPERATOR(TokenTypes.STAR_ASSIGN, "*="),
	NOT_EQUAL_OPERATOR(TokenTypes.NOT_EQUAL, "!="),
	RIGHT_SHIFT_ASSIGNMENT_OPERATOR(TokenTypes.SR_ASSIGN, ">>="),
	RIGHT_SHIFT_OPERATOR(TokenTypes.SR, ">>"),
	SIMPLE_ASSIGNMENT_OPERATOR(TokenTypes.ASSIGN, "="),
	SUBTRACT_ASSIGNMENT_OPERATOR(TokenTypes.MINUS_ASSIGN, "-="),
	SUBTRACTION_OPERATOR(TokenTypes.MINUS, "-"),
	UNARY_BITWISE_OPERATOR(TokenTypes.BNOT, "~", false, true),
	UNARY_MINUS(TokenTypes.UNARY_MINUS, "-", false, true),
	UNARY_PLUS(TokenTypes.UNARY_PLUS, "+", false, true),
	UNSIGNED_RIGHT_SHIFT_OPERATOR(TokenTypes.BSR, ">>>");

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

	private JavaOperator(int type, String value) {
		this(type, value, true, true);
	}

	private JavaOperator(
		int type, String value, boolean hasLeftHandExpression,
		boolean hasRightHandExpression) {

		_type = type;
		_value = value;
		_hasLeftHandExpression = hasLeftHandExpression;
		_hasRightHandExpression = hasRightHandExpression;
	}

	private boolean _hasLeftHandExpression;
	private boolean _hasRightHandExpression;
	private int _type;
	private String _value;

}