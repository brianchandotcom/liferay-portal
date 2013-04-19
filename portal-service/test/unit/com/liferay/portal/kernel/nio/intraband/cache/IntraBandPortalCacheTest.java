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

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(PortalCacheActionType.class);
			}

		};

	@Test
	public void testCacheListener() {
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

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

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

				PortalCacheActionType[] portalCacheActionTypes =
					PortalCacheActionType.values();

				Deserializer deserializer = new Deserializer(
					datagram.getDataByteBuffer());

				Assert.assertEquals(
					PortalCacheActionType.GET,
					portalCacheActionTypes[deserializer.readInt()]);
				Assert.assertEquals(_testName, deserializer.readString());

				try {
					Assert.assertEquals(
						testKey, (String)deserializer.readObject());
				}
				catch (ClassNotFoundException cnfe) {
					Assert.fail();
				}

				super.doSendDatagram(registrationReference, datagram);

				Serializer serializer = new Serializer();

				serializer.writeObject(testValue);

				DatagramHelper.getCompletionHandler(datagram).replied(
					null,
					Datagram.createResponseDatagram(
						datagram, serializer.toByteBuffer()));
			}

		};

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, new MockRegistrationReference(mockIntraBand));

		// Normal get

		Assert.assertEquals(testValue, intraBandPortalCache.get(testKey));

		// Unable to get, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.WARNING);

		RuntimeException runtimeException = new RuntimeException();

		runtimeExceptionReference.set(runtimeException);

		Assert.assertNull(intraBandPortalCache.get(testKey));
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to get, coverting to cache miss", logRecord.getMessage());
		Assert.assertSame(runtimeException, logRecord.getThrown());

		// Unable to get, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.OFF);

		Assert.assertNull(intraBandPortalCache.get(testKey));
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
					datagram.getDataByteBuffer());

				int actionTypeOrdinal = deserializer.readInt();

				PortalCacheActionType[] portalCacheActionTypes =
					PortalCacheActionType.values();

				Assert.assertEquals(
					PortalCacheActionType.GET_BULK,
					portalCacheActionTypes[actionTypeOrdinal]);
				Assert.assertEquals(_testName, deserializer.readString());

				try {
					Assert.assertEquals(
						testKeys, (List<String>)deserializer.readObject());
				}
				catch (ClassNotFoundException cnfe) {
					Assert.fail();
				}

				super.doSendDatagram(registrationReference, datagram);

				Serializer serializer = new Serializer();

				serializer.writeObject((Serializable)testValues);

				DatagramHelper.getCompletionHandler(datagram).replied(
					null,
					Datagram.createResponseDatagram(
						datagram, serializer.toByteBuffer()));
			}

		};

		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, new MockRegistrationReference(mockIntraBand));

		// Normal bulk get

		Assert.assertEquals(testValues, intraBandPortalCache.get(testKeys));

		// Unable to bulk get, with log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.WARNING);

		RuntimeException runtimeException = new RuntimeException();

		runtimeExceptionReference.set(runtimeException);

		Assert.assertEquals(
			Arrays.asList(null, null, null),
			intraBandPortalCache.get(testKeys));
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to bulk get, coverting to cache miss",
			logRecord.getMessage());
		Assert.assertSame(runtimeException, logRecord.getThrown());

		// Unable to bulk get, without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			IntraBandPortalCache.class.getName(), Level.OFF);

		Assert.assertEquals(
			Arrays.asList(null, null, null),
			intraBandPortalCache.get(testKeys));
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

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.PUT,
			portalCacheActionTypes[actionTypeOrdinal]);
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

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.PUT_TTL,
			portalCacheActionTypes[actionTypeOrdinal]);
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

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.REMOVE,
			portalCacheActionTypes[actionTypeOrdinal]);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
	}

	@Test
	public void testRemoveAll() {
		IntraBandPortalCache<String, String> intraBandPortalCache =
			new IntraBandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intraBandPortalCache.removeAll();

		Datagram datagram = _mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.REMOVE_ALL,
			portalCacheActionTypes[actionTypeOrdinal]);
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