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

package com.liferay.portal.cluster.multiple.internal.io;

import com.liferay.petra.io.SerializationConstants;
import com.liferay.petra.io.Serializer;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import java.nio.ByteBuffer;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Lance Ji
 */
public class ClusterDeserializerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new ClusterDeserializer(ByteBuffer.allocate(3));
	}

	@Test
	public void testReadObjectOrdinaryNPE() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(SerializationConstants.TC_OBJECT);

		unsyncByteArrayOutputStream.write(1);
		unsyncByteArrayOutputStream.write(new byte[4]);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		Date date = new Date(123456);

		objectOutputStream.writeObject(date);

		ByteBuffer byteBuffer =
			unsyncByteArrayOutputStream.unsafeGetByteBuffer();

		// Corrupt magic header

		byteBuffer.put(6, (byte)0xFF);

		ClusterDeserializer clusterDeserializer = new ClusterDeserializer(
			byteBuffer);

		try {
			clusterDeserializer.readObject();

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				re.getCause() instanceof StreamCorruptedException);
		}
	}

	@Test
	public void testWithSerializer() throws Exception {
		Serializer serializer = new Serializer();

		Date date = new Date(123456);

		serializer.writeObject(date);

		ClusterDeserializer clusterDeserializer = new ClusterDeserializer(
			serializer.toByteBuffer());

		Object object = clusterDeserializer.readObject();

		Assert.assertTrue(object instanceof Date);
		Assert.assertEquals(object, date);
	}

	@Test(expected = IllegalStateException.class)
	public void testWriteNonObjectWithSerializer() throws Exception {
		Serializer serializer = new Serializer();

		Class<?> clazz = getClass();

		serializer.writeObject(clazz);

		ClusterDeserializer clusterDeserializer = new ClusterDeserializer(
			serializer.toByteBuffer());

		clusterDeserializer.readObject();
	}

}