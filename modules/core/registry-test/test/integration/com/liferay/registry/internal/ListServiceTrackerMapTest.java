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

import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
public class ListServiceTrackerMapTest {

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
	public void testGestServiceWithDifferentRanking() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		TrackedOne TrackedOne1 = new TrackedOne();
		registerDefaultTrackedOne(TrackedOne1, 1);

		TrackedOne TrackedOne3 = new TrackedOne();
		registerDefaultTrackedOne(TrackedOne3, 3);

		TrackedOne TrackedOne2 = new TrackedOne();
		registerDefaultTrackedOne(TrackedOne2, 2);

		List<TrackedOne> services = mapServiceTracker.getService("aTarget");

		Assert.assertEquals(3, services.size());

		Iterator<? extends TrackedOne> iterator = services.iterator();

		Assert.assertEquals(TrackedOne3, iterator.next());
		Assert.assertEquals(TrackedOne2, iterator.next());
		Assert.assertEquals(TrackedOne1, iterator.next());
	}

	@Test
	public void testGestServiceWithUnregistering() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		TrackedOne TrackedOne1 = new TrackedOne();
		registerDefaultTrackedOne(TrackedOne1, 1);

		TrackedOne TrackedOne3 = new TrackedOne();
		registerDefaultTrackedOne(TrackedOne3, 3);

		TrackedOne TrackedOne2 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr2 = registerDefaultTrackedOne(
			TrackedOne2, 2);

		List<TrackedOne> services = mapServiceTracker.getService("aTarget");

		Assert.assertEquals(3, services.size());

		Iterator<? extends TrackedOne> iterator = services.iterator();

		sr2.unregister();

		/* Deregistering a service should not affect an already existing
		collection or iterator */
		Assert.assertEquals(TrackedOne3, iterator.next());
		Assert.assertEquals(TrackedOne2, iterator.next());
		Assert.assertEquals(TrackedOne1, iterator.next());

		services = mapServiceTracker.getService("aTarget");

		// From here on we should see the changes in services

		Assert.assertEquals(2, services.size());

		iterator = services.iterator();

		Assert.assertEquals(TrackedOne3, iterator.next());
		Assert.assertEquals(TrackedOne1, iterator.next());
	}

	@Test
	public void testGetServicesIsNullAfterDeregistration() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		ServiceRegistration<TrackedOne> sr1 = registerDefaultTrackedOne(
			new TrackedOne());
		ServiceRegistration<TrackedOne> sr2 = registerDefaultTrackedOne(
			new TrackedOne());

		Assert.assertNotNull(mapServiceTracker.getService("aTarget"));

		sr1.unregister();
		sr2.unregister();

		Assert.assertNull(mapServiceTracker.getService("aTarget"));
	}

	@Test
	public void testGetServicesWithDifferentKeys() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		registerDefaultTrackedOne(new TrackedOne(), "aTarget");
		registerDefaultTrackedOne(new TrackedOne(), "anotherTarget");
		registerDefaultTrackedOne(new TrackedOne(), "aTarget");
		registerDefaultTrackedOne(new TrackedOne(), "anotherTarget");
		registerDefaultTrackedOne(new TrackedOne(), "anotherTarget");

		List<TrackedOne> aTargetCollection = mapServiceTracker.getService(
			"aTarget");

		Assert.assertNotNull(aTargetCollection);

		List<TrackedOne> anotherTargetCollection = mapServiceTracker.getService(
			"anotherTarget");

		Assert.assertNotNull(anotherTargetCollection);

		Assert.assertEquals(2, aTargetCollection.size());
		Assert.assertEquals(3, anotherTargetCollection.size());
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		registerDefaultTrackedOne(new TrackedOne());

		List<TrackedOne> services = mapServiceTracker.getService("aTarget");

		Assert.assertEquals(1, services.size());
	}

	@Test
	public void testGetServiceWithSimpleRegistrationTwice() {
		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			_createDefaultMapServiceTracker();

		registerDefaultTrackedOne(new TrackedOne());
		registerDefaultTrackedOne(new TrackedOne());

		List<TrackedOne> services = mapServiceTracker.getService("aTarget");

		Assert.assertEquals(2, services.size());
	}

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

	private ServiceTrackerMap<String, List<TrackedOne>>
		_createDefaultMapServiceTracker() {

		ServiceTrackerMap<String, List<TrackedOne>> mapServiceTracker =
			ServiceTrackerMapFactory.createListServiceTracker(
				TrackedOne.class, "target");

		mapServiceTracker.open();

		return mapServiceTracker;
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;

}