/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.demo.data.creator;

import com.liferay.portal.kernel.util.HashMapBuilder;

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
		return HashMapBuilder.put(
			"max-height", String.valueOf(_maxHeight)
		).put(
			"max-width", String.valueOf(_maxWidth)
		).build();
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