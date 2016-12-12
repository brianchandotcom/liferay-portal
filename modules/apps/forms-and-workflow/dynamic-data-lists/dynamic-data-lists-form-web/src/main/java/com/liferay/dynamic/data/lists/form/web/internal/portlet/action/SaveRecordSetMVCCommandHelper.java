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

import com.liferay.dynamic.data.lists.form.web.internal.converter.DDLFormRulesToDDMFormRulesConverter;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
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
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = SaveRecordSetMVCCommandHelper.class)
public class SaveRecordSetMVCCommandHelper {

	public DDLRecordSet saveRecordSet(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long recordSetId = ParamUtil.getLong(portletRequest, "recordSetId");

		if (recordSetId == 0) {
			return addRecordSet(portletRequest, portletResponse);
		}
		else {
			return updateRecordSet(portletRequest, portletResponse);
		}
	}

	protected DDMStructure addDDMStructure(
			PortletRequest portletRequest, DDMFormValues settingsDDMFormValues)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String structureKey = ParamUtil.getString(
			portletRequest, "structureKey");
		String storageType = getStorageType(settingsDDMFormValues);
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		DDMForm ddmForm = getDDMForm(portletRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(portletRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		return ddmStructureService.addStructure(
			groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DDLRecordSet.class), structureKey,
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), description),
			ddmForm, ddmFormLayout, storageType,
			DDMStructureConstants.TYPE_AUTO, serviceContext);
	}

	protected DDLRecordSet addRecordSet(
			PortletRequest portletRequest, long ddmStructureId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		return addRecordSet(
			portletRequest, ddmStructureId,
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), description));
	}

	protected DDLRecordSet addRecordSet(
			PortletRequest portletRequest, long ddmStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String recordSetKey = ParamUtil.getString(
			portletRequest, "recordSetKey");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), portletRequest);

		return ddlRecordSetService.addRecordSet(
			groupId, ddmStructureId, recordSetKey, nameMap, descriptionMap,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_FORMS, serviceContext);
	}

	protected DDLRecordSet addRecordSet(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			portletRequest);

		DDMStructure ddmStructure = addDDMStructure(
			portletRequest, settingsDDMFormValues);

		DDLRecordSet recordSet = addRecordSet(
			portletRequest, ddmStructure.getStructureId());

		updateRecordSetSettings(
			portletRequest, recordSet, settingsDDMFormValues);

		return recordSet;
	}

	protected DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException {

		try {
			String definition = ParamUtil.getString(
				portletRequest, "definition");

			DDMForm ddmForm = ddmFormJSONDeserializer.deserialize(definition);

			List<DDMFormRule> ddmFormRules = getDDMFormRules(portletRequest);

			ddmForm.setDDMFormRules(ddmFormRules);

			return ddmForm;
		}
		catch (PortalException pe) {
			throw new StructureDefinitionException(pe);
		}
	}

	protected DDMFormLayout getDDMFormLayout(PortletRequest portletRequest)
		throws PortalException {

		try {
			String layout = ParamUtil.getString(portletRequest, "layout");

			return ddmFormLayoutJSONDeserializer.deserialize(layout);
		}
		catch (PortalException pe) {
			throw new StructureLayoutException(pe);
		}
	}

	protected List<DDMFormRule> getDDMFormRules(PortletRequest portletRequest)
		throws PortalException {

		String rules = ParamUtil.getString(portletRequest, "rules");

		if (Validator.isNull(rules) || Objects.equals("[]", rules)) {
			return Collections.emptyList();
		}

		JSONDeserializer<DDLFormRule[]> jsonDeserializer =
			jsonFactory.createJSONDeserializer();

		DDLFormRule[] ddlFormRules = jsonDeserializer.deserialize(
			rules, DDLFormRule[].class);

		return ddlFormRulesToDDMFormRulesConverter.convert(
			ListUtil.toList(ddlFormRules));
	}

	protected Map<Locale, String> getLocalizedMap(Locale locale, String value) {
		Map<Locale, String> localizedMap = new HashMap<>();

		localizedMap.put(locale, value);

		return localizedMap;
	}

	protected DDMFormValues getSettingsDDMFormValues(
			PortletRequest portletRequest)
		throws PortalException {

		String serializedSettingsDDMFormValues = ParamUtil.getString(
			portletRequest, "serializedSettingsDDMFormValues");

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

	protected DDMStructure updateDDMStructure(PortletRequest portletRequest)
		throws Exception {

		long ddmStructureId = ParamUtil.getLong(
			portletRequest, "ddmStructureId");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		DDMForm ddmForm = getDDMForm(portletRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(portletRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		return ddmStructureService.updateStructure(
			ddmStructureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			getLocalizedMap(ddmForm.getDefaultLocale(), name),
			getLocalizedMap(ddmForm.getDefaultLocale(), description), ddmForm,
			ddmFormLayout, serviceContext);
	}

	protected DDLRecordSet updateRecordSet(
			PortletRequest portletRequest, long ddmStructureId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(portletRequest, "recordSetId");

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), portletRequest);

		return ddlRecordSetService.updateRecordSet(
			recordSetId, ddmStructureId,
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), description),
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, serviceContext);
	}

	protected DDLRecordSet updateRecordSet(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		DDMStructure ddmStructure = updateDDMStructure(portletRequest);

		DDLRecordSet recordSet = updateRecordSet(
			portletRequest, ddmStructure.getStructureId());

		DDMFormValues settingsDDMFormValues = getSettingsDDMFormValues(
			portletRequest);

		updateRecordSetSettings(
			portletRequest, recordSet, settingsDDMFormValues);

		return recordSet;
	}

	protected void updateRecordSetSettings(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDMFormValues settingsDDMFormValues)
		throws PortalException {

		ddlRecordSetService.updateRecordSet(
			recordSet.getRecordSetId(), settingsDDMFormValues);

		updateWorkflowDefinitionLink(
			portletRequest, recordSet, settingsDDMFormValues);
	}

	protected void updateWorkflowDefinitionLink(
			PortletRequest portletRequest, DDLRecordSet recordSet,
			DDMFormValues ddmFormValues)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		String workflowDefinition = getWorkflowDefinition(ddmFormValues);

		workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(), groupId,
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0,
			workflowDefinition);
	}

	@Reference
	protected DDLFormRulesToDDMFormRulesConverter
		ddlFormRulesToDDMFormRulesConverter;

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
	protected JSONFactory jsonFactory;

	@Reference
	protected volatile WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

}