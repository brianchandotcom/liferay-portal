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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.util.PropsImpl;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AddFormInstanceRecordMVCCommandHelperTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws Exception {
		setUpAddRecordMVCCommandHelper();
		setUpLanguageUtil();
		setUpResourceBundleUtil();

		mockGetDDMFormLayout();
	}

	@Test
	public void testInvisibleAndLocalizableField() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build());

		_ddmFormField.setLocalizable(true);

		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(_ddmForm);

		LocalizedValue localizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(
				"Test", "Teste", LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_FIELD_NAME, localizedValue);

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Value value = getFieldValue();

		Assert.assertEquals(
			StringPool.BLANK, value.getString(LocaleUtil.BRAZIL));

		Assert.assertEquals(StringPool.BLANK, value.getString(LocaleUtil.US));
	}

	@Test
	public void testInvisibleFieldWithNullValue() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build());

		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(_ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(_FIELD_NAME, null);

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertNull(getFieldValue());
	}

	@Test
	public void testNotRequiredAndInvisibleField() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build());

		_ddmFormField.setRequired(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertEquals(
			new UnlocalizedValue(StringPool.BLANK), getFieldValue());

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testNotRequiredAndVisibleField() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", true
			).build());

		_ddmFormField.setRequired(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertEquals(
			new UnlocalizedValue(_STRING_VALUE), getFieldValue());

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndInvisibleField() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", false
			).build());

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertEquals(
			new UnlocalizedValue(StringPool.BLANK), getFieldValue());

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndVisibleField() throws Exception {
		mockDDMFormEvaluator(
			HashMapBuilder.<String, Object>put(
				"visible", true
			).build());

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertEquals(
			new UnlocalizedValue(_STRING_VALUE), getFieldValue());

		Assert.assertTrue(_ddmFormField.isRequired());
	}

	protected Value getFieldValue() {
		for (DDMFormFieldValue ddmFormFieldValue :
				_ddmFormValues.getDDMFormFieldValues()) {

			if (Objects.equals(ddmFormFieldValue.getName(), _FIELD_NAME)) {
				return ddmFormFieldValue.getValue();
			}
		}

		return null;
	}

	protected void mockDDMFormEvaluator(
			Map<String, Object> fieldChangesProperties)
		throws Exception {

		_ddmForm = DDMFormTestUtil.createDDMForm(_FIELD_NAME);

		Map<String, DDMFormField> ddmFormFields = _ddmForm.getDDMFormFieldsMap(
			true);

		_ddmFormField = ddmFormFields.get(_FIELD_NAME);

		_ddmFormField.setLocalizable(false);

		_ddmFormField.setRequired(true);

		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(_ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				_FIELD_NAME, _STRING_VALUE);

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormEvaluatorEvaluateResponse.Builder builder =
			DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
				HashMapBuilder.
					<DDMFormEvaluatorFieldContextKey, Map<String, Object>>put(
						new DDMFormEvaluatorFieldContextKey(
							_FIELD_NAME, ddmFormFieldValue.getInstanceId()),
						fieldChangesProperties
					).build());

		builder.withDisabledPagesIndexes(Collections.emptySet());

		when(
			_ddmFormEvaluator.evaluate(
				Matchers.any(DDMFormEvaluatorEvaluateRequest.class))
		).thenReturn(
			builder.build()
		);
	}

	protected void mockGetDDMFormLayout() throws Exception {
		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		when(
			_ddmFormInstanceService, "getFormInstance", Matchers.anyLong()
		).thenReturn(
			formInstance
		);

		DDMStructure ddmStructure = mock(DDMStructure.class);

		when(
			_ddmStructureLocalService, "getStructure", Matchers.anyLong()
		).thenReturn(
			ddmStructure
		);

		when(
			ddmStructure, "getDDMFormLayout"
		).thenReturn(
			new DDMFormLayout()
		);
	}

	protected void setUpAddRecordMVCCommandHelper() throws Exception {
		_addRecordMVCCommandHelper =
			new AddFormInstanceRecordMVCCommandHelper();

		field(
			AddFormInstanceRecordMVCCommandHelper.class,
			"_ddmFormInstanceService"
		).set(
			_addRecordMVCCommandHelper, _ddmFormInstanceService
		);

		field(
			AddFormInstanceRecordMVCCommandHelper.class, "_ddmFormEvaluator"
		).set(
			_addRecordMVCCommandHelper, _ddmFormEvaluator
		);

		field(
			AddFormInstanceRecordMVCCommandHelper.class,
			"_ddmStructureLocalService"
		).set(
			_addRecordMVCCommandHelper, _ddmStructureLocalService
		);

		field(
			AddFormInstanceRecordMVCCommandHelper.class, "_portal"
		).set(
			_addRecordMVCCommandHelper, _portal
		);

		when(
			_portal, "getHttpServletRequest", _actionRequest
		).thenReturn(
			_httpServletRequest
		);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(mock(Language.class));
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

	private static final String _FIELD_NAME = "field0";

	private static final String _STRING_VALUE = "string value";

	@Mock
	private ActionRequest _actionRequest;

	private AddFormInstanceRecordMVCCommandHelper _addRecordMVCCommandHelper;
	private DDMForm _ddmForm;

	@Mock
	private DDMFormEvaluator _ddmFormEvaluator;

	private DDMFormField _ddmFormField;

	@Mock
	private DDMFormInstanceService _ddmFormInstanceService;

	private DDMFormValues _ddmFormValues;

	@Mock
	private DDMStructureLocalService _ddmStructureLocalService;

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private Portal _portal;

}