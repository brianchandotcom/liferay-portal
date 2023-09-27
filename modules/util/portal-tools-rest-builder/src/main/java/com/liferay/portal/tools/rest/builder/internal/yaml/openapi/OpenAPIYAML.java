/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml.openapi;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class OpenAPIYAML {

	public Components getComponents() {
		return _components;
	}

	public Info getInfo() {
		return _info;
	}

	public Map<String, PathItem> getPathItems() {
		return _pathItems;
	}

	public void setComponents(Components components) {
		_components = components;
	}

	public void setInfo(Info info) {
		_info = info;
	}

	public void setPathItems(Map<String, PathItem> pathItems) {
		_pathItems = pathItems;
	}

	private Components _components;
	private Info _info;
	private Map<String, PathItem> _pathItems;

}