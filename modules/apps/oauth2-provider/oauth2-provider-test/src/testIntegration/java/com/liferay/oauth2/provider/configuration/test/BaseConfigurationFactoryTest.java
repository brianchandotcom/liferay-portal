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
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.ws.rs.core.Application;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jodd.util.StringUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

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
	public void testExcessiveActivations() throws Exception {
		Configuration configuration = _createFactoryConfiguration(
			OAuth2ProviderApplicationUserAgentConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"_portalK8sConfigMapModifier.cardinality.minimum", 0
			).put(
				"baseURL", "http://foo.me"
			).put(
				"companyId",
				() -> {
					Company company = null;

					try {
						company = CompanyTestUtil.addCompany();
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}

					return company.getCompanyId();
				}
			).build());

		List<String> errorLogEntries = new ArrayList<>();

		LogListener logListener = logEntry -> {
			if (Objects.equals(logEntry.getLogLevel(), LogLevel.ERROR)) {
				errorLogEntries.add(logEntry.getMessage());
			}
		};

		_logReaderService.addLogListener(logListener);

		ServiceRegistration<?> serviceRegistration = null;

		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				TestPropsValues.getCompanyId())) {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			serviceRegistration = bundleContext.registerService(
				Application.class,
				new Application() {
				},
				null);

			Assert.assertTrue(
				StringUtil.join(errorLogEntries, "\n"),
				errorLogEntries.isEmpty());
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(configuration);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}

			_logReaderService.removeLogListener(logListener);
		}
	}

	@Test
	public void testExcessiveLogging() throws Exception {
		_testExcessiveLogging(
			OAuth2ProviderApplicationHeadlessServerConfiguration.class.
				getName());
		_testExcessiveLogging(
			OAuth2ProviderApplicationUserAgentConfiguration.class.getName());
	}

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
			companyId, properties,
			_userLocalService.getUserByScreenName(
				companyId, UserConstants.SCREEN_NAME_DEFAULT_SERVICE_ACCOUNT));

		properties.put("userAccountScreenName", "test");

		List<User> users = _userLocalService.getUsersByRoleName(
			companyId, RoleConstants.ADMINISTRATOR, 0, 1);

		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationHeadlessServerConfiguration.class.
				getName(),
			companyId, properties, users.get(0));

		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationUserAgentConfiguration.class.getName(),
			companyId, properties, _userLocalService.getGuestUser(companyId));

		_user = UserTestUtil.addUser();

		properties.put("userAccountScreenName", _user.getScreenName());

		_testGetFactoryConfiguration(
			OAuth2ProviderApplicationHeadlessServerConfiguration.class.
				getName(),
			companyId, properties, _user);
	}

	private Configuration _createFactoryConfiguration(
			String className, Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration =
			_configurationAdmin.getFactoryConfiguration(
				className, _EXTERNAL_REFERENCE_CODE, StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(configuration, properties);

		return configuration;
	}

	private OAuth2Application _fetchOAuthApplication(long companyId)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(50);

		OAuth2Application oAuth2Application = null;

		while ((countDownLatch.getCount() > 0) && (oAuth2Application == null)) {
			try {
				oAuth2Application =
					_oAuth2ApplicationLocalService.
						getOAuth2ApplicationByExternalReferenceCode(
							_EXTERNAL_REFERENCE_CODE, companyId);
			}
			catch (Exception exception) {

				// Ignore

			}

			countDownLatch.countDown();

			countDownLatch.await(10, TimeUnit.MILLISECONDS);
		}

		return oAuth2Application;
	}

	private void _testExcessiveLogging(String className) throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				className, LoggerTestUtil.INFO)) {

			_createFactoryConfiguration(
				className,
				HashMapDictionaryBuilder.<String, Object>put(
					"_portalK8sConfigMapModifier.cardinality.minimum", 0
				).put(
					"baseURL", "http://foo.me"
				).put(
					"companyId", companyId
				).build());

			Assert.assertTrue(ListUtil.isEmpty(logCapture.getLogEntries()));
		}
	}

	private void _testGetFactoryConfiguration(
			String className, long companyId,
			Dictionary<String, Object> properties, User user)
		throws Exception {

		Configuration configuration = _createFactoryConfiguration(
			className, properties);

		try {
			OAuth2Application oAuth2Application = _fetchOAuthApplication(
				companyId);

			Assert.assertNotNull(oAuth2Application);
			Assert.assertEquals(
				user.getUserId(),
				oAuth2Application.getClientCredentialUserId());
			Assert.assertEquals(
				_EXTERNAL_REFERENCE_CODE, oAuth2Application.getName());
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(configuration);
		}

		Thread.sleep(200);

		OAuth2Application oAuth2Application = _fetchOAuthApplication(companyId);

		Assert.assertNull(oAuth2Application);
	}

	private static final String _EXTERNAL_REFERENCE_CODE = "foo";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private LogReaderService _logReaderService;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}