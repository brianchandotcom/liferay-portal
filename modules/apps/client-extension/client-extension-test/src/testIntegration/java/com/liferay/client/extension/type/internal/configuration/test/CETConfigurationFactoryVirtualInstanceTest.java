/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.feature.flag.test.util.FeatureFlagTestHelper;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeoutException;

import javax.portlet.Portlet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gregory Amerson
 */
@RunWith(Arquillian.class)
public class CETConfigurationFactoryVirtualInstanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Company company = CompanyLocalServiceUtil.addCompany(
			null, _VIRTUAL_HOSTNAME, _VIRTUAL_HOSTNAME, _VIRTUAL_HOSTNAME, 0,
			true, null, null, null, null, null, null);

		_autoCloseables.add(
			() -> CompanyLocalServiceUtil.deleteCompany(company));

		_virtualInstanceCompanyId = company.getCompanyId();
	}

	@AfterClass
	public static void tearDownClass() {
		ListIterator<AutoCloseable> listIterator = _autoCloseables.listIterator(
			_autoCloseables.size());

		while (listIterator.hasPrevious()) {
			AutoCloseable previousAutoCloseable = listIterator.previous();

			try {
				previousAutoCloseable.close();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	@Test
	public void testCETFactoryWithVirtualInstanceAndFF() throws Exception {
		FeatureFlagTestHelper featureFlagTestHelper =
			new FeatureFlagTestHelper();

		featureFlagTestHelper.setFeatureFlagValue(
			_virtualInstanceCompanyId, "LPS-202104", true);

		String liferayMode = SystemProperties.get("liferay.mode");

		try {
			SystemProperties.set("liferay.mode", "non-test");

			Dictionary<String, Object> properties =
				HashMapDictionaryBuilder.<String, Object>put(
					"baseURL", "${portalURL}/o/test_vi.localtest.me"
				).put(
					"buildTimestamp", System.currentTimeMillis()
				).put(
					"description", ""
				).put(
					"dxp.lxc.liferay.com.virtualInstanceId", "vi.localtest.me"
				).put(
					"name", "Test vi.localtest.me"
				).put(
					"projectId", "test"
				).put(
					"projectName", "test"
				).put(
					"properties", new String[] {""}
				).put(
					"sourceCodeURL", ""
				).put(
					"type", "customElement"
				).put(
					"typeSettings",
					new String[] {
						"friendlyURLMapping=test", "instanceable=false",
						"urls=index.js", "useESM=false", "htmlElementName=test",
						"portletCategoryName=category.client-extensions"
					}
				).put(
					"webContextPath", "/test_vi.localtest.me"
				).build();

			Bundle bundle = FrameworkUtil.getBundle(
				CETConfigurationFactoryVirtualInstanceTest.class);

			BundleContext bundleContext = bundle.getBundleContext();

			Configuration configuration = OSGiServiceUtil.callService(
				bundleContext, ConfigurationAdmin.class,
				(ConfigurationAdmin configurationAdmin) ->
					configurationAdmin.getFactoryConfiguration(
						"com.liferay.client.extension.type.configuration." +
							"CETConfiguration",
						"test/vi.localtest.me", StringPool.QUESTION));

			ConfigurationTestUtil.saveConfiguration(
				configuration.getPid(), properties);

			_autoCloseables.add(
				() -> ConfigurationTestUtil.deleteConfiguration(configuration));

			CET cet = _cetManager.getCET(_virtualInstanceCompanyId, "LXC:test");

			Assert.assertNotNull(cet);

			Assert.assertEquals("Test vi.localtest.me", cet.getName());

			String filterString = StringBundler.concat(
				"(&(javax.portlet.name=com_liferay_client_extension_web",
				"_internal_portlet_ClientExtensionEntryPortlet_",
				_virtualInstanceCompanyId, "_LXC_test)",
				"(objectClass=javax.portlet.Portlet))");
			int timeout = 10_000;

			ServiceTracker<Portlet, Portlet> serviceTracker =
				ServiceTrackerFactory.open(bundleContext, filterString, null);

			try {
				Portlet portlet = serviceTracker.waitForService(timeout);

				if (portlet == null) {
					throw new TimeoutException(
						StringBundler.concat(
							"Time out on waiting for ", filterString, " after ",
							timeout, "ms"));
				}

				ServiceReference<?> serviceReference =
					serviceTracker.getServiceReference();

				String[] prop = (String[])serviceReference.getProperty(
					"com.liferay.portlet.header-portal-javascript");

				Assert.assertFalse(prop[0].contains("?t="));
			}
			finally {
				serviceTracker.close();
			}
		}
		finally {
			featureFlagTestHelper.setFeatureFlagValue(
				_virtualInstanceCompanyId, "LPS-202104", false);
			SystemProperties.set("liferay.mode", liferayMode);
		}
	}

	private static final String _VIRTUAL_HOSTNAME = "vi.localtest.me";

	private static final Log _log = LogFactoryUtil.getLog(
		CETConfigurationFactoryVirtualInstanceTest.class);

	private static final List<AutoCloseable> _autoCloseables =
		new ArrayList<>();
	private static long _virtualInstanceCompanyId;

	@Inject
	private CETManager _cetManager;

}