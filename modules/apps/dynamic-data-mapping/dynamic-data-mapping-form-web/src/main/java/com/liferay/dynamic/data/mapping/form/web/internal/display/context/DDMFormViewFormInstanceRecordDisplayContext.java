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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormAdminRequestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDMFormViewFormInstanceRecordDisplayContext {

	public DDMFormViewFormInstanceRecordDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger) {

		_httpServletResponse = httpServletResponse;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;

		_ddmFormAdminRequestHelper = new DDMFormAdminRequestHelper(
			httpServletRequest);
	}

	public Map<String, Object> getDDMFormReactData(RenderRequest renderRequest)
		throws Exception {

		return getDDMFormReactData(renderRequest, true);
	}

	public Map<String, Object> getDDMFormReactData(
			RenderRequest renderRequest, boolean readOnly)
		throws Exception {

		DDMFormInstanceRecord formInstanceRecord = getDDMFormInstanceRecord();

		DDMFormInstance formInstance = formInstanceRecord.getFormInstance();

		DDMFormInstanceVersion formInstanceVersion =
			formInstance.getFormInstanceVersion(
				formInstanceRecord.getFormInstanceVersion());

		DDMStructureVersion structureVersion =
			formInstanceVersion.getStructureVersion();

		DDMFormRenderingContext formRenderingContext =
			createDDMFormRenderingContext(
				structureVersion.getDDMForm(), readOnly);

		DDMFormValues formValues = getDDMFormValues(
			renderRequest, formInstanceRecord, structureVersion);

		formRenderingContext.setContainerId(
			"ddmForm".concat(StringUtil.randomString()));
		formRenderingContext.setDDMFormValues(formValues);
		formRenderingContext.setLocale(formValues.getDefaultLocale());

		DDMFormInstanceVersion latestApprovedFormInstanceVersion =
			_ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				formInstance.getFormInstanceId(),
				WorkflowConstants.STATUS_APPROVED);

		DDMStructureVersion latestApprovedStructureVersion =
			latestApprovedFormInstanceVersion.getStructureVersion();

		updateDDMFormFields(
			structureVersion.getDDMForm(),
			latestApprovedStructureVersion.getDDMForm());

		DDMFormLayout formLayout = structureVersion.getDDMFormLayout();

		return _ddmFormRenderer.getReactData(
			structureVersion.getDDMForm(), formLayout, formRenderingContext);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm, boolean readOnly) {

		DDMFormRenderingContext formRenderingContext =
			new DDMFormRenderingContext();

		String redirectURL = ParamUtil.getString(
			_ddmFormAdminRequestHelper.getRequest(), "redirect");

		Locale locale = ddmForm.getDefaultLocale();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		if (availableLocales.contains(_ddmFormAdminRequestHelper.getLocale())) {
			locale = _ddmFormAdminRequestHelper.getLocale();
		}

		if (Validator.isNotNull(redirectURL)) {
			formRenderingContext.setCancelLabel(
				LanguageUtil.get(locale, "cancel"));
		}

		formRenderingContext.setHttpServletRequest(
			_ddmFormAdminRequestHelper.getRequest());
		formRenderingContext.setHttpServletResponse(_httpServletResponse);
		formRenderingContext.setLocale(locale);
		formRenderingContext.setPortletNamespace(
			PortalUtil.getPortletNamespace(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN));
		formRenderingContext.setReadOnly(readOnly);

		if (Validator.isNotNull(redirectURL)) {
			formRenderingContext.setRedirectURL(redirectURL);
		}
		else {
			formRenderingContext.setShowCancelButton(false);
		}

		formRenderingContext.setViewMode(true);

		return formRenderingContext;
	}

	protected DDMFormInstanceRecord getDDMFormInstanceRecord()
		throws PortalException {

		HttpServletRequest httpServletRequest =
			_ddmFormAdminRequestHelper.getRequest();

		long formInstanceRecordId = ParamUtil.getLong(
			httpServletRequest, "formInstanceRecordId");

		if (formInstanceRecordId > 0) {
			return _ddmFormInstanceRecordLocalService.fetchFormInstanceRecord(
				formInstanceRecordId);
		}

		return (DDMFormInstanceRecord)httpServletRequest.getAttribute(
			DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_RECORD);
	}

	protected DDMFormValues getDDMFormValues(
			RenderRequest renderRequest,
			DDMFormInstanceRecord formInstanceRecord,
			DDMStructureVersion structureVersion)
		throws PortalException {

		DDMFormValues formValues = formInstanceRecord.getDDMFormValues();

		DDMFormValues mergedFormValues = _ddmFormValuesMerger.merge(
			formValues,
			_ddmFormValuesFactory.create(
				renderRequest, structureVersion.getDDMForm()));

		mergedFormValues.setAvailableLocales(formValues.getAvailableLocales());
		mergedFormValues.setDefaultLocale(formValues.getDefaultLocale());

		return mergedFormValues;
	}

	protected boolean isDDMFormFieldRemoved(
		Map<String, DDMFormField> latestFormFieldMap, String fieldName) {

		if (latestFormFieldMap.containsKey(fieldName)) {
			return false;
		}

		return true;
	}

	protected void setDDMFormFieldRemovedLabel(DDMFormField formField) {
		Locale locale = _ddmFormAdminRequestHelper.getLocale();

		LocalizedValue label = formField.getLabel();

		String labelString = label.getString(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		label.addString(
			locale,
			LanguageUtil.format(
				resourceBundle, "x-removed", labelString, false));
	}

	protected void updateDDMFormField(
		Map<String, DDMFormField> latestFormFieldMap, DDMFormField formField) {

		boolean removed = isDDMFormFieldRemoved(
			latestFormFieldMap, formField.getName());

		if (removed) {
			setDDMFormFieldRemovedLabel(formField);
		}

		formField.setReadOnly(true);

		// Nested fields

		for (DDMFormField nestedFormField :
				formField.getNestedDDMFormFields()) {

			updateDDMFormField(latestFormFieldMap, nestedFormField);
		}
	}

	protected void updateDDMFormFields(
		DDMForm currentForm, DDMForm latestForm) {

		if (Objects.equals(currentForm, latestForm)) {
			return;
		}

		Map<String, DDMFormField> latestDDMFormFieldMap =
			latestForm.getDDMFormFieldsMap(true);

		for (DDMFormField formField : currentForm.getDDMFormFields()) {
			updateDDMFormField(latestDDMFormFieldMap, formField);
		}
	}

	private final DDMFormAdminRequestHelper _ddmFormAdminRequestHelper;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final HttpServletResponse _httpServletResponse;

}