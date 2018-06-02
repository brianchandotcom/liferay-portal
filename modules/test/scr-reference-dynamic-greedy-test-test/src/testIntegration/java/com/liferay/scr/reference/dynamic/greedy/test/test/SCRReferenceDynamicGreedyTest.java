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

package com.liferay.scr.reference.dynamic.greedy.test.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scr.reference.dynamic.greedy.test.ComponentController;
import com.liferay.scr.reference.dynamic.greedy.test.DynamicGreedyComponent;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class SCRReferenceDynamicGreedyTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDynamicGreedyAtLeastOne() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyAtLeastOneComponent",
			"at_least_one",
			bindingCalls -> {
				Assert.assertEquals(
					bindingCalls,
					Arrays.asList(
						"bindAtLeastOneDependency-" + _service1, "step1",
						"bindAtLeastOneDependency-" + _service2, "step2",
						"unbindAtLeastOneDependency-" + _service1, "step3",
						"unbindAtLeastOneDependency-" + _service2));
			});
	}

	@Test
	public void testDynamicGreedyMandatory() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyMandatoryComponent",
			"mandatory",
			bindingCalls -> {
				Assert.assertEquals(
					bindingCalls,
					Arrays.asList(
						"bindMandatoryDependency-" + _service1, "step1",
						"bindMandatoryDependency-" + _service2,
						"unbindMandatoryDependency-" + _service1, "step2",
						"step3", "unbindMandatoryDependency-" + _service2));
			});
	}

	@Test
	public void testDynamicGreedyMultiple() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyMultipleComponent",
			"multiple",
			bindingCalls -> {
				Assert.assertEquals(
					bindingCalls,
					Arrays.asList(
						"bindMultipleDependency-" + _service1, "step1",
						"bindMultipleDependency-" + _service2, "step2",
						"unbindMultipleDependency-" + _service1, "step3",
						"unbindMultipleDependency-" + _service2));
			});
	}

	@Test
	public void testDynamicGreedyOptional() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyOptionalComponent",
			"optional",
			bindingCalls -> {
				Assert.assertEquals(
					bindingCalls,
					Arrays.asList(
						"bindOptionalDependency-" + _service1, "step1",
						"bindOptionalDependency-" + _service2,
						"unbindOptionalDependency-" + _service1, "step2",
						"step3", "unbindOptionalDependency-" + _service2));
			});
	}

	private void _testDynamicGreedyComponent(
			String name, String referenceCardinality,
			Consumer<List<String>> bindingCallsConsumer)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			SCRReferenceDynamicGreedyTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("reference.cardinality", referenceCardinality);

		_componentController.enabledComponent(name);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(Object.class, _service1, properties);

		ServiceTracker<DynamicGreedyComponent, DynamicGreedyComponent>
			serviceTracker = new ServiceTracker<>(
				bundleContext,
				bundleContext.createFilter(
					"(&(objectClass=" + DynamicGreedyComponent.class.getName() +
						")(reference.cardinality=" + referenceCardinality +
							"))"),
				null);

		serviceTracker.open();

		try {
			DynamicGreedyComponent dynamicGreedyComponent =
				serviceTracker.waitForService(0);

			List<String> bindingCalls =
				dynamicGreedyComponent.getBindingCalls();

			bindingCalls.add("step1");

			properties.put("service.ranking", 1);

			ServiceRegistration<?> serviceRegistration2 =
				bundleContext.registerService(
					Object.class, _service2, properties);

			bindingCalls.add("step2");

			serviceRegistration.unregister();

			bindingCalls.add("step3");

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}

			bindingCallsConsumer.accept(bindingCalls);
		}
		finally {
			serviceTracker.close();

			_componentController.disableComponent(name);
		}
	}

	@Inject
	private static ComponentController _componentController;

	private static final Object _service1 = "service 1";
	private static final Object _service2 = "service 2";

}