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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldTypeSettingsTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONDeserializerTest
	extends BaseDDMFormDeserializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormJSONDeserializer();
		setUpPortalUtil();
	}

	@Test
	public void testDDMFormDeserializationWithSchemaVersion() throws Exception {
		DDMForm ddmForm = deserialize(
			read(
				"ddm-form-json-deserializer-with-definition-schema-" +
					"version.json"));

		Assert.assertEquals("2.0", ddmForm.getDefinitionSchemaVersion());
	}

	@Test
	public void testDDMFormSuccessPageSettingsDifferentDefaultLocale()
		throws Exception {

		DDMForm ddmForm = deserialize(
			read(
				"ddm-form-success-page-settings-different-default-" +
					"locale.json"));

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			ddmForm.getDDMFormSuccessPageSettings();

		Assert.assertNotNull(ddmFormSuccessPageSettings);

		LocalizedValue body = ddmFormSuccessPageSettings.getBody();

		Assert.assertEquals("Texto", body.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals("Texto", body.getString(LocaleUtil.NETHERLANDS));

		LocalizedValue title = ddmFormSuccessPageSettings.getTitle();

		Assert.assertEquals("Título", title.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals("Título", title.getString(LocaleUtil.NETHERLANDS));

		Assert.assertTrue(ddmFormSuccessPageSettings.isEnabled());
	}

	@Override
	protected DDMForm deserialize(String serializedDDMForm) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				serializedDDMForm);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormJSONDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	@Override
	protected String getDeserializerType() {
		return "json";
	}

	protected DDMFormFieldTypeServicesTracker
		getMockedDDMFormFieldTypeServicesTracker() {

		setUpDefaultDDMFormFieldType();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		DDMFormFieldRenderer ddmFormFieldRenderer = mock(
			DDMFormFieldRenderer.class);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(
				Matchers.anyString())
		).thenReturn(
			ddmFormFieldRenderer
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				Matchers.anyString())
		).thenReturn(
			_defaultDDMFormFieldType
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				Matchers.anyString())
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"ddm.form.field.type.icon", "my-icon"
			).put(
				"ddm.form.field.type.js.class.name", "myJavaScriptClass"
			).put(
				"ddm.form.field.type.js.module", "myJavaScriptModule"
			).build()
		);

		return ddmFormFieldTypeServicesTracker;
	}

	@Override
	protected String getTestFileExtension() {
		return ".json";
	}

	protected void setUpDDMFormJSONDeserializer() throws Exception {

		// DDM form field type services tracker

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormJSONDeserializer.class, "_ddmFormFieldTypeServicesTracker");

		field.set(
			_ddmFormJSONDeserializer,
			getMockedDDMFormFieldTypeServicesTracker());

		// JSON factory

		field = ReflectionUtil.getDeclaredField(
			DDMFormJSONDeserializer.class, "_jsonFactory");

		field.set(_ddmFormJSONDeserializer, new JSONFactoryImpl());
	}

	protected void setUpDefaultDDMFormFieldType() {
		when(
			_defaultDDMFormFieldType.getDDMFormFieldTypeSettings()
		).then(
			new Answer<Class<? extends DDMFormFieldTypeSettings>>() {

				@Override
				public Class<? extends DDMFormFieldTypeSettings> answer(
						InvocationOnMock invocationOnMock)
					throws Throwable {

					return DDMFormFieldTypeSettingsTestUtil.getSettings();
				}

			}
		);
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

	@Override
	protected void testBooleanDDMFormField(DDMFormField ddmFormField) {
		super.testBooleanDDMFormField(ddmFormField);

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		Assert.assertNotNull(ddmFormFieldValidation);

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		Assert.assertEquals(
			"Boolean2282", ddmFormFieldValidationExpression.getValue());

		LocalizedValue errorMessageLocalizedValue =
			ddmFormFieldValidation.getErrorMessageLocalizedValue();

		Assert.assertEquals(
			"You must check this box to continue.",
			errorMessageLocalizedValue.getString(LocaleUtil.US));

		Assert.assertEquals("true", ddmFormField.getVisibilityExpression());
	}

	@Override
	protected void testDDMFormRules(List<DDMFormRule> ddmFormRules) {
		Assert.assertEquals(ddmFormRules.toString(), 2, ddmFormRules.size());

		DDMFormRule ddmFormRule1 = ddmFormRules.get(0);

		Assert.assertEquals("Condition 1", ddmFormRule1.getCondition());
		Assert.assertEquals(
			Arrays.asList("Action 1", "Action 2"), ddmFormRule1.getActions());
		Assert.assertTrue(ddmFormRule1.isEnabled());

		DDMFormRule ddmFormRule2 = ddmFormRules.get(1);

		Assert.assertEquals("Condition 2", ddmFormRule2.getCondition());
		Assert.assertEquals(
			Arrays.asList("Action 3"), ddmFormRule2.getActions());
		Assert.assertFalse(ddmFormRule2.isEnabled());
	}

	@Override
	protected void testDDMFormSuccessPageSettings(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		Assert.assertNotNull(ddmFormSuccessPageSettings);

		LocalizedValue body = ddmFormSuccessPageSettings.getBody();

		Assert.assertEquals("Body Text", body.getString(LocaleUtil.US));
		Assert.assertEquals("Texto", body.getString(LocaleUtil.BRAZIL));

		LocalizedValue title = ddmFormSuccessPageSettings.getTitle();

		Assert.assertEquals("Title Text", title.getString(LocaleUtil.US));
		Assert.assertEquals("Título", title.getString(LocaleUtil.BRAZIL));

		Assert.assertTrue(ddmFormSuccessPageSettings.isEnabled());
	}

	@Override
	protected void testDecimalDDMFormField(DDMFormField ddmFormField) {
		super.testDecimalDDMFormField(ddmFormField);

		Assert.assertEquals("false", ddmFormField.getVisibilityExpression());
	}

	private final DDMFormJSONDeserializer _ddmFormJSONDeserializer =
		new DDMFormJSONDeserializer();

	@Mock
	private DDMFormFieldType _defaultDDMFormFieldType;

}