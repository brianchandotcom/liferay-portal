/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

import java.util.Objects;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Capability)) {
			return false;
		}

		Capability capability = (Capability)object;

		if (Objects.equals(_name, capability._name) &&
			Objects.equals(_value, capability._value)) {

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
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _value);
	}

	@Override
	public String toString() {
		return StringBundler.concat("{name=", _name, ", value=", _value, "}");
	}

	private final String _name;
	private final String _value;

}