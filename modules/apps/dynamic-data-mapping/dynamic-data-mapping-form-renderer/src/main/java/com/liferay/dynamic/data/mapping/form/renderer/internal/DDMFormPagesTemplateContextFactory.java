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

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDMFormPagesTemplateContextFactory {

	public DDMFormPagesTemplateContextFactory(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout,
		DDMFormRenderingContext ddmFormRenderingContext,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		JSONFactory jsonFactory) {

		_ddmForm = ddmForm;
		_ddmFormLayout = ddmFormLayout;
		_ddmFormRenderingContext = ddmFormRenderingContext;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_jsonFactory = jsonFactory;

		DDMFormValues ddmFormValues =
			ddmFormRenderingContext.getDDMFormValues();

		if ((ddmFormValues == null) ||
			ListUtil.isEmpty(ddmFormValues.getDDMFormFieldValues())) {

			DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
				new DefaultDDMFormValuesFactory(
					ddmForm, ddmFormRenderingContext.getLocale());

			ddmFormValues = defaultDDMFormValuesFactory.create();
		}

		_ddmFormValues = ddmFormValues;

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap(true);
		_locale = ddmFormRenderingContext.getLocale();
	}

	public List<Object> create() {
		_evaluate();

		return createPagesTemplateContext(
			_ddmFormLayout.getDDMFormLayoutPages());
	}

	public void setDDMFormEvaluator(DDMFormEvaluator ddmFormEvaluator) {
		_ddmFormEvaluator = ddmFormEvaluator;
	}

	public void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
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

		return HashMapBuilder.<String, Object>put(
			"fields",
			createFieldsTemplateContext(
				ddmFormLayoutColumn.getDDMFormFieldNames())
		).put(
			"size", ddmFormLayoutColumn.getSize()
		).build();
	}

	protected List<Object> createFieldsTemplateContext(
		List<String> ddmFormFieldNames) {

		List<Object> fieldsTemplateContext = new ArrayList<>();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			List<Object> fieldTemplateContexts = createFieldTemplateContext(
				ddmFormFieldName);

			if (ListUtil.isNotEmpty(fieldTemplateContexts)) {
				fieldsTemplateContext.addAll(fieldTemplateContexts);
			}
		}

		return fieldsTemplateContext;
	}

	protected List<Object> createFieldTemplateContext(String ddmFormFieldName) {
		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			new DDMFormFieldTemplateContextFactory(
				_ddmFormEvaluator, ddmFormFieldName, _ddmFormFieldsMap,
				_ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges(),
				_ddmFormFieldValuesMap.get(ddmFormFieldName),
				_ddmFormRenderingContext, _ddmStructureLayoutLocalService,
				_ddmStructureLocalService, _jsonFactory, _pageEnabled,
				_ddmFormLayout);

		ddmFormFieldTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormFieldTemplateContextFactory.create();
	}

	protected List<Object> createPagesTemplateContext(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		List<Object> pagesTemplateContext = new ArrayList<>();

		int i = 0;

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			pagesTemplateContext.add(
				createPageTemplateContext(ddmFormLayoutPage, i++));
		}

		return pagesTemplateContext;
	}

	protected Map<String, Object> createPageTemplateContext(
		DDMFormLayoutPage ddmFormLayoutPage, int pageIndex) {

		Map<String, Object> pageTemplateContext = new HashMap<>();

		LocalizedValue description = ddmFormLayoutPage.getDescription();

		pageTemplateContext.put("description", description.getString(_locale));

		_pageEnabled = isPageEnabled(pageIndex);

		pageTemplateContext.put("enabled", _pageEnabled);

		pageTemplateContext.put(
			"localizedDescription",
			getLocalizedValueMap(description, _ddmFormRenderingContext));

		LocalizedValue title = ddmFormLayoutPage.getTitle();

		pageTemplateContext.put(
			"localizedTitle",
			getLocalizedValueMap(title, _ddmFormRenderingContext));

		pageTemplateContext.put(
			"rows",
			createRowsTemplateContext(
				ddmFormLayoutPage.getDDMFormLayoutRows()));

		boolean showRequiredFieldsWarning = isShowRequiredFieldsWarning(
			ddmFormLayoutPage.getDDMFormLayoutRows());

		pageTemplateContext.put(
			"showRequiredFieldsWarning", showRequiredFieldsWarning);

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
		DDMFormLayoutRow ddmFormLayoutRow) {

		return HashMapBuilder.<String, Object>put(
			"columns",
			createColumnsTemplateContext(
				ddmFormLayoutRow.getDDMFormLayoutColumns())
		).build();
	}

	protected Map<String, String> getLocalizedValueMap(
		LocalizedValue localizedValue,
		DDMFormRenderingContext ddmFormRenderingContext) {

		Map<String, String> map = new HashMap<>();

		Map<Locale, String> values = localizedValue.getValues();

		for (Map.Entry<Locale, String> entry : values.entrySet()) {
			String languageId = LocaleUtil.toLanguageId(entry.getKey());

			String keyValue = getValue(
				ddmFormRenderingContext, entry.getValue());

			map.put(languageId, keyValue);
		}

		return map;
	}

	protected String getValue(
		DDMFormRenderingContext ddmFormRenderingContext, String value) {

		if (ddmFormRenderingContext.isViewMode()) {
			return HtmlUtil.extractText(value);
		}

		return value;
	}

	protected boolean isPageEnabled(int pageIndex) {
		Set<Integer> disabledPagesIndexes =
			_ddmFormEvaluatorEvaluateResponse.getDisabledPagesIndexes();

		if (disabledPagesIndexes.contains(pageIndex)) {
			return false;
		}

		return true;
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

	private void _evaluate() {
		try {
			DDMFormEvaluatorEvaluateRequest.Builder
				formEvaluatorEvaluateRequestBuilder =
					DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
						_ddmForm, _ddmFormValues, _locale);

			HttpServletRequest httpServletRequest =
				_ddmFormRenderingContext.getHttpServletRequest();

			formEvaluatorEvaluateRequestBuilder.withCompanyId(
				PortalUtil.getCompanyId(httpServletRequest));

			formEvaluatorEvaluateRequestBuilder.withDDMFormInstanceId(
				_ddmFormRenderingContext.getDDMFormInstanceId());
			formEvaluatorEvaluateRequestBuilder.withDDMFormLayout(
				_ddmFormLayout);
			formEvaluatorEvaluateRequestBuilder.withEditingFieldValue(
				Validator.isNotNull(
					httpServletRequest.getParameter("trigger")));
			formEvaluatorEvaluateRequestBuilder.withGroupId(
				_ddmFormRenderingContext.getGroupId());
			formEvaluatorEvaluateRequestBuilder.withUserId(
				PortalUtil.getUserId(httpServletRequest));
			formEvaluatorEvaluateRequestBuilder.withViewMode(_isViewMode());

			_ddmFormEvaluatorEvaluateResponse = _ddmFormEvaluator.evaluate(
				formEvaluatorEvaluateRequestBuilder.build());
		}
		catch (Exception exception) {
			_log.error("Unable to evaluate the form", exception);

			throw new IllegalStateException(
				"Unexpected error occurred during form evaluation", exception);
		}
	}

	private boolean _isViewMode() {
		Boolean viewMode = _ddmFormRenderingContext.getProperty("viewMode");

		if (viewMode != null) {
			return viewMode;
		}

		String portletNamespace =
			_ddmFormRenderingContext.getPortletNamespace();

		if ((portletNamespace != null) &&
			!StringUtil.equals(
				portletNamespace,
				PortalUtil.getPortletNamespace(
					DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormPagesTemplateContextFactory.class);

	private final DDMForm _ddmForm;
	private DDMFormEvaluator _ddmFormEvaluator;
	private DDMFormEvaluatorEvaluateResponse _ddmFormEvaluatorEvaluateResponse;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap;
	private final DDMFormLayout _ddmFormLayout;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final DDMFormValues _ddmFormValues;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private boolean _pageEnabled;

}