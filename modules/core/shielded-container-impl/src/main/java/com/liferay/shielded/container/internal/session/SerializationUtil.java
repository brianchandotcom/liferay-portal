/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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