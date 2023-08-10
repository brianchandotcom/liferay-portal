/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gregory Amerson
 */
@RunWith(Arquillian.class)
public class RoutesPortalK8sConfigMapModifierTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_bundle = FrameworkUtil.getBundle(
			RoutesPortalK8sConfigMapModifierTest.class);

		_bundleContext = _bundle.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, PortalK8sConfigMapModifier.class, null);

		_serviceTracker.open();

		_portalK8sConfigMapModifier = _serviceTracker.waitForService(2000);

		Assert.assertNotNull(_portalK8sConfigMapModifier);

		ServiceReference<PortalK8sConfigMapModifier> serviceReference =
			_serviceTracker.getServiceReference();

		Assert.assertEquals(
			-1, serviceReference.getProperty("service.ranking"));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceTracker.close();
	}

	@Test
	public void testDefaultDXPRoutes() throws Exception {
		Path dxpPath = Paths.get(
			PropsUtil.get(PropsKeys.LIFERAY_HOME), "routes/default/dxp");

		Assert.assertTrue(Files.exists(dxpPath));

		File dxpDir = dxpPath.toFile();

		String[] fileNames = dxpDir.list();

		Assert.assertEquals(Arrays.toString(fileNames), 4, fileNames.length);

		Path lxcDXPMainDomainPath = dxpPath.resolve(
			"com.liferay.lxc.dxp.main.domain");

		Assert.assertTrue(Files.exists(lxcDXPMainDomainPath));
		Assert.assertEquals(
			"localhost:8080",
			new String(Files.readAllBytes(lxcDXPMainDomainPath)));
	}

	@Test
	public void testMultipleVirtualHostsInNondefaultCompany() throws Exception {
		String webId = "fizzbuzz.com";

		_company = _companyLocalService.addCompany(
			null, webId, webId, webId, 0, true, null, null, null, null, null,
			null);

		Path dxpPath = Paths.get(
			PropsUtil.get(PropsKeys.LIFERAY_HOME), "routes", webId, "dxp");

		Assert.assertTrue(Files.exists(dxpPath));

		File dxpDir = dxpPath.toFile();

		String[] fileNames = dxpDir.list();

		Assert.assertEquals(Arrays.toString(fileNames), 4, fileNames.length);

		Path lxcDXPMainDomainPath = dxpPath.resolve(
			"com.liferay.lxc.dxp.main.domain");

		Assert.assertTrue(Files.exists(lxcDXPMainDomainPath));
		Assert.assertEquals(
			webId + ":8080",
			new String(Files.readAllBytes(lxcDXPMainDomainPath)));

		VirtualHost virtualHost = _virtualHostLocalService.createVirtualHost(
			_counterLocalService.increment());

		virtualHost.setCompanyId(_company.getCompanyId());
		virtualHost.setHostname("foobar.com");

		_virtualHostLocalService.addVirtualHost(virtualHost);

		List<String> lxcDXPDomains = StringUtil.split(
			new String(
				Files.readAllBytes(
					dxpPath.resolve("com.liferay.lxc.dxp.domains"))),
			CharPool.NEW_LINE);

		Assert.assertEquals(lxcDXPDomains.toString(), 2, lxcDXPDomains.size());

		Assert.assertEquals("fizzbuzz.com:8080", lxcDXPDomains.get(0));
		Assert.assertEquals("foobar.com:8080", lxcDXPDomains.get(1));
	}

	@Test
	public void testProjectRoutes() throws Exception {
		String projectName = RandomTestUtil.randomString();
		String serviceId = RandomTestUtil.randomString();

		String configMapName = StringBundler.concat(
			projectName, StringPool.DASH, TestPropsValues.COMPANY_WEB_ID,
			"-lxc-ext-init-metadata");

		_portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.put("foo", "bar");
				data.put("fizz", "buzz");

				Map<String, String> labels = configMapModel.labels();

				labels.put("lxc.liferay.com/metadataType", "ext-init");
				labels.put("ext.lxc.liferay.com/projectName", projectName);
				labels.put("ext.lxc.liferay.com/serviceId", serviceId);
				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					TestPropsValues.COMPANY_WEB_ID);
			},
			configMapName);

		Path projectMetadataPath = Paths.get(
			PropsUtil.get(PropsKeys.LIFERAY_HOME), "routes/default",
			projectName);

		Assert.assertTrue(
			projectMetadataPath.toString() + " should exist",
			Files.exists(projectMetadataPath));

		File projectMetadataDir = projectMetadataPath.toFile();

		String[] fileNames = projectMetadataDir.list();

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Path fooPath = projectMetadataPath.resolve("foo");

		Assert.assertTrue(
			fooPath.toString() + " does not exist", Files.exists(fooPath));

		Assert.assertEquals("bar", new String(Files.readAllBytes(fooPath)));

		Path fizzPath = projectMetadataPath.resolve("fizz");

		Assert.assertTrue(
			fizzPath.toString() + " does not exist", Files.exists(fooPath));

		Assert.assertEquals("buzz", new String(Files.readAllBytes(fizzPath)));
	}

	private static Bundle _bundle;
	private static BundleContext _bundleContext;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static PortalK8sConfigMapModifier _portalK8sConfigMapModifier;

	private static ServiceTracker
		<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>
			_serviceTracker;

	private Company _company;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}