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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class Capability implements Serializable {

	public Capability(String name, String value) {
		_name = name;
		_value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Capability)) {
			return false;
		}

		Capability capability = (Capability)obj;

		if (Validator.equals(_name, capability._name) &&
			Validator.equals(_value, capability._value)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name=");
		sb.append(_name);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private final String _name;
	private final String _value;

}