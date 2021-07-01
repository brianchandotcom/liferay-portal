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

package com.liferay.shielded.container.internal.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/**
 * @author Shuyang Zhou
 */
public class SerializationUtil {

	public static Object deserialize(byte[] data, ClassLoader classLoader)
		throws Exception {

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(data);
			ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream) {

				@Override
				protected Class<?> resolveClass(
						ObjectStreamClass objectStreamClass)
					throws ClassNotFoundException, IOException {

					return Class.forName(
						objectStreamClass.getName(), true, classLoader);
				}

			}) {

			return objectInputStream.readObject();
		}
	}

	public static byte[] serialize(Object object) throws IOException {
		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(object);
			objectOutputStream.flush();

			return byteArrayOutputStream.toByteArray();
		}
	}

}