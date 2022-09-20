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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.k8s.agent.configuration.PortalK8sAgentConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.function.AwaitingConfigurationHolder;
import com.liferay.portal.test.function.CloseableHolder;
import com.liferay.portal.test.function.ConfigurationHolder;
import com.liferay.portal.test.function.CreatingConfigurationHolder;
import com.liferay.portal.test.function.ThreadContextClassLoaderCloseableHolder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.NamespacedKubernetesClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesMixedDispatcher;
import io.fabric8.kubernetes.client.server.mock.KubernetesMockServer;
import io.fabric8.mockwebserver.Context;
import io.fabric8.mockwebserver.ServerRequest;
import io.fabric8.mockwebserver.ServerResponse;

import java.net.InetAddress;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
@Ignore
@RunWith(Arquillian.class)
public class PortalK8sAgentImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_bundle = FrameworkUtil.getBundle(PortalK8sAgentImplTest.class);

		_bundleContext = _bundle.getBundleContext();

		Map<ServerRequest, Queue<ServerResponse>> serverResponses =
			new HashMap<>();

		_kubernetesMockServer = new KubernetesMockServer(
			new Context(), new MockWebServer(), serverResponses,
			new KubernetesMixedDispatcher(
				serverResponses, Collections.emptyList()) {

				@Override
				public MockResponse dispatch(RecordedRequest request)
					throws InterruptedException {

					try (ThreadContextClassLoaderCloseableHolder
							threadContextClassLoaderCloseableHolder =
								new ThreadContextClassLoaderCloseableHolder(
									DefaultKubernetesClient.class)) {

						return super.dispatch(request);
					}
					catch (Exception exception) {
						_log.error(exception);
					}

					return null;
				}

			},
			false);

		_kubernetesMockServer.init(InetAddress.getLoopbackAddress(), 0);

		_kubernetesMockClient = _kubernetesMockServer.createClient();

		_agentConfigurationHolder = new CreatingConfigurationHolder(
			_configurationAdmin, PortalK8sAgentConfiguration.class.getName());
		_portalK8sConfigMapModifierCloseableHolder =
			new PortalK8sConfigMapModifierCloseableHolder(_bundleContext);

		_agentConfigurationHolder.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"apiServerHost", _kubernetesMockServer.getHostName()
			).put(
				"apiServerPort", _kubernetesMockServer.getPort()
			).put(
				"apiServerSSL", Boolean.FALSE
			).put(
				"caCertData",
				StringUtil.read(
					PortalK8sAgentImplTest.class, "dependencies/ca.crt")
			).put(
				"namespace", "test"
			).put(
				"saToken", "saToken"
			).build());

		_portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierCloseableHolder.waitForService(2000);

		Assert.assertNotNull(_portalK8sConfigMapModifier);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_agentConfigurationHolder.close();

		_kubernetesMockClient.close();

		_kubernetesMockServer.destroy();

		_portalK8sConfigMapModifierCloseableHolder.close();
	}

	@Test
	public void testCreateDXPMetadata() throws Exception {
		String webId = "foo.lxc.com";

		_companyLocalService.addCompany(null, webId, webId, webId, 0, true);

		ConfigMap configMap = _kubernetesMockClient.configMaps(
		).withName(
			webId.concat("-lxc-dxp-metadata")
		).waitUntilCondition(
			it -> it != null, 20, TimeUnit.SECONDS
		);

		Assert.assertNotNull(configMap);

		Map<String, String> data = configMap.getData();

		Assert.assertEquals(webId, data.get("com.liferay.lxc.dxp.domains"));
		Assert.assertEquals(webId, data.get("com.liferay.lxc.dxp.mainDomain"));

		ObjectMeta objectMeta = configMap.getMetadata();

		Map<String, String> labels = objectMeta.getLabels();

		Assert.assertEquals(
			webId, labels.get("dxp.lxc.liferay.com/virtualInstanceId"));
		Assert.assertEquals("dxp", labels.get("lxc.liferay.com/metadataType"));
	}

	@Test
	public void testCreateExtInitMetadata() throws Exception {
		String serviceId = RandomTestUtil.randomString();

		String configMapName = StringBundler.concat(
			serviceId, StringPool.DASH, TestPropsValues.COMPANY_WEB_ID,
			"-lxc-ext-init-metadata");

		_portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.put(
					"com.liferay.lxc.dxp.domains",
					TestPropsValues.COMPANY_WEB_ID);
				data.put(
					"com.liferay.lxc.dxp.mainDomain",
					TestPropsValues.COMPANY_WEB_ID);

				Map<String, String> labels = configMapModel.labels();

				labels.put("lxc.liferay.com/metadataType", "ext-init");
				labels.put("ext.lxc.liferay.com/serviceId", serviceId);
				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					TestPropsValues.COMPANY_WEB_ID);
			},
			configMapName);

		ConfigMap configMap = _kubernetesMockClient.configMaps(
		).withName(
			configMapName
		).waitUntilCondition(
			it -> it != null, 20, TimeUnit.SECONDS
		);

		Assert.assertNotNull(configMap);

		Map<String, String> data = configMap.getData();

		Assert.assertEquals(
			TestPropsValues.COMPANY_WEB_ID,
			data.get("com.liferay.lxc.dxp.domains"));
		Assert.assertEquals(
			TestPropsValues.COMPANY_WEB_ID,
			data.get("com.liferay.lxc.dxp.mainDomain"));

		ObjectMeta objectMeta = configMap.getMetadata();

		Map<String, String> labels = objectMeta.getLabels();

		Assert.assertEquals(
			TestPropsValues.COMPANY_WEB_ID,
			labels.get("dxp.lxc.liferay.com/virtualInstanceId"));
		Assert.assertEquals(
			serviceId, labels.get("ext.lxc.liferay.com/serviceId"));
		Assert.assertEquals(
			"ext-init", labels.get("lxc.liferay.com/metadataType"));
	}

	@Test
	public void testListenForExtProvisionMetadata() throws Exception {
		ConfigMapBuilder configMapBuilder = new ConfigMapBuilder();

		String serviceId = RandomTestUtil.randomString();

		String mainDomain = serviceId.concat("-extproject.lfr.sh");

		_kubernetesMockClient.configMaps(
		).createOrReplace(
			configMapBuilder.withNewMetadata(
			).withName(
				StringBundler.concat(
					serviceId, "-", TestPropsValues.COMPANY_WEB_ID,
					"-lxc-ext-provision-metadata")
			).addToLabels(
				"dxp.lxc.liferay.com/virtualInstanceId",
				TestPropsValues.COMPANY_WEB_ID
			).addToAnnotations(
				"ext.lxc.liferay.com/domains",
				serviceId.concat("-extproject.lfr.sh")
			).addToAnnotations(
				"ext.lxc.liferay.com/mainDomain", mainDomain
			).addToLabels(
				"ext.lxc.liferay.com/projectId", RandomTestUtil.randomString()
			).addToLabels(
				"ext.lxc.liferay.com/serviceId", serviceId
			).addToLabels(
				"lxc.liferay.com/metadataType", "ext-provision"
			).endMetadata(
			).addToData(
				"foo.client-extension-config.json",
				"{\"test.pid\": {\"test.key\": \"test.value\"}}"
			).build()
		);

		try (ConfigurationHolder configurationHolder =
				new AwaitingConfigurationHolder(
					_bundleContext, _configurationAdmin, "test.pid", 10000,
					TimeUnit.MILLISECONDS)) {

			Dictionary<String, Object> properties =
				configurationHolder.getProperties();

			Assert.assertEquals(
				Http.HTTPS_WITH_SLASH.concat(mainDomain),
				properties.get("baseURL"));
			Assert.assertEquals(
				TestPropsValues.getCompanyId(),
				(long)properties.get("companyId"));
			Assert.assertEquals("test.value", properties.get("test.key"));
		}
	}

	@Test
	public void testModifyConfigMap() throws Exception {
		try {
			_portalK8sConfigMapModifier.modifyConfigMap(
				configMapModel -> {
				},
				null);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertNotNull(nullPointerException);
		}

		try {
			_portalK8sConfigMapModifier.modifyConfigMap(null, null);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertNotNull(nullPointerException);
		}
	}

	public static class PortalK8sConfigMapModifierCloseableHolder
		extends CloseableHolder
			<ServiceTracker
				<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>> {

		public PortalK8sConfigMapModifierCloseableHolder(
				BundleContext bundleContext)
			throws Exception {

			super(
				serviceTracker -> serviceTracker.close(),
				() -> {
					ServiceTracker
						<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>
							serviceTracker = new ServiceTracker<>(
								bundleContext, PortalK8sConfigMapModifier.class,
								null);

					serviceTracker.open();

					return serviceTracker;
				});
		}

		public PortalK8sConfigMapModifier waitForService(long timeout)
			throws Exception {

			ServiceTracker
				<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>
					serviceTracker = get();

			return serviceTracker.waitForService(timeout);
		}

	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalK8sAgentImplTest.class);

	private static ConfigurationHolder _agentConfigurationHolder;
	private static Bundle _bundle;
	private static BundleContext _bundleContext;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static NamespacedKubernetesClient _kubernetesMockClient;
	private static KubernetesMockServer _kubernetesMockServer;
	private static PortalK8sConfigMapModifier _portalK8sConfigMapModifier;
	private static PortalK8sConfigMapModifierCloseableHolder
		_portalK8sConfigMapModifierCloseableHolder;

}