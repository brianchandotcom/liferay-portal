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

package com.liferay.dynamic.data.lists.form.web.internal.notification;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.HtmlImpl;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class DDLFormEmailNotificationSenderTest {

	@Before
	public void setUp() throws Exception {
		setUpDDLFormEmailNotificationSender();
		setUpDDMFormFieldTypeServicesTracker();
		setUpHtmlUtil();
	}

	@Test
	public void testGetField() {
		DDMFormValues ddmFormValues = createDDMFormValues(
			new UnlocalizedValue("test"));

		Map<String, Object> fieldLabelValueMap =
			_ddlFormEmailNotificationSender.getField(
				ddmFormValues.getDDMFormFieldValues(), Locale.US);

		Assert.assertEquals(
			fieldLabelValueMap.toString(), 2, fieldLabelValueMap.size());

		Assert.assertTrue(fieldLabelValueMap.containsKey("label"));
		Assert.assertTrue(fieldLabelValueMap.containsKey("value"));

		Assert.assertNull(fieldLabelValueMap.get("label"));
		Assert.assertEquals("test", fieldLabelValueMap.get("value"));
	}

	@Test
	public void testGetFieldWithNullValue() {
		DDMFormValues ddmFormValues = createDDMFormValues(null);

		Map<String, Object> fieldLabelValueMap =
			_ddlFormEmailNotificationSender.getField(
				ddmFormValues.getDDMFormFieldValues(), Locale.US);

		Assert.assertEquals(
			fieldLabelValueMap.toString(), 2, fieldLabelValueMap.size());

		Assert.assertTrue(fieldLabelValueMap.containsKey("label"));
		Assert.assertTrue(fieldLabelValueMap.containsKey("value"));

		Assert.assertNull(fieldLabelValueMap.get("label"));
		Assert.assertEquals(StringPool.BLANK, fieldLabelValueMap.get("value"));
	}

	protected DDMFormValues createDDMFormValues(Value value) {
		DDMFormField ddmFormField = new DDMFormField("TextField", "text");

		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("a1hd");
		ddmFormFieldValue.setName("TextField");
		ddmFormFieldValue.setValue(value);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		ddmFormValues.setDefaultLocale(Locale.US);

		return ddmFormValues;
	}

	protected void setUpDDLFormEmailNotificationSender() throws Exception {
		_ddlFormEmailNotificationSender = new DDLFormEmailNotificationSender();

		MemberMatcher.field(
			DDLFormEmailNotificationSender.class,
			"_ddmFormFieldTypeServicesTracker"
		).set(
			_ddlFormEmailNotificationSender, _ddmFormFieldTypeServicesTracker
		);
	}

	protected void setUpDDMFormFieldTypeServicesTracker() {
		PowerMockito.when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				Matchers.anyString())
		).thenReturn(
			_defaultDDMFormFieldValueRenderer
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private DDLFormEmailNotificationSender _ddlFormEmailNotificationSender;

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

}