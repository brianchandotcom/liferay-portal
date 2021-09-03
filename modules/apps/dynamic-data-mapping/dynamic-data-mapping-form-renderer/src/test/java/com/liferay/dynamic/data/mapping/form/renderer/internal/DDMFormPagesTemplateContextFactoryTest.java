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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.DDMFormEvaluatorImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.HasGooglePlacesAPIKeyFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.JumpPageFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.SetVisibleFunction;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.util.DDMFormFieldTemplateContextContributorTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.google.places.util.GooglePlacesUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.CalendarFactoryImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.HtmlImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

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
@PrepareForTest(
	{
		GooglePlacesUtil.class, LocaleThreadLocal.class,
		ResourceBundleLoaderUtil.class, ResourceBundleUtil.class
	}
)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	"com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil"
)
public class DDMFormPagesTemplateContextFactoryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpCalendarFactoryUtil();
		setUpDDMFormFieldTypeServicesTracker();
		setUpFastDateFormatFactoryUtil();
		setUpGooglePlacesUtil();
		setUpHtmlUtil();
		setUpHttpServletRequest();
		setUpLanguageResources();
		setUpLanguageUtil();
		setUpLocaleThreadLocal();
		setUpPortalUtil();
		setUpResourceBundle();
		setUpResourceBundleLoaderUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCheckboxMultipleFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");
		String formFieldOption = String.format(_HTML_WRAPPER, "option");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", formFieldLabel, "checkbox-multiple", "string", false,
				false, true, formFieldTip, formFieldOption));

		mockDDMFormFieldTypeServicesTracker(
			"checkbox-multiple",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createCheckboxMultipleDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals(formFieldOption, optionField.get("label"));

		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
	}

	@Test
	public void testDateFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", formFieldLabel, "date", "string", false, false, true,
				formFieldTip));

		mockDDMFormFieldTypeServicesTracker(
			"date",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createDateDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));
		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
	}

	@Test
	public void testDisablePages() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormRule(
			new DDMFormRule(Arrays.asList("jumpPage(0, 2)"), "TRUE"));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", "Field1", "text", "string", false, false, true));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field2", "Field2", "text", "string", false, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field3", "Field3", "text", "string", false, false, false));

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
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 1 Description", "Page 1",
						new String[] {"Field1"}),
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 2 Description", "Page 2",
						new String[] {"Field2"}),
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 3 Description", "Page 3",
						new String[] {"Field3"})),
				ddmFormValues, false, false, false);

		mockDDMFormFieldTypeServicesTracker(
			"text",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createTextDDMFormFieldTemplateContextContributor());

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
	public void testGridFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");
		String formFieldOption = String.format(_HTML_WRAPPER, "option");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createGridDDMFormField(
				"Field1", formFieldLabel, "grid", "string", false, false, true,
				formFieldTip, formFieldOption));

		mockDDMFormFieldTypeServicesTracker(
			"grid",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createGridDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Field1", new UnlocalizedValue("{}")));

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				ddmFormValues, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));

		List<Map<String, String>> columns =
			(List<Map<String, String>>)fieldTemplateContext.get("columns");

		Map<String, String> columnField = columns.get(0);

		Assert.assertEquals(formFieldOption, columnField.get("label"));

		List<Map<String, String>> rows =
			(List<Map<String, String>>)fieldTemplateContext.get("rows");

		Map<String, String> rowField = rows.get(0);

		Assert.assertEquals(formFieldOption, rowField.get("label"));

		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
	}

	@Test
	public void testInvisibleFieldByGooglePlacesAPIKey() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		ddmForm.addDDMFormRule(
			new DDMFormRule(
				Collections.singletonList(
					"setVisible('Field1', hasGooglePlacesAPIKey())"),
				"TRUE"));

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, true);

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			ddmFormPagesTemplateContextFactory.create());

		Assert.assertFalse(
			GetterUtil.getBoolean(fieldTemplateContext.get("visible")));
	}

	@Test
	public void testNumericFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");
		String formFieldPlaceholder = String.format(
			_HTML_WRAPPER, "placeHolder");
		String formFieldTooltip = String.format(_HTML_WRAPPER, "toolTip");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createNumericDDMFormField(
				"Field1", formFieldLabel, "integer", false, false, true,
				formFieldTip, formFieldPlaceholder, formFieldTooltip));

		mockDDMFormFieldTypeServicesTracker(
			"numeric",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createNumericDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));
		Assert.assertEquals(
			formFieldPlaceholder, fieldTemplateContext.get("placeholder"));
		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
		Assert.assertEquals(
			formFieldTooltip, fieldTemplateContext.get("tooltip"));
	}

	@Test
	public void testOnePageThreeRows() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			"Field1", "Field2", "Field3", "Field4", "Field5");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 1 Description", "Page 1",
						DDMFormLayoutTestUtil.createDDMFormLayoutRow(
							DDMFormLayoutTestUtil.createDDMFormLayoutColumns(
								"Field1", "Field2")),
						DDMFormLayoutTestUtil.createDDMFormLayoutRow("Field3"),
						DDMFormLayoutTestUtil.createDDMFormLayoutRow(
							"Field4", "Field5"))),
				null, false, true, false);

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
	public void testPageDescription() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String descriptionPage = String.format(
			_HTML_WRAPPER, "descriptionPage");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					descriptionPage, "titlePage", null),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> pageTemplateContext =
			(Map<String, Object>)pages.get(0);

		Assert.assertEquals(
			descriptionPage, pageTemplateContext.get("description"));
	}

	@Test
	public void testPageTitle() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String pageTitle = String.format(_HTML_WRAPPER, "titlePage");

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"descriptionPage", pageTitle, null),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> pageTemplateContext =
			(Map<String, Object>)pages.get(0);

		Assert.assertEquals(pageTitle, pageTemplateContext.get("title"));
	}

	@Test
	public void testRadioFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldOption = String.format(_HTML_WRAPPER, "option");
		String formFieldPredefinedValue = StringBundler.concat(
			StringPool.OPEN_BRACKET,
			String.format(_HTML_WRAPPER, "predefinedValue"),
			StringPool.CLOSE_BRACKET);
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", formFieldLabel, "radio", "string", false, false, true,
				formFieldTip, formFieldPredefinedValue, formFieldOption));

		mockDDMFormFieldTypeServicesTracker(
			"radio",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createRadioDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, false);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));
		Assert.assertEquals(formFieldOption, optionField.get("label"));

		Object predefinedValue = fieldTemplateContext.get("predefinedValue");

		Assert.assertEquals(
			formFieldPredefinedValue, predefinedValue.toString());

		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
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

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 1 Description", "Page 1",
						DDMFormLayoutTestUtil.createDDMFormLayoutColumns(
							"Field1", "Field2")),
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 2 Description", "Page 2",
						new String[] {"Field3"})),
				null, false, false, false);

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

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 1 Description", "Page 1",
						DDMFormLayoutTestUtil.createDDMFormLayoutColumns(
							"Field1", "Field2")),
					DDMFormLayoutTestUtil.createDDMFormLayoutPage(
						"Page 2 Description", "Page 2",
						new String[] {"Field3"})),
				null, false, true, false);

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

	@Test
	public void testSelectFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldOption = String.format(_HTML_WRAPPER, "option");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", formFieldLabel, "select", "string", false, false,
				true, formFieldTip, formFieldOption));

		mockDDMFormFieldTypeServicesTracker(
			"select",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createSelectDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, true);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals(formFieldOption, optionField.get("label"));

		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
	}

	@Test
	public void testTextFieldTemplateContext() throws Exception {

		// Dynamic data mapping form

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		String formFieldLabel = String.format(_HTML_WRAPPER, "label");
		String formFieldOption = String.format(_HTML_WRAPPER, "option");
		String formFieldPredefinedValue = String.format(
			_HTML_WRAPPER, "predefinedValue");
		String formFieldPlaceholder = String.format(
			_HTML_WRAPPER, "placeHolder");
		String formFieldTip = String.format(_HTML_WRAPPER, "tip");
		String formFieldTooltip = String.format(_HTML_WRAPPER, "toolTip");

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Field1", formFieldLabel, "text", "string", false, false, true,
				formFieldTip, formFieldPredefinedValue, formFieldPlaceholder,
				formFieldTooltip, formFieldOption));

		mockDDMFormFieldTypeServicesTracker(
			"text",
			_ddmFormFieldTemplateContextContributorTestHelper.
				createTextDDMFormFieldTemplateContextContributor());

		// Template context

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			createDDMFormPagesTemplateContextFactory(
				ddmForm,
				DDMFormLayoutTestUtil.createDDMFormLayout(
					"Page 1 Description", "Page 1", new String[] {"Field1"}),
				null, false, true, false);

		List<Object> pages = ddmFormPagesTemplateContextFactory.create();

		Map<String, Object> fieldTemplateContext = getFieldTemplateContext(
			pages);

		Assert.assertEquals(formFieldLabel, fieldTemplateContext.get("label"));

		List<Map<String, String>> options =
			(List<Map<String, String>>)fieldTemplateContext.get("options");

		Map<String, String> optionField = options.get(0);

		Assert.assertEquals(formFieldOption, optionField.get("label"));

		Assert.assertEquals(
			formFieldPlaceholder, fieldTemplateContext.get("placeholder"));
		Assert.assertEquals(
			formFieldPredefinedValue,
			fieldTemplateContext.get("predefinedValue"));
		Assert.assertEquals(formFieldTip, fieldTemplateContext.get("tip"));
		Assert.assertEquals(
			formFieldTooltip, fieldTemplateContext.get("tooltip"));
	}

	protected void assertColumnSize(
		int expectedSize, Map<String, Object> columnTemplateContex) {

		Assert.assertEquals(
			expectedSize, MapUtil.getInteger(columnTemplateContex, "size"));
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
		ddmFormRenderingContext.setHttpServletRequest(_httpServletRequest);
		ddmFormRenderingContext.setLocale(_LOCALE);
		ddmFormRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormRenderingContext.setReadOnly(ddmFormReadOnly);
		ddmFormRenderingContext.setReturnFullContext(true);
		ddmFormRenderingContext.setShowRequiredFieldsWarning(
			showRequiredFieldsWarning);
		ddmFormRenderingContext.setViewMode(viewMode);

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			new DDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormRenderingContext,
				_ddmStructureLayoutLocalService, _ddmStructureLocalService,
				_groupLocalService, new JSONFactoryImpl());

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			getDDMFormEvaluator());
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormPagesTemplateContextFactory;
	}

	protected DDMFormEvaluator getDDMFormEvaluator() throws Exception {
		DDMExpressionFactoryImpl ddmExpressionFactoryImpl =
			new DDMExpressionFactoryImpl();

		DDMFormEvaluator ddmFormEvaluator = new DDMFormEvaluatorImpl();

		field(
			DDMFormEvaluatorImpl.class, "ddmExpressionFactory"
		).set(
			ddmFormEvaluator, ddmExpressionFactoryImpl
		);

		field(
			DDMFormEvaluatorImpl.class, "ddmFormFieldTypeServicesTracker"
		).set(
			ddmFormEvaluator, _ddmFormFieldTypeServicesTracker
		);

		Map<String, DDMExpressionFunctionFactory>
			ddmExpressionFunctionFactoryMap =
				HashMapBuilder.<String, DDMExpressionFunctionFactory>put(
					"hasGooglePlacesAPIKey",
					() -> new HasGooglePlacesAPIKeyFunction()
				).put(
					"jumpPage", () -> new JumpPageFunction()
				).put(
					"setVisible", () -> new SetVisibleFunction()
				).build();

		DDMExpressionFunctionTracker ddmExpressionFunctionTracker = mock(
			DDMExpressionFunctionTracker.class);

		when(
			ddmExpressionFunctionTracker.getDDMExpressionFunctionFactories(
				Matchers.any())
		).thenReturn(
			ddmExpressionFunctionFactoryMap
		);

		field(
			DDMExpressionFactoryImpl.class, "ddmExpressionFunctionTracker"
		).set(
			ddmExpressionFactoryImpl, ddmExpressionFunctionTracker
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

	protected void setUpCalendarFactoryUtil() {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());
	}

	protected void setUpDDMFormFieldTypeServicesTracker() {
		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();

		Mockito.when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				Matchers.anyString())
		).thenReturn(
			(DDMFormFieldValueAccessor<Object>)ddmFormFieldValueAccessor
		);
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	protected void setUpGooglePlacesUtil() {
		mockStatic(GooglePlacesUtil.class);

		when(
			GooglePlacesUtil.getGooglePlacesAPIKey(
				Matchers.anyLong(), Matchers.anyLong(),
				Matchers.any(GroupLocalService.class))
		).thenReturn(
			StringPool.BLANK
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpHttpServletRequest() throws Exception {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathThemeImages(StringPool.BLANK);
		themeDisplay.setUser(Mockito.mock(User.class));

		when(
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	protected void setUpLanguageResources() {
		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);
	}

	protected void setUpLanguageUtil() {
		Language language = mock(Language.class);

		whenLanguageGet(
			language, LocaleUtil.US, "this-field-is-required",
			"This field is required.");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void setUpLocaleThreadLocal() {
		mockStatic(LocaleThreadLocal.class);

		when(
			LocaleThreadLocal.getThemeDisplayLocale()
		).thenReturn(
			LocaleUtil.US
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getCompanyId(Matchers.any(PortletRequest.class))
		).thenReturn(
			1L
		);

		when(
			portal.getUserId(Matchers.any(PortletRequest.class))
		).thenReturn(
			1L
		);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	protected void setUpResourceBundle() {
		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);
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

	@Mock
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Mock
	private DDMStructureLocalService _ddmStructureLocalService;

	@Mock
	private GroupLocalService _groupLocalService;

	private HttpServletRequest _httpServletRequest;

}