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

package com.liferay.object.field.render;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldRenderingContext {

	public long getGroupId() {
		return _groupId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public Locale getLocale() {
		return _locale;
	}

	public long getObjectEntryId() {
		return _objectEntryId;
	}

	public String getPortletId() {
		return _portletId;
	}

	public Map<String, Object> getProperties() {
		return _properties;
	}

	public Object getProperty(String name) {
		return _properties.get(name);
	}

	public long getUserId() {
		return _userId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setHttpServletResponse(
		HttpServletResponse httpServletResponse) {

		_httpServletResponse = httpServletResponse;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setObjectEntryId(long objectEntryId) {
		_objectEntryId = objectEntryId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setProperties(Map<String, Object> properties) {
		_properties.putAll(properties);
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _groupId;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Locale _locale;
	private long _objectEntryId;
	private String _portletId;
	private final Map<String, Object> _properties = new HashMap<>();
	private long _userId;

}