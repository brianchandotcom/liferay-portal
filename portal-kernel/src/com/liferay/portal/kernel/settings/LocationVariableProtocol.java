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

package com.liferay.portal.kernel.settings;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public enum LocationVariableProtocol {

	FILE("file"), RESOURCE("resource"), SERVER_PROPERTY("server-property");

	public static boolean isProtocol(String string) {
		for (LocationVariableProtocol locationVariableProtocol :
				LocationVariableProtocol.values()) {

			if (locationVariableProtocol.equals(string)) {
				return true;
			}
		}

		return false;
	}

	public boolean equals(String protocolString) {
		return Objects.equals(_value, protocolString);
	}

	public String getValue() {
		return _value;
	}

	private LocationVariableProtocol(String value) {
		_value = value;
	}

	private final String _value;

}