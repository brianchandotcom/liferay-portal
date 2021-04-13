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

package com.liferay.dynamic.data.mapping.render;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.internal.render.CheckboxDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.DateDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.DecimalDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.DocumentLibraryDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.GeolocationDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.IntegerDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.LinkToPageDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.SelectDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.internal.render.TextDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.CalendarFactoryImpl;
import com.liferay.portal.util.DateFormatFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.HtmlImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({DLAppLocalServiceUtil.class, LayoutServiceUtil.class})
public class DDMFormFieldValueRendererTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpCalendarFactoryUtil();
		setUpDateFormatFactoryUtil();
		setUpDLAppLocalServiceUtil();
		setUpFastDateFormatFactoryUtil();
		setUpHtmlUtil();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpLayoutServiceUtil();
		setUpLocaleUtil();
	}

	@Test
	public void testCheckboxFieldValueRendererWithoutRepeatableValues() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Checkbox", createLocalizedValue("false", "true", LocaleUtil.US));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new CheckboxDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("No", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("Sim", renderedValue);
	}

	@Test
	public void testCheckboxFieldValueRendererWithRepeatableValues() {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Checkbox",
				createLocalizedValue("false", "true", LocaleUtil.US)));

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Checkbox",
				createLocalizedValue("true", "true", LocaleUtil.US)));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new CheckboxDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals("No, Yes", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.BRAZIL);

		Assert.assertEquals("Sim, Sim", renderedValue);
	}

	@Test
	public void testDateFieldValueRenderer() {
		String valueString = "2014-10-22";

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Date", new UnlocalizedValue(valueString));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new DateDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("10/22/2014", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("22/10/2014", renderedValue);
	}

	@Test
	public void testDateFieldValueRendererWithEmptyValue() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Date", new UnlocalizedValue(StringPool.BLANK));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new DateDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals(StringPool.BLANK, renderedValue);
	}

	@Test
	public void testDecimalFieldValueRenderer() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Decimal", createLocalizedValue("1.2", "1.2", LocaleUtil.US));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new DecimalDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("1.2", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("1,2", renderedValue);
	}

	@Test
	public void testDecimalFieldValueRendererWithoutDefaultValue() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Decimal",
			createLocalizedValue(StringPool.BLANK, "1,2", LocaleUtil.US));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new DecimalDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("0", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("1,2", renderedValue);
	}

	@Test
	public void testDocumentLibraryFieldValueRenderer() {
		JSONObject jsonObject = JSONUtil.put(
			"groupId", RandomTestUtil.randomLong()
		).put(
			"uuid", RandomTestUtil.randomString()
		);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"DocumentLibrary", new UnlocalizedValue(jsonObject.toString()));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new DocumentLibraryDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("File Entry Title", renderedValue);
	}

	@Test
	public void testGeolocationFieldValueRenderer() {
		JSONObject jsonObject = JSONUtil.put(
			"latitude", 9.8765
		).put(
			"longitude", 1.2345
		);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Geolocation", new UnlocalizedValue(jsonObject.toString()));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new GeolocationDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.SPAIN);

		if (JavaDetector.isJDK8()) {
			Assert.assertEquals(
				"Latitud: 9,877, Longitud: 1,234", renderedValue);
		}
		else {
			Assert.assertEquals(
				"Latitud: 9,876, Longitud: 1,234", renderedValue);
		}

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		if (JavaDetector.isJDK8()) {
			Assert.assertEquals(
				"Latitude: 9.877, Longitude: 1.234", renderedValue);
		}
		else {
			Assert.assertEquals(
				"Latitude: 9.876, Longitude: 1.234", renderedValue);
		}
	}

	@Test
	public void testIntegerFieldValueRenderer() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Integer", createLocalizedValue("1", "2", LocaleUtil.US));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new IntegerDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("1", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("2", renderedValue);
	}

	@Test
	public void testLinkToPageFieldValueRenderer() {
		JSONObject jsonObject = JSONUtil.put(
			"groupId", RandomTestUtil.randomLong()
		).put(
			"layoutId", RandomTestUtil.randomLong()
		).put(
			"privateLayout", RandomTestUtil.randomBoolean()
		);

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"LinkToPage", new UnlocalizedValue(jsonObject.toString()));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new LinkToPageDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("Layout Name", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("Nome da Pagina", renderedValue);
	}

	@Test
	public void testNumberFieldValueRenderer() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Number", createLocalizedValue("1", "2.1", LocaleUtil.US));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new IntegerDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("1", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("2,1", renderedValue);
	}

	@Test(expected = ValueAccessorException.class)
	public void testSelectFieldValueRendererWithInvalidValue()
		throws Exception {

		DDMForm ddmForm = createDDMFormWithSelectField();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Select", new UnlocalizedValue("Invalid JSON")));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new SelectDDMFormFieldValueRenderer();

		ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);
	}

	@Test
	public void testSelectFieldValueRendererWithoutRepeatableValues() {
		DDMForm ddmForm = createDDMFormWithSelectField();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		JSONArray jsonArray = toJSONArray("Option 1", "Option 2");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Select", new UnlocalizedValue(jsonArray.toString())));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new SelectDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals("English Label 1, English Label 2", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.BRAZIL);

		Assert.assertEquals(
			"Portuguese Label 1, Portuguese Label 2", renderedValue);
	}

	@Test
	public void testSelectFieldValueRendererWithRepeatableValues() {
		DDMForm ddmForm = createDDMFormWithSelectField();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		JSONArray jsonArray = toJSONArray("Option 1", "Option 2");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Select", new UnlocalizedValue(jsonArray.toString())));

		jsonArray = toJSONArray("Option 1");

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Select", new UnlocalizedValue(jsonArray.toString())));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new SelectDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals(
			"English Label 1, English Label 2, English Label 1", renderedValue);

		renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.BRAZIL);

		Assert.assertEquals(
			"Portuguese Label 1, Portuguese Label 2, Portuguese Label 1",
			renderedValue);
	}

	@Test
	public void testTextFieldValueRendererWithoutRepeatableValues() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			"Text", new UnlocalizedValue("Scott Joplin"));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new TextDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("Scott Joplin", renderedValue);
	}

	@Test
	public void testTextFieldValueRendererWithRepeatableValues() {
		DDMFormValues ddmFormValues = new DDMFormValues(null);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Text", new UnlocalizedValue("Charlie Parker")));

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"Text", new UnlocalizedValue("Dave Brubeck")));

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			new TextDDMFormFieldValueRenderer();

		String renderedValue = ddmFormFieldValueRenderer.render(
			ddmFormValues.getDDMFormFieldValues(), LocaleUtil.US);

		Assert.assertEquals("Charlie Parker, Dave Brubeck", renderedValue);
	}

	protected DDMForm createDDMFormWithSelectField() {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmFormField.setDataType("string");
		ddmFormField.setRepeatable(true);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Option 1", LocaleUtil.US, "English Label 1");
		ddmFormFieldOptions.addOptionLabel(
			"Option 2", LocaleUtil.US, "English Label 2");

		ddmFormFieldOptions.addOptionLabel(
			"Option 1", LocaleUtil.BRAZIL, "Portuguese Label 1");
		ddmFormFieldOptions.addOptionLabel(
			"Option 2", LocaleUtil.BRAZIL, "Portuguese Label 2");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	protected void setUpCalendarFactoryUtil() {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());
	}

	protected void setUpDateFormatFactoryUtil() {
		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(new DateFormatFactoryImpl());
	}

	protected void setUpDLAppLocalServiceUtil() throws Exception {
		mockStatic(DLAppLocalServiceUtil.class);

		FileEntry fileEntry = mock(FileEntry.class);

		when(
			fileEntry.getTitle()
		).thenReturn(
			"File Entry Title"
		);

		when(
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				Matchers.anyString(), Matchers.anyLong())
		).thenReturn(
			fileEntry
		);
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpLayoutServiceUtil() throws Exception {
		mockStatic(LayoutServiceUtil.class);

		when(
			LayoutServiceUtil.getLayoutName(
				Matchers.anyLong(), Matchers.anyBoolean(), Matchers.anyLong(),
				Matchers.eq("en_US"))
		).thenReturn(
			"Layout Name"
		);

		when(
			LayoutServiceUtil.getLayoutName(
				Matchers.anyLong(), Matchers.anyBoolean(), Matchers.anyLong(),
				Matchers.eq("pt_BR"))
		).thenReturn(
			"Nome da Pagina"
		);
	}

	protected JSONArray toJSONArray(String... values) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String value : values) {
			jsonArray.put(value);
		}

		return jsonArray;
	}

}