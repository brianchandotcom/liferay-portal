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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.DebugUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ConstantNameCheck
	extends com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck {

	public void setCamelCaseTypeNames(String camelCaseTypeNames) {
		_camelCaseTypeNames = StringUtil.split(camelCaseTypeNames);
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setImmutableFieldTypes(String immutableFieldTypes) {
		_immutableFieldTypes = StringUtil.split(immutableFieldTypes);
	}

	public void setShowDebugInformation(boolean showDebugInformation) {
		_showDebugInformation = showDebugInformation;
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!_enabled) {
			return;
		}

		if (!_showDebugInformation) {
			_checkConstantName(detailAST);

			return;
		}

		long startTime = System.currentTimeMillis();

		_checkConstantName(detailAST);

		long endTime = System.currentTimeMillis();

		Class<?> clazz = getClass();

		DebugUtil.increaseProcessingTime(
			clazz.getSimpleName(), endTime - startTime);
	}

	private void _checkConstantName(DetailAST detailAST) {
		if (!mustCheckName(detailAST)) {
			return;
		}

		String regex = null;

		String typeName = DetailASTUtil.getTypeName(detailAST);

		if (ArrayUtil.contains(_camelCaseTypeNames, typeName) ||
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

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE)) {
			accessLevel = JavaTerm.ACCESS_MODIFIER_PRIVATE;

			regex = StringUtil.replaceFirst(regex, '^', "^_");
		}
		else if (modifiersAST.branchContains(TokenTypes.LITERAL_PROTECTED)) {
			accessLevel = JavaTerm.ACCESS_MODIFIER_PROTECTED;
		}
		else if (modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {
			accessLevel = JavaTerm.ACCESS_MODIFIER_PUBLIC;
		}
		else {
			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(nameAST.getText());

		if (!matcher.find()) {
			if (typeName == null) {
				log(
					nameAST.getLineNo(), _MSG_INVALID_CONSTANT_NAME,
					accessLevel, nameAST.getText(), regex);
			}
			else {
				log(
					nameAST.getLineNo(), _MSG_INVALID_CONSTANT_TYPE_NAME,
					accessLevel, nameAST.getText(), typeName, regex);
			}
		}
	}

	private boolean _isImmutableFieldType(String typeName) {
		for (String immutableFieldType : _immutableFieldTypes) {
			if (typeName.equals(immutableFieldType) ||
				typeName.startsWith(immutableFieldType + "[]")) {

				return true;
			}
		}

		return false;
	}

	private static final String _CAMEL_CASE_REGEX = "^[a-z0-9][a-zA-Z0-9]*$";

	private static final String _CONSTANT_NAME_REGEX =
		"^[a-zA-Z0-9][_a-zA-Z0-9]*$";

	private static final String _MSG_INVALID_CONSTANT_NAME =
		"name.invalidConstantPattern";

	private static final String _MSG_INVALID_CONSTANT_TYPE_NAME =
		"name.invalidConstantTypePattern";

	private static final String _UPPER_CASE_REGEX = "^[A-Z0-9][_A-Z0-9]*$";

	private String[] _camelCaseTypeNames = new String[0];
	private boolean _enabled = true;
	private String[] _immutableFieldTypes = new String[0];
	private boolean _showDebugInformation;

}