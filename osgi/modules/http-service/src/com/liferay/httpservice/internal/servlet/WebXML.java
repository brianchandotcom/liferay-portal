/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.internal.definition.FilterDefinition;
import com.liferay.httpservice.internal.definition.ListenerDefinition;
import com.liferay.httpservice.internal.definition.ServletDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXML {

	public Hashtable<String, String> getContextParams() {
		return _contextParams;
	}

	public Map<String, FilterDefinition> getFilters() {
		return _filters;
	}

	public List<ListenerDefinition> getListeners() {
		return _listeners;
	}

	public Map<String, ServletDefinition> getServlets() {
		return _servlets;
	}

	public void setContextParam(String key, String value) {
		_contextParams.put(key, value);
	}

	public void setContextParams(Hashtable<String, String> contextParams) {
		_contextParams = contextParams;
	}

	public void setFilter(String filterName, FilterDefinition filter) {
		_filters.put(filterName, filter);
	}

	public void setFilters(Map<String, FilterDefinition> filters) {
		_filters = filters;
	}

	public void setListener(String className, ListenerDefinition listener) {
		_listeners.add(listener);
	}

	public void setListeners(List<ListenerDefinition> listeners) {
		_listeners = listeners;
	}

	public void setServlet(String mapping, ServletDefinition servlet) {
		_servlets.put(mapping, servlet);
	}

	public void setServlets(Map<String, ServletDefinition> servlets) {
		_servlets = servlets;
	}

	private Hashtable<String, String> _contextParams =
		new Hashtable<String, String>();
	private Map<String, FilterDefinition> _filters =
		new HashMap<String, FilterDefinition>();
	private List<ListenerDefinition> _listeners =
		new ArrayList<ListenerDefinition>();
	private Map<String, ServletDefinition> _servlets =
		new HashMap<String, ServletDefinition>();

}