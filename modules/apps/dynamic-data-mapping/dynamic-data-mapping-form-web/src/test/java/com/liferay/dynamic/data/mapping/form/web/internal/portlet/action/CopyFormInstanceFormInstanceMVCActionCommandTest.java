/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.helper.SaveFormInstanceMVCCommandHelper;
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
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public class CopyFormInstanceFormInstanceMVCActionCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpCopyFormInstanceMVCActionCommand();
		setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testCreateFormInstanceSettingsDDMFormValues() throws Exception {
		DDMForm formInstanceSettingsDDMForm = DDMFormFactory.create(
			DDMFormInstanceSettings.class);

		DDMFormInstance formInstance = Mockito.mock(DDMFormInstance.class);

		DDMFormValues ddmFormValues = createDDMFormValues(
			formInstanceSettingsDDMForm);

		Mockito.when(
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
			_getDDMFormFieldsSize(formInstanceSettingsDDMForm),
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

	protected static void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
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
				_createLocalizedDDMFormFieldValue(
					ddmFormField.getName(), StringUtil.randomString());

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	private static void _setUpCopyFormInstanceMVCActionCommand() {
		_copyFormInstanceMVCActionCommand.saveFormInstanceMVCCommandHelper =
			Mockito.mock(SaveFormInstanceMVCCommandHelper.class);
	}

	private static void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	private static void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private DDMFormFieldValue _createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, enValue);

		return createDDMFormFieldValue(name, localizedValue);
	}

	private int _getDDMFormFieldsSize(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		return ddmFormFields.size();
	}

	private static final CopyFormInstanceMVCActionCommand
		_copyFormInstanceMVCActionCommand =
			new CopyFormInstanceMVCActionCommand();

}