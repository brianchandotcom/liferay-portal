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

package com.liferay.portal.k8s.agent.test;

import java.util.Dictionary;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class AnnotationsPortalK8sConfigurationPropertiesMutatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(PortalK8sAgentInitTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testAnnotationsMutator() throws Exception {
		String filterString = StringBundler.concat(
			"(&(objectClass=",
			PortalK8sConfigurationPropertiesMutator.class.getName(),
			")(component.name=",
			"*.AnnotationsPortalK8sConfigurationPropertiesMutator))");

		ServiceTracker<PortalK8sConfigurationPropertiesMutator,
			PortalK8sConfigurationPropertiesMutator> mutatorTracker =
				new ServiceTracker<>(
					_bundleContext, FrameworkUtil.createFilter(filterString),
					null);

		try {
			mutatorTracker.open();

			PortalK8sConfigurationPropertiesMutator mutator =
				mutatorTracker.waitForService(4000);

			HashMap<String,String> annotations = new HashMap<String, String>();

			annotations.put(
				"cloud.liferay.com/context-data",
				StringBundler.concat(
					"{\"domains\": [\"foo\"], \"environment\": \"uat\"}"));

			HashMap<String,String> labels = new HashMap<String, String>();
			Dictionary<String, Object> properties = new HashMapDictionary<>();

			mutator.mutateConfigurationProperties(
				annotations, labels, properties);

			Assert.assertArrayEquals(
				new String[] {"foo"},
				(String[])properties.get("com.liferay.lxc.ext.domains"));

			Assert.assertEquals(
				"uat", (String)properties.get("k8s.lxc.environment"));
		}
		finally {
			mutatorTracker.close();
		}
	}

	private BundleContext _bundleContext;

}
