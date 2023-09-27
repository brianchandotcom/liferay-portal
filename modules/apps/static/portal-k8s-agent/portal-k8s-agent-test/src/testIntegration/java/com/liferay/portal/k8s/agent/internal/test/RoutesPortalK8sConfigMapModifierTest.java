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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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

	@Test
	public void test() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			RoutesPortalK8sConfigMapModifierTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceTracker<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>
			serviceTracker = new ServiceTracker<>(
				bundleContext, PortalK8sConfigMapModifier.class, null);

		serviceTracker.open();

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			serviceTracker.waitForService(2000);

		Assert.assertNotNull(portalK8sConfigMapModifier);

		ServiceReference<PortalK8sConfigMapModifier> serviceReference =
			serviceTracker.getServiceReference();

		Assert.assertEquals(
			-1, serviceReference.getProperty("service.ranking"));

		try {
			_test(
				TestPropsValues.getCompanyId(), "localhost",
				portalK8sConfigMapModifier, "default");

			String webId = "able.com";

			Company company = _companyLocalService.addCompany(
				null, webId, webId, webId, 0, true, null, null, null, null,
				null, null);

			_test(
				company.getCompanyId(), webId, portalK8sConfigMapModifier,
				webId);
		}
		finally {
			serviceTracker.close();
		}
	}

	private void _test(
			long companyId, String hostname,
			PortalK8sConfigMapModifier portalK8sConfigMapModifier, String webId)
		throws Exception {

		// DXP

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
			hostname + ":8080",
			new String(Files.readAllBytes(lxcDXPMainDomainPath)));

		VirtualHost virtualHost = _virtualHostLocalService.createVirtualHost(
			_counterLocalService.increment());

		virtualHost.setCompanyId(companyId);

		String virtualHostHostname = RandomTestUtil.randomString();

		virtualHost.setHostname(virtualHostHostname);

		_virtualHostLocalService.addVirtualHost(virtualHost);

		List<String> lxcDXPDomains = StringUtil.split(
			new String(
				Files.readAllBytes(
					dxpPath.resolve("com.liferay.lxc.dxp.domains"))),
			CharPool.NEW_LINE);

		Assert.assertEquals(lxcDXPDomains.toString(), 2, lxcDXPDomains.size());

		Collections.sort(lxcDXPDomains);

		if (hostname.compareTo(virtualHostHostname) > 0) {
			Assert.assertEquals(
				virtualHostHostname + ":8080", lxcDXPDomains.get(0));
			Assert.assertEquals(hostname + ":8080", lxcDXPDomains.get(1));
		}
		else {
			Assert.assertEquals(hostname + ":8080", lxcDXPDomains.get(0));
			Assert.assertEquals(
				virtualHostHostname + ":8080", lxcDXPDomains.get(1));
		}

		// Ext init

		String projectName = RandomTestUtil.randomString();
		String serviceId = RandomTestUtil.randomString();

		portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.put("testKey1", "testValue1");
				data.put("testKey2", "testValue2");

				Map<String, String> labels = configMapModel.labels();

				labels.put("lxc.liferay.com/metadataType", "ext-init");
				labels.put("ext.lxc.liferay.com/projectName", projectName);
				labels.put("ext.lxc.liferay.com/serviceId", serviceId);
				labels.put("dxp.lxc.liferay.com/virtualInstanceId", webId);
			},
			StringBundler.concat(
				projectName, StringPool.DASH, webId, "-lxc-ext-init-metadata"));

		Path projectPath = Paths.get(
			PropsUtil.get(PropsKeys.LIFERAY_HOME), "routes/" + webId,
			projectName);

		Assert.assertTrue(Files.exists(projectPath));

		File projectDir = projectPath.toFile();

		fileNames = projectDir.list();

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Path testKey1Path = projectPath.resolve("testKey1");

		Assert.assertTrue(Files.exists(testKey1Path));

		Assert.assertEquals(
			"testValue1", new String(Files.readAllBytes(testKey1Path)));

		Path testKey2Path = projectPath.resolve("testKey2");

		Assert.assertTrue(Files.exists(testKey1Path));

		Assert.assertEquals(
			"testValue2", new String(Files.readAllBytes(testKey2Path)));
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}