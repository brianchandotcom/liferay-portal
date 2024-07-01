/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.openapi;

import java.util.Map;

/**
 * @author Alberto Javier Moreno Lage
 */
public class Discriminator {

	public Map<String, String> getMapping() {
		return _mapping;
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public void setMapping(Map<String, String> mapping) {
		_mapping = mapping;
	}

	public void setPropertyName(String propertyName) {
		_propertyName = propertyName;
	}

	private Map<String, String> _mapping;
	private String _propertyName;

}