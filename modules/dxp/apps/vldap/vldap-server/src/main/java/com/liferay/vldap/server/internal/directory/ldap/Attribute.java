/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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