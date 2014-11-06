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

package com.liferay.portal.parsers.bbcode;

/**
 * @author Iliyan Peychev
 */
public class BBCodeItem {

	public BBCodeItem(int type, String attribute, String value) {
		_type = type;
		_attribute = attribute;
		_value = value;
	}

	public String getAttribute() {
		return _attribute;
	}

	public int getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	private final String _attribute;
	private final int _type;
	private final String _value;

}