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

import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerCustomizers;
import com.liferay.registry.collections.ServiceTrackerCustomizers.ServiceWithProperties;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Hashtable;
import java.util.Map;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class ServiceTrackerCustomizersTest {

	@Test
	public void testServiceWithPropertiesCustomizer() {
		ServiceTrackerMap<String, ServiceWithProperties<TrackedOne>>
			serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
				TrackedOne.class, "target",
				ServiceTrackerCustomizers.<TrackedOne>serviceWithProperties());

		serviceTrackerMap.open();

		try {
			Map<String, Object> properties = new Hashtable<>();

			properties.put("property", "aProperty");
			properties.put("target", "aTarget");

			TrackedOne trackedOne = new TrackedOne();

			ServiceRegistration<TrackedOne> serviceRegistration =
				RegistryUtil.getRegistry().registerService(
					TrackedOne.class, trackedOne, properties);

			ServiceWithProperties<TrackedOne> serviceWithProperties =
				serviceTrackerMap.getService("aTarget");

			Assert.assertEquals(trackedOne, serviceWithProperties.getService());

			Map<String, Object> propertiesMap =
				serviceWithProperties.getProperties();

			Assert.assertTrue(propertiesMap.containsKey("property"));
			Assert.assertTrue(propertiesMap.containsKey("target"));

			Assert.assertEquals("aProperty", propertiesMap.get("property"));
			Assert.assertEquals("aTarget", propertiesMap.get("target"));

			serviceRegistration.unregister();
		}
		finally {
			serviceTrackerMap.close();
		}
	}

	private class TrackedOne {
	}

}