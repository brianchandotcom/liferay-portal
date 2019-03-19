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

package com.liferay.portal.kernel.io;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author     Tina Tian
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SerializableObjectWrapperTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEquals() throws Exception {
		_testEquals();
	}

	@Test
	public void testHashCode() throws Exception {
		_testHashCode();
	}

	@Test
	public void testSpecialEquals() throws Exception {
		Assert.assertTrue(
			_SPECIAL_TEST_SERIALIZABLE_1.equals(_SPECIAL_TEST_SERIALIZABLE_2));
		Assert.assertTrue(
			_specialTestSerializableWrapper1.equals(
				_specialTestSerializableWrapper2));

		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_specialTestSerializableWrapper1),
				_getDeserializedObject(_specialTestSerializableWrapper1)));
		Assert.assertTrue(
			_specialTestSerializableWrapper1.equals(
				_getDeserializedObject(_specialTestSerializableWrapper1)));
		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_specialTestSerializableWrapper1),
				_specialTestSerializableWrapper1));

		Assert.assertTrue(
			_specialTestSerializableWrapper1.equals(
				_getDeserializedObject(_specialTestSerializableWrapper2)));
		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_specialTestSerializableWrapper1),
				_specialTestSerializableWrapper2));
		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_specialTestSerializableWrapper1),
				_getDeserializedObject(_specialTestSerializableWrapper2)));
	}

	@Test
	public void testSpecialEqualsWithBrokenClassLoader() throws Exception {
		ClassLoaderPool.unregister(ClassLoaderPool.class.getClassLoader());

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassNotFoundException cnfe = new ClassNotFoundException();

		currentThread.setContextClassLoader(
			new ClassLoader() {

				@Override
				public Class<?> loadClass(String name)
					throws ClassNotFoundException {

					if (name.equals(SpecialTestSerializable.class.getName())) {
						throw cnfe;
					}

					return super.loadClass(name);
				}

			});

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SerializableObjectWrapper.class.getName(), Level.ALL)) {

			Assert.assertTrue(
				Objects.equals(
					_getDeserializedObject(_specialTestSerializableWrapper1),
					_getDeserializedObject(_specialTestSerializableWrapper1)));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(
				_specialTestSerializableWrapper1.equals(
					_getDeserializedObject(_specialTestSerializableWrapper1)));

			_assertLogAndClear(logRecords, cnfe);

			Assert.assertTrue(
				Objects.equals(
					_getDeserializedObject(_specialTestSerializableWrapper1),
					_specialTestSerializableWrapper1));

			_assertLogAndClear(logRecords, cnfe);

			Assert.assertFalse(
				_specialTestSerializableWrapper1.equals(
					_getDeserializedObject(_specialTestSerializableWrapper2)));

			_assertLogAndClear(logRecords, cnfe);

			Assert.assertFalse(
				Objects.equals(
					_getDeserializedObject(_specialTestSerializableWrapper1),
					_specialTestSerializableWrapper2));

			_assertLogAndClear(logRecords, cnfe);

			Assert.assertFalse(
				Objects.equals(
					_getDeserializedObject(_specialTestSerializableWrapper1),
					_getDeserializedObject(_specialTestSerializableWrapper2)));

			_assertLogAndClear(logRecords, cnfe);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Test
	public void testUnwrap() throws Exception {
		Assert.assertEquals(
			_TEST_SERIALIZABLE,
			SerializableObjectWrapper.unwrap(_testSerializableObjectWrapper));

		Assert.assertEquals(
			_TEST_SERIALIZABLE,
			SerializableObjectWrapper.unwrap(
				_getDeserializedObject(_testSerializableObjectWrapper)));

		Assert.assertEquals(
			_TEST_SERIALIZABLE,
			SerializableObjectWrapper.unwrap(_TEST_SERIALIZABLE));
	}

	@Test
	public void testWithBrokenClassLoader() throws Exception {
		ClassLoaderPool.unregister(ClassLoaderPool.class.getClassLoader());

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassNotFoundException cnfe = new ClassNotFoundException();

		currentThread.setContextClassLoader(
			new ClassLoader() {

				@Override
				public Class<?> loadClass(String name)
					throws ClassNotFoundException {

					if (name.equals(TestSerializable.class.getName())) {
						throw cnfe;
					}

					return super.loadClass(name);
				}

			});

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SerializableObjectWrapper.class.getName(), Level.ALL)) {

			// Test unwrap

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertNull(
				SerializableObjectWrapper.unwrap(
					_getDeserializedObject(_testSerializableObjectWrapper)));

			_assertLogAndClear(logRecords, cnfe);

			// Test equals

			_testEquals();

			// Test hash code

			_testHashCode();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Test
	public void testWriteExternal() throws Exception {
		SerializableObjectWrapper deserializedObject = _getDeserializedObject(
			_testSerializableObjectWrapper);

		Assert.assertEquals(
			deserializedObject, _getDeserializedObject(deserializedObject));
	}

	private void _assertLogAndClear(
		List<LogRecord> logRecords, ClassNotFoundException cnfe) {

		Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to deserialize object", logRecord.getMessage());
		Assert.assertSame(cnfe, logRecord.getThrown());

		logRecords.clear();
	}

	private SerializableObjectWrapper _getDeserializedObject(
			SerializableObjectWrapper serializableObjectWrapper)
		throws Exception {

		try (UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream()) {

			try (ObjectOutputStream oos = new ObjectOutputStream(ubaos)) {
				oos.writeObject(serializableObjectWrapper);
			}

			try (UnsyncByteArrayInputStream ubais =
					new UnsyncByteArrayInputStream(
						ubaos.unsafeGetByteArray(), 0, ubaos.size());
				ObjectInputStream ois = new ObjectInputStream(ubais)) {

				return (SerializableObjectWrapper)ois.readObject();
			}
		}
	}

	private void _testEquals() throws Exception {
		Assert.assertFalse(
			_testSerializableObjectWrapper.equals(_TEST_SERIALIZABLE));
		Assert.assertFalse(
			_testSerializableObjectWrapper.equals(
				new SerializableObjectWrapper(_ANOTHER_TEST_SERIALIZABLE)));

		Assert.assertTrue(
			_testSerializableObjectWrapper.equals(
				_testSerializableObjectWrapper));
		Assert.assertTrue(
			_testSerializableObjectWrapper.equals(
				new SerializableObjectWrapper(_TEST_SERIALIZABLE)));
		Assert.assertTrue(
			_testSerializableObjectWrapper.equals(
				_getDeserializedObject(_testSerializableObjectWrapper)));
		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_testSerializableObjectWrapper),
				_testSerializableObjectWrapper));
		Assert.assertTrue(
			Objects.equals(
				_getDeserializedObject(_testSerializableObjectWrapper),
				_getDeserializedObject(_testSerializableObjectWrapper)));
	}

	private void _testHashCode() throws Exception {
		Assert.assertNotEquals(
			_testSerializableObjectWrapper.hashCode(),
			new SerializableObjectWrapper(
				_ANOTHER_TEST_SERIALIZABLE
			).hashCode());

		Assert.assertEquals(
			_testSerializableObjectWrapper.hashCode(),
			new SerializableObjectWrapper(
				_TEST_SERIALIZABLE
			).hashCode());

		SerializableObjectWrapper deserializedObject = _getDeserializedObject(
			_testSerializableObjectWrapper);

		Assert.assertEquals(
			_testSerializableObjectWrapper.hashCode(),
			deserializedObject.hashCode());
	}

	private static final TestSerializable _ANOTHER_TEST_SERIALIZABLE =
		new TestSerializable("_ANOTHER_TEST_SERIALIZABLE");

	private static final SpecialTestSerializable _SPECIAL_TEST_SERIALIZABLE_1 =
		new SpecialTestSerializable("_SPECIAL_TEST_SERIALIZABLE", "");

	private static final SpecialTestSerializable _SPECIAL_TEST_SERIALIZABLE_2 =
		new SpecialTestSerializable(
			"_SPECIAL_TEST_SERIALIZABLE", "_SPECIAL_TEST_SERIALIZABLE_VALUE");

	private static final TestSerializable _TEST_SERIALIZABLE =
		new TestSerializable("_TEST_SERIALIZABLE");

	private final SerializableObjectWrapper _specialTestSerializableWrapper1 =
		new SerializableObjectWrapper(_SPECIAL_TEST_SERIALIZABLE_1);
	private final SerializableObjectWrapper _specialTestSerializableWrapper2 =
		new SerializableObjectWrapper(_SPECIAL_TEST_SERIALIZABLE_2);
	private final SerializableObjectWrapper _testSerializableObjectWrapper =
		new SerializableObjectWrapper(_TEST_SERIALIZABLE);

	private static class SpecialTestSerializable implements Serializable {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof SpecialTestSerializable)) {
				return false;
			}

			SpecialTestSerializable specialTestSerializable =
				(SpecialTestSerializable)object;

			return Objects.equals(_name, specialTestSerializable._name);
		}

		@Override
		public int hashCode() {
			return _name.hashCode();
		}

		private SpecialTestSerializable(String name, String value) {
			_name = name;
			_value = value;
		}

		private final String _name;
		private final String _value;

	}

	private static class TestSerializable implements Serializable {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof TestSerializable)) {
				return false;
			}

			TestSerializable testSerializable = (TestSerializable)object;

			return Objects.equals(_name, testSerializable._name);
		}

		@Override
		public int hashCode() {
			return _name.hashCode();
		}

		private TestSerializable(String name) {
			_name = name;
		}

		private final String _name;

	}

}