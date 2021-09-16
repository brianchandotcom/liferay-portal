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

package com.liferay.registry.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.internal.InterfaceBoth;
import com.liferay.registry.internal.InterfaceOne;
import com.liferay.registry.internal.InterfaceTwo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Raymond Augé
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class RegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(RegistryTest.class);

		_bundleContext = bundle.getBundleContext();

		_registry = RegistryUtil.getRegistry();
	}

	@Test
	public void testGetBundleContext() {
		Assert.assertNotNull(_bundleContext);
	}

	@Test
	public void testGetRegistry() {
		Assert.assertNotNull(_registry);
	}

	@Test
	public void testGetServiceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			_registry.getServiceReference(InterfaceOne.class));

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassName() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			_registry.getServiceReference(InterfaceOne.class.getName()));

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassNameAndFilterString() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		InterfaceOne[] interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(interfaceOnes), 1, interfaceOnes.length);

		serviceRegistrationA.unregister();

		interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(interfaceOnes), 1, interfaceOnes.length);

		serviceRegistrationB.unregister();

		interfaceOnes = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(interfaceOnes);
	}

	@Test
	public void testGetServiceReferenceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferenceByClassName() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferencesByClass() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, interfaceOneB);

		Assert.assertNotNull(serviceRegistrationB);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 2, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(
			serviceReferences.toString(), 0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassAndFilterString()
		throws Exception {

		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(
			serviceReferences.toString(), 0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassName() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, interfaceOneB);

		Assert.assertNotNull(serviceRegistrationB);

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(
			Arrays.toString(serviceReferences), 2, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServiceReferencesByClassNameAndFilterString()
		throws Exception {

		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(
				InterfaceOne.class.getName(), filterString);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(
			Arrays.toString(serviceReferences), 1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServicesByClassAndFilterString() throws Exception {
		InterfaceOne interfaceOneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, interfaceOneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne interfaceOneB = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(
				InterfaceOne.class, interfaceOneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<InterfaceOne> interfaceOnes = _registry.getServices(
			InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 1, interfaceOnes.size());

		serviceRegistrationA.unregister();

		interfaceOnes = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 1, interfaceOnes.size());

		serviceRegistrationB.unregister();

		interfaceOnes = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(interfaceOnes.toString(), 0, interfaceOnes.size());
	}

	@Test
	public void testRegisterServiceByClass() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassAndProperties() {
		InterfaceOne interfaceOne = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "A");
		properties.put("b.property", "B");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class, interfaceOne, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("A", serviceReference.getProperty("a.property"));
		Assert.assertEquals("B", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassNames() {
		InterfaceBoth interfaceOne = new InterfaceBoth() {
		};

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);
		Assert.assertTrue(registeredInterfaceOne instanceof InterfaceOne);
		Assert.assertTrue(registeredInterfaceOne instanceof InterfaceTwo);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByClassNamesAndProperties() {
		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "E");
		properties.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			new InterfaceBoth() {
			},
			properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("E", serviceReference.getProperty("a.property"));
		Assert.assertEquals("F", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		Object registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByFilterString() {
		InterfaceOne interfaceOne = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class.getName(), interfaceOne);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertEquals(interfaceOne, registeredInterfaceOne);

		serviceRegistration.unregister();

		registeredInterfaceOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	@Test
	public void testRegisterServiceByFilterStringAndProperties() {
		InterfaceOne interfaceOne = getInstance();

		Map<String, Object> properties = new HashMap<>();

		properties.put("a.property", "C");
		properties.put("b.property", "D");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class.getName(), interfaceOne, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals("C", serviceReference.getProperty("a.property"));
		Assert.assertEquals("D", serviceReference.getProperty("b.property"));
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredInterfaceOne = _registry.getService(
			serviceReference);

		Assert.assertNull(registeredInterfaceOne);
	}

	protected InterfaceOne getInstance() {
		return new InterfaceOne() {
		};
	}

	private BundleContext _bundleContext;
	private Registry _registry;

}