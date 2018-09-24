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

package com.liferay.portal.struts;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class Definition {

	public Definition(String path, Map<String, String> attributes) {
		_path = path;
		_attributes = attributes;
	}

	public Map<String, String> getAttributes() {
		return _attributes;
	}

	public String getPath() {
		return _path;
	}

	private final Map<String, String> _attributes;
	private final String _path;

}