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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.rules.DDMFormRuleEvaluatorHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pablo Carvalho
 */
@Component(immediate = true, service = DDMFormEvaluator.class)
public class DDMFormEvaluatorImpl implements DDMFormEvaluator {

	@Override
	public DDMFormEvaluationResult evaluate(
			DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale)
		throws DDMFormEvaluationException {

		try {
			DDMFormRuleEvaluatorHelper ddmFormRuleEvaluatorHelper =
				new DDMFormRuleEvaluatorHelper(
					_ddmDataProviderConsumerTracker,
					_ddmDataProviderInstanceService, _ddmExpressionFactory,
					ddmForm, ddmFormValues, _ddmFormValuesJSONDeserializer,
					_jsonFactory, locale);

			List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
				ddmFormRuleEvaluatorHelper.evaluate();

			validateRequired(ddmForm, ddmFormFieldEvaluationResults, locale);

			DDMFormEvaluationResult ddmFormEvaluationResult =
				new DDMFormEvaluationResult();

			ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
				ddmFormFieldEvaluationResults);

			return ddmFormEvaluationResult;
		}
		catch (PortalException pe) {
			throw new DDMFormEvaluationException(pe);
		}
	}

	protected boolean isDDMFormFieldValueEmpty(
		DDMFormField ddmFormField,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		Object value = ddmFormFieldEvaluationResult.getValue();

		if (value == null) {
			return true;
		}

		String valueString = value.toString();

		if (valueString.isEmpty()) {
			return true;
		}

		String dataType = ddmFormField.getDataType();

		if (Objects.equals(dataType, "boolean") &&
			Objects.equals(valueString, "false")) {

			return true;
		}

		return false;
	}

	protected void validateRequired(
		DDMForm ddmForm,
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults,
		Locale locale) {

		Map<String, DDMFormField> map = ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			DDMFormField ddmFormField = map.get(
				ddmFormFieldEvaluationResult.getName());

			if (ddmFormField.isRequired() &&
				ddmFormFieldEvaluationResult.isVisible() &&
				isDDMFormFieldValueEmpty(
					ddmFormField, ddmFormFieldEvaluationResult)) {

				ddmFormFieldEvaluationResult.setErrorMessage(
					LanguageUtil.get(locale, "this-field-is-required"));

				ddmFormFieldEvaluationResult.setValid(false);
			}
			else if (!ddmFormField.isRequired() &&
					 isDDMFormFieldValueEmpty(
						 ddmFormField, ddmFormFieldEvaluationResult)) {

				ddmFormFieldEvaluationResult.setErrorMessage("");
				ddmFormFieldEvaluationResult.setValid(true);
			}
		}
	}

	@Reference
	private DDMDataProviderConsumerTracker _ddmDataProviderConsumerTracker;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

}