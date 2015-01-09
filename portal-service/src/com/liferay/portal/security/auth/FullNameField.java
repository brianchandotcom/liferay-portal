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

import com.liferay.portal.kernel.util.ArrayUtil;

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

	public String[] getValueOptions() {
		return _valueOptions;
	}

	public boolean isFreeText() {
		return ArrayUtil.isEmpty(_valueOptions);
	}

	public boolean isRequired() {
		return _required;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setValueOptions(String[] valueOptions) {
		_valueOptions = valueOptions;
	}

	private final String _name;
	private boolean _required;
	private String[] _valueOptions;

}