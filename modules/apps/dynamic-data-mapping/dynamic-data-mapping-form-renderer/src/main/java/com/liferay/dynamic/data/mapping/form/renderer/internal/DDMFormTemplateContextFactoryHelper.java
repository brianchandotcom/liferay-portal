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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 * @author Rafael Praxedes
 */
public class DDMFormTemplateContextFactoryHelper {

	public Set<String> getEvaluableDDMFormFieldNames(DDMForm ddmForm) {
		Set<String> evaluableDDMFormFieldNames = new HashSet<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldNames = ddmFormFieldsMap.keySet();

		evaluableDDMFormFieldNames.addAll(
			getReferencedFieldNamesByDDMFormRules(
				ddmForm.getDDMFormRules(), ddmFormFieldNames));

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			if (isDDMFormFieldEvaluable(ddmFormField)) {
				evaluableDDMFormFieldNames.add(ddmFormField.getName());
			}

			String visibilityExpression =
				ddmFormField.getVisibilityExpression();

			evaluableDDMFormFieldNames.addAll(
				getReferencedFieldNamesByExpression(
					visibilityExpression, ddmFormFieldNames));
		}

		return evaluableDDMFormFieldNames;
	}

	protected Set<String> getReferencedFieldNamesByDDMFormRules(
		List<DDMFormRule> ddmFormRules, Set<String> ddmFormFieldNames) {

		Set<String> referencedFieldNames = new HashSet<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			String condition = ddmFormRule.getCondition();

			referencedFieldNames.addAll(
				getReferencedFieldNamesByExpression(
					condition, ddmFormFieldNames));

			for (String action : ddmFormRule.getActions()) {
				referencedFieldNames.addAll(
					getReferencedFieldNamesByExpression(
						action, ddmFormFieldNames));
			}
		}

		return referencedFieldNames;
	}

	protected Set<String> getReferencedFieldNamesByExpression(
		String expression, Set<String> ddmFormFieldNames) {

		if (Validator.isNull(expression)) {
			return Collections.emptySet();
		}

		Set<String> referencedFieldNames = new HashSet<>();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			if (expression.contains(ddmFormFieldName)) {
				referencedFieldNames.add(ddmFormFieldName);
			}
		}

		return referencedFieldNames;
	}

	protected boolean isDDMFormFieldEvaluable(DDMFormField ddmFormField) {
		if (ddmFormField.isRequired()) {
			return true;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if ((ddmFormFieldValidation != null) &&
			Validator.isNotNull(ddmFormFieldValidation.getExpression())) {

			return true;
		}

		return false;
	}

}