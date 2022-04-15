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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.helper;

import com.liferay.dynamic.data.mapping.exception.FormInstanceExpiredException;
import com.liferay.dynamic.data.mapping.exception.FormInstanceSubmissionLimitException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class AddFormInstanceRecordMVCCommandHelperTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());

		_setUpLanguageUtil();
		_setUpResourceBundleUtil();
	}

	@Before
	public void setUp() throws Exception {
		_setUpAddFormInstanceRecordMVCCommandHelper();

		_mockGetDDMFormLayout();
	}

	@Test
	public void testDisabledField() throws Exception {
		_updateNonevaluableDDMFormFields(
			false, "readOnly", true, RandomTestUtil.randomBoolean(),
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(false, new UnlocalizedValue(StringPool.BLANK));
	}

	@Test
	public void testEnabledField() throws Exception {
		boolean required = RandomTestUtil.randomBoolean();

		_updateNonevaluableDDMFormFields(
			false, "readOnly", false, required,
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(required, new UnlocalizedValue(_STRING_VALUE));
	}

	@Test
	public void testInvisibleAndLocalizableField() throws Exception {
		_updateNonevaluableDDMFormFields(
			true, "visible", false, RandomTestUtil.randomBoolean(),
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US));

		Value value = _getFieldValue(_FIELD_NAME);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));

		value = _getFieldValue(_NESTED_FIELD_NAME);

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));
	}

	@Test
	public void testInvisibleField() throws Exception {
		_updateNonevaluableDDMFormFields(
			false, "visible", false, RandomTestUtil.randomBoolean(),
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(false, new UnlocalizedValue(StringPool.BLANK));
	}

	@Test
	public void testInvisibleFieldWithNullValue() throws Exception {
		_updateNonevaluableDDMFormFields(
			RandomTestUtil.randomBoolean(), "visible", false,
			RandomTestUtil.randomBoolean(), null);

		_assertDDMFormFields(false, null);
	}

	@Test(expected = FormInstanceExpiredException.class)
	public void testValidateExpirationStatus() throws Exception {
		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			_actionRequest.getAttribute(Matchers.eq(WebKeys.THEME_DISPLAY))
		).thenReturn(
			themeDisplay
		);

		_addFormInstanceRecordMVCCommandHelper.validateExpirationStatus(
			_mockDDMFormInstance(), _actionRequest);
	}

	@Test(expected = FormInstanceSubmissionLimitException.class)
	public void testValidateSubmissionLimitStatus() throws Exception {
		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			_actionRequest.getAttribute(Matchers.eq(WebKeys.THEME_DISPLAY))
		).thenReturn(
			themeDisplay
		);

		_addFormInstanceRecordMVCCommandHelper.validateSubmissionLimitStatus(
			_mockDDMFormInstance(),
			_mockDDMFormInstanceRecordVersionLocalService(), _actionRequest);
	}

	@Test
	public void testVisibleField() throws Exception {
		boolean required = RandomTestUtil.randomBoolean();

		_updateNonevaluableDDMFormFields(
			false, "visible", true, required,
			new UnlocalizedValue(_STRING_VALUE));

		_assertDDMFormFields(required, new UnlocalizedValue(_STRING_VALUE));
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private static void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private void _assertDDMFormFields(
		boolean expectedRequired, Value expectedValue) {

		Assert.assertEquals(expectedRequired, _ddmFormField.isRequired());
		Assert.assertEquals(expectedValue, _getFieldValue(_FIELD_NAME));
		Assert.assertEquals(expectedValue, _getFieldValue(_NESTED_FIELD_NAME));
	}

	private void _createDDMFormFields(boolean localizable, boolean required) {
		_ddmForm = DDMFormTestUtil.createDDMForm();

		_ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			_FIELD_NAME, localizable, false, required);

		_ddmForm.addDDMFormField(_ddmFormField);

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), null, null, null, false, false,
			false);

		ddmFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				_NESTED_FIELD_NAME, localizable, false, required));

		_ddmForm.addDDMFormField(ddmFormField);
	}

	private void _createDDMFormValues(Value value) {
		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(_ddmForm);

		_ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_FIELD_INSTANCE_ID, _FIELD_NAME, value));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				RandomTestUtil.randomString(), null);

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_NESTED_FIELD_INSTANCE_ID, _NESTED_FIELD_NAME, value));

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	private Value _getFieldValue(String fieldName) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap(true);

		if (!ddmFormFieldValuesMap.containsKey(fieldName)) {
			return null;
		}

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			fieldName);

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		return ddmFormFieldValue.getValue();
	}

	private void _mockDDMFormEvaluator(
		Map<String, Object> fieldChangesProperties) {

		Mockito.when(
			_ddmFormEvaluator.evaluate(
				Matchers.any(DDMFormEvaluatorEvaluateRequest.class))
		).thenReturn(
			DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
				HashMapBuilder.put(
					new DDMFormEvaluatorFieldContextKey(
						_FIELD_NAME, _FIELD_INSTANCE_ID),
					fieldChangesProperties
				).put(
					new DDMFormEvaluatorFieldContextKey(
						_NESTED_FIELD_NAME, _NESTED_FIELD_INSTANCE_ID),
					fieldChangesProperties
				).build()
			).withDisabledPagesIndexes(
				Collections.emptySet()
			).build()
		);
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettings();

		Mockito.when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceRecordVersionLocalService
		_mockDDMFormInstanceRecordVersionLocalService() {

		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService = Mockito.mock(
				DDMFormInstanceRecordVersionLocalService.class);

		Mockito.when(
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			Collections.singletonList(
				Mockito.mock(DDMFormInstanceRecordVersion.class))
		);

		return ddmFormInstanceRecordVersionLocalService;
	}

	private DDMFormInstanceSettings _mockDDMFormInstanceSettings() {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			ddmFormInstanceSettings.expirationDate()
		).thenReturn(
			"1987-09-22"
		);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUser()
		).thenReturn(
			true
		);

		return ddmFormInstanceSettings;
	}

	private void _mockGetDDMFormLayout() throws Exception {
		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			_ddmFormInstanceService.getFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		Mockito.when(
			_ddmStructureLocalService.getStructure(Matchers.anyLong())
		).thenReturn(
			ddmStructure
		);

		Mockito.doReturn(
			new DDMFormLayout()
		).when(
			ddmStructure
		).getDDMFormLayout();
	}

	private ThemeDisplay _mockThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getTimeZone()
		).thenReturn(
			TimeZone.getDefault()
		);

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			Mockito.mock(User.class)
		);

		return themeDisplay;
	}

	private void _setUpAddFormInstanceRecordMVCCommandHelper() {
		_addFormInstanceRecordMVCCommandHelper =
			new AddFormInstanceRecordMVCCommandHelper();

		ReflectionTestUtil.setFieldValue(
			_addFormInstanceRecordMVCCommandHelper, "_ddmFormEvaluator",
			_ddmFormEvaluator);

		ReflectionTestUtil.setFieldValue(
			_addFormInstanceRecordMVCCommandHelper, "_ddmFormInstanceService",
			_ddmFormInstanceService);

		ReflectionTestUtil.setFieldValue(
			_addFormInstanceRecordMVCCommandHelper, "_ddmStructureLocalService",
			_ddmStructureLocalService);

		ReflectionTestUtil.setFieldValue(
			_addFormInstanceRecordMVCCommandHelper, "_portal", _portal);

		Mockito.when(
			_portal.getHttpServletRequest(_actionRequest)
		).thenReturn(
			_httpServletRequest
		);
	}

	private void _updateNonevaluableDDMFormFields(
			boolean localizable, String propertyName, Object propertyValue,
			boolean required, Value value)
		throws Exception {

		_createDDMFormFields(localizable, required);

		_createDDMFormValues(value);

		_mockDDMFormEvaluator(
			HashMapBuilder.put(
				propertyName, propertyValue
			).build());

		_addFormInstanceRecordMVCCommandHelper.updateNonevaluableDDMFormFields(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);
	}

	private static final String _FIELD_INSTANCE_ID =
		RandomTestUtil.randomString();

	private static final String _FIELD_NAME = RandomTestUtil.randomString();

	private static final String _NESTED_FIELD_INSTANCE_ID =
		RandomTestUtil.randomString();

	private static final String _NESTED_FIELD_NAME =
		RandomTestUtil.randomString();

	private static final String _STRING_VALUE = RandomTestUtil.randomString();

	private static AddFormInstanceRecordMVCCommandHelper
		_addFormInstanceRecordMVCCommandHelper;

	private final ActionRequest _actionRequest = Mockito.mock(
		ActionRequest.class);
	private DDMForm _ddmForm;
	private final DDMFormEvaluator _ddmFormEvaluator = Mockito.mock(
		DDMFormEvaluator.class);
	private DDMFormField _ddmFormField;
	private final DDMFormInstanceService _ddmFormInstanceService = Mockito.mock(
		DDMFormInstanceService.class);
	private DDMFormValues _ddmFormValues;
	private final DDMStructureLocalService _ddmStructureLocalService =
		Mockito.mock(DDMStructureLocalService.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Portal _portal = Mockito.mock(Portal.class);

}