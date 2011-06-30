/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.BreadcrumbEntry;

import java.util.Map;

/**
 * @author Sergio González
 */
public class BreadcrumbEntryImpl implements BreadcrumbEntry {

	public BreadcrumbEntryImpl(
		String title, String url, Map<String, Object> data) {

		_title = title;
		_url = url;
		_data = data;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private Map<String, Object> _data;
	private String _title;
	private String _url;

}