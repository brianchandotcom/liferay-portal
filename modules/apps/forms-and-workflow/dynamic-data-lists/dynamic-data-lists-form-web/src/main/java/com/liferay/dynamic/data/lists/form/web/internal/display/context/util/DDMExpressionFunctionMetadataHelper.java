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

package com.liferay.dynamic.data.lists.form.web.internal.display.context.util;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Rafael Praxedes
 */
public class DDMExpressionFunctionMetadataHelper {

	public DDMExpressionFunctionMetadataHelper(ResourceBundle resourceBundle) {
		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"belongs-to", LanguageUtil.get(resourceBundle, "belongs-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_USER, _TYPE_LIST}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"greater-than",
				LanguageUtil.get(resourceBundle, "is-greater-than"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"greater-than-equals",
				LanguageUtil.get(resourceBundle, "is-greater-than-or-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"less-than", LanguageUtil.get(resourceBundle, "is-less-than"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"less-than-equals",
				LanguageUtil.get(resourceBundle, "is-less-than-or-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"equals-to", LanguageUtil.get(resourceBundle, "is-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"equals-to", LanguageUtil.get(resourceBundle, "is-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_TEXT, _TYPE_TEXT}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"not-equals-to",
				LanguageUtil.get(resourceBundle, "is-not-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER, _TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"not-equals-to",
				LanguageUtil.get(resourceBundle, "is-not-equal-to"),
				_TYPE_BOOLEAN, new String[] {_TYPE_TEXT, _TYPE_TEXT}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"contains", LanguageUtil.get(resourceBundle, "contains"),
				_TYPE_BOOLEAN, new String[] {_TYPE_TEXT, _TYPE_TEXT}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"not-contains",
				LanguageUtil.get(resourceBundle, "does-not-contain"),
				_TYPE_BOOLEAN, new String[] {_TYPE_TEXT, _TYPE_TEXT}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"is-empty", LanguageUtil.get(resourceBundle, "is-empty"),
				_TYPE_BOOLEAN, new String[] {_TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"is-empty", LanguageUtil.get(resourceBundle, "is-empty"),
				_TYPE_BOOLEAN, new String[] {_TYPE_TEXT}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"not-is-empty",
				LanguageUtil.get(resourceBundle, "is-not-empty"), _TYPE_BOOLEAN,
				new String[] {_TYPE_NUMBER}));

		addDDMExpressionFunctionMetadata(
			new DDMExpressionFunctionMetadata(
				"not-is-empty",
				LanguageUtil.get(resourceBundle, "is-not-empty"), _TYPE_BOOLEAN,
				new String[] {_TYPE_TEXT}));
	}

	public Map<String, List<DDMExpressionFunctionMetadata>>
		getDDMExpressionFunctionsMetadata() {

		return _ddmExpressionFunctionsMetadata;
	}

	public static class DDMExpressionFunctionMetadata {

		public DDMExpressionFunctionMetadata(
			String name, String label, String returnType,
			String[] parameterTypes) {

			_name = name;
			_label = label;
			_returnType = returnType;
			_parameterTypes = parameterTypes;
		}

		public String getLabel() {
			return _label;
		}

		public String getName() {
			return _name;
		}

		public String[] getParameterTypes() {
			return _parameterTypes;
		}

		public String getReturnType() {
			return _returnType;
		}

		private final String _label;
		private final String _name;
		private final String[] _parameterTypes;
		private final String _returnType;

	}

	protected void addDDMExpressionFunctionMetadata(
		DDMExpressionFunctionMetadata expressionFunctionMetadata) {

		String firstParameterType =
			expressionFunctionMetadata.getParameterTypes()[0];

		List<DDMExpressionFunctionMetadata> expressionFunctionMetadataList =
			_ddmExpressionFunctionsMetadata.get(firstParameterType);

		if (expressionFunctionMetadataList == null) {
			expressionFunctionMetadataList = new ArrayList<>();

			_ddmExpressionFunctionsMetadata.put(
				firstParameterType, expressionFunctionMetadataList);
		}

		expressionFunctionMetadataList.add(expressionFunctionMetadata);
	}

	private static final String _TYPE_BOOLEAN = "boolean";

	private static final String _TYPE_LIST = "list";

	private static final String _TYPE_NUMBER = "number";

	private static final String _TYPE_TEXT = "text";

	private static final String _TYPE_USER = "user";

	private final Map<String, List<DDMExpressionFunctionMetadata>>
		_ddmExpressionFunctionsMetadata = new HashMap<>();

}