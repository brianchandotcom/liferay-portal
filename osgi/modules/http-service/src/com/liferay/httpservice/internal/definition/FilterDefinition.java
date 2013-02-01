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

package com.liferay.httpservice.internal.definition;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Filter;

/**
 * @author Raymond Augé
 */
public class FilterDefinition {

	public FilterDefinition() {
	}

	public FilterDefinition(
		String name, Filter filter, Dictionary<String, String> initParams) {

		_filter = filter;
		_initParams = initParams;
		_name = name;
	}

	public Filter getFilter() {
		return _filter;
	}

	public Dictionary<String, String> getInitParams() {
		return _initParams;
	}

	public String getName() {
		return _name;
	}

	public void setFilter(Filter filter) {
		_filter = filter;
	}

	public void setInitParam(String key, String value) {
		if (_initParams == null) {
			_initParams = new Hashtable<String, String>();
		}

		_initParams.put(key, value);
	}

	public void setInitParams(Dictionary<String, String> initParams) {
		_initParams = initParams;
	}

	public void setName(String name) {
		_name = name;
	}

	private Filter _filter;
	private Dictionary<String, String> _initParams;
	private String _name;

}