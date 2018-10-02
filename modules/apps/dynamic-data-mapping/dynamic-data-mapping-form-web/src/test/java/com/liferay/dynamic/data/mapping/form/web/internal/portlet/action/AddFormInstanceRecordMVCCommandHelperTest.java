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
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.util.PropsImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	}

	@Test
	public void testNotRequiredAndInvisibleField() throws Exception {
		Map<String, Object> changedProperties = new HashMap<>();

		changedProperties.put("visible", false);

		mockDDMFormEvaluator(changedProperties);

		_ddmFormField.setRequired(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testNotRequiredAndVisibleField() throws Exception {
		Map<String, Object> changedProperties = new HashMap<>();

		changedProperties.put("visible", true);

		mockDDMFormEvaluator(changedProperties);

		_ddmFormField.setRequired(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndInvisibleField() throws Exception {
		Map<String, Object> changedProperties = new HashMap<>();

		changedProperties.put("visible", false);

		mockDDMFormEvaluator(changedProperties);

		mockGetDDMFormLayout();

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndVisibleField() throws Exception {
		Map<String, Object> changedProperties = new HashMap<>();

		changedProperties.put("visible", true);

		mockDDMFormEvaluator(changedProperties);

		mockGetDDMFormLayout();

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertTrue(_ddmFormField.isRequired());
	}

	protected void mockDDMFormEvaluator(
			Map<String, Object> fieldChangesProperties)
		throws Exception {

		_ddmForm = DDMFormTestUtil.createDDMForm("field0");

		Map<String, DDMFormField> ddmFormFields = _ddmForm.getDDMFormFieldsMap(
			true);

		_ddmFormField = ddmFormFields.get("field0");

		_ddmFormField.setRequired(true);

		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(_ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"field0", StringPool.BLANK);

		_ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges = new HashMap<>();

		ddmFormFieldsPropertyChanges.put(
			new DDMFormEvaluatorFieldContextKey(
				"field0", ddmFormFieldValue.getInstanceId()),
			fieldChangesProperties);

		DDMFormEvaluatorEvaluateResponse.Builder builder =
			DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
				ddmFormFieldsPropertyChanges);

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
			_request
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
	private Portal _portal;

	@Mock
	private HttpServletRequest _request;

}