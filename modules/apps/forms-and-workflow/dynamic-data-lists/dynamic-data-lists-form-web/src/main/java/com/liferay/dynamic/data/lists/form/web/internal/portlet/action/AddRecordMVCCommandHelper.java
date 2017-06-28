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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = AddRecordMVCCommandHelper.class)
public class AddRecordMVCCommandHelper {

	public void updateRequiredFieldsAccordingToVisibility(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		List<DDMFormField> requiredFields = getRequiredFields(ddmForm);

		if (requiredFields.isEmpty()) {
			return;
		}

		DDMFormEvaluationResult ddmFormEvaluationResult = evaluate(
			actionRequest, ddmForm, ddmFormValues, locale);

		DDMFormLayout ddmFormLayout = getDDMFormLayout(actionRequest);

		Set<String> fieldsFromDisabledPages = getFieldNamesFromDisabledPages(
			ddmFormEvaluationResult, ddmFormLayout);

		Set<String> invisibleFields = getInvisibleFields(
			ddmFormEvaluationResult);

		if (!invisibleFields.isEmpty()) {
			removeRequiredProperty(invisibleFields, requiredFields);
		}

		if (!fieldsFromDisabledPages.isEmpty()) {
			removeRequiredProperty(fieldsFromDisabledPages, requiredFields);
		}
	}

	protected DDMFormEvaluationResult evaluate(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, locale);

		ddmFormEvaluatorContext.addProperty(
			"groupId", ParamUtil.getLong(actionRequest, "groupId"));
		ddmFormEvaluatorContext.addProperty(
			"request", _portal.getHttpServletRequest(actionRequest));

		return _ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);
	}

	protected DDMFormLayout getDDMFormLayout(ActionRequest actionRequest)
		throws PortalException {

		if (_ddlRecordSetService == null) {
			return new DDMFormLayout();
		}

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			recordSet.getDDMStructureId());

		return ddmStructure.getDDMFormLayout();
	}

	protected Set<String> getFieldNamesFromDisabledPages(
		DDMFormEvaluationResult ddmFormEvaluationResult,
		DDMFormLayout ddmFormLayout) {

		HashSet<String> fieldNamesFromDisablePages = new HashSet<>();

		for (int index : ddmFormEvaluationResult.getDisabledPagesIndexes()) {
			fieldNamesFromDisablePages.addAll(
				getFieldNamesFromPage(
					ddmFormLayout.getDDMFormLayoutPage(index)));
		}

		return fieldNamesFromDisablePages;
	}

	protected Set<String> getFieldNamesFromPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayoutPage.getDDMFormLayoutRows();

		HashSet<String> fieldNames = new HashSet<>();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				fieldNames.addAll(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		}

		return fieldNames;
	}

	protected Set<String> getInvisibleFields(
		DDMFormEvaluationResult ddmFormEvaluationResult) {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResults();

		Stream<DDMFormFieldEvaluationResult> stream =
			ddmFormFieldEvaluationResults.stream();

		stream = stream.filter(result -> !result.isVisible());

		Stream<String> fieldNameStream = stream.map(result -> result.getName());

		return fieldNameStream.collect(Collectors.toSet());
	}

	protected List<DDMFormField> getRequiredFields(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Collection<DDMFormField> ddmFormFields = ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream = stream.filter(ddmFormField -> ddmFormField.isRequired());

		return stream.collect(Collectors.toList());
	}

	protected void removeRequiredProperty(DDMFormField ddmFormField) {
		ddmFormField.setRequired(false);
	}

	protected void removeRequiredProperty(
		Set<String> invisibleFields, List<DDMFormField> requiredFields) {

		Stream<DDMFormField> stream = requiredFields.stream();

		stream = stream.filter(
			field -> invisibleFields.contains(field.getName()));

		stream.forEach(this::removeRequiredProperty);
	}

	@Reference
	private DDLRecordSetService _ddlRecordSetService;

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}