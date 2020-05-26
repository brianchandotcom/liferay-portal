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
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class GenericTypeCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkType(detailAST, detailAST.findFirstToken(TokenTypes.TYPE));
	}

	private void _checkType(DetailAST detailAST, DetailAST typeDetailAST) {
		if ((typeDetailAST == null) ||
			(detailAST.findFirstToken(TokenTypes.ELLIPSIS) != null)) {

			return;
		}

		DetailAST identDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return;
		}

		DetailAST typeArgumentsDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsDetailAST != null) {
			List<DetailAST> typeArgumentDetailASTList = getAllChildTokens(
				typeArgumentsDetailAST, false, TokenTypes.TYPE_ARGUMENT);

			if (isAttributeValue(_POPULATE_TYPE_NAMES_KEY)) {
				_populateGenericTypeNames(
					typeDetailAST, typeArgumentDetailASTList);
			}

			for (DetailAST typeArgumentDetailAST : typeArgumentDetailASTList) {
				_checkType(detailAST, typeArgumentDetailAST);
			}

			return;
		}

		String genericTypeName = _getGenericTypeName(typeDetailAST);

		if (genericTypeName == null) {
			return;
		}

		if (!genericTypeName.startsWith("com.liferay.")) {
			if ((detailAST.getType() == TokenTypes.METHOD_DEF) &&
				_overridesUnknownTerm(detailAST)) {

				return;
			}

			if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				DetailAST parentDetailAST = getParentWithTokenType(
					detailAST, TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF);

				if ((parentDetailAST != null) &&
					_overridesUnknownTerm(parentDetailAST)) {

					return;
				}
			}
		}

		DetailAST parentDetailAST = typeDetailAST;

		while (true) {
			if (parentDetailAST == null) {
				break;
			}

			if (_hasSuppressWarningsAnnotation(parentDetailAST, "rawtypes")) {
				return;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		Map<String, Integer> genericTypeNamesMap = _getGenericTypeNamesMap();

		int genericTypeCount = GetterUtil.getInteger(
			genericTypeNamesMap.get(genericTypeName));

		if (genericTypeCount == 1) {
			log(
				typeDetailAST, _MSG_PARAMETERIZE_GENERIC_TYPE, "type",
				genericTypeName);
		}
		else {
			log(
				typeDetailAST, _MSG_PARAMETERIZE_GENERIC_TYPE, "types",
				genericTypeName);
		}
	}

	private String _getGenericTypeName(DetailAST typeDetailAST) {
		Map<String, Integer> genericTypeNamesMap = _getGenericTypeNamesMap();

		String typeName = getFullyQualifiedTypeName(typeDetailAST, false);

		if ((typeName != null) && genericTypeNamesMap.containsKey(typeName)) {
			return typeName;
		}

		typeName = getTypeName(typeDetailAST, false);

		if (genericTypeNamesMap.containsKey("java.lang." + typeName)) {
			return "java.lang." + typeName;
		}

		FileContents fileContents = getFileContents();

		FileText fileText = fileContents.getText();

		typeName =
			JavaSourceUtil.getPackageName((String)fileText.getFullText()) +
				StringPool.DOUBLE_PERIOD + typeName;

		if (genericTypeNamesMap.containsKey(typeName)) {
			return typeName;
		}

		return null;
	}

	private Map<String, Integer> _getGenericTypeNamesMap() {
		Tuple genericTypeNamesTuple = _getGenericTypeNamesTuple();

		JSONObject jsonObject = (JSONObject)genericTypeNamesTuple.getObject(0);

		JSONArray jsonArray = (JSONArray)jsonObject.get(
			_GENERIC_TYPE_NAMES_CATEGORY);

		Map<String, Integer> genericTypeNamesMap = new TreeMap<>();

		for (Object object : JSONUtil.toObjectList(jsonArray)) {
			jsonObject = (JSONObject)object;

			genericTypeNamesMap.put(
				jsonObject.getString("name"),
				jsonObject.getInt("genericTypeCount"));
		}

		return genericTypeNamesMap;
	}

	private synchronized Tuple _getGenericTypeNamesTuple() {
		if (_genericTypeNamesTuple != null) {
			return _genericTypeNamesTuple;
		}

		_genericTypeNamesTuple = getTypeNamesTuple(
			_GENERIC_TYPE_NAMES_FILE_NAME, _GENERIC_TYPE_NAMES_CATEGORY);

		return _genericTypeNamesTuple;
	}

	private boolean _hasSuppressWarningsAnnotation(
		DetailAST detailAST, String warning) {

		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST == null) {
			return false;
		}

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "SuppressWarnings");

		if (annotationDetailAST == null) {
			return false;
		}

		List<DetailAST> literalStringDetailASTList = getAllChildTokens(
			annotationDetailAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST literalStringDetailAST : literalStringDetailASTList) {
			String s = literalStringDetailAST.getText();

			if (s.equals("\"" + warning + "\"")) {
				return true;
			}
		}

		return false;
	}

	private boolean _overridesUnknownTerm(DetailAST detailAST) {
		if (!AnnotationUtil.containsAnnotation(detailAST, "Override")) {
			return false;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.CLASS_DEF) ||
			(parentDetailAST.findFirstToken(TokenTypes.EXTENDS_CLAUSE) !=
				null)) {

			return true;
		}

		DetailAST implementsClauseDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.IMPLEMENTS_CLAUSE);

		if (implementsClauseDetailAST == null) {
			return false;
		}

		List<String> importNames = getImportNames(detailAST);

		DetailAST childDetailAST = implementsClauseDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				return false;
			}

			if (childDetailAST.getType() == TokenTypes.IDENT) {
				String implementedClassName = childDetailAST.getText();

				if (ArrayUtil.contains(
						_JAVA_LANG_INTERFACE_NAMES, implementedClassName)) {

					return true;
				}

				for (String importName : importNames) {
					if (importName.endsWith("." + implementedClassName)) {
						if (!importName.startsWith("com.liferay.")) {
							return true;
						}

						break;
					}
				}
			}
			else if (childDetailAST.getType() == TokenTypes.DOT) {
				FullIdent fullIdent = FullIdent.createFullIdent(childDetailAST);

				String implementedClassName = fullIdent.getText();

				if (!implementedClassName.startsWith("com.liferay.")) {
					return true;
				}
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private void _populateGenericTypeNames(
		DetailAST typeDetailAST, List<DetailAST> typeArgumentDetailASTList) {

		Tuple genericTypeNamesTuple = _getGenericTypeNamesTuple();

		File genericTypeNamesFile = (File)genericTypeNamesTuple.getObject(1);

		if (genericTypeNamesFile == null) {
			return;
		}

		for (DetailAST typeArgumentDetailAST : typeArgumentDetailASTList) {
			DetailAST firstChildDetailAST =
				typeArgumentDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.WILDCARD_TYPE) {
				return;
			}

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				String name = firstChildDetailAST.getText();

				if (name.length() == 1) {
					return;
				}
			}
		}

		Map<String, Integer> genericTypeNamesMap = _getGenericTypeNamesMap();

		String typeName = getFullyQualifiedTypeName(typeDetailAST, false);

		if ((typeName == null) || genericTypeNamesMap.containsKey(typeName)) {
			return;
		}

		genericTypeNamesMap.put(typeName, typeArgumentDetailASTList.size());

		try {
			JSONObject jsonObject = new JSONObjectImpl();

			JSONArray jsonArray = new JSONArrayImpl();

			for (Map.Entry<String, Integer> entry :
					genericTypeNamesMap.entrySet()) {

				JSONObject curJSONObject = new JSONObjectImpl();

				curJSONObject.put(
					"genericTypeCount", entry.getValue()
				).put(
					"name", entry.getKey()
				);

				jsonArray.put(curJSONObject);
			}

			jsonObject.put(_GENERIC_TYPE_NAMES_CATEGORY, jsonArray);

			FileUtil.write(genericTypeNamesFile, JSONUtil.toString(jsonObject));

			System.out.println(
				StringBundler.concat(
					"Added '", typeName, "' to '",
					_GENERIC_TYPE_NAMES_FILE_NAME, "'"));

			_genericTypeNamesTuple = null;
		}
		catch (IOException ioException) {
		}
	}

	private static final String _GENERIC_TYPE_NAMES_CATEGORY =
		"genericTypeNames";

	private static final String _GENERIC_TYPE_NAMES_FILE_NAME =
		"generic-type-names.json";

	private static final String[] _JAVA_LANG_INTERFACE_NAMES = {
		"Appendable", "AutoCloseable", "CharSequence", "Cloneable",
		"Comparable", "Iterable", "Readable", "Runnable"
	};

	private static final String _MSG_PARAMETERIZE_GENERIC_TYPE =
		"generic.type.parameterize";

	private static final String _POPULATE_TYPE_NAMES_KEY = "populateTypeNames";

	private Tuple _genericTypeNamesTuple;

}