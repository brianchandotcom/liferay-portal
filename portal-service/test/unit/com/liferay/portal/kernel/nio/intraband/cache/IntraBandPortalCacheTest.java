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

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.nio.ByteBuffer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCacheTest {

	@Test
	public void testCacheListener() {

		// Satisfy CacheListener

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.registerCacheListener(null);
		intraBandPortalCache.registerCacheListener(null, null);
		intraBandPortalCache.unregisterCacheListener(null);
		intraBandPortalCache.unregisterCacheListeners();
	}

	@Test
	public void testConstructor() throws Exception {
		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		Assert.assertEquals(_testName, intraBandPortalCache.getName());
		Assert.assertSame(
			_mockRegistrationReference,
			getRegistrationReference(intraBandPortalCache));
		Assert.assertSame(_mockIntraBand, getIntraBand(intraBandPortalCache));
	}

	@Test
	public void testDestroy() {
		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.destroy();

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(
			PortalCacheActionType.DESTROY, portalCacheActionType);
		Assert.assertEquals(_testName, deserializer.readString());
	}

	@Test
	public void testGet() {
		final String testKey = "testKey";
		final String testValue = "testValue";

		final AtomicReference<RuntimeException> runtimeExceptionReference =
			new AtomicReference<RuntimeException>();

		MockIntraBand mockIntraBand = new MockIntraBand() {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				RuntimeException runtimeException =
					runtimeExceptionReference.get();

				if (runtimeException != null) {
					throw runtimeException;
				}

				Deserializer deserializer = new Deserializer(
					datagram.getData());

				int actionTypeOrdinal = deserializer.readInt();

				PortalCacheActionType portalCacheActionType =
					PortalCacheActionType.values()[actionTypeOrdinal];

				Assert.assertEquals(
					PortalCacheActionType.GET, portalCacheActionType);

				String cacheName = deserializer.readString();

				Assert.assertEquals(_testName, cacheName);

				try {
					String receivedKey = (String)deserializer.readObject();

					Assert.assertEquals(testKey, receivedKey);
				}
				catch (ClassNotFoundException cnfe) {
					Assert.fail();
				}

				super.doSendDatagram(registrationReference, datagram);

				Serializer serializer = new Serializer();

				serializer.writeObject(testValue);

				ByteBuffer byteBuffer = serializer.toByteBuffer();

				Datagram responseDatagram = Datagram.createResponseDatagram(
					datagram, byteBuffer);

				DatagramHelper.getCompletionHandler(datagram).replied(
					null, responseDatagram);
			}

		};

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraBand);

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, mockRegistrationReference);

		// 1) Normal Get

		String receivedValue = intraBandPortalCache.get(testKey);

		Assert.assertEquals(testValue, receivedValue);

		// 2) Failed Get with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.WARNING);

		RuntimeException runtimeException = new RuntimeException();

		runtimeExceptionReference.set(runtimeException);

		receivedValue = intraBandPortalCache.get(testKey);

		Assert.assertNull(receivedValue);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Get failure, coverting to cache miss", logRecord.getMessage());
		Assert.assertSame(runtimeException, logRecord.getThrown());

		// 3) Failed Get without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.OFF);

		receivedValue = intraBandPortalCache.get(testKey);

		Assert.assertNull(receivedValue);

		Assert.assertTrue(logRecords.isEmpty());
	}

	@Test
	public void testGetBulk() {
		final List<String> testKeys = Arrays.asList(
			"testKey1", "testKey2", "testKey3");

		final List<String> testValues = Arrays.asList(
			"testValue1", "testValue2", "testValue3");

		final AtomicReference<RuntimeException> runtimeExceptionReference =
			new AtomicReference<RuntimeException>();

		MockIntraBand mockIntraBand = new MockIntraBand() {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				RuntimeException runtimeException =
					runtimeExceptionReference.get();

				if (runtimeException != null) {
					throw runtimeException;
				}

				Deserializer deserializer = new Deserializer(
					datagram.getData());

				int actionTypeOrdinal = deserializer.readInt();

				PortalCacheActionType portalCacheActionType =
					PortalCacheActionType.values()[actionTypeOrdinal];

				Assert.assertEquals(
					PortalCacheActionType.GET_BULK, portalCacheActionType);

				String cacheName = deserializer.readString();

				Assert.assertEquals(_testName, cacheName);

				try {
					List<String> receivedKeys =
						(List<String>)deserializer.readObject();

					Assert.assertEquals(testKeys, receivedKeys);
				}
				catch (ClassNotFoundException cnfe) {
					Assert.fail();
				}

				super.doSendDatagram(registrationReference, datagram);

				Serializer serializer = new Serializer();

				serializer.writeObject((Serializable)testValues);

				ByteBuffer byteBuffer = serializer.toByteBuffer();

				Datagram responseDatagram = Datagram.createResponseDatagram(
					datagram, byteBuffer);

				DatagramHelper.getCompletionHandler(datagram).replied(
					null, responseDatagram);
			}

		};

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraBand);

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, mockRegistrationReference);

		// 1) Normal Bulk Get

		List<String> receivedValues = (List<String>)intraBandPortalCache.get(
			testKeys);

		Assert.assertEquals(testValues, receivedValues);

		// 2) Failed Bulk Get with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.WARNING);

		RuntimeException runtimeException = new RuntimeException();

		runtimeExceptionReference.set(runtimeException);

		receivedValues = (List<String>)intraBandPortalCache.get(testKeys);

		Assert.assertEquals(Arrays.asList(null, null, null), receivedValues);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Bulk get failure, coverting to cache miss",
			logRecord.getMessage());
		Assert.assertSame(runtimeException, logRecord.getThrown());

		// 3) Failed Bulk Get without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.OFF);

		receivedValues = (List<String>)intraBandPortalCache.get(testKeys);

		Assert.assertEquals(Arrays.asList(null, null, null), receivedValues);

		Assert.assertTrue(logRecords.isEmpty());
	}

	@Test
	public void testPut() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		Method bridgePutMethod = ReflectionUtil.getBridgeMethod(
			IntraBandPortalCache.class, "put", Serializable.class,
			Object.class);

		bridgePutMethod.invoke(intraBandPortalCache, testKey, testValue);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(PortalCacheActionType.PUT, portalCacheActionType);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
		Assert.assertEquals(testValue, deserializer.readObject());
	}

	@Test
	public void testPutTTL() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";
		int testTTL = 100;

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.put(testKey, testValue, testTTL);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(
			PortalCacheActionType.PUT_TTL, portalCacheActionType);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
		Assert.assertEquals(testValue, deserializer.readObject());
		Assert.assertEquals(testTTL, deserializer.readInt());
	}

	@Test
	public void testRemove() throws Exception {
		String testKey = "testKey";

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.remove(testKey);

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(
			PortalCacheActionType.REMOVE, portalCacheActionType);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
	}

	@Test
	public void testRemoveALL() {
		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.removeAll();

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(datagram.getData());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[actionTypeOrdinal];

		Assert.assertEquals(
			PortalCacheActionType.REMOVE_ALL, portalCacheActionType);
		Assert.assertEquals(_testName, deserializer.readString());
	}

	private static MockIntraBand getIntraBand(
			IntraBandPortalCache<?, ?> intraBandPortalCache)
		throws Exception {

		Field intraBandField = ReflectionUtil.getDeclaredField(
			IntraBandPortalCache.class, "_intraBand");

		return (MockIntraBand)intraBandField.get(intraBandPortalCache);
	}

	private static MockRegistrationReference getRegistrationReference(
			IntraBandPortalCache<?, ?> intraBandPortalCache)
		throws Exception {

		Field registrationReferenceField = ReflectionUtil.getDeclaredField(
			IntraBandPortalCache.class, "_registrationReference");

		return (MockRegistrationReference)registrationReferenceField.get(
			intraBandPortalCache);
	}

	private MockIntraBand _mockIntraBand = new MockIntraBand();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraBand);
	private String _testName = "testName";

}