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

package com.liferay.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Release;
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
	public void testDeprecatedModulesUpgradeChat() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpChatModuleData", "com.liferay.chat.service",
			"dependencies/chat-tables.sql", null);
	}

	@Test
	public void testDeprecatedModulesUpgradeDictionary() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpDictionaryModuleData", "com.liferay.dictionary.web", null,
			"com_liferay_dictionary_web_portlet_DictionaryPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeDirectory() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpDirectoryModuleData", "com.liferay.directory.web", null,
			"com_liferay_directory_web_portlet_DirectoryPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeHelloWorld() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpHelloWorldModuleData", "com.liferay.hello.world.web", null,
			"com_liferay_hello_world_web_portlet_HelloWorldPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeImageEditor() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpImageEditorModuleData",
			"com.liferay.frontend.image.editor.web", null,
			"com_liferay_image_editor_web_portlet_ImageEditorPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeInvitation() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpInvitationModuleData", "com.liferay.invitation.web", null,
			"com_liferay_invitation_web_portlet_InvitationPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeMailReader() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpMailReaderModuleData", "com.liferay.mail.reader.service",
			"dependencies/mail-reader-tables.sql",
			"com_liferay_mail_reader_web_portlet_MailPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeOpenSocial() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpOpenSocialModuleData", "opensocial-portlet",
			"dependencies/opensocial-tables.sql", "3_WAR_opensocialportlet");
	}

	@Test
	public void testDeprecatedModulesUpgradePrivateMessaging()
		throws Exception {

		_testDeprecatedModulesUpgrade(
			"cleanUpPrivateMessagingModuleData",
			"com.liferay.social.privatemessaging.service",
			"dependencies/private-messaging-tables.sql",
			"com_liferay_social_privatemessaging_web_portlet_" +
				"PrivateMessagingPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeShopping() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpShoppingModuleData", "com.liferay.shopping.service",
			"dependencies/shopping-tables.sql",
			"com_liferay_shopping_web_portlet_ShoppingPortlet");
	}

	@Test
	public void testDeprecatedModulesUpgradeSoftwareCatalog() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpSoftwareCatalogModuleData",
			"com.liferay.softwarecatalog.service",
			"dependencies/software-catalog-tables.sql", "98");
	}

	@Test
	public void testDeprecatedModulesUpgradeTwitter() throws Exception {
		_testDeprecatedModulesUpgrade(
			"cleanUpTwitterModuleData", "com.liferay.twitter.service",
			"dependencies/twitter-tables.sql",
			"com_liferay_twitter_web_portlet_TwitterPortlet");
	}

	private void _testDeprecatedModulesUpgrade(
			String propertyKey, String servletContextName, String sqlFilePath,
			String portletPreferencePortletId)
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
	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.data.cleanup.internal.configuration." +
			"DataCleanupConfiguration";

	private static final String[] _SERVLET_CONTEXT_NAMES = {
		"com.liferay.chat.service", "com.liferay.dictionary.web",
		"com.liferay.directory.web", "com.liferay.frontend.image.editor.web",
		"com.liferay.hello.world.web", "com.liferay.invitation.web",
		"com.liferay.mail.reader.service", "com.liferay.shopping.service",
		"com.liferay.social.privatemessaging.service",
		"com.liferay.softwarecatalog.service", "com.liferay.twitter.service",
		"opensocial-portlet"
	};

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	@DeleteAfterTestRun
	private Layout _layout;

}