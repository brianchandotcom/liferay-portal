/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuardTestRuleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class PortalInstancesConfigurationFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			PortalInstancesConfigurationFactoryTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void test() throws Exception {
		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instance.lifecycle.internal." +
					"PortalInstanceLifecycleListenerManagerImpl",
				LoggerTestUtil.WARN)) {

			String webId = RandomTestUtil.randomString();
			CountDownLatch countDownLatch = new CountDownLatch(1);

			ServiceRegistration<PortalInstanceLifecycleListener>
				serviceRegistration = _bundleContext.registerService(
					PortalInstanceLifecycleListener.class,
					new BasePortalInstanceLifecycleListener() {

						@Override
						public void portalInstanceRegistered(Company company)
							throws Exception {

							if (Objects.equals(company.getWebId(), webId)) {
								countDownLatch.countDown();
							}
						}

					},
					null);

			Configuration configuration =
				_configurationAdmin.getFactoryConfiguration(
					"com.liferay.portal.instances.internal.configuration." +
						"PortalInstancesConfiguration",
					webId, StringPool.QUESTION);

			try {
				ConfigurationTestUtil.saveConfiguration(
					configuration,
					HashMapDictionaryBuilder.<String, Object>put(
						"mx", webId.concat(".foo.bar")
					).put(
						"virtualHostname", webId.concat(".foo.bar")
					).build());

				countDownLatch.await(60, TimeUnit.SECONDS);

				_company = _companyLocalService.getCompanyByWebId(webId);

				Assert.assertNotNull(_company);
			}
			finally {
				ConfigurationTestUtil.deleteConfiguration(configuration);

				serviceRegistration.unregister();

				DataGuardTestRuleUtil.smartDelete(
					_companyLocalService, Company.class, _company);
			}
		}
	}

	private BundleContext _bundleContext;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}