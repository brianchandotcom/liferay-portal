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

package com.liferay.dynamic.data.mapping.internal.notification;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.HtmlImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class DDMFormEmailNotificationSenderTest {

	@Before
	public void setUp() throws Exception {
		_setUpDDMFormEmailNotificationSender();
		_setUpDDMFormFieldTypeServicesTracker();
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

	private void _setUpDDMFormEmailNotificationSender() throws Exception {
		_ddmFormEmailNotificationSender = new DDMFormEmailNotificationSender();

		MemberMatcher.field(
			DDMFormEmailNotificationSender.class,
			"_ddmFormFieldTypeServicesTracker"
		).set(
			_ddmFormEmailNotificationSender, _ddmFormFieldTypeServicesTracker
		);
	}

	private void _setUpDDMFormFieldTypeServicesTracker() {
		PowerMockito.when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				Matchers.anyString())
		).thenReturn(
			_defaultDDMFormFieldValueRenderer
		);
	}

	private void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private DDMFormEmailNotificationSender _ddmFormEmailNotificationSender;

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

}