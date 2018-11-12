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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

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
	public void setUp() {
		setUpCopyFormInstanceMVCActionCommand();
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateFormInstanceSettingsDDMFormValues() throws Exception {
		DDMForm formInstanceSettingsDDMForm = DDMFormFactory.create(
			DDMFormInstanceSettings.class);

		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		DDMFormValues ddmFormValues = createDDMFormValues(
			formInstanceSettingsDDMForm);

		when(
			formInstance.getSettingsDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		DDMFormValues formInstanceSettingsDDMFormValuesCopy =
			_copyFormInstanceMVCActionCommand.
				createFormInstanceSettingsDDMFormValues(formInstance);

		Assert.assertEquals(
			formInstanceSettingsDDMForm,
			formInstanceSettingsDDMFormValuesCopy.getDDMForm());

		List<DDMFormFieldValue> formInstanceSettingsDDMFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		List<DDMFormFieldValue> formInstanceSettingsDDMFormFieldValuesCopy =
			formInstanceSettingsDDMFormValuesCopy.getDDMFormFieldValues();

		Assert.assertEquals(
			formInstanceSettingsDDMFormFieldValuesCopy.toString(),
			getDDMFormFieldsSize(formInstanceSettingsDDMForm),
			formInstanceSettingsDDMFormFieldValuesCopy.size());

		for (int i = 0; i < formInstanceSettingsDDMFormFieldValuesCopy.size();
			 i++) {

			DDMFormFieldValue ddmFormFieldValue =
				formInstanceSettingsDDMFormFieldValues.get(i);

			DDMFormFieldValue ddmFormFieldValueCopy =
				formInstanceSettingsDDMFormFieldValuesCopy.get(i);

			Value valueCopy = ddmFormFieldValueCopy.getValue();

			DDMFormField ddmFormField = ddmFormFieldValueCopy.getDDMFormField();

			if (Objects.equals(ddmFormField.getName(), "published")) {
				Assert.assertEquals(
					"false", valueCopy.getString(LocaleUtil.US));
			}
			else {
				Value value = ddmFormFieldValue.getValue();

				Assert.assertEquals(
					value.getString(LocaleUtil.US),
					valueCopy.getString(LocaleUtil.US));
			}
		}
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
					ddmFormField.getName(), StringUtil.randomString());

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

	protected void setUpCopyFormInstanceMVCActionCommand() {
		_copyFormInstanceMVCActionCommand.saveFormInstanceMVCCommandHelper =
			mock(SaveFormInstanceMVCCommandHelper.class);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
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

	private final CopyFormInstanceMVCActionCommand
		_copyFormInstanceMVCActionCommand =
			new CopyFormInstanceMVCActionCommand();

}