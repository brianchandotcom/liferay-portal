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

package com.liferay.bean.portlet.cdi.extension.internal.annotated;

import com.liferay.bean.portlet.cdi.extension.internal.xml.BaseBeanFilterImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Neil Griffin
 */
public class BeanFilterAnnotationImpl extends BaseBeanFilterImpl {

	public BeanFilterAnnotationImpl(
		Class<?> filterClass, PortletLifecycleFilter portletLifecycleFilter) {

		_filterClass = filterClass;
		_portletLifecycleFilter = portletLifecycleFilter;
		_initParams = new HashMap<>();

		for (InitParameter initParameter :
				_portletLifecycleFilter.initParams()) {

			_initParams.put(initParameter.name(), initParameter.value());
		}

		_lifecycles = new LinkedHashSet<>();

		if (ActionFilter.class.isAssignableFrom(filterClass)) {
			_lifecycles.add(PortletRequest.ACTION_PHASE);
		}

		if (EventFilter.class.isAssignableFrom(filterClass)) {
			_lifecycles.add(PortletRequest.EVENT_PHASE);
		}

		if (HeaderFilter.class.isAssignableFrom(filterClass)) {
			_lifecycles.add(PortletRequest.HEADER_PHASE);
		}

		if (RenderFilter.class.isAssignableFrom(filterClass)) {
			_lifecycles.add(PortletRequest.RENDER_PHASE);
		}

		if (ResourceFilter.class.isAssignableFrom(filterClass)) {
			_lifecycles.add(PortletRequest.RESOURCE_PHASE);
		}

		_portletNames = new HashSet<>(
			Arrays.asList(_portletLifecycleFilter.portletNames()));
	}

	@Override
	public Class<?> getFilterClass() {
		return _filterClass;
	}

	@Override
	public String getFilterName() {
		return _portletLifecycleFilter.filterName();
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
	public int getOrdinal() {
		return _portletLifecycleFilter.ordinal();
	}

	@Override
	public Set<String> getPortletNames() {
		return _portletNames;
	}

	private final Class<?> _filterClass;
	private final Map<String, String> _initParams;
	private final Set<String> _lifecycles;
	private final PortletLifecycleFilter _portletLifecycleFilter;
	private final Set<String> _portletNames;

}