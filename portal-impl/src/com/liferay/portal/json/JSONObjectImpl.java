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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Writer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONObjectImpl implements JSONObject {

	public JSONObjectImpl() {
		_jsonObject = new org.json.JSONObject();
	}

	public JSONObjectImpl(JSONObject jsonObject, String[] names)
		throws JSONException {

		try {
			JSONObjectImpl jsonObjectImpl = (JSONObjectImpl)jsonObject;

			_jsonObject = new org.json.JSONObject(
				jsonObjectImpl.getJSONObject(), names);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(Map<?, ?> map) {
		_jsonObject = new org.json.JSONObject(map);
	}

	public JSONObjectImpl(Object bean) {
		_jsonObject = new org.json.JSONObject(bean);
	}

	public JSONObjectImpl(Object obj, String[] names) {
		_jsonObject = new org.json.JSONObject(obj, names);
	}

	public JSONObjectImpl(org.json.JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	public JSONObjectImpl(String json) throws JSONException {
		try {
			if (Validator.isNull(json)) {
				json = _NULL_JSON;
			}

			_jsonObject = new org.json.JSONObject(json);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	@Override
	public Object get(String key) {
		return _jsonObject.opt(key);
	}

	@Override
	public boolean getBoolean(String key) {
		return _jsonObject.optBoolean(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return _jsonObject.optBoolean(key, defaultValue);
	}

	@Override
	public double getDouble(String key) {
		return _jsonObject.optDouble(key);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return _jsonObject.optDouble(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return _jsonObject.optInt(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return _jsonObject.optInt(key, defaultValue);
	}

	@Override
	public JSONArray getJSONArray(String key) {
		org.json.JSONArray jsonArray = _jsonObject.optJSONArray(key);

		if (jsonArray == null) {
			return null;
		}

		return new JSONArrayImpl(jsonArray);
	}

	public org.json.JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public JSONObject getJSONObject(String key) {
		org.json.JSONObject jsonObject = _jsonObject.optJSONObject(key);

		if (jsonObject == null) {
			return null;
		}

		return new JSONObjectImpl(jsonObject);
	}

	@Override
	public long getLong(String key) {
		return _jsonObject.optLong(key);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return _jsonObject.optLong(key, defaultValue);
	}

	@Override
	public String getString(String key) {
		return _jsonObject.optString(key);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return _jsonObject.optString(key, defaultValue);
	}

	@Override
	public boolean has(String key) {
		return _jsonObject.has(key);
	}

	@Override
	public boolean isNull(String key) {
		return _jsonObject.isNull(key);
	}

	@Override
	public Iterator<String> keys() {
		return _jsonObject.keys();
	}

	@Override
	public int length() {
		return _jsonObject.length();
	}

	@Override
	public JSONArray names() {
		return new JSONArrayImpl(_jsonObject.names());
	}

	@Override
	public JSONObject put(String key, boolean value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, Date value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, double value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, int value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, JSONArray value) {
		try {
			_jsonObject.put(key, ((JSONArrayImpl)value).getJSONArray());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, JSONObject value) {
		try {
			_jsonObject.put(key, ((JSONObjectImpl)value).getJSONObject());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, long value) {
		try {
			_jsonObject.put(key, String.valueOf(value));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, Object value) {
		try {
			if (value instanceof JSONArray) {
				put(key, (JSONArray)value);
			}
			else if (value instanceof JSONObject) {
				put(key, (JSONObject)value);
			}
			else {
				_jsonObject.put(key, value);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject put(String key, String value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public JSONObject putException(Exception exception) {
		try {
			_jsonObject.put(
				"exception",
				exception.getClass() + StringPool.COLON +
					exception.getMessage());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		try {
			_jsonObject = new org.json.JSONObject(objectInput.readUTF());
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public Object remove(String key) {
		return _jsonObject.remove(key);
	}

	@Override
	public String toJSONString() {
		return toString();
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	@Override
	public String toString(int indentFactor) throws JSONException {
		try {
			return _jsonObject.toString(indentFactor);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	@Override
	public Writer write(Writer writer) throws JSONException {
		try {
			return _jsonObject.write(writer);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(toString());
	}

	private static final String _NULL_JSON = "{}";

	private static final Log _log = LogFactoryUtil.getLog(JSONObjectImpl.class);

	private org.json.JSONObject _jsonObject;

}