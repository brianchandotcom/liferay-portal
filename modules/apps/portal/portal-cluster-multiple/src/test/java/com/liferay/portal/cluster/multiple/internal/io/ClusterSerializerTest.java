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

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.SerializationConstants;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Lance Ji
 */
public class ClusterSerializerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new ClusterSerializer();
	}

	@Test
	public void testWithDeserializer() throws Exception {
		ClusterSerializer clusterSerializer = new ClusterSerializer();

		Date date = new Date(123456);

		clusterSerializer.writeObject(date);

		ByteBuffer byteBuffer = clusterSerializer.toByteBuffer();

		Assert.assertEquals(SerializationConstants.TC_OBJECT, byteBuffer.get());

		Deserializer deserializer = new Deserializer(
			clusterSerializer.toByteBuffer());

		Object object = deserializer.readObject();

		Assert.assertTrue(object instanceof Date);
		Assert.assertEquals(object, date);
	}

	@Test
	public void testWriteObjectOrdinaryNPE() throws Exception {
		ClusterSerializer clusterSerializer = new ClusterSerializer();

		Serializable serializable = new Serializable() {

			private void writeObject(ObjectOutputStream objectOutputStream)
				throws IOException {

				throw new IOException("Forced IOException");
			}

		};

		try {
			clusterSerializer.writeObject(serializable);

			Assert.fail();
		}
		catch (RuntimeException re) {
			String message = re.getMessage();

			Assert.assertTrue(
				message.startsWith(
					"Unable to write ordinary serializable object "));

			Throwable throwable = re.getCause();

			Assert.assertTrue(throwable instanceof IOException);
			Assert.assertEquals("Forced IOException", throwable.getMessage());
		}
	}

}