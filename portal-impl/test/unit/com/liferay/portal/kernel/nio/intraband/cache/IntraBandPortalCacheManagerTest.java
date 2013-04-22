/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCacheManagerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(PortalCacheActionType.class);
			}

		};

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

		// Create on missing

		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intraBandPortalCacheManager);

		Assert.assertTrue(portalCaches.isEmpty());

		String portalCacheName = "portalCacheName";

		PortalCache<?, ?> portalCache = intraBandPortalCacheManager.getCache(
			portalCacheName);

		Assert.assertNotNull(portalCache);
		Assert.assertEquals(portalCacheName, portalCache.getName());
		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache, portalCaches.get(portalCacheName));

		// Get existing

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
		String className = IntraBandPortalCacheManagerTest.class.getName();
		String resourcePath = className.replace('.', '/');

		resourcePath = resourcePath.concat(".class");

		ClassLoader classLoader =
			IntraBandPortalCacheManagerTest.class.getClassLoader();

		URL url = classLoader.getResource(resourcePath);

		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		intraBandPortalCacheManager.reconfigureCaches(url);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.RECONFIGURE,
			portalCacheActionTypes[actionTypeOrdinal]);
		Assert.assertEquals(url.toExternalForm(), deserializer.readString());
	}

	@Test
	public void testRemoveAndClear() throws Exception {
		IntraBandPortalCacheManager<String, String>
			intraBandPortalCacheManager =
				new IntraBandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		String portalCacheName1 = "portalCacheName1";

		PortalCache<?, ?> portalCache1 = intraBandPortalCacheManager.getCache(
			portalCacheName1);

		String portalCacheName2 = "portalCacheName2";

		PortalCache<?, ?> portalCache2 = intraBandPortalCacheManager.getCache(
			portalCacheName2);

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intraBandPortalCacheManager);

		Assert.assertEquals(2, portalCaches.size());
		Assert.assertSame(portalCache1, portalCaches.get(portalCacheName1));
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

		intraBandPortalCacheManager.removeCache(portalCacheName1);

		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

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