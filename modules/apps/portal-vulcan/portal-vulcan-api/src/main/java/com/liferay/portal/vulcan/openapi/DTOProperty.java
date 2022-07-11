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

package com.liferay.portal.vulcan.openapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class DTOProperty {

	public DTOProperty(
		Map<String, Object> extensions, String name, String type) {

		_extensions = extensions;
		_name = name;
		_type = type;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public DTOProperty(String name, String type) {
		this(new HashMap<>(), name, type);
	}

	public String getDescription() {
		return _description;
	}

	public List<DTOProperty> getDTOProperties() {
		return _dtoProperties;
	}

	public Map<String, Object> getExtensions() {
		return _extensions;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDTOProperties(List<DTOProperty> dtoProperties) {
		_dtoProperties = dtoProperties;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _description;
	private List<DTOProperty> _dtoProperties = new ArrayList<>();
	private final Map<String, Object> _extensions;
	private String _name;
	private boolean _required;
	private String _type;

}