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

package com.liferay.portal.kernel.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.TransientValue;

import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class Message implements Cloneable, Serializable {

	public static Message fromByteArray(byte[] bytes)
		throws ClassNotFoundException {

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		return deserializer.readObject();
	}

	@Override
	public Message clone() {
		Message message = new Message();

		message._destinationName = _destinationName;
		message._payload = _payload;
		message._response = _response;
		message._responseDestinationName = _responseDestinationName;
		message._responseId = _responseId;

		if (_values != null) {
			message._values = new HashMap<>(_values);
		}

		return message;
	}

	public boolean contains(String key) {
		if (_values == null) {
			return false;
		}

		return _values.containsKey(key);
	}

	public void copyFrom(Message message) {
		_destinationName = message._destinationName;
		_payload = message._payload;
		_response = message._response;
		_responseDestinationName = message._responseDestinationName;
		_responseId = message._responseId;

		if (message._values != null) {
			_values = new HashMap<>(message._values);
		}
	}

	public void copyTo(Message message) {
		message._destinationName = _destinationName;
		message._payload = _payload;
		message._response = _response;
		message._responseDestinationName = _responseDestinationName;
		message._responseId = _responseId;

		if (_values != null) {
			message._values = new HashMap<>(_values);
		}
	}

	public Object get(String key) {
		if (_values == null) {
			return null;
		}

		Object value = _values.get(key);

		if (value instanceof TransientValue) {
			TransientValue<Object> transientValue =
				(TransientValue<Object>)value;

			value = transientValue.getValue();
		}

		return value;
	}

	public boolean getBoolean(String key) {
		boolean value = false;

		Object object = get(key);

		if (object instanceof Boolean) {
			Boolean booleanObj = (Boolean)object;

			value = booleanObj.booleanValue();
		}
		else {
			value = GetterUtil.getBoolean((String)object);
		}

		return value;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public double getDouble(String key) {
		double value = 0;

		Object object = get(key);

		if (object instanceof Number) {
			Number number = (Number)object;

			value = number.doubleValue();
		}
		else {
			value = GetterUtil.getDouble((String)object);
		}

		return value;
	}

	public int getInteger(String key) {
		int value = 0;

		Object object = get(key);

		if (object instanceof Number) {
			Number number = (Number)object;

			value = number.intValue();
		}
		else {
			value = GetterUtil.getInteger((String)object);
		}

		return value;
	}

	public long getLong(String key) {
		long value = 0;

		Object object = get(key);

		if (object instanceof Number) {
			Number number = (Number)object;

			value = number.longValue();
		}
		else {
			value = GetterUtil.getLong((String)object);
		}

		return value;
	}

	public Object getPayload() {
		return _payload;
	}

	public Object getResponse() {
		return _response;
	}

	public String getResponseDestinationName() {
		return _responseDestinationName;
	}

	public String getResponseId() {
		return _responseId;
	}

	public String getString(String key) {
		return GetterUtil.getString(String.valueOf(get(key)));
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	public void put(String key, Object value) {
		if (value == null) {
			if (_values != null) {
				_values.remove(key);
			}

			return;
		}

		if (_values == null) {
			_values = new HashMap<>();
		}

		if (!(value instanceof Serializable)) {
			value = new TransientValue<>(value);
		}

		_values.put(key, value);
	}

	public void remove(String key) {
		if (_values != null) {
			_values.remove(key);
		}
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public void setResponse(Object response) {
		_response = response;
	}

	public void setResponseDestinationName(String responseDestinationName) {
		_responseDestinationName = responseDestinationName;
	}

	public void setResponseId(String responseId) {
		_responseId = responseId;
	}

	public void setValues(Map<String, Object> values) {
		_values = values;
	}

	public byte[] toByteArray() {
		Serializer serializer = new Serializer();

		serializer.writeObject(this);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{destinationName=");
		sb.append(_destinationName);
		sb.append(", response=");
		sb.append(_response);
		sb.append(", responseDestinationName=");
		sb.append(_responseDestinationName);
		sb.append(", responseId=");
		sb.append(_responseId);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append(", values=");
		sb.append(MapUtil.toString(_values, null, ".*[pP]assword.*"));
		sb.append("}");

		return sb.toString();
	}

	private String _destinationName;
	private Object _payload;
	private Object _response;
	private String _responseDestinationName;
	private String _responseId;
	private Map<String, Object> _values;

}