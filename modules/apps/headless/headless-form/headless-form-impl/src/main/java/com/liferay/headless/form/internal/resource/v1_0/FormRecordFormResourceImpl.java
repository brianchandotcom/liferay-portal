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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormRecordForm;
import com.liferay.headless.form.internal.dto.v1_0.util.FormRecordUtil;
import com.liferay.headless.form.resource.v1_0.FormRecordFormResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form-record-form.properties",
	scope = ServiceScope.PROTOTYPE, service = FormRecordFormResource.class
)
public class FormRecordFormResourceImpl extends BaseFormRecordFormResourceImpl {

	@Override
	public FormRecord postFormFormRecord(
			Long formId, FormRecordForm formRecordForm)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formId);

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmFormInstance, formRecordForm.getFieldValues(), locale);

		ServiceContext formServiceContext = _getServiceContext(
			formRecordForm.getDraft());

		return FormRecordUtil.toFormRecord(
			_ddmFormInstanceRecordService.addFormInstanceRecord(
				ddmFormInstance.getGroupId(),
				ddmFormInstance.getFormInstanceId(), ddmFormValues,
				formServiceContext),
			locale, _portal, _userLocalService);
	}

	@Override
	public FormRecord putFormRecord(
			Long formRecordId, FormRecordForm formRecordForm)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordService.getFormInstanceRecord(formRecordId);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmFormInstance, formRecordForm.getFieldValues(), locale);

		ServiceContext formServiceContext = _getServiceContext(
			formRecordForm.getDraft());

		return FormRecordUtil.toFormRecord(
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				formRecordId, false, ddmFormValues, formServiceContext),
			locale, _portal, _userLocalService);
	}

	private DDMFormValues _getDDMFormValues(
			DDMFormInstance ddmFormInstance, String fieldValues, Locale locale)
		throws Exception {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);
		ddmFormValues.setDefaultLocale(locale);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(fieldValues);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			String name = jsonObject.getString("name");

			ddmFormFieldValue.setName(name);
			DDMFormField ddmFormField = ddmFormFieldsMap.get(name);

			Value value = _EMPTY_VALUE;

			if (ddmFormField != null) {
				Object rawValue = jsonObject.get("value");

				value = Optional.ofNullable(
					rawValue
				).map(
					Object::toString
				).map(
					stringValue -> _getValue(ddmFormField, locale, stringValue)
				).orElse(
					_EMPTY_VALUE
				);
			}

			ddmFormFieldValue.setValue(value);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	private ServiceContext _getServiceContext(boolean draft)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), _httpServletRequest);

		if (draft) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_DRAFT);
			serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		return serviceContext;
	}

	private Value _getValue(
		DDMFormField ddmFormField, Locale locale, String stringValue) {

		if (ddmFormField.isLocalizable()) {
			Value value = new LocalizedValue();

			value.addString(locale, stringValue);

			return value;
		}

		return _EMPTY_VALUE;
	}

	private static final Value _EMPTY_VALUE = new UnlocalizedValue(
		(String)null);

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}