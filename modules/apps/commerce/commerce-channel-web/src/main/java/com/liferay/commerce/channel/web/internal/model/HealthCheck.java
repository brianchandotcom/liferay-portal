/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

/**
 * @author Marco Leo
 */
public class HealthCheck {

	public HealthCheck(String key, String name, String description) {
		_key = key;
		_name = name;
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	private final String _description;
	private final String _key;
	private final String _name;

}