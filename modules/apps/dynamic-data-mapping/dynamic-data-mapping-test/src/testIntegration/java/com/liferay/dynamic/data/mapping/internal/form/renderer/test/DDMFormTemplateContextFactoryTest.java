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

package com.liferay.dynamic.data.mapping.internal.form.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTemplateContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

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
		_httpServletRequest = new MockHttpServletRequest();

		_setUpThemeDisplay();

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
		String containerId = StringUtil.randomString();

		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext = builder.withContainerId(
			containerId
		).withHttpServletRequest(
			_httpServletRequest
		).withLocale(
			LocaleUtil.US
		).build();

		Assert.assertEquals(
			containerId, ddmFormTemplateContext.get("containerId"));
	}

	@Test
	public void testContainerIdGeneration() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).build();

		Assert.assertNotNull(ddmFormTemplateContext.get("containerId"));
	}

	@Test
	public void testEvaluatorURL() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).build();

		Assert.assertEquals(
			"/o/dynamic-data-mapping-form-context-provider/",
			ddmFormTemplateContext.get("evaluatorURL"));
	}

	@Test
	public void testPortletNamespace() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withPortletNamespace(
				"_PORTLET_NAMESPACE_"
			).build();

		Assert.assertEquals(
			"_PORTLET_NAMESPACE_",
			ddmFormTemplateContext.get("portletNamespace"));
	}

	@Test
	public void testReadOnly() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withReadOnly(
				true
			).build();

		Assert.assertTrue((boolean)ddmFormTemplateContext.get("readOnly"));
	}

	@Test
	public void testShowPartialResultsToRespondents() throws Exception {
		Map<String, Object> ddmFormTemplateContext =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory
			).withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withProperty(
				"showPartialResultsToRespondents", true
			).build();

		Assert.assertTrue(
			(boolean)ddmFormTemplateContext.get(
				"showPartialResultsToRespondents"));
	}

	@Test
	public void testShowRequiredFieldsWarning() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withShowRequiredFieldsWarning(
				false
			).build();

		Assert.assertFalse(
			(boolean)ddmFormTemplateContext.get("showRequiredFieldsWarning"));
	}

	@Test
	public void testShowSubmitButton() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withShowSubmitButton(
				true
			).build();

		Assert.assertTrue(
			(boolean)ddmFormTemplateContext.get("showSubmitButton"));
	}

	@Test
	public void testShowSubmitButtonAndReadOnlyEnabled() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withReadOnly(
				true
			).withShowSubmitButton(
				true
			).build();

		Assert.assertFalse(
			(boolean)ddmFormTemplateContext.get("showSubmitButton"));
	}

	@Test
	public void testStrings() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).build();

		Assert.assertEquals(
			HashMapBuilder.put(
				"next", "Next"
			).put(
				"previous", "Previous"
			).build(),
			ddmFormTemplateContext.get("strings"));
	}

	@Test
	public void testSubmitLabel() throws Exception {
		String submitLabel = StringUtil.randomString();

		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withSubmitLabel(
				submitLabel
			).build();

		Assert.assertEquals(
			submitLabel, ddmFormTemplateContext.get("submitLabel"));

		ddmFormTemplateContext = builder.withSubmitLabel(
			null
		).build();

		Assert.assertEquals(
			"Submit", ddmFormTemplateContext.get("submitLabel"));
	}

	@Test
	public void testTemplateNamespace() throws Exception {

		// Settings form

		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withPaginationMode(
				DDMFormLayout.SETTINGS_MODE
			).build();

		Assert.assertEquals(
			"ddm.settings_form",
			ddmFormTemplateContext.get("templateNamespace"));

		// Simple form

		ddmFormTemplateContext = builder.withPaginationMode(
			null
		).build();

		Assert.assertEquals(
			"ddm.simple_form", ddmFormTemplateContext.get("templateNamespace"));

		// Tabbed form

		ddmFormTemplateContext = builder.withPaginationMode(
			DDMFormLayout.TABBED_MODE
		).build();

		Assert.assertEquals(
			"ddm.tabbed_form", ddmFormTemplateContext.get("templateNamespace"));

		// Paginated form

		ddmFormTemplateContext = builder.withPaginationMode(
			StringPool.BLANK
		).build();

		Assert.assertEquals(
			"ddm.paginated_form",
			ddmFormTemplateContext.get("templateNamespace"));

		// Wizard form

		ddmFormTemplateContext = builder.withPaginationMode(
			DDMFormLayout.WIZARD_MODE
		).build();

		Assert.assertEquals(
			"ddm.wizard_form", ddmFormTemplateContext.get("templateNamespace"));
	}

	@Test
	public void testValidations() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).build();

		HashMap<String, Object> actualValidationsMap =
			(HashMap<String, Object>)ddmFormTemplateContext.get("validations");

		Assert.assertEquals(
			actualValidationsMap.toString(), 3, actualValidationsMap.size());
		Assert.assertTrue(actualValidationsMap.containsKey("date"));
		Assert.assertTrue(actualValidationsMap.containsKey("numeric"));
		Assert.assertTrue(actualValidationsMap.containsKey("string"));

		_assertValidations(
			actualValidationsMap, "date",
			HashMapBuilder.put(
				"dateRange",
				"futureDates({name}, \"{parameter}\") AND pastDates({name}, " +
					"\"{parameter}\")"
			).put(
				"futureDates", "futureDates({name}, \"{parameter}\")"
			).put(
				"pastDates", "pastDates({name}, \"{parameter}\")"
			).build());

		_assertValidations(
			actualValidationsMap, "numeric",
			HashMapBuilder.put(
				"eq", "{name} == {parameter}"
			).put(
				"gt", "{name} > {parameter}"
			).put(
				"gteq", "{name} >= {parameter}"
			).put(
				"lt", "{name} < {parameter}"
			).put(
				"lteq", "{name} <= {parameter}"
			).put(
				"neq", "{name} != {parameter}"
			).build());

		_assertValidations(
			actualValidationsMap, "string",
			HashMapBuilder.put(
				"contains", "contains({name}, \"{parameter}\")"
			).put(
				"email", "isEmailAddress({name})"
			).put(
				"notContains", "NOT(contains({name}, \"{parameter}\"))"
			).put(
				"regularExpression", "match({name}, \"{parameter}\")"
			).put(
				"url", "isURL({name})"
			).build());
	}

	@Test
	public void testViewMode() throws Exception {
		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		Map<String, Object> ddmFormTemplateContext =
			builder.withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withViewMode(
				true
			).build();

		Assert.assertTrue((boolean)ddmFormTemplateContext.get("viewMode"));
	}

	private void _assertValidations(
		HashMap<String, Object> actualValidationsMap, String dataType,
		HashMap<String, String> expectedValidationsMap) {

		Object[] actualValidations = (Object[])actualValidationsMap.get(
			dataType);

		Assert.assertEquals(
			Arrays.toString(actualValidations), expectedValidationsMap.size(),
			actualValidations.length);

		Stream.of(
			actualValidations
		).map(
			HashMap.class::cast
		).forEach(
			actualValidation -> {
				Assert.assertTrue(actualValidation.containsKey("label"));
				Assert.assertTrue(actualValidation.containsKey("name"));
				Assert.assertTrue(
					actualValidation.containsKey("parameterMessage"));
				Assert.assertTrue(actualValidation.containsKey("template"));

				Assert.assertEquals(
					expectedValidationsMap.remove(actualValidation.get("name")),
					actualValidation.get("template"));
			}
		);

		Assert.assertEquals(
			expectedValidationsMap.toString(), 0,
			expectedValidationsMap.size());
	}

	private void _setUpThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathContext("/my/path/context/");
		themeDisplay.setPathThemeImages("/my/theme/images/");
		themeDisplay.setUser(TestPropsValues.getUser());

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
	}

	@Inject
	private static DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	private HttpServletRequest _httpServletRequest;
	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

}