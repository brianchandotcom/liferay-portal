/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.yaml.openapi;

/**
 * @author Carlos Correa
 */
public class FieldDefinition {

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _name;

}