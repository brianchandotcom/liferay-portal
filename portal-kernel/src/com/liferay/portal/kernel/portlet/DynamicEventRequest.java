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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.EventRequest;
import javax.portlet.filter.EventRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 * @see    DynamicActionRequest
 * @see    DynamicRenderRequest
 * @see    DynamicResourceRequest
 */
public class DynamicEventRequest extends EventRequestWrapper {

	public DynamicEventRequest(EventRequest eventRequest) {
		this(eventRequest, null, true);
	}

	public DynamicEventRequest(EventRequest eventRequest, boolean inherit) {
		this(eventRequest, null, inherit);
	}

	public DynamicEventRequest(
		EventRequest eventRequest, Map<String, String[]> params) {

		this(eventRequest, params, true);
	}

	public DynamicEventRequest(
		EventRequest eventRequest, Map<String, String[]> params,
		boolean inherit) {

		super(eventRequest);

		_params = new HashMap<>();
		_inherit = inherit;

		if (params != null) {
			_params.putAll(params);
		}

		if (_inherit && (eventRequest instanceof DynamicEventRequest)) {
			DynamicEventRequest dynamicEventRequest =
				(DynamicEventRequest)eventRequest;

			setRequest(dynamicEventRequest.getRequest());

			params = dynamicEventRequest.getDynamicParameterMap();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String name = entry.getKey();
				String[] oldValues = entry.getValue();

				String[] curValues = _params.get(name);

				if (curValues == null) {
					_params.put(name, oldValues);
				}
				else {
					String[] newValues = ArrayUtil.append(oldValues, curValues);

					_params.put(name, newValues);
				}
			}
		}
	}

	public Map<String, String[]> getDynamicParameterMap() {
		return _params;
	}

	@Override
	public String getParameter(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameter(name);
		}

		if (ArrayUtil.isNotEmpty(values)) {
			return values[0];
		}

		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<>();

		if (_inherit) {
			map.putAll(super.getParameterMap());
		}

		map.putAll(_params);

		return map;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Set<String> names = new LinkedHashSet<>();

		if (_inherit) {
			Enumeration<String> enu = super.getParameterNames();

			while (enu.hasMoreElements()) {
				names.add(enu.nextElement());
			}
		}

		names.addAll(_params.keySet());

		return Collections.enumeration(names);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameterValues(name);
		}

		return values;
	}

	public void setParameter(String name, String value) {
		_params.put(name, new String[] {value});
	}

	public void setParameterValues(String name, String[] values) {
		_params.put(name, values);
	}

	private final boolean _inherit;
	private final Map<String, String[]> _params;

}