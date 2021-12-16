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

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class LegacyPropertiesUtil {

	public static List<LegacyProperty> getLegacyProperties(
			String fileName, String content)
		throws Exception {

		List<LegacyProperty> legacyProperties = new ArrayList<>();

		List<String> lines = _getLines(content);

		FileText fileText = new FileText(new File(fileName), lines);

		FileContents fileContents = new FileContents(fileText);

		DetailAST rootDetailAST = JavaParser.parse(fileContents);

		DetailAST nextSiblingDetailAST = rootDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST.getType() != TokenTypes.CLASS_DEF) {
				nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

				continue;
			}

			List<DetailAST> variableDefinitionDetailASTList =
				DetailASTUtil.getAllChildTokens(
					nextSiblingDetailAST, true, TokenTypes.VARIABLE_DEF);

			for (DetailAST variableDefinitionDetailAST :
					variableDefinitionDetailASTList) {

				legacyProperties = _addLegacyProperties(
					legacyProperties, variableDefinitionDetailAST);
			}

			return legacyProperties;
		}
	}

	private static List<LegacyProperty> _addLegacyProperties(
		List<LegacyProperty> legacyProperties,
		DetailAST variableDefinitionDetailAST) {

		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return legacyProperties;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.ARRAY_INIT) {
			return legacyProperties;
		}

		DetailAST identDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String variableName = identDetailAST.getText();

		if (!variableName.contains("_PORTAL_") &&
			!variableName.contains("_SYSTEM_")) {

			return legacyProperties;
		}

		List<DetailAST> arrayValueDetailASTList = _getArrayValueDetailASTList(
			firstChildDetailAST);

		for (DetailAST arrayValueDetailAST : arrayValueDetailASTList) {
			if (arrayValueDetailAST.getType() == TokenTypes.ARRAY_INIT) {
				legacyProperties = _addLegacyProperty(
					legacyProperties, variableName,
					_getArrayValueDetailASTList(arrayValueDetailAST));
			}
			else {
				legacyProperties = _addLegacyProperty(
					legacyProperties, variableName,
					Arrays.asList(arrayValueDetailAST));
			}
		}

		return legacyProperties;
	}

	private static List<LegacyProperty> _addLegacyProperty(
		List<LegacyProperty> legacyProperties, String variableName,
		List<DetailAST> detailASTList) {

		if (detailASTList.isEmpty()) {
			return legacyProperties;
		}

		String legacyPropertyName = _getStringValue(detailASTList.get(0));

		if (legacyPropertyName == null) {
			return legacyProperties;
		}

		String moduleName = null;
		String newPropertyName = null;

		if (detailASTList.size() > 1) {
			newPropertyName = _getStringValue(detailASTList.get(1));

			if (detailASTList.size() > 2) {
				moduleName = _getStringValue(detailASTList.get(2));
			}
		}

		legacyProperties.add(
			new LegacyProperty(
				legacyPropertyName, moduleName, newPropertyName, variableName));

		return legacyProperties;
	}

	private static List<DetailAST> _getArrayValueDetailASTList(
		DetailAST arrayInitDetailAST) {

		List<DetailAST> arrayValueDetailASTList = new ArrayList<>();

		DetailAST childDetailAST = arrayInitDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				return arrayValueDetailASTList;
			}

			if ((childDetailAST.getType() != TokenTypes.COMMA) &&
				(childDetailAST.getType() != TokenTypes.RCURLY)) {

				arrayValueDetailASTList.add(childDetailAST);
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private static List<String> _getLines(String s) throws Exception {
		List<String> lines = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(s))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

	private static String _getStringValue(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EXPR) {
			detailAST = detailAST.getFirstChild();
		}

		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			String text = detailAST.getText();

			return text.substring(1, text.length() - 1);
		}

		if (detailAST.getType() == TokenTypes.PLUS) {
			String left = _getStringValue(detailAST.getFirstChild());
			String right = _getStringValue(detailAST.getLastChild());

			if ((left != null) && (right != null)) {
				return left + right;
			}
		}

		return null;
	}

}