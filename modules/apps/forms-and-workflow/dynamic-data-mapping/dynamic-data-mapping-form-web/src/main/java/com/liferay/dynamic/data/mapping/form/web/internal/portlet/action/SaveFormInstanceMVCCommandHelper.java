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

import com.liferay.dynamic.data.mapping.exception.FormInstanceSettingsRedirectURLException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureLayoutException;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializerRequest;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuery;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQueryFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.util.DDMFormInstanceFieldSettingsValidator;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = SaveFormInstanceMVCCommandHelper.class)
public class SaveFormInstanceMVCCommandHelper {

	public DDMFormInstance saveFormInstance(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return saveFormInstance(portletRequest, portletResponse, false);
	}

	public DDMFormInstance saveFormInstance(
			PortletRequest portletRequest, PortletResponse portletResponse,
			boolean validateDDMFormFieldSettings)
		throws Exception {

		long formInstanceId = ParamUtil.getLong(
			portletRequest, "formInstanceId");

		if (formInstanceId == 0) {
			return addFormInstance(
				portletRequest, portletResponse, validateDDMFormFieldSettings);
		}
		else {
			return updateFormInstance(
				portletRequest, portletResponse, validateDDMFormFieldSettings);
		}
	}

	protected DDMStructure addDDMStructure(
			PortletRequest portletRequest, DDMFormValues settingsDDMFormValues,
			boolean validateDDMFormFieldSettings)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String structureKey = ParamUtil.getString(
			portletRequest, "structureKey");
		String storageType = getStorageType(settingsDDMFormValues);
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		DDMForm ddmForm = getDDMForm(portletRequest, serviceContext);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(portletRequest);

		Map<Locale, String> nameMap = getLocalizedMap(
			name, ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale());
		Map<Locale, String> descriptionMap = getLocalizedMap(
			description, ddmForm.getAvailableLocales(),
			ddmForm.getDefaultLocale());

		if (validateDDMFormFieldSettings) {
			formInstanceFieldSettingsValidator.validate(
				portletRequest, ddmForm);
		}

