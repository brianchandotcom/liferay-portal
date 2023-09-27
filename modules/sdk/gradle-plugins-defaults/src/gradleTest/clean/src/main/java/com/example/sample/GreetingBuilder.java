/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample;

/**
 * @author Peter Shin
 */
public class GreetingBuilder {

	public GreetingBuilder(String name) {
		_name = name;
	}

	public String getHello() {
		return "Hello " + getName();
	}

	public String getName() {
		return _name;
	}

	private final String _name;

}