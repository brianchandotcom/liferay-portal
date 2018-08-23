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

import com.liferay.petra.string.StringBundler;
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
			String s = array[1];

			int x = -1;

			while (true) {
				x = name.indexOf(s, x + 1);

				if (x == -1) {
					break;
				}

				int y = x + s.length();

				if ((y != name.length()) &&
					!Character.isUpperCase(name.charAt(y))) {

					continue;
				}

				String newName =
					name.substring(0, x) + array[0] + name.substring(y);

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
		if (StringUtil.isUpperCase(name)) {
			return;
		}

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

		String nameTrailingDigits = _getTrailingDigits(name);

		String trimmedName = StringUtil.replaceLast(
			name, nameTrailingDigits, StringPool.BLANK);

		String leadingUnderline = StringPool.BLANK;

		if (name.startsWith(StringPool.UNDERLINE)) {
			leadingUnderline = StringPool.UNDERLINE;

			trimmedName = trimmedName.substring(1);
		}

		String typeNameTrailingDigits = _getTrailingDigits(typeName);

		String trimmedTypeName = StringUtil.replaceLast(
			typeName, typeNameTrailingDigits, StringPool.BLANK);

		String expectedName = _getExpectedVariableName(trimmedTypeName);

		if (StringUtil.equals(trimmedName, expectedName)) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(trimmedName, trimmedTypeName)) {
			for (int i = expectedName.length() - 1; i >= 0; i--) {
				char c1 = trimmedName.charAt(i);

				if (c1 == expectedName.charAt(i)) {
					continue;
				}

				if (i < (expectedName.length() - 1)) {
					char c2 = trimmedName.charAt(i + 1);

					if (Character.isUpperCase(c1) &&
						(Character.isDigit(c2) || Character.isUpperCase(c2))) {

						return;
					}
				}
			}

			log(
				detailAST.getLineNo(), _MSG_TYPO_VARIABLE, name,
				StringBundler.concat(
					leadingUnderline, expectedName, nameTrailingDigits));

			return;
		}

		trimmedName = StringUtil.toLowerCase(trimmedName);
		trimmedTypeName = StringUtil.toLowerCase(trimmedTypeName);

		if ((trimmedName.charAt(0) != trimmedTypeName.charAt(0)) ||
			(trimmedName.charAt(trimmedName.length() - 1) !=
				trimmedTypeName.charAt(trimmedTypeName.length() - 1))) {

			return;
		}

		int min = Math.min(trimmedName.length(), trimmedTypeName.length());
		int diff = Math.abs(trimmedName.length() - trimmedTypeName.length());

		if ((min < 5) || (diff > 1)) {
			return;
		}

		int i = StringUtil.startsWithWeight(trimmedName, trimmedTypeName);

		trimmedName = trimmedName.substring(i);

		if (trimmedName.startsWith(StringPool.UNDERLINE)) {
			return;
		}

		trimmedTypeName = trimmedTypeName.substring(i);

		for (int j = 1;; j++) {
			if ((j > trimmedName.length()) || (j > trimmedTypeName.length())) {
				break;
			}

			if (trimmedName.charAt(trimmedName.length() - j) !=
					trimmedTypeName.charAt(trimmedTypeName.length() - j)) {

				if (!_containSameCharacters(trimmedName, trimmedTypeName)) {
					return;
				}

				break;
			}
		}

		log(
			detailAST.getLineNo(), _MSG_TYPO_VARIABLE, name,
			_getExpectedVariableName(
				typeName, leadingUnderline, nameTrailingDigits));
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

		if (typeName.startsWith("IDf")) {
			return StringUtil.replaceFirst(typeName, "IDf", "idf");
		}

		if (typeName.startsWith("OSGi")) {
			return StringUtil.replaceFirst(typeName, "OSGi", "osgi");
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

	private String _getExpectedVariableName(
		String typeName, String leadingUnderline, String trailingDigits) {

		return StringBundler.concat(
			leadingUnderline, _getExpectedVariableName(typeName),
			trailingDigits);
	}

	private String _getTrailingDigits(String s) {
		String digits = StringPool.BLANK;

		for (int i = s.length() - 1; i >= 0; i--) {
			if (Character.isDigit(s.charAt(i))) {
				digits = s.charAt(i) + digits;
			}
			else {
				return digits;
			}
		}

		return digits;
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

	private static final String[][] _ALL_CAPS_STRINGS = {
		{"DDL", "Ddl"}, {"DDM", "Ddm"}, {"DL", "Dl"}, {"PK", "Pk"}
	};

	private static final String _MSG_RENAME_VARIABLE = "variable.rename";

	private static final String _MSG_TYPO_VARIABLE = "variable.typo";

	private static final Pattern _isVariableNamePattern = Pattern.compile(
		"(_?)(is|IS_)([A-Z])(.*)");

}