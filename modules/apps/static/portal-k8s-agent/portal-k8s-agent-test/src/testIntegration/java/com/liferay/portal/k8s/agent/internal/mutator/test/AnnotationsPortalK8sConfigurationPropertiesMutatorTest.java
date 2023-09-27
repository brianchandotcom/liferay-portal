/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.mutator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
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
public class AnnotationsPortalK8sConfigurationPropertiesMutatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMutateConfigurationProperties() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			AnnotationsPortalK8sConfigurationPropertiesMutatorTest.class);
		String filterString = StringBundler.concat(
			"(&(component.name=*.",
			"AnnotationsPortalK8sConfigurationPropertiesMutator)(objectClass=",
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

			String[] domains = {"ext.domain.example", "other.domain.example"};
			String mainDomain = RandomTestUtil.randomString();
			Dictionary<String, Object> properties = new HashMapDictionary<>();

			portalK8sConfigurationPropertiesMutator.
				mutateConfigurationProperties(
					HashMapBuilder.put(
						"ext.lxc.liferay.com/domains",
						StringUtil.merge(domains, "\n")
					).put(
						"ext.lxc.liferay.com/mainDomain", mainDomain
					).build(),
					new HashMap<>(), properties);

			Assert.assertEquals(
				mainDomain,
				(String)properties.get("ext.lxc.liferay.com.mainDomain"));
			Assert.assertArrayEquals(
				new String[] {"ext.domain.example", "other.domain.example"},
				(String[])properties.get("ext.lxc.liferay.com.domains"));
		}
		finally {
			serviceTracker.close();
		}
	}

}