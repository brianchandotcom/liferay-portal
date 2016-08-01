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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormLayoutJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class DDMFormTemplateContextFactoryTest {

	@Before
	public void setUp() throws Exception {
		_ddmFormTemplateContextFactory =
			new DDMFormTemplateContextFactoryImpl();

		setUpDDM();
		setUpDDMFormContextProviderServlet();
		setUpDDMFormFieldTypeServicesTracker();
		setUpDDMFormFieldTypesJSONSerializer();
		setUpDDMFormJSONSerializer();
		setUpDDMFormLayoutJSONSerializer();

		setUpLanguageUtil();
		setUpLocaleThreadLocal();
		setUpPortalClassLoaderUtil();
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

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		Assert.assertEquals(containerId, templateContext.get("containerId"));
	}

	@Test
	public void testContainerIdGeneration() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, new DDMFormRenderingContext());

		Assert.assertNotNull(templateContext.get("containerId"));
	}

	@Test
	public void testEvaluatorURL() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

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

		ddmFormRenderingContext.setReadOnly(true);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormRenderingContext);

		String expectedRequiredFieldsWarningHTML =
			"<label class=\"required-warning\">All fields marked with '*' " +
				"are required.</label>";

		Assert.assertEquals(
			expectedRequiredFieldsWarningHTML,
			templateContext.get("requiredFieldsWarningMessageHTML"));
	}

	@Test
	public void testShowRequiredFieldsWarning() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

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

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), new DDMFormRenderingContext());

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

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), new DDMFormRenderingContext());

		Assert.assertEquals("Submit", templateContext.get("submitLabel"));
	}

	@Test
	public void testTemplateNamespace() throws Exception {

		// Settings form

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setPaginationMode(DDMFormLayout.SETTINGS_MODE);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				DDMFormTestUtil.createDDMForm(), ddmFormLayout,
				new DDMFormRenderingContext());

		Assert.assertEquals(
			"ddm.settings_form", templateContext.get("templateNamespace"));

		// Simple form

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), new DDMFormRenderingContext());

		Assert.assertEquals(
			"ddm.simple_form", templateContext.get("templateNamespace"));

		// Tabbed form

		ddmFormLayout.setPaginationMode(DDMFormLayout.TABBED_MODE);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormLayout,
			new DDMFormRenderingContext());

		Assert.assertEquals(
			"ddm.tabbed_form", templateContext.get("templateNamespace"));

		// Paginated form

		ddmFormLayout.setPaginationMode(DDMFormLayout.WIZARD_MODE);

		templateContext = _ddmFormTemplateContextFactory.create(
			DDMFormTestUtil.createDDMForm(), ddmFormLayout,
			new DDMFormRenderingContext());

		Assert.assertEquals(
			"ddm.paginated_form", templateContext.get("templateNamespace"));
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

	protected void setUpDDMFormFieldTypeServicesTracker() throws Exception {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormFieldTypeServicesTracker",
			ddmFormFieldTypeServicesTracker);
	}

	protected void setUpDDMFormFieldTypesJSONSerializer() throws Exception {
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer = mock(
			DDMFormFieldTypesJSONSerializer.class);

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormFieldTypesJSONSerializer",
			ddmFormFieldTypesJSONSerializer);
	}

	protected void setUpDDMFormJSONSerializer() throws Exception {
		DDMFormJSONSerializer ddmFormJSONSerializer =
			new DDMFormJSONSerializerImpl();

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormJSONSerializer",
			ddmFormJSONSerializer);

		setDeclaredField(
			ddmFormJSONSerializer, "_jsonFactory", new JSONFactoryImpl());
	}

	protected void setUpDDMFormLayoutJSONSerializer() throws Exception {
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer =
			new DDMFormLayoutJSONSerializerImpl();

		setDeclaredField(
			_ddmFormTemplateContextFactory, "_ddmFormLayoutJSONSerializer",
			ddmFormLayoutJSONSerializer);

		setDeclaredField(
			ddmFormLayoutJSONSerializer, "_jsonFactory", new JSONFactoryImpl());
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

	protected void setUpPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(PortalImpl.class.getClassLoader());
	}

	private DDMFormTemplateContextFactoryImpl _ddmFormTemplateContextFactory;
	private Language _language;
	private Locale _originalSiteDefaultLocale;

}