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

package com.liferay.dynamic.data.mapping.form.renderer.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMFormTemplateContextFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_request = new MockHttpServletRequest();

		setUpThemeDisplay();

		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();
		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);
	}

	@Test
	public void testContainerId() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		String containerId = StringUtil.randomString();

		ddmFormRenderingContext.setContainerId(containerId);

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(containerId, templateContext.get("containerId"));
	}

	@Test
	public void testContainerIdGeneration() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertNotNull(templateContext.get("containerId"));
	}

	@Test
	public void testEvaluatorURL() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(
			"/o/dynamic-data-mapping-form-context-provider/",
			templateContext.get("evaluatorURL"));
	}

	@Test
	public void testPortletNamespace() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setPortletNamespace("_PORTLET_NAMESPACE_");

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(
			"_PORTLET_NAMESPACE_", templateContext.get("portletNamespace"));
	}

	@Test
	public void testReadOnly() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(true, templateContext.get("readOnly"));
	}

	@Test
	public void testRequiredFieldsWarningHTML() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Object sanitizedContent = templateContext.get(
			"requiredFieldsWarningMessageHTML");

		Class<?> clazz = sanitizedContent.getClass();

		Method method = clazz.getMethod("getContent");

		method.setAccessible(true);

		String value = (String)method.invoke(sanitizedContent);

		Assert.assertTrue(
			value,
			value.startsWith(
				"<label class=\"required-warning\">All fields marked with "));
		Assert.assertTrue(value, value.endsWith(" are required.</label>"));
	}

	@Test
	public void testShowRequiredFieldsWarning() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setShowRequiredFieldsWarning(false);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(
			false, templateContext.get("showRequiredFieldsWarning"));
	}

	@Test
	public void testShowSubmitButton() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setShowSubmitButton(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(true, templateContext.get("showSubmitButton"));
	}

	@Test
	public void testShowSubmitButtonAndReadOnlyEnabled() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setShowSubmitButton(true);
		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(false, templateContext.get("showSubmitButton"));
	}

	@Test
	public void testStrings() throws Exception {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormRenderingContext);

		Map<String, String> expectedStringsMap = new HashMap<>();

		expectedStringsMap.put("next", "Next");
		expectedStringsMap.put("previous", "Previous");

		Assert.assertEquals(expectedStringsMap, templateContext.get("strings"));
	}

	@Test
	public void testSubmitLabel() throws Exception {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		String submitLabel = StringUtil.randomString();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setSubmitLabel(submitLabel);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormRenderingContext);

		Assert.assertEquals(submitLabel, templateContext.get("submitLabel"));

		ddmFormRenderingContext = new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormRenderingContext);

		Assert.assertEquals("Submit", templateContext.get("submitLabel"));
	}

	@Test
	public void testTemplateNamespace() throws Exception {

		// Settings form

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setPaginationMode(DDMFormLayout.SETTINGS_MODE);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormLayout,
				ddmFormRenderingContext);

		Assert.assertEquals(
			"ddm.settings_form", templateContext.get("templateNamespace"));

		// Simple form

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormRenderingContext);

		Assert.assertEquals(
			"ddm.simple_form", templateContext.get("templateNamespace"));

		// Tabbed form

		ddmFormLayout.setPaginationMode(DDMFormLayout.TABBED_MODE);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormLayout,
			ddmFormRenderingContext);

		Assert.assertEquals(
			"ddm.tabbed_form", templateContext.get("templateNamespace"));

		// Paginated form

		ddmFormLayout.setPaginationMode(StringPool.BLANK);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormLayout,
			ddmFormRenderingContext);

		Assert.assertEquals(
			"ddm.paginated_form", templateContext.get("templateNamespace"));

		// Wizard form

		ddmFormLayout.setPaginationMode(DDMFormLayout.WIZARD_MODE);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormLayout,
			ddmFormRenderingContext);

		Assert.assertEquals(
			"ddm.wizard_form", templateContext.get("templateNamespace"));
	}

	@Test
	public void testViewMode() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(LocaleUtil.US);
		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setViewMode(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(true, templateContext.get("viewMode"));
	}

	protected void setUpThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathContext("/my/path/context/");
		themeDisplay.setPathThemeImages("/my/theme/images/");

		_request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
	}

	@Inject
	private static DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;
	private HttpServletRequest _request;

}