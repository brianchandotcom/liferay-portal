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

package com.liferay.registry;

import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ServiceRegistrarTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testRegisterService() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistrar<Foo> serviceRegistrar = registry.getServiceRegistrar(
			Foo.class);

		ServiceRegistration<Foo> serviceRegistration1 = null;
		ServiceRegistration<Foo> serviceRegistration2 = null;

		try {
			serviceRegistration1 = serviceRegistrar.registerService(
				Foo.class, new Foo());
			serviceRegistration2 = serviceRegistrar.registerService(
				Foo.class, new Foo());

			Collection<ServiceRegistration<Foo>> serviceRegistrations =
				serviceRegistrar.getServiceRegistrations();

			Assert.assertEquals(
				serviceRegistrations.toString(), 2,
				serviceRegistrations.size());

			Collection<Foo> services = registry.getServices(Foo.class, null);

			Assert.assertEquals(services.toString(), 2, services.size());

			serviceRegistrar.destroy();

			services = registry.getServices(Foo.class, null);

			Assert.assertTrue(services.isEmpty());
		}
		finally {
			if (serviceRegistration1 != null) {
				serviceRegistration1.unregister();
			}

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}
		}
	}

	private static class Foo {
	}

}