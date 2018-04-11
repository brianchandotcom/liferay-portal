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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class VariableNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST.branchContains(TokenTypes.LITERAL_PROTECTED) ||
			modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		_checkCaps(detailAST, name);
		_checkIsVariableName(detailAST, name);
		_checkTypo(detailAST, name);
	}

	private void _checkCaps(DetailAST detailAST, String name) {
		for (String[] array : _ALL_CAPS_STRINGS) {
			Pattern pattern = Pattern.compile(
				"(.*)" + array[1] + "([A-Z].*|$)");

			Matcher matcher = pattern.matcher(name);

			if (matcher.find()) {
				String newName = matcher.group(1) + array[0] + matcher.group(2);

				log(detailAST.getLineNo(), _MSG_RENAME_VARIABLE, name, newName);
			}
		}
	}

	private void _checkIsVariableName(DetailAST detailAST, String name) {
		if (!_isBooleanType(detailAST.findFirstToken(TokenTypes.TYPE))) {
			return;
		}

		Matcher matcher = _isVariableNamePattern.matcher(name);

		if (!matcher.find()) {
			return;
		}

		String group2 = matcher.group(2);

		String newName = null;

		if (group2.equals("is")) {
			newName =
				StringUtil.toLowerCase(matcher.group(3)) + matcher.group(4);

			if (!Validator.isVariableName(newName)) {
				return;
			}

			newName = matcher.group(1) + newName;
		}
		else {
			newName = matcher.group(1) + matcher.group(3) + matcher.group(4);
		}

		if (!_classHasVariableWithName(detailAST, newName)) {
			log(detailAST.getLineNo(), _MSG_RENAME_VARIABLE, name, newName);
		}
	}

	private void _checkTypo(DetailAST detailAST, String name) {
		DetailAST typeAST = detailAST.findFirstToken(TokenTypes.TYPE);

		DetailAST firstChildAST = typeAST.getFirstChild();

		if ((firstChildAST == null) ||
			(firstChildAST.getType() != TokenTypes.IDENT)) {

			return;
		}

		String typeName = firstChildAST.getText();

		if (typeName.contains(StringPool.UNDERLINE)) {
			return;
		}

		String s1 = StringUtil.toLowerCase(_trimTrailingDigits(typeName));

		String originalName = name;

		boolean leadingUnderLine = false;

		if (name.startsWith(StringPool.UNDERLINE)) {
			leadingUnderLine = true;

			name = name.substring(1);
		}

		String s2 = StringUtil.toLowerCase(_trimTrailingDigits(name));

		if (s1.equals(s2)) {
			return;
		}

		if ((s1.charAt(0) != s2.charAt(0)) ||
			(s1.charAt(s1.length() - 1) != s2.charAt(s2.length() - 1))) {

			return;
		}

		int min = Math.min(s1.length(), s2.length());
		int diff = Math.abs(s1.length() - s2.length());

		if ((min < 5) || (diff > 1)) {
			return;
		}

		int i = StringUtil.startsWithWeight(s1, s2);

		s1 = s1.substring(i);
		s2 = s2.substring(i);

		if (s2.startsWith(StringPool.UNDERLINE)) {
			return;
		}

		for (int j = 1;; j++) {
			if ((j > s1.length()) || (j > s2.length())) {
				break;
			}

			if (s1.charAt(s1.length() - j) != s2.charAt(s2.length() - j)) {
				if (!_containSameCharacters(s1, s2)) {
					return;
				}

				break;
			}
		}

		String expectedName = _getExpectedVariableName(typeName);

		if (leadingUnderLine) {
			expectedName = StringPool.UNDERLINE + expectedName;
		}

		log(
			detailAST.getLineNo(), _MSG_TYPO_VARIABLE, originalName,
			expectedName);
	}

	private boolean _classHasVariableWithName(
		DetailAST detailAST, String variableName) {

		DetailAST parentAST = detailAST.getParent();

		List<DetailAST> definitionASTList = new ArrayList<>();

		while (true) {
			if (parentAST == null) {
				break;
			}

			if (parentAST.getType() == TokenTypes.METHOD_DEF) {
				definitionASTList.addAll(
					DetailASTUtil.getAllChildTokens(
						parentAST, true, TokenTypes.PARAMETER_DEF,
						TokenTypes.VARIABLE_DEF));
			}

			if (parentAST.getType() == TokenTypes.CLASS_DEF) {
				DetailAST objblockAST = parentAST.findFirstToken(
					TokenTypes.OBJBLOCK);

				definitionASTList.addAll(
					DetailASTUtil.getAllChildTokens(
						objblockAST, false, TokenTypes.VARIABLE_DEF));
			}

			parentAST = parentAST.getParent();
		}

		for (DetailAST definitionAST : definitionASTList) {
			DetailAST definitionNameAST = definitionAST.findFirstToken(
				TokenTypes.IDENT);

			if (variableName.equals(definitionNameAST.getText())) {
				return true;
			}
		}

		return false;
	}

	private boolean _containSameCharacters(String s1, String s2) {
		char[] chars1 = s1.toCharArray();
		char[] chars2 = s2.toCharArray();

		Arrays.sort(chars1);
		Arrays.sort(chars2);

		return Arrays.equals(chars1, chars2);
	}

	private String _getExpectedVariableName(String typeName) {
		if (StringUtil.isUpperCase(typeName)) {
			return StringUtil.toLowerCase(typeName);
		}

		for (int i = 0; i < typeName.length(); i++) {
			char c = typeName.charAt(i);

			if (!Character.isLowerCase(c)) {
				continue;
			}

			if (i == 0) {
				return typeName;
			}

			if (i == 1) {
				return StringUtil.toLowerCase(typeName.substring(0, 1)) +
					typeName.substring(1);
			}

			return StringUtil.toLowerCase(typeName.substring(0, i - 1)) +
				typeName.substring(i - 1);
		}

		return StringUtil.toLowerCase(typeName);
	}

	private boolean _isBooleanType(DetailAST typeAST) {
		DetailAST childAST = typeAST.getFirstChild();

		if (childAST == null) {
			return false;
		}

		if (childAST.getType() == TokenTypes.LITERAL_BOOLEAN) {
			return true;
		}

		if (childAST.getType() == TokenTypes.IDENT) {
			String name = childAST.getText();

			if (name.equals("Boolean")) {
				return true;
			}
		}

		return false;
	}

	private String _trimTrailingDigits(String s) {
		for (int i = s.length() - 1; i >= 0; i--) {
			if (!Character.isDigit(s.charAt(i))) {
				return s.substring(0, i + 1);
			}
		}

		return StringPool.BLANK;
	}

	private static final String[][] _ALL_CAPS_STRINGS = {
		new String[] {"DDL", "Ddl"}, new String[] {"DDM", "Ddm"},
		new String[] {"DL", "Dl"}, new String[] {"PK", "Pk"}
	};

	private static final String _MSG_RENAME_VARIABLE = "variable.rename";

	private static final String _MSG_TYPO_VARIABLE = "variable.typo";

	private static final Pattern _isVariableNamePattern = Pattern.compile(
		"(_?)(is|IS_)([A-Z])(.*)");

}