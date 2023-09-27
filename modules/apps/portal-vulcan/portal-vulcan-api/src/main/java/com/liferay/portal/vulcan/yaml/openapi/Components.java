/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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