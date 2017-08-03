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

package com.liferay.vldap.server.internal.directory.ldap;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Igor Beslic
 */
public class Attribute {

	public Attribute(String name, byte[] bytes) {
		setAttributeId(name);
		setValue(bytes);
	}

	public Attribute(String name, String value) {
		setAttributeId(name);
		setValue(value);
	}

	public String getAttributeId() {
		return _attributeId;
	}

	public byte[] getBytes() {
		return _bytes;
	}

	public String getValue() {
		return _value;
	}

	public boolean isBinary() {
		if (_bytes != null) {
			return true;
		}

		return false;
	}

	public void setAttributeId(String attributeId) {
		_attributeId = attributeId;
	}

	public void setValue(byte[] bytes) {
		_bytes = bytes;
	}

	public void setValue(String value) {
		_value = value;
	}

	private String _attributeId;
	private byte[] _bytes;
	private String _value;

}