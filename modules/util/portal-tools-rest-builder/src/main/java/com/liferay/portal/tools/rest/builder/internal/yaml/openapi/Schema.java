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

import java.util.Map;

/**
 * @author Peter Shin
 */
public class Schema {

	public String getDescription() {
		return _description;
	}

	public String getFormat() {
		return _format;
	}

	public Items getItems() {
		return _items;
	}

	public Map<String, Properties> getProperties() {
		return _properties;
	}

	public String getReference() {
		return _reference;
	}

	public String getType() {
		return _type;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setItems(Items items) {
		_items = items;
	}

	public void setProperties(Map<String, Properties> properties) {
		_properties = properties;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _description;
	private String _format;
	private Items _items;
	private Map<String, Properties> _properties;
	private String _reference;
	private String _type;

}