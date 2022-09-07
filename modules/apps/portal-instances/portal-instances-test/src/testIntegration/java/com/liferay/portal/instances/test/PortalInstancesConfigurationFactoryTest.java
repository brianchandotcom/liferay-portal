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

package com.liferay.portal.instances.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.instances.configuration.PortalInstancesConfiguration;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

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
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

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

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			PortalInstancesConfigurationFactoryTest.class);

		_bundleContext = bundle.getBundleContext();

		_webId = RandomTestUtil.randomString();

		_portalInstanceConfiguration =
			_configurationAdmin.getFactoryConfiguration(
				PortalInstancesConfiguration.class.getName(), _webId,
				StringPool.QUESTION);

		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			FrameworkUtil.createFilter(
				"(component.name=com.liferay.portal.instances.internal." +
					"configuration.PortalInstancesConfigurationFactory)"),
			null);

		_serviceTracker.open();

		ConfigurationTestUtil.saveConfiguration(
			_portalInstanceConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"mx", _webId.concat(".foo.bar")
			).put(
				"virtualHostname", _webId.concat(".foo.bar")
			).build());

		// It can take a very long time to instantiate a company. But by
		// the time the factoryInstance for a particular configuration
		// is available the company should have been created.

		Object factoryInstance = _serviceTracker.waitForService(40000);

		Assert.assertNotNull(factoryInstance);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_portalInstanceConfiguration);

		_serviceTracker.close();
	}

	@Test
	public void testCreateVirtualInstanceFromFactory() throws Exception {
		_company = _companyLocalService.getCompanyByWebId(_webId);

		Assert.assertNotNull(_company);
	}

	private static BundleContext _bundleContext;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Configuration _portalInstanceConfiguration;
	private static ServiceTracker<Object, Object> _serviceTracker;
	private static String _webId;

	@DeleteAfterTestRun
	private Company _company;

}