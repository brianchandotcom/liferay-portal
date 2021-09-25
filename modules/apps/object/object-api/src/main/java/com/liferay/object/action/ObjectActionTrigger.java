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

package com.liferay.object.action;

/**
 * @author Marco Leo
 */
public class ObjectActionTrigger {

	public ObjectActionTrigger(String className, String key, String type) {
		_className = className;
		_key = key;
		_type = type;
	}

	public String getClassName() {
		return _className;
	}

	public String getKey() {
		return _key;
	}

	public String getType() {
		return _type;
	}

	private final String _className;
	private final String _key;
	private final String _type;

}