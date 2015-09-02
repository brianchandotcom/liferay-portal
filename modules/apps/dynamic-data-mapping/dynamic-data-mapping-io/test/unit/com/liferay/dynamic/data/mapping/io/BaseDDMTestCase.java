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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.io.internal.DDMFormFieldTypesJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormLayoutJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormLayoutJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormValuesJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormValuesJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormXSDDeserializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTrackerUtil;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldTypeSettingsTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(
	{
		DDMFormFieldTypesJSONSerializerUtil.class,
		DDMFormFieldTypeServicesTrackerUtil.class,
		DDMFormJSONDeserializerUtil.class, DDMFormJSONSerializerUtil.class,
		DDMFormLayoutJSONDeserializerUtil.class,
		DDMFormLayoutJSONSerializerUtil.class,
		DDMFormValuesJSONDeserializerUtil.class,
		DDMFormValuesJSONSerializerUtil.class, DDMFormXSDDeserializerUtil.class,
		LocaleUtil.class
	}
)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{
		"com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil",
		"com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil",
		"com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTrackerUtil"
	}

)
public class BaseDDMTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormFieldTypesJSONSerializerUtil();
		setUpDDMFormFieldTypeServicesTrackerUtil();
		setUpDDMFormJSONDeserializerUtil();
		setUpDDMFormJSONSerializerUtil();
		setUpDDMFormLayoutJSONDeserializerUtil();
		setUpDDMFormLayoutJSONSerializerUtil();
		setUpDDMFormValuesJSONDeserializerUtil();
		setUpDDMFormValuesJSONSerializerUtil();
		setUpDDMFormXSDDeserializerUtil();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
	}

	protected DDMFormLayoutColumn createDDMFormLayoutColumn(
		int size, String... fieldNames) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			size, fieldNames);

		return ddmFormLayoutColumn;
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... fieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int size = 12 / fieldNames.length;

		for (String fieldName : fieldNames) {
			ddmFormLayoutColumns.add(new DDMFormLayoutColumn(size, fieldName));
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn... ddmFormLayoutColumns) {

		return createDDMFormLayoutRow(Arrays.asList(ddmFormLayoutColumns));
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);

		return ddmFormLayoutRow;
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpDDMFormFieldTypeServicesTrackerUtil() {
		setUpDefaultDDMFormFieldType();

		mockStatic(DDMFormFieldTypeServicesTrackerUtil.class);

		when(
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldType(
				Matchers.anyString())
		).thenReturn(
			_defaultDDMFormFieldType
		);
	}

	protected void setUpDDMFormFieldTypesJSONSerializerUtil() throws Exception {
		mockStatic(DDMFormFieldTypesJSONSerializerUtil.class);

		when(
			DDMFormFieldTypesJSONSerializerUtil.
				getDDMFormFieldTypesJSONSerializer()
		).thenReturn(
			new DDMFormFieldTypesJSONSerializerImpl()
		);

		when(
			DDMFormFieldTypesJSONSerializerUtil.serialize(Matchers.anyList())
		).thenCallRealMethod();
	}

	protected void setUpDDMFormJSONDeserializerUtil() throws Exception {
		mockStatic(DDMFormJSONDeserializerUtil.class);

		when(
			DDMFormJSONDeserializerUtil.getDDMFormJSONDeserializer()
		).thenReturn(
			new DDMFormJSONDeserializerImpl()
		);

		when(
			DDMFormJSONDeserializerUtil.deserialize(Matchers.anyString())
		).thenCallRealMethod();
	}

	protected void setUpDDMFormJSONSerializerUtil() {
		mockStatic(DDMFormJSONSerializerUtil.class);

		when(
			DDMFormJSONSerializerUtil.getDDMFormJSONSerializer()
		).thenReturn(
			new DDMFormJSONSerializerImpl()
		);

		when(
			DDMFormJSONSerializerUtil.serialize(Matchers.any(DDMForm.class))
		).thenCallRealMethod();
	}

	protected void setUpDDMFormLayoutJSONDeserializerUtil() throws Exception {
		mockStatic(DDMFormLayoutJSONDeserializerUtil.class);

		when(
			DDMFormLayoutJSONDeserializerUtil.getDDMFormLayoutJSONDeserializer()
		).thenReturn(
			new DDMFormLayoutJSONDeserializerImpl()
		);

		when(
			DDMFormLayoutJSONDeserializerUtil.deserialize(Matchers.anyString())
		).thenCallRealMethod();
	}

	protected void setUpDDMFormLayoutJSONSerializerUtil() {
		mockStatic(DDMFormLayoutJSONSerializerUtil.class);

		when(
			DDMFormLayoutJSONSerializerUtil.getDDMFormLayoutJSONSerializer()
		).thenReturn(
			new DDMFormLayoutJSONSerializerImpl()
		);

		when(
			DDMFormLayoutJSONSerializerUtil.serialize(
				Matchers.any(DDMFormLayout.class))
		).thenCallRealMethod();
	}

	protected void setUpDDMFormValuesJSONDeserializerUtil() throws Exception {
		mockStatic(DDMFormValuesJSONDeserializerUtil.class);

		when(
			DDMFormValuesJSONDeserializerUtil.getDDMFormValuesJSONDeserializer()
		).thenReturn(
			new DDMFormValuesJSONDeserializerImpl()
		);

		when(
			DDMFormValuesJSONDeserializerUtil.deserialize(
				Matchers.any(DDMForm.class), Matchers.anyString())
		).thenCallRealMethod();
	}

	protected void setUpDDMFormValuesJSONSerializerUtil() {
		mockStatic(DDMFormValuesJSONSerializerUtil.class);

		when(
			DDMFormValuesJSONSerializerUtil.getDDMFormValuesJSONSerializer()
		).thenReturn(
			new DDMFormValuesJSONSerializerImpl()
		);

		when(
			DDMFormValuesJSONSerializerUtil.serialize(
				Matchers.any(DDMFormValues.class))
		).thenCallRealMethod();
	}

	protected void setUpDDMFormXSDDeserializerUtil() throws Exception {
		mockStatic(DDMFormXSDDeserializerUtil.class);

		when(
			DDMFormXSDDeserializerUtil.getDDMFormXSDDeserializer()
		).thenReturn(
			new DDMFormXSDDeserializerImpl()
		);

		when(
			DDMFormXSDDeserializerUtil.deserialize(Matchers.anyString())
		).thenCallRealMethod();
	}

	protected void setUpDefaultDDMFormFieldType() {
		when (
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

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLanguageUtil() {
		Set<Locale> availableLocales = SetUtil.fromArray(
			new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US});

		whenLanguageGetAvailableLocalesThen(availableLocales);

		whenLanguageIsAvailableLocale("en_US");
		whenLanguageIsAvailableLocale("pt_BR");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		when(
			LocaleUtil.toLanguageIds((Locale[])Matchers.any())
		).then(
			new Answer<String[]>() {

				@Override
				public String[] answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					Locale[] locales = (Locale[])args[0];

					String[] languageIds = new String[locales.length];

					for (int i = 0; i < locales.length; i++) {
						languageIds[i] = LocaleUtil.toLanguageId(locales[i]);
					}

					return languageIds;
				}
			}
		);
	}

	protected void whenLanguageGetAvailableLocalesThen(
		Set<Locale> availableLocales) {

		when(
			language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);
	}

	protected void whenLanguageIsAvailableLocale(String languageId) {
		when(
			language.isAvailableLocale(Matchers.eq(languageId))
		).thenReturn(
			true
		);
	}

	@Mock
	protected Language language;

	@Mock
	private DDMFormFieldType _defaultDDMFormFieldType;

}