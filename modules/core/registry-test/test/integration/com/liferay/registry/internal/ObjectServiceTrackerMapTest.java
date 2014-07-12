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

package com.liferay.registry.internal;

import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class ObjectServiceTrackerMapTest {

	@Before
	public void setup() throws BundleException, InvalidSyntaxException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();
	}

	@Test
	public void testGetServiceAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = _createDefaultMapServiceTracker();

		ServiceRegistration<TrackedOne> sr = registerDefaultTrackedOne(
			new TrackedOne());

		Assert.assertNotNull(ServiceTrackerMap.getService("aTarget"));

		sr.unregister();

		Assert.assertNull(ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = _createDefaultMapServiceTracker();

		TrackedOne TrackedOne1 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerDefaultTrackedOne(
			TrackedOne1, 2);

		TrackedOne TrackedOne2 = new TrackedOne();

		registerDefaultTrackedOne(TrackedOne2, 1);

		Assert.assertEquals(
			TrackedOne1, ServiceTrackerMap.getService("aTarget"));

		sr1.unregister();

		Assert.assertEquals(
			TrackedOne2, ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemovalInverseOrder()
		throws InterruptedException {

		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = _createDefaultMapServiceTracker();

		TrackedOne TrackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr2 = registerDefaultTrackedOne(
			TrackedOne2, 1);

		TrackedOne TrackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr1 = registerDefaultTrackedOne(
			TrackedOne1, 2);

		Assert.assertEquals(
			TrackedOne1, ServiceTrackerMap.getService("aTarget"));

		sr1.unregister();

		Assert.assertEquals(
			TrackedOne2, ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceIsNullAfterDeregistration() {
		ServiceTrackerMap<String, TrackedOne> mapServiceTracker =
			_createDefaultMapServiceTracker();

		ServiceRegistration<TrackedOne> sr1 = registerDefaultTrackedOne(
			new TrackedOne());
		ServiceRegistration<TrackedOne> sr2 = registerDefaultTrackedOne(
			new TrackedOne());
		ServiceRegistration<TrackedOne> sr3 = registerDefaultTrackedOne(
			new TrackedOne());

		Assert.assertNotNull(mapServiceTracker.getService("aTarget"));

		sr1.unregister();
		sr2.unregister();
		sr3.unregister();

		Assert.assertNull(mapServiceTracker.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithCustomComparator() {
		ServiceTrackerMap<String, TrackedOne> mapServiceTracker =
			ServiceTrackerMapFactory.createObjectServiceTrackerMap(
				TrackedOne.class, "(target=*)",
				new ServiceTrackerMapFactory.PropertyServiceReferenceMapper(
					"target"),
				new Comparator<ServiceReference<TrackedOne>>() {
					@Override
					public int compare(
						ServiceReference<TrackedOne> o1,
						ServiceReference<TrackedOne> o2) {

						return -1;
					}
				}
		);

		mapServiceTracker.open();

		TrackedOne TrackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr1 = registerDefaultTrackedOne(
			TrackedOne1);

		TrackedOne TrackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr2 = registerDefaultTrackedOne(
			TrackedOne2);

		Assert.assertEquals(
			TrackedOne2, mapServiceTracker.getService("aTarget"));

		sr1.unregister();
		sr2.unregister();

		registerDefaultTrackedOne(TrackedOne2);

		registerDefaultTrackedOne(TrackedOne1);

		Assert.assertEquals(
			TrackedOne1, mapServiceTracker.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithCustomResolver() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap =

			ServiceTrackerMapFactory.createObjectServiceTrackerMap(
				TrackedOne.class, "(&(target=*)(other=*))",
				new ServiceReferenceMapper<String>() {

					@Override
					public void map(
						ServiceReference<?> serviceReference,
						Emitter<String> keys) {

						keys.emit(
							serviceReference.getProperty("target") + " - " +
								serviceReference.getProperty("other")
						);
					}
				}
			);

		ServiceTrackerMap.open();

		Dictionary<String, String> properties = new Hashtable<String, String>();

		properties.put("target", "aTarget");
		properties.put("other", "aProperty");

		_bundleContext.registerService(
			TrackedOne.class, new TrackedOne(), properties);

		Assert.assertNotNull(
			ServiceTrackerMap.getService("aTarget - aProperty"));
	}

	@Test
	public void testGetServiceWithIncorrectKey() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = _createDefaultMapServiceTracker();

		registerDefaultTrackedOne(new TrackedOne(), "anotherTarget");

		Assert.assertNull(ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = _createDefaultMapServiceTracker();

		registerDefaultTrackedOne(new TrackedOne());

		Assert.assertNotNull(ServiceTrackerMap.getService("aTarget"));
	}

	@ArquillianResource
	public Bundle _bundle;

	protected ServiceRegistration<TrackedOne> registerDefaultTrackedOne(
		TrackedOne TrackedOne) {

		return registerDefaultTrackedOne(TrackedOne, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerDefaultTrackedOne(
		TrackedOne TrackedOne, int ranking) {

		return registerDefaultTrackedOne(TrackedOne, "aTarget", ranking);
	}

	protected ServiceRegistration<TrackedOne> registerDefaultTrackedOne(
		TrackedOne TrackedOne, String target) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, TrackedOne, properties);
	}

	protected ServiceRegistration<TrackedOne> registerDefaultTrackedOne(
		TrackedOne TrackedOne, String target, int ranking) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("target", target);
		properties.put("service.ranking", ranking);

		return _bundleContext.registerService(
			TrackedOne.class, TrackedOne, properties);
	}

	private ServiceTrackerMap<String, TrackedOne>
		_createDefaultMapServiceTracker() {

		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap =
				ServiceTrackerMapFactory.createObjectServiceTrackerMap(
					TrackedOne.class, "target");

		ServiceTrackerMap.open();

		return ServiceTrackerMap;
	}

	private BundleContext _bundleContext;

}