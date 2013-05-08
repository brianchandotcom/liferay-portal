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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
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
public class IntrabandPortalCacheManagerTest {

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
		IntrabandPortalCacheManager<String, String>
			intrabandPortalCacheManager =
				new IntrabandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		Assert.assertSame(
			_mockRegistrationReference,
			getRegistrationReference(intrabandPortalCacheManager));
		Assert.assertSame(
			_mockIntraband, getIntraband(intrabandPortalCacheManager));
	}

	@Test
	public void testGetCache() throws Exception {

		// Create on missing

		IntrabandPortalCacheManager<String, String>
			intrabandPortalCacheManager =
				new IntrabandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intrabandPortalCacheManager);

		Assert.assertTrue(portalCaches.isEmpty());

		String portalCacheName = "portalCacheName";

		PortalCache<?, ?> portalCache = intrabandPortalCacheManager.getCache(
			portalCacheName);

		Assert.assertNotNull(portalCache);
		Assert.assertEquals(portalCacheName, portalCache.getName());
		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache, portalCaches.get(portalCacheName));

		// Get existing

		PortalCache<?, ?> portalCache2 = intrabandPortalCacheManager.getCache(
			portalCacheName);

		Assert.assertNotNull(portalCache2);
		Assert.assertEquals(portalCacheName, portalCache2.getName());
		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache, portalCache2);
	}

	@Test
	public void testPortalCacheManagerGetterAndSetter() {
		Assert.assertNull(IntrabandPortalCacheManager.getPortalCacheManager());

		PortalCacheManager<String, String> portalCacheManager =
			new MockPortalCacheManager();

		IntrabandPortalCacheManager.setPortalCacheManager(portalCacheManager);

		Assert.assertSame(
			portalCacheManager,
			IntrabandPortalCacheManager.getPortalCacheManager());
	}

	@Test
	public void testReconfigureCaches() {
		String className = IntrabandPortalCacheManagerTest.class.getName();
		String resourcePath = className.replace('.', '/');

		resourcePath = resourcePath.concat(".class");

		ClassLoader classLoader =
			IntrabandPortalCacheManagerTest.class.getClassLoader();

		URL url = classLoader.getResource(resourcePath);

		IntrabandPortalCacheManager<String, String>
			intrabandPortalCacheManager =
				new IntrabandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		intrabandPortalCacheManager.reconfigureCaches(url);

		Datagram datagram = _mockIntraband.getDatagram();

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
		IntrabandPortalCacheManager<String, String>
			intrabandPortalCacheManager =
				new IntrabandPortalCacheManager<String, String>(
					_mockRegistrationReference);

		String portalCacheName1 = "portalCacheName1";

		PortalCache<?, ?> portalCache1 = intrabandPortalCacheManager.getCache(
			portalCacheName1);

		String portalCacheName2 = "portalCacheName2";

		PortalCache<?, ?> portalCache2 = intrabandPortalCacheManager.getCache(
			portalCacheName2);

		Map<String, PortalCache<?, ?>> portalCaches = getPortalCaches(
			intrabandPortalCacheManager);

		Assert.assertEquals(2, portalCaches.size());
		Assert.assertSame(portalCache1, portalCaches.get(portalCacheName1));
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

		intrabandPortalCacheManager.removeCache(portalCacheName1);

		Assert.assertEquals(1, portalCaches.size());
		Assert.assertSame(portalCache2, portalCaches.get(portalCacheName2));

		intrabandPortalCacheManager.clearAll();

		Assert.assertTrue(portalCaches.isEmpty());
	}

	private static MockIntraband getIntraband(
			IntrabandPortalCacheManager<?, ?> intrabandPortalCacheManager)
		throws Exception {

		Field intrabandField = ReflectionUtil.getDeclaredField(
			IntrabandPortalCacheManager.class, "_intraband");

		return (MockIntraband)intrabandField.get(intrabandPortalCacheManager);
	}

	private static Map<String, PortalCache<?, ?>> getPortalCaches(
			IntrabandPortalCacheManager<?, ?> intrabandPortalCacheManager)
		throws Exception {

		Field intrabandField = ReflectionUtil.getDeclaredField(
			IntrabandPortalCacheManager.class, "_portalCaches");

		return (Map<String, PortalCache<?, ?>>)intrabandField.get(
			intrabandPortalCacheManager);
	}

	private static MockRegistrationReference getRegistrationReference(
			IntrabandPortalCacheManager<?, ?> intrabandPortalCacheManager)
		throws Exception {

		Field registrationReferenceField = ReflectionUtil.getDeclaredField(
			IntrabandPortalCacheManager.class, "_registrationReference");

		return (MockRegistrationReference)registrationReferenceField.get(
			intrabandPortalCacheManager);
	}

	private MockIntraband _mockIntraband = new MockIntraband();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);

	private static class MockPortalCacheManager
		implements PortalCacheManager<String, String> {

		@Override
		public void clearAll() {
		}

		@Override
		public PortalCache<String, String> getCache(String name) {
			return null;
		}

		@Override
		public PortalCache<String, String> getCache(
			String name, boolean blocking) {

			return null;
		}

		@Override
		public void reconfigureCaches(URL configurationURL) {
		}

		@Override
		public void removeCache(String name) {
		}

	}

}