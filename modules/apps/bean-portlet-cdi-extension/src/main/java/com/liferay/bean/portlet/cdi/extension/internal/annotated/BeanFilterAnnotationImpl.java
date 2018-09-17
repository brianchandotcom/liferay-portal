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

import com.liferay.bean.portlet.cdi.extension.internal.BeanFilter;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.PortletLifecycleFilter;

/**
 * @author Neil Griffin
 */
public class BeanFilterAnnotationImpl implements BeanFilter {

	public BeanFilterAnnotationImpl(Class<?> filterClass) {
		_filterClass = filterClass;
		_portletLifecycleFilter = filterClass.getAnnotation(
			PortletLifecycleFilter.class);

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
	public Set<String> getPortletNames() {
		return _portletNames;
	}

	@Override
	public Dictionary<String, Object> toDictionary() {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			"service.ranking:Integer", _portletLifecycleFilter.ordinal());

		for (InitParameter initParameter :
				_portletLifecycleFilter.initParams()) {

			String value = initParameter.value();

			if (value != null) {
				dictionary.put(
					"javax.portlet.init-param.".concat(initParameter.name()),
					value);
			}
		}

		return dictionary;
	}

	private final Class<?> _filterClass;
	private final PortletLifecycleFilter _portletLifecycleFilter;
	private final Set<String> _portletNames;

}