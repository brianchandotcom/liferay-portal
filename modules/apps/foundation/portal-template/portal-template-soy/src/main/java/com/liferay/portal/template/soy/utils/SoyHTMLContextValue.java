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

package com.liferay.portal.template.soy.utils;

/**
 * @author Bruno Basto
 */
public class SoyHTMLContextValue implements CharSequence {

	public SoyHTMLContextValue(String value) {
		this._value = value;
	}

	@Override
	public char charAt(int index) {
		return _value.charAt(index);
	}

	@Override
	public int length() {
		return _value.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return _value.subSequence(start, end);
	}

	@Override
	public String toString() {
		return _value;
	}

	private final String _value;

}