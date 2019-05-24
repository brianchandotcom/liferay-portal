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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ConstantNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkConstantName(detailAST);
	}

	private void _checkConstantName(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (!modifiersDetailAST.branchContains(TokenTypes.FINAL) ||
			!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {

			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		if (name.equals("serialPersistentFields") ||
			name.equals("serialVersionUID")) {

			return;
		}

		String regex = null;

		String typeName = DetailASTUtil.getTypeName(detailAST, false);

		List<String> camelCaseTypeNames = getAttributeValues(
			_CAMEL_CASE_TYPE_NAMES_KEY);

		if (camelCaseTypeNames.contains(typeName) ||
			DetailASTUtil.isCollection(
				detailAST.findFirstToken(TokenTypes.TYPE))) {

			regex = _CAMEL_CASE_REGEX;
		}
		else if (_isImmutableFieldType(typeName)) {
			regex = _UPPER_CASE_REGEX;
		}
		else {
			regex = _CONSTANT_NAME_REGEX;
			typeName = null;
		}

		String accessLevel = null;

		if (modifiersDetailAST.branchContains(TokenTypes.LITERAL_PRIVATE)) {
			accessLevel = JavaTerm.ACCESS_MODIFIER_PRIVATE;

			regex = StringUtil.replaceFirst(regex, '^', "^_");
		}
		else if (modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PROTECTED)) {

			accessLevel = JavaTerm.ACCESS_MODIFIER_PROTECTED;
		}
		else if (modifiersDetailAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {
			accessLevel = JavaTerm.ACCESS_MODIFIER_PUBLIC;
		}
		else {
			return;
		}

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(name);

		if (!matcher.find()) {
			if (typeName == null) {
				log(
					nameDetailAST, _MSG_INVALID_CONSTANT_NAME, accessLevel,
					name, regex);
			}
			else {
				log(
					nameDetailAST, _MSG_INVALID_CONSTANT_TYPE_NAME, accessLevel,
					name, typeName, regex);
			}
		}
	}

	private boolean _isImmutableFieldType(String typeName) {
		List<String> immutableFieldTypes = getAttributeValues(
			_IMMUTABLE_FIELD_TYPES_KEY);

		for (String immutableFieldType : immutableFieldTypes) {
			if (typeName.equals(immutableFieldType) ||
				typeName.startsWith(immutableFieldType + "[]")) {

				return true;
			}
		}

		return false;
	}

	private static final String _CAMEL_CASE_REGEX = "^[a-z0-9][a-zA-Z0-9]*$";

	private static final String _CAMEL_CASE_TYPE_NAMES_KEY =
		"camelCaseTypeNames";

	private static final String _CONSTANT_NAME_REGEX =
		"^[a-zA-Z0-9][_a-zA-Z0-9]*$";

	private static final String _IMMUTABLE_FIELD_TYPES_KEY =
		"immutableFieldTypes";

	private static final String _MSG_INVALID_CONSTANT_NAME =
		"name.invalidConstantPattern";

	private static final String _MSG_INVALID_CONSTANT_TYPE_NAME =
		"name.invalidConstantTypePattern";

	private static final String _UPPER_CASE_REGEX = "^[A-Z0-9][_A-Z0-9]*$";

}