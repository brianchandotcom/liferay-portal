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

package com.liferay.talend.properties.parameters;

import java.util.Objects;

import javax.ws.rs.core.UriBuilder;

/**
 * @author Igor Beslic
 */
public class RequestParameter {

	public RequestParameter(String location, String name, String value) {
		_location = location;
		_name = name;
		_value = value;
	}

	public void apply(UriBuilder uriBuilder) {
		if (Objects.equals("query", _location)) {
			uriBuilder.queryParam(getName(), getValue());
		}
		else if (Objects.equals("path", _location)) {
			uriBuilder.resolveTemplate(getName(), getValue());
		}
	}

	public String getLocation() {
		return _location;
	}

	public String getName() {
		if (_name.endsWith("*")) {
			return _name.substring(0, _name.length() - 1);
		}

		return _name;
	}

	public String getValue() {
		return _value;
	}

	public boolean isPathLocation() {
		if (Objects.equals("path", _location)) {
			return true;
		}

		return false;
	}

	private final String _location;
	private final String _name;
	private final String _value;

}