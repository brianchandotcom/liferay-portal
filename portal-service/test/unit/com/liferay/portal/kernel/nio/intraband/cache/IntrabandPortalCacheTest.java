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

package com.liferay.portal.kernel.nio.intraband.cache;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.Serializable;

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
public class IntrabandPortalCacheTest {

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
		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intrabandPortalCache.registerCacheListener(null);
		intrabandPortalCache.registerCacheListener(null, null);
		intrabandPortalCache.unregisterCacheListener(null);
		intrabandPortalCache.unregisterCacheListeners();
	}

	@Test
	public void testConstructor() throws Exception {
		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		Assert.assertEquals(_testName, intrabandPortalCache.getName());
		Assert.assertSame(
			_mockRegistrationReference,
			ReflectionTestUtil.getFieldValue(
				intrabandPortalCache, "_registrationReference"));
		Assert.assertSame(
			_mockIntraband,
			ReflectionTestUtil.getFieldValue(
				intrabandPortalCache, "_intraband"));
	}

	@Test
	public void testGet() {

		// Normal get

		final String testKey = "testKey";
		final String testValue = "testValue";

		final AtomicReference<RuntimeException> runtimeExceptionReference =
			new AtomicReference<RuntimeException>();

		MockIntraband mockIntraband = new MockIntraband() {

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
					Assert.assertEquals(testKey, deserializer.readObject());
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

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, new MockRegistrationReference(mockIntraband));

		Assert.assertEquals(testValue, intrabandPortalCache.get(testKey));

		CaptureHandler captureHandler = null;

		try {

			// Unable to get, with log

			captureHandler = JDKLoggerTestUtil.configureJDKLogger(
				IntrabandPortalCache.class.getName(), Level.WARNING);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			RuntimeException runtimeException = new RuntimeException();

			runtimeExceptionReference.set(runtimeException);

			Assert.assertNull(intrabandPortalCache.get(testKey));
			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to get, coverting to cache miss",
				logRecord.getMessage());
			Assert.assertSame(runtimeException, logRecord.getThrown());

			// Unable to get, without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			Assert.assertNull(intrabandPortalCache.get(testKey));
			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			if (captureHandler != null) {
				captureHandler.close();
			}
		}
	}

	@Test
	public void testPut() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		ReflectionTestUtil.invokeBridge(
			intrabandPortalCache, "put",
			new Class<?>[] {Serializable.class, Object.class}, testKey,
			testValue);

		Datagram datagram = _mockIntraband.getDatagram();

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
	public void testPutQuiet() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		ReflectionTestUtil.invokeBridge(
			intrabandPortalCache, "putQuiet",
			new Class<?>[] {Serializable.class, Object.class}, testKey,
			testValue);

		Datagram datagram = _mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.PUT_QUIET,
			portalCacheActionTypes[actionTypeOrdinal]);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
		Assert.assertEquals(testValue, deserializer.readObject());
	}

	@Test
	public void testPutQuietTTL() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";
		int testTTL = 100;

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intrabandPortalCache.putQuiet(testKey, testValue, testTTL);

		Datagram datagram = _mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		int actionTypeOrdinal = deserializer.readInt();

		PortalCacheActionType[] portalCacheActionTypes =
			PortalCacheActionType.values();

		Assert.assertEquals(
			PortalCacheActionType.PUT_QUIET_TTL,
			portalCacheActionTypes[actionTypeOrdinal]);
		Assert.assertEquals(_testName, deserializer.readString());
		Assert.assertEquals(testKey, deserializer.readObject());
		Assert.assertEquals(testValue, deserializer.readObject());
		Assert.assertEquals(testTTL, deserializer.readInt());
	}

	@Test
	public void testPutTTL() throws Exception {
		String testKey = "testKey";
		String testValue = "testValue";
		int testTTL = 100;

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intrabandPortalCache.put(testKey, testValue, testTTL);

		Datagram datagram = _mockIntraband.getDatagram();

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

		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intrabandPortalCache.remove(testKey);

		Datagram datagram = _mockIntraband.getDatagram();

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
		IntrabandPortalCache<String, String> intrabandPortalCache =
			new IntrabandPortalCache<String, String>(
				_testName, _mockRegistrationReference);

		intrabandPortalCache.removeAll();

		Datagram datagram = _mockIntraband.getDatagram();

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

	private MockIntraband _mockIntraband = new MockIntraband();
	private MockRegistrationReference _mockRegistrationReference =
		new MockRegistrationReference(_mockIntraband);
	private String _testName = "testName";

}