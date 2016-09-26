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
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureLayoutException;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuery;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQueryFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=saveRecordSet"
	},
	service = MVCResourceCommand.class
)
public class SaveRecordSetMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			resourceRequest);

		DDMStructure ddmStructure = saveDDMStructure(
			resourceRequest, settingsDDMFormValues);

		DDLRecordSet recordSet = saveRecordSet(
			resourceRequest, ddmStructure.getStructureId());

		updateRecordSetSettings(
			resourceRequest, recordSet, settingsDDMFormValues);
	}

	protected DDMForm getDDMForm(ResourceRequest resourceRequest)
		throws PortalException {

		try {
			String definition = ParamUtil.getString(
				resourceRequest, "definition");

			return ddmFormJSONDeserializer.deserialize(definition);
		}
		catch (PortalException pe) {
			throw new StructureDefinitionException(pe);
		}
	}

	protected DDMFormLayout getDDMFormLayout(ResourceRequest resourceRequest)
		throws PortalException {

		try {
			String layout = ParamUtil.getString(resourceRequest, "layout");

			return ddmFormLayoutJSONDeserializer.deserialize(layout);
		}
		catch (PortalException pe) {
			throw new StructureLayoutException(pe);
		}
	}

	protected Map<Locale, String> getLocalizedMap(Locale locale, String value) {
		Map<Locale, String> localizedMap = new HashMap<>();

		localizedMap.put(locale, value);

		return localizedMap;
	}

	protected DDMFormValues getSettingsDDMFormValues(
			ResourceRequest resourceRequest)
		throws PortalException {

		String serializedSettingsDDMFormValues = ParamUtil.getString(
			resourceRequest, "serializedSettingsDDMFormValues");

		DDMForm ddmForm = DDMFormFactory.create(DDLRecordSetSettings.class);

		DDMFormValues settingsDDMFormValues =
			ddmFormValuesJSONDeserializer.deserialize(
				ddmForm, serializedSettingsDDMFormValues);

		return settingsDDMFormValues;
	}

	protected String getStorageType(DDMFormValues ddmFormValues)
		throws PortalException {

		DDMFormValuesQuery ddmFormValuesQuery =
			ddmFormValuesQueryFactory.create(ddmFormValues, "/storageType");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Value value = ddmFormFieldValue.getValue();

		String storageType = value.getString(ddmFormValues.getDefaultLocale());

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

		return value.getString(ddmFormValues.getDefaultLocale());
	}

	protected DDMStructure saveDDMStructure(
			ResourceRequest resourceRequest,
			DDMFormValues settingsDDMFormValues)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(
			resourceRequest, "ddmStructureId");
		long groupId = ParamUtil.getLong(resourceRequest, "groupId");
		String structureKey = ParamUtil.getString(
			resourceRequest, "structureKey");
		String storageType = getStorageType(settingsDDMFormValues);
		String name = ParamUtil.getString(resourceRequest, "name");
		String description = ParamUtil.getString(
			resourceRequest, "description");
		DDMForm ddmForm = getDDMForm(resourceRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(resourceRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), resourceRequest);

		if (ddmStructureId != 0) {
			return ddmStructureService.updateStructure(
				ddmStructureId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
				getLocalizedMap(
					themeDisplay.getSiteDefaultLocale(), description),
				ddmForm, ddmFormLayout, serviceContext);
		}
		else {
			return ddmStructureService.addStructure(
				groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				PortalUtil.getClassNameId(DDLRecordSet.class), structureKey,
				getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
				getLocalizedMap(
					themeDisplay.getSiteDefaultLocale(), description),
				ddmForm, ddmFormLayout, storageType,
				DDMStructureConstants.TYPE_AUTO, serviceContext);
		}
	}

	protected DDLRecordSet saveRecordSet(
			ResourceRequest resourceRequest, long ddmStructureId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");
		String recordSetKey = ParamUtil.getString(
			resourceRequest, "recordSetKey");
		String name = ParamUtil.getString(resourceRequest, "name");
		String description = ParamUtil.getString(
			resourceRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), resourceRequest);

		if (recordSetId != 0) {
			return ddlRecordSetService.updateRecordSet(
				recordSetId, ddmStructureId,
				getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
				getLocalizedMap(
					themeDisplay.getSiteDefaultLocale(), description),
				DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, serviceContext);
		}
		else {
			return ddlRecordSetService.addRecordSet(
				groupId, ddmStructureId, recordSetKey,
				getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
				getLocalizedMap(
					themeDisplay.getSiteDefaultLocale(), description),
				DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
				DDLRecordSetConstants.SCOPE_FORMS, serviceContext);
		}
	}

	protected void updateRecordSetSettings(
			ResourceRequest resourceRequest, DDLRecordSet recordSet,
			DDMFormValues settingsDDMFormValues)
		throws PortalException {

		ddlRecordSetService.updateRecordSet(
			recordSet.getRecordSetId(), settingsDDMFormValues);

		updateWorkflowDefinitionLink(
			resourceRequest, recordSet, settingsDDMFormValues);
	}

	protected void updateWorkflowDefinitionLink(
			ResourceRequest resourceRequest, DDLRecordSet recordSet,
			DDMFormValues ddmFormValues)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		String workflowDefinition = getWorkflowDefinition(ddmFormValues);

		workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0,
			workflowDefinition);
	}

	@Reference
	protected DDLRecordSetService ddlRecordSetService;

	@Reference
	protected DDMFormJSONDeserializer ddmFormJSONDeserializer;

	@Reference
	protected DDMFormLayoutJSONDeserializer ddmFormLayoutJSONDeserializer;

	@Reference
	protected DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

	@Reference
	protected DDMFormValuesQueryFactory ddmFormValuesQueryFactory;

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

}