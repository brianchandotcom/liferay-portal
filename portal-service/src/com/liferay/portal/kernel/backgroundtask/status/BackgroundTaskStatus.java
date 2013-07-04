/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.backgroundtask.status;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatus implements Serializable {

	public Serializable get(String key) {
		return _attributes.get(key);
	}

	public Map<String, Serializable> getAttributes() {
		return Collections.unmodifiableMap(_attributes);
	}

	public String getJSONString() {
		return JSONFactoryUtil.serialize(_attributes);
	}

	public void put(String key, Serializable value) {
		_attributes.put(key, value);
	}

	private Map<String, Serializable> _attributes =
		new ConcurrentHashMap<String, Serializable>();

}