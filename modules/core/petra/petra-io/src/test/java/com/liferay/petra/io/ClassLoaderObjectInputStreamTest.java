/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ClassLoaderObjectInputStreamTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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