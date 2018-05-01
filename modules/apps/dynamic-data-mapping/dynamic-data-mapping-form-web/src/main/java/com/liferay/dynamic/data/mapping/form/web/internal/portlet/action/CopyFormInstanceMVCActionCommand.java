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

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

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
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=copyFormInstance"
	},
	service = MVCActionCommand.class
)
public class CopyFormInstanceMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	protected DDMStructure copyFormInstanceDDMStructure(
			ActionRequest actionRequest, DDMFormInstance formInstance)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		return ddmStructureService.copyStructure(
			formInstance.getStructureId(), serviceContext);
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

		String storageType = saveFormInstanceMVCCommandHelper.getStorageType(
			settingsDDMFormValues);

		storageTypeDDMFormFieldValue.setValue(
			new UnlocalizedValue(storageType));
	}

	protected DDMFormValues createFormInstanceSettingsDDMFormValues(
			ActionRequest actionRequest, DDMFormInstance formInstance)
		throws Exception {

		DDMForm settingsDDMForm = DDMFormFactory.create(
			DDMFormInstanceSettings.class);

		DDMFormValues defaultSettingsDDMFormValues =
			ddmFormValuesFactory.create(actionRequest, settingsDDMForm);

		copySettingsStorageTypeValue(
			formInstance.getSettingsDDMFormValues(),
			defaultSettingsDDMFormValues);

		return defaultSettingsDDMFormValues;
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long formInstanceId = ParamUtil.getLong(
			actionRequest, "formInstanceId");

		DDMFormInstance formInstance = ddmFormInstanceService.getFormInstance(
			formInstanceId);

		DDMStructure ddmStructureCopy = copyFormInstanceDDMStructure(
			actionRequest, formInstance);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			ddmStructureCopy.getDefaultLanguageId());

		DDMFormInstance formInstanceCopy =
			saveFormInstanceMVCCommandHelper.addFormInstance(
				actionRequest, ddmStructureCopy.getStructureId(),
				getNameMap(formInstance, defaultLocale),
				formInstance.getDescriptionMap(),
				formInstance.getSettingsDDMFormValues());

		DDMFormValues settingsDDMFormValues =
			createFormInstanceSettingsDDMFormValues(
				actionRequest, formInstance);

		ddmFormInstanceService.updateFormInstance(
			formInstanceCopy.getFormInstanceId(), settingsDDMFormValues);
	}

	protected Map<Locale, String> getNameMap(
		DDMFormInstance formInstance, Locale defaultLocale) {

		Map<Locale, String> nameMap = formInstance.getNameMap();

		ResourceBundle resourceBundle = getResourceBundle(defaultLocale);

		String name = LanguageUtil.format(
			resourceBundle, "copy-of-x", nameMap.get(defaultLocale));

		nameMap.put(defaultLocale, name);

		return nameMap;
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
	protected DDMFormInstanceService ddmFormInstanceService;

	@Reference
	protected DDMFormValuesFactory ddmFormValuesFactory;

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected SaveFormInstanceMVCCommandHelper saveFormInstanceMVCCommandHelper;

}