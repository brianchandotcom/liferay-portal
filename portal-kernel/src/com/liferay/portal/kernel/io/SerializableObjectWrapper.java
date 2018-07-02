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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.Arrays;

/**
 * @author     Tina Tian
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SerializableObjectWrapper implements Externalizable {

	public static <T> T unwrap(Object object) {
		if (!(object instanceof SerializableObjectWrapper)) {
			return (T)object;
		}

		SerializableObjectWrapper serializableWrapper =
			(SerializableObjectWrapper)object;

		if (serializableWrapper._serializable instanceof LazySerializable) {
			LazySerializable lazySerializable =
				(LazySerializable)serializableWrapper._serializable;

			Serializable serializable = lazySerializable.getSerializable();

			if (serializable == null) {
				return null;
			}

			serializableWrapper._serializable = serializable;
		}

		return (T)serializableWrapper._serializable;
	}

	/**
	 * The empty constructor is required by {@link Externalizable}. Do not use
	 * this for any other purpose.
	 */
	public SerializableObjectWrapper() {
		_hashCode = 0;
	}

	public SerializableObjectWrapper(Serializable serializable) {
		_serializable = serializable;

		_hashCode = serializable.hashCode();
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

		if (_hashCode != serializableWrapper._hashCode) {
			return false;
		}

		if ((_serializable instanceof LazySerializable) &&
			(serializableWrapper._serializable instanceof LazySerializable)) {

			LazySerializable lazySerializable1 =
				(LazySerializable)_serializable;
			LazySerializable lazySerializable2 =
				(LazySerializable)serializableWrapper._serializable;

			if (Arrays.equals(
					lazySerializable1.getData(), lazySerializable2.getData())) {

				return true;
			}
		}

		if (_serializable instanceof LazySerializable) {
			LazySerializable lazySerializable = (LazySerializable)_serializable;

			Serializable serializable = lazySerializable.getSerializable();

			if (serializable == null) {
				return Arrays.equals(
					lazySerializable.getData(), serializableWrapper._getData());
			}

			_serializable = serializable;
		}

		if (serializableWrapper._serializable instanceof LazySerializable) {
			LazySerializable lazySerializable =
				(LazySerializable)serializableWrapper._serializable;

			Serializable serializable = lazySerializable.getSerializable();

			if (serializable == null) {
				return Arrays.equals(_getData(), lazySerializable.getData());
			}

			serializableWrapper._serializable = serializable;
		}

		return _serializable.equals(serializableWrapper._serializable);
	}

	@Override
	public int hashCode() {
		return _hashCode;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		_hashCode = objectInput.readInt();

		byte[] data = new byte[objectInput.readInt()];

		objectInput.readFully(data);

		_serializable = new LazySerializable(data);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeInt(_hashCode);

		byte[] data = _getData();

		objectOutput.writeInt(data.length);

		objectOutput.write(data, 0, data.length);
	}

	private byte[] _getData() {
		if (_serializable instanceof LazySerializable) {
			LazySerializable lazySerializable = (LazySerializable)_serializable;

			return lazySerializable.getData();
		}

		Serializer serializer = new Serializer();

		serializer.writeObject(_serializable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SerializableObjectWrapper.class);

	private int _hashCode;
	private volatile Serializable _serializable;

	private static class LazySerializable implements Serializable {

		public byte[] getData() {
			return _data;
		}

		public Serializable getSerializable() {
			Deserializer deserializer = new Deserializer(
				ByteBuffer.wrap(_data));

			try {
				return deserializer.readObject();
			}
			catch (ClassNotFoundException cnfe) {
				_log.error("Unable to deserialize object", cnfe);

				return null;
			}
		}

		private LazySerializable(byte[] data) {
			_data = data;
		}

		private final byte[] _data;

	}

}