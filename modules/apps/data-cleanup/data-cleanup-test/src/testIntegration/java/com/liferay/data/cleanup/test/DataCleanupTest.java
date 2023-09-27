/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class DataCleanupTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		for (String servletContextName : _SERVLET_CONTEXT_NAMES) {
			_releaseLocalService.addRelease(servletContextName, "0.0.0");
		}
	}

	@After
	public void tearDown() {
		for (String servletContextName : _SERVLET_CONTEXT_NAMES) {
			Release release = _releaseLocalService.fetchRelease(
				servletContextName);

			if (release != null) {
				_releaseLocalService.deleteRelease(release);
			}
		}
	}

	@Test
	public void testDeprecatedModulesUpgradeAmazonRankings() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpAmazonRankingsModuleData",
			"com.liferay.amazon.rankings.web", null,
			"com_liferay_amazon_rankings_web_portlet_AmazonRankingsPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeChat() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpChatModuleData", "com.liferay.chat.service",
			"dependencies/chat-tables.sql", null, null);
	}

	@Test
	public void testDeprecatedModulesUpgradeCurrencyConverter()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpCurrencyConverterModuleData",
			"com.liferay.currency.converter.web", null,
			"com_liferay_currency_converter_web_portlet_" +
				"CurrencyConverterPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeDictionary() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpDictionaryModuleData", "com.liferay.dictionary.web", null,
			"com_liferay_dictionary_web_portlet_DictionaryPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeDirectory() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpDirectoryModuleData", "com.liferay.directory.web", null,
			"com_liferay_directory_web_portlet_DirectoryPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeDocumentLibraryFileRank()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpDocumentLibraryFileRankModuleData",
			"com.liferay.document.library.file.rank.service",
			"dependencies/document-library-file-rank-tables.sql", null, null);
	}

	@Test
	public void testDeprecatedModulesUpgradeGoogleMaps() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpGoogleMapsModuleData", "com.liferay.google.maps.web", null,
			"com_liferay_google_maps_web_portlet_GoogleMapsPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeHelloWorld() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpHelloWorldModuleData", "com.liferay.hello.world.web", null,
			"com_liferay_hello_world_web_portlet_HelloWorldPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeHTMLPreview() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpHTMLPreviewModuleData", "com.liferay.html.preview.service",
			"dependencies/html-preview-tables.sql", null, null);
	}

	@Test
	public void testDeprecatedModulesUpgradeImageEditor() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpImageEditorModuleData",
			"com.liferay.frontend.image.editor.web", null,
			"com_liferay_image_editor_web_portlet_ImageEditorPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeInvitation() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpInvitationModuleData", "com.liferay.invitation.web", null,
			"com_liferay_invitation_web_portlet_InvitationPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeLoanCalculator() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpLoanCalculatorModuleData",
			"com.liferay.loan.calculator.web", null,
			"com_liferay_loan_calculator_portlet_LoanCalculatorPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeMailReader() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpMailReaderModuleData", "com.liferay.mail.reader.service",
			"dependencies/mail-reader-tables.sql",
			"com_liferay_mail_reader_web_portlet_MailPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeNetworkUtilities()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpNetworkUtilitiesModuleData",
			"com.liferay.network.utilities.web", null,
			"com_liferay_network_utilities_web_portlet_NetworkUtilitiesPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeOAuth() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpOAuthModuleData", "com.liferay.oauth.service",
			"dependencies/oauth-tables.sql",
			"com_liferay_oauth_web_internal_portlet_AdminPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeOpenSocial() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpOpenSocialModuleData", "opensocial-portlet",
			"dependencies/opensocial-tables.sql", "3_WAR_opensocialportlet",
			"OPEN_SOCIAL_DATA_");
	}

	@Test
	public void testDeprecatedModulesUpgradePasswordGenerator()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpPasswordGeneratorModuleData",
			"com.liferay.password.generator.web", null,
			"com_liferay_password_generator_web_portlet_" +
				"PasswordGeneratorPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradePortalSecurityWedeployAuth()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpPortalSecurityWedeployAuthModuleData",
			"com.liferay.portal.security.wedeploy.auth.service",
			"dependencies/portal-security-wedeploy-auth-tables.sql",
			"com_liferay_portal_security_wedeploy_auth_web_internal_" +
				"portlet_WeDeployAuthAdminPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradePrivateMessaging()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpPrivateMessagingModuleData",
			"com.liferay.social.privatemessaging.service",
			"dependencies/private-messaging-tables.sql",
			"com_liferay_social_privatemessaging_web_portlet_" +
				"PrivateMessagingPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeQuickNote() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpQuickNoteModuleData", "com.liferay.quick.note.web", null,
			"com_liferay_quick_note_web_portlet_QuickNotePortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeRecentDocuments() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpRecentDocumentsModuleData",
			"com.liferay.recent.documents.web", null,
			"com_liferay_recent_documents_web_portlet_RecentDocumentsPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeShopping() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpShoppingModuleData", "com.liferay.shopping.service",
			"dependencies/shopping-tables.sql",
			"com_liferay_shopping_web_portlet_ShoppingPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeSocialActivity() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpSocialActivityModuleData",
			"com.liferay.social.activity.web", null,
			"com_liferay_social_activity_web_portlet_SocialActivityPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeSocialGroupStatistics()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpSocialGroupStatisticsModuleData",
			"com.liferay.social.group.statistics.web", null,
			"com_liferay_social_group_statistics_web_portlet_" +
				"SocialGroupStatisticsPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeSocialRequest() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpSocialRequestModuleData", "com.liferay.social.requests.web",
			null,
			"com_liferay_social_requests_web_portlet_SocialRequestsPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeSocialUserStatistics()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpSocialUserStatisticsModuleData",
			"com.liferay.social.user.statistics.web", null,
			"com_liferay_social_user_statistics_web_portlet_" +
				"SocialUserStatisticsPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeSoftwareCatalog() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpSoftwareCatalogModuleData",
			"com.liferay.softwarecatalog.service",
			"dependencies/software-catalog-tables.sql",
			"com.liferay.portlet.softwarecatalog", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeTranslator() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpTranslatorModuleData", "com.liferay.translator.web", null,
			"com_liferay_translator_web_portlet_TranslatorPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeTwitter() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpTwitterModuleData", "com.liferay.twitter.service",
			"dependencies/twitter-tables.sql",
			"com_liferay_twitter_web_portlet_TwitterPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeUnitConverter() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpUnitConverterModuleData", "com.liferay.unit.converter.web",
			null, "com_liferay_unit_converter_web_portlet_UnitConverterPortlet",
			null);
	}

	@Test
	public void testDeprecatedModulesUpgradeWeatherModule() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpWeatherModuleData", "com.liferay.weather.web", null,
			"com_liferay_weather_web_portlet_WeatherPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeWebForm() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpWebFormModuleData", "com.liferay.web.form.web", null,
			"com_liferay_web_form_web_portlet_WebFormPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeWebProxy() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpWebProxyModuleData", "com.liferay.web.proxy.web", null,
			"com_liferay_web_proxy_web_portlet_WebProxyPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeWysiwyg() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpWysiwygModuleData", "com.liferay.wysiwyg.web", null,
			"com_liferay_wysiwyg_web_portlet_WYSIWYGPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeXSLContent() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpXSLContentModuleData", "com.liferay.xsl.content.web", null,
			"com_liferay_xsl_content_web_portlet_XSLContentPortlet", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeYoutube() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpYoutubeModuleData", "com.liferay.youtube.web", null,
			"com_liferay_youtube_web_portlet_YouTubePortlet", null);
	}

	private void _testDeprecatedModulesUpgrade(
			String propertyKey, String servletContextName, String sqlFilePath,
			String portletPreferencePortletId, String expandoTableName)
		throws Exception {

		if (Validator.isNotNull(sqlFilePath)) {
			try (InputStream inputStream =
					DataCleanupTest.class.getResourceAsStream(sqlFilePath)) {

				DB db = DBManagerUtil.getDB();

				db.runSQLTemplateString(StringUtil.read(inputStream), true);
			}
		}

		if (portletPreferencePortletId != null) {
			_layout = LayoutTestUtil.addTypePortletLayout(
				TestPropsValues.getGroupId());

			UnicodeProperties unicodeProperties =
				_layout.getTypeSettingsProperties();

			unicodeProperties.put(
				"test-property-1", portletPreferencePortletId);
			unicodeProperties.put(
				"test-property-2",
				portletPreferencePortletId + "," + portletPreferencePortletId);
			unicodeProperties.put(
				"test-property-3", "abc," + portletPreferencePortletId);
			unicodeProperties.put(
				"test-property-4", portletPreferencePortletId + ",def");
			unicodeProperties.put(
				"test-property-5",
				"abc," + portletPreferencePortletId + ",def");

			_layout.setTypeSettings(unicodeProperties.toString());

			_layout = _layoutLocalService.updateLayout(_layout);
		}

		String expandoColumnName = "testColumn";
		long expandoTableId = 0;
		long expandoValueId = 0;

		if (Validator.isNotNull(expandoTableName)) {
			ClassName className = _classNameLocalService.addClassName(
				expandoTableName + "test");

			ExpandoTable expandoTable = ExpandoTestUtil.addTable(
				className.getClassNameId(), expandoTableName);

			expandoTableId = expandoTable.getTableId();

			ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
				expandoTable, expandoColumnName, ExpandoColumnConstants.STRING);

			ExpandoValue expandoValue = ExpandoTestUtil.addValue(
				expandoTable, expandoColumn, className.getClassNameId(),
				"testValue");

			expandoValueId = expandoValue.getValueId();
		}

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				propertyKey, true
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID, properties)) {

			FinderCacheUtil.clearLocalCache();

			for (String currentServletContextName : _SERVLET_CONTEXT_NAMES) {
				Release release = _releaseLocalService.fetchRelease(
					currentServletContextName);

				if (servletContextName.equals(currentServletContextName)) {
					Assert.assertNull(release);
				}
				else {
					Assert.assertNotNull(release);
				}
			}
		}

		if (portletPreferencePortletId != null) {
			EntityCacheUtil.clearLocalCache();

			_layout = _layoutLocalService.getLayout(_layout.getPlid());

			UnicodeProperties unicodeProperties =
				_layout.getTypeSettingsProperties();

			Assert.assertNull(unicodeProperties.getProperty("test-property-1"));
			Assert.assertNull(unicodeProperties.getProperty("test-property-2"));
			Assert.assertEquals(
				"abc", unicodeProperties.getProperty("test-property-3"));
			Assert.assertEquals(
				"def", unicodeProperties.getProperty("test-property-4"));
			Assert.assertEquals(
				"abc,def", unicodeProperties.getProperty("test-property-5"));
		}

		if (Validator.isNotNull(expandoTableName)) {
			Assert.assertEquals(
				null,
				_expandoColumnLocalService.getColumn(
					expandoTableId, expandoColumnName));

			Assert.assertEquals(
				null,
				_expandoTableLocalService.fetchExpandoTable(expandoTableId));

			Assert.assertEquals(
				null,
				_expandoValueLocalService.fetchExpandoValue(expandoValueId));
		}
	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.data.cleanup.internal.configuration." +
			"DataCleanupConfiguration";

	private static final String[] _SERVLET_CONTEXT_NAMES = {
		"com.liferay.amazon.rankings.web",
		"com.liferay.document.library.file.rank.service",
		"com.liferay.chat.service", "com.liferay.currency.converter.web",
		"com.liferay.dictionary.web", "com.liferay.directory.web",
		"com.liferay.frontend.image.editor.web", "com.liferay.google.maps.web",
		"com.liferay.hello.world.web", "com.liferay.html.preview.service",
		"com.liferay.invitation.web", "com.liferay.loan.calculator.web",
		"com.liferay.mail.reader.service", "com.liferay.network.utilities.web",
		"com.liferay.oauth.service", "com.liferay.password.generator.web",
		"com.liferay.portal.security.wedeploy.auth.service",
		"com.liferay.quick.note.web", "com.liferay.recent.documents.web",
		"com.liferay.shopping.service", "com.liferay.social.activity.web",
		"com.liferay.social.group.statistics.web",
		"com.liferay.social.privatemessaging.service",
		"com.liferay.social.requests.web",
		"com.liferay.social.user.statistics.web",
		"com.liferay.softwarecatalog.service", "com.liferay.translator.web",
		"com.liferay.twitter.service", "com.liferay.unit.converter.web",
		"com.liferay.weather.web", "com.liferay.web.form.web",
		"com.liferay.web.proxy.web", "com.liferay.wysiwyg.web",
		"com.liferay.xsl.content.web", "com.liferay.youtube.web",
		"opensocial-portlet"
	};

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private static ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private static ExpandoValueLocalService _expandoValueLocalService;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	@DeleteAfterTestRun
	private Layout _layout;

}