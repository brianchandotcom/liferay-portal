/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.StringBundler;

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
		return StringBundler.concat(
			"{fieldName=", _fieldName, ", type=", _type, ", reverse=", _reverse,
			"}");
	}

	private String _fieldName;
	private boolean _reverse;
	private int _type;

}