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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Jorge Ferrer
 */
public class FullNameField {

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isFreeText() {
		return ArrayUtil.isEmpty(_values);
	}

	public boolean isRequired() {
		return _required;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private String _name;
	private boolean _required;
	private String[] _values;

}