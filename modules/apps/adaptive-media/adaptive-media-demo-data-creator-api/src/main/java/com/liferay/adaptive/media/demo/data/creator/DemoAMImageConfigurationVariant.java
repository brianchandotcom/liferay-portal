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

package com.liferay.adaptive.media.demo.data.creator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public enum DemoAMImageConfigurationVariant {

	L("Large demo size", "", "demo-large", 800, 800),
	M("Medium size", "", "demo-medium", 400, 400),
	S("Small demo size", "", "demo-small", 100, 100),
	XL("Extra large demo size", "", "demo-xlarge", 1200, 1200),
	XS("Extra small demo size", "", "demo-xsmall", 50, 50);

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", String.valueOf(_maxHeight));
		properties.put("max-width", String.valueOf(_maxWidth));

		return properties;
	}

	public String getUuid() {
		return _uuid;
	}

	private DemoAMImageConfigurationVariant(
		String name, String description, String uuid, int maxHeight,
		int maxWidth) {

		_name = name;
		_description = description;
		_uuid = uuid;
		_maxHeight = maxHeight;
		_maxWidth = maxWidth;
	}

	private final String _description;
	private final int _maxHeight;
	private final int _maxWidth;
	private final String _name;
	private final String _uuid;

}