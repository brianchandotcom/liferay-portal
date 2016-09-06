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
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

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
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMStructureLocalService ddmStructureLocalService) {

		_httpServletResponse = httpServletResponse;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmStructureLocalService = ddmStructureLocalService;

		_ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			httpServletRequest);
	}

	public String getDDMFormHTML(RenderRequest renderRequest)
		throws PortalException {

		DDLRecord record = getRecord();

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			renderRequest, ddmForm);

		ddmFormValues = _ddmFormValuesMerger.merge(
			record.getDDMFormValues(), ddmFormValues);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldReadOnly(ddmFormField);
		}

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
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

	protected DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStucture != null) {
			return _ddmStucture;
		}

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return null;
		}

		_ddmStucture = _ddmStructureLocalService.getStructure(
			recordSet.getDDMStructureId());

		return _ddmStucture;
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

	protected DDLRecordSet getRecordSet() throws PortalException {
		DDLRecord record = getRecord();

		if (record == null) {
			return null;
		}

		return record.getRecordSet();
	}

	protected void setDDMFormFieldReadOnly(DDMFormField ddmFormField) {
		ddmFormField.setReadOnly(true);

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldReadOnly(nestedDDMFormField);
		}
	}

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructure _ddmStucture;
	private final HttpServletResponse _httpServletResponse;

}