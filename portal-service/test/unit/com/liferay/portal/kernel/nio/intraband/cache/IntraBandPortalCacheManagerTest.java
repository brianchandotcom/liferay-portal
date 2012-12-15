/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.cache;

import com.liferay.portal.cache.memory.MemoryPortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCacheManagerTest {

	@Test
	public void testConstructor() throws Exception {
		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		Assert.assertSame(
			_mockRegistrationReference,
			getRegistrationReference(intraBandPortalCacheManager));
		Assert.assertSame(
			_mockIntraBand, getIntraBand(intraBandPortalCacheManager));
	}

	@Test
	public void testGetCache() throws Exception {
		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		String portalCacheName = "portalCacheName";

		// 1) Create on missing

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intraBandPortalCacheManager);

		Assert.assertTrue(portalCaches.isEmpty());

		PortalCache<?, ?> portalCache = intraBandPortalCacheManager.getCache(
			portalCacheName);

		Assert.assertNotNull(portalCache);
		Assert.assertEquals(portalCacheName, portalCache.getName());
		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache, portalCaches.get(portalCacheName));

		// 2) Get exist

		PortalCache<?, ?> portalCache2 = intraBandPortalCacheManager.getCache(
			portalCacheName);

		Assert.assertNotNull(portalCache2);
		Assert.assertEquals(portalCacheName, portalCache2.getName());
		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache, portalCache2);
	}

	@Test
	public void testPortalCacheManagerGetterAndSetter() {
		Assert.assertNull(IntraBandPortalCacheManager.getPortalCacheManager());

		PortalCacheManager<String, String> portalCacheManager =
			new MemoryPortalCacheManager<String, String>();

		IntraBandPortalCacheManager.setPortalCacheManager(portalCacheManager);

		Assert.assertSame(
			portalCacheManager,
			IntraBandPortalCacheManager.getPortalCacheManager());
	}

	@Test
	public void testReconfigureCaches() {
		String resourcePath = getClass().getName().replace('.', '/').concat(
			".class");

		URL testURL = getClass().getClassLoader().getResource(resourcePath);

		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		intraBandPortalCacheManager.reconfigureCaches(testURL);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(
			PortalCacheActionType.RECONFIGURE, portalCacheActionType);
		Assert.assertEquals(
			testURL.toExternalForm(), deserializer.readString());
	}

	@Test
	public void testRemoveAndClear() throws Exception {
		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		String portalCacheName1 = "portalCacheName1";
		String portalCacheName2 = "portalCacheName2";

		PortalCache<?, ?> portalCache1 = intraBandPortalCacheManager.getCache(
			portalCacheName1);
		PortalCache<?, ?> portalCache2 = intraBandPortalCacheManager.getCache(
			portalCacheName2);

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intraBandPortalCacheManager);

		Assert.assertEquals(2, portalCaches.size());
		Assert.assertSame(portalCache1, portalCaches.get(portalCacheName1));
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

		// 1) Remove

		intraBandPortalCacheManager.removeCache(portalCacheName1);

		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

		// 2) Clear

		intraBandPortalCacheManager.clearAll();

		Assert.assertTrue(portalCaches.isEmpty());
	}

	private static MockIntraBand getIntraBand(
			IntraBandPortalCacheManager<?, ?> intraBandPortalCacheManager)
		throws Exception {

		Field intraBandField = ReflectionUtil.getDeclaredField(
			IntraBandPortalCacheManager.class, "_intraBand");

		return (MockIntraBand)intraBandField.get(intraBandPortalCacheManager);
	}

	private static Map<String, PortalCache<?, ?>> getPortalCaches(
			IntraBandPortalCacheManager<?, ?> intraBandPortalCacheManager)
		throws Exception {

		Field intraBandField = ReflectionUtil.getDeclaredField(
			IntraBandPortalCacheManager.class, "_portalCaches");

		return (Map<String, PortalCache<?, ?>>)intraBandField.get(
			intraBandPortalCacheManager);
	}

	private static MockRegistrationReference getRegistrationReference(
			IntraBandPortalCacheManager<?, ?> intraBandPortalCacheManager)
		throws Exception {

		Field registrationReferenceField = ReflectionUtil.getDeclaredField(
			IntraBandPortalCacheManager.class, "_registrationReference");

		return (MockRegistrationReference)registrationReferenceField.get(
			intraBandPortalCacheManager);
	}

	private MockIntraBand _mockIntraBand = new MockIntraBand();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraBand);

}