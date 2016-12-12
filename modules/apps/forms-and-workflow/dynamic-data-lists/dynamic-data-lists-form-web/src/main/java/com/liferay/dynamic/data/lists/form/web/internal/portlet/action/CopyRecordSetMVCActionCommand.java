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
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=copyRecordSet"
	},
	service = MVCActionCommand.class
)
public class CopyRecordSetMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	protected DDMStructure copyRecordSetDDMStructure(
			ActionRequest actionRequest, DDLRecordSet recordSet)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		return ddmStructureService.copyStructure(
			recordSet.getDDMStructureId(), serviceContext);
	}

	protected void copySettingsStorageTypeValue(
			DDMFormValues settingsDDMFormValues,
			DDMFormValues defaultSettingsDDMFormValues)
		throws Exception {

		DDMFormFieldValue storageTypeDDMFormFieldValue =
			getStorageTypeDDMFormFieldValue(defaultSettingsDDMFormValues);

		if (storageTypeDDMFormFieldValue == null) {
			return;
		}

		String storageType = saveRecordSetMVCCommandHelper.getStorageType(
			settingsDDMFormValues);

		storageTypeDDMFormFieldValue.setValue(
			new UnlocalizedValue(storageType));
	}

	protected DDMFormValues createRecordSetSettingsDDMFormValues(
			ActionRequest actionRequest, DDLRecordSet recordSet)
		throws Exception {

		DDMForm settingsDDMForm = DDMFormFactory.create(
			DDLRecordSetSettings.class);

		DDMFormValues defaultSettingsDDMFormValues =
			ddmFormValuesFactory.create(actionRequest, settingsDDMForm);

		copySettingsStorageTypeValue(
			recordSet.getSettingsDDMFormValues(), defaultSettingsDDMFormValues);

		return defaultSettingsDDMFormValues;
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		DDLRecordSet recordSet = ddlRecordSetService.getRecordSet(recordSetId);

		DDMStructure ddmStructureCopy = copyRecordSetDDMStructure(
			actionRequest, recordSet);

		DDLRecordSet recordSetCopy = saveRecordSetMVCCommandHelper.addRecordSet(
			actionRequest, ddmStructureCopy.getStructureId(),
			getNameMap(recordSet, themeDisplay.getSiteDefaultLocale()),
			getDescriptionMap(recordSet, themeDisplay.getSiteDefaultLocale()));

		DDMFormValues settingsDDMFormValues =
			createRecordSetSettingsDDMFormValues(actionRequest, recordSet);

		ddlRecordSetService.updateRecordSet(
			recordSetCopy.getRecordSetId(), settingsDDMFormValues);
	}

	protected Map<Locale, String> getDescriptionMap(
		DDLRecordSet recordSet, Locale locale) {

		return saveRecordSetMVCCommandHelper.getLocalizedMap(
			locale, recordSet.getDescription(locale, true));
	}

	protected Map<Locale, String> getNameMap(
		DDLRecordSet recordSet, Locale locale) {

		ResourceBundle resourceBundle = getResourceBundle(locale);

		String name = LanguageUtil.format(
			resourceBundle, "copy-of-x", recordSet.getName(locale, true));

		return saveRecordSetMVCCommandHelper.getLocalizedMap(locale, name);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle("content.Language", locale, clazz);
	}

	protected DDMFormFieldValue getStorageTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues) {

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			if (Objects.equals(ddmFormFieldValue.getName(), "storageType")) {
				return ddmFormFieldValue;
			}
		}

		return null;
	}

	@Reference
	protected DDLRecordSetService ddlRecordSetService;

	@Reference
	protected DDMFormValuesFactory ddmFormValuesFactory;

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected SaveRecordSetMVCCommandHelper saveRecordSetMVCCommandHelper;

}