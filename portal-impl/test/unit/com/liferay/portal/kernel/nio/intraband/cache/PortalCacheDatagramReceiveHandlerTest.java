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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class PortalCacheDatagramReceiveHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(PortalCacheActionType.class);
			}

		};

	@Before
	public void setUp() {
		IntrabandPortalCacheManager.setPortalCacheManager(
			_mockPortalCacheManager);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testClearAll() throws Exception {
		Assert.assertFalse(_mockPortalCacheManager._clearAll);

		Serializer serializer = createSerializer(
			PortalCacheActionType.CLEAR_ALL);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertTrue(_mockPortalCacheManager._clearAll);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testDestroy() throws Exception {
		Assert.assertFalse(_mockPortalCacheManager._destroyed);

		Serializer serializer = createSerializer(PortalCacheActionType.DESTROY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertTrue(_mockPortalCacheManager._destroyed);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGet() throws Exception {
		Serializer serializer = createSerializer(PortalCacheActionType.GET);

		serializer.writeObject(_TEST_KEY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Datagram responseDatagram = _mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		Assert.assertEquals(_TEST_VALUE, deserializer.readObject());
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetPut() throws Exception {
		Serializer serializer = createSerializer(PortalCacheActionType.PUT);

		serializer.writeObject(_TEST_KEY);
		serializer.writeObject(_TEST_VALUE);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_TEST_NAME);

		Assert.assertEquals(_TEST_KEY, mockPortalCache._key);
		Assert.assertEquals(_TEST_VALUE, mockPortalCache._value);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetPutTTL() throws Exception {
		Serializer serializer = createSerializer(PortalCacheActionType.PUT_TTL);

		serializer.writeObject(_TEST_KEY);
		serializer.writeObject(_TEST_VALUE);
		serializer.writeInt(100);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_TEST_NAME);

		Assert.assertEquals(_TEST_KEY, mockPortalCache._key);
		Assert.assertEquals(_TEST_VALUE, mockPortalCache._value);
		Assert.assertEquals(100, mockPortalCache._timeToLive);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetRemove() throws Exception {
		Serializer serializer = createSerializer(PortalCacheActionType.REMOVE);

		serializer.writeObject(_TEST_KEY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_TEST_NAME);

		Assert.assertEquals(_TEST_KEY, mockPortalCache._key);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetRemoveAll() throws Exception {
		Serializer serializer = createSerializer(
			PortalCacheActionType.REMOVE_ALL);

		serializer.writeObject(_TEST_KEY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_TEST_NAME);

		Assert.assertTrue(mockPortalCache._removeAll);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetRemoveCache() throws Exception {
		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_TEST_NAME);

		Map<String, PortalCache<String, String>> portalCaches =
			_mockPortalCacheManager._portalCaches;

		Assert.assertSame(mockPortalCache, portalCaches.get(_TEST_NAME));

		Serializer serializer = createSerializer(
			PortalCacheActionType.REMOVE_CACHE);

		serializer.writeObject(_TEST_NAME);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertFalse(portalCaches.containsKey(_TEST_NAME));
	}

	@AdviseWith(adviceClasses = {
		PortalExecutorManagerUtilAdvice.class, PortalCacheActionTypeAdvice.class
	})
	@Test
	public void testMockValue() throws Exception {
		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		PortalCacheActionType portalCacheActionType =
			portalCacheActionTypes[portalCacheActionTypes.length - 1];

		Serializer serializer = createSerializer(portalCacheActionType);

		serializer.writeObject(_TEST_KEY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		try {
			portalCacheDatagramReceiveHandler.doReceive(
				_mockRegistrationReference,
				Datagram.createRequestDatagram(
					_portalCacheType, serializer.toByteBuffer()));

			Assert.fail();
		}
		catch (PortalCacheException pce) {
			Assert.assertEquals(
				"Unsupported portal cache action type MOCK_VALUE",
				pce.getMessage());
		}

		Method enumSwitchTableMethod = getEnumSwitchTableMethod();

		int[] switchTable = (int[])enumSwitchTableMethod.invoke(null);

		Assert.assertEquals(portalCacheActionTypes.length, switchTable.length);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testReconfigureCaches() throws Exception {
		String className =
			PortalCacheDatagramReceiveHandlerTest.class.getName();

		String resourcePath = className.replace('.', '/');

		resourcePath = resourcePath.concat(".class");

		ClassLoader classLoader =
			PortalCacheDatagramReceiveHandlerTest.class.getClassLoader();

		URL url = classLoader.getResource(resourcePath);

		Serializer serializer = new Serializer();

		serializer.writeInt(PortalCacheActionType.RECONFIGURE.ordinal());
		serializer.writeString(url.toString());

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertEquals(url, _mockPortalCacheManager._configurationURL);

		Method enumSwitchTableMethod = getEnumSwitchTableMethod();

		int[] switchTable = (int[])enumSwitchTableMethod.invoke(null);

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(portalCacheActionTypes.length, switchTable.length);
	}

	@Aspect
	public static class PortalCacheActionTypeAdvice {

		@Around(
			"execution(* com.liferay.portal.kernel.nio.intraband.cache." +
				"PortalCacheActionType.values())")
		public PortalCacheActionType[] values(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			PortalCacheActionType[] portalCacheActionTypes =
				(PortalCacheActionType[])proceedingJoinPoint.proceed();

			PortalCacheActionType[] newPortalCacheActionTypes =
				new PortalCacheActionType[portalCacheActionTypes.length + 1];

			System.arraycopy(
				portalCacheActionTypes, 0, newPortalCacheActionTypes, 0,
				portalCacheActionTypes.length);

			int ordinal = newPortalCacheActionTypes.length - 1;

			newPortalCacheActionTypes[ordinal] = ReflectionUtil.newEnumElement(
				PortalCacheActionType.class, "MOCK_VALUE", ordinal);

			return newPortalCacheActionTypes;
		}

	}

	protected static Method getEnumSwitchTableMethod() throws Exception {
		String className = PortalCacheActionType.class.getName();

		String methodName = "$SWITCH_TABLE$" + className.replace('.', '$');

		return ReflectionUtil.getDeclaredMethod(
			PortalCacheDatagramReceiveHandler.class, methodName);
	}

	protected Serializer createSerializer(
		PortalCacheActionType portalCacheActionType) {

		Serializer serializer = new Serializer();

		serializer.writeInt(portalCacheActionType.ordinal());
		serializer.writeString(_TEST_NAME);

		return serializer;
	}

	private static final String _TEST_KEY = "testKey";

	private static final String _TEST_NAME = "testName";

	private static final String _TEST_VALUE = "testValue";

	private MockIntraband _mockIntraband = new MockIntraband();
	private MockPortalCacheManager _mockPortalCacheManager =
		new MockPortalCacheManager();;
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);
	private SystemDataType _portalCacheSystemDataType =
		SystemDataType.PORTAL_CACHE;
	private byte _portalCacheType = _portalCacheSystemDataType.getValue();

	private class MockPortalCache implements PortalCache<String, String> {

		public MockPortalCache(String name) {
			_name = name;
		}

		@Override
		public String get(String key) {
			if (_TEST_KEY.equals(key)) {
				return _TEST_VALUE;
			}

			return null;
		}

		@Override
		public String getName() {
			return _name;
		}

		@Override
		public void put(String key, String value) {
			_key = key;
			_value = value;
		}

		@Override
		public void put(String key, String value, int timeToLive) {
			_key = key;
			_value = value;
			_timeToLive = timeToLive;
		}

		@Override
		public void registerCacheListener(
			CacheListener<String, String> cacheListener) {
		}

		@Override
		public void registerCacheListener(
			CacheListener<String, String> cacheListener,
			CacheListenerScope cacheListenerScope) {
		}

		@Override
		public void remove(String key) {
			_key = key;
		}

		@Override
		public void removeAll() {
			_removeAll = true;
		}

		@Override
		public void unregisterCacheListener(
			CacheListener<String, String> cacheListener) {
		}

		@Override
		public void unregisterCacheListeners() {
		}

		private String _key;
		private String _name;
		private boolean _removeAll;
		private int _timeToLive;
		private String _value;

	}

	private class MockPortalCacheManager
		implements PortalCacheManager<String, String> {

		@Override
		public void clearAll() {
			_clearAll = true;
		}

		@Override
		public void destroy() {
			_destroyed = true;
		}

		@Override
		public PortalCache<String, String> getCache(String name) {
			return getCache(name, false);
		}

		@Override
		public PortalCache<String, String> getCache(
			String name, boolean blocking) {

			PortalCache<String, String> portalCache = _portalCaches.get(name);

			if (portalCache == null) {
				portalCache = new MockPortalCache(name);

				_portalCaches.put(name, portalCache);
			}

			return portalCache;
		}

		@Override
		public void reconfigureCaches(URL configurationURL) {
			_configurationURL = configurationURL;
		}

		@Override
		public void removeCache(String name) {
			_portalCaches.remove(name);
		}

		private URL _configurationURL;
		private boolean _clearAll;
		private boolean _destroyed;
		private Map<String, PortalCache<String, String>> _portalCaches =
			new ConcurrentHashMap<String, PortalCache<String, String>>();

	}

}