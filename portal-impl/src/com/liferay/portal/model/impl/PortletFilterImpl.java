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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;

import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletFilterImpl implements PortletFilter {

	public PortletFilterImpl(
		String filterName, String filterClass, Set<String> lifecycles,
		Map<String, String> initParams, PortletApp portletApp) {

		_filterName = filterName;
		_filterClass = filterClass;
		_lifecycles = lifecycles;
		_initParams = initParams;
		_portletApp = portletApp;
	}

	@Override
	public String getFilterClass() {
		return _filterClass;
	}

	@Override
	public String getFilterName() {
		return _filterName;
	}

	@Override
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	@Override
	public Set<String> getLifecycles() {
		return _lifecycles;
	}

	@Override
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	@Override
	public void setFilterClass(String filterClass) {
		_filterClass = filterClass;
	}

	@Override
	public void setFilterName(String filterName) {
		_filterName = filterName;
	}

	@Override
	public void setInitParams(Map<String, String> initParams) {
		_initParams = initParams;
	}

	@Override
	public void setLifecycles(Set<String> lifecycles) {
		_lifecycles = lifecycles;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _filterClass;
	private String _filterName;
	private Map<String, String> _initParams;
	private Set<String> _lifecycles;
	private PortletApp _portletApp;

}