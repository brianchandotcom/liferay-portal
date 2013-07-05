/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.json;

import java.io.Writer;

import java.util.Date;
import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public interface JSONObject {

	public boolean getBoolean(String key);

	public boolean getBoolean(String key, boolean defaultValue);

	public double getDouble(String key);

	public double getDouble(String key, double defaultValue);

	public int getInt(String key);

	public int getInt(String key, int defaultValue);

	public JSONArray getJSONArray(String key);

	public JSONObject getJSONObject(String key);

	public long getLong(String key);

	public long getLong(String key, long defaultValue);

	public String getString(String key);

	public String getString(String key, String defaultValue);

	public boolean has(String key);

	public boolean isNull(String key);

	public Iterator<String> keys();

	public int length();

	public JSONArray names();

	public JSONObject put(String key, boolean value);

	public JSONObject put(String key, Date value);

	public JSONObject put(String key, double value);

	public JSONObject put(String key, int value);

	public JSONObject put(String key, JSONArray value);

	public JSONObject put(String key, JSONObject value);

	public JSONObject put(String key, long value);

	public JSONObject put(String key, String value);

	public JSONObject putErrorMessage(int statusCode, String errorMessage);

	public JSONObject putException(Exception exception);

	public JSONObject putStatus(int statusCode);

	public Object remove(String key);

	@Override
	public String toString();

	public String toString(int indentFactor) throws JSONException;

	public Writer write(Writer writer) throws JSONException;

}