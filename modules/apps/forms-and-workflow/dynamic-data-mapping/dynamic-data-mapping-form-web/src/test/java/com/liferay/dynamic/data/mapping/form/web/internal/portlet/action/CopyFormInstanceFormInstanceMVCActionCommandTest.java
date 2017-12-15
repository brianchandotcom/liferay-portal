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

import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class CopyFormInstanceFormInstanceMVCActionCommandTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpCopyFormInstanceMVCActionCommand();
		setUpLanguageUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateFormInstanceSettingsDDMFormValues() throws Exception {
		DDMForm expectedFormInstanceSettingsDDMForm = DDMFormFactory.create(
			DDMFormInstanceSettings.class);

		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		DDMFormValues ddmFormValues = createDDMFormValues(
			expectedFormInstanceSettingsDDMForm);

		when(
			formInstance.getSettingsDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		String expectedStorageType = StringUtil.randomString();

		mockSaveFormInstanceMVCCommandHelperGetStorageType(expectedStorageType);

		_copyFormInstanceMVCActionCommand.saveFormInstanceMVCCommandHelper =
			_saveFormInstanceMVCCommandHelper;

		when(
			_ddmFormValuesFactory.create(
				Matchers.any(ActionRequest.class), Matchers.any(DDMForm.class))
		).thenReturn(
			ddmFormValues
		);

		DDMFormValues formInstanceSettingsDDMFormValues =
			_copyFormInstanceMVCActionCommand.
				createFormInstanceSettingsDDMFormValues(
					_actionRequest, formInstance);

		Assert.assertEquals(
			expectedFormInstanceSettingsDDMForm,
			formInstanceSettingsDDMFormValues.getDDMForm());

		List<DDMFormFieldValue> formInstanceSettingsDDMFormFieldValues =
			formInstanceSettingsDDMFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			formInstanceSettingsDDMFormFieldValues.toString(),
			getDDMFormFieldsSize(expectedFormInstanceSettingsDDMForm),
			formInstanceSettingsDDMFormFieldValues.size());

		DDMFormFieldValue storageTypeDDMFormFieldValue =
			_copyFormInstanceMVCActionCommand.getStorageTypeDDMFormFieldValue(
				formInstanceSettingsDDMFormValues);

		Assert.assertNotNull(storageTypeDDMFormFieldValue);

		Value storageTypeDDMFormFieldValueValue =
			storageTypeDDMFormFieldValue.getValue();

		Assert.assertEquals(
			expectedStorageType,
			storageTypeDDMFormFieldValueValue.getString(LocaleUtil.US));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Value value) {

		return createDDMFormFieldValue(StringUtil.randomString(), name, value);
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			DDMFormFieldValue ddmFormFieldValue =
				createLocalizedDDMFormFieldValue(
					ddmFormField.getName(), "test");

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, enValue);

		return createDDMFormFieldValue(name, localizedValue);
	}

	protected int getDDMFormFieldsSize(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		return ddmFormFields.size();
	}

	protected void mockSaveFormInstanceMVCCommandHelperGetStorageType(
			String returnStorageType)
		throws Exception {

		when(
			_saveFormInstanceMVCCommandHelper.getStorageType(
				Matchers.any(DDMFormValues.class))
		).thenReturn(
			returnStorageType
		);
	}

	protected void setUpCopyFormInstanceMVCActionCommand() throws Exception {
		_copyFormInstanceMVCActionCommand =
			new CopyFormInstanceMVCActionCommand();

		field(
			CopyFormInstanceMVCActionCommand.class, "ddmFormValuesFactory"
		).set(
			_copyFormInstanceMVCActionCommand, _ddmFormValuesFactory
		);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	@Mock
	private ActionRequest _actionRequest;

	private CopyFormInstanceMVCActionCommand _copyFormInstanceMVCActionCommand;

	@Mock
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Mock
	private SaveFormInstanceMVCCommandHelper _saveFormInstanceMVCCommandHelper;

}