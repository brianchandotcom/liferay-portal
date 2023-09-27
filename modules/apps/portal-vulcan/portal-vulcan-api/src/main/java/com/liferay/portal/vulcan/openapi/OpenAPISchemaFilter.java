/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.openapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class OpenAPISchemaFilter {

	public String getApplicationPath() {
		return _applicationPath;
	}

	public List<DTOProperty> getDTOProperties() {
		return _dtoProperties;
	}

	public Map<String, String> getSchemaMappings() {
		return _schemaMappings;
	}

	public void setApplicationPath(String applicationPath) {
		_applicationPath = applicationPath;
	}

	public void setDTOProperties(List<DTOProperty> dtoProperties) {
		_dtoProperties = dtoProperties;
	}

	public void setSchemaMappings(Map<String, String> schemaMappings) {
		_schemaMappings = schemaMappings;
	}

	private String _applicationPath;
	private List<DTOProperty> _dtoProperties = new ArrayList<>();
	private Map<String, String> _schemaMappings = new HashMap<>();

}