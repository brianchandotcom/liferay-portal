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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMFormPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=addFormInstanceRecord"
	},
	service = MVCResourceCommand.class
)
public class AddFormInstanceRecordMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected DDMFormFieldValue createDDMFormFieldValue(
		Map<String, DDMFormField> ddmFormFieldsMap, Locale siteDefaultLocale,
		JSONObject jsonObject) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(
			jsonObject.getString("instanceId", StringPool.BLANK));

		String name = jsonObject.getString("name", StringPool.BLANK);

		ddmFormFieldValue.setName(name);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(name);

		Value value = null;

		if (ddmFormField.isLocalizable()) {
			value = createLocalizedValue(
				siteDefaultLocale, jsonObject.getJSONObject("value"));
		}
		else {
			value = new UnlocalizedValue(
				jsonObject.getString("value", StringPool.BLANK));
		}

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected void createDDMFormFieldValues(
		DDMFormValues ddmFormValues, JSONObject jsonObject) {

		JSONArray jsonArray = jsonObject.getJSONArray("fieldValues");

		if (jsonArray == null) {
			return;
		}

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap(
			ddmFormValues);

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormValues.addDDMFormFieldValue(
				createDDMFormFieldValue(
					ddmFormFieldsMap, ddmFormValues.getDefaultLocale(),
					jsonArray.getJSONObject(i)));
		}
	}

	protected DDMFormValues createDDMFormValues(
			DDMFormInstance ddmFormInstance, ResourceRequest resourceRequest)
		throws Exception {

		String serializedDDMFormValues = ParamUtil.getString(
			resourceRequest, "serializedDDMFormValues");

		if (Validator.isNull(serializedDDMFormValues)) {
			return null;
		}

		DDMForm ddmForm = getDDMForm(ddmFormInstance);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		setDDMFormValuesLocales(
			_portal.getSiteDefaultLocale(ddmFormInstance.getGroupId()),
			ddmFormValues);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormValues);

		createDDMFormFieldValues(ddmFormValues, jsonObject);

		return ddmFormValues;
	}

	protected Value createLocalizedValue(
		Locale siteDefaultLocale, JSONObject jsonObject) {

		Value value = new LocalizedValue(siteDefaultLocale);

		String valueString = jsonObject.getString(
			LanguageUtil.getLanguageId(siteDefaultLocale), StringPool.BLANK);

		value.addString(siteDefaultLocale, valueString);

		return value;
	}

	protected ServiceContext createServiceContext(
			ResourceRequest resourceRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), resourceRequest);

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);
		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return serviceContext;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long formInstanceId = ParamUtil.getLong(
			resourceRequest, "formInstanceId");

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formInstanceId);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmFormInstance, resourceRequest);

		if (ddmFormValues == null) {
			return;
		}

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionLocalService.
				fetchLatestFormInstanceRecordVersion(
					themeDisplay.getUserId(), formInstanceId,
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT);

		ServiceContext serviceContext = createServiceContext(resourceRequest);

		if (ddmFormInstanceRecordVersion == null) {
			_ddmFormInstanceRecordService.addFormInstanceRecord(
				ddmFormInstance.getGroupId(), formInstanceId, ddmFormValues,
				serviceContext);
		}
		else {
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				ddmFormInstanceRecordVersion.getFormInstanceRecordId(), false,
				ddmFormValues, serviceContext);
		}
	}

	protected DDMForm getDDMForm(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	protected Map<String, DDMFormField> getDDMFormFieldsMap(
		DDMFormValues ddmFormValues) {

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		return ddmForm.getDDMFormFieldsMap(true);
	}

	protected void setDDMFormValuesLocales(
		Locale siteDefaultLocale, DDMFormValues ddmFormValues) {

		ddmFormValues.addAvailableLocale(siteDefaultLocale);
		ddmFormValues.setDefaultLocale(siteDefaultLocale);
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}