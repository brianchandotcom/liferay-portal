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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

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
 * @author Leonardo Barros
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AddRecordMVCCommandHelperTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpAddRecordMVCCommandHelper();
		setUpLanguageUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testNotRequiredAndInvisibleField() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			setUpDDMFormFieldEvaluation();

		_ddmFormField.setRequired(false);

		ddmFormFieldEvaluationResult.setVisible(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testNotRequiredAndVisibleField() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			setUpDDMFormFieldEvaluation();

		_ddmFormField.setRequired(false);

		ddmFormFieldEvaluationResult.setVisible(true);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndInvisibleField() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			setUpDDMFormFieldEvaluation();

		ddmFormFieldEvaluationResult.setVisible(false);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertFalse(_ddmFormField.isRequired());
	}

	@Test
	public void testRequiredAndVisibleField() throws Exception {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			setUpDDMFormFieldEvaluation();

		ddmFormFieldEvaluationResult.setVisible(true);

		_addRecordMVCCommandHelper.updateRequiredFieldsAccordingToVisibility(
			_actionRequest, _ddmForm, _ddmFormValues, LocaleUtil.US);

		Assert.assertTrue(_ddmFormField.isRequired());
	}

	protected void setUpAddRecordMVCCommandHelper() throws Exception {
		_addRecordMVCCommandHelper = new AddRecordMVCCommandHelper();

		field(
			AddRecordMVCCommandHelper.class, "_ddmFormEvaluator"
		).set(
			_addRecordMVCCommandHelper, _ddmFormEvaluator
		);

		field(
			AddRecordMVCCommandHelper.class, "_portal"
		).set(
			_addRecordMVCCommandHelper, _portal
		);

		when(
			_portal, "getHttpServletRequest", _actionRequest
		).thenReturn(
			_request
		);
	}

	protected DDMFormFieldEvaluationResult setUpDDMFormFieldEvaluation()
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

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"field0", ddmFormFieldValue.getInstanceId());

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ListUtil.fromArray(
				new DDMFormFieldEvaluationResult[] {
					ddmFormFieldEvaluationResult
				}));

		when(
			_ddmFormEvaluator, "evaluate",
			Matchers.any(DDMFormEvaluatorContext.class)
		).thenReturn(
			ddmFormEvaluationResult
		);

		return ddmFormFieldEvaluationResult;
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

	private AddRecordMVCCommandHelper _addRecordMVCCommandHelper;
	private DDMForm _ddmForm;

	@Mock
	private DDMFormEvaluator _ddmFormEvaluator;

	private DDMFormField _ddmFormField;
	private DDMFormValues _ddmFormValues;

	@Mock
	private Portal _portal;

	@Mock
	private HttpServletRequest _request;

}