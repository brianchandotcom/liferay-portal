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

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

/**
 * @author     Leonardo Barros
 * @deprecated As of Judson (7.1.x), replaced by {@link DDMFormRule}
 */
@Deprecated
public class DDMFormFieldRule implements Serializable {

	public DDMFormFieldRule(DDMFormFieldRule ddmFormFieldRule) {
		_expression = ddmFormFieldRule._expression;
		_ddmFormFieldRuleType = ddmFormFieldRule._ddmFormFieldRuleType;
	}

	public DDMFormFieldRule(
		String expression, DDMFormFieldRuleType ddmFormFieldRuleType) {

		_expression = expression;
		_ddmFormFieldRuleType = ddmFormFieldRuleType;
	}

	public DDMFormFieldRuleType getDDMFormFieldRuleType() {
		return _ddmFormFieldRuleType;
	}

	public String getExpression() {
		return _expression;
	}

	public void setDDMFormFieldRuleType(
		DDMFormFieldRuleType ddmFormFieldRuleType) {

		_ddmFormFieldRuleType = ddmFormFieldRuleType;
	}

	public void setExpression(String expression) {
		_expression = expression;
	}

	private DDMFormFieldRuleType _ddmFormFieldRuleType;
	private String _expression;

}