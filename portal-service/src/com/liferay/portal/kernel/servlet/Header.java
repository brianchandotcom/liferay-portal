/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import java.io.Serializable;

/**
 * @author Michael Young
 */
public class Header implements Serializable {

	public static final int DATE_TYPE = 2;

	public static final int INTEGER_TYPE = 1;

	public static final int STRING_TYPE = 3;

	public long getDateValue() {
		return _dateValue;
	}

	public void setDateValue(long dateValue) {
		_dateValue = dateValue;
	}

	public int getIntValue() {
		return _intValue;
	}

	public void setIntValue(int intValue) {
		_intValue = intValue;
	}

	public String getStringValue() {
		return _stringValue;
	}

	public void setStringValue(String stringValue) {
		_stringValue = stringValue;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	@Override
	public String toString() {
		if (_type == DATE_TYPE) {
			return String.valueOf(_dateValue);
		}
		else if (_type == INTEGER_TYPE) {
			return String.valueOf(_intValue);
		}
		else {
			return _stringValue;
		}
	}

	private int _intValue;
	private long _dateValue;
	private String _stringValue;
	private int _type;

}