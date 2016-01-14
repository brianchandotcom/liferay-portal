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

package com.liferay.frontend.taglib.servlet.taglib.util;

/**
 * @author Jürgen Kappler
 */
public class ManagementBarFilterItem {

	public ManagementBarFilterItem(String label, String url) {
		this(label, url, label);
	}

	public ManagementBarFilterItem(String label, String url, String value) {
		_label = label;
		_url = url;
		_value = value;
	}

	public String getLabel() {
		return _label;
	}

	public String getURL() {
		return _url;
	}

	public String getValue() {
		return _value;
	}

	private final String _label;
	private final String _url;
	private final String _value;

}