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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatus implements Serializable {

	public void clearAttributes() {
		_attributes.clear();
	}

	public Serializable getAttribute(String key) {
		return _attributes.get(key);
	}

	public Map<String, Serializable> getAttributes() {
		return Collections.unmodifiableMap(_attributes);
	}

	public String getAttributesJSON() {
		return JSONFactoryUtil.serialize(_attributes);
	}

	public void setAttribute(String key, Serializable value) {
		_attributes.put(key, value);
	}

	private final Map<String, Serializable> _attributes =
		new ConcurrentHashMap<>();

}