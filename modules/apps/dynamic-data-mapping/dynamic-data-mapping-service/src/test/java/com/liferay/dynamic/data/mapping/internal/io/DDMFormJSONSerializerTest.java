/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONSerializerTest extends BaseDDMFormSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormJSONSerializer();
		setUpPortalUtil();
	}

	@Test
	public void testDDMFormSerialization() throws Exception {
		String expectedJSON = read("ddm-form-json-serializer-test-data.json");

		DDMForm ddmForm = createDDMForm();

		ddmForm.setDDMFormRules(_createDDMFormRules());
		ddmForm.setDDMFormSuccessPageSettings(
			_createDDMFormSuccessPageSettings());

		String actualJSON = serialize(ddmForm);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	@Test
	public void testDDMFormSerializationWithSchemaVersion() throws Exception {
		String expectedJSON = read(
			"ddm-form-json-serializer-with-definition-schema-version.json");

		DDMForm ddmForm = createDDMForm();

		ddmForm.setDefinitionSchemaVersion("2.0");

		String actualJSON = serialize(ddmForm);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected DDMFormFieldTypeServicesRegistry
		getMockedDDMFormFieldTypeServicesRegistry() {

		setUpDefaultDDMFormFieldType();

		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry =
			Mockito.mock(DDMFormFieldTypeServicesRegistry.class);

		DDMFormFieldRenderer ddmFormFieldRenderer = Mockito.mock(
			DDMFormFieldRenderer.class);

		Mockito.when(
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldRenderer(
				Mockito.anyString())
		).thenReturn(
			ddmFormFieldRenderer
		);

		Mockito.when(
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(
				Mockito.anyString())
		).thenReturn(
			_defaultDDMFormFieldType
		);

		Mockito.when(
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypeProperties(
				Mockito.anyString())
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"ddm.form.field.type.icon", "my-icon"
			).put(
				"ddm.form.field.type.js.class.name", "myJavaScriptClass"
			).put(
				"ddm.form.field.type.js.module", "myJavaScriptModule"
			).build()
		);

		return ddmFormFieldTypeServicesRegistry;
	}

	protected String serialize(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormJSONSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected void setUpDefaultDDMFormFieldType() {
		Mockito.when(
			_defaultDDMFormFieldType.getDDMFormFieldTypeSettings()
		).then(
			(Answer<Class<? extends DDMFormFieldTypeSettings>>)
				invocationOnMock ->
					DDMFormFieldTypeSettingsTestUtil.getSettings()
		);
	}

	protected void setUpPortalUtil() {
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

	private List<DDMFormRule> _createDDMFormRules() {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		DDMFormRule ddmFormRule1 = new DDMFormRule(
			Arrays.asList("Action 1", "Action 2"), "Condition 1");

		ddmFormRules.add(ddmFormRule1);

		DDMFormRule ddmFormRule2 = new DDMFormRule(
			Arrays.asList("Action 3"), "Condition 2");

		ddmFormRule2.setEnabled(false);

		ddmFormRules.add(ddmFormRule2);

		return ddmFormRules;
	}

	private DDMFormSuccessPageSettings _createDDMFormSuccessPageSettings() {
		LocalizedValue body = new LocalizedValue(LocaleUtil.US);

		body.addString(LocaleUtil.US, "Body Text");
		body.addString(LocaleUtil.BRAZIL, "Texto");

		LocalizedValue title = new LocalizedValue(LocaleUtil.US);

		title.addString(LocaleUtil.US, "Title Text");
		title.addString(LocaleUtil.BRAZIL, "Título");

		return new DDMFormSuccessPageSettings(body, title, true);
	}

	private void _setUpDDMFormJSONSerializer() throws Exception {

		// DDM form field type services tracker

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializer.class, "_ddmFormFieldTypeServicesRegistry");

		field.set(
			_ddmFormJSONSerializer,
			getMockedDDMFormFieldTypeServicesRegistry());

		// JSON factory

		field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializer.class, "_jsonFactory");

		field.set(_ddmFormJSONSerializer, new JSONFactoryImpl());
	}

	private final DDMFormJSONSerializer _ddmFormJSONSerializer =
		new DDMFormJSONSerializer();
	private final DDMFormFieldType _defaultDDMFormFieldType = Mockito.mock(
		DDMFormFieldType.class);

}