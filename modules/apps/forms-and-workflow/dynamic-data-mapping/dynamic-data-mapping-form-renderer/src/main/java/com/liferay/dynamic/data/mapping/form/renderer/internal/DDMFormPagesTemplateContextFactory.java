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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormPagesTemplateContextFactory {

	public DDMFormPagesTemplateContextFactory(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout,
		DDMFormRenderingContext ddmFormRenderingContext) {

		_ddmForm = ddmForm;
		_ddmFormLayout = ddmFormLayout;
		_ddmFormRenderingContext = ddmFormRenderingContext;

		DDMFormValues ddmFormValues =
			ddmFormRenderingContext.getDDMFormValues();

		if (ddmFormValues == null) {
			DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
				new DefaultDDMFormValuesFactory(
					ddmForm, ddmFormRenderingContext.getLocale());

			ddmFormValues = defaultDDMFormValuesFactory.create();
		}
		else {
			removeStaleDDMFormFieldValues(
				ddmForm.getDDMFormFieldsMap(true),
				ddmFormValues.getDDMFormFieldValues());
		}

		_ddmFormValues = ddmFormValues;

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();
		_locale = ddmFormRenderingContext.getLocale();
	}

	public List<Object> create() {
		return createPagesTemplateContext(
			_ddmFormLayout.getDDMFormLayoutPages());
	}

	protected boolean containsRequiredField(List<String> ddmFormFieldNames) {
		for (String ddmFormFieldName : ddmFormFieldNames) {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

			if (ddmFormField.isRequired()) {
				return true;
			}
		}

		return false;
	}

	protected List<Object> createColumnsTemplateContext(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		List<Object> columnsTemplateContext = new ArrayList<>();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			columnsTemplateContext.add(
				createColumnTemplateContext(ddmFormLayoutColumn));
		}

		return columnsTemplateContext;
	}

	protected Map<String, Object> createColumnTemplateContext(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		Map<String, Object> columnTemplateContext = new HashMap<>();

		columnTemplateContext.put(
			"fields",
			createFieldsTemplateContext(
				ddmFormLayoutColumn.getDDMFormFieldNames()));
		columnTemplateContext.put("size", ddmFormLayoutColumn.getSize());

		return columnTemplateContext;
	}

	protected Map<String, DDMFormFieldEvaluationResult>
		createDDMFormFieldEvaluationResultsMap() {

		try {
			DDMFormEvaluationResult ddmFormEvaluationResult =
				_ddmFormEvaluator.evaluate(_ddmForm, _ddmFormValues, _locale);

			return ddmFormEvaluationResult.
				getDDMFormFieldEvaluationResultsMap();
		}
		catch (DDMFormEvaluationException ddmfee) {
			_log.error("Unable to evaluate the form", ddmfee);
		}

		return new HashMap<>();
	}

	protected List<Object> createFieldsTemplateContext(
		List<String> ddmFormFieldNames) {

		List<Object> fieldsTemplateContext = new ArrayList<>();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			fieldsTemplateContext.addAll(
				createFieldTemplateContext(ddmFormFieldName));
		}

		return fieldsTemplateContext;
	}

	protected List<Object> createFieldTemplateContext(String ddmFormFieldName) {
		if (_ddmFormFieldEvaluationResultMap == null) {
			_ddmFormFieldEvaluationResultMap =
				createDDMFormFieldEvaluationResultsMap();
		}

		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			new DDMFormFieldTemplateContextFactory(
				_ddmFormFieldsMap,
				_ddmFormFieldEvaluationResultMap.get(ddmFormFieldName),
				_ddmFormFieldValuesMap.get(ddmFormFieldName),
				_ddmFormRenderingContext);

		ddmFormFieldTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormFieldTemplateContextFactory.create();
	}

	protected List<Object> createPagesTemplateContext(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		List<Object> pagesTemplateContext = new ArrayList<>();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			pagesTemplateContext.add(
				createPageTemplateContext(ddmFormLayoutPage));
		}

		return pagesTemplateContext;
	}

	protected Map<String, Object> createPageTemplateContext(
		DDMFormLayoutPage ddmFormLayoutPage) {

		Map<String, Object> pageTemplateContext = new HashMap<>();

		LocalizedValue description = ddmFormLayoutPage.getDescription();

		pageTemplateContext.put("description", description.getString(_locale));

		pageTemplateContext.put(
			"rows",
			createRowsTemplateContext(
				ddmFormLayoutPage.getDDMFormLayoutRows()));

		boolean showRequiredFieldsWarning = isShowRequiredFieldsWarning(
			ddmFormLayoutPage.getDDMFormLayoutRows());

		pageTemplateContext.put(
			"showRequiredFieldsWarning", showRequiredFieldsWarning);

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		pageTemplateContext.put("title", title.getString(_locale));

		return pageTemplateContext;
	}

	protected List<Object> createRowsTemplateContext(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		List<Object> rowsTemplateContext = new ArrayList<>();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			rowsTemplateContext.add(createRowTemplateContext(ddmFormLayoutRow));
		}

		return rowsTemplateContext;
	}

	protected Map<String, Object> createRowTemplateContext(
		DDMFormLayoutRow ddFormLayoutRow) {

		Map<String, Object> rowTemplateContext = new HashMap<>();

		rowTemplateContext.put(
			"columns",
			createColumnsTemplateContext(
				ddFormLayoutRow.getDDMFormLayoutColumns()));

		return rowTemplateContext;
	}

	protected boolean isShowRequiredFieldsWarning(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		if (!_ddmFormRenderingContext.isShowRequiredFieldsWarning()) {
			return false;
		}

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				if (containsRequiredField(
						ddmFormLayoutColumn.getDDMFormFieldNames())) {

					return true;
				}
			}
		}

		return false;
	}

	protected void removeStaleDDMFormFieldValues(
		Map<String, DDMFormField> ddmFormFieldsMap,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Iterator<DDMFormFieldValue> iterator = ddmFormFieldValues.iterator();

		while (iterator.hasNext()) {
			DDMFormFieldValue ddmFormFieldValue = iterator.next();

			if (!ddmFormFieldsMap.containsKey(ddmFormFieldValue.getName())) {
				iterator.remove();
			}

			removeStaleDDMFormFieldValues(
				ddmFormFieldsMap,
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}
	}

	protected void setDDMFormEvaluator(DDMFormEvaluator ddmFormEvaluator) {
		_ddmFormEvaluator = ddmFormEvaluator;
	}

	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormPagesTemplateContextFactory.class);

	private final DDMForm _ddmForm;
	private DDMFormEvaluator _ddmFormEvaluator;
	private Map<String, DDMFormFieldEvaluationResult>
		_ddmFormFieldEvaluationResultMap;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap;
	private final DDMFormLayout _ddmFormLayout;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final DDMFormValues _ddmFormValues;
	private final Locale _locale;

}