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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java;

import java.util.Optional;

/**
 * @author Javier de Arcos
 */
public class JavaClassProperty {

	public JavaClassProperty(
		String description, String name, boolean readOnly, boolean required,
		String type, boolean writeOnly) {

		_description = Optional.ofNullable(
			description
		).orElse(
			""
		);
		_name = name;
		_readOnly = readOnly;
		_required = required;
		_type = type;
		_writeOnly = writeOnly;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isRequired() {
		return _required;
	}

	public boolean isWriteOnly() {
		return _writeOnly;
	}

	private final String _description;
	private final String _name;
	private final boolean _readOnly;
	private final boolean _required;
	private final String _type;
	private final boolean _writeOnly;

}