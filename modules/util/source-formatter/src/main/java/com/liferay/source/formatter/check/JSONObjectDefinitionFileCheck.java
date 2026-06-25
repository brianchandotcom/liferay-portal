/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JSONSourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JSONObjectDefinitionFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!absolutePath.endsWith("-object-definition.json") &&
			!absolutePath.endsWith(
				"object-definition.batch-engine-data.json")) {

			return content;
		}

		try {
			JSONObject jsonObject = new JSONObjectImpl(content);

			if (absolutePath.endsWith("-object-definition.json")) {
				_checkClassName(fileName, jsonObject);
				_sortObjectFields(jsonObject);

				return JSONUtil.toString(jsonObject);
			}
			else if (absolutePath.endsWith(
						"object-definition.batch-engine-data.json")) {

				JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

				if (itemsJSONArray == null) {
					return JSONUtil.toString(jsonObject);
				}

				List<Object> items = JSONUtil.toObjectList(itemsJSONArray);

				for (Object item : items) {
					_sortObjectFields((JSONObject)item);
				}

				return JSONUtil.toString(jsonObject);
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return content;
	}

	private boolean _appendString(DetailAST detailAST, StringBundler sb) {
		if (detailAST == null) {
			return false;
		}

		if (detailAST.getType() == TokenTypes.STRING_LITERAL) {
			String text = detailAST.getText();

			sb.append(text.substring(1, text.length() - 1));

			return true;
		}

		if (detailAST.getType() != TokenTypes.PLUS) {
			return false;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return false;
		}

		DetailAST nextSiblingDetailAST = firstChildDetailAST.getNextSibling();

		if (_appendString(firstChildDetailAST, sb) &&
			_appendString(nextSiblingDetailAST, sb)) {

			return true;
		}

		return false;
	}

	private void _checkClassName(String fileName, JSONObject jsonObject)
		throws Exception {

		String regex = _getClassNameRegex();

		if (regex == null) {
			return;
		}

		String className = jsonObject.getString("className");

		if (className.isBlank() || className.matches(regex)) {
			return;
		}

		addMessage(
			fileName,
			"\"className\" does not match the pattern specified by \"_class" +
				"NamePattern\" in \"ObjectDefinitionClassNameProcessorImpl\"");
	}

	private synchronized String _getClassNameRegex() throws Exception {
		if (_classNameRegex != null) {
			return _classNameRegex;
		}

		File portalDir = getPortalDir();

		if (portalDir == null) {
			return null;
		}

		File file = new File(
			portalDir,
			"/modules/apps/object/object-service/src/main/java/com/liferay" +
				"/object/internal/definition/processor" +
					"/ObjectDefinitionClassNameProcessorImpl.java");

		String content = FileUtil.read(file);

		FileText fileText = new FileText(
			file, CheckstyleUtil.getLines(content));

		FileContents fileContents = new FileContents(fileText);

		DetailAST rootDetailAST = JavaParser.parse(fileContents);

		DetailAST nextSiblingDetailAST = rootDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST.getType() != TokenTypes.CLASS_DEF) {
				nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

				continue;
			}

			DetailAST objBlockDetailAST = nextSiblingDetailAST.findFirstToken(
				TokenTypes.OBJBLOCK);

			List<DetailAST> variableDefinitionDetailASTs =
				DetailASTUtil.getAllChildTokens(
					objBlockDetailAST, false, TokenTypes.VARIABLE_DEF);

			for (DetailAST variableDefinitionDetailAST :
					variableDefinitionDetailASTs) {

				DetailAST identDetailAST =
					variableDefinitionDetailAST.findFirstToken(
						TokenTypes.IDENT);

				if (identDetailAST == null) {
					continue;
				}

				String variableName = identDetailAST.getText();

				if (!variableName.equals("_classNamePattern")) {
					continue;
				}

				DetailAST assignDetailAST =
					variableDefinitionDetailAST.findFirstToken(
						TokenTypes.ASSIGN);

				if (assignDetailAST == null) {
					return null;
				}

				DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

				if ((firstChildDetailAST == null) ||
					(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

					return null;
				}

				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if ((firstChildDetailAST == null) ||
					(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

					return null;
				}

				FullIdent fullIdent = FullIdent.createFullIdentBelow(
					firstChildDetailAST);

				if (!StringUtil.equals(
						fullIdent.getText(), "Pattern.compile")) {

					return null;
				}

				_classNameRegex = _getClassNameRegex(firstChildDetailAST);

				return _classNameRegex;
			}

			return null;
		}
	}

	private String _getClassNameRegex(DetailAST detailAST) {
		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		if ((elistDetailAST == null) || (elistDetailAST.getChildCount() == 0)) {
			return null;
		}

		DetailAST exprDetailAST = elistDetailAST.findFirstToken(
			TokenTypes.EXPR);

		if (exprDetailAST == null) {
			return null;
		}

		StringBundler sb = new StringBundler();

		if (_appendString(exprDetailAST.getFirstChild(), sb)) {
			return StringUtil.replace(sb.toString(), "\\\\", "\\");
		}

		return null;
	}

	private void _sortObjectFields(JSONObject jsonObject) {
		JSONArray objectFieldsJSONArray = jsonObject.getJSONArray(
			"objectFields");

		if (objectFieldsJSONArray == null) {
			return;
		}

		jsonObject.put(
			"objectFields",
			JSONSourceUtil.sortJSONArray(
				objectFieldsJSONArray, new ObjectFieldComparator()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONObjectDefinitionFileCheck.class);

	private String _classNameRegex;

	private static class ObjectFieldComparator implements Comparator<Object> {

		@Override
		public int compare(Object object1, Object object2) {
			JSONObject jsonObject1 = (JSONObject)object1;
			JSONObject jsonObject2 = (JSONObject)object2;

			boolean relationship1 = _isRelationship(jsonObject1);
			boolean relationship2 = _isRelationship(jsonObject2);

			if (relationship1 != relationship2) {
				if (relationship1) {
					return 1;
				}

				return -1;
			}

			String name1 = jsonObject1.getString("name");
			String name2 = jsonObject2.getString("name");

			return name1.compareTo(name2);
		}

		private boolean _isRelationship(JSONObject jsonObject) {
			String businessType = jsonObject.getString("businessType");

			if (businessType.equals("Relationship")) {
				return true;
			}

			String name = jsonObject.getString("name");

			if (Validator.isNotNull(name) && name.startsWith("r_")) {
				return true;
			}

			return false;
		}

	}

}