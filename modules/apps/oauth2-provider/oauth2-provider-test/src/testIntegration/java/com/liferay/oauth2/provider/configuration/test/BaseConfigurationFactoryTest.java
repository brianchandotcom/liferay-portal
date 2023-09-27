/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration;
import com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class BaseConfigurationFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetFactoryConfiguration() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"_portalK8sConfigMapModifier.cardinality.minimum", 0
			).put(
				"baseURL", "http://foo.me"
			).put(
				"companyId", companyId
			).build();

		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationHeadlessServerConfiguration.class.
				getName(),
			properties,
			_userLocalService.getUserByScreenName(
				companyId, PropsValues.DEFAULT_ADMIN_SCREEN_NAME));
		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationUserAgentConfiguration.class.getName(),
			properties, _userLocalService.getGuestUser(companyId));

		_user = UserTestUtil.addUser();

		properties.put("userAccountScreenName", _user.getScreenName());

		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationHeadlessServerConfiguration.class.
				getName(),
			properties, _user);
	}

	private OAuth2Application _fetchOAuthApplication(
			String externalReferenceCode)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(50);

		OAuth2Application oAuth2Application = null;

		while ((countDownLatch.getCount() > 0) && (oAuth2Application == null)) {
			try {
				oAuth2Application =
					_oAuth2ApplicationLocalService.
						getOAuth2ApplicationByExternalReferenceCode(
							externalReferenceCode,
							TestPropsValues.getCompanyId());
			}
			catch (Exception exception) {

				// Ignore

			}

			countDownLatch.countDown();

			countDownLatch.await(10, TimeUnit.MILLISECONDS);
		}

		return oAuth2Application;
	}

	private void _testGetFactoryConfiguration(
			String className, Dictionary<String, Object> properties, User user)
		throws Exception {

		String externalReferenceCode = "foo";

		Configuration configuration =
			_configurationAdmin.getFactoryConfiguration(
				className, externalReferenceCode, StringPool.QUESTION);

		try {
			ConfigurationTestUtil.saveConfiguration(configuration, properties);

			OAuth2Application oAuth2Application = _fetchOAuthApplication(
				externalReferenceCode);

			Assert.assertNotNull(oAuth2Application);
			Assert.assertEquals(
				user.getUserId(),
				oAuth2Application.getClientCredentialUserId());
			Assert.assertEquals(
				externalReferenceCode, oAuth2Application.getName());
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(configuration);
		}

		Thread.sleep(200);

		OAuth2Application oAuth2Application = _fetchOAuthApplication(
			externalReferenceCode);

		Assert.assertNull(oAuth2Application);
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private User _user;

}