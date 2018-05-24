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

import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

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
			dynamicGreedyOptionalComponent -> {
				List<String> bindingCalls =
					dynamicGreedyOptionalComponent.getBindingCalls();

				Assert.assertEquals(
					bindingCalls.toString(), 2, bindingCalls.size());
				Assert.assertEquals(
					"bindAtLeastOneDependency-" + _service1,
					bindingCalls.get(0));
				Assert.assertEquals(
					"bindAtLeastOneDependency-" + _service2,
					bindingCalls.get(1));
			});
	}

	@Test
	public void testDynamicGreedyMandatory() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyMandatoryComponent",
			"mandatory",
			dynamicGreedyOptionalComponent -> {
				List<String> bindingCalls =
					dynamicGreedyOptionalComponent.getBindingCalls();

				Assert.assertEquals(
					bindingCalls.toString(), 3, bindingCalls.size());
				Assert.assertEquals(
					"bindMandatoryDependency-" + _service1,
					bindingCalls.get(0));
				Assert.assertEquals(
					"bindMandatoryDependency-" + _service2,
					bindingCalls.get(1));
				Assert.assertEquals(
					"unbindMandatoryDependency-" + _service1,
					bindingCalls.get(2));
			});
	}

	@Test
	public void testDynamicGreedyMultiple() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyMultipleComponent",
			"multiple",
			dynamicGreedyOptionalComponent -> {
				List<String> bindingCalls =
					dynamicGreedyOptionalComponent.getBindingCalls();

				Assert.assertEquals(
					bindingCalls.toString(), 2, bindingCalls.size());
				Assert.assertEquals(
					"bindMultipleDependency-" + _service1, bindingCalls.get(0));
				Assert.assertEquals(
					"bindMultipleDependency-" + _service2, bindingCalls.get(1));
			});
	}

	@Test
	public void testDynamicGreedyOptional() throws Exception {
		_testDynamicGreedyComponent(
			"com.liferay.scr.reference.dynamic.greedy.test.internal." +
				"DynamicGreedyOptionalComponent",
			"optional",
			dynamicGreedyOptionalComponent -> {
				List<String> bindingCalls =
					dynamicGreedyOptionalComponent.getBindingCalls();

				Assert.assertEquals(
					bindingCalls.toString(), 3, bindingCalls.size());
				Assert.assertEquals(
					"bindOptionalDependency-" + _service1, bindingCalls.get(0));
				Assert.assertEquals(
					"bindOptionalDependency-" + _service2, bindingCalls.get(1));
				Assert.assertEquals(
					"unbindOptionalDependency-" + _service1,
					bindingCalls.get(2));
			});
	}

	private void _testDynamicGreedyComponent(
			String name, String referenceCardinality,
			Consumer<DynamicGreedyComponent> componentAssertion)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(DynamicGreedyComponent.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("reference.cardinality", referenceCardinality);

		_componentController.enabledComponent(name);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(Object.class, _service1, properties);

		ServiceRegistration<?> serviceRegistration2 = null;

		try {
			Collection<ServiceReference<DynamicGreedyComponent>>
				serviceReferences = bundleContext.getServiceReferences(
					DynamicGreedyComponent.class,
					"(reference.cardinality=" + referenceCardinality + ")");

			Stream<ServiceReference<DynamicGreedyComponent>> stream =
				serviceReferences.stream();

			Optional<ServiceReference<DynamicGreedyComponent>> optional =
				stream.findFirst();

			ServiceReference<DynamicGreedyComponent> serviceReference =
				optional.orElse(null);

			Assert.assertNotNull(
				serviceReferences.toString(), serviceReference);

			DynamicGreedyComponent dynamicGreedyComponent =
				bundleContext.getService(serviceReference);

			properties.put("service.ranking", 1);

			serviceRegistration2 = bundleContext.registerService(
				Object.class, _service2, properties);

			componentAssertion.accept(dynamicGreedyComponent);
		}
		finally {
			serviceRegistration.unregister();

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}

			_componentController.disableComponent(name);
		}
	}

	@Inject
	private static ComponentController _componentController;

	private static final Object _service1 = "service 1";
	private static final Object _service2 = "service 2";

}