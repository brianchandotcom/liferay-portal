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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.CalculateDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class CalculateDDMFormRuleActionSerializer
	implements DDMFormRuleActionSerializer {

	public CalculateDDMFormRuleActionSerializer(
		CalculateDDMFormRuleAction calculateDDMFormRuleAction) {

		_calculateDDMFormRuleAction = calculateDDMFormRuleAction;
	}

	@Override
	public String serialize(
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		DDMForm ddmForm = ddmFormRuleSerializerContext.getAttribute("form");

		Map<String, DDMFormField> ddmFormFieldMap = ddmForm.getDDMFormFieldsMap(
			true);

		String expression = removeBrackets(
			_calculateDDMFormRuleAction.getExpression());

		Set<String> keySet = ddmFormFieldMap.keySet();

		Stream<String> ddmFormFieldStream = keySet.stream();

		ddmFormFieldStream = ddmFormFieldStream.filter(
			ddmFormField -> expression.contains(ddmFormField));

		Set<String> ddmFormFields = ddmFormFieldStream.collect(
			Collectors.toSet());

		String newExpression = expression;

		int start = Integer.MAX_VALUE;
		int end = Integer.MIN_VALUE;

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < expression.length(); i++) {
			char token = expression.charAt(i);

			sb.append(token);

			String compareStr = sb.toString();

			boolean match = matchAnyField(compareStr, ddmFormFields);

			if (match) {
				if (start == Integer.MAX_VALUE) {
					start = i;
				}

				if (i == (expression.length() - 1)) {
					end = expression.length();
				}
			}
			else {
				end = i;
				sb = new StringBuffer();

				if (end > start) {
					newExpression = replace(
						expression, newExpression, start, end);
				}

				start = Integer.MAX_VALUE;
				end = Integer.MIN_VALUE;
			}
		}

		if (end > start) {
			newExpression = replace(expression, newExpression, start, end);
		}

		return String.format(
			_FUNCTION_CALL_BINARY_EXPRESSION_FORMAT, "calculate",
			_calculateDDMFormRuleAction.getTarget(), newExpression);
	}

	protected boolean matchAnyField(
		String compareStr, Set<String> ddmFormFields) {

		for (String ddmFormField : ddmFormFields) {
			if (ddmFormField.contains(compareStr)) {
				return true;
			}
		}

		return false;
	}

	protected String removeBrackets(String expression) {
		return StringUtil.removeChars(
			expression, CharPool.OPEN_BRACKET, CharPool.CLOSE_BRACKET);
	}

	protected String replace(
		String expression, String newExpression, int start, int end) {

		String matchFound = expression.substring(start, end);

		String matchReplacement = String.format(
			_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getValue", matchFound);

		return StringUtil.replaceFirst(
			newExpression, matchFound, matchReplacement, start);
	}

	private static final String _FUNCTION_CALL_BINARY_EXPRESSION_FORMAT =
		"%s('%s', %s)";

	private static final String _FUNCTION_CALL_UNARY_EXPRESSION_FORMAT =
		"%s('%s')";

	private final CalculateDDMFormRuleAction _calculateDDMFormRuleAction;

}