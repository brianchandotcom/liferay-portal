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
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class PortalK8sAgentImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
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

					try (ClassLoaderClosableHolder classLoaderClosableHolder =
							new ClassLoaderClosableHolder(
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
	}

	@After
	public void tearDown() {
		_kubernetesMockClient.close();

		_kubernetesMockServer.destroy();
	}

	@Test
	public void testCreateDXPMetadata() throws Exception {
		try (ConfigurationHolder configurationHolder =
				new CreatingConfigurationHolder(
					PortalK8sAgentConfiguration.class.getName());
			PortalK8sConfigMapModifierClosableHolder
				portalK8sConfigMapModifierHolder =
					new PortalK8sConfigMapModifierClosableHolder(
						_bundleContext)) {

			configurationHolder.update(
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

			PortalK8sConfigMapModifier portalK8sConfigMapModifier =
				portalK8sConfigMapModifierHolder.waitForService(2000);

			Assert.assertNotNull(portalK8sConfigMapModifier);

			String configMapName = TestPropsValues.COMPANY_WEB_ID.concat(
				"-lxc-dxp-metadata");

			portalK8sConfigMapModifier.modifyConfigMap(
				model -> {
					Map<String, String> data = model.data();

					data.put(
						"com.liferay.lxc.dxp.domains",
						TestPropsValues.COMPANY_WEB_ID);
					data.put(
						"com.liferay.lxc.dxp.mainDomain",
						TestPropsValues.COMPANY_WEB_ID);

					Map<String, String> labels = model.labels();

					labels.put(
						"dxp.lxc.liferay.com/virtualInstanceId",
						TestPropsValues.COMPANY_WEB_ID);
					labels.put("lxc.liferay.com/metadataType", "dxp");
				},
				configMapName);

			ConfigMap configMap = _kubernetesMockClient.configMaps(
			).withName(
				configMapName
			).get();

			Assert.assertNotNull(configMap);

			ObjectMeta objectMeta = configMap.getMetadata();

			Map<String, String> data = configMap.getData();

			Assert.assertEquals(
				TestPropsValues.COMPANY_WEB_ID,
				data.get("com.liferay.lxc.dxp.domains"));
			Assert.assertEquals(
				TestPropsValues.COMPANY_WEB_ID,
				data.get("com.liferay.lxc.dxp.mainDomain"));

			Map<String, String> labels = objectMeta.getLabels();

			Assert.assertEquals(
				TestPropsValues.COMPANY_WEB_ID,
				labels.get("dxp.lxc.liferay.com/virtualInstanceId"));
			Assert.assertEquals(
				"dxp", labels.get("lxc.liferay.com/metadataType"));
		}
	}

	@Test
	public void testCreateDXPMetadataFromCompany() throws Exception {
		try (ConfigurationHolder configurationHolder =
				new CreatingConfigurationHolder(
					PortalK8sAgentConfiguration.class.getName());
			PortalK8sConfigMapModifierClosableHolder
				portalK8sConfigMapModifierHolder =
					new PortalK8sConfigMapModifierClosableHolder(
						_bundleContext)) {

			configurationHolder.update(
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

			PortalK8sConfigMapModifier portalK8sConfigMapModifier =
				portalK8sConfigMapModifierHolder.waitForService(2000);

			Assert.assertNotNull(portalK8sConfigMapModifier);

			String webId = "foo-prd.lxc.com";

			_companyLocalService.addCompany(null, webId, webId, webId, 0, true);

			String configMapName = webId.concat("-lxc-dxp-metadata");

			ConfigMap configMap = _kubernetesMockClient.configMaps(
			).withName(
				configMapName
			).get();

			Assert.assertNotNull(configMap);

			ObjectMeta objectMeta = configMap.getMetadata();

			Map<String, String> data = configMap.getData();

			Assert.assertEquals(webId, data.get("com.liferay.lxc.dxp.domains"));
			Assert.assertEquals(
				webId, data.get("com.liferay.lxc.dxp.mainDomain"));

			Map<String, String> labels = objectMeta.getLabels();

			Assert.assertEquals(
				webId, labels.get("dxp.lxc.liferay.com/virtualInstanceId"));
			Assert.assertEquals(
				"dxp", labels.get("lxc.liferay.com/metadataType"));
		}
	}

	@Test
	public void testInitialization() throws Exception {
		try (ConfigurationHolder configurationHolder =
				new CreatingConfigurationHolder(
					PortalK8sAgentConfiguration.class.getName());
			PortalK8sConfigMapModifierClosableHolder
				portalK8sConfigMapModifierHolder =
					new PortalK8sConfigMapModifierClosableHolder(
						_bundleContext)) {

			PortalK8sConfigMapModifier portalK8sConfigMapModifier =
				portalK8sConfigMapModifierHolder.waitForService(2000);

			Assert.assertNull(portalK8sConfigMapModifier);

			configurationHolder.update(
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
					"namespace", "default"
				).put(
					"saToken", "saToken"
				).build());

			portalK8sConfigMapModifier =
				portalK8sConfigMapModifierHolder.waitForService(2000);

			Assert.assertNotNull(portalK8sConfigMapModifier);
		}
	}

	@Test
	public void testListenForExtProvisionMetadata() throws Exception {
		try (ConfigurationHolder configurationHolder1 =
				new CreatingConfigurationHolder(
					PortalK8sAgentConfiguration.class.getName());
			PortalK8sConfigMapModifierClosableHolder
				portalK8sConfigMapModifierHolder =
					new PortalK8sConfigMapModifierClosableHolder(
						_bundleContext)) {

			configurationHolder1.update(
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

			PortalK8sConfigMapModifier portalK8sConfigMapModifier =
				portalK8sConfigMapModifierHolder.waitForService(2000);

			Assert.assertNotNull(portalK8sConfigMapModifier);

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
				).addToAnnotations(
					"ext.lxc.liferay.com/domains",
					serviceId.concat("-extproject.lfr.sh")
				).addToAnnotations(
					"ext.lxc.liferay.com/mainDomain", mainDomain
				).addToLabels(
					"dxp.lxc.liferay.com/virtualInstanceId",
					TestPropsValues.COMPANY_WEB_ID
				).addToLabels(
					"ext.lxc.liferay.com/projectId",
					RandomTestUtil.randomString()
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

			try (ConfigurationHolder configurationHolder2 =
					new AwaitingConfigurationHolder(
						_bundleContext, "test.pid", 10000,
						TimeUnit.MILLISECONDS)) {

				Dictionary<String, Object> properties =
					configurationHolder2.getProperties();

				Assert.assertEquals(
					Http.HTTPS_WITH_SLASH.concat(mainDomain),
					properties.get("baseURL"));
				Assert.assertEquals(
					TestPropsValues.getCompanyId(),
					(long)properties.get("companyId"));
				Assert.assertEquals("test.value", properties.get("test.key"));
			}
		}
	}

	public static class AwaitingConfigurationHolder
		extends ConfigurationHolder {

		public AwaitingConfigurationHolder(
				BundleContext bundleContext, String pid, long timeout,
				TimeUnit timeUnit)
			throws Exception {

			super(
				() -> {
					CountDownLatch countDownLatch = new CountDownLatch(2);

					ServiceRegistration<ManagedService> serviceRegistration =
						bundleContext.registerService(
							ManagedService.class,
							properties -> countDownLatch.countDown(),
							HashMapDictionaryBuilder.<String, Object>put(
								Constants.SERVICE_PID, pid
							).build());

					try {
						countDownLatch.await(timeout, timeUnit);
					}
					finally {
						serviceRegistration.unregister();
					}

					Configuration[] configurations =
						_configurationAdmin.listConfigurations(
							"(service.pid=".concat(
								pid
							).concat(
								")"
							));

					Assert.assertNotNull(configurations);

					return configurations[0];
				});
		}

	}

	public static class ClassLoaderClosableHolder
		extends ClosableHolder<ClassLoader> {

		public ClassLoaderClosableHolder(Class<?> clazz) throws Exception {
			super(
				classLoader -> {
					Thread currentThread = Thread.currentThread();

					currentThread.setContextClassLoader(classLoader);
				},
				() -> {
					Thread currentThread = Thread.currentThread();

					ClassLoader classLoader =
						currentThread.getContextClassLoader();

					currentThread.setContextClassLoader(clazz.getClassLoader());

					return classLoader;
				});
		}

	}

	public static class ConfigurationHolder
		extends ClosableHolder<Configuration> {

		public ConfigurationHolder(
				UnsafeSupplier<Configuration, Exception> onInitUnsafeSupplier)
			throws Exception {

			super(
				configuration -> configuration.delete(), onInitUnsafeSupplier);
		}

		public Dictionary<String, Object> getProperties() throws Exception {
			Configuration configuration = get();

			return configuration.getProcessedProperties(null);
		}

		public void update(Dictionary<String, Object> properties)
			throws Exception {

			Configuration configuration = get();

			configuration.update(properties);
		}

	}

	public static class CreatingConfigurationHolder
		extends ConfigurationHolder {

		public CreatingConfigurationHolder(String pid) throws Exception {
			super(() -> _configurationAdmin.getConfiguration(pid, "?"));
		}

	}

	public static class PortalK8sConfigMapModifierClosableHolder
		extends ClosableHolder
			<ServiceTracker
				<PortalK8sConfigMapModifier, PortalK8sConfigMapModifier>> {

		public PortalK8sConfigMapModifierClosableHolder(
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

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private NamespacedKubernetesClient _kubernetesMockClient;
	private KubernetesMockServer _kubernetesMockServer;

}