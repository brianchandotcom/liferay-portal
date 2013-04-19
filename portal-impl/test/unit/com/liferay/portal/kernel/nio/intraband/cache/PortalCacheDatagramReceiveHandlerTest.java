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
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.Arrays;
import java.util.Collection;
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
		IntraBandPortalCacheManager.setPortalCacheManager(
			_mockPortalCacheManager);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testDestroy() throws Exception {
		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_testName);

		Assert.assertFalse(mockPortalCache._destroyed);

		Serializer serializer = _createSerializer(
			PortalCacheActionType.DESTROY);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertTrue(mockPortalCache._destroyed);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGet() throws Exception {
		Serializer serializer = _createSerializer(PortalCacheActionType.GET);

		serializer.writeObject(_testKey);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Datagram responseDatagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		Assert.assertEquals(_testValue, deserializer.readObject());
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetBulk() throws Exception {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.GET_BULK);

		serializer.writeObject((Serializable)_testKeys);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Datagram responseDatagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		Assert.assertEquals(_testValues, deserializer.readObject());
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetPut() throws Exception {
		Serializer serializer = _createSerializer(PortalCacheActionType.PUT);

		serializer.writeObject(_testKey);
		serializer.writeObject(_testValue);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_testName);

		Assert.assertEquals(_testKey, mockPortalCache._key);
		Assert.assertEquals(_testValue, mockPortalCache._value);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetPutTTL() throws Exception {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.PUT_TTL);

		serializer.writeObject(_testKey);
		serializer.writeObject(_testValue);
		serializer.writeInt(100);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_testName);

		Assert.assertEquals(_testKey, mockPortalCache._key);
		Assert.assertEquals(_testValue, mockPortalCache._value);
		Assert.assertEquals(100, mockPortalCache._timeToLive);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetRemove() throws Exception {
		Serializer serializer = _createSerializer(PortalCacheActionType.REMOVE);

		serializer.writeObject(_testKey);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_testName);

		Assert.assertEquals(_testKey, mockPortalCache._key);
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testGetRemoveAll() throws Exception {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.REMOVE_ALL);

		serializer.writeObject(_testKey);

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		MockPortalCache mockPortalCache =
			(MockPortalCache)_mockPortalCacheManager.getCache(_testName);

		Assert.assertTrue(mockPortalCache._removeAll);
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

		Serializer serializer = _createSerializer(portalCacheActionType);

		serializer.writeObject(_testKey);

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

		Method enumSwitchTableMethod = _getEnumSwitchTableMethod();

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

		URL testURL = classLoader.getResource(resourcePath);

		Serializer serializer = new Serializer();

		serializer.writeInt(PortalCacheActionType.RECONFIGURE.ordinal());
		serializer.writeString(testURL.toString());

		PortalCacheDatagramReceiveHandler portalCacheDatagramReceiveHandler =
			new PortalCacheDatagramReceiveHandler();

		portalCacheDatagramReceiveHandler.doReceive(
			_mockRegistrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));

		Assert.assertEquals(testURL, _mockPortalCacheManager._configurationURL);

		Method enumSwitchTableMethod = _getEnumSwitchTableMethod();

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

	private static Method _getEnumSwitchTableMethod() throws Exception {
		String enumClassName = PortalCacheActionType.class.getName();

		String methodName = "$SWITCH_TABLE$" + enumClassName.replace('.', '$');

		return ReflectionUtil.getDeclaredMethod(
			PortalCacheDatagramReceiveHandler.class, methodName);
	}

	private Serializer _createSerializer(
		PortalCacheActionType portalCacheActionType) {

		Serializer serializer = new Serializer();

		serializer.writeInt(portalCacheActionType.ordinal());
		serializer.writeString(_testName);

		return serializer;
	}

	private MockIntraBand _mockIntraBand = new MockIntraBand();
	private MockPortalCacheManager _mockPortalCacheManager =
		new MockPortalCacheManager();;
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraBand);
	private SystemDataType _portalCacheSystemDataType =
		SystemDataType.PORTAL_CACHE;
	private byte _portalCacheType = _portalCacheSystemDataType.getValue();
	private String _testKey = "testKey";
	private List<String> _testKeys = Arrays.asList(
			"testKey1", "testKey2", "testKey3");
	private String _testName = "testName";
	private String _testValue = "testValue";
	private List<String> _testValues = Arrays.asList(
			"testValue1", "testValue2", "testValue3");

	private class MockPortalCache implements PortalCache<String, String> {

		public MockPortalCache(String name) {
			_name = name;
		}

		public void destroy() {
			_destroyed = true;
		}

		public Collection<String> get(Collection<String> keys) {
			if (_testKeys.equals(keys)) {
				return _testValues;
			}

			return null;
		}

		public String get(String key) {
			if (_testKey.equals(key)) {
				return _testValue;
			}

			return null;
		}

		public String getName() {
			return _name;
		}

		public void put(String key, String value) {
			_key = key;
			_value = value;
		}

		public void put(String key, String value, int timeToLive) {
			_key = key;
			_value = value;
			_timeToLive = timeToLive;
		}

		public void registerCacheListener(
			CacheListener<String, String> cacheListener) {
		}

		public void registerCacheListener(
			CacheListener<String, String> cacheListener,
			CacheListenerScope cacheListenerScope) {
		}

		public void remove(String key) {
			_key = key;
		}

		public void removeAll() {
			_removeAll = true;
		}

		public void unregisterCacheListener(
			CacheListener<String, String> cacheListener) {
		}

		public void unregisterCacheListeners() {
		}

		private boolean _destroyed;
		private String _key;
		private String _name;
		private boolean _removeAll;
		private int _timeToLive;
		private String _value;

	}

	private class MockPortalCacheManager
		implements PortalCacheManager<String, String> {

		public void clearAll() {
		}

		public PortalCache<String, String> getCache(String name) {
			return getCache(name, false);
		}

		public PortalCache<String, String> getCache(
			String name, boolean blocking) {

			PortalCache<String, String> portalCache = _portalCaches.get(name);

			if (portalCache == null) {
				portalCache = new MockPortalCache(name);

				_portalCaches.put(name, portalCache);
			}

			return portalCache;
		}

		public void reconfigureCaches(URL configurationURL) {
			_configurationURL = configurationURL;
		}

		public void removeCache(String name) {
		}

		private URL _configurationURL;
		private Map<String, PortalCache<String, String>> _portalCaches =
			new ConcurrentHashMap<String, PortalCache<String, String>>();

	}

}