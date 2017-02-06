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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTemplateContextFactoryTest {

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testNotReadOnlyTextFieldAndReadOnlyForm() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Field1", false, false, false);

		boolean readOnly = false;

		ddmFormField.setReadOnly(readOnly);

		ddmForm.addDDMFormField(ddmFormField);

		// Dynamic data mapping form field evaluation

		String instanceId = StringUtil.randomString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("Field1", instanceId);

		ddmFormFieldEvaluationResult.setReadOnly(readOnly);
		ddmFormFieldEvaluationResult.setVisible(true);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			getDDMFormEvaluationResult(ddmFormFieldEvaluationResult);

		// Dynamic data mapping form values

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field1", "Value 1");

		ddmFormFieldValue.setInstanceId(instanceId);

		ddmFormFieldValues.add(ddmFormFieldValue);

		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			createDDMFormFieldTemplateContextFactory(
				ddmForm, ddmFormEvaluationResult, ddmFormFieldValues, true,
				getTextDDMFormFieldRenderer(),
				getTextDDMFormFieldTemplateContextContributor());

		List<Object> fields = ddmFormFieldTemplateContextFactory.create();

		Assert.assertEquals(fields.toString(), 1, fields.size());

		Map<String, Object> fieldTemplateContext =
			(Map<String, Object>)fields.get(0);

		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldTemplateContext, "readOnly"));
	}

	@Test
	public void testReadOnlyTextFieldAndNotReadOnlyForm() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Field1", false, false, true);

		boolean readOnly = true;

		ddmFormField.setReadOnly(readOnly);

		ddmForm.addDDMFormField(ddmFormField);

		// Dynamic data mapping form field evaluation

		String instanceId = StringUtil.randomString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("Field1", instanceId);

		ddmFormFieldEvaluationResult.setReadOnly(readOnly);
		ddmFormFieldEvaluationResult.setVisible(true);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			getDDMFormEvaluationResult(ddmFormFieldEvaluationResult);

		// Dynamic data mapping form values

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field1", "Value 1");

		ddmFormFieldValue.setInstanceId(instanceId);

		ddmFormFieldValues.add(ddmFormFieldValue);

		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			createDDMFormFieldTemplateContextFactory(
				ddmForm, ddmFormEvaluationResult, ddmFormFieldValues, false,
				getTextDDMFormFieldRenderer(),
				getTextDDMFormFieldTemplateContextContributor());

		List<Object> fields = ddmFormFieldTemplateContextFactory.create();

		Assert.assertEquals(fields.toString(), 1, fields.size());

		Map<String, Object> fieldTemplateContext =
			(Map<String, Object>)fields.get(0);

		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldTemplateContext, "readOnly"));
	}

	@Test
	public void testTextField() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		boolean required = true;

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Field1", false, false, required);

		ddmFormField.setLabel(
			DDMFormValuesTestUtil.createLocalizedValue("Field 1", _LOCALE));
		ddmFormField.setReadOnly(false);
		ddmFormField.setTip(
			DDMFormValuesTestUtil.createLocalizedValue(
				"This is a tip.", _LOCALE));

		ddmFormField.setProperty("displayStyle", "singleline");

		ddmForm.addDDMFormField(ddmFormField);

		// Dynamic data mapping form field evaluation

		String instanceId = StringUtil.randomString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("Field1", instanceId);

		ddmFormFieldEvaluationResult.setRequired(required);
		ddmFormFieldEvaluationResult.setValid(true);
		ddmFormFieldEvaluationResult.setVisible(true);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			getDDMFormEvaluationResult(ddmFormFieldEvaluationResult);

		// Dynamic data mapping form values

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field1", "Value 1");

		ddmFormFieldValue.setInstanceId(instanceId);

		ddmFormFieldValues.add(ddmFormFieldValue);

		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			createDDMFormFieldTemplateContextFactory(
				ddmForm, ddmFormEvaluationResult, ddmFormFieldValues, false,
				getTextDDMFormFieldRenderer(),
				getTextDDMFormFieldTemplateContextContributor());

		List<Object> fields = ddmFormFieldTemplateContextFactory.create();

		Assert.assertEquals(fields.toString(), 1, fields.size());

		Map<String, Object> fieldTemplateContext =
			(Map<String, Object>)fields.get(0);

		Assert.assertEquals(
			"singleline",
			MapUtil.getString(fieldTemplateContext, "displayStyle"));
		Assert.assertEquals(
			"Field 1", MapUtil.getString(fieldTemplateContext, "label"));
		Assert.assertEquals(
			false, MapUtil.getBoolean(fieldTemplateContext, "readOnly"));
		Assert.assertEquals(
			false, MapUtil.getBoolean(fieldTemplateContext, "repeatable"));
		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldTemplateContext, "required"));
		Assert.assertEquals(
			"This is a tip.", MapUtil.getString(fieldTemplateContext, "tip"));
		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldTemplateContext, "valid"));
		Assert.assertEquals(
			StringPool.BLANK,
			MapUtil.getString(fieldTemplateContext, "validationErrorMessage"));
		Assert.assertEquals(
			"Value 1", MapUtil.getString(fieldTemplateContext, "value"));
		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldTemplateContext, "visible"));

		String expectedName = String.format(
			_FIELD_NAME_FORMAT, "Field1", instanceId, 0, _LOCALE.toString());

		Assert.assertEquals(
			expectedName, MapUtil.getString(fieldTemplateContext, "name"));
	}

	protected DDMFormFieldTemplateContextFactory
		createDDMFormFieldTemplateContextFactory(
			DDMForm ddmForm, DDMFormEvaluationResult ddmFormEvaluationResult,
			List<DDMFormFieldValue> ddmFormFieldValues, boolean ddmFormReadOnly,
			DDMFormFieldRenderer ddmFormFieldRenderer,
			DDMFormFieldTemplateContextContributor
				ddmFormFieldTemplateContextContributor) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(_LOCALE);
		ddmFormRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormRenderingContext.setReadOnly(ddmFormReadOnly);

		DDMFormFieldTemplateContextFactory ddmFormFieldTemplateContextFactory =
			new DDMFormFieldTemplateContextFactory(
				ddmForm.getDDMFormFieldsMap(true), ddmFormEvaluationResult,
				ddmFormFieldValues, ddmFormRenderingContext);

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			mockDDMFormFieldTypeServicesTracker(
				ddmFormFieldRenderer, ddmFormFieldTemplateContextContributor);

		ddmFormFieldTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			ddmFormFieldTypeServicesTracker);

		return ddmFormFieldTemplateContextFactory;
	}

	protected DDMFormEvaluationResult getDDMFormEvaluationResult(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ddmFormFieldEvaluationResults);

		return ddmFormEvaluationResult;
	}

	protected DDMFormFieldRenderer getTextDDMFormFieldRenderer() {
		return new BaseDDMFormFieldRenderer() {

			public String getTemplateLanguage() {
				return null;
			}

			public String getTemplateNamespace() {
				return "ddm.text";
			}

			public TemplateResource getTemplateResource() {
				return null;
			}

		};
	}

	protected DDMFormFieldTemplateContextContributor
		getTextDDMFormFieldTemplateContextContributor() {

		return new DDMFormFieldTemplateContextContributor() {

			public Map<String, Object> getParameters(
				DDMFormField ddmFormField,
				DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

				Map<String, Object> parameters = new HashMap<>();

				parameters.put(
					"displayStyle", ddmFormField.getProperty("displayStyle"));

				return parameters;
			}

		};
	}

	protected DDMFormFieldTypeServicesTracker
		mockDDMFormFieldTypeServicesTracker(
			DDMFormFieldRenderer ddmFormFieldRenderer,
			DDMFormFieldTemplateContextContributor
				ddmFormFieldTemplateContextContributor) {

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(
				Matchers.anyString())
		).thenReturn(
			ddmFormFieldRenderer
		);

		when(
			ddmFormFieldTypeServicesTracker.
				getDDMFormFieldTemplateContextContributor(Matchers.anyString())
		).thenReturn(
			ddmFormFieldTemplateContextContributor
		);

		return ddmFormFieldTypeServicesTracker;
	}

	protected void setUpLanguageUtil() {
		Language language = mock(Language.class);

		whenLanguageGet(
			language, LocaleUtil.US, LanguageConstants.KEY_DIR, "ltr");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void whenLanguageGet(
		Language language, Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private static final String _FIELD_NAME_FORMAT =
		"_PORTLET_NAMESPACE_ddm$$%s$%s$%d$$%s";

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _PORTLET_NAMESPACE = "_PORTLET_NAMESPACE_";

}