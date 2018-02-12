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

package com.liferay.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class Preference implements Cloneable, Serializable {

	public Preference(String name, String value) {
		this(name, new String[] {value});
	}

	public Preference(String name, String value, boolean readOnly) {
		this(name, new String[] {value}, readOnly);
	}

	public Preference(String name, String[] values) {
		this(name, values, false);
	}

	public Preference(String name, String[] values, boolean readOnly) {
		_name = name;
		_values = values;
		_readOnly = readOnly;
	}

	@Override
	public Object clone() {
		return new Preference(_name, _values, _readOnly);
	}

	public String getName() {
		return _name;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6 + (_values.length * 2 - 1));

		sb.append("{name=");
		sb.append(getName());
		sb.append(", readOnly=");
		sb.append(_readOnly);
		sb.append(", values=[");

		for (int i = 0; i < _values.length; i++) {
			sb.append(_values[i]);

			if (i < (_values.length - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	private final String _name;
	private final boolean _readOnly;
	private String[] _values;

}