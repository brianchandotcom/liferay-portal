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

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM,
		"mvc.command.name=addRecord"
	},
	service = MVCResourceCommand.class
)
public class AddRecordMVCResourceCommand extends BaseMVCResourceCommand {

	protected DDMFormFieldValue createDDMFormFieldValue(
		DDMForm ddmForm, JSONObject jsonObject) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			Boolean.TRUE);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(
			jsonObject.getString("instanceId", StringPool.BLANK));

		String name = jsonObject.getString("name", StringPool.BLANK);

		ddmFormFieldValue.setName(name);

		DDMFormField ddmFormField = ddmFormFields.get(name);

		Value value = null;

		if (ddmFormField.isLocalizable()) {
			value = createLocalizedValue(
				ddmForm, jsonObject.getJSONObject("value"));
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

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormValues.addDDMFormFieldValue(
				createDDMFormFieldValue(ddmForm, jsonArray.getJSONObject(i)));
		}
	}

	protected DDMFormValues createDDMFormValues(
			DDLRecordSet recordSet, ResourceRequest resourceRequest)
		throws Exception {

		String serializedDDMFormValues = ParamUtil.getString(
			resourceRequest, "serializedDDMFormValues");

		if (Validator.isNull(serializedDDMFormValues)) {
			return null;
		}

		DDMForm ddmForm = getDDMForm(recordSet);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormValues);

		setLocales(ddmForm, jsonObject);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		createDDMFormFieldValues(ddmFormValues, jsonObject);

		return ddmFormValues;
	}

	protected Value createLocalizedValue(
		DDMForm ddmForm, JSONObject jsonObject) {

		Locale defaultLocale = ddmForm.getDefaultLocale();

		Value value = new LocalizedValue(defaultLocale);

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		for (Locale availableLocale : availableLocales) {
			String languageId = LanguageUtil.getLanguageId(availableLocale);

			String ddmFormFieldValue = jsonObject.getString(
				languageId, StringPool.BLANK);

			value.addString(availableLocale, ddmFormFieldValue);
		}

		return value;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		DDMFormValues ddmFormValues = createDDMFormValues(
			recordSet, resourceRequest);

		if (ddmFormValues == null) {
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), resourceRequest);

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);
		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DDLRecordVersion recordVersion =
			_ddlRecordVersionLocalService.fetchRecordVersion(
				themeDisplay.getUserId(), recordSetId, recordSet.getVersion(),
				WorkflowConstants.STATUS_DRAFT);

		if (recordVersion == null) {
			_ddlRecordService.addRecord(
				recordSet.getGroupId(), recordSetId,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
				serviceContext);
		}
		else {
			long recordId = recordVersion.getRecordId();

			_ddlRecordService.updateRecord(
				recordId, false, DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
				ddmFormValues, serviceContext);
		}
	}

	protected DDMForm getDDMForm(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getDDMForm();
	}

	protected void setLocales(DDMForm ddmForm, JSONObject jsonObject) {
		if (!jsonObject.has("defaultLanguageId") ||
			!jsonObject.has("availableLanguageIds")) {

			return;
		}

		String defaultLanguageIdString = jsonObject.getString(
			"defaultLanguageId");

		ddmForm.setDefaultLocale(
			LocaleUtil.fromLanguageId(defaultLanguageIdString));

		JSONArray availableLanguageIdsJSONArray = jsonObject.getJSONArray(
			"availableLanguageIds");

		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < availableLanguageIdsJSONArray.length(); i++) {
			availableLocales.add(
				LocaleUtil.fromLanguageId(
					availableLanguageIdsJSONArray.getString(i)));
		}

		ddmForm.setAvailableLocales(availableLocales);
	}

	@Reference
	private DDLRecordService _ddlRecordService;

	@Reference
	private DDLRecordSetService _ddlRecordSetService;

	@Reference
	private DDLRecordVersionLocalService _ddlRecordVersionLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}