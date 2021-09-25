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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ObjectActionRequest {

	public ObjectActionRequest(long userId) {
		_userId = userId;
	}

	public Map<String, Serializable> getProperties() {
		return null;
	}

	public long getUserId() {
		return _userId;
	}

	public void setPayload(Serializable payload) {
		_payload = payload;
	}

	public void setProperties(Map<String, Serializable> properties) {
		_properties = properties;
	}

	private Serializable _payload;
	private Map<String, Serializable> _properties = new HashMap<>();
	private final long _userId;

}