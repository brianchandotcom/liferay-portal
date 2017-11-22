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

package com.liferay.petra.io;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ClassLoaderObjectInputStreamTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testClassLoaderObjectInputStream() throws Exception {
		TestSerializable testSerializable = new TestSerializable("test");

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeObject(testSerializable);

			objectOutputStream.flush();
		}

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray());

		try (ObjectInputStream objectInputStream = getObjectInputStream(
				unsyncByteArrayInputStream,
				ClassLoaderObjectInputStreamTest.class.getClassLoader())) {

			Assert.assertEquals(
				testSerializable, objectInputStream.readObject());
		}
	}

	protected ObjectInputStream getObjectInputStream(
			InputStream inputStream, ClassLoader classLoader)
		throws IOException {

		return new ClassLoaderObjectInputStream(inputStream, classLoader);
	}

	private static class TestSerializable implements Serializable {

		@Override
		public boolean equals(Object object) {
			TestSerializable testSerializable = (TestSerializable)object;

			return _value.equals(testSerializable._value);
		}

		@Override
		public int hashCode() {
			return _value.hashCode();
		}

		private TestSerializable(Serializable value) {
			_value = value;
		}

		private final Serializable _value;

	}

}