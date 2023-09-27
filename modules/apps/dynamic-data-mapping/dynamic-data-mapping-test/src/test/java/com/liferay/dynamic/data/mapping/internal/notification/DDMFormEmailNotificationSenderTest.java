/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.notification;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HtmlImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEmailNotificationSenderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_setUpDDMFormEmailNotificationSender();
		_setUpDDMFormFieldTypeServicesRegistry();
		_setUpHtmlUtil();
	}

	@Test
	public void testGetFieldProperties() {
		DDMFormValues ddmFormValues = _createDDMFormValues(
			_createDDMForm(new DDMFormField("TextField", "text")),
			_createDDMFormFieldValue(
				"a1hd", "TextField", new UnlocalizedValue("test")));

		Map<String, Object> fieldProperties =
			_ddmFormEmailNotificationSender.getFieldProperties(
				ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals(
			fieldProperties.toString(), 2, fieldProperties.size());

		Assert.assertTrue(fieldProperties.containsKey("label"));
		Assert.assertTrue(fieldProperties.containsKey("value"));
		Assert.assertNull(fieldProperties.get("label"));

		Assert.assertEquals(
			"test", String.valueOf(fieldProperties.get("value")));
	}

	@Test
	public void testGetFieldPropertiesNullValue() {
		DDMFormValues ddmFormValues = _createDDMFormValues(
			_createDDMForm(new DDMFormField("TextField", "text")),
			_createDDMFormFieldValue("a1hd", "TextField", null));

		Map<String, Object> fieldProperties =
			_ddmFormEmailNotificationSender.getFieldProperties(
				ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals(
			fieldProperties.toString(), 2, fieldProperties.size());

		Assert.assertTrue(fieldProperties.containsKey("label"));
		Assert.assertTrue(fieldProperties.containsKey("value"));
		Assert.assertNull(fieldProperties.get("label"));

		Assert.assertEquals(
			StringPool.BLANK, String.valueOf(fieldProperties.get("value")));
	}

	@Test
	public void testGetFields() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("TextField", "text");

		ddmFormField.addNestedDDMFormField(
			new DDMFormField("NumericField", "numeric"));

		DDMForm ddmForm = _createDDMForm(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue = _createDDMFormFieldValue(
			"a1hd", "TextField",
			DDMFormValuesTestUtil.createLocalizedValue("test", LocaleUtil.US));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			_createDDMFormFieldValue(
				"uxyj", "NumericField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"1", LocaleUtil.US)));

		DDMFormValues ddmFormValues = _createDDMFormValues(
			ddmForm, ddmFormFieldValue);

		List<Object> fields = _ddmFormEmailNotificationSender.getFields(
			Arrays.asList("TextField"),
			_ddmFormEmailNotificationSender.getDDMFormFieldValuesMap(
				_mockDDMFormInstanceRecord(ddmFormValues)),
			LocaleUtil.US);

		Assert.assertEquals(fields.toString(), 2, fields.size());

		Map<String, Object> fieldProperties = (Map<String, Object>)fields.get(
			0);

		Assert.assertEquals(
			"test", String.valueOf(fieldProperties.get("value")));

		fieldProperties = (Map<String, Object>)fields.get(1);

		Assert.assertEquals("1", String.valueOf(fieldProperties.get("value")));
	}

	private static void _setUpDDMFormEmailNotificationSender()
		throws Exception {

		_ddmFormEmailNotificationSender = new DDMFormEmailNotificationSender();

		ReflectionTestUtil.setFieldValue(
			_ddmFormEmailNotificationSender,
			"_ddmFormFieldTypeServicesRegistry",
			_ddmFormFieldTypeServicesRegistry);
	}

	private static void _setUpDDMFormFieldTypeServicesRegistry() {
		Mockito.when(
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldValueRenderer(
				Mockito.anyString())
		).thenReturn(
			_defaultDDMFormFieldValueRenderer
		);
	}

	private static void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private DDMForm _createDDMForm(DDMFormField ddmFormField) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		String instanceId, String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	private DDMFormValues _createDDMFormValues(
		DDMForm ddmForm, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord(
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = Mockito.mock(
			DDMFormInstanceRecord.class);

		Mockito.when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		return ddmFormInstanceRecord;
	}

	private static DDMFormEmailNotificationSender
		_ddmFormEmailNotificationSender;
	private static final DDMFormFieldTypeServicesRegistry
		_ddmFormFieldTypeServicesRegistry = Mockito.mock(
			DDMFormFieldTypeServicesRegistry.class);
	private static final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

}