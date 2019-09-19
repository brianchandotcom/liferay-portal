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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class PropertiesVerifyPropertiesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("/modules/")) {
			return content;
		}

		PropertyType propertyType = null;

		if (absolutePath.matches(".*/portal([-\\w]+)?\\.properties")) {
			propertyType = PropertyType.PORTAL;
		}
		else if (absolutePath.matches(".*/system([-\\w]+)?\\.properties")) {
			propertyType = PropertyType.SYSTEM;
		}
		else {
			return content;
		}

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		List<LegacyProperty> legacyProperties = _getLegacyProperties(
			absolutePath);

		for (LegacyProperty legacyProperty : legacyProperties) {
			if (!properties.containsKey(legacyProperty.getName()) ||
				!propertyType.equals(legacyProperty.getPropertyType())) {

				continue;
			}

			StringBundler sb = new StringBundler(10);

			if (propertyType.equals(PropertyType.PORTAL)) {
				sb.append("Portal ");
			}
			else {
				sb.append("System ");
			}

			sb.append("property '");
			sb.append(legacyProperty.getName());

			ActionType actionType = legacyProperty.getActionType();

			if (actionType.equals(ActionType.MIGRATED)) {
				sb.append("' was migrated to ");

				if (propertyType.equals(PropertyType.PORTAL)) {
					sb.append("'system.properties'");
				}
				else {
					sb.append("'portal.properties'");
				}
			}
			else if (actionType.equals(ActionType.MODULARIZED)) {
				sb.append("' was modularized");
			}
			else if (actionType.equals(ActionType.OBSOLETE)) {
				sb.append("' is obsolete");
			}
			else {
				sb.append("' was renamed");
			}

			sb.append(". See '");
			sb.append(
				StringUtil.removeSubstring(
					_VERIFY_PROPERTIES_FILE_NAME, ".java"));
			sb.append("#");
			sb.append(legacyProperty.getVariableName());
			sb.append("'.");

			addMessage(fileName, sb.toString());
		}

		return content;
	}

	private static List<String> _getLines(String s) throws IOException {
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

	private void _addLegacyProperties(DetailAST variableDefinitionDetailAST) {
		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return;
		}

		DetailAST identDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String variableName = identDetailAST.getText();

		if (variableName.equals("_MIGRATED_PORTAL_KEYS")) {
			_addLegacyProperties(
				PropertyType.PORTAL, ActionType.MIGRATED, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_MIGRATED_SYSTEM_KEYS")) {
			_addLegacyProperties(
				PropertyType.SYSTEM, ActionType.MIGRATED, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_MODULARIZED_PORTAL_KEYS")) {
			_addLegacyProperties(
				PropertyType.PORTAL, ActionType.MODULARIZED, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_MODULARIZED_SYSTEM_KEYS")) {
			_addLegacyProperties(
				PropertyType.SYSTEM, ActionType.MODULARIZED, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_OBSOLETE_PORTAL_KEYS")) {
			_addLegacyProperties(
				PropertyType.PORTAL, ActionType.OBSOLETE, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_OBSOLETE_SYSTEM_KEYS")) {
			_addLegacyProperties(
				PropertyType.SYSTEM, ActionType.OBSOLETE, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_RENAMED_PORTAL_KEYS")) {
			_addLegacyProperties(
				PropertyType.PORTAL, ActionType.RENAMED, assignDetailAST,
				variableName);
		}
		else if (variableName.equals("_RENAMED_SYSTEM_KEYS")) {
			_addLegacyProperties(
				PropertyType.SYSTEM, ActionType.RENAMED, assignDetailAST,
				variableName);
		}
	}

	private void _addLegacyProperties(
		PropertyType propertyType, ActionType actionType,
		DetailAST assignDetailAST, String variableName) {

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.ARRAY_INIT) {
			return;
		}

		List<DetailAST> arrayValueDetailASTList = _getArrayValueDetailASTList(
			firstChildDetailAST);

		for (DetailAST arrayValueDetailAST : arrayValueDetailASTList) {
			if (arrayValueDetailAST.getType() == TokenTypes.ARRAY_INIT) {
				_addLegacyProperty(
					propertyType, actionType, variableName,
					_getArrayValueDetailASTList(arrayValueDetailAST));
			}
			else {
				_addLegacyProperty(
					propertyType, actionType, variableName,
					Arrays.asList(arrayValueDetailAST));
			}
		}
	}

	private void _addLegacyProperty(
		PropertyType propertyType, ActionType actionType, String variableName,
		List<DetailAST> detailASTList) {

		if (detailASTList.isEmpty()) {
			return;
		}

		String name = _getStringValue(detailASTList.get(0));

		if (name != null) {
			_legacyProperties.add(
				new LegacyProperty(
					name, propertyType, actionType, variableName));
		}
	}

	private List<DetailAST> _getArrayValueDetailASTList(
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

	private synchronized List<LegacyProperty> _getLegacyProperties(
			String absolutePath)
		throws Exception {

		if (_legacyProperties != null) {
			return _legacyProperties;
		}

		_legacyProperties = new ArrayList<>();

		String verifyPropertiesContent = getPortalContent(
			_VERIFY_PROPERTIES_FILE_NAME, absolutePath);

		List<String> lines = _getLines(verifyPropertiesContent);

		FileText fileText = new FileText(
			new File(_VERIFY_PROPERTIES_FILE_NAME), lines);

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

				_addLegacyProperties(variableDefinitionDetailAST);
			}

			return _legacyProperties;
		}
	}

	private String _getStringValue(DetailAST detailAST) {
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

	private static final String _VERIFY_PROPERTIES_FILE_NAME =
		"portal-impl/src/com/liferay/portal/verify/VerifyProperties.java";

	private List<LegacyProperty> _legacyProperties;

	private enum ActionType {

		MIGRATED, MODULARIZED, OBSOLETE, RENAMED

	}

	private class LegacyProperty {

		public LegacyProperty(
			String name, PropertyType propertyType, ActionType actionType,
			String variableName) {

			_name = name;
			_propertyType = propertyType;
			_actionType = actionType;
			_variableName = variableName;
		}

		public ActionType getActionType() {
			return _actionType;
		}

		public String getName() {
			return _name;
		}

		public PropertyType getPropertyType() {
			return _propertyType;
		}

		public String getVariableName() {
			return _variableName;
		}

		private final ActionType _actionType;
		private final String _name;
		private final PropertyType _propertyType;
		private final String _variableName;

	}

	private enum PropertyType {

		PORTAL, SYSTEM

	}

}