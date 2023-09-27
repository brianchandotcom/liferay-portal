/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.json;

import com.liferay.petra.function.UnsafeSupplier;

import java.io.Externalizable;
import java.io.Writer;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("rawtypes")
public interface JSONArray extends Externalizable, Iterable, JSONSerializable {

	public Object get(int index);

	public boolean getBoolean(int index);

	public double getDouble(int index);

	public int getInt(int index);

	public JSONArray getJSONArray(int index);

	public JSONObject getJSONObject(int index);

	public long getLong(int index);

	public String getString(int index);

	public boolean isNull(int index);

	public String join(String separator) throws JSONException;

	public int length();

	public JSONArray put(boolean value);

	public JSONArray put(double value);

	public JSONArray put(int value);

	public JSONArray put(JSONArray jsonArray);

	public JSONArray put(JSONObject jsonObject);

	public JSONArray put(long value);

	public JSONArray put(Object value);

	public JSONArray put(String value);

	public JSONArray put(UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	@Override
	public String toString();

	public String toString(int indentFactor) throws JSONException;

	public Writer write(Writer writer) throws JSONException;

}