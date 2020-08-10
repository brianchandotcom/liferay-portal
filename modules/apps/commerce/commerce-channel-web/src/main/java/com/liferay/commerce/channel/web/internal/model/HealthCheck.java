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