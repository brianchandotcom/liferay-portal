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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
				_getMockHttpServletRequest()
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

		Assert.assertEquals(0, ddmFormTemplateContext.get("activePage"));
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

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		int activePage = RandomTestUtil.randomInt();

		mockHttpServletRequest.setParameter(
			"activePage", String.valueOf(activePage));

		ddmFormTemplateContext = DDMFormTemplateContext.Builder.newBuilder(
			_ddmFormTemplateContextFactory
		).withHttpServletRequest(
			mockHttpServletRequest
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

		Assert.assertEquals(
			activePage, ddmFormTemplateContext.get("activePage"));
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
					"label", "Range"
				).put(
					"name", "dateRange"
				).put(
					"template",
					"futureDates({name}, \"{parameter}\") AND " +
						"pastDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Future Dates"
				).put(
					"name", "futureDates"
				).put(
					"template", "futureDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Past Dates"
				).put(
					"name", "pastDates"
				).put(
					"template", "pastDates({name}, \"{parameter}\")"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("numeric")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "Is Equal To"
				).put(
					"name", "eq"
				).put(
					"template", "{name} == {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Is Greater Than"
				).put(
					"name", "gt"
				).put(
					"template", "{name} > {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Is Greater Than Or Equal To"
				).put(
					"name", "gteq"
				).put(
					"template", "{name} >= {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Is Less Than"
				).put(
					"name", "lt"
				).put(
					"template", "{name} < {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Is Less Than Or Equal To"
				).put(
					"name", "lteq"
				).put(
					"template", "{name} <= {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Is Not Equal To"
				).put(
					"name", "neq"
				).put(
					"template", "{name} != {parameter}"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("string")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "Contains"
				).put(
					"name", "contains"
				).put(
					"template", "contains({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Is an email"
				).put(
					"name", "email"
				).put(
					"template", "isEmailAddress({name})"
				).build(),
				HashMapBuilder.put(
					"label", "Does Not Contain"
				).put(
					"name", "notContains"
				).put(
					"template", "NOT(contains({name}, \"{parameter}\"))"
				).build(),
				HashMapBuilder.put(
					"label", "Matches"
				).put(
					"name", "regularExpression"
				).put(
					"template", "match({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Is a URL"
				).put(
					"name", "url"
				).put(
					"template", "isURL({name})"
				).build()));

		DDMFormTemplateContext.Builder builder =
			DDMFormTemplateContext.Builder.newBuilder(
				_ddmFormTemplateContextFactory);

		ddmFormTemplateContext = builder.withHttpServletRequest(
			_getMockHttpServletRequest()
		).withLocale(
			LocaleUtil.BRAZIL
		).build();

		validations =
			(Map<String, Map<String, String>[]>)ddmFormTemplateContext.get(
				"validations");

		Assert.assertEquals(validations.toString(), 3, validations.size());

		_assertValidations(
			ListUtil.fromArray(validations.get("date")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "Faixa"
				).put(
					"name", "dateRange"
				).put(
					"template",
					"futureDates({name}, \"{parameter}\") AND " +
						"pastDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Datas futuras"
				).put(
					"name", "futureDates"
				).put(
					"template", "futureDates({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "Datas passadas"
				).put(
					"name", "pastDates"
				).put(
					"template", "pastDates({name}, \"{parameter}\")"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("numeric")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "É igual a"
				).put(
					"name", "eq"
				).put(
					"template", "{name} == {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "É maior que"
				).put(
					"name", "gt"
				).put(
					"template", "{name} > {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "É maior ou igual a"
				).put(
					"name", "gteq"
				).put(
					"template", "{name} >= {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "É menor que"
				).put(
					"name", "lt"
				).put(
					"template", "{name} < {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "É menor ou igual a"
				).put(
					"name", "lteq"
				).put(
					"template", "{name} <= {parameter}"
				).build(),
				HashMapBuilder.put(
					"label", "Não é igual"
				).put(
					"name", "neq"
				).put(
					"template", "{name} != {parameter}"
				).build()));
		_assertValidations(
			ListUtil.fromArray(validations.get("string")),
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "Contêm"
				).put(
					"name", "contains"
				).put(
					"template", "contains({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "É um e-mail"
				).put(
					"name", "email"
				).put(
					"template", "isEmailAddress({name})"
				).build(),
				HashMapBuilder.put(
					"label", "Não contêm"
				).put(
					"name", "notContains"
				).put(
					"template", "NOT(contains({name}, \"{parameter}\"))"
				).build(),
				HashMapBuilder.put(
					"label", "Correspondências"
				).put(
					"name", "regularExpression"
				).put(
					"template", "match({name}, \"{parameter}\")"
				).build(),
				HashMapBuilder.put(
					"label", "É um URL"
				).put(
					"name", "url"
				).put(
					"template", "isURL({name})"
				).build()));

		ddmFormTemplateContext = builder.withHttpServletRequest(
			_getMockHttpServletRequest()
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

			String expectedLabel = null;
			String expectedTemplate = null;

			for (Map<String, String> expectedValidationMap :
					expectedValidations) {

				if (Objects.equals(
						actualValidationMap.get("name"),
						expectedValidationMap.get("name"))) {

					expectedLabel = expectedValidationMap.get("label");
					expectedTemplate = expectedValidationMap.get("template");

					break;
				}
			}

			Assert.assertEquals(
				expectedLabel, actualValidationMap.get("label"));
			Assert.assertEquals(
				expectedTemplate, actualValidationMap.get("template"));
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathContext("/my/path/context/");
		themeDisplay.setPathThemeImages("/my/theme/images/");
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private static DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

}