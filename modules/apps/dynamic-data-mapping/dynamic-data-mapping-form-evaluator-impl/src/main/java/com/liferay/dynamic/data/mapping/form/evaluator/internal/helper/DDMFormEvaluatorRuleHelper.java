/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.helper;

import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionObserver;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorRuleHelper {

	public DDMFormEvaluatorRuleHelper(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormEvaluatorExpressionObserver ddmFormEvaluatorExpressionObserver) {

		_ddmFormFieldsMap = ddmFormFieldsMap;
		_ddmFormEvaluatorExpressionObserver =
			ddmFormEvaluatorExpressionObserver;
	}

	public void checkFieldAffectedByAction(DDMFormRule ddmFormRule) {
		for (DDMFormField ddmFormField : _ddmFormFieldsMap.values()) {
			checkFieldAffectedByAction(ddmFormRule, ddmFormField);
		}
	}

	protected void checkFieldAffectedByAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		_checkFieldAffectedBySetReadOnlyAction(ddmFormRule, ddmFormField);
		_checkFieldAffectedBySetRequiredAction(ddmFormRule, ddmFormField);
		_checkFieldAffectedBySetVisibleAction(ddmFormRule, ddmFormField);
	}

	private void _checkFieldAffectedBySetReadOnlyAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (_containsAction(
				ddmFormRule, "setEnabled", ddmFormField.getName(),
				!ddmFormField.isReadOnly())) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "readOnly",
					!ddmFormField.isReadOnly());

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	private void _checkFieldAffectedBySetRequiredAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (_containsAction(
				ddmFormRule, "setRequired", ddmFormField.getName(),
				ddmFormField.isRequired())) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "required",
					!ddmFormField.isRequired());

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	private void _checkFieldAffectedBySetVisibleAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (_containsAction(
				ddmFormRule, "setVisible", ddmFormField.getName(), true)) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "visible", false);

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	private boolean _containsAction(
		DDMFormRule ddmFormRule, String functionName, String ddmFormFieldName,
		boolean defaultValue) {

		String setBooleanPropertyAction = String.format(
			"%s('%s', %s)", functionName, ddmFormFieldName, defaultValue);

		for (String action : ddmFormRule.getActions()) {
			if (Objects.equals(setBooleanPropertyAction, action)) {
				return true;
			}
		}

		return false;
	}

	private final DDMFormEvaluatorExpressionObserver
		_ddmFormEvaluatorExpressionObserver;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;

}