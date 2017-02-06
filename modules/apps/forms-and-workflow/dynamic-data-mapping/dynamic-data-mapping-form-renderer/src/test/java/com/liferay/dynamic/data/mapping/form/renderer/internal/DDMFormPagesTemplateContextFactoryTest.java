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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.DDMFormEvaluatorImpl;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;

/**
 * @author Marcellus Tavares
 */
public class DDMFormPagesTemplateContextFactoryTest {

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testDisablePages() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormRule(
			new DDMFormRule("TRUE", Arrays.asList("jumpPage(0, 2)")));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", "Field1", "text", "string", false, false, true));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field2", "Field2", "text", "string", false, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field3", "Field3", "text", "string", false, false, false));

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage1 = createDDMFormLayoutPage(
			"Page 1", "Page 1 Description");

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field1"));

		ddmFormLayoutPage1.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage1);

		DDMFormLayoutPage ddmFormLayoutPage2 = createDDMFormLayoutPage(
			"Page 2", "Page 2 Description");

		ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field2"));

		ddmFormLayoutPage2.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage2);

		DDMFormLayoutPage ddmFormLayoutPage3 = createDDMFormLayoutPage(
			"Page 3", "Page 3 Description");

		ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field3"));

		ddmFormLayoutPage3.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage3);

		// Dynamic data mapping form values

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field1", "A"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field2", ""));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"Field3", ""));

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormValues, false, false);

		List<Object> pagesTemplateContext =
			ddmFormPagesTemplateContextFactory.create();

		Assert.assertEquals(
			pagesTemplateContext.toString(), 3, pagesTemplateContext.size());

		Map<String, Object> page1TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(0);

		Assert.assertTrue(MapUtil.getBoolean(page1TemplateContext, "enabled"));

		Map<String, Object> page2TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(1);

		Assert.assertFalse(MapUtil.getBoolean(page2TemplateContext, "enabled"));

		Map<String, Object> page3TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(2);

		Assert.assertTrue(MapUtil.getBoolean(page3TemplateContext, "enabled"));
	}

	@Test
	public void testOnePageThreeRows() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			"Field1", "Field2", "Field3", "Field4", "Field5");

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			"Page 1", "Page 1 Description");

		DDMFormLayoutRow ddmFormLayoutRow1 = new DDMFormLayoutRow();

		ddmFormLayoutRow1.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field1", "Field2"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow1);

		DDMFormLayoutRow ddmFormLayoutRow2 = new DDMFormLayoutRow();

		ddmFormLayoutRow2.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field3"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow2);

		DDMFormLayoutRow ddmFormLayoutRow3 = new DDMFormLayoutRow();

		ddmFormLayoutRow3.addDDMFormLayoutColumn(
			new DDMFormLayoutColumn(12, "Field4", "Field5"));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow3);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Assert.assertEquals(pages.toString(), 1, pages.size());

		Map<String, Object> page1 = (Map<String, Object>)pages.get(0);

		Assert.assertEquals("Page 1", page1.get("title"));
		Assert.assertEquals("Page 1 Description", page1.get("description"));

		List<Object> rows = (List<Object>)page1.get("rows");

		Assert.assertEquals(rows.toString(), 3, rows.size());

		Map<String, Object> row1 = (Map<String, Object>)rows.get(0);

		List<Object> columnsRow1 = (List<Object>)row1.get("columns");

		Assert.assertEquals(columnsRow1.toString(), 2, columnsRow1.size());

		assertColumnSize(6, (Map<String, Object>)columnsRow1.get(0));
		assertColumnSize(6, (Map<String, Object>)columnsRow1.get(1));

		Map<String, Object> row2 = (Map<String, Object>)rows.get(1);

		List<Object> columnsRow2 = (List<Object>)row2.get("columns");

		Assert.assertEquals(columnsRow2.toString(), 1, columnsRow2.size());

		assertColumnSize(12, (Map<String, Object>)columnsRow2.get(0));

		Map<String, Object> row3 = (Map<String, Object>)rows.get(2);

		List<Object> columnsRow3 = (List<Object>)row3.get("columns");

		Assert.assertEquals(columnsRow3.toString(), 1, columnsRow3.size());

		assertColumnSize(12, (Map<String, Object>)columnsRow3.get(0));
	}

	@Test
	public void testRequiredFieldsWithoutRequiredFieldsWarning() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", "Field1", "text", "string", false, false, true));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field2", "Field2", "text", "string", false, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field3", "Field3", "text", "string", false, false, false));

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage1 = createDDMFormLayoutPage(
			"Page 1", "Page 1 Description");

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field1", "Field2"));

		ddmFormLayoutPage1.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage1);

		DDMFormLayoutPage ddmFormLayoutPage2 = createDDMFormLayoutPage(
			"Page 2", "Page 2 Description");

		ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field3"));

		ddmFormLayoutPage2.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage2);

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, false);

		List<Object> pagesTemplateContext =
			ddmFormPagesTemplateContextFactory.create();

		Assert.assertEquals(
			pagesTemplateContext.toString(), 2, pagesTemplateContext.size());

		Map<String, Object> page1TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(0);

		Assert.assertFalse(
			MapUtil.getBoolean(
				page1TemplateContext, "showRequiredFieldsWarning"));

		Map<String, Object> page2TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(1);

		Assert.assertFalse(
			MapUtil.getBoolean(
				page2TemplateContext, "showRequiredFieldsWarning"));
	}

	@Test
	public void testRequiredFieldsWithRequiredFieldsWarning() {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", "Field1", "text", "string", false, false, true));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field2", "Field2", "text", "string", false, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field3", "Field3", "text", "string", false, false, false));

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage1 = createDDMFormLayoutPage(
			"Page 1", "Page 1 Description");

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field1", "Field2"));

		ddmFormLayoutPage1.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage1);

		DDMFormLayoutPage ddmFormLayoutPage2 = createDDMFormLayoutPage(
			"Page 2", "Page 2 Description");

		ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns("Field3"));

		ddmFormLayoutPage2.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage2);

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true);

		List<Object> pagesTemplateContext =
			ddmFormPagesTemplateContextFactory.create();

		Assert.assertEquals(
			pagesTemplateContext.toString(), 2, pagesTemplateContext.size());

		Map<String, Object> page1TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(0);

		Assert.assertTrue(
			MapUtil.getBoolean(
				page1TemplateContext, "showRequiredFieldsWarning"));

		Map<String, Object> page2TemplateContext =
			(Map<String, Object>)pagesTemplateContext.get(1);

		Assert.assertFalse(
			MapUtil.getBoolean(
				page2TemplateContext, "showRequiredFieldsWarning"));
	}

	protected void assertColumnSize(
		int expectedSize, Map<String, Object> columnTemplateContex) {

		Assert.assertEquals(
			expectedSize, MapUtil.getInteger(columnTemplateContex, "size"));
	}

	protected DDMFormLayoutColumn createDDMFormLayoutColumn(
		String ddmFormFieldName, int size) {

		return new DDMFormLayoutColumn(size, ddmFormFieldName);
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... ddmFormFieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int ddmFormLayoutColumnSize =
			DDMFormLayoutColumn.FULL / ddmFormFieldNames.length;

		for (String ddmFormFieldName : ddmFormFieldNames) {
			ddmFormLayoutColumns.add(
				createDDMFormLayoutColumn(
					ddmFormFieldName, ddmFormLayoutColumnSize));
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutPage createDDMFormLayoutPage(
		String titleString, String descriptionString) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue title = new LocalizedValue(_LOCALE);

		title.addString(_LOCALE, titleString);

		ddmFormLayoutPage.setTitle(title);

		LocalizedValue description = new LocalizedValue(_LOCALE);

		description.addString(_LOCALE, descriptionString);

		ddmFormLayoutPage.setDescription(description);

		return ddmFormLayoutPage;
	}

	protected DDMFormPagesTemplateContextFactory
		createDDMFormPagesTemplateContextFactory(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormValues ddmFormValues, boolean ddmFormReadOnly,
			boolean showRequiredFieldsWarning) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		ddmFormRenderingContext.setLocale(_LOCALE);
		ddmFormRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormRenderingContext.setReadOnly(ddmFormReadOnly);
		ddmFormRenderingContext.setShowRequiredFieldsWarning(
			showRequiredFieldsWarning);

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			new DDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			getDDMFormEvaluator());
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			getDDMFormFieldTypesServicesTracker());

		return ddmFormPagesTemplateContextFactory;
	}

	protected DDMFormEvaluator getDDMFormEvaluator() {
		DDMFormEvaluator ddmFormEvaluator = new DDMFormEvaluatorImpl();

		ReflectionTestUtil.setFieldValue(
			ddmFormEvaluator, "_ddmExpressionFactory",
			new DDMExpressionFactoryImpl());

		return ddmFormEvaluator;
	}

	protected DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypesServicesTracker() {

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		return ddmFormFieldTypeServicesTracker;
	}

	protected void setUpLanguageUtil() {
		Language language = mock(Language.class);

		whenLanguageGet(
			language, LocaleUtil.US, "this-field-is-required",
			"This field is required.");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void whenLanguageGet(
		Language language, Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _PORTLET_NAMESPACE = StringUtil.randomString();

}