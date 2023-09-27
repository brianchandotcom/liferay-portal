/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.mutator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
@Ignore
@RunWith(Arquillian.class)
public class LabelsPortalK8sConfigurationPropertiesMutatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMutateConfigurationProperties() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			LabelsPortalK8sConfigurationPropertiesMutatorTest.class);
		String filterString = StringBundler.concat(
			"(&(component.name=*.LabelsPortalK8sConfigurationPropertiesMutator",
			")(objectClass=",
			PortalK8sConfigurationPropertiesMutator.class.getName(), "))");

		ServiceTracker
			<PortalK8sConfigurationPropertiesMutator,
			 PortalK8sConfigurationPropertiesMutator> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					FrameworkUtil.createFilter(filterString), null);

		try {
			serviceTracker.open();

			PortalK8sConfigurationPropertiesMutator
				portalK8sConfigurationPropertiesMutator =
					serviceTracker.waitForService(4000);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			portalK8sConfigurationPropertiesMutator.
				mutateConfigurationProperties(
					new HashMap<>(),
					HashMapBuilder.put(
						"ext.lxc.liferay.com/serviceId", "customrestservice"
					).put(
						"lxc.liferay.com/metadataType", "ext-provision"
					).build(),
					properties);

			Assert.assertEquals(
				"customrestservice",
				(String)properties.get("ext.lxc.liferay.com.serviceId"));
			Assert.assertEquals(
				"ext-provision",
				(String)properties.get("lxc.liferay.com.metadataType"));
		}
		finally {
			serviceTracker.close();
		}
	}

}