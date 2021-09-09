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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class OpenAPISchemaFilter {

	public String getApplicationPath() {
		return _applicationPath;
	}

	public DTOProperty getDTOProperty() {
		return _dtoProperty;
	}

	public Map<String, String> getSchemaMappings() {
		return _schemaMappings;
	}

	public void setApplicationPath(String applicationPath) {
		_applicationPath = applicationPath;
	}

	public void setDTOProperty(DTOProperty dtoProperty) {
		_dtoProperty = dtoProperty;
	}

	public void setSchemaMappings(Map<String, String> schemaMappings) {
		_schemaMappings = schemaMappings;
	}

	private String _applicationPath;
	private DTOProperty _dtoProperty;
	private Map<String, String> _schemaMappings = new HashMap<>();

}