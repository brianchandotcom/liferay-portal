/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathContext("/my/path/context/");
		themeDisplay.setPathThemeImages("/my/theme/images/");
		themeDisplay.setUser(TestPropsValues.getUser());

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

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
	public void testCreateDDMFormTemplateContext() throws Exception {
		String containerId = StringUtil.randomString();
		String submitLabel = StringUtil.randomString();

		Map<String, Object> ddmFormTemplateContext =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory
			).withContainerId(
				containerId
			).withHttpServletRequest(
				_httpServletRequest
			).withLocale(
				LocaleUtil.US
			).withPaginationMode(
				DDMFormLayout.SETTINGS_MODE
			).withShowSubmitButton(
				true
			).withSubmitLabel(
				submitLabel
			).withViewMode(
				true
			).build();

		Assert.assertEquals(
			containerId, ddmFormTemplateContext.get("containerId"));
		Assert.assertFalse((boolean)ddmFormTemplateContext.get("readOnly"));
		Assert.assertTrue(
			(boolean)ddmFormTemplateContext.get("showSubmitButton"));
		Assert.assertEquals(
			submitLabel, ddmFormTemplateContext.get("submitLabel"));
		Assert.assertEquals(
			"ddm.settings_form",
			ddmFormTemplateContext.get("templateNamespace"));
		Assert.assertTrue((boolean)ddmFormTemplateContext.get("viewMode"));

		ddmFormTemplateContext = DDMFormTemplateContext.Builder.newBuilder(
			_ddmFormTemplateContextFactory
		).withHttpServletRequest(
			_httpServletRequest
		).withLocale(
			LocaleUtil.US
		).withPaginationMode(
			null
		).withPortletNamespace(
			"_PORTLET_NAMESPACE_"
		).withProperty(
			"showPartialResultsToRespondents", true
		).withReadOnly(
			true
		).withShowRequiredFieldsWarning(
			false
		).withShowSubmitButton(
			true
		).withSubmitLabel(
			null
		).build();

		Assert.assertNotNull(ddmFormTemplateContext.get("containerId"));
		Assert.assertEquals(
			"/o/dynamic-data-mapping-form-context-provider/",
			ddmFormTemplateContext.get("evaluatorURL"));
		Assert.assertEquals(
			"_PORTLET_NAMESPACE_",
			ddmFormTemplateContext.get("portletNamespace"));
		Assert.assertTrue((boolean)ddmFormTemplateContext.get("readOnly"));
		Assert.assertTrue(
			(boolean)ddmFormTemplateContext.get(
				"showPartialResultsToRespondents"));
		Assert.assertFalse(
			(boolean)ddmFormTemplateContext.get("showRequiredFieldsWarning"));
		Assert.assertFalse(
			(boolean)ddmFormTemplateContext.get("showSubmitButton"));
		Assert.assertEquals(
			HashMapBuilder.put(
				"next", "Next"
			).put(
				"previous", "Previous"
			).build(),
			ddmFormTemplateContext.get("strings"));
		Assert.assertEquals(
			"Submit", ddmFormTemplateContext.get("submitLabel"));
		Assert.assertEquals(
			"ddm.simple_form", ddmFormTemplateContext.get("templateNamespace"));

		Map<String, Map<String, String>[]> validations =
			(Map<String, Map<String, String>[]>)ddmFormTemplateContext.get(
				"validations");

		Assert.assertEquals(validations.toString(), 3, validations.size());

		_assertValidations(
			ListUtil.fromArray(validations.get("date")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"name", "dateRange"
				).put(
					"template",
					"futureDates({name}, \"{parameter}\") AND " +
						"pastDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"name", "futureDates"
				).put(
					"template", "futureDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"name", "pastDates"
				).put(
					"template", "pastDates({name}, \"{parameter}\")"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("numeric")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"name", "eq"
				).put(
					"template", "{name} == {parameter}"
				).build(),
				HashMapBuilder.put(
					"name", "gt"
				).put(
					"template", "{name} > {parameter}"
				).build(),
				HashMapBuilder.put(
					"name", "gteq"
				).put(
					"template", "{name} >= {parameter}"
				).build(),
				HashMapBuilder.put(
					"name", "lt"
				).put(
					"template", "{name} < {parameter}"
				).build(),
				HashMapBuilder.put(
					"name", "lteq"
				).put(
					"template", "{name} <= {parameter}"
				).build(),
				HashMapBuilder.put(
					"name", "neq"
				).put(
					"template", "{name} != {parameter}"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("string")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"name", "contains"
				).put(
					"template", "contains({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"name", "email"
				).put(
					"template", "isEmailAddress({name})"
				).build(),
				HashMapBuilder.put(
					"name", "notContains"
				).put(
					"template", "NOT(contains({name}, \"{parameter}\"))"
				).build(),
				HashMapBuilder.put(
					"name", "regularExpression"
				).put(
					"template", "match({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"name", "url"
				).put(
					"template", "isURL({name})"
				).build()));

		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		ddmFormTemplateContext = builder.withHttpServletRequest(
			_httpServletRequest
		).withLocale(
			LocaleUtil.US
		).withPaginationMode(
			StringPool.BLANK
		).build();

		Assert.assertEquals(
			"ddm.paginated_form",
			ddmFormTemplateContext.get("templateNamespace"));

		ddmFormTemplateContext = builder.withPaginationMode(
			DDMFormLayout.TABBED_MODE
		).build();

		Assert.assertEquals(
			"ddm.tabbed_form", ddmFormTemplateContext.get("templateNamespace"));

		ddmFormTemplateContext = builder.withPaginationMode(
			DDMFormLayout.WIZARD_MODE
		).build();

		Assert.assertEquals(
			"ddm.wizard_form", ddmFormTemplateContext.get("templateNamespace"));
	}

	private void _assertValidations(
		List<Map<String, String>> actualValidations,
		List<Map<String, String>> expectedValidations) {

		Assert.assertEquals(
			actualValidations.toString(), expectedValidations.size(),
			actualValidations.size());

		for (Map<String, String> actualValidationMap : actualValidations) {
			Assert.assertTrue(actualValidationMap.containsKey("label"));
			Assert.assertTrue(actualValidationMap.containsKey("name"));
			Assert.assertTrue(
				actualValidationMap.containsKey("parameterMessage"));
			Assert.assertTrue(actualValidationMap.containsKey("template"));

			String expectedTemplate = null;

			for (Map<String, String> expectedValidationMap :
					expectedValidations) {

				if (Objects.equals(
						actualValidationMap.get("name"),
						expectedValidationMap.get("name"))) {

					expectedTemplate = expectedValidationMap.get("template");

					break;
				}
			}

			Assert.assertEquals(
				expectedTemplate, actualValidationMap.get("template"));
		}
	}

	@Inject
	private static DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	private HttpServletRequest _httpServletRequest;
	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

}