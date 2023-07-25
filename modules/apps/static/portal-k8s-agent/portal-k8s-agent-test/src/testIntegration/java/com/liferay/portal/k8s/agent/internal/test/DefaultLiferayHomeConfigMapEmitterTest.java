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

package com.liferay.portal.k8s.agent.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
public class DefaultLiferayHomeConfigMapEmitterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_bundle = FrameworkUtil.getBundle(
			DefaultLiferayHomeConfigMapEmitterTest.class);

		_bundleContext = _bundle.getBundleContext();

		_liferayHomePath = Paths.get(System.getProperty("liferay.home"));

		Assert.assertTrue(
			"liferay.home does not exist", Files.exists(_liferayHomePath));

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
	public void testDefaultDxpMetadata() throws Exception {
		Path dxpMetadataPath = _liferayHomePath.resolve(
			"cx-metadata/default/dxp-metadata");

		Assert.assertTrue(
			dxpMetadataPath.toString() + " does not exist",
			Files.exists(dxpMetadataPath));

		File dxpMetadataDir = dxpMetadataPath.toFile();

		String[] files = dxpMetadataDir.list();

		Assert.assertEquals(Arrays.toString(files), 3, files.length);

		Path mainDomainPath = dxpMetadataPath.resolve(
			"com.liferay.lxc.dxp.mainDomain");

		Assert.assertTrue(
			mainDomainPath.toString() + " does not exist",
			Files.exists(mainDomainPath));

		Assert.assertEquals(
			"localhost:8080", new String(Files.readAllBytes(mainDomainPath)));
	}

	@Test
	public void testMultipleVirtualHostsInVirtualInstance() throws Exception {
		String webId = "fizzbuzz.com";

		_company = _companyLocalService.addCompany(
			null, webId, webId, webId, 0, true, null, null, null, null, null,
			null);

		_virtualHost = _virtualHostLocalService.createVirtualHost(-1);

		_virtualHost.setCompanyId(_company.getCompanyId());

		_virtualHost.setHostname("foobar.com");

		_virtualHostLocalService.addVirtualHost(_virtualHost);

		Path dxpMetadataPath = _liferayHomePath.resolve(
			"cx-metadata/" + webId + "/dxp-metadata");

		Assert.assertTrue(
			dxpMetadataPath.toString() + " does not exist",
			Files.exists(dxpMetadataPath));

		File dxpMetadataDir = dxpMetadataPath.toFile();

		String[] files = dxpMetadataDir.list();

		Assert.assertEquals(Arrays.toString(files), 3, files.length);

		Path mainDomainPath = dxpMetadataPath.resolve(
			"com.liferay.lxc.dxp.mainDomain");

		Assert.assertTrue(
			mainDomainPath.toString() + " does not exist",
			Files.exists(mainDomainPath));

		Assert.assertEquals(
			webId + ":8080", new String(Files.readAllBytes(mainDomainPath)));

		Path domainsPath = dxpMetadataPath.resolve(
			"com.liferay.lxc.dxp.domains");

		List<String> lxcDXPDomains = StringUtil.split(
			new String(Files.readAllBytes(domainsPath)), CharPool.NEW_LINE);

		Assert.assertEquals(lxcDXPDomains.toString(), 2, lxcDXPDomains.size());

		Assert.assertEquals("fizzbuzz.com:8080", lxcDXPDomains.get(0));

		Assert.assertEquals("foobar.com:8080", lxcDXPDomains.get(1));
	}

	@Test
	public void testProjectMetadata() throws Exception {
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

		Path projectMetadataPath = _liferayHomePath.resolve(
			Paths.get("cx-metadata/default", projectName));

		Assert.assertTrue(
			projectMetadataPath.toString() + " should exist",
			Files.exists(projectMetadataPath));

		File projectMetadataDir = projectMetadataPath.toFile();

		String[] files = projectMetadataDir.list();

		Assert.assertEquals(Arrays.toString(files), 2, files.length);

		Path fooPath = projectMetadataPath.resolve("foo");

		Assert.assertTrue(
			fooPath.toString() + " does not exist", Files.exists(fooPath));

		Assert.assertEquals("bar", new String(Files.readAllBytes(fooPath)));

		Path fizzPath = projectMetadataPath.resolve("fizz");

		Assert.assertTrue(
			fizzPath.toString() + " does not exist", Files.exists(fooPath));

		Assert.assertEquals("buzz", new String(Files.readAllBytes(fizzPath)));
	}

	@Test
	public void testVirtualInstanceDxpMetadata() throws Exception {
		String webId = "foo.lxc.com";

		_companyLocalService.addCompany(
			null, webId, webId, webId, 0, true, null, null, null, null, null,
			null);

		Path dxpMetadataPath = _liferayHomePath.resolve(
			"cx-metadata/" + webId + "/dxp-metadata");

		Assert.assertTrue(
			dxpMetadataPath.toString() + " does not exist",
			Files.exists(dxpMetadataPath));

		File dxpMetadataDir = dxpMetadataPath.toFile();

		String[] files = dxpMetadataDir.list();

		Assert.assertEquals(Arrays.toString(files), 3, files.length);

		Path mainDomainPath = dxpMetadataPath.resolve(
			"com.liferay.lxc.dxp.mainDomain");

		Assert.assertTrue(
			mainDomainPath.toString() + " does not exist",
			Files.exists(mainDomainPath));

		Assert.assertEquals(
			webId + ":8080", new String(Files.readAllBytes(mainDomainPath)));
	}

	private static Bundle _bundle;
	private static BundleContext _bundleContext;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Path _liferayHomePath;

	@Inject
	private static PortalK8sConfigMapModifier _portalK8sConfigMapModifier;

	private static ServiceTracker
		<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>
			_serviceTracker;

	private Company _company;
	private VirtualHost _virtualHost;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}