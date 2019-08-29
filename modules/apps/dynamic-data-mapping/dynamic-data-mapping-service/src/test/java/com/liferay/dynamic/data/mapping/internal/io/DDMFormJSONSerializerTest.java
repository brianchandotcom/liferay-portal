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
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldTypeSettingsTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONSerializerTest extends BaseDDMFormSerializerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormJSONSerializer();
		setUpPortalUtil();
	}

	@Test
	public void testDDMFormSerialization() throws Exception {
		String expectedJSON = read("ddm-form-json-serializer-test-data.json");

		DDMForm ddmForm = createDDMForm();

		ddmForm.setDDMFormRules(createDDMFormRules());
		ddmForm.setDDMFormSuccessPageSettings(
			createDDMFormSuccessPageSettings());

		String actualJSON = serialize(ddmForm);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected List<DDMFormRule> createDDMFormRules() {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		DDMFormRule ddmFormRule1 = new DDMFormRule(
			"Condition 1", Arrays.asList("Action 1", "Action 2"));

		ddmFormRules.add(ddmFormRule1);

		DDMFormRule ddmFormRule2 = new DDMFormRule(
			"Condition 2", Arrays.asList("Action 3"));

		ddmFormRule2.setEnabled(false);

		ddmFormRules.add(ddmFormRule2);

		return ddmFormRules;
	}

	protected DDMFormSuccessPageSettings createDDMFormSuccessPageSettings() {
		LocalizedValue body = new LocalizedValue(LocaleUtil.US);

		body.addString(LocaleUtil.US, "Body Text");
		body.addString(LocaleUtil.BRAZIL, "Texto");

		LocalizedValue title = new LocalizedValue(LocaleUtil.US);

		title.addString(LocaleUtil.US, "Title Text");
		title.addString(LocaleUtil.BRAZIL, "Título");

		return new DDMFormSuccessPageSettings(body, title, true);
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

		Map<String, Object> properties = new HashMap<>();

		properties.put("ddm.form.field.type.icon", "my-icon");
		properties.put(
			"ddm.form.field.type.js.class.name", "myJavaScriptClass");
		properties.put("ddm.form.field.type.js.module", "myJavaScriptModule");

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				Matchers.anyString())
		).thenReturn(
			properties
		);

		return ddmFormFieldTypeServicesTracker;
	}

	protected String serialize(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormJSONSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected void setUpDDMFormJSONSerializer() throws Exception {

		// DDM form field type services tracker

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializer.class, "_ddmFormFieldTypeServicesTracker");

		field.set(
			_ddmFormJSONSerializer, getMockedDDMFormFieldTypeServicesTracker());

		// JSON factory

		field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializer.class, "_jsonFactory");

		field.set(_ddmFormJSONSerializer, new JSONFactoryImpl());
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

	private final DDMFormJSONSerializer _ddmFormJSONSerializer =
		new DDMFormJSONSerializer();

	@Mock
	private DDMFormFieldType _defaultDDMFormFieldType;

}