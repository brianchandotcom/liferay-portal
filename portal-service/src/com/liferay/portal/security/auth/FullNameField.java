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

package com.liferay.portal.security.auth;

/**
* @author Jorge Ferrer
*/
public class FullNameField {

	public FullNameField(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public String[] getOptions() {
		return _options;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setOptions(String[] options) {
		_options = options;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	private final String _name;
	private String[] _options;
	private boolean _required;

}