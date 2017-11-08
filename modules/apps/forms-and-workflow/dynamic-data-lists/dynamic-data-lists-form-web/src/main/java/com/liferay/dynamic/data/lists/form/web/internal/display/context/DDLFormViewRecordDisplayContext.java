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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.form.web.internal.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDLFormViewRecordDisplayContext {

	public DDLFormViewRecordDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DDLRecordLocalService ddlRecordLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger) {

		_httpServletResponse = httpServletResponse;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;

		_ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			httpServletRequest);
	}

	public String getDDMFormHTML(RenderRequest renderRequest)
		throws PortalException {

		DDLRecord record = getRecord();

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDLRecordSetVersion recordSetVersion = recordSet.getRecordSetVersion(
			record.getRecordSetVersion());

		DDMStructureVersion ddmStructureVersion =
			recordSetVersion.getDDMStructureVersion();

		DDMForm currentDDMForm = ddmStructureVersion.getDDMForm();

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			renderRequest, currentDDMForm);

		ddmFormValues = _ddmFormValuesMerger.merge(
			record.getDDMFormValues(), ddmFormValues);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		updateDDMFormFields(currentDDMForm, ddmStructure.getDDMForm());

		DDMFormLayout ddmFormLayout = ddmStructureVersion.getDDMFormLayout();

		return _ddmFormRenderer.render(
			currentDDMForm, ddmFormLayout, ddmFormRenderingContext);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext() {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			_ddlFormAdminRequestHelper.getRequest());
		ddmFormRenderingContext.setHttpServletResponse(_httpServletResponse);
		ddmFormRenderingContext.setLocale(
			_ddlFormAdminRequestHelper.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			PortalUtil.getPortletNamespace(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN));
		ddmFormRenderingContext.setReadOnly(true);

		return ddmFormRenderingContext;
	}

	protected DDLRecord getRecord() throws PortalException {
		HttpServletRequest httpServletRequest =
			_ddlFormAdminRequestHelper.getRequest();

		long recordId = ParamUtil.getLong(httpServletRequest, "recordId");

		if (recordId > 0) {
			return _ddlRecordLocalService.fetchRecord(recordId);
		}

		Object record = httpServletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

		if (record instanceof DDLFormRecord) {
			DDLFormRecord formRecord = (DDLFormRecord)record;

			return formRecord.getDDLRecord();
		}
		else {
			return (DDLRecord)record;
		}
	}

	protected boolean isDDMFormFieldRemoved(
		Map<String, DDMFormField> latestDDMFormFieldMap, String fieldName) {

		if (latestDDMFormFieldMap.containsKey(fieldName)) {
			return false;
		}

		return true;
	}

	protected void setDDMFormFieldRemovedLabel(DDMFormField ddmFormField) {
		Locale locale = _ddlFormAdminRequestHelper.getLocale();

		LocalizedValue label = ddmFormField.getLabel();

		String labelString = label.getString(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		label.addString(
			locale,
			LanguageUtil.format(
				resourceBundle, "x-removed", labelString, false));
	}

	protected void updateDDMFormField(
		Map<String, DDMFormField> latestDDMFormFieldMap,
		DDMFormField ddmFormField) {

		boolean removed = isDDMFormFieldRemoved(
			latestDDMFormFieldMap, ddmFormField.getName());

		if (removed) {
			setDDMFormFieldRemovedLabel(ddmFormField);
		}

		ddmFormField.setReadOnly(true);

		// Nested fields

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			updateDDMFormField(latestDDMFormFieldMap, nestedDDMFormField);
		}
	}

	protected void updateDDMFormFields(
		DDMForm currentDDMForm, DDMForm latestDDMForm) {

		Map<String, DDMFormField> latestDDMFormFieldMap =
			latestDDMForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : currentDDMForm.getDDMFormFields()) {
			updateDDMFormField(latestDDMFormFieldMap, ddmFormField);
		}
	}

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final HttpServletResponse _httpServletResponse;

}