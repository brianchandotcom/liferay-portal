/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return StringBundler.concat(
			"{fieldName=", _fieldName, ", reverse=", _reverse, "}");
	}

	private String _fieldName;
	private boolean _reverse;

}