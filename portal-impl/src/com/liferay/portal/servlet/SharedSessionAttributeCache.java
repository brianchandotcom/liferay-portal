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

package com.liferay.portal.servlet;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * @author     Michael C. Han
 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
 */
@Deprecated
public class SharedSessionAttributeCache implements Serializable {

	public static SharedSessionAttributeCache getInstance(HttpSession session) {
		synchronized (session) {
			SharedSessionAttributeCache cache =
				(SharedSessionAttributeCache)session.getAttribute(_SESSION_KEY);

			if (cache == null) {
				cache = new SharedSessionAttributeCache();

				session.setAttribute(_SESSION_KEY, cache);
			}

			return cache;
		}
	}

	public boolean contains(String name) {
		return _attributes.containsKey(name);
	}

	public Map<String, Object> getValues() {
		return _attributes;
	}

	public void removeAttribute(String key) {
		_attributes.remove(key);
	}

	public void setAttribute(String key, Object value) {
		_attributes.put(key, value);
	}

	private SharedSessionAttributeCache() {
		_attributes = new ConcurrentHashMap<>();
	}

	private static final String _SESSION_KEY =
		SharedSessionAttributeCache.class.getName();

	private final Map<String, Object> _attributes;

}