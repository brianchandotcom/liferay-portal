/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONArrayImpl implements JSONArray {

	public JSONArrayImpl() {
		_jsonArray = new org.json.JSONArray();
	}

	public JSONArrayImpl(Collection<?> collection) {
		_jsonArray = new org.json.JSONArray(collection);
	}

	public JSONArrayImpl(org.json.JSONArray jsonArray) {
		_jsonArray = jsonArray;
	}

	public JSONArrayImpl(String json) throws JSONException {
		try {
			if (Validator.isNull(json)) {
				json = _NULL_JSON;
			}

			_jsonArray = new org.json.JSONArray(json);
		}
		catch (Exception exception) {
			throw new JSONException(exception);
		}
	}

	@Override
	public Object get(int index) {
		Object value = _jsonArray.opt(index);

		if (value instanceof org.json.JSONArray) {
			return new JSONArrayImpl((org.json.JSONArray)value);
		}

		if (value instanceof org.json.JSONObject) {
			return new JSONObjectImpl((org.json.JSONObject)value);
		}

		return value;
	}

	@Override
	public boolean getBoolean(int index) {
		return _jsonArray.optBoolean(index);
	}

	@Override
	public double getDouble(int index) {
		return _jsonArray.optDouble(index);
	}

	@Override
	public int getInt(int index) {
		return _jsonArray.optInt(index);
	}

	public org.json.JSONArray getJSONArray() {
		return _jsonArray;
	}

	@Override
	public JSONArray getJSONArray(int index) {
		org.json.JSONArray jsonArray = _jsonArray.optJSONArray(index);

		if (jsonArray == null) {
			return null;
		}

		return new JSONArrayImpl(jsonArray);
	}

	@Override
	public JSONObject getJSONObject(int index) {
		org.json.JSONObject jsonObj = _jsonArray.optJSONObject(index);

		if (jsonObj == null) {
			return null;
		}

		return new JSONObjectImpl(jsonObj);
	}

	@Override
	public long getLong(int index) {
		return _jsonArray.optLong(index);
	}

	@Override
	public String getString(int index) {
		return _jsonArray.optString(index);
	}

	@Override
	public boolean isNull(int index) {
		return _jsonArray.isNull(index);
	}

	@Override
	public Iterator<Object> iterator() {
		List<Object> list = new ArrayList<>();

		for (int i = 0; i < length(); i++) {
			list.add(get(i));
		}

		return list.iterator();
	}

	@Override
	public String join(String separator) throws JSONException {
		try {
			return _jsonArray.join(separator);
		}
		catch (Exception exception) {
			throw new JSONException(exception);
		}
	}

	@Override
	public int length() {
		return _jsonArray.length();
	}

	@Override
	public JSONArray put(boolean value) {
		_jsonArray.put(value);

		return this;
	}

	@Override
	public JSONArray put(double value) {
		try {
			_jsonArray.put(value);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return this;
	}

	@Override
	public JSONArray put(int value) {
		_jsonArray.put(value);

		return this;
	}

	@Override
	public JSONArray put(JSONArray jsonArray) {
		JSONArrayImpl jsonArrayImpl = (JSONArrayImpl)jsonArray;

		_jsonArray.put(jsonArrayImpl.getJSONArray());

		return this;
	}

	@Override
	public JSONArray put(JSONObject jsonObject) {
		JSONObjectImpl jsonObjectImpl = (JSONObjectImpl)jsonObject;

		_jsonArray.put(jsonObjectImpl.getJSONObject());

		return this;
	}

	@Override
	public JSONArray put(long value) {
		_jsonArray.put(String.valueOf(value));

		return this;
	}

	@Override
	public JSONArray put(Object value) {
		if (value instanceof JSONArray) {
			put((JSONArray)value);
		}
		else if (value instanceof JSONObject) {
			put((JSONObject)value);
		}
		else {
			_jsonArray.put(value);
		}

		return this;
	}

	@Override
	public JSONArray put(String value) {
		_jsonArray.put(value);

		return this;
	}

	@Override
	public JSONArray put(
		UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

		try {
			Object value = valueUnsafeSupplier.get();

			if (value != null) {
				return put(value);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return this;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		try {
			_jsonArray = new org.json.JSONArray(
				(String)objectInput.readObject());
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	@Override
	public String toJSONString() {
		return toString();
	}

	@Override
	public String toString() {
		return _jsonArray.toString();
	}

	@Override
	public String toString(int indentFactor) throws JSONException {
		try {
			return _jsonArray.toString(indentFactor);
		}
		catch (Exception exception) {
			throw new JSONException(exception);
		}
	}

	@Override
	public Writer write(Writer writer) throws JSONException {
		try {
			return _jsonArray.write(writer);
		}
		catch (Exception exception) {
			throw new JSONException(exception);
		}
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeObject(toString());
	}

	private static final String _NULL_JSON = "[]";

	private static final Log _log = LogFactoryUtil.getLog(JSONArrayImpl.class);

	private org.json.JSONArray _jsonArray;

}