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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceTestUtil {

	public static DDMFormInstance addDDMFormInstance(
			DDMForm ddmForm, Group group, DDMFormValues settingsDDMFormValues,
			long userId)
		throws Exception {

		return addDDMFormInstance(
			DDMStructureTestUtil.addStructure(
				group.getGroupId(), DDMFormInstance.class.getName(), ddmForm,
				LocaleUtil.US),
			group, settingsDDMFormValues, userId);
	}

	public static DDMFormInstance addDDMFormInstance(
			DDMForm ddmForm, Group group, long userId)
		throws Exception {

		return addDDMFormInstance(
			ddmForm, group, createSettingsDDMFormValues(), userId);
	}

	public static DDMFormInstance addDDMFormInstance(
			DDMStructure ddmStructure, Group group,
			DDMFormValues settingsDDMFormValues, long userId)
		throws Exception {

		return DDMFormInstanceLocalServiceUtil.addFormInstance(
			userId, group.getGroupId(), ddmStructure.getStructureId(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			settingsDDMFormValues, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMFormInstance addDDMFormInstance(Group group, long userId)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("text");

		return addDDMFormInstance(
			ddmForm, group, createSettingsDDMFormValues(), userId);
	}

	public static DDMFormValues createSettingsDDMFormValues() {
		DDMForm ddmForm = DDMFormFactory.create(DDMFormInstanceSettings.class);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"autosaveEnabled", "true"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromAddress", "from@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromName", "Joe Bloggs"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailSubject", "New Form Submission"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailToAddress", "to@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"published", "Joe Bloggs"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"redirectURL", "http://www.google.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireAuthentication", "false"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireCaptcha", "true"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"sendEmailNotification", "false"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"storageType", StorageType.DEFAULT.getValue()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"workflowDefinition", "[\"no-workflow\"]"));

		return ddmFormValues;
	}

	public static void deleteDDMFormInstance(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceLocalServiceUtil.deleteFormInstance(ddmFormInstance);
	}

	public static DDMFormInstance updateDDMFormInstance(
			long formInstanceId, DDMFormValues settingsDDMFormValues)
		throws PortalException {

		return DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstanceId, settingsDDMFormValues);
	}

}