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

package com.liferay.portal.tools.rest.builder.internal.yaml.openapi;

/**
 * @author Peter Shin
 */
public class Items {

	public String getFormat() {
		return _format;
	}

	public String getReference() {
		return _reference;
	}

	public String getType() {
		return _type;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _format;
	private String _reference;
	private String _type;

}