		return ddmStructureService.addStructure(
			groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_portal.getClassNameId(DDMFormInstance.class), structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType,
			DDMStructureConstants.TYPE_AUTO, serviceContext);
	}

	protected DDMFormInstance addFormInstance(
			PortletRequest portletRequest, long ddmStructureId,
			Locale defaultLocale, Set<Locale> availableLocales,
			DDMFormValues settingsDDMFormValues)
		throws Exception {

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		return addFormInstance(
			portletRequest, ddmStructureId,
			getLocalizedMap(name, availableLocales, defaultLocale),
			getLocalizedMap(description, availableLocales, defaultLocale),
			settingsDDMFormValues);
	}

	protected DDMFormInstance addFormInstance(
			PortletRequest portletRequest, long ddmStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMFormValues settingsDDMFormValues)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstance.class.getName(), portletRequest);

		if (ParamUtil.getBoolean(portletRequest, "autoSave")) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_DRAFT);
		}

		validateRedirectURL(settingsDDMFormValues);

		return formInstanceService.addFormInstance(
			groupId, ddmStructureId, nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	protected DDMFormInstance addFormInstance(
			PortletRequest portletRequest, PortletResponse portletResponse,
			boolean validateFormFieldsSettings)
		throws Exception {

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			portletRequest);

		DDMStructure ddmStructure = addDDMStructure(
			portletRequest, settingsDDMFormValues, validateFormFieldsSettings);

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return addFormInstance(
			portletRequest, ddmStructure.getStructureId(),
			ddmForm.getDefaultLocale(), ddmForm.getAvailableLocales(),
			settingsDDMFormValues);
	}

	protected DDMForm getDDMForm(
			PortletRequest portletRequest, ServiceContext serviceContext)
		throws PortalException {

		try {
			String serializedFormBuilderContext = ParamUtil.getString(
				portletRequest, "serializedFormBuilderContext");

			return ddmFormBuilderContextToDDMForm.deserialize(
				DDMFormContextDeserializerRequest.with(
					serializedFormBuilderContext));
		}
		catch (PortalException pe) {
			throw new StructureDefinitionException(pe);
		}
	}

	protected DDMFormLayout getDDMFormLayout(PortletRequest portletRequest)
		throws PortalException {

		try {
			String serializedFormBuilderContext = ParamUtil.getString(
				portletRequest, "serializedFormBuilderContext");

			return ddmFormBuilderContextToDDMFormLayout.deserialize(
				DDMFormContextDeserializerRequest.with(
					serializedFormBuilderContext));
		}
		catch (PortalException pe) {
			throw new StructureLayoutException(pe);
		}
	}

	protected Map<Locale, String> getLocalizedMap(
			String value, Set<Locale> availableLocales, Locale defaultLocale)
		throws PortalException {

		Map<Locale, String> localizedMap = new HashMap<>();

		JSONObject jsonObject = jsonFactory.createJSONObject(value);

		String defaultValueString = jsonObject.getString(
			LocaleUtil.toLanguageId(defaultLocale));

		for (Locale availableLocale : availableLocales) {
			String valueString = jsonObject.getString(
				LocaleUtil.toLanguageId(availableLocale), defaultValueString);

			localizedMap.put(availableLocale, valueString);
		}

		return localizedMap;
	}

	protected DDMFormValues getSettingsDDMFormValues(
			PortletRequest portletRequest)
		throws PortalException {

		String settingsContext = ParamUtil.getString(
			portletRequest, "serializedSettingsContext");

		DDMFormValues settingsDDMFormValues =
			ddmFormTemplateContextToDDMFormValues.deserialize(
				DDMFormContextDeserializerRequest.with(
					DDMFormFactory.create(DDMFormInstanceSettings.class),
					settingsContext));

		return settingsDDMFormValues;
	}

	protected String getSingleValue(String value) {
		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value);

			if (jsonArray.length() > 0) {
				return jsonArray.getString(0);
			}

			return StringPool.BLANK;
		}
		catch (Exception e) {
			return value;
		}
	}

	protected String getStorageType(DDMFormValues ddmFormValues)
		throws PortalException {

		DDMFormValuesQuery ddmFormValuesQuery =
			ddmFormValuesQueryFactory.create(ddmFormValues, "/storageType");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Value value = ddmFormFieldValue.getValue();

		String storageType = getSingleValue(
			value.getString(ddmFormValues.getDefaultLocale()));

		if (Validator.isNull(storageType)) {
			storageType = StorageType.JSON.toString();
		}

		return storageType;
	}

	protected String getWorkflowDefinition(DDMFormValues ddmFormValues)
		throws PortalException {

		DDMFormValuesQuery ddmFormValuesQuery =
			ddmFormValuesQueryFactory.create(
				ddmFormValues, "/workflowDefinition");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Value value = ddmFormFieldValue.getValue();

		return getSingleValue(
			value.getString(ddmFormValues.getDefaultLocale()));
	}

	protected DDMStructure updateDDMStructure(
			PortletRequest portletRequest, boolean validateDDMFormFieldSettings)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		long ddmStructureId = ParamUtil.getLong(
			portletRequest, "ddmStructureId");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		DDMForm ddmForm = getDDMForm(portletRequest, serviceContext);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(portletRequest);

		Map<Locale, String> nameMap = getLocalizedMap(
			name, ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale());
		Map<Locale, String> descriptionMap = getLocalizedMap(
			description, ddmForm.getAvailableLocales(),
			ddmForm.getDefaultLocale());

		if (validateDDMFormFieldSettings) {
			formInstanceFieldSettingsValidator.validate(
				portletRequest, ddmForm);
		}

		return ddmStructureService.updateStructure(
			ddmStructureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, serviceContext);
	}

	protected DDMFormInstance updateFormInstance(
			PortletRequest portletRequest, long ddmStructureId,
			Set<Locale> availableLocales, Locale defaultLocale,
			DDMFormValues settingsDDMFormValues)
		throws Exception {

		long formInstanceId = ParamUtil.getLong(
			portletRequest, "formInstanceId");

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstance.class.getName(), portletRequest);

		if (ParamUtil.getBoolean(portletRequest, "autoSave")) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		validateRedirectURL(settingsDDMFormValues);

		return formInstanceService.updateFormInstance(
			formInstanceId, ddmStructureId,
			getLocalizedMap(name, availableLocales, defaultLocale),
			getLocalizedMap(description, availableLocales, defaultLocale),
			settingsDDMFormValues, serviceContext);
	}

	protected DDMFormInstance updateFormInstance(
			PortletRequest portletRequest, PortletResponse portletResponse,
			boolean validateDDMFormFieldSettings)
		throws Exception {

		DDMStructure ddmStructure = updateDDMStructure(
			portletRequest, validateDDMFormFieldSettings);

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			portletRequest);

		return updateFormInstance(
			portletRequest, ddmStructure.getStructureId(),
			ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale(),
			settingsDDMFormValues);
	}

	protected void validateRedirectURL(DDMFormValues settingsDDMFormValues)
		throws PortalException {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			settingsDDMFormValues.getDDMFormFieldValuesMap();

		if (!ddmFormFieldValuesMap.containsKey("redirectURL")) {
			return;
		}

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"redirectURL");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		for (Locale availableLocale : value.getAvailableLocales()) {
			String valueString = value.getString(availableLocale);

			if (Validator.isNotNull(valueString)) {
				String escapedRedirect = _portal.escapeRedirect(valueString);

				if (Validator.isNull(escapedRedirect)) {
					throw new FormInstanceSettingsRedirectURLException();
				}
			}
		}
	}

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=form)"
	)
	protected DDMFormContextDeserializer<DDMForm>
		ddmFormBuilderContextToDDMForm;

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=formLayout)"
	)
	protected DDMFormContextDeserializer<DDMFormLayout>
		ddmFormBuilderContextToDDMFormLayout;

	@Reference(
		target = "(dynamic.data.mapping.form.builder.context.deserializer.type=formValues)"
	)
	protected DDMFormContextDeserializer<DDMFormValues>
		ddmFormTemplateContextToDDMFormValues;

	@Reference
	protected DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

	@Reference
	protected DDMFormValuesQueryFactory ddmFormValuesQueryFactory;

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected volatile DDMFormInstanceFieldSettingsValidator
		formInstanceFieldSettingsValidator;

	@Reference
	protected DDMFormInstanceService formInstanceService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected volatile WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

	@Reference
	private Portal _portal;

}