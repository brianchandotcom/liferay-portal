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

package com.liferay.info.sort;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Jorge Ferrer
 */
public class Sort implements Serializable {

	public Sort(String fieldName, boolean reverse) {
		_fieldName = fieldName;
		_reverse = reverse;
	}

	public String getFieldName() {
		return _fieldName;
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

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{fieldName=");
		sb.append(_fieldName);
		sb.append(", reverse=");
		sb.append(_reverse);
		sb.append("}");

		return sb.toString();
	}

	private String _fieldName;
	private boolean _reverse;

}