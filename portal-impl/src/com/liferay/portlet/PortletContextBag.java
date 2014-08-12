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

package com.liferay.portlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURLGenerationListener;
import javax.portlet.filter.PortletFilter;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextBag {

	public PortletContextBag(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public Map<String, CustomUserAttributes> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	public Map<String, PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public Map<String, PortletURLGenerationListener> getPortletURLListeners() {
		return _urlListeners;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	private final Map<String, CustomUserAttributes> _customUserAttributes =
		new HashMap<String, CustomUserAttributes>();
	private final Map<String, PortletFilter> _portletFilters =
		new HashMap<String, PortletFilter>();
	private final String _servletContextName;
	private final Map<String, PortletURLGenerationListener> _urlListeners =
		new HashMap<String, PortletURLGenerationListener>();

}