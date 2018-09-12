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

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.DDMFormEvaluatorImpl;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.util.DDMFormFieldTemplateContextContributorTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({ResourceBundleLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	"com.liferay.portal.kernel.util.ResourceBundleLoaderUtil"
)
public class DDMFormPagesTemplateContextFactoryTest extends PowerMockito {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		setUpHtmlUtil();
		setUpLanguageUtil();
		setUpResourceBundleLoaderUtil();
		setUpResourceBundleUtil();
		setUpDDMFormTemplateContextFactoryUtil();
	}

	@Test
	public void testDisablePages() throws Exception {

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

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 2", "Page 2 Description", "Field2");

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 3", "Page 3 Description", "Field3");

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
				ddmForm, ddmFormLayout, ddmFormValues, false, false, false);

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
	public void testExtractHTMLTagsFromCheckboxMultipleFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"),
				"checkbox-multiple", "string", false, false, true,
				String.format(_HTML_WRAPPER, "tip"),
				String.format(_HTML_WRAPPER, "option")));

		mockDDMFormFieldTypeServicesTracker(
			"checkbox-multiple",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createCheckboxMultipleDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals("option", optionField.get("label"));

		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
	}

	@Test
	public void testExtractHTMLTagsFromDateFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "date",
				"string", false, false, true,
				String.format(_HTML_WRAPPER, "tip")));

		mockDDMFormFieldTypeServicesTracker(
			"date",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createDateDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));
		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
	}

	@Test
	public void testExtractHTMLTagsFromGridFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createGridDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "grid",
				"string", false, false, true,
				String.format(_HTML_WRAPPER, "tip"),
				String.format(_HTML_WRAPPER, "option")));

		mockDDMFormFieldTypeServicesTracker(
			"grid",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createGridDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Field1", new UnlocalizedValue("{}")));

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormValues, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));

		List<Map<String, String>> columns =
			(List<Map<String, String>>)fieldTemplateContext.get("columns");

		Map<String, String> columnField = columns.get(0);

		Assert.assertEquals("option", columnField.get("label"));

		List<Map<String, String>> rows =
			(List<Map<String, String>>)fieldTemplateContext.get("rows");

		Map<String, String> rowField = rows.get(0);

		Assert.assertEquals("option", rowField.get("label"));

		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
	}

	@Test
	public void testExtractHTMLTagsFromNumericFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createNumericDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "integer",
				false, false, true, String.format(_HTML_WRAPPER, "tip"),
				String.format(_HTML_WRAPPER, "placeHolder"),
				String.format(_HTML_WRAPPER, "toolTip")));

		mockDDMFormFieldTypeServicesTracker(
			"numeric",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createNumericDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));
		Assert.assertEquals(
			"placeHolder", fieldTemplateContext.get("placeholder"));
		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
		Assert.assertEquals("toolTip", fieldTemplateContext.get("tooltip"));
	}

	@Test
	public void testExtractHTMLTagsFromPageDescription() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage1 = createDDMFormLayoutPage(
			"titlePage", String.format(_HTML_WRAPPER, "descriptionPage"));

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage1);

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> pageTemplateContext =
			(Map<String, Object>)pages.get(0);

		Assert.assertEquals(
			"descriptionPage", pageTemplateContext.get("description"));
	}

	@Test
	public void testExtractHTMLTagsFromPageTitle() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage1 = createDDMFormLayoutPage(
			String.format(_HTML_WRAPPER, "titlePage"), "descriptionPage");

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage1);

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> pageTemplateContext =
			(Map<String, Object>)pages.get(0);

		Assert.assertEquals("titlePage", pageTemplateContext.get("title"));
	}

	@Test
	public void testExtractHTMLTagsFromRadioFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "radio",
				"string", false, false, true,
				String.format(_HTML_WRAPPER, "tip"),
				StringBundler.concat(
					StringPool.OPEN_BRACKET,
					String.format(_HTML_WRAPPER, "predefinedValue"),
					StringPool.CLOSE_BRACKET),
				String.format(_HTML_WRAPPER, "option")));

		mockDDMFormFieldTypeServicesTracker(
			"radio",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createRadioDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));
		Assert.assertEquals("option", optionField.get("label"));

		Object predefinedValue = fieldTemplateContext.get("predefinedValue");

		Assert.assertEquals("predefinedValue", predefinedValue.toString());

		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
	}

	@Test
	public void testExtractHTMLTagsFromSelectFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "select",
				"string", false, false, true,
				String.format(_HTML_WRAPPER, "tip"),
				String.format(_HTML_WRAPPER, "option")));

		mockDDMFormFieldTypeServicesTracker(
			"select",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createSelectDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals("option", optionField.get("label"));

		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
	}

	@Test
	public void testExtractHTMLTagsFromTextFieldTemplateContext()
		throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", String.format(_HTML_WRAPPER, "label"), "text",
				"string", false, false, true,
				String.format(_HTML_WRAPPER, "tip"),
				String.format(_HTML_WRAPPER, "predefinedValue"),
				String.format(_HTML_WRAPPER, "placeHolder"),
				String.format(_HTML_WRAPPER, "toolTip"),
				String.format(_HTML_WRAPPER, "option")));

		mockDDMFormFieldTypeServicesTracker(
			"text",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createTextDDMFormFieldTemplateContextContributor());

		// Dynamic data mapping form layout

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals("label", fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals("option", optionField.get("label"));

		Assert.assertEquals(
			"placeHolder", fieldTemplateContext.get("placeholder"));
		Assert.assertEquals(
			"predefinedValue", fieldTemplateContext.get("predefinedValue"));
		Assert.assertEquals("tip", fieldTemplateContext.get("tip"));
		Assert.assertEquals("toolTip", fieldTemplateContext.get("tooltip"));
	}

	@Test
	public void testOnePageThreeRows() throws Exception {

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
				ddmForm, ddmFormLayout, null, false, true, false);

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
	public void testRequiredFieldsWithoutRequiredFieldsWarning()
		throws Exception {

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

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1", "Field2");

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 2", "Page 2 Description", "Field3");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, false, false);

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
	public void testRequiredFieldsWithRequiredFieldsWarning() throws Exception {

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

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 1", "Page 1 Description", "Field1", "Field2");

		ddmFormLayout = createDDMFormLayoutPage(
			ddmFormLayout, "Page 2", "Page 2 Description", "Field3");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, null, false, true, false);

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

	protected DDMFormLayout createDDMFormLayoutPage(
		DDMFormLayout ddmFormLayout, String pageTitle, String pageDescription,
		String... ddmFormFieldName) {

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			pageTitle, pageDescription);

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			createDDMFormLayoutColumns(ddmFormFieldName));

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		return ddmFormLayout;
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
				boolean showRequiredFieldsWarning, boolean viewMode)
		throws Exception {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setLocale(_LOCALE);
		ddmFormRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormRenderingContext.setReadOnly(ddmFormReadOnly);
		ddmFormRenderingContext.setShowRequiredFieldsWarning(
			showRequiredFieldsWarning);
		ddmFormRenderingContext.setViewMode(viewMode);

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			new DDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			getDDMFormEvaluator());
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormPagesTemplateContextFactory;
	}

	protected DDMFormEvaluator getDDMFormEvaluator() throws Exception {
		DDMFormEvaluator ddmFormEvaluator = new DDMFormEvaluatorImpl();

		field(
			DDMFormEvaluatorImpl.class, "_ddmExpressionFactory"
		).set(
			ddmFormEvaluator, new DDMExpressionFactoryImpl()
		);

		field(
			DDMFormEvaluatorImpl.class, "_ddmFormFieldTypeServicesTracker"
		).set(
			ddmFormEvaluator, _ddmFormFieldTypeServicesTracker
		);

		return ddmFormEvaluator;
	}

	protected Map<String, Object> getFieldTemplateContext(List<Object> pages) {
		Map<String, Object> page1 = (Map<String, Object>)pages.get(0);

		List<Object> rows = (List<Object>)page1.get("rows");

		Map<String, Object> row1 = (Map<String, Object>)rows.get(0);

		List<Object> columnsRow1 = (List<Object>)row1.get("columns");

		Map<String, Object> column1Row1 = (Map<String, Object>)columnsRow1.get(
			0);

		List<Object> fieldsColumn1Row1 = (List<Object>)column1Row1.get(
			"fields");

		return (Map<String, Object>)fieldsColumn1Row1.get(0);
	}

	protected void mockDDMFormFieldTypeServicesTracker(
		String type,
		DDMFormFieldTemplateContextContributor
			ddmFormFieldTemplateContextContributor) {

		when(
			_ddmFormFieldTypeServicesTracker.
				getDDMFormFieldTemplateContextContributor(Matchers.eq(type))
		).thenReturn(
			ddmFormFieldTemplateContextContributor
		);
	}

	protected void setUpDDMFormTemplateContextFactoryUtil() {
		_request = Mockito.mock(HttpServletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathThemeImages(StringPool.BLANK);

		when(
			(ThemeDisplay)_request.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpLanguageUtil() {
		Language language = mock(Language.class);

		whenLanguageGet(
			language, LocaleUtil.US, "this-field-is-required",
			"This field is required.");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void setUpResourceBundleLoaderUtil() {
		mockStatic(ResourceBundleLoaderUtil.class);

		ResourceBundleLoader portalResourceBundleLoader = mock(
			ResourceBundleLoader.class);

		when(
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader()
		).thenReturn(
			portalResourceBundleLoader
		);
	}

	protected void setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	protected void whenLanguageGet(
		Language language, Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.any(ResourceBundle.class), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private static final String _HTML_WRAPPER = "<a>%s</a>";

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _PORTLET_NAMESPACE = StringUtil.randomString();

	private final DDMFormFieldTemplateContextContributorTestHelper
		_ddmFormFieldTemplateContextContributorTestHelper =
			new DDMFormFieldTemplateContextContributorTestHelper();

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private HttpServletRequest _request;

}