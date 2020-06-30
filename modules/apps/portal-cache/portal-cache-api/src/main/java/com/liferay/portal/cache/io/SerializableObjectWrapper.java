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

package com.liferay.portal.cache.io;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.Objects;

/**
 * @author Tina Tian
 */
public class SerializableObjectWrapper implements Serializable {

	public static <T> T unwrap(Object object) {
		if (!(object instanceof SerializableObjectWrapper)) {
			return (T)object;
		}

		SerializableObjectWrapper serializableWrapper =
			(SerializableObjectWrapper)object;

		SerializableHolder serializableHolder =
			serializableWrapper._serializableHolder;

		return (T)serializableHolder.getSerializable();
	}

	public SerializableObjectWrapper(Serializable serializable) {
		_serializableHolder = new SerializableHolder(serializable, null);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SerializableObjectWrapper)) {
			return false;
		}

		SerializableObjectWrapper serializableWrapper =
			(SerializableObjectWrapper)object;

		SerializableHolder serializableHolder =
			serializableWrapper._serializableHolder;

		return Objects.equals(
			_serializableHolder.getSerializable(),
			serializableHolder.getSerializable());
	}

	@Override
	public int hashCode() {
		Serializable serializable = _serializableHolder.getSerializable();

		return serializable.hashCode();
	}

	private void readObject(ObjectInputStream objectInputStream)
		throws IOException {

		byte[] data = new byte[objectInputStream.readInt()];

		objectInputStream.readFully(data);

		_serializableHolder = new SerializableHolder(null, data);
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		Serializer serializer = new Serializer();

		serializer.writeObject(_serializableHolder.getSerializable());

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		objectOutputStream.writeInt(byteBuffer.remaining());

		objectOutputStream.write(
			byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SerializableObjectWrapper.class);

	private static final long serialVersionUID = 5383490138816033114L;

	private SerializableHolder _serializableHolder;

	private static class SerializableHolder {

		public Serializable getSerializable() {
			Serializable serializable = _serializable;

			if (serializable == null) {
				if (_bytes == null) {
					return null;
				}

				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(_bytes));

				try {
					serializable = deserializer.readObject();

					_serializable = serializable;
				}
				catch (ClassNotFoundException cnfe) {
					_log.error("Unable to deserialize object", cnfe);
				}
			}

			return serializable;
		}

		private SerializableHolder(Serializable serializable, byte[] bytes) {
			_serializable = serializable;
			_bytes = bytes;
		}

		private final byte[] _bytes;
		private volatile Serializable _serializable;

	}

}