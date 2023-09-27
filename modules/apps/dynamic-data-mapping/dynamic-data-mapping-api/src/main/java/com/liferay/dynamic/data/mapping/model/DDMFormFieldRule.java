/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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