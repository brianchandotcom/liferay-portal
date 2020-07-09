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

import com.liferay.petra.io.ProtectedObjectInputStream;
import com.liferay.petra.io.constants.SerializationConstants;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.lang.ClassResolverUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class ClusterSerializationUtil {

	public static Object readObject(byte[] bytes, int offset, int length)
		throws ClassNotFoundException {

		byte serializableType = bytes[offset];

		if (SerializationConstants.TC_OBJECT != serializableType) {
			throw new IllegalStateException(
				"Unable to deserialize this type:" + serializableType);
		}

		try {
			ObjectInputStream objectInputStream =
				new ClusterProtectedAnnotatedObjectInputStream(
					new UnsyncByteArrayInputStream(
						bytes, offset + 1, length - 1));

			return objectInputStream.readObject();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static byte[] writeObject(Serializable serializable) {
		try {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			unsyncByteArrayOutputStream.write(SerializationConstants.TC_OBJECT);

			ObjectOutputStream objectOutputStream =
				new ClusterAnnotatedObjectOutputStream(
					unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(serializable);

			objectOutputStream.flush();

			return unsyncByteArrayOutputStream.unsafeGetByteArray();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to write ordinary serializable object " + serializable,
				ioException);
		}
	}

	private static class ClusterAnnotatedObjectOutputStream
		extends ObjectOutputStream {

		@Override
		protected void annotateClass(Class<?> clazz) throws IOException {
			String contextName = ClassLoaderPool.getContextName(
				clazz.getClassLoader());

			writeUTF(contextName);
		}

		private ClusterAnnotatedObjectOutputStream(OutputStream outputStream)
			throws IOException {

			super(outputStream);
		}

	}

	private static class ClusterProtectedAnnotatedObjectInputStream
		extends ProtectedObjectInputStream {

		@Override
		protected Class<?> doResolveClass(ObjectStreamClass objectStreamClass)
			throws ClassNotFoundException, IOException {

			String contextName = readUTF();

			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				contextName);

			String className = objectStreamClass.getName();

			return ClassResolverUtil.resolve(className, classLoader);
		}

		private ClusterProtectedAnnotatedObjectInputStream(
				InputStream inputStream)
			throws IOException {

			super(inputStream);
		}

	}

}