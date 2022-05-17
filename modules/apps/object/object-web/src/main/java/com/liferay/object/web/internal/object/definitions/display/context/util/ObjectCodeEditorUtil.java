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

package com.liferay.object.web.internal.object.definitions.display.context.util;

import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(immediate = true, service = {})
public class ObjectCodeEditorUtil {

	public static List<Map<String, Object>> getCodeEditorElements(
		boolean includeDDMExpressionBuilderElements, Locale locale,
		long objectDefinitionId) {

		List<Map<String, Object>> codeEditorElements = new ArrayList<>();

		codeEditorElements.add(
			_createCodeEditorElement(
				ListUtil.toList(
					_objectFieldLocalService.getObjectFields(
						objectDefinitionId),
					objectField -> HashMapBuilder.put(
						"content", objectField.getName()
					).put(
						"label", objectField.getLabel(locale)
					).put(
						"tooltip", StringPool.BLANK
					).build()),
				"fields", locale));

		if (includeDDMExpressionBuilderElements) {
			Collections.addAll(
				codeEditorElements,
				_createCodeEditorElement(
					DDMExpressionOperator.getItems(locale), "operators",
					locale),
				_createCodeEditorElement(
					DDMExpressionFunction.getItems(locale), "functions",
					locale));
		}

		return codeEditorElements;
	}

	private static Map<String, Object> _createCodeEditorElement(
		List<HashMap<String, String>> items, String key, Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"items", items
		).put(
			"label", LanguageUtil.get(locale, key)
		).build();
	}

	@Reference(unbind = "-")
	private void _setObjectFieldLocalService(
		ObjectFieldLocalService objectFieldLocalService) {

		_objectFieldLocalService = objectFieldLocalService;
	}

	private static ObjectFieldLocalService _objectFieldLocalService;

	private enum DDMExpressionFunction {

		COMPARE_DATES("compareDates(field_name, parameter)", "compare-dates"),
		CONCAT("concat(parameter1, parameter2, parameterN)", "concat"),
		CONDITION("condition(condition, parameter1, parameter2)", "condition"),
		CONTAINS("contains(field_name, parameter)", "contains"),
		DOES_NOT_CONTAIN(
			"NOT(contains(field_name, parameter))", "does-not-contain"),
		FUTURE_DATES("futureDates(field_name, parameter)", "future-dates"),
		IS_A_URL("isURL(field_name)", "is-a-url"),
		IS_AN_EMAIL("isEmailAddress(field_name)", "is-an-email"),
		IS_DECIMAL("isDecimal(parameter)", "is-decimal"),
		IS_EMPTY("isEmpty(parameter)", "is-empty"),
		IS_EQUAL_TO("field_name == parameter", "is-equal-to"),
		IS_GREATER_THAN("field_name > parameter", "is-greater-than"),
		IS_GREATER_THAN_OR_EQUAL_TO(
			"field_name >= parameter", "is-greater-than-or-equal-to"),
		IS_INTEGER("isInteger(parameter)", "is-integer"),
		IS_LESS_THAN("field_name < parameter", "is-less-than"),
		IS_LESS_THAN_OR_EQUAL_TO(
			"field_name <= parameter", "is-less-than-or-equal-to"),
		IS_NOT_EQUAL_TO("field_name != parameter", "is-not-equal-to"),
		MATCH("match(field_name, parameter)", "match"),
		PAST_DATES("pastDates(field_name, parameter)", "past-dates"),
		RANGE(
			"futureDates(field_name, parameter) AND pastDates(" +
				"field_name, parameter)",
			"range"),
		SUM("sum(parameter1, parameter2, parameterN)", "sum");

		public static List<HashMap<String, String>> getItems(Locale locale) {
			List<HashMap<String, String>> values = new ArrayList<>();

			for (DDMExpressionFunction ddmExpressionFunction : values()) {
				values.add(
					HashMapBuilder.put(
						"content", ddmExpressionFunction._content
					).put(
						"label",
						LanguageUtil.get(locale, ddmExpressionFunction._key)
					).put(
						"tooltip", StringPool.BLANK
					).build());
			}

			return values;
		}

		private DDMExpressionFunction(String content, String key) {
			_content = content;
			_key = key;
		}

		private String _content;
		private String _key;

	}

	private enum DDMExpressionOperator {

		AND("AND", "and"), DIVIDED_BY("field_name / field_name2", "divided-by"),
		MINUS("field_name - field_name2", "minus"), OR("OR", "or"),
		PLUS("field_name + field_name2", "plus"),
		TIMES("field_name * field_name2", "times");

		public static List<HashMap<String, String>> getItems(Locale locale) {
			List<HashMap<String, String>> values = new ArrayList<>();

			for (DDMExpressionOperator ddmExpressionOperator : values()) {
				values.add(
					HashMapBuilder.put(
						"content", ddmExpressionOperator._content
					).put(
						"label",
						LanguageUtil.get(locale, ddmExpressionOperator._key)
					).put(
						"tooltip", StringPool.BLANK
					).build());
			}

			return values;
		}

		private DDMExpressionOperator(String content, String key) {
			_content = content;
			_key = key;
		}

		private String _content;
		private String _key;

	}

}