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