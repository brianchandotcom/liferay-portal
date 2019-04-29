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

package com.liferay.dynamic.data.mapping.helper;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Lino Alves
 */
public class DDMFormInstanceTestHelper {

	public static DDMFormValues createFormInstanceSettingsDDMFormValues() {
		DDMForm formInstanceSettingsDDMForm = DDMFormFactory.create(
			DDMFormInstanceSettings.class);

		DDMFormValues formInstanceSettingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(
				formInstanceSettingsDDMForm);

		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"autosaveEnabled", "true"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromAddress", "from@liferay.com"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromName", "Joe Bloggs"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailSubject", "New Form Submission"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailToAddress", "to@liferay.com"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"published", "Joe Bloggs"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"redirectURL", "http://www.google.com"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireAuthentication", "false"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireCaptcha", "true"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"sendEmailNotification", "false"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"storageType", "json"));
		formInstanceSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"workflowDefinition", "Single Approver@1"));

		return formInstanceSettingsDDMFormValues;
	}

	public DDMFormInstanceTestHelper(Group group) {
		_group = group;
	}

	public DDMFormInstance addDDMFormInstance(DDMForm ddmForm)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDMFormInstance.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		return addDDMFormInstance(ddmStructure);
	}

	public DDMFormInstance addDDMFormInstance(DDMStructure ddmStructure)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return DDMFormInstanceLocalServiceUtil.addFormInstance(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ddmStructure.getStructureId(), nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	public DDMFormInstance addDDMFormInstance(
			DDMStructure ddmStructure, FileEntry fileEntry)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		DDMForm fileEntryDDMForm = new DDMForm();

		Set<Locale> availableLocales = new LinkedHashSet<>();

		availableLocales.add(LocaleUtil.US);

		fileEntryDDMForm.setAvailableLocales(availableLocales);

		fileEntryDDMForm.setDefaultLocale(LocaleUtil.US);

		DDMFormTestUtil.addDocumentLibraryDDMFormField(
			fileEntryDDMForm, "DocumentsAndMedia9t17");

		DDMFormValues ddmFormValues = new DDMFormValues(fileEntryDDMForm);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		JSONObject jsonObject = JSONUtil.put(
			"classPK", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", "document"
		).put(
			"uuid", fileEntry.getUuid()
		);

		List<DDMFormField> ddmFormFields = fileEntryDDMForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
					ddmFormField.getName(), jsonObject.toString()));
		}

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(
			fileEntryDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return DDMFormInstanceLocalServiceUtil.addFormInstance(
			TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
			descriptionMap, fileEntryDDMForm, ddmFormLayout, ddmFormValues,
			serviceContext);
	}

	public DDMFormInstance updateFormInstance(
			long formInstanceId, DDMFormValues settingsDDMFormValues)
		throws PortalException {

		return DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstanceId, settingsDDMFormValues);
	}

	public DDMFormInstance updateFormInstance(
			long formInstanceId, DDMStructure ddmStructure)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.getFormInstance(formInstanceId);

		return DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstanceId, ddmStructure.getStructureId(), nameMap, null,
			formInstance.getSettingsDDMFormValues(), serviceContext);
	}

	private final Group _group;

}