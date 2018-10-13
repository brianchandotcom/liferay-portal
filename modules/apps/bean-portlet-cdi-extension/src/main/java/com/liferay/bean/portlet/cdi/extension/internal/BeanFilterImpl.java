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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

import javax.portlet.filter.PortletFilter;

/**
 * @author Neil Griffin
 */
public class BeanFilterImpl implements BeanFilter {

	public BeanFilterImpl(
		String filterName, Class<? extends PortletFilter> filterClass,
		int ordinal, Set<String> portletNames, Set<String> lifecycles,
		Map<String, String> initParams) {

		_filterName = filterName;
		_filterClass = filterClass;
		_ordinal = ordinal;
		_portletNames = portletNames;
		_lifecycles = lifecycles;
		_initParams = initParams;
	}

	@Override
	public Class<? extends PortletFilter> getFilterClass() {
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
	public int getOrdinal() {
		return _ordinal;
	}

	@Override
	public Set<String> getPortletNames() {
		return _portletNames;
	}

	@Override
	public Dictionary<String, Object> toDictionary() {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Set<String> lifecycles = getLifecycles();

		if (!lifecycles.isEmpty()) {
			dictionary.put("filter.lifecycles", lifecycles);
		}

		dictionary.put("service.ranking:Integer", getOrdinal());

		Map<String, String> initParams = getInitParams();

		for (Map.Entry<String, String> entry : initParams.entrySet()) {
			String value = entry.getValue();

			if (value != null) {
				dictionary.put(
					"javax.portlet.init-param.".concat(entry.getKey()), value);
			}
		}

		return dictionary;
	}

	private final Class<? extends PortletFilter> _filterClass;
	private final String _filterName;
	private final Map<String, String> _initParams;
	private final Set<String> _lifecycles;
	private final int _ordinal;
	private final Set<String> _portletNames;

}