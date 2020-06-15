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
public class Response {

	public Map<String, Content> getContent() {
		return _content;
	}

	public String getDescription() {
		return _description;
	}

	public void setContent(Map<String, Content> content) {
		_content = content;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private Map<String, Content> _content;
	private String _description;

}