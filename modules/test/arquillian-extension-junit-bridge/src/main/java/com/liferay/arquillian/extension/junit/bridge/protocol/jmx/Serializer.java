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

package com.liferay.arquillian.extension.junit.bridge.protocol.jmx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Matthew Tambara
 */
public class Serializer {

	public static byte[] toByteArray(Object object) {
		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(object);
			objectOutputStream.flush();

			return byteArrayOutputStream.toByteArray();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Could not serialize object: " + object, ioe);
		}
	}

	public static <T> T toObject(Class<T> type, byte[] objectArray) {
		try (InputStream inputStream = new ByteArrayInputStream(objectArray);
			ObjectInputStream objectInputStream = new ObjectInputStream(
				inputStream)) {

			Object object = objectInputStream.readObject();

			return (T)object;
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not deserialize object: " + objectArray, e);
		}
	}

}