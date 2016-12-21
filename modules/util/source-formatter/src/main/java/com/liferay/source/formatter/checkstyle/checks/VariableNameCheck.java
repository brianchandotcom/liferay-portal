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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class VariableNameCheck extends AbstractCheck {

	public static final String MSG_RENAME_VARIABLE = "variable.rename";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		_checkCaps(detailAST, name);
		_checkIsVariableName(detailAST, name);
	}

	private void _checkCaps(DetailAST detailAST, String name) {
		for (String[] array : _ALL_CAPS_STRINGS) {
			Pattern pattern = Pattern.compile(
				"(.*)" + array[1] + "([A-Z].*|$)");

			Matcher matcher = pattern.matcher(name);

			if (matcher.find()) {
				String newName = matcher.group(1) + array[0] + matcher.group(2);

				log(detailAST.getLineNo(), MSG_RENAME_VARIABLE, name, newName);
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
			log(detailAST.getLineNo(), MSG_RENAME_VARIABLE, name, newName);
		}
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

	private static final String[][] _ALL_CAPS_STRINGS = new String[][] {
		new String[] {"DDL", "Ddl"}, new String[] {"DDM", "Ddm"},
		new String[] {"DL", "Dl"}, new String[] {"PK", "Pk"}
	};

	private static final Pattern _isVariableNamePattern = Pattern.compile(
		"(_?)(is|IS_)([A-Z])(.*)");

}