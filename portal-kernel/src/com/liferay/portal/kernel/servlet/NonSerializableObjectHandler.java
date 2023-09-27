/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import java.io.Serializable;

/**
 * @author Igor Spasic
 * @see    com.liferay.portal.kernel.util.TransientValue
 */
public final class NonSerializableObjectHandler implements Serializable {

	public static Object getValue(Object value) {
		if (value instanceof NonSerializableObjectHandler) {
			NonSerializableObjectHandler nonSerializableObjectHandler =
				(NonSerializableObjectHandler)value;

			value = nonSerializableObjectHandler.getValue();
		}

		return value;
	}

	public NonSerializableObjectHandler(Object value) {
		_value = value;
	}

	public Object getValue() {
		return _value;
	}

	private final transient Object _value;

}