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
import com.liferay.petra.io.SerializationConstants;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.lang.ClassResolverUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import java.nio.ByteBuffer;

/**
 * @author Lance Ji
 */
public class ClusterDeserializer {

	public ClusterDeserializer(ByteBuffer byteBuffer) {
		_byteBuffer = byteBuffer;
	}

	public Object readObject() throws ClassNotFoundException {
		try {
			byte serializableType = _byteBuffer.get();

			if (SerializationConstants.TC_OBJECT == serializableType) {
				ObjectInputStream objectInputStream =
					new ClusterProtectedAnnotatedObjectInputStream(
						new UnsyncByteArrayInputStream(
							_byteBuffer.array(), _byteBuffer.position(),
							_byteBuffer.remaining()));

				return objectInputStream.readObject();
			}

			throw new IllegalStateException(
				"Unable to deserialize this type:" + serializableType);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private final ByteBuffer _byteBuffer;

	private class ClusterProtectedAnnotatedObjectInputStream
		extends ProtectedObjectInputStream {

		public ClusterProtectedAnnotatedObjectInputStream(
				InputStream inputStream)
			throws IOException {

			super(inputStream);
		}

		@Override
		protected Class<?> doResolveClass(ObjectStreamClass objectStreamClass)
			throws ClassNotFoundException, IOException {

			String contextName = readUTF();

			ClassLoader classLoader = ClusterClassLoaderPool.getClassLoader(
				contextName);

			String className = objectStreamClass.getName();

			return ClassResolverUtil.resolve(className, classLoader);
		}

	}

}