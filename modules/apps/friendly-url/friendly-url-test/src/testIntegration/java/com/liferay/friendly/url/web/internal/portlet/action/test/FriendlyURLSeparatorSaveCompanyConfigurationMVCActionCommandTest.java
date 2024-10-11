/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mikel Lorza
 */
@FeatureFlags("LPD-11147")
@RunWith(Arquillian.class)
@Sync
public class FriendlyURLSeparatorSaveCompanyConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());
	}

	@Test
	public void testDoProcessActionSaveConfiguration() throws Exception {
		_testDoProcessActionSaveConfiguration(
			_getRandomFriendlyURLSeparatorsMap());
	}

	@Test
	public void testDoProcessActionSaveConfigurationWithSomeInvalidCharacters()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "test%&?/22");

		_testDoProcessActionSaveConfiguration(friendlyURLSeparators);
	}

	@Test
	public void testDoProcessActionWithANumberAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "12");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-can-not-be-a-number",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithCommerceSeparatorAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "p");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-other-asset-type-may-use-this-prefix",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithDefaultSeparatorAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "b");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-other-asset-type-may-use-this-prefix",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithEmptyWordAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(
			JournalArticle.class.getName(), StringPool.BLANK);

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-cannot-be-empty",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithInvalidCharactersAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "%&/()?¿*");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-invalid-characters",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithLayoutFriendlyURLAsAFriendlyURLSeparator()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		String layoutFriendlyURL = layout.getFriendlyURL(LocaleUtil.US);

		friendlyURLSeparators.put(
			JournalArticle.class.getName(),
			layoutFriendlyURL.replaceAll(StringPool.SLASH, StringPool.BLANK));

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-other-asset-type-may-use-this-prefix",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithRepeatedSeparatorAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "test1");
		friendlyURLSeparators.put(
			"com.liferay.blogs.model.BlogsEntry", "test1");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		String errors = HttpComponentsUtil.getParameter(
			mockActionResponse.getRedirect(),
			"_com_liferay_configuration_admin_web_portlet_" +
				"InstanceSettingsPortlet_errors",
			false);

		JSONObject errorsJSONObject = _jsonFactory.createJSONObject(
			HttpComponentsUtil.decodeURL(errors));

		JSONObject fieldsJSONObject = errorsJSONObject.getJSONObject("fields");

		if (fieldsJSONObject.has(
				"_com_liferay_configuration_admin_web_portlet_" +
					"InstanceSettingsPortlet_" +
						JournalArticle.class.getName())) {

			_assertRedirectURL(
				JournalArticle.class.getName(),
				"friendly-url-separator-error-other-asset-type-may-use-this-" +
					"prefix",
				friendlyURLSeparators, mockActionResponse.getRedirect());
		}
		else {
			_assertRedirectURL(
				"com.liferay.blogs.model.BlogsEntry",
				"friendly-url-separator-error-other-asset-type-may-use-this-" +
					"prefix",
				friendlyURLSeparators, mockActionResponse.getRedirect());
		}
	}

	@Test
	public void testDoProcessActionWithReservedKeywordAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "web");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-other-asset-type-may-use-this-prefix",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	@Test
	public void testDoProcessActionWithReservedLanguageKeywordAsAFriendlyURLSeparator()
		throws Exception {

		Map<String, String> friendlyURLSeparators =
			_getRandomFriendlyURLSeparatorsMap();

		friendlyURLSeparators.put(JournalArticle.class.getName(), "en");

		MockActionResponse mockActionResponse = new MockActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(friendlyURLSeparators),
			mockActionResponse);

		_assertRedirectURL(
			JournalArticle.class.getName(),
			"friendly-url-separator-error-other-asset-type-may-use-this-prefix",
			friendlyURLSeparators, mockActionResponse.getRedirect());
	}

	private void _assertRedirectURL(
		String className, String errorFieldKey,
		Map<String, String> friendlyURLSeparators, String redirect) {

		Assert.assertNotNull(redirect);

		String errors = HttpComponentsUtil.getParameter(
			redirect,
			"_com_liferay_configuration_admin_web_portlet_" +
				"InstanceSettingsPortlet_errors",
			false);

		Assert.assertNotNull(errors);

		Assert.assertEquals(
			JSONUtil.put(
				"errorMessage",
				_language.get(
					LocaleUtil.US,
					"friendly-url-separator-error-changes-could-not-be-saved")
			).put(
				"fields",
				JSONUtil.put(
					"_com_liferay_configuration_admin_web_portlet_" +
						"InstanceSettingsPortlet_" + className,
					_language.get(LocaleUtil.US, errorFieldKey))
			).toString(),
			HttpComponentsUtil.decodeURL(errors));

		String urlSeparator = HttpComponentsUtil.getParameter(
			redirect,
			"_com_liferay_configuration_admin_web_portlet_" +
				"InstanceSettingsPortlet_" + className,
			false);

		Assert.assertNotNull(urlSeparator);

		Assert.assertEquals(
			friendlyURLSeparators.get(className),
			HttpComponentsUtil.decodeURL(urlSeparator));
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String> friendlyURLSeparators)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.setParameter(
			"redirect", "http://localhost:8080");

		for (Map.Entry<String, String> friendlyURLSeparator :
				friendlyURLSeparators.entrySet()) {

			mockLiferayPortletActionRequest.setParameter(
				friendlyURLSeparator.getKey(), friendlyURLSeparator.getValue());
		}

		return mockLiferayPortletActionRequest;
	}

	private Map<String, String> _getRandomFriendlyURLSeparatorsMap() {
		Map<String, String> friendlyURLSeparators = new HashMap<>();

		for (FriendlyURLResolver friendlyURLResolver :
				FriendlyURLResolverRegistryUtil.
					getFriendlyURLResolversAsCollection()) {

			if (!friendlyURLResolver.isURLSeparatorConfigurable()) {
				continue;
			}

			friendlyURLSeparators.put(
				friendlyURLResolver.getKey(),
				_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
					RandomTestUtil.randomString()));
		}

		return friendlyURLSeparators;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		Layout layout = new LayoutImpl();

		layout.setType(LayoutConstants.TYPE_CONTROL_PANEL);

		themeDisplay.setLayout(layout);

		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPpid(
			"com_liferay_configuration_admin_web_portlet_" +
				"InstanceSettingsPortlet");
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _testDoProcessActionSaveConfiguration(
			Map<String, String> friendlyURLSeparators)
		throws Exception {

		ConfigurationTestUtil.updateConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class.getName(),
			() -> {
				_mvcActionCommand.processAction(
					_getMockLiferayPortletActionRequest(friendlyURLSeparators),
					new MockLiferayPortletActionResponse());

				Configuration configuration =
					_configurationAdmin.getConfiguration(
						FriendlyURLSeparatorCompanyConfiguration.class.
							getName(),
						StringPool.QUESTION);

				configuration.update();
			});

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_friendlyURLSeparatorConfigurationManager.
				getFriendlyURLSeparatorsJSON(_company.getCompanyId()));

		for (Map.Entry<String, String> friendlyURLSeparator :
				friendlyURLSeparators.entrySet()) {

			String normalizedFriendlyURLSeparator =
				_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
					friendlyURLSeparator.getValue());

			Assert.assertEquals(
				StringPool.SLASH + normalizedFriendlyURLSeparator +
					StringPool.SLASH,
				jsonObject.get(friendlyURLSeparator.getKey()));
		}
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Inject
	private FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Language _language;

	@Inject(
		filter = "mvc.command.name=/instance_settings/friendly_url_separator_save_company_configuration"
	)
	private MVCActionCommand _mvcActionCommand;

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		public String getRedirect() {
			return _location;
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			_location = location;
		}

		private String _location;

	}

}