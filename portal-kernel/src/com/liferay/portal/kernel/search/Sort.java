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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Bruno Farache
 */
public class Sort implements Serializable {

	public static final int CUSTOM_TYPE = 9;

	public static final int DOC_TYPE = 1;

	public static final int DOUBLE_TYPE = 7;

	public static final int FLOAT_TYPE = 5;

	public static final int GEO_DISTANCE_TYPE = 10;

	public static final int INT_TYPE = 4;

	public static final int LONG_TYPE = 6;

	public static final int SCORE_TYPE = 0;

	public static final int STRING_TYPE = 3;

	public Sort() {
	}

	public Sort(String fieldName, boolean reverse) {
		this(fieldName, STRING_TYPE, reverse);
	}

	public Sort(String fieldName, int type, boolean reverse) {
		_fieldName = fieldName;
		_type = type;
		_reverse = reverse;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public int getType() {
		return _type;
	}

	public boolean isReverse() {
		return _reverse;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setReverse(boolean reverse) {
		_reverse = reverse;
	}

	public void setType(int type) {
		_type = type;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{fieldName=");
		sb.append(_fieldName);
		sb.append(", type=");
		sb.append(_type);
		sb.append(", reverse=");
		sb.append(_reverse);
		sb.append("}");

		return sb.toString();
	}

	private String _fieldName;
	private boolean _reverse;
	private int _type;

}