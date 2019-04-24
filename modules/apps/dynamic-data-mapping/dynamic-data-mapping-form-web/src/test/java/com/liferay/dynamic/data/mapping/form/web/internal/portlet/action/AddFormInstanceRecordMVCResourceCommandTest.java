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

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.context.DDMFormContextToDDMFormValues;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.ResourceRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Rodrigo Paulino
 */
@RunWith(MockitoJUnitRunner.class)
public class AddFormInstanceRecordMVCResourceCommandTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormContextToDDMFormValues();

		setUpAddFormInstanceRecordMVCResourceCommand();
		setUpDDMFormInstance();
		setUpProps();
		setUpLanguageUtil();
	}

	@Test
	public void testCreateDDMFormValues() throws Exception {
		String serializedDDMFormValues = read("ddm-form-values.json");

		when(
			_resourceRequest.getParameter("serializedDDMFormValues")
		).thenReturn(
			serializedDDMFormValues
		);

		when(
			_language.getLanguageId(_resourceRequest)
		).thenReturn(
			"pt_BR"
		);

		when(
			_language.isAvailableLocale(LocaleUtil.BRAZIL)
		).thenReturn(
			true
		);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField1", true, false, false));
		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"TextField2", true, false, false));

		when(
			_ddmStructure.getDDMForm()
		).thenReturn(
			ddmForm
		);

		LocalizedValue value1 = new LocalizedValue();

		value1.addString(LocaleUtil.BRAZIL, "Texto 1");

		DDMFormFieldValue ddmFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"eBvF8zup", "TextField1", value1);

		LocalizedValue value2 = new LocalizedValue();

		value2.addString(LocaleUtil.BRAZIL, "Texto 2");

		DDMFormFieldValue ddmFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"6VYYLvfJ", "TextField2", value2);

		DDMFormValues ddmFormValues1 =
			DDMFormValuesTestUtil.createDDMFormValues(
				ddmForm, SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL}),
				LocaleUtil.BRAZIL);

		ddmFormValues1.addDDMFormFieldValue(ddmFormFieldValue1);
		ddmFormValues1.addDDMFormFieldValue(ddmFormFieldValue2);

		DDMFormValues ddmFormValues2 =
			_addFormInstanceRecordMVCResourceCommand.createDDMFormValues(
				_ddmFormInstance, _resourceRequest);

		Assert.assertNotEquals(LocaleUtil.getSiteDefault(), LocaleUtil.BRAZIL);
		Assert.assertTrue(Objects.equals(ddmFormValues1, ddmFormValues2));
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpAddFormInstanceRecordMVCResourceCommand()
		throws Exception {

		_addFormInstanceRecordMVCResourceCommand =
			new AddFormInstanceRecordMVCResourceCommand();

		field(
			AddFormInstanceRecordMVCResourceCommand.class,
			"_ddmFormBuilderContextToDDMFormValues"
		).set(
			_addFormInstanceRecordMVCResourceCommand,
			_ddmFormContextToDDMFormValues
		);
	}

	protected void setUpDDMFormContextToDDMFormValues() throws Exception {
		_ddmFormContextToDDMFormValues = new DDMFormContextToDDMFormValues();

		field(
			DDMFormContextToDDMFormValues.class, "jsonFactory"
		).set(
			_ddmFormContextToDDMFormValues, new JSONFactoryImpl()
		);
	}

	protected void setUpDDMFormInstance() throws Exception {
		when(
			_ddmFormInstance.getStructure()
		).thenReturn(
			_ddmStructure
		);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpProps() {
		PropsUtil.setProps(new PropsImpl());
	}

	private AddFormInstanceRecordMVCResourceCommand
		_addFormInstanceRecordMVCResourceCommand;
	private DDMFormContextDeserializer<DDMFormValues>
		_ddmFormContextToDDMFormValues;

	@Mock
	private DDMFormInstance _ddmFormInstance;

	@Mock
	private DDMStructure _ddmStructure;

	@Mock
	private Language _language;

	@Mock
	private ResourceRequest _resourceRequest;

}