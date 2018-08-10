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

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.UnsafeSanitizedContentOrdainer;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.utils.SoyHTMLSanitizer;
import com.liferay.portal.util.PortalImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class DDMFormTemplateContextFactoryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormTemplateContextFactory();

		setUpDDM();
		setUpDDMFormContextProviderServlet();
		setUpDDMFormEvaluator();
		setUpDDMFormFieldTypeServicesTracker();
		setUpDDMFormFieldTypesJSONSerializer();
		setUpDDMFormJSONSerializer();
		setUpDDMFormLayoutJSONSerializer();

		setUpJSONFactory();
		setUpLanguageUtil();
		setUpLocaleThreadLocal();
		setUpPortal();
		setUpPortalClassLoaderUtil();

		setUpDDMFormTemplateContextFactoryUtil();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
	}

	@Test
	public void testContainerId() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		String containerId = StringUtil.randomString();

		ddmFormRenderingContext.setContainerId(containerId);

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

		ddmFormRenderingContext.setHttpServletRequest(_request);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		String expectedEvaluatorURL =
			"CONTEXT_PATH/dynamic-data-mapping-form-context-provider/";

		Assert.assertEquals(
			expectedEvaluatorURL, templateContext.get("evaluatorURL"));
	}

	@Test
	public void testPortletNamespace() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

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

		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(true, templateContext.get("readOnly"));
	}

	@Test
	public void testRequiredFieldsWarningHTML() throws Exception {

		// Mock language

		when(
			_language.format(
				Matchers.any(ResourceBundle.class),
				Matchers.eq("all-fields-marked-with-x-are-required"),
				Matchers.anyString(), Matchers.eq(false))
		).thenReturn(
			"All fields marked with '*' are required."
		);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		String expectedRequiredFieldsWarningHTML =
			"<label class=\"required-warning\">All fields marked with '*' " +
				"are required.</label>";

		SanitizedContent sanitizedContent =
			(SanitizedContent)templateContext.get(
				"requiredFieldsWarningMessageHTML");

		Assert.assertEquals(
			expectedRequiredFieldsWarningHTML, sanitizedContent.getContent());
	}

	@Test
	public void testShowRequiredFieldsWarning() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

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
		when(
			_language.get(
				Matchers.any(ResourceBundle.class), Matchers.eq("next"))
		).thenReturn(
			"Next"
		);

		when(
			_language.get(
				Matchers.any(ResourceBundle.class), Matchers.eq("previous"))
		).thenReturn(
			"Previous"
		);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

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

		ddmFormRenderingContext.setHttpServletRequest(_request);
		ddmFormRenderingContext.setSubmitLabel(submitLabel);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormRenderingContext);

		Assert.assertEquals(submitLabel, templateContext.get("submitLabel"));

		// Default value

		when(
			_language.get(Matchers.any(Locale.class), Matchers.eq("submit"))
		).thenReturn(
			"Submit"
		);

		ddmFormRenderingContext = new DDMFormRenderingContext();

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

	protected void setDeclaredField(
			Object targetObject, String fieldName, Object fieldValue)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			targetObject.getClass(), fieldName);

		field.set(targetObject, fieldValue);
	}

	protected void setUpDDM() throws Exception {
		setDeclaredField(_ddmFormTemplateContextFactory, "_ddm", new DDMImpl());
	}

	protected void setUpDDMFormContextProviderServlet() throws Exception {
		Servlet ddmFormContextProviderServlet = mock(Servlet.class);

		ServletConfig ddmFormContextProviderServletConfig = mock(
			ServletConfig.class);

		when(
			ddmFormContextProviderServlet.getServletConfig()
		).thenReturn(
			ddmFormContextProviderServletConfig
		);

		ServletContext ddmFormContextProviderServletContext = mock(
			ServletContext.class);

		when(
			ddmFormContextProviderServletConfig.getServletContext()
		).thenReturn(
			ddmFormContextProviderServletContext
		);

		when(
			ddmFormContextProviderServletContext.getContextPath()
		).thenReturn(
			"CONTEXT_PATH"
		);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormContextProviderServlet",
			ddmFormContextProviderServlet);
	}

	protected void setUpDDMFormEvaluator() throws Exception {
		DDMFormEvaluator ddmFormEvaluator = mock(DDMFormEvaluator.class);

		when(
			ddmFormEvaluator.evaluate(
				Matchers.any(DDMFormEvaluatorContext.class))
		).thenReturn(
			new DDMFormEvaluationResult()
		);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormEvaluator",
			ddmFormEvaluator);
	}

	protected void setUpDDMFormFieldTypeServicesTracker() throws Exception {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormFieldTypeServicesTracker",
			ddmFormFieldTypeServicesTracker);
	}

	protected void setUpDDMFormFieldTypesJSONSerializer() throws Exception {
		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer = mock(
			DDMFormFieldTypesSerializer.class);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormFieldTypesJSONSerializer",
			ddmFormFieldTypesSerializer);
	}

	protected void setUpDDMFormJSONSerializer() throws Exception {
		DDMFormJSONSerializer ddmFormJSONSerializer =
			new DDMFormJSONSerializer();

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormJSONSerializer",
			ddmFormJSONSerializer);

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		setDeclaredField(
			ddmFormJSONSerializer, "_ddmFormFieldTypeServicesTracker",
			ddmFormFieldTypeServicesTracker);

		setDeclaredField(ddmFormJSONSerializer, "_jsonFactory", _jsonFactory);
	}

	protected void setUpDDMFormLayoutJSONSerializer() throws Exception {
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer =
			new DDMFormLayoutJSONSerializer();

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormLayoutJSONSerializer",
			ddmFormLayoutJSONSerializer);

		setDeclaredField(
			ddmFormLayoutJSONSerializer, "_jsonFactory", _jsonFactory);
	}

	protected void setUpDDMFormTemplateContextFactory() throws Exception {
		_ddmFormTemplateContextFactory =
			new DDMFormTemplateContextFactoryImpl();

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		field(
			DDMFormTemplateContextFactoryImpl.class,
			"_ddmFormTemplateContextFactoryHelper"
		).set(
			_ddmFormTemplateContextFactory,
			new DDMFormTemplateContextFactoryHelper(
				ddmDataProviderInstanceService)
		);

		field(
			DDMFormTemplateContextFactoryImpl.class, "_soyHTMLSanitizer"
		).set(
			_ddmFormTemplateContextFactory,
			new SoyHTMLSanitizer() {

				@Override
				public Object sanitize(String value) {
					return UnsafeSanitizedContentOrdainer.ordainAsSafe(
						value, SanitizedContent.ContentKind.HTML);
				}

			}
		);
	}

	protected void setUpDDMFormTemplateContextFactoryUtil() {
		_request = Mockito.mock(HttpServletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathThemeImages(StringPool.BLANK);

		Mockito.when(
			(ThemeDisplay)_request.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	protected void setUpJSONFactory() throws Exception {
		setDeclaredField(
			_ddmFormTemplateContextFactory, "_jsonFactory", _jsonFactory);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_language = mock(Language.class);

		languageUtil.setLanguage(_language);
	}

	protected void setUpLocaleThreadLocal() {
		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);
	}

	protected void setUpPortal() throws Exception {
		setDeclaredField(_ddmFormTemplateContextFactory, "_portal", _portal);
	}

	protected void setUpPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(PortalImpl.class.getClassLoader());
	}

	private DDMFormTemplateContextFactoryImpl _ddmFormTemplateContextFactory;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private Language _language;
	private Locale _originalSiteDefaultLocale;
	private final Portal _portal = new PortalImpl();
	private HttpServletRequest _request;

}