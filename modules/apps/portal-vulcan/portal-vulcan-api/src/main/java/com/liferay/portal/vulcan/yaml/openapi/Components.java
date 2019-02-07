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

package com.liferay.portal.vulcan.yaml.openapi;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class Components {

	public Map<String, Parameter> getParameters() {
		return _parameters;
	}

	public Map<String, Schema> getSchemas() {
		return _schemas;
	}

	public void setParameters(Map<String, Parameter> parameters) {
		_parameters = parameters;
	}

	public void setSchemas(Map<String, Schema> schemas) {
		_schemas = schemas;
	}

	private Map<String, Parameter> _parameters;
	private Map<String, Schema> _schemas;

}