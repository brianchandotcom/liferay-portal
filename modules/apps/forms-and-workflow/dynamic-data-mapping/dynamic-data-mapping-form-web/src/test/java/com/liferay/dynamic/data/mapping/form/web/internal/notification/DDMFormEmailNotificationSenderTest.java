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

package com.liferay.dynamic.data.mapping.form.web.internal.notification;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.UnsafeSanitizedContentOrdainer;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.template.soy.utils.SoyHTMLSanitizer;
import com.liferay.portal.template.soy.utils.SoyRawData;
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
public class DDMFormEmailNotificationSenderTest {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormEmailNotificationSender();
		setUpDDMFormFieldTypeServicesTracker();
		setUpHtmlUtil();
	}

	@Test
	public void testGetField() {
		DDMFormValues ddmFormValues = createDDMFormValues(
			new UnlocalizedValue("test"));

		Map<String, Object> fieldLabelValueMap =
			_ddmFormEmailNotificationSender.getField(
				ddmFormValues.getDDMFormFieldValues(), Locale.US);

		Assert.assertEquals(
			fieldLabelValueMap.toString(), 2, fieldLabelValueMap.size());

		Assert.assertTrue(fieldLabelValueMap.containsKey("label"));
		Assert.assertTrue(fieldLabelValueMap.containsKey("value"));
		Assert.assertNull(fieldLabelValueMap.get("label"));

		SoyRawData soyRawData = (SoyRawData)fieldLabelValueMap.get("value");

		Assert.assertEquals("test", String.valueOf(soyRawData.getValue()));
	}

	@Test
	public void testGetFieldWithNullValue() {
		DDMFormValues ddmFormValues = createDDMFormValues(null);

		Map<String, Object> fieldLabelValueMap =
			_ddmFormEmailNotificationSender.getField(
				ddmFormValues.getDDMFormFieldValues(), Locale.US);

		Assert.assertEquals(
			fieldLabelValueMap.toString(), 2, fieldLabelValueMap.size());

		Assert.assertTrue(fieldLabelValueMap.containsKey("label"));
		Assert.assertTrue(fieldLabelValueMap.containsKey("value"));
		Assert.assertNull(fieldLabelValueMap.get("label"));

		SoyRawData soyRawData = (SoyRawData)fieldLabelValueMap.get("value");

		Assert.assertEquals(
			StringPool.BLANK, String.valueOf(soyRawData.getValue()));
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

	protected void setUpDDMFormEmailNotificationSender() throws Exception {
		_ddmFormEmailNotificationSender = new DDMFormEmailNotificationSender();

		MemberMatcher.field(
			DDMFormEmailNotificationSender.class,
			"_ddmFormFieldTypeServicesTracker"
		).set(
			_ddmFormEmailNotificationSender, _ddmFormFieldTypeServicesTracker
		);

		MemberMatcher.field(
			DDMFormEmailNotificationSender.class, "_soyHTMLSanitizer"
		).set(
			_ddmFormEmailNotificationSender,
			new SoyHTMLSanitizer() {

				@Override
				public Object sanitize(String value) {
					return UnsafeSanitizedContentOrdainer.ordainAsSafe(
						value, SanitizedContent.ContentKind.HTML);
				}

			}
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

	private DDMFormEmailNotificationSender _ddmFormEmailNotificationSender;

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

}