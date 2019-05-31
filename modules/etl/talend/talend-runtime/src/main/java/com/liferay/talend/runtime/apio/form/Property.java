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

package com.liferay.talend.runtime.apio.form;

/**
 * Represents the Apio form's property
 *
 * @author Zoltán Takács
 */
public class Property {

	public Property(
		String name, boolean required, boolean readable, boolean writeable) {

		if (name == null) {
			throw new IllegalArgumentException("Name is NULL");
		}

		_name = name;
		_required = required;
		_readable = readable;
		_writeable = writeable;
	}

	public String getName() {
		return _name;
	}

	public boolean isReadable() {
		return _readable;
	}

	public boolean isRequired() {
		return _required;
	}

	public boolean isWriteable() {
		return _writeable;
	}

	private final String _name;
	private final boolean _readable;
	private final boolean _required;
	private final boolean _writeable;

}