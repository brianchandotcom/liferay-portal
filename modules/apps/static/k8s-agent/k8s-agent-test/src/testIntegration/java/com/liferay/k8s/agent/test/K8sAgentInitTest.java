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

package com.liferay.k8s.agent.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.k8s.agent.K8sAgent;
import com.liferay.k8s.agent.configuration.v1.K8sAgentConfiguration;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import io.fabric8.kubernetes.client.server.mock.KubernetesMockServer;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;

import org.junit.Assert;
import org.junit.Before;
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
public class K8sAgentInitTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	@Rule
	public static final KubernetesServer server = new KubernetesServer(
		false, true);

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(K8sAgentInitTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testK8sAgentInitialization() throws Exception {
		ServiceTracker<K8sAgent, K8sAgent> k8sAgentTracker =
			new ServiceTracker<>(_bundleContext, K8sAgent.class, null);

		Configuration configuration = null;

		try {
			k8sAgentTracker.open();

			K8sAgent k8sAgent = k8sAgentTracker.waitForService(2000);

			Assert.assertNull(k8sAgent);

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			KubernetesMockServer kubernetesMockServer =
				server.getKubernetesMockServer();

			configuration = _configurationAdmin.getConfiguration(
				K8sAgentConfiguration.class.getName(), "?");

			String caCertData = ContentUtil.get(classLoader, "ca.crt");

			configuration.update(
				HashMapDictionaryBuilder.<String, Object>put(
					"apiServerHost", kubernetesMockServer.getHostName()
				).put(
					"apiServerPort", kubernetesMockServer.getPort()
				).put(
					"apiServerSSL", Boolean.FALSE
				).put(
					"caCertData", caCertData
				).put(
					"namespace", "default"
				).put(
					"saToken", "saToken"
				).build());

			k8sAgent = k8sAgentTracker.waitForService(2000);

			Assert.assertNotNull(k8sAgent);
		}
		finally {
			k8sAgentTracker.close();

			if (configuration != null) {
				configuration.delete();
			}
		}
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private BundleContext _bundleContext;

